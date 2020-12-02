package com.tutorial.crud.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*Encargada de generar la seguridad*/
public class UserMain implements UserDetails {
    private String name;
    private String userName;
    private String email;
    private String password;
    /*Clase específica de la seguridad de Spring boot -GrantedAuthority-*/
    private Collection<? extends GrantedAuthority> authorities;

    public UserMain(String name, String userName, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /*Convierte un user que represdenta a la entidad de la db en un usermain para
    saber los privilegios de cada usuario
     */
    public static UserMain build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role
                .getRoleName().name())).collect(Collectors.toList());
        return new UserMain(user.getName(),user.getUserName(),user.getEmail(),user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
