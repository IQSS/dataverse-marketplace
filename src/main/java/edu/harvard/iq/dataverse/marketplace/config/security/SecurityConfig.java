package edu.harvard.iq.dataverse.marketplace.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        return http
             .csrf(AbstractHttpConfigurer::disable)
             .authorizeHttpRequests((requests) -> requests
             .anyRequest().permitAll())
             .build();

        // return http
        //     .csrf(AbstractHttpConfigurer::disable)
        //     .authorizeHttpRequests((requests) -> requests
        //         .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
        //         .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
        //         .requestMatchers("/openapi/**").permitAll()
        //         .requestMatchers("/swagger-ui/**").permitAll()
        //         .requestMatchers("/").permitAll()
        //         .requestMatchers("/resources/**").permitAll()
        //         .requestMatchers("/api/**").hasAuthority("DVH_ADMIN")
        //         .anyRequest().authenticated())
        // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // //.addFilterAfter(tokenAuthFilter, BasicAuthenticationFilter.class)
        // .build();


        
        // return http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        //   .authorizeHttpRequests(request -> request.requestMatchers("/admin/**")
        //     .hasRole("ADMIN")
        //     .requestMatchers("/anonymous*")
        //     .anonymous()
        //     .requestMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico", "/rest")
        //     .permitAll()
        //     .anyRequest()
        //     .authenticated())
        //   .formLogin(form -> form.loginPage("/index.html")
        //     .loginProcessingUrl("/perform_login")
        //     .defaultSuccessUrl("/homepage.html", true)
        //     .failureUrl("/index.html?error=true"))
        //   .logout(logout -> logout.logoutUrl("/perform_logout")
        //     .deleteCookies("JSESSIONID"))
        //   .build();
    }

}
