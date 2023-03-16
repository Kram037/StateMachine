package co.develhope.loginDemo.salary.controllers;

import co.develhope.loginDemo.salary.entities.CreateSalaryDTO;
import co.develhope.loginDemo.salary.entities.Salary;
import co.develhope.loginDemo.user.entities.User;
import co.develhope.loginDemo.user.repositories.UserRepository;
import co.develhope.loginDemo.user.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import co.develhope.loginDemo.salary.repositories.SalaryRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all-user/{id}")
    @PreAuthorize("hasRole('"+Role.ADMIN+"')")
    public List<Salary> getAll(@PathVariable Long id){
        return salaryRepository.findAll();
    }

    @GetMapping("/salary-user/{id}")
    @PreAuthorize("hasRole('"+Role.ADMIN+"')")
    public Salary getSalaryByUid(@PathVariable Long id){
        return salaryRepository.findByUserId(id);
    }

    @PostMapping("/create-user-salary/{id}")
    @PreAuthorize("hasRole('"+Role.ADMIN+"')")
    public Salary createSalary(@PathVariable Long id, @RequestBody CreateSalaryDTO salaryDTO){
        Salary s = new Salary();
        s.setAmount(salaryDTO.getAmount());
        Optional<User> user = userRepository.findById(id);
        s.setUser(user.get());
        return salaryRepository.save(s);
    }

}
