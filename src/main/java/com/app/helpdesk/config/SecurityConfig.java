package com.app.helpdesk.config;

import com.app.helpdesk.security.JWTAuthenticationFilter;
import com.app.helpdesk.security.JWTAuthorizationFilter;
import com.app.helpdesk.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Classe de configuração do spring secutity
 * */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Constante utilizada para dar acesso aos verbos http do banco de dados H2
     * */
    private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};

    /**
     * Injeção de dependência para o perfil do ambiente configurado no application.properties
     * */
    @Autowired
    private Environment env;

    /**
     * Injeta a classe costumizada para criar tokens
     * */
    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Injeta o UserDetailsService
     * */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Configuração para habilizar a utilização dos endpoints para usuários autenticados
     * @param http Objeto para configurar as requisições http gerenciadas pelo spring security
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Validação para liberar a visualização do banco de dados H2 em localhost:8080/h2-console
        if(Arrays.asList(env.getActiveProfiles()).contains("test")) http.headers().frameOptions().disable();

        http.cors().and().csrf().disable();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        http.authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS)
                .permitAll()
                .anyRequest()
                .authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Método para sobrescrever o método authenticationManager de WebSecurityConfigurerAdapter
     * utilizado para autenticação do usuário
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getBCryptPasswordEncoder());
    }

    /**
     * Fabrica para configuração do cors do backend da aplicação
     * */
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Injeta uma instancia do encriptador de senhas
     * */
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
