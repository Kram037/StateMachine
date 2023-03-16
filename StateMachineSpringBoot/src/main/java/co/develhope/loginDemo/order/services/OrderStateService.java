package co.develhope.loginDemo.order.services;

import co.develhope.loginDemo.order.entities.Order;
import co.develhope.loginDemo.order.entities.OrderStateEnum;
import co.develhope.loginDemo.order.repositories.OrderRepository;
import co.develhope.loginDemo.user.entities.User;
import co.develhope.loginDemo.user.services.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderStateService{

    @Autowired
    private OrderRepository ordersRepository;

    @Autowired
    private RiderService riderService;

    public Order setAccept(Order order) throws Exception {
        if(order == null) throw new Exception("Order is null");
        if(order.getOrderState() != OrderStateEnum.CREATED) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(order.getRestaurant().getId() != user.getId()) throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.ACCEPTED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return ordersRepository.save(order);
    }

    public Order setInPreparation(Order order) throws Exception{
        if(order == null) throw new Exception("Order is null");
        if(order.getOrderState() != OrderStateEnum.ACCEPTED) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(order.getRestaurant().getId() != user.getId()) throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.IN_PREPARATION);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return ordersRepository.save(order);
    }

    public Order setReady(Order order) throws Exception{
        if(order == null) throw new Exception("Order is null");
        if(order.getOrderState() != OrderStateEnum.IN_PREPARATION) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(order.getRestaurant().getId() != user.getId()) throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.READY);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return ordersRepository.save(order);
    }

    public Order setDelivering(Order order) throws Exception{
        if(order == null) throw new Exception("Order is null");
        if(order.getOrderState() != OrderStateEnum.READY) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(order.getRider().getId() != user.getId()) throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.DELIVERING);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return ordersRepository.save(order);
    }

    public Order setCompleted(Order order) throws Exception{
        if(order == null) throw new Exception("Order is null");
        if(order.getOrderState() != OrderStateEnum.READY) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(order.getRider().getId() != user.getId()) throw new Exception("This is not your order");
        order.setOrderState(OrderStateEnum.COMPLETED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return ordersRepository.save(order);
    }

    public Optional<Order> findById(long id) {
        return ordersRepository.findById(id);
    }

}
