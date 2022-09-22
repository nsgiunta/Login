package co.develhope.Login.order.entities;

import co.develhope.Login.user.entities.User;
import co.develhope.Login.utils.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;

    private OrderStateEnum status = OrderStateEnum.CREATED;

    @ManyToOne
    private User restaurant;

    @ManyToOne
    private User rider;

    private Double totalPrice;

    public Order() {
    }

    public Order(Long id, String description, String address, String number, String city, String zipCode, String state,
                 Double totalPrice, OrderStateEnum status, User restaurant, User rider) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.number = number;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.status = status;
        this.restaurant = restaurant;
        this.rider = rider;
    }

    public OrderStateEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStateEnum status) {
        this.status = status;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
}
