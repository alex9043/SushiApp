package ru.alex9043.sushiapp.repository.order.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.order.order.Order;
import ru.alex9043.sushiapp.model.user.User;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findByUser(User user);
}