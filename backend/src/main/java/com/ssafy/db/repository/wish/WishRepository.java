package com.ssafy.db.repository.wish;

import com.ssafy.db.entity.WishProduct;
import org.kurento.client.internal.server.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<WishProduct, Long> {
    Optional<WishProduct> findWishProductByUserUserIdAndProductId(String userId, Long productId);
    Long countByProductId(Long productId);

    @Modifying
    @Query("DELETE FROM WishProduct w WHERE w.productId = :pid")
    void deleteAllByProductId(@Param("pid") Long pid);

    @Modifying
    @Query("DELETE FROM WishProduct w WHERE w.user.userId = :uid")
    void deleteAllByUserId(@Param("uid") String uid);
}
