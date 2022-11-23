package com.example.springsecurity2.config;

import com.example.springsecurity2.model.SiteUser;
import com.example.springsecurity2.repository.SiteUserRepository;
import com.example.springsecurity2.util.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final SiteUserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        var user = new SiteUser();
//        user.setUsername("admin");
//        user.setPassword(passwordEncoder.encode("password"));
//        user.setEmail("admin@example.com");
//        user.setGender(0);
//        user.setAdmin(true);
//        user.setAuthority(Authority.ADMIN);
//
//        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
//            userRepository.save(user);
//        }
    }
}
