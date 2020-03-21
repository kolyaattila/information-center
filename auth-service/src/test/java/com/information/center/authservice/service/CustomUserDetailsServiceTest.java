package com.information.center.authservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.information.center.authservice.entity.UserEntity;
import com.information.center.authservice.repository.UserRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

  private static final String USERNAME = "username";
  @InjectMocks
  private CustomUserDetailsService customUserDetailsService;

  @Mock
  private UserRepository userRepository;
  private UserEntity user;

  @Before
  public void setUp() {
    user = new UserEntity();
  }

  @Test
  public void loadUserByUsername_expectUser() {
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

    UserDetails response = customUserDetailsService.loadUserByUsername(USERNAME);

    assertEquals(user, response);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsername_expectUsernameNotFoundException() {
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

    customUserDetailsService.loadUserByUsername(USERNAME);
  }
}
