package com.ssafy.db.repository.trade;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QTradeSection;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.TradeSection;
import com.ssafy.db.entity.User;
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
}
