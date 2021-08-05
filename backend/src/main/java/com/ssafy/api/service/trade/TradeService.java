package com.ssafy.api.service.trade;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TradeService {
    Page<TradeListGetRes> getBuyerList(PageReq pageReq, String buyer);
    Page<TradeListGetRes> getSellerList(PageReq pageReq, String seller);

    TradeHistory createTradeHistory(TradeRegistPatchReq buyUpdatePostReq);
    void deleteTradeInfo(Long buyProductId);
    Boolean checkTradeHistory(Long productId);
}
