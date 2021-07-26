package com.ssafy.db.repository.product;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.QProduct;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QProduct qProduct = QProduct.product;

    public Optional<Product> findByProductCName(String productName) {
        Product product = jpaQueryFactory.select(qProduct).from(qProduct)
                .where(qProduct.productName.eq(productName)).fetchOne();
        if(product == null) return Optional.empty();
        return Optional.ofNullable(product);
    }
}
