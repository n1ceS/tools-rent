package pl.marczuk.toolsrent.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.marczuk.toolsrent.dto.OrderDto;
import pl.marczuk.toolsrent.model.Item;
import pl.marczuk.toolsrent.model.Order;
import pl.marczuk.toolsrent.service.ItemService;
import pl.marczuk.toolsrent.service.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final ItemService itemService;
    private final OrderService orderService;

    public OrderController(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<List<OrderDto>> getUserOrdersByMonth(@PathVariable(name = "month") Integer month, @ApiParam(hidden = true) @AuthenticationPrincipal String username) {
        List<Order> orders = orderService.findAllByUsernameAndMonth(username, month);
        return new ResponseEntity<>(orders.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    //
    @GetMapping("{username}/month/{month}")
    public ResponseEntity<List<OrderDto>> getUserOrdersByMonthAndUser(@PathVariable(name = "month") Integer month, @PathVariable(name = "username" ) String username) {
        List<Order> orders = orderService.findAllByUsernameAndMonth(username, month);
        return new ResponseEntity<>(orders.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<OrderDto> addOrder(@RequestBody @Valid OrderDto orderDto, @ApiParam(hidden = true) @AuthenticationPrincipal String username) {
        Order order = orderService.addOrder(convertToEntity(orderDto), username);
        orderDto.setTotalCost(order.getOrderCost().getTotalCost());
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    //that can be extracted to utils
    private Order convertToEntity(OrderDto orderDto) {
        Order order = new Order();
        Item item = itemService.getItemByName(orderDto.getItemName().toUpperCase());
        order.setStartDate(orderDto.getStartDate());
        order.setEndDate(orderDto.getEndDate());
        order.setItem(item);
        return order;
    }
    private OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setItemName(order.getItem().getName());
        orderDto.setStartDate(order.getStartDate());
        orderDto.setEndDate(order.getEndDate());
        orderDto.setTotalCost(order.getOrderCost().getTotalCost());
        return orderDto;
    }
}
