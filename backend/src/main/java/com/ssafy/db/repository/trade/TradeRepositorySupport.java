package com.ssafy.db.repository.trade;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.trade.TradeListGetRes;
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

    public List<TradeListGetRes> findByBuyer(String buyer) {
        List<TradeListGetRes> tradeList = jpaQueryFactory
                .select(Projections.constructor(TradeListGetRes.class, qProduct.id, qProduct.productName, qTradeHistory.tradeDate,
                        qProduct.user.userId, qTradeHistory.buyer,
                        qTradeHistory.price, qProduct.categories, qProduct.imageUrl, qProduct.isSold))
                .from(qProduct)
                .join(qTradeHistory)
                .on(qProduct.id.eq(qTradeHistory.productId))
                .where(qTradeHistory.buyer.eq(buyer))
                .fetch();
        return tradeList;
    }

    public List<TradeListGetRes> findBySeller(String seller){
        List<TradeListGetRes> tradeList = jpaQueryFactory
                .select(Projections.constructor(TradeListGetRes.class, qProduct.id, qProduct.productName, qTradeHistory.tradeDate,
                        qProduct.user.userId, qTradeHistory.buyer,
                        qTradeHistory.price, qProduct.categories, qProduct.imageUrl, qProduct.isSold))
                .from(qProduct)
                .join(qTradeHistory)
                .on(qProduct.id.eq(qTradeHistory.productId))
                .where(qTradeHistory.seller.eq(seller))
                .fetch();
        return tradeList;
    }

    public List<Product> findAllByBuyerSub(String buyer){
        List<Product> list = jpaQueryFactory
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
