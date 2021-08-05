package com.ssafy.db.repository.wish;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.db.entity.QProduct;
import com.ssafy.db.entity.QWishProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public class WishRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QWishProduct qWishProduct = QWishProduct.wishProduct;
    QProduct qProduct = QProduct.product;

    public Page<WishListGetRes> findByUserId(Pageable pageable, String userId) {
        QueryResults<WishListGetRes> result = jpaQueryFactory
                .select(Projections.constructor(WishListGetRes.class, qProduct.id, qProduct.user.userId,
                        qProduct.productName, qProduct.basePrice, qProduct.categories, qProduct.imageUrl, qProduct.isSold,
                        qProduct.reserveTime))
                .from(qWishProduct)
                .join(qProduct)
                .on(qWishProduct.productId.eq(qProduct.id))
                .where(qWishProduct.user.userId.eq(userId))
                .orderBy(qProduct.registTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return  new PageImpl<>(result.getResults(),pageable,result.getTotal());
    }
}