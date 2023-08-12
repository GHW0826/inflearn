package com.example.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

    Environment env;

    @Autowired
    public FirstServiceController(Environment env) {
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the First Service.";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        System.out.println(header);
        return "Hello World in First Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        System.out.println("Server port=" + request.getServerPort());
        return String.format("Hi, there, This is a message from First Service on Port %s",
                env.getProperty("local.server.port"));
    }
}
