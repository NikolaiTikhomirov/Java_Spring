package ru.gb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//    converter.setJwtGrantedAuthoritiesConverter(source -> {
//      Map<String, Object> resourceAccess = source.getClaim("realm_access");
//      List<String> roles = (List<String>) resourceAccess.get("roles");
//
//      return roles.stream()
//        .map(SimpleGrantedAuthority::new)
//        .collect(Collectors.toList());
//    });

    return httpSecurity
      .authorizeHttpRequests(configurer -> configurer
        .requestMatchers("/ui/issues/**").hasAuthority("admin")
        .requestMatchers("/ui/readers/**").hasAuthority("reader")
        .requestMatchers("/ui/books/**").authenticated()
        .anyRequest().permitAll()
      )
//      .oauth2ResourceServer(configurer -> configurer
//        .jwt(jwtConfigurer -> jwtConfigurer
//          .jwtAuthenticationConverter(converter))
//      )
        .formLogin(Customizer.withDefaults())
      .build();
  }

}
