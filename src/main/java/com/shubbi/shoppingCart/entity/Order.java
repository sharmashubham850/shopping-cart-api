package com.shubbi.shoppingCart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name="order_product",
            joinColumns = @JoinColumn(name="order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="product_id", referencedColumnName = "id")
    )
    private List<Product> items;



    public Order(String description, List<Product> items) {
        this.description = description;
        this.items = items;
    }

    public void addItem(Product product){
        if (this.items == null) this.items = new ArrayList<>();

        this.items.add(product);
    }
}
