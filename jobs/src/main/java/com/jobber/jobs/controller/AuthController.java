package com.jobber.jobs.controller;

import com.jobber.jobs.dto.AuthRequestDTO;
import com.jobber.jobs.dto.AuthSignUpDTO;
import com.jobber.jobs.dto.JwtResponseDTO;
import com.jobber.jobs.models.UserDetailsServiceImpl;
import com.jobber.jobs.models.UserInfo;
import com.jobber.jobs.models.UserRole;
import com.jobber.jobs.services.JwtService;
import com.jobber.jobs.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/hi")
    public void hi(){
        System.out.println("Hi!");
    }

    @PostMapping("/signUp")
    public ResponseEntity<AuthSignUpDTO> signUp(@RequestBody AuthSignUpDTO authSignUpDTO){
        Set<UserRole> roles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setName("ROLE_USER");
        roles.add(userRole);

        UserInfo signedUpUser = UserInfo.builder()
                .fullName(authSignUpDTO.getFullName())
                .username(authSignUpDTO.getUsername())
                .email(authSignUpDTO.getEmail())
                .password(authSignUpDTO.getPassword())
                .roles(roles)
                .build();

        signUpService.addUser(signedUpUser);

        return ResponseEntity.ok(authSignUpDTO);
    }

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("Invalid request!");
        }
    }


}
