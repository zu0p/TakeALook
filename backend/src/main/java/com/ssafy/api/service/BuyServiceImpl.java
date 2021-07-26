package com.ssafy.api.service;

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
        System.out.println("service");
        List<Product> list = buyRepositorySupport.findAllByBuyer(buyer);
        return list;
    }
}
