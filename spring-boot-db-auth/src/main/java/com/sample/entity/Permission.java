package com.sample.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "PERMISSIONS")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity {
    private String name;

    @ManyToMany
    private List<Role> roles;

}
