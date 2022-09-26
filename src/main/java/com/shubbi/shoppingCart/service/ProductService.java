package com.shubbi.shoppingCart.service;

import com.shubbi.shoppingCart.entity.Product;
import com.shubbi.shoppingCart.exception.ProductNotFoundException;
import com.shubbi.shoppingCart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long productId){
        return productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id "+ productId+ " not found!")
        );
    }

    public void saveProducts(List<Product> productList){
        productRepository.saveAll(productList);
    }

    public Product updateProduct(Long productId, Product newProduct){
        return productRepository.findById(productId)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setAvailableQuantity(newProduct.getAvailableQuantity());
                    return productRepository.save(product);
                })
                .orElseGet(
                        () -> {
                            newProduct.setId(productId);
                            return productRepository.save(newProduct);
                        }
                );
    }

    public void deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id "+ productId+ " not found!")
        );

        productRepository.delete(product);
    }

    public Product getProductData(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + productId + " not found!")
        );

        if (product.getAvailableQuantity() < quantity)
            throw new RuntimeException("Quantity exceeded for item: " + productId);

        product.setAvailableQuantity(product.getAvailableQuantity() - quantity);

        return product;
    }
}
