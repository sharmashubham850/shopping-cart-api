package com.shubbi.shoppingCart.controller;

import com.shubbi.shoppingCart.entity.Order;
import com.shubbi.shoppingCart.entity.CartItem;
import com.shubbi.shoppingCart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orderList = orderService.getAllOrders();

        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId){
        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/items/{orderId}")
    public ResponseEntity<List<CartItem>> getOrderItems(@PathVariable Long orderId){
        List<CartItem> cartItemList = orderService.orderItems(orderId);

        return ResponseEntity.ok(cartItemList);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> orderMap){
        Order createdOrder = orderService.createOrder(orderMap);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order){
        Order updatedOrder = orderService.updateOrder(orderId, order);

        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);

        return ResponseEntity.status(200).body("Order delete successful");
    }
}
