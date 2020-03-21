package com.nin.xloyalty.service;

import com.nin.xloyalty.model.Product;
import com.nin.xloyalty.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }
    public List<Product> findProductByName (String name){
        return productRepository.findProductByName(name.toLowerCase());
    }
}
