package com.example.springsecurity2.config;

import com.example.springsecurity2.repository.SiteUserRepository;
import com.example.springsecurity2.util.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final SiteUserRepository repository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                        // 「/register」「/login」は、ADMINユーザだけアクセス可能にする
                        .mvcMatchers("/register", "/login").permitAll()
                        // 「/adminの配下」は、ADMINユーザだけアクセス可能にする
                        .mvcMatchers("/admin/**").hasAnyAuthority(Authority.ADMIN.name())
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll())
                .rememberMe().key("Unique and Secret");
        return http.build();
    }
}
