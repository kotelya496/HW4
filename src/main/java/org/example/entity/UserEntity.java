package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdat;

    public UserEntity() {    }

    public UserEntity(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        createdat = LocalDateTime.now();
    }
}
