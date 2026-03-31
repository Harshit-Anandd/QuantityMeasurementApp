package com.app.quantitymeasurement.security;

import com.app.quantitymeasurement.model.UserEntity;
import com.app.quantitymeasurement.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String password = user.passwordHash == null ? "" : user.passwordHash;
        return User.builder()
                .username(user.email)
                .password(password)
                .authorities("ROLE_" + user.role.name())
                .disabled(!user.enabled)
                .build();
    }
}
