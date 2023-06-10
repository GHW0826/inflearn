package io.security.oauth2.springsecurityoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@SpringBootApplication
public class SpringSecurityOauth2Application {

    public static void main(String[] args) {

        SpringApplication.run(SpringSecurityOauth2Application.class, args);
    }

}
