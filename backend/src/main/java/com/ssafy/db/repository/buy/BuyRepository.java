package com.ssafy.db.repository.buy;

import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<TradeHistory, Long> {
    List<Product> findAllByBuyer(String buyer);
}
