package co.develhope.loginDemo.order.controllers;

import co.develhope.loginDemo.order.entities.Order;
import co.develhope.loginDemo.order.entities.OrderDTO;
import co.develhope.loginDemo.order.services.OrderService;
import co.develhope.loginDemo.user.entities.User;
import co.develhope.loginDemo.user.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController{

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    public Order create(@RequestBody OrderDTO order) throws Exception{
        return orderService.save(order);
    }

    @GetMapping("/single-order/{id}")
    @PostAuthorize("hasRole('"+Role.RESTAURANT+"') OR returnObject.body == null OR returnObject.body.createdBy.id == authentication.principal.id")
    public ResponseEntity<Order> getSingle(@PathVariable Long id,Principal principal){
        Optional<Order> order = orderService.findById(id);
        if(order.isEmpty()) return ResponseEntity.notFound().build();
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(Role.hasRole(user,Role.REGISTERED) && order.get().getCreatedBy().getId() == user.getId()){
            return ResponseEntity.ok(order.get());
        }else if(Role.hasRole(user,Role.RESTAURANT) && order.get().getRestaurant().getId() == user.getId()) {
            return ResponseEntity.ok(order.get());
        }else if(Role.hasRole(user,Role.RIDER) && order.get().getRider().getId() == user.getId()) {
            return ResponseEntity.ok(order.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all-orders")
    public ResponseEntity<Object> getAll(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Role.hasRole(user, Role.REGISTERED)){
            return ResponseEntity.ok(orderService.findByCreatedBy(user));
        }else if(Role.hasRole(user, Role.RESTAURANT)){
            return ResponseEntity.ok(orderService.findByRestaurant(user));
        } else if (Role.hasRole(user, Role.RIDER)) {
            return ResponseEntity.ok(orderService.findByRider(user));
        }
        return null;
    }

}
