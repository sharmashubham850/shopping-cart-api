package com.shubbi.shoppingCart.service;

import com.shubbi.shoppingCart.entity.Order;
import com.shubbi.shoppingCart.entity.OrderItem;
import com.shubbi.shoppingCart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public List<OrderItem> orderItems(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow();
        return order.getItems();
    }

    public Order createOrder(Map<String, Object> orderMap){
        String orderDescription = (String) orderMap.get("description");
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) orderMap.get("items");

        List<OrderItem> orderItems = getOrderItems(cartItems);

        Order order = new Order(orderDescription, orderItems);
        Order createdOrder = orderRepository.save(order);

//        orderItems.forEach(orderItem -> {
//            orderItem.setOrder(createdOrder);
//        });

        System.out.println("createdOrder = " + createdOrder);
        return createdOrder;

    }

    private List<OrderItem> getOrderItems(List<Map<String, Object>> items) {
        List<OrderItem> orderItemList = items.stream()
                .map(item -> {
                    Long productId = Long.valueOf((Integer)(item.get("productId")));
                    Integer quantity = (Integer)item.get("quantity");

//                    Product productData = productService.getProductData(productId, quantity);
                    return new OrderItem(productId, quantity);
                })
                .collect(Collectors.toList());

        return orderItemList;
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
                            order.setOrderId(orderId);
                            return orderRepository.save(order);
                        }
                );
    }

    public void deleteOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow();

        orderRepository.delete(order);
    }
}
