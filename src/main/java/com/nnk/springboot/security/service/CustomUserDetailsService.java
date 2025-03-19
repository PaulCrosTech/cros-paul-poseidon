package com.nnk.springboot.security.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Custom user details service
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor
     * @param userRepository userRepository
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Load user by username from database
     * @param userName username
     * @return UserDetails
     * @throws UsernameNotFoundException UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("====> Authenticate user with username '{}' <====", userName);

        Optional<User> user = userRepository.findByUsername(userName);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + userName + " is not found");
        }
        return new org.springframework.security.core.userdetails.User (
                user.get().getUsername(),
                user.get().getPassword(),
                getGrantedAuthorities(user.get().getRole())
        );
    }


    /**
     * Get granted authorities
     * @param role role
     * @return List<GrantedAuthority>
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}
