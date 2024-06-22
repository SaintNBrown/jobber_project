package com.jobber.jobs.models;

import com.jobber.jobs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Inside loadByUsername method...");

        UserInfo userInfo = userRepository.findByUsername(username);
        if(userInfo == null){
            log.error("Username not found! {}", username);
            throw new UsernameNotFoundException("Could not find user..");
        }

        log.info("User authenticated successfully");
        return new CustomUserDetails(userInfo);
    }
}
