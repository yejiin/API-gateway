package com.example.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @GetMapping("")
    public String addRequestHeader(@RequestHeader(value="Accept") String accept) {
        System.out.println("Accept = " + accept);
        return "user - addRequestHeader";
    }
}
