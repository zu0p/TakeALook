package com.ssafy.api.response.buy;

import com.ssafy.db.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("BuyResponse")
public class BuyRes {
    @ApiModelProperty(name="Buy List")
    Timestamp registTime; //상품 등록 시간
    Timestamp reserveTime; // 거래 방 예약 시간
    Timestamp restrictTime; // 거래 방 제한 시간
    String productName;
    Integer basePrice;
    String category;
    String description;
    String state;
    String imageUrl;
    Boolean idSold;
    List<Product> list;

    public static BuyRes of(List<Product> products) {
        BuyRes res = new BuyRes();
        res.list = new ArrayList<>();
        for(Product p: products) {
            Product tmp = new Product();
            tmp.setRegistTime(p.getRegistTime());
            tmp.setReserveTime(p.getReserveTime());
            tmp.setProductName(p.getProductName());
            res.list.add(tmp);
        }
        return res;
    }
}
