package com.eindopdracht.DJCorner.security;

import com.eindopdracht.DJCorner.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService udService, PasswordEncoder passwordEncoder) {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(udService);
        return new ProviderManager(auth);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        //users
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users").hasRole("ADMIN")
                        .requestMatchers("/users/register").hasAnyRole("STAFF", "ADMIN")

                        //submissions
                        .requestMatchers("/submissions/**").authenticated()

                        //feedback
                        .requestMatchers(HttpMethod.PATCH, "/submissions/*/feedback").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/feedback/status/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/feedback").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/feedback").hasAnyRole("ADMIN", "STAFF")

                        //playlists
                        .requestMatchers(HttpMethod.GET, "/playlists").authenticated()
                        .requestMatchers(HttpMethod.POST, "/playlists").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/playlists").hasAnyRole("ADMIN", "STAFF")

                        //tags
                        .requestMatchers(HttpMethod.GET, "/tags").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/tags").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/tags").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PUT, "/tags").hasAnyRole("ADMIN", "STAFF")

                        //shows
                        .requestMatchers(HttpMethod.GET, "/shows").permitAll()
                        .requestMatchers(HttpMethod.POST, "/shows").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PUT, "/shows").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PATCH, "/shows").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/shows").hasAnyRole("ADMIN", "STAFF")

                        .anyRequest().denyAll()
                )
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}