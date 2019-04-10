package com.itrach.cbinterceptor.demo.controller;

import com.itrach.cbinterceptor.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getUser() {
        System.err.println(userRepository.findById(Long.valueOf("1")).get());
        return new ResponseEntity<>(userRepository.findById(Long.valueOf("1")).get(), HttpStatus.OK);
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<?> getUserByMail(@PathVariable String mail) {
        System.err.println(userRepository.findByEmail(mail).get());
        return new ResponseEntity<>(userRepository.findByEmail(mail).get(), HttpStatus.OK);
    }
}
