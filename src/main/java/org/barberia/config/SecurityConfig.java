
package org.barberia.config;

import org.barberia.seguridad.UsuarioDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UsuarioDetailsServiceImpl usuarioDetailsService;

    @Bean
    @SuppressWarnings("deprecation")
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    // Registra el proveedor de autenticación personalizado
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetailsService);
        provider.setPasswordEncoder(passwordEncoder());  // Sin encriptar
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//

                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/login", "/registro", "/css/**", "/js/**").permitAll()

                        // Rutas accesibles para todos los roles autenticados
                        .requestMatchers("/", "/home").hasAnyRole("cliente", "barbero", "administrador")

                        // Rutas exclusivas para cliente y administrador
                        .requestMatchers("/servicios/**").hasAnyRole("cliente", "administrador")

                        // Rutas accesibles para cliente, barbero y administrador
                        .requestMatchers("/citas/**").hasAnyRole("cliente", "barbero", "administrador")

                        // Rutas exclusivas para administrador
                        .requestMatchers("/usuarios/**", "/barberos/**").hasRole("administrador")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()


                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)  // Redirige al home después del login para todos los roles
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.authenticationProvider(authenticationProvider()).build();
    }
}





