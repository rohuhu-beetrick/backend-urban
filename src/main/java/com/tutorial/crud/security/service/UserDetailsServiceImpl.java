package com.tutorial.crud.security.service;

import com.tutorial.crud.security.entity.User;
import com.tutorial.crud.security.entity.UserMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    /*Cargamos el usuario obteniendolo por userservice para hacer build con UserMain y obtener sus authorities con springBoot*/
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //para igualar user con optional, lo convertimos con get
        User user = userService.getByUserName(userName).get();
        return UserMain.build(user);
    }
}
