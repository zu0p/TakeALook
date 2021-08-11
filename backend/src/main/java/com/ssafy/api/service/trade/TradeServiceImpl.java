package com.ssafy.api.service.trade;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.trade.TradeSectionCreateReq;
import com.ssafy.api.request.trade.TradeSectionReturnReq;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.TradeHistory;
import com.ssafy.db.entity.TradeSection;
import com.ssafy.db.repository.product.ProductRepository;
import com.ssafy.db.repository.trade.TradeSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.db.repository.trade.TradeRepository;
import com.ssafy.db.repository.trade.TradeRepositorySupport;

@Service("buyService")
public class TradeServiceImpl implements TradeService {
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    TradeSectionRepository tradeSectionRepository;
    @Autowired
    TradeRepositorySupport tradeRepositorySupport;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<TradeListGetRes> getBuyerList(PageReq pageReq, String buyer) {
        Pageable pageable = PageRequest.of(pageReq.getPage(),pageReq.getSize());
        Page<TradeListGetRes> list = tradeRepositorySupport.findByBuyer(pageable, buyer);
        return list;
    }

    @Override
    public Page<TradeListGetRes> getSellerList(PageReq pageReq, String seller) {
        Pageable pageable = PageRequest.of(pageReq.getPage(),pageReq.getSize());
        Page<TradeListGetRes> list = tradeRepositorySupport.findBySeller(pageable, seller);
        return list;
    }

    @Override
    public TradeHistory createTradeHistory(TradeSectionReturnReq buyUpdatePostReq) {
        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.setProductId(buyUpdatePostReq.getProductId());
        tradeHistory.setSeller(buyUpdatePostReq.getUserId());
        return tradeRepository.save(tradeHistory);
    }

    @Override
    public TradeSection createTradeSection(TradeSectionCreateReq tradeSectionCreateReq, String room) {
        TradeSection tradeSection = new TradeSection();
        tradeSection.setSeller(tradeSectionCreateReq.getSeller());
        tradeSection.setProductId(tradeSectionCreateReq.getProductId());
        tradeSection.setUrl(room);
        tradeSection.setIsActive(true);
        return tradeSectionRepository.save(tradeSection);
    }

    @Override
    public void deleteTradeInfo(Long productId) {
        TradeHistory tradeHistory = tradeRepository.findTradeHistoryByProductId(productId).get();
        tradeRepository.delete(tradeHistory);
    }

    @Override
    public Boolean checkTradeHistory(Long productId){
        if(tradeRepository.findTradeHistoryByProductId(productId).isPresent()) return true;
        else return false;
    }

}
