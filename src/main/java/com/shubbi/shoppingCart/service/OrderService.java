package com.shubbi.shoppingCart.service;

import com.shubbi.shoppingCart.entity.Customer;
import com.shubbi.shoppingCart.entity.Order;
import com.shubbi.shoppingCart.entity.CartItem;
import com.shubbi.shoppingCart.entity.Product;
import com.shubbi.shoppingCart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public List<CartItem> orderItems(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow();
        return order.getItems();
    }

    public Order createOrder(Map<String, Object> orderMap){
        String orderDescription = (String) orderMap.get("description");
        String customerName = (String) orderMap.get("customerName");
        String customerEmail = (String) orderMap.get("customerEmail");
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) orderMap.get("cartItems");

        Customer newCustomer = new Customer(customerName, customerEmail);

        List<CartItem> orderItems = getOrderItems(cartItems);
        double orderAmount = 0.0;
        for(CartItem item: orderItems){
            orderAmount += item.getAmount();
        }

        Order order = new Order(orderDescription, LocalDateTime.now(), newCustomer, orderItems, orderAmount);

        return orderRepository.save(order);


    }

    private List<CartItem> getOrderItems(List<Map<String, Object>> items) {
        List<CartItem> orderItems = items.stream()
                .map(item -> {
                    Long productId = Long.valueOf((Integer)(item.get("productId")));
                    Integer quantity = (Integer)item.get("quantity");

                    Product cartProduct = productService.getProductData(productId, quantity);

                    return new CartItem(cartProduct, quantity, cartProduct.getPrice()*quantity);
                })
                .collect(Collectors.toList());

        return orderItems;
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
