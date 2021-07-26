package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.CollectionExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.QProduct;
import com.ssafy.db.entity.QTradeHistory;
import com.ssafy.db.entity.TradeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BuyRepositorySupport{
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QProduct qProduct = QProduct.product;
    QTradeHistory qTradeHistory = QTradeHistory.tradeHistory;

    public List<Product> findAllByBuyer(String buyer){
        System.out.println("repository");
        List<Product> list = jpaQueryFactory
                .select(qProduct)
                .from(qProduct)
                .where(qProduct.id.in(
                        JPAExpressions
                        .select(qTradeHistory.product)
                        .from(qTradeHistory)
                        .where(qTradeHistory.buyer.eq(buyer)))
                )
                .fetch();
//        List<Product> tmp = new ArrayList<>();
//        tmp.add(new Product());
//        return list==null?tmp:list;
        return list;
    }

}
