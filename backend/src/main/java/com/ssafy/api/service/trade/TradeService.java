package com.ssafy.api.service.trade;

import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface TradeService {
    List<Product> getBuyerList(String userId);
    List<Product> getSellerList(String userId);
    TradeHistory createTradeHistory(TradeRegistPatchReq buyUpdatePostReq);
    void deleteTradeInfo(Long buyProductId);
    Boolean checkTradeHistory(Long productId);
}
