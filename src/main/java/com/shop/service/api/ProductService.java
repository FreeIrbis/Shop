package com.shop.service.api;

import com.shop.repository.entity.Product;

public interface ProductService {
    Iterable<Product> listAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProduct(Long id);
}
