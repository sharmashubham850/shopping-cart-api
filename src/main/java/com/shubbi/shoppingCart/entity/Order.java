package com.shubbi.shoppingCart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    private Long orderId;

    private String description;
    private LocalDateTime date;

    @Generated(GenerationTime.INSERT)
    @Column(columnDefinition = "serial")
    private Integer invoiceNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private List<CartItem> items;

    private Double totalAmount;


    public Order(String description, LocalDateTime date, Double totalAmount) {
        this.description = description;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public Order(String description, LocalDateTime date, List<CartItem> items, Double totalAmount) {
        this.description = description;
        this.date = date;
        this.items = items;
        this.totalAmount = totalAmount;
    }
}
