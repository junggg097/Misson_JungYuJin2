package com.example.Shoppingmall.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Setter
    private String nickname;
    @Setter
    private String name;
    @Setter
    private int age;
    @Setter
    private String email;
    @Setter
    private String phone;

    // 권한 데이터
    // ROLE_INERT,ROLE_USER,ROLE_BUSINESS,ROLE_ADMIN
    @Setter
    private String authorities;

}
