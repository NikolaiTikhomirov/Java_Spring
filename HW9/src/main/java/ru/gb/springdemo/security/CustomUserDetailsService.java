package ru.gb.springdemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final ReaderRepository readerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Reader reader = readerRepository.findByLogin(username)
      .orElseThrow(() -> new UsernameNotFoundException(username));


    return new org.springframework.security.core.userdetails.User(reader.getLogin(), reader.getPassword(), List.of(
      new SimpleGrantedAuthority(reader.getRole())
    ));
  }

}
