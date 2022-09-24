package com.shubbi.shoppingCart.service;

import com.shubbi.shoppingCart.entity.Order;
import com.shubbi.shoppingCart.entity.Product;
import com.shubbi.shoppingCart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId){
        return orderRepository.findById(orderId).orElseThrow();
    }

    public Order createOrder(Map<String, Object> orderMap){
        String orderDescription = (String) orderMap.get("description");
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) orderMap.get("items");

        List<Product> orderItems = getOrderItems(cartItems);

        Order newOrder = new Order(orderDescription, orderItems);
        return orderRepository.save(newOrder);
    }

    private List<Product> getOrderItems(List<Map<String, Object>> items) {
        List<Product> productList = items.stream()
                .map(item -> productService.getProductData(item))
                .collect(Collectors.toList());

        return productList;
    }

    public Order updateOrder(Long orderId, Order order){
        return orderRepository.findById(orderId)
                .map(
                        o -> {
                            o.setDescription(order.getDescription());
                            o.setItems(order.getItems());
                            return orderRepository.save(o);
                        }
                ).orElseGet(
                        () -> {
                            order.setId(orderId);
                            return orderRepository.save(order);
                        }
                );
    }

    public void deleteOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow();

        orderRepository.delete(order);
    }
}
