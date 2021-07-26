package com.ssafy.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
public class WishProduct extends BaseEntity {
    String productId;
    @ManyToOne(fetch = FetchType.LAZY)
    User user;
}