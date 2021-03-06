package com.ssafy.db.repository.trade;

import com.ssafy.db.entity.TradeSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeSectionRepository extends JpaRepository<TradeSection, String> {
    Long countByProductId(Long productId);
    TradeSection findTradeSectionByProductId(Long productId);
    TradeSection findTradeSectionByUrl(String url);
}
