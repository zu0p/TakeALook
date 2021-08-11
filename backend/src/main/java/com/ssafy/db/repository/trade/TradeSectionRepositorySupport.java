package com.ssafy.db.repository.trade;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.trade.TradeSectionEnterReq;
import com.ssafy.db.entity.QTradeSection;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.TradeSection;
import com.ssafy.db.entity.User;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TradeSectionRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QTradeSection qTradeSection = QTradeSection.tradeSection;

    public Optional<TradeSection> findTradeSectionBySeller(String seller) {
        TradeSection tradeSection = jpaQueryFactory.select(qTradeSection).from(qTradeSection)
                .where(qTradeSection.seller.eq(seller)).fetchOne();
        if(tradeSection == null) return Optional.empty();
        return Optional.ofNullable(tradeSection);
    }

    public Optional<TradeSection> findTradeSectionByProductId(Long productId){
        TradeSection tradeSection = jpaQueryFactory.select(qTradeSection).from(qTradeSection)
                .where(qTradeSection.productId.eq(productId)).fetchOne();
        if(tradeSection == null) return Optional.empty();
        return Optional.ofNullable(tradeSection);
    }

    public Optional<TradeSection> findTradeSectionByProductId(String url){
        TradeSection tradeSection = jpaQueryFactory.select(qTradeSection).from(qTradeSection)
                .where(qTradeSection.url.eq(url)).fetchOne();
        if(tradeSection == null) return Optional.empty();
        return Optional.ofNullable(tradeSection);
    }

}
