package com.shop.service.api;

import com.shop.repository.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Iterable<Product> listAllProducts();

    Page<Product> findAllPageable(Pageable pageable);

    Product getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProduct(Long id);
}
