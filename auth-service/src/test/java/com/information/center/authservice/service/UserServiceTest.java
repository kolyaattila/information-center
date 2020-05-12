package com.information.center.authservice.service;

import com.information.center.authservice.convert.UserConverter;
import com.information.center.authservice.entity.RoleEntity;
import com.information.center.authservice.entity.UserEntity;
import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.model.UserRoleRequest;
import com.information.center.authservice.repository.RoleRepository;
import com.information.center.authservice.repository.UserRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import model.ErrorResponse;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String USERNAME = "username";
    private static final String ROLE_1 = "roleName1";
    private static final String ROLE_2 = "roleName2";
    private final ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
    @Mock
    private UserConverter userConverter;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService userService;
    private UserRequest userRequest;
    private UserEntity user;
    private RoleEntity role;
    private PasswordUpdateRequest passwordUpdateRequest;
    private UserRoleRequest userRoleRequest;

    @Before
    public void setUp() {
        role = new RoleEntity();
        userRoleRequest = UserRoleRequest.builder()
                .roles(Arrays.asList(ROLE_1, ROLE_2))
                .username(USERNAME)
                .build();
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

    @Test
    public void assignRolesToUser_expectAssignedRoles() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.ofNullable(user));
        when(roleRepository.findByName(ROLE_1)).thenReturn(Optional.ofNullable(role));
        when(roleRepository.findByName(ROLE_2)).thenReturn(Optional.ofNullable(role));

        ErrorResponse response = userService.assignRolesToUser(userRoleRequest);
        assertFalse(response.hasErrors());
    }

    @Test
    public void assignRolesToUser_expectErrorResponse() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.ofNullable(user));
        when(roleRepository.findByName(ROLE_1)).thenReturn(Optional.empty());
        when(roleRepository.findByName(ROLE_2)).thenReturn(Optional.ofNullable(role));

        ErrorResponse response = userService.assignRolesToUser(userRoleRequest);
        assertTrue(response.hasErrors());
    }

    @Test(expected = InconsistentDataException.class)
    public void assignRolesToUser_expectInconsistentDataException() {
        when(userRepository.findByUsername(USERNAME)).thenThrow(InconsistentDataException.class);

        userService.assignRolesToUser(userRoleRequest);
    }

    @Test(expected = InsertFailedException.class)
    public void assignRolesToUser_expectInsertFailedException() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.ofNullable(user));
        when(roleRepository.findByName(ROLE_1)).thenReturn(Optional.ofNullable(role));
        when(roleRepository.findByName(ROLE_2)).thenReturn(Optional.ofNullable(role));
        when(userRepository.save(any())).thenThrow(HibernateException.class);

        userService.assignRolesToUser(userRoleRequest);
    }

    @Test
    public void deleteUser_expectDeletedUser() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.ofNullable(user));
        userService.deleteUser(USERNAME);
        verify(userRepository).delete(captor.capture());
        assertEquals(user, captor.getValue());
    }

    @Test(expected = InconsistentDataException.class)
    public void deleteUser_expectInconsistentDataException() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        userService.deleteUser(USERNAME);
        verify(userRepository).delete(captor.capture());
        assertEquals(user, captor.getValue());
    }
}
