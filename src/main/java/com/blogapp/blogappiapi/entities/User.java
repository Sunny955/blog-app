package com.blogapp.blogappiapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="user_name", nullable = false,length = 100)
    private String name;
    @Column(name="user_email",nullable = false,length = 50)
    private String email;
    @Column(name="user_password",nullable = false,length = 20)
    private String password;
    @Column(nullable = true,length = 500)
    private String about;
}