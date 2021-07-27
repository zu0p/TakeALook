package com.ssafy.api.response.product;

import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class ProductDeleteRes {
    @ApiModelProperty(name="상품 판매자")
    String userId;
    @ApiModelProperty(name="상품명")
    String productName;

    public static ProductDeleteRes of(User user, Product product) {
        ProductDeleteRes res = new ProductDeleteRes();
        res.setUserId(user.getUserId());
        res.setProductName(product.getProductName());
        return res;
    }
}
