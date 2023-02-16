package com.example.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public String addRequestHeader(@RequestHeader(value="Accept") String accept) {
        log.debug("Accept:{}" + accept);
        return "user - addRequestHeader";
    }

    @GetMapping("/{id}")
    public String getPathVariable(@PathVariable("id") String id) {
        log.debug("UserController.getPathVariable id:{}", id);
        return "user - getPathVariable";
    }
}
