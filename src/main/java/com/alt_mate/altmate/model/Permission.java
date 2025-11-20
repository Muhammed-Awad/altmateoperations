package com.example.altmate_operations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PermissionCategory category;

    @ManyToMany(mappedBy = "permissions")
    private Set<User> users = new HashSet<>();

}
