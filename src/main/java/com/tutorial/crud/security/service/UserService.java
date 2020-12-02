package com.tutorial.crud.security.service;

import com.tutorial.crud.security.entity.User;
import com.tutorial.crud.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional /*Mantiene la coherencia en la db si hay varios accesos,
                hace rollback en caso de fallo*/

public class UserService {
    @Autowired //para importar interfaces e instanciarlas
    UserRepository userRepository;

    public Optional<User> getByUserName(String userName){
        return userRepository.findByUserName((userName));
    }

    public boolean existsByUserName(String userName){
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void save(User user){
        userRepository.save(user); //guarda en la db el user
    }
}
