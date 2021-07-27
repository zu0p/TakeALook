package com.ssafy.api.service;

import com.ssafy.api.request.BuyUpdateReq;
import com.ssafy.db.entity.TradeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Product;
import com.ssafy.db.repository.BuyRepository;
import com.ssafy.db.repository.BuyRepositorySupport;

import java.util.List;

@Service("buyService")
public class BuyServiceImpl implements BuyService{
    @Autowired
    BuyRepository buyRepository;

    @Autowired
    BuyRepositorySupport buyRepositorySupport;

    @Override
    public List<Product> getBuyList(String buyer) {
        //System.out.println("service");
        List<Product> list = buyRepositorySupport.findAllByBuyer(buyer);
        return list;
    }

    @Override
    public TradeHistory createBuyHistory(BuyUpdateReq buyUpdatePostReq) {
        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.setBuyer(buyUpdatePostReq.getBuyer());
        tradeHistory.setPrice(buyUpdatePostReq.getPrice());
        tradeHistory.setProduct(buyUpdatePostReq.getProduct());
        tradeHistory.setSeller(buyUpdatePostReq.getSeller());
        tradeHistory.setTradeDate(buyUpdatePostReq.getTrade_date());
        return buyRepository.save(tradeHistory);
    }

    @Override
    public void deleteBuyInfo(Long buyProductId) {
        TradeHistory tradeHistory = buyRepository.findById(buyProductId).get();
        buyRepository.delete(tradeHistory);
    }
}
