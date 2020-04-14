package com.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "PERMISSIONS")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Permission extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

}
