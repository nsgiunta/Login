package co.develhope.Login.order.service;

import co.develhope.Login.order.entities.Order;
import co.develhope.Login.order.entities.OrderDTO;
import co.develhope.Login.order.repositories.OrdersRepository;
import co.develhope.Login.user.entities.User;
import co.develhope.Login.user.repositories.UserRepository;
import co.develhope.Login.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    public Order save(OrderDTO orderInput) throws Exception {
        if (orderInput == null) return null;
      User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(user);
        order.setAddress(orderInput.getAddress());
        order.setNumber(orderInput.getNumber());
        order.setCity(order.getCity());
        order.setZipCode(orderInput.getZipCode());
        order.setState(orderInput.getState());
        order.setDescription(orderInput.getDescription());
        order.setTotalPrice(orderInput.getTotalPrice());
      //check for restaurant
        if (orderInput.getRestaurant() == null) throw new Exception("Restaurant is null");
        Optional<User> restaurant = userRepository.findById(orderInput.getRestaurant());
        if (!restaurant.isPresent() || !Roles.hasRole(restaurant.get(), Roles.RESTAURANT))
            throw new Exception("Restaurant not found");
        return ordersRepository.save(order);
    }


    public Order update(Long id, Order orderInput){
        if (orderInput == null) return null;
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderInput.setId(id);
        orderInput.setUpdatedAt(LocalDateTime.now());
        orderInput.setUpdatedBy(user);
        return ordersRepository.save(orderInput);
    }

    public boolean canEdit(Long id) {
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> order = ordersRepository.findById(id);
        if (!order.isPresent()) return false;
        return order.get().getCreatedBy().getId() == user.getId();
    }
}
