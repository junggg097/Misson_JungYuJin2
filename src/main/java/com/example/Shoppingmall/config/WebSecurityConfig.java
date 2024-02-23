package com.example.Shoppingmall.config;

import com.example.Shoppingmall.jwt.JwtTokenFilter;
import com.example.Shoppingmall.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

// @Bean 을 비롯해 여러 설정을 하기 위한 Bean 객체
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsManager manager;

    // 메서드의 결과를 Bean 객체로 관리해주는 어노테이션
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                // csrf 보안 해제
                .csrf(AbstractHttpConfigurer::disable)
                // url에 따른 요청 인가
                .authorizeHttpRequests(auth -> auth
                        // 어떤 경로에 대한 설정인지
                        .requestMatchers(
                                // /no-auth로 오는 요청은 모두 허가
                                "/no-auth",
                                "/token/issue",
                                "/token/validate"
                        )
                        // 이 경로에 도달할 수 있는 사람에 대한 설정(모두)
                        .permitAll()
                        .requestMatchers("/users/my-profile")
                        .authenticated()
                        .requestMatchers(
                                "/users/register"
                        )
                        .anonymous()
                )
                // JWT를 사용하기 때문에 보안 관련 세션 해제
                .sessionManagement(
                        session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // JWT 필터를 권한 필터 앞에 삽입
                .addFilterBefore(
                        new JwtTokenFilter(
                                jwtTokenUtils,
                                manager
                        ),
                        AuthorizationFilter.class
                );
        return http.build();
    }

}
