package com.jobber.jobs.services;

import com.jobber.jobs.dto.AuthSignUpDTO;
import com.jobber.jobs.dto.AuthSignUpResponse;
import com.jobber.jobs.models.UserInfo;
import com.jobber.jobs.models.UserRole;
import com.jobber.jobs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Slf4j
@Service
public class SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(UserInfo userInfo){

        UserInfo newUSer = UserInfo.builder()
                .fullName(userInfo.getFullName())
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .email(userInfo.getEmail())
                .build();
        if((userRepository.findByUsername(userInfo.getUsername()) == null)){
            newUSer.setUsername(userInfo.getUsername());
        }

        userRepository.save(newUSer);
    }
}
