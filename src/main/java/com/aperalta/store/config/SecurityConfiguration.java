package com.aperalta.store.config;

import com.aperalta.store.security.AuthoritiesConstants;
import com.aperalta.store.security.jwt.JWTConfigurer;
import com.aperalta.store.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final TokenProvider tokenProvider;
    private final ApplicationProperties applicationProperties;
    private final CorsFilter corsFilter;

    public SecurityConfiguration(
            TokenProvider tokenProvider, ApplicationProperties applicationProperties, CorsFilter corsFilter
    ) {
        this.tokenProvider = tokenProvider;
        this.applicationProperties = applicationProperties;
        this.corsFilter = corsFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                        })
                )
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives(applicationProperties.getSecurity().getContentSecurityPolicy())
                        )
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                        .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).maxAgeInSeconds(31536000))
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/invalid-session")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/api/authenticate").permitAll()
                                .requestMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                                .requestMatchers("/public/**").permitAll()
                                .anyRequest().authenticated()
                ).apply(securityConfigurerAdapter());

        return http.build();
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
