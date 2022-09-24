package com.shubbi.shoppingCart.controller;

import com.shubbi.shoppingCart.entity.Product;
import com.shubbi.shoppingCart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = productService.getAllProducts();

        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId){
        Product product = productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<String> addProducts(@RequestBody List<Product> productList){
        productService.saveProducts(productList);

        return ResponseEntity.status(HttpStatus.CREATED).body("Products added successfully");
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product){
        Product updatedProduct = productService.updateProduct(productId, product);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);

        return ResponseEntity.ok("Product delete successful");
    }
}
