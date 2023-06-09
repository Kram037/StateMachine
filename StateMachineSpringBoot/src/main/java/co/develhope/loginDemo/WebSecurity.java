package co.develhope.loginDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception{
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()

                .antMatchers("/auth/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/admin/global/all-data-eraser").hasRole("ROLE_SUPER_ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN","ROLE_OWNER","ROLE_SUPER_ADMIN")
                .antMatchers("/blog/**").hasRole("ROLE_EDITOR")
                .antMatchers("/dev-tools/**").hasAnyAuthority("DEV_READ","DEV_DELETE")
                .antMatchers("/dev-tools-bis/**").hasAnyAuthority("DO_DEV_TOOLS_READ")
                .anyRequest().authenticated();
        ;

        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);

    }

}


