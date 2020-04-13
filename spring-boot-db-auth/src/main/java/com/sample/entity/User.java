package com.sample.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class User extends BaseEntity {
    private String name;
    private String password;

    @ManyToMany
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


}
