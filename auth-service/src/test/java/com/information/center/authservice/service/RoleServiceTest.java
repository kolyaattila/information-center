package com.information.center.authservice.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.information.center.authservice.convert.RoleConverter;
import com.information.center.authservice.entity.RoleEntity;
import com.information.center.authservice.entity.UserEntity;
import com.information.center.authservice.model.RoleRequest;
import com.information.center.authservice.repository.RoleRepository;
import com.information.center.authservice.repository.UserRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import java.util.Arrays;
import java.util.Optional;
import model.ErrorResponse;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

  private static final String ROLE_NAME = "role";
  private static final String USERNAME_2 = "username2";
  private static final String USERNAME_1 = "username1";
  @Mock
  private RoleRepository roleRepository;
  @Mock
  private RoleConverter roleConverter;
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private RoleService roleService;
  private RoleRequest roleRequest;
  private RoleEntity role;
  private UserEntity user1;
  private UserEntity user2;

  @Before
  public void setUp() {
    roleRequest = RoleRequest.builder()
        .name(ROLE_NAME)
        .usernames(Arrays.asList(USERNAME_1, USERNAME_2))
        .build();
    role = new RoleEntity();
    user1 = new UserEntity();
    user2 = new UserEntity();
  }

  @Test(expected = InconsistentDataException.class)
  public void saveRole_expectInconsistentDataException() {
    when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.of(role));

    roleService.saveRole(roleRequest);
  }

  @Test
  public void saveRole_expectCreateRoleWithError() {
    when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.empty());
    when(roleConverter.toRole(roleRequest)).thenReturn(role);
    when(userRepository.findByUsername(USERNAME_1)).thenReturn(Optional.of(user1));
    when(userRepository.findByUsername(USERNAME_2)).thenReturn(Optional.empty());

    ErrorResponse response = roleService.saveRole(roleRequest);

    verify(roleRepository).save(role);
    assertTrue(response.hasErrors());
    assertTrue(response.getMessage().contains("User " + USERNAME_2 + " not found"));
  }

  @Test(expected = InsertFailedException.class)
  public void saveRole_expectInsertFailedException() {
    when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.empty());
    when(roleConverter.toRole(roleRequest)).thenReturn(role);
    when(userRepository.findByUsername(USERNAME_1)).thenReturn(Optional.of(user1));
    when(userRepository.findByUsername(USERNAME_2)).thenReturn(Optional.empty());
    when(roleRepository.save(role)).thenThrow(HibernateException.class);

    roleService.saveRole(roleRequest);
  }

  @Test(expected = InsertFailedException.class)
  public void updateRole_expectInsertFailedException() {
    when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.of(role));
    when(userRepository.findByUsername(USERNAME_2)).thenReturn(Optional.of(user2));
    when(userRepository.findByUsername(USERNAME_1)).thenReturn(Optional.empty());
    when(roleRepository.save(role)).thenThrow(HibernateException.class);

    roleService.updateRole(roleRequest);
  }

  @Test(expected = InconsistentDataException.class)
  public void updateRole_expectInconsistentDataException() {
    when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.empty());

    roleService.updateRole(roleRequest);
  }

  @Test
  public void updateRole_expectUpdateRoleWithErrors() {
    when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.of(role));
    when(userRepository.findByUsername(USERNAME_2)).thenReturn(Optional.of(user2));
    when(userRepository.findByUsername(USERNAME_1)).thenReturn(Optional.empty());

    ErrorResponse response = roleService.updateRole(roleRequest);

    verify(roleRepository).save(role);
    assertTrue(response.hasErrors());
    assertTrue(response.getMessage().contains("User " + USERNAME_1 + " not found"));
  }
}
