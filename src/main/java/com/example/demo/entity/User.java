package com.example.demo.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    /**
     * Default constructor.
     * Required by JPA.
     */
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

