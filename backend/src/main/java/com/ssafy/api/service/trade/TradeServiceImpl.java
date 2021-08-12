package com.ssafy.api.service.trade;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.api.request.trade.TradeSectionCreateReq;
import com.ssafy.api.request.trade.TradeSectionEnterReq;
import com.ssafy.api.response.trade.TradeCompleteRes;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.TradeHistory;
import com.ssafy.db.entity.TradeSection;
import com.ssafy.db.repository.product.ProductRepository;
import com.ssafy.db.repository.trade.TradeSectionRepository;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.db.repository.trade.TradeRepository;
import com.ssafy.db.repository.trade.TradeRepositorySupport;

import java.util.*;

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
    public List<TradeCompleteRes> getChatList(String userId) {
        List<TradeCompleteRes> listByByer = tradeRepositorySupport.findByUserIdBuyer(userId);
        List<TradeCompleteRes> listBySeller = tradeRepositorySupport.findByUserIdSeller(userId);
        List<TradeCompleteRes> chatList = ListUtils.union(listByByer, listBySeller);

        Collections.sort(chatList, new Comparator<TradeCompleteRes>(){
            @Override
            public int compare(TradeCompleteRes t1, TradeCompleteRes t2){
                return -(t1.getTradeDate().compareTo(t2.getTradeDate()));
            }
        });

        return chatList;
    }


    @Override
    public TradeHistory createTradeHistory(TradeRegistPatchReq buyUpdatePostReq) {
        String roomId = UUID.randomUUID().toString();

        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.setBuyer(buyUpdatePostReq.getBuyer());
        tradeHistory.setPrice(buyUpdatePostReq.getPrice());
        tradeHistory.setProductId(buyUpdatePostReq.getProductId());
        tradeHistory.setSeller(buyUpdatePostReq.getSeller());
        tradeHistory.setTradeDate(buyUpdatePostReq.getTradeDate());
        tradeHistory.setRoomId(roomId);
        return tradeRepository.save(tradeHistory);
    }

    @Override
    public TradeSection createTradeSection(TradeSectionCreateReq tradeSectionCreateReq, String room) {
        TradeSection tradeSection = new TradeSection();
        tradeSection.setSeller(tradeSectionCreateReq.getSeller());
        tradeSection.setProductId(tradeSectionCreateReq.getProductId());
        tradeSection.setUrl(room);
        tradeSection.setIsActive(true);
        tradeSection.setIsStarted(false);
        tradeSection.setPriceGap(tradeSectionCreateReq.getPriceGap());
        return tradeSectionRepository.save(tradeSection);
    }

    @Override
    public TradeSection updateTradeSection(TradeSection oldTradeSection, Integer maxPrice) {
        TradeSection tradeSection = new TradeSection();
        tradeSection.setSeller(oldTradeSection.getSeller());
        tradeSection.setProductId(oldTradeSection.getProductId());
        tradeSection.setUrl(oldTradeSection.getUrl());
        tradeSection.setIsActive(oldTradeSection.getIsActive());
        tradeSection.setIsStarted(oldTradeSection.getIsStarted());
        tradeSection.setPriceGap(oldTradeSection.getPriceGap());
        tradeSection.setMaxPrice(maxPrice);
        return tradeSectionRepository.save(tradeSection);
    }

    @Override
    public TradeSection findTradeSection(TradeSectionEnterReq tradeSectionEnterReq) {
        TradeSection tradeSection = tradeSectionRepository.findTradeSectionByProductId(tradeSectionEnterReq.getProductId());
        return tradeSection;
    }

    @Override
    public TradeSection findTradeSectionByRoomUrl(String url) {
        TradeSection tradeSection = tradeSectionRepository.findTradeSectionByUrl(url);
        tradeSection.setIsStarted(Boolean.TRUE);
        tradeSectionRepository.save(tradeSection);
        return tradeSection;
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
