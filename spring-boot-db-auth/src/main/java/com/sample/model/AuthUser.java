package com.sample.model;


import com.sample.entity.Permission;
import com.sample.entity.Role;
import com.sample.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Builder
@Data
public class AuthUser implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
     /*   List<Permission> permissionList = user.getRoles()
                .stream()
                .map(Role::getPermissions)
                .flatMap(Collection::stream).collect(Collectors.toList()) ;
        return  permissionList.stream().map(mappedSimpleGrantedAuthorityFunction).distinct().collect(Collectors.toList());*/

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("WRITE"));
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("READ"));

        return simpleGrantedAuthorities;
    }

    Function<Permission, SimpleGrantedAuthority> mappedSimpleGrantedAuthorityFunction = permission -> new SimpleGrantedAuthority(permission.getName());

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
