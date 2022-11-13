package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.enums.UserRoleEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private UserRoleEnum name;

    public Role() {}

    public UserRoleEnum getName() {
        return name;
    }

    public Role setName(UserRoleEnum name) {
        this.name = name;
        return this;
    }
}
