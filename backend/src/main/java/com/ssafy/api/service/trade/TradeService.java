package com.ssafy.api.service.trade;

import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;

import java.util.List;

public interface TradeService {
    List<Product> getTradeList(String userId);
    TradeHistory createTradeHistory(TradeRegistPatchReq buyUpdatePostReq);
    void deleteTradeInfo(Long buyProductId);
}
