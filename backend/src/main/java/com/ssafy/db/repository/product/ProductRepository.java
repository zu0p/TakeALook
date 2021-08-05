package com.ssafy.db.repository.product;

import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.db.entity.Product;
import org.kurento.client.internal.server.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long productId);
    Optional<Product> findProductByUserUserIdAndId(String userId, Long productId);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.user.userId = :uid")
    void deleteAllByUserId(@Param("uid") String uid);
}
