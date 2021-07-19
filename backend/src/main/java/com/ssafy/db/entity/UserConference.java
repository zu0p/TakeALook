package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user")
@Getter
@Setter
public class UserConference extends BaseEntity{
    @Column
    int conferenceId;

    @ManyToOne
    @JoinColumn(name="user_id")
    int userId;
}
