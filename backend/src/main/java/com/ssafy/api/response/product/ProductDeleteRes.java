package com.ssafy.api.response.product;

import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProductDeleteResponse")
public class ProductDeleteRes {
    @ApiModelProperty(name="상품 판매자")
    String userId;
    @ApiModelProperty(name="상품명")
    Long productId;

    public static ProductDeleteRes of(User user, Long productId) {
        ProductDeleteRes res = new ProductDeleteRes();
        res.setUserId(user.getUserId());
        res.setProductId(productId);
        return res;
    }
}
