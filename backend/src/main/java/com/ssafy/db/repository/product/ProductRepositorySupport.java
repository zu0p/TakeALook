package com.ssafy.db.repository.product;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.QProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QProduct qProduct = QProduct.product;

    public Optional<Product> findByProductId(Long productId) {
        Product product = jpaQueryFactory.select(qProduct).from(qProduct)
                .where(qProduct.id.eq(productId)).fetchOne();
        if (product == null) return Optional.empty();
        return Optional.ofNullable(product);
    }

    public Optional<Product> findProductByIdAndUserId(String userId, Long productId){
        Product product = jpaQueryFactory.select(qProduct).from(qProduct)
                .where(qProduct.id.eq(productId), qProduct.id.eq(productId))
                .fetchOne();
        if (product == null) return Optional.empty();
        return Optional.ofNullable(product);
    }
}
