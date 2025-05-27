/*package com.holidaymatrix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()  // Active CORS avec la configuration définie ci-dessous
                .csrf().disable()  // Désactive CSRF pour simplifier (à réactiver si nécessaire)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()  // Autorise tout le monde à accéder à /login
                        .anyRequest().authenticated()  // Protège les autres routes
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login")  // Définit /login comme la route de traitement du login
                        .permitAll()
                )
                .logout().permitAll();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}*/

/*
package com.holidaymatrix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Gestion CORS
                .csrf(csrf -> csrf.disable()) // Désactive CSRF pour une API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll() // Autorise /login sans authentification
                        .anyRequest().authenticated() // Protège les autres routes
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // Route pour traiter la connexion
                        .successHandler(jsonAuthenticationSuccessHandler()) // Réponse JSON en cas de succès
                        .failureHandler(jsonAuthenticationFailureHandler()) // Réponse JSON en cas d’échec
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Login successful\"}");
        };
    }

    @Bean
    public AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(401); // Statut Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid username or password\"}");
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // URL de ton frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}*/

/*package com.holidaymatrix.config;

import com.holidaymatrix.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Gestion CORS
                .csrf(csrf -> csrf.disable()) // Désactive CSRF pour une API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/api/auth/register").permitAll() // Autorise /login et /api/auth/register
                        .anyRequest().authenticated() // Protège les autres routes
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // Route pour traiter la connexion
                        .successHandler(jsonAuthenticationSuccessHandler()) // Réponse JSON en cas de succès
                        .failureHandler(jsonAuthenticationFailureHandler()) // Réponse JSON en cas d’échec
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .userDetailsService(userDetailsService); // Utilise le service personnalisé pour charger les utilisateurs
            .formLogin(formLogin -> formLogin.disable());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Login successful\"}");
        };
    }

    @Bean
    public AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(401); // Statut Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid username or password\"}");
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // URL de ton frontend Angular
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/

// make en commentaire le 10/05/2025 a 18:18
/*package com.holidaymatrix.config;

import com.holidaymatrix.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Gestion CORS
                .csrf(csrf -> csrf.disable()) // Désactive CSRF pour une API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/api/auth/register", "/api/auth/login").permitAll() // Autorise les endpoints d'authentification
                        .anyRequest().authenticated() // Protège les autres routes
                )
                .formLogin(form -> form.disable()) // Désactive le formulaire de connexion par défaut
                .userDetailsService(userDetailsService); // Utilise le service personnalisé pour charger les utilisateurs

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // URL de ton frontend Angular
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/
//SecurityConfig.java

//make en comment le 26/05/2025 a 6:14
/*
package com.holidaymatrix.config;

import com.holidaymatrix.security.JwtRequestFilter;
import com.holidaymatrix.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/role/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices/*dg-validation").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices/*hos-validation").hasAuthority("HEAD_OF_SERVICE")
                        .requestMatchers("/api/dashboard/employee").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/



/*
package com.holidaymatrix.config;

import com.holidaymatrix.security.JwtRequestFilter;
import com.holidaymatrix.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints publics (authentification)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Gestion des utilisateurs - Admin/HOS uniquement
                        .requestMatchers("/api/users/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/users/role/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Matrices - Validations spécifiques
                        .requestMatchers("/api/matrices/*dg-validation").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices//hos-validation").hasAuthority("HEAD_OF_SERVICE")
                        .requestMatchers("/api/matrices/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Plannings - Permissions par rôle
                        .requestMatchers("/api/holiday-planning/*validate/manager").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/*validate/hos").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/employee/**").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Statistiques - Permissions par rôle
                        .requestMatchers("/api/statistics/managers").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/statistics/global").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/statistics/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/statistics/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Dashboard - Permissions par rôle
                        .requestMatchers("/api/dashboard/manager").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/hos").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/dg").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/employee").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/**").authenticated()

                        // Organisations et Positions
                        .requestMatchers("/api/organizations/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/positions/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Employés
                        .requestMatchers("/api/employees/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/employees/**").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Notifications
                        .requestMatchers("/api/notifications/**").authenticated()

                        // Commentaires
                        .requestMatchers("/api/comments/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Toutes les autres requêtes nécessitent une authentification
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/



//commenté le 27/05/2025 a 12:09 am
/*
package com.holidaymatrix.config;

import com.holidaymatrix.security.JwtRequestFilter;
import com.holidaymatrix.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints publics (authentification)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Gestion des utilisateurs - Admin/HOS uniquement
                        .requestMatchers("/api/users/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/users/role/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Matrices - Validations spécifiques
                        .requestMatchers("/api/matrices/*dg-validation").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices/*hos-validation").hasAuthority("HEAD_OF_SERVICE")
                        .requestMatchers("/api/matrices/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Plannings - Permissions par rôle
                        .requestMatchers("/api/holiday-planning/*validate/manager").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/*validate/hos").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/employee/**").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Statistiques - Permissions par rôle
                        .requestMatchers("/api/statistics/managers").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/statistics/global").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/statistics/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/statistics/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Dashboard - Permissions par rôle
                        .requestMatchers("/api/dashboard/manager").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/hos").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/dg").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/employee").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/**").authenticated()

                        // Organisations et Positions
                        .requestMatchers("/api/organizations/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/positions/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Employés
                        .requestMatchers("/api/employees/user/**").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/employees/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/employees/**").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Notifications
                        .requestMatchers("/api/notifications/**").authenticated()

                        // Commentaires
                        .requestMatchers("/api/comments/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // Toutes les autres requêtes nécessitent une authentification
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/
package com.holidaymatrix.config;

import com.holidaymatrix.security.JwtRequestFilter;
import com.holidaymatrix.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Pour utiliser @PreAuthorize
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // === ENDPOINTS PUBLICS ===
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        // === AUTHENTIFICATION & UTILISATEURS ===
                        .requestMatchers("/api/users/role/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/users/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === EMPLOYÉS ===
                        // Accès aux données d'employés par ID utilisateur (avec validation de sécurité dans le contrôleur)
                        .requestMatchers("/api/employees/user/**").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Accès aux équipes de managers
                        .requestMatchers("/api/employees/manager/*/team").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Tous les employés (admin seulement)
                        .requestMatchers("/api/employees/all").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // CRUD sur les employés
                        .requestMatchers("/api/employees/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === MATRICES ===
                        // Validation DG
                        .requestMatchers("/api/matrices/*/dg-validation").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices/*/validate/dg").hasAuthority("DIRECTION_GENERAL")
                        // Validation HOS
                        .requestMatchers("/api/matrices/*/hos-validation").hasAuthority("HEAD_OF_SERVICE")
                        .requestMatchers("/api/matrices/*/validate/hos").hasAuthority("HEAD_OF_SERVICE")
                        // Gestion par manager
                        .requestMatchers("/api/matrices/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices/create").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/matrices/update/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Consultation général
                        .requestMatchers("/api/matrices/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === PLANIFICATIONS ===
                        // Validation par manager et HOS
                        .requestMatchers("/api/holiday-planning/*/validate/manager").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/*/validate/hos").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/*/validate").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Suppression
                        .requestMatchers("/api/holiday-planning/*/delete").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Gestion par manager
                        .requestMatchers("/api/holiday-planning/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/create").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Consultation par employé
                        .requestMatchers("/api/holiday-planning/employee/**").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/holiday-planning/my-planning").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Gestion général
                        .requestMatchers("/api/holiday-planning/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === STATISTIQUES ===
                        // Statistiques globales
                        .requestMatchers("/api/statistics/global").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/statistics/all-managers").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Statistiques par manager
                        .requestMatchers("/api/statistics/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/statistics/my-team").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Général
                        .requestMatchers("/api/statistics/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === DASHBOARD ===
                        // Dashboards spécifiques par rôle
                        .requestMatchers("/api/dashboard/manager").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/hos").hasAnyAuthority("HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/dg").hasAuthority("DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/employee").hasAnyAuthority("EMPLOYEE", "MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        // Données du dashboard
                        .requestMatchers("/api/dashboard/pending-requests").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/team-summary").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/dashboard/**").authenticated()

                        // === ORGANISATIONS & POSITIONS ===
                        .requestMatchers("/api/organizations/**").hasAnyAuthority("ADMIN", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/positions/manager/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")
                        .requestMatchers("/api/positions/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === NOTIFICATIONS ===
                        .requestMatchers("/api/notifications/my").authenticated()
                        .requestMatchers("/api/notifications/mark-read/**").authenticated()
                        .requestMatchers("/api/notifications/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === COMMENTAIRES ===
                        .requestMatchers("/api/comments/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === PÉRIODES DE VACANCES ===
                        .requestMatchers("/api/holiday-periods/**").hasAnyAuthority("MANAGER", "HEAD_OF_SERVICE", "DIRECTION_GENERAL")

                        // === FALLBACK - Toutes les autres requêtes nécessitent une authentification ===
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origines autorisées
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "http://127.0.0.1:4200"
        ));

        // Méthodes HTTP autorisées
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        // Headers autorisés
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Headers exposés au client
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // Autoriser les credentials
        configuration.setAllowCredentials(true);

        // Cache des options CORS (5 minutes)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}