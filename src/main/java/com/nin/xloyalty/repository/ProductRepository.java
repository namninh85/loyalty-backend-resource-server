package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.isActive=true AND lower(p.name) LIKE %"+":name"+"%")
    List<Product> findProductByName(@Param("name") String name);
}
