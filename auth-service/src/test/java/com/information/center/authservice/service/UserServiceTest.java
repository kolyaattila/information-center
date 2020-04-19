package com.information.center.authservice.service;

import com.information.center.authservice.convert.UserConverter;
import com.information.center.authservice.entity.UserEntity;
import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.repository.UserRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String USERNAME = "username";
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
    public void updatePassword_incorrectPassword() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(passwordUpdateRequest.getOldPassword(), user.getPassword())).thenReturn(false);

        userService.updatePassword(passwordUpdateRequest);
    }

    @Test(expected = InconsistentDataException.class)
    public void updatePassword_OldPasswordEqualsNewPassword() {
        passwordUpdateRequest.setOldPassword("password");

        userService.updatePassword(passwordUpdateRequest);
    }

    @Test(expected = InconsistentDataException.class)
    public void updatePassword_userDoNotExist() {
        when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.empty());

        userService.updatePassword(passwordUpdateRequest);
    }

    @Test
    public void updatePassword_expectUpdatePassword() {
        when(passwordEncoder.matches(passwordUpdateRequest.getOldPassword(), user.getPassword())).thenReturn(true);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(passwordUpdateRequest.getPassword())).thenReturn("passwordEncoded");

        userService.updatePassword(passwordUpdateRequest);

        verify(userRepository).save(captor.capture());
        UserEntity userCaptor = captor.getValue();
        assertEquals("passwordEncoded", userCaptor.getPassword());
    }

    @Test(expected = InsertFailedException.class)
    public void updatePassword_expectInsertFailedException() {
        when(passwordEncoder.matches(passwordUpdateRequest.getOldPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(passwordUpdateRequest.getPassword())).thenReturn("passwordEncoded");
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenThrow(HibernateException.class);

        userService.updatePassword(passwordUpdateRequest);
    }
}
