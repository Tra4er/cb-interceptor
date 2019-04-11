package com.itrach.cbinterceptor.demo.controller;

import com.itrach.cbinterceptor.annotation.CbInterceptor;
import com.itrach.cbinterceptor.demo.model.User;
import com.itrach.cbinterceptor.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    @CbInterceptor
    public Object getUsers(HttpServletRequest request) {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @CbInterceptor
    public Object putUser(@PathVariable Integer id, @RequestBody User user, HttpServletRequest request) {
        Optional<User> userO = userRepository.findById(id.longValue());
        User userBD = userO.get();
        userBD.setName(user.getName());
        userBD.setSecondName(user.getSecondName());
        userBD.setSurname(user.getSurname());

        return new ResponseEntity<>(userRepository.save(userBD), HttpStatus.OK);
    }

    @PutMapping("/email/{id}")
    @CbInterceptor
    public Object putEmail(@PathVariable Integer id, @RequestBody String email, HttpServletRequest request) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>("Email is taken.", HttpStatus.BAD_REQUEST);
        } else {
            Optional<User> userO = userRepository.findById(id.longValue());
            User user1 = userO.get();
            user1.setEmail(email);
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @CbInterceptor
    public Object getUserById(@PathVariable Integer id, HttpServletRequest request) {
        return new ResponseEntity<>(userRepository.findById(id.longValue()), HttpStatus.OK);
    }

    @GetMapping("/docs")
    @CbInterceptor
    public Object sendDocs(HttpServletRequest request) throws Throwable {
        Thread.sleep(10000);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
