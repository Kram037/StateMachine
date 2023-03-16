package co.develhope.loginDemo.order.services;

import co.develhope.loginDemo.order.entities.Order;
import co.develhope.loginDemo.order.entities.OrderDTO;
import co.develhope.loginDemo.order.repositories.OrderRepository;
import co.develhope.loginDemo.user.entities.User;
import co.develhope.loginDemo.user.repositories.UserRepository;
import co.develhope.loginDemo.user.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public Order save(OrderDTO orderInput) throws Exception{
        if(orderInput == null) return null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(user);
        order.setAddress(orderInput.getAddress());
        order.setCity(orderInput.getCity());
        order.setDescription(orderInput.getDescription());
        order.setState(orderInput.getState());
        order.setZipCode(orderInput.getZipCode());
        order.setNumber(orderInput.getNumber());
        order.setTotalPrice(orderInput.getTotalPrice());
        Optional<User> restaurant = userRepository.findById(orderInput.getRestaurantDTO());
        if(restaurant.isEmpty() || !Role.hasRole(restaurant.get(), Role.RESTAURANT)) throw new Exception("Restaurant not found");
        order.setRestaurant(restaurant.get());
        return orderRepository.save(order);
    }

    public Order update(Long id, Order orderInput){
        if(orderInput == null) return null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderInput.setId(id);
        orderInput.setUpdatedAt(LocalDateTime.now());
        orderInput.setUpdatedBy(user);
        return orderRepository.save(orderInput);
    }

    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> order = orderRepository.findById(id);
        return order.filter(value -> value.getCreatedBy().getId() == user.getId()).isPresent();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Object findByCreatedBy(User user) {
        return orderRepository.findByCreatedBy(user);
    }

    public Object findByRestaurant(User user) {
        return orderRepository.findByRestaurant(user);
    }

    public Object findByRider(User user) {
        return orderRepository.findByRider(user);
    }

}
