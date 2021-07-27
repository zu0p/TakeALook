package com.ssafy.api.service;

import com.querydsl.core.Tuple;
import com.ssafy.api.request.BuyUpdatePostReq;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;

import java.util.List;

public interface BuyService {
    List<Product> getBuyList(String userId);
    TradeHistory createBuyHistory(BuyUpdatePostReq buyUpdatePostReq);
}
