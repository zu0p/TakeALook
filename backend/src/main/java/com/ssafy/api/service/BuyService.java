package com.ssafy.api.service;

import com.querydsl.core.Tuple;
import com.ssafy.db.entity.Product;

import java.util.List;

public interface BuyService {
    List<Product> getBuyList(String userId);
}
