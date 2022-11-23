package com.example.springsecurity2.controller;

import com.example.springsecurity2.model.SiteUser;
import com.example.springsecurity2.repository.SiteUserRepository;
import com.example.springsecurity2.util.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class SecurityController {

    private final SiteUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Authentication -> 認証済みのユーザ情報を取得
    @GetMapping("/")
    public String showList(Authentication loginUser, Model model) {
        model.addAttribute("username", loginUser.getName());
        model.addAttribute("authority", loginUser.getAuthorities());
        return "user";
    }

    @GetMapping("/admin/list")
    public String showAdminList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "list";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") SiteUser user) {
        return "register";
    }

    @PostMapping("/register")
    public String process(@Validated @ModelAttribute("user") SiteUser user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.isAdmin()) {
            user.setAuthority(Authority.ADMIN);
        } else {
            user.setAuthority(Authority.USER);
        }
        userRepository.save(user);
        return "redirect:/login?register";
    }
}
