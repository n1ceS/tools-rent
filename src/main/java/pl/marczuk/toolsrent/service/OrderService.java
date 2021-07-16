package pl.marczuk.toolsrent.service;

import pl.marczuk.toolsrent.model.Order;

import java.util.List;

public interface OrderService {
    Order addOrder(Order order, String username);

    List<Order> findAllByUsernameAndMonth(String username, Integer month);

}
