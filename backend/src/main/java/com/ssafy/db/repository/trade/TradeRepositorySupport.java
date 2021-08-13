package com.ssafy.db.repository.trade;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.api.response.trade.TradeCompleteRes;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.QProduct;
import com.ssafy.db.entity.QTradeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TradeRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QProduct qProduct = QProduct.product;
    QTradeHistory qTradeHistory = QTradeHistory.tradeHistory;

    public Page<TradeListGetRes> findByBuyer(Pageable pageable, String buyer) {
        QueryResults<TradeListGetRes> result = jpaQueryFactory
                .select(Projections.constructor(TradeListGetRes.class, qProduct.id, qProduct.productName, qTradeHistory.tradeDate,
                        qProduct.user.userId, qTradeHistory.buyer,
                        qTradeHistory.price, qProduct.categories, qProduct.imageUrl, qProduct.isSold))
                .from(qProduct)
                .join(qTradeHistory)
                .on(qProduct.id.eq(qTradeHistory.productId))
                .where(qTradeHistory.buyer.eq(buyer))
                .orderBy(qProduct.registTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(),pageable,result.getTotal());
    }

    public Page<TradeListGetRes> findBySeller(Pageable pageable, String seller){
        QueryResults<TradeListGetRes> result = jpaQueryFactory
                .select(Projections.constructor(TradeListGetRes.class, qProduct.id, qProduct.productName, qTradeHistory.tradeDate,
                        qProduct.user.userId, qTradeHistory.buyer,
                        qTradeHistory.price, qProduct.categories, qProduct.imageUrl, qProduct.isSold))
                .from(qProduct)
                .join(qTradeHistory)
                .on(qProduct.id.eq(qTradeHistory.productId))
                .where(qTradeHistory.seller.eq(seller))
                .orderBy(qProduct.registTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(),pageable,result.getTotal());
    }

    public List<TradeCompleteRes> findByUserIdBuyer(String userId) {
        List<TradeCompleteRes> result = jpaQueryFactory
                .select(Projections.constructor(TradeCompleteRes.class, qProduct.id, qProduct.productName, qTradeHistory.tradeDate,
                        qTradeHistory.seller, qTradeHistory.price, qTradeHistory.roomId))
                .from(qProduct)
                .join(qTradeHistory)
                .on(qProduct.id.eq(qTradeHistory.productId))
                .where(qTradeHistory.buyer.eq(userId))
                .fetch();

        return result;
    }

    public List<TradeCompleteRes> findByUserIdSeller(String userId) {
        List<TradeCompleteRes> result = jpaQueryFactory
                .select(Projections.constructor(TradeCompleteRes.class, qProduct.id, qProduct.productName, qTradeHistory.tradeDate,
                        qTradeHistory.buyer, qTradeHistory.price, qTradeHistory.roomId))
                .from(qProduct)
                .join(qTradeHistory)
                .on(qProduct.id.eq(qTradeHistory.productId))
                .where(qTradeHistory.seller.eq(userId))
                .fetch();

        return result;
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
