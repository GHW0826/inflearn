package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
public class CsrfConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AccessDecisionManager y;
        AffirmativeBased q;
        ConsensusBased r;
        UnanimousBased w;

        http.authorizeHttpRequests()
                .anyRequest().authenticated();
        http.formLogin();
    }
}
