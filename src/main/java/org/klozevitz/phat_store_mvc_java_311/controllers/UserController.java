package org.klozevitz.phat_store_mvc_java_311.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import org.klozevitz.phat_store_mvc_java_311.repositories.ApplicationUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    @PostMapping("/registration")
    public String registration(@RequestParam String email,@RequestParam String password){
        ApplicationUser applicationUser = new ApplicationUser(email,passwordEncoder.encode(password));
        applicationUserRepository.save(applicationUser);
        return "redirect:/";
    }

//    @PostMapping("localhost:9000/register/{userRef}")
//    public String emailVerification(@PathVariable String userRef) {
//
//    }

}
