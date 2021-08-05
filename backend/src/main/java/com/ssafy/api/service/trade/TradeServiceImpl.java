package com.ssafy.api.service.trade;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.db.entity.TradeHistory;
import com.ssafy.db.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Product;
import com.ssafy.db.repository.trade.TradeRepository;
import com.ssafy.db.repository.trade.TradeRepositorySupport;

import javax.transaction.Transactional;
import java.util.List;

@Service("buyService")
public class TradeServiceImpl implements TradeService {
    @Autowired
    TradeRepository tradeRepository;
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
    public TradeHistory createTradeHistory(TradeRegistPatchReq buyUpdatePostReq) {
        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.setBuyer(buyUpdatePostReq.getBuyer());
        tradeHistory.setPrice(buyUpdatePostReq.getPrice());
        tradeHistory.setProductId(buyUpdatePostReq.getProductId());
        tradeHistory.setSeller(buyUpdatePostReq.getSeller());
        tradeHistory.setTradeDate(buyUpdatePostReq.getTrade_date());
        return tradeRepository.save(tradeHistory);
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
