package com.ssafy.api.service.product;

import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.api.request.product.ProductUpdatePatchReq;
import com.ssafy.db.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductRegisterPostReq productRegisterPostReq);
    List<Product> getProductsByUID(String userId);
    Product getProductByPID(Long productIndexId);
    Product updateProduct(Product product, ProductUpdatePatchReq productUpdatePatchReq);
    void deleteProduct(Long productIndexId);
}
