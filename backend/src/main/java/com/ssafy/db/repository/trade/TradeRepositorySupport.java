package com.ssafy.db.repository.trade;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.QProduct;
import com.ssafy.db.entity.QTradeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TradeRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QProduct qProduct = QProduct.product;
    QTradeHistory qTradeHistory = QTradeHistory.tradeHistory;

    public List<Product> findAllByBuyer(String buyer){
        System.out.println("repository");
        List<Product> list = jpaQueryFactory
                //.select(qProduct.id, qProduct.productName, qTradeHistory.price, qProduct.category, qProduct.description, qProduct.state, qProduct.user.userId, qTradeHistory.buyer)
                .select(qProduct)
                .from(qProduct)
                .where(qProduct.id.in(
                        JPAExpressions
                        .select(qTradeHistory.productId)
                        .from(qTradeHistory)
                        .where(qTradeHistory.buyer.eq(buyer)))
                )
                .fetch();
        return list;
    }

}
