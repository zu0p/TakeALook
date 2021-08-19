package com.ssafy.api.service.trade;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.api.request.trade.TradeSectionCreateReq;
import com.ssafy.api.request.trade.TradeSectionEnterReq;
import com.ssafy.api.request.trade.TradeSectionUpdatePriceReq;
import com.ssafy.api.response.trade.TradeCompleteRes;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.TradeHistory;
import com.ssafy.db.entity.TradeSection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TradeService {
    Page<TradeListGetRes> getBuyerList(PageReq pageReq, String buyer);
    Page<TradeListGetRes> getSellerList(PageReq pageReq, String seller);

    List<TradeCompleteRes> getChatList(String userId);

    TradeHistory createTradeHistory(TradeRegistPatchReq buyUpdatePostReq);
    TradeSection createTradeSection(TradeSectionCreateReq tradeSectionCreateReq, String room);
    TradeSection updateTradeSection(TradeSection tradeSection, Integer maxPrice);
    TradeSection findTradeSection(TradeSectionEnterReq tradeSectionEnterReq);
    TradeSection findTradeSectionByRoomUrl(String url);
    TradeSection findTradeSectionAndStartByRoomUrl(String url);
    void deleteTradeInfo(Long buyProductId);

    void deleteTradeSection(Long productId);

    Boolean checkTradeHistory(Long productId);
}
