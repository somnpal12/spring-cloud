package com.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;
 
@Entity(name = "ROLES")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Role extends BaseEntity {

    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
