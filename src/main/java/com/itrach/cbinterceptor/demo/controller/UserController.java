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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getUsers() {
//        System.err.println(userRepository.findById(Long.valueOf("1")).get());
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        System.err.println(userRepository.findById(id.longValue()));
        return new ResponseEntity<>(userRepository.findById(id.longValue()), HttpStatus.OK);
    }
}
