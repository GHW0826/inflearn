package shop.coding.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.coding.bank.config.jwt.JwtAuthenticationFilter;
import shop.coding.bank.config.jwt.JwtAuthorizationFilter;
import shop.coding.bank.domain.user.UserEnum;
import shop.coding.bank.dto.ResponseDto;
import shop.coding.bank.handler.ex.CustomForbiddenException;
import shop.coding.bank.util.CustomResponseUtil;

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean // Ioc 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨.
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("debug: BCryptPasswordEncoder bean register");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록.
    public class CustomSecurityFilterManger extends AbstractHttpConfigurer<CustomSecurityFilterManger, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }
    }

    // JWT 서버 세션 사용 안힘.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("debug: filterChain bean register");

        http.headers().frameOptions().disable(); // iframe 허용안함.
        http.csrf().disable(); // enable이면 post맨 작동 안함.
        http.cors().configurationSource(configurationSource());

        // jsessionID를 서버쪽에서 관리 안함.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // react, 앱에서 요청에 api만 반환
        http.formLogin().disable();
        // httpbasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행.
        http.httpBasic().disable();

        // 필터 적용
        http.apply(new CustomSecurityFilterManger());

        // 인증 실패
        // Exception 가로채기
        http.exceptionHandling().authenticationEntryPoint((request, response, authException)-> {
            String uri = request.getRequestURI();
            log.debug("디버그: " + uri);
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
        });

        // 권한 실패
        http.exceptionHandling().accessDeniedHandler((request, response, e) ->  {
            CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
        });
        
        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()
                // 최근 공식 문서는 ROLE_를 안붙여도 됨.
                .antMatchers("/api/admin/**").hasRole(""+UserEnum.ADMIN)
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("debug: configurationSource cors config register in SecurityFilterChain");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // get, post, put, delete
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
        configuration.setAllowCredentials(true); // 클라쪽에서 쿠키 요청 허용

        // 설정하지 않으면 브라우저에서 자바스크립트로 못땡김
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
