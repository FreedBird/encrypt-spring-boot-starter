package com.tdcq.platform.domain;

/**
 * Created by Enzo Cotter on 2022/6/18.
 */
public class User {

    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
