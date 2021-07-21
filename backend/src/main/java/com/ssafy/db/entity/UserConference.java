package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * user_conference 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
public class UserConference extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONFERENCE_ID")
    Conference conference;

    // ManyToOne 어노테이션은 즉시 로딩(EAGER)이 기본값 -> 연결된 엔티티 정보까지 한번에 가져오려고 한다.
    // 따라서 성능에 문제가 될 수 있으므로 FetchType.LAZY 를 지정해 주는 것이 좋다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    User user;
}
