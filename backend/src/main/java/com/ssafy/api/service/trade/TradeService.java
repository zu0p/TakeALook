package com.ssafy.api.service.trade;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.api.request.trade.TradeSectionCreateReq;
import com.ssafy.api.request.trade.TradeSectionEnterReq;
import com.ssafy.api.response.trade.TradeCompleteRes;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.TradeHistory;
import com.ssafy.db.entity.TradeSection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TradeService {
    Page<TradeListGetRes> getBuyerList(PageReq pageReq, String buyer);
    Page<TradeListGetRes> getSellerList(PageReq pageReq, String seller);

    List<TradeCompleteRes> getChatList(String userId);

    TradeHistory createTradeHistory(TradeRegistPatchReq buyUpdatePostReq);
    TradeSection createTradeSection(TradeSectionCreateReq tradeSectionCreateReq, String room);
    TradeSection findTradeSection(TradeSectionEnterReq tradeSectionEnterReq);
    void findTradeSectionByRoomUrl(String url);

    void deleteTradeInfo(Long buyProductId);
    Boolean checkTradeHistory(Long productId);
}
