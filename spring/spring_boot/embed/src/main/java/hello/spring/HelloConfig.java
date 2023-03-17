package hello.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration //주석 처리
public class HelloConfig {
    @Bean
    public HelloController helloController() {
        return new HelloController();
    }
}