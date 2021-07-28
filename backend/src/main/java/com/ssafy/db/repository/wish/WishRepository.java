package com.ssafy.db.repository.wish;

import com.ssafy.db.entity.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<WishProduct, Long> {
    Optional<WishProduct> findWishProductByUserUserIdAndProductId(String userId, Long productId);
}
