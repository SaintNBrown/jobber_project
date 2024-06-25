package com.jobber.jobs.controller;

import com.jobber.jobs.models.UserInfo;
import com.jobber.jobs.repository.UserRepository;
import com.jobber.jobs.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/getPassword")
    public ResponseEntity<String> getPassword(@RequestHeader(name = "Authorization") String token){
        //extract username from the provided token
        String username = jwtService.extractUsername(token);

        //find user with the username
        UserInfo user = userRepository.findByUsername(username);

        // checks token validity
        if(!jwtService.validateToken(token, (UserDetails) user)){
            return ResponseEntity.badRequest().body("Invalid token");
        }

        // checks the validity of the extracted user password
        if(!passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(user.getPassword()))){
            return ResponseEntity.badRequest().body("Cannot get password!");
        }
            return ResponseEntity.ok(user.getPassword());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        Optional<UserInfo> tempUser = userRepository.findById(id);
        tempUser.ifPresent(userInfo -> userRepository.delete(userInfo));
    }
}
