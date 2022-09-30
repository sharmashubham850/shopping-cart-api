package com.shubbi.shoppingCart.repository;

import com.shubbi.shoppingCart.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    // Used for Join Fetch query
    // It instructs query to fetch customer by using join clause and thus avoids n+1 query issue
    @EntityGraph(attributePaths = {"customer"})
    List<Order> findAll();
}
