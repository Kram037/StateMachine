package co.develhope.loginDemo.user.repositories;

import co.develhope.loginDemo.user.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    public Role findByName(String name);

}
