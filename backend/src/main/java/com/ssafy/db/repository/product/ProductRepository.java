package com.ssafy.db.repository.product;

import com.ssafy.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long productId);
    Optional<Product> findProductByUserUserIdAndId(String userId, Long productId);
}
