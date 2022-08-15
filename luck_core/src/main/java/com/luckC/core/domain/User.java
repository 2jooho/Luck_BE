package com.luckC.core.domain;

import java.time.LocalDateTime;

public class User {

    private long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime birthday;
    private LocalDateTime createdAt;

    public User() {
    }
}
