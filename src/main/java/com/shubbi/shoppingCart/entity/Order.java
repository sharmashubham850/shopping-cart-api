package com.shubbi.shoppingCart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 50, initialValue = 1001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    private Long orderId;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private List<OrderItem> items;

    public Order(String description) {
        this.description = description;
    }

    public Order(String description, List<OrderItem> items) {
        this.description = description;
        this.items = items;
    }

    public void addOrderItem(OrderItem item){
        if (this.items == null) this.items = new ArrayList<>();

        this.items.add(item);


    }
}
