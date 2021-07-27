package com.ssafy.api.service;

import com.ssafy.api.request.buy.BuyUpdateReq;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;

import java.util.List;

public interface BuyService {
    List<Product> getBuyList(String userId);
    TradeHistory createBuyHistory(BuyUpdateReq buyUpdatePostReq);
    void deleteBuyInfo(Long buyProductId);
    Product getBuyDetail(String productId);
}
