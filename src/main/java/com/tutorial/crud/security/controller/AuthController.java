package com.tutorial.crud.security.controller;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.security.dto.JwtDto;
import com.tutorial.crud.security.dto.LoginUser;
import com.tutorial.crud.security.dto.NewUser;
import com.tutorial.crud.security.entity.Role;
import com.tutorial.crud.security.entity.User;
import com.tutorial.crud.security.enums.RoleName;
import com.tutorial.crud.security.jwt.JwtProvider;
import com.tutorial.crud.security.service.RoleService;
import com.tutorial.crud.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtProvider jwtProvider;

    /* Metodo para register/login */
    @PostMapping("/new")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos o email mal"), HttpStatus.BAD_REQUEST);
        if(userService.existsByUserName(newUser.getUserName()))
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(newUser.getEmail()))
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        User user = new User(newUser.getName(),newUser.getUserName(),newUser.getEmail(),
                passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
        if(newUser.getRoles().contains("admin"))
            roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity(new Mensaje("usuario guardado"),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> Login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUserName(),loginUser.getPassword()));
        //Autenticamos el usuario con el login
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt,userDetails.getUsername(),userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
