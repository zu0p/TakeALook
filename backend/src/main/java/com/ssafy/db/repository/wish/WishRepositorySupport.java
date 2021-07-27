package com.ssafy.db.repository.wish;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.QProduct;
import com.ssafy.db.entity.QWishProduct;
import com.ssafy.db.entity.WishProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QWishProduct qWishProduct = QWishProduct.wishProduct;
    QProduct qProduct = QProduct.product;

    public List<WishListGetRes> findByUserId (String userId) {
        List<WishListGetRes> wlist = jpaQueryFactory
                .select(Projections.constructor(WishListGetRes.class,qProduct.id, qProduct.user.userId,
                        qProduct.productName,qProduct.basePrice, qProduct.categories, qProduct.description,
                        qProduct.state, qProduct.imageUrl, qProduct.isSold))
                .from(qWishProduct)
                .join(qProduct)
                .on(qWishProduct.productId.eq(qProduct.id)).where(qWishProduct.user.userId.eq(userId)).fetch();
        return wlist;
    }
}