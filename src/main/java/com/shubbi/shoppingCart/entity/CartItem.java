package com.shubbi.shoppingCart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="product_id", referencedColumnName = "id")
    @JsonIncludeProperties({"id", "name"})
    private Product product;

    private Integer quantity;
    private Double amount;

    public CartItem(Product product, Integer quantity, Double amount) {
        this.product = product;
        this.quantity = quantity;
        this.amount = amount;
    }
}
