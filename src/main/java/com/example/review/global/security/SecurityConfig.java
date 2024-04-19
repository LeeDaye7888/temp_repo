package com.example.review.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/api/signup", "/api/signin", "/api/items/**", "/api/categories"};

    // 특정 http 요청에 대한 웹 기반 보안을 구성한다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults()); // 기본 설정 유지 // 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.
        http.authorizeHttpRequests((auth) ->
                auth.requestMatchers(allowedUrls).permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/seller/**").hasAnyAuthority("ADMIN", "SELLER")
                        .anyRequest().authenticated()); //위에 제외하고는 다 로그인되어야 한다. (카테고리, 회원가입, 로그인.... -> 로그인 안해도 가능)
        http.exceptionHandling((handling) ->
            handling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
            );

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // http 요청에 대한 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userDetailsService) // 사용자 정보를 가져올 서비스를 재정의, 재정의하는 서비스는 반드시 UserDetailsService를 상속받은 클래스여야 한다.
            .passwordEncoder(bCryptPasswordEncoder);  // 비밀번호를 암호화하기 위한 인코더를 설정

        return authenticationManagerBuilder.build();
    }

    // 비밀번호를 db에 암호화해서 저장하기 위해 인코더가 필요하다. 인코더를 설정해준다.
    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
