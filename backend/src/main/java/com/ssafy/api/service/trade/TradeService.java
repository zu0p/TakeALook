package com.ssafy.api.service.trade;

import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface TradeService {
    List<TradeListGetRes> getBuyerList(String userId);
    List<TradeListGetRes> getSellerList(String userId);
    TradeHistory createTradeHistory(TradeRegistPatchReq buyUpdatePostReq);
    void deleteTradeInfo(Long buyProductId);
    Boolean checkTradeHistory(Long productId);
}
