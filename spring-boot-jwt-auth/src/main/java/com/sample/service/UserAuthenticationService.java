package com.sample.service;

import com.sample.entity.Permission;
import com.sample.entity.Role;
import com.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserAuthenticationService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.sample.entity.User user = userRepository.findByName(username);


        List<Permission> permissionList = user.getRoles()
                .stream()
                .map(Role::getPermissions)
                .flatMap(Collection::stream).collect(Collectors.toList());

        List<SimpleGrantedAuthority> simpleGrantedAuthorities
                = permissionList.stream()
                .map(mappedSimpleGrantedAuthorityFunction)
                .distinct()
                .collect(Collectors.toList());

        if(user.getName().equals("user1")){
            simpleGrantedAuthorities.add( new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        /*return  AuthUser.builder().user(user).simpleGrantedAuthorities(simpleGrantedAuthorities).build();*/

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), simpleGrantedAuthorities);
    }

    Function<Permission, SimpleGrantedAuthority> mappedSimpleGrantedAuthorityFunction = permission -> new SimpleGrantedAuthority(permission.getName());

}
