package com.ssafy.api.service;

import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.db.entity.Product;

public interface ProductService {
    Product createUser(ProductRegisterPostReq productRegisterPostReq);
}
