package com.ssafy.db.repository.product;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.product.ProductListGetRes;
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

    public List<ProductListGetRes> findAllProduct(){
        List<ProductListGetRes> productList = jpaQueryFactory
                .select(Projections.constructor(ProductListGetRes.class,qProduct.id, qProduct.user.userId,
                        qProduct.productName,qProduct.basePrice, qProduct.categories,qProduct.description,qProduct.state,
                        qProduct.imageUrl, qProduct.isSold,qProduct.registTime,qProduct.registTime))
                .from(qProduct)
                .fetch();
        return productList;
    }
}
