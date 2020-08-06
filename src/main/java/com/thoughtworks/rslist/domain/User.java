package com.thoughtworks.rslist.domain;

import javax.validation.constraints.*;
import java.io.Serializable;

public class User implements Serializable {
    @NotNull
    @Size(max = 8)
    private String userName;
    @NotNull
    private String gender;
    @NotNull
    @Max(value = 100)
    @Min(value = 18)
    private int age;
    @Email
    private String email;
    @Pattern(regexp = "1\\d{10}")
    private String phone;
    private int voteNum = 10;

    public User(){}

    public User(String userName, String gender, int age, String email, String phone, int voteNum) {
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.voteNum = voteNum;
    }

    @Override
    public String toString() {
        return "{userName="+userName+", " +
                "gender="+gender+", age="+age+", " +
                "email="+email+", phone="+phone+", voteNum="+voteNum+"}";
    }

    @Override
    public boolean equals(Object obj) {
        return this.userName.equals(((User) obj).getUserName());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }
}
