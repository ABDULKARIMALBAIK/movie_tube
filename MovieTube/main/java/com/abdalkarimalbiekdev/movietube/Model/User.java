package com.abdalkarimalbiekdev.movietube.Model;

import java.util.Date;

import io.reactivex.annotations.Nullable;

public class User {

    @Nullable
    private int id;
    @Nullable
    private String email;
    @Nullable
    private String password;
    @Nullable
    private String name;
    @Nullable
    private String dateJoin;
    @Nullable
    private String token;


    public User() {
    }

    public User(int id, String email, String password, String name, String dateJoin, String token) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateJoin = dateJoin;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
