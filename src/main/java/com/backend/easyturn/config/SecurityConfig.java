package com.backend.easyturn.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(config-> config.disable())
                .authorizeHttpRequests((authorizeRequests) -> {
                   authorizeRequests
                           .requestMatchers("/auth/**").permitAll()
                           .requestMatchers("/speciality/speciality-names").permitAll()
                            .requestMatchers("/speciality/get-all-specialities").permitAll()
                            .requestMatchers("/speciality/get-speciality/{idSpeciality}").permitAll()
                            .requestMatchers("/professional/get-all-professionals").permitAll()
                           .requestMatchers("/healthinsurance/get-all").permitAll()
                            .requestMatchers("/professional/get-professional/{idProfessional}").permitAll()
                           .requestMatchers("/institution/").permitAll()
                            .requestMatchers(HttpMethod.GET,"/healthinsurance/{id}").permitAll()


                            .anyRequest().authenticated();
                })
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }) // NO VAMOS A MANEJAR UNA SESSION POR ESO STATELESS
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
