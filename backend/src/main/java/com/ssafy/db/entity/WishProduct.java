package com.ssafy.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString
public class WishProduct extends BaseEntity {

    String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;
}