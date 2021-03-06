package com.ssafy.db.repository.trade;

import com.ssafy.db.entity.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<TradeHistory, Long> {
    Optional<TradeHistory> findTradeHistoryByProductId(Long productId);
}
