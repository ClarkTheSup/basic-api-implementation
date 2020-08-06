package com.thoughtworks.rslist.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class UserDto {
    @Id
    @GeneratedValue
    int id;
    @Column(name = "name")
    private String userName;
    private String gender;
    private int age;
    private String email;
    private String phone;
    private int voteNum = 10;
}
