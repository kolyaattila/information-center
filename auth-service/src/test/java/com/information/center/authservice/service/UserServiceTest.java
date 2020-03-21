package com.information.center.authservice.service;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.information.center.authservice.convert.UserConverter;
import com.information.center.authservice.entity.UserEntity;
import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.repository.UserRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  public static final String USERNAME = "username";
  private final ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
  @Mock
  private UserConverter userConverter;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @InjectMocks
  private UserService userService;
  private UserRequest userRequest;
  private UserEntity user;
  private PasswordUpdateRequest passwordUpdateRequest;

  @Before
  public void setUp() {
    userRequest = UserRequest.builder()
        .password("password")
        .username(USERNAME)
        .build();
    user = new UserEntity();
    passwordUpdateRequest = PasswordUpdateRequest.builder()
        .oldPassword("oldPassword")
        .password("password")
        .username(USERNAME)
        .build();
  }

  @Test(expected = InconsistentDataException.class)
  public void createUser_expectInconsistentDataException() {
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

    userService.createUser(userRequest);
  }

  @Test
  public void createUser_expectCreateUser() {
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
    when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("passwordEncoded");
    when(userConverter.toUser(userRequest)).thenReturn(user);

    userService.createUser(userRequest);

    verify(userRepository).save(user);
  }

  @Test(expected = InsertFailedException.class)
  public void createUser_expectInsertFailedException() {
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
    when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("passwordEncoded");
    when(userConverter.toUser(userRequest)).thenReturn(user);
    when(userRepository.save(user)).thenThrow(HibernateException.class);

    userService.createUser(userRequest);
  }

  @Test(expected = InconsistentDataException.class)
  public void updatePassword_OldPasswordIsBlank() {
    when(passwordEncoder.encode(passwordUpdateRequest.getPassword())).thenReturn("passwordEncoded");
    when(passwordEncoder.encode(passwordUpdateRequest.getOldPassword()))
        .thenReturn(EMPTY_STRING);

    userService.updatePassword(passwordUpdateRequest);
  }

  @Test(expected = InconsistentDataException.class)
  public void updatePassword_OldPasswordEqualsNewPassword() {
    when(passwordEncoder.encode(passwordUpdateRequest.getPassword())).thenReturn("passwordEncoded");
    when(passwordEncoder.encode(passwordUpdateRequest.getOldPassword()))
        .thenReturn("passwordEncoded");

    userService.updatePassword(passwordUpdateRequest);
  }

  @Test(expected = InconsistentDataException.class)
  public void updatePassword_userDoNotExist() {
    when(passwordEncoder.encode(passwordUpdateRequest.getPassword())).thenReturn("passwordEncoded");
    when(passwordEncoder.encode(passwordUpdateRequest.getOldPassword()))
        .thenReturn("oldPasswordEncoded");
    when(userRepository.findByUsernameAndPassword(USERNAME, "oldPasswordEncoded"))
        .thenReturn(Optional.empty());

    userService.updatePassword(passwordUpdateRequest);
  }

  @Test
  public void updatePassword_expectUpdatePassword() {
    when(passwordEncoder.encode(passwordUpdateRequest.getPassword())).thenReturn("passwordEncoded");
    when(passwordEncoder.encode(passwordUpdateRequest.getOldPassword()))
        .thenReturn("oldPasswordEncoded");
    when(userRepository.findByUsernameAndPassword(USERNAME, "oldPasswordEncoded"))
        .thenReturn(Optional.of(user));

    userService.updatePassword(passwordUpdateRequest);

    verify(userRepository).save(captor.capture());
    UserEntity userCaptor = captor.getValue();
    assertEquals("passwordEncoded", userCaptor.getPassword());
  }

  @Test(expected = InsertFailedException.class)
  public void updatePassword_expectInsertFailedException() {
    when(passwordEncoder.encode(passwordUpdateRequest.getPassword())).thenReturn("passwordEncoded");
    when(passwordEncoder.encode(passwordUpdateRequest.getOldPassword()))
        .thenReturn("oldPasswordEncoded");
    when(userRepository.findByUsernameAndPassword(USERNAME, "oldPasswordEncoded"))
        .thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenThrow(HibernateException.class);

    userService.updatePassword(passwordUpdateRequest);
  }
}
