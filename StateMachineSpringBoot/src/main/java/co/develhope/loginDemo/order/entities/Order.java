package co.develhope.loginDemo.order.entities;

import co.develhope.loginDemo.entities.BaseEntity;
import co.develhope.loginDemo.user.entities.User;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;
    private String address;
    private String number;
    private String city;
    private String zipCode;
    private double totalPrice;
    private String state;
    private OrderStateEnum orderStateEnum;

    @ManyToOne
    private User restaurant;

    @ManyToOne
    private User rider;

    public Order(Long id,String description,String address,String number,String city,String zipCode,double totalPrice,String state,OrderStateEnum orderStateEnum,User restaurant,User rider) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.number = number;
        this.city = city;
        this.zipCode = zipCode;
        this.totalPrice = totalPrice;
        this.state = state;
        this.orderStateEnum = orderStateEnum;
        this.restaurant = restaurant;
        this.rider = rider;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OrderStateEnum getOrderState() {
        return orderStateEnum;
    }

    public void setOrderState(OrderStateEnum orderStateEnum) {
        this.orderStateEnum = orderStateEnum;
    }

    public User getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(User restaurant) {
        this.restaurant = restaurant;
    }

    public User getRider() {
        return rider;
    }

    public void setRider(User rider) {
        this.rider = rider;
    }

}
