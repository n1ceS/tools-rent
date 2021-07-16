package pl.marczuk.toolsrent.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marczuk.toolsrent.exception.BadRequestException;
import pl.marczuk.toolsrent.exception.ResourceNotFoundException;
import pl.marczuk.toolsrent.model.Item;
import pl.marczuk.toolsrent.model.Order;
import pl.marczuk.toolsrent.model.OrderCost;
import pl.marczuk.toolsrent.model.User;
import pl.marczuk.toolsrent.payload.ApiResponse;
import pl.marczuk.toolsrent.repository.OrderRepository;
import pl.marczuk.toolsrent.repository.UserRepository;
import pl.marczuk.toolsrent.service.OrderService;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Order addOrder(Order order, String username) {
//        ---VERSION PREPARED FOR HOURS---
//        long hours = ChronoUnit.HOURS.between(order.getStartDate(), order.getEndDate()); // ex. when 30 minutes, then will be 0 hours
//        long hours = (long) Math.ceil(ChronoUnit.MINUTES.between(order.getStartDate(), order.getEndDate())/60D); // version with 30 min - 1 hour, 60 min - 1 hour and 61 min -
        
        long minutes = ChronoUnit.MINUTES.between(order.getStartDate(), order.getEndDate());
        Item item = order.getItem();
        BigDecimal totalCost = item.getPriceList().getPrice().multiply(BigDecimal.valueOf(minutes));
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
        order.setUser(user);
        order.setOrderCost(new OrderCost(order, totalCost));

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public List<Order> findAllByUsernameAndMonth(String username, Integer month) {
        userRepository.findUserByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
        if(month < 0 || month > 12) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Value month have to be beetween 1 and 12");
            throw new BadRequestException(apiResponse);
        }
        String date = Calendar.getInstance().get(Calendar.YEAR) + "-" + String.format("%02d", month) + "%"; //
        List<Order> orders = orderRepository.findAllByUsernameAndMonth(username, date);

        return orders;
    }
}
