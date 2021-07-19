package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 유저 모델 정의.
 */
@Entity
@Table(name="conference")
@Getter
@Setter
public class ConferenceCategory extends BaseEntity{
    @OneToMany(mappedBy = "conference")
    String name;
}
