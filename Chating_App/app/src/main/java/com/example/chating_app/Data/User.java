package com.example.chating_app.Data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @NonNull
    @PrimaryKey()
    public String userName;
    public String name;
    public String password;
    public byte[] image;

    public User(String userName, String name, String password, byte[] image) {
        this.image = image;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }
}
