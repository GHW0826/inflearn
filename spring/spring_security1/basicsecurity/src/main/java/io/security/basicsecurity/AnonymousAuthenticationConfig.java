package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AnonymousAuthenticationConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .anyRequest().authenticated();

        http.formLogin();

        // 10. session Management
//        http.sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false);
        http.sessionManagement()
                .sessionFixation().changeSessionId();
    }
}
