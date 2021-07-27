package com.ssafy.api.service;

import com.ssafy.api.request.buy.BuyUpdateReq;
import com.ssafy.db.entity.TradeHistory;
import com.ssafy.db.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Product;
import com.ssafy.db.repository.buy.BuyRepository;
import com.ssafy.db.repository.buy.BuyRepositorySupport;

import java.util.List;

@Service("buyService")
public class BuyServiceImpl implements BuyService{
    @Autowired
    BuyRepository buyRepository;

    @Autowired
    BuyRepositorySupport buyRepositorySupport;

    @Autowired
    ProductRepository productRepository;

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

    @Override
    public Product getBuyDetail(String productId) {
        Product product = productRepository.findById(Long.parseLong(productId)).get();
        return product;
    }
}
