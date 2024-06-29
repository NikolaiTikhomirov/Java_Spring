package ru.gb.security;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.model.Reader;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  private final WebClient webClient;

  public CustomUserDetailsService(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction, WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.filter(loadBalancerExchangeFilterFunction).build();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Reader reader = webClient.get()
            .uri("http://user-service/reader/name/{username}", username)
            .retrieve()
            .bodyToMono(Reader.class)
            .block();
//            .orElseThrow(() -> new UsernameNotFoundException(username));


    return new org.springframework.security.core.userdetails.User(reader.getLogin(), reader.getPassword(), List.of(
      new SimpleGrantedAuthority(reader.getRole())
    ));
  }

}
