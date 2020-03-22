package com.information.center.authservice.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.information.center.authservice.convert.PermissionConverter;
import com.information.center.authservice.entity.Permission;
import com.information.center.authservice.entity.Role;
import com.information.center.authservice.model.PermissionRequest;
import com.information.center.authservice.repository.PermissionRepository;
import com.information.center.authservice.repository.RoleRepository;
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
public class PermissionServiceTest {


  public static final String PERMISSION_NAME = "permissionName";
  public static final String ROLE_ADMIN = "ADMIN";
  public static final String ROLE_USER = "USER";
  @Mock
  private PermissionRepository permissionRepository;
  @Mock
  private PermissionConverter permissionConverter;
  @Mock
  private RoleRepository roleRepository;
  @InjectMocks
  private PermissionService permissionService;
  private PermissionRequest permissionRequest;
  private Permission permission;
  private Role roleAdmin;
  private Role roleUser;


  @Before
  public void setUp() {
    permissionRequest = PermissionRequest.builder()
        .name(PERMISSION_NAME)
        .roles(Arrays.asList(ROLE_ADMIN, ROLE_USER))
        .build();

    permission = new Permission();

    roleAdmin = new Role();
    roleAdmin.setName(ROLE_ADMIN);

    roleUser = new Role();
    roleUser.setName(ROLE_USER);
  }

  @Test(expected = InconsistentDataException.class)
  public void createPermission_expectInconsistentDataException() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.of(permission));

    permissionService.createPermission(permissionRequest);
  }

  @Test
  public void createPermission_expectNotFoundAdminRole() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.empty());
    when(permissionConverter.toPermission(permissionRequest)).thenReturn(permission);
    when(roleRepository.findByName(ROLE_ADMIN)).thenReturn(Optional.empty());
    when(roleRepository.findByName(ROLE_USER)).thenReturn(Optional.of(roleUser));

    ErrorResponse response = permissionService.createPermission(permissionRequest);

    verify(permissionRepository).save(this.permission);
    assertTrue(response.hasErrors());
    assertTrue(response.getMessage().contains("Role " + ROLE_ADMIN + " not found"));
  }

  @Test(expected = InsertFailedException.class)
  public void createPermission_expectInsertFailedException() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.empty());
    when(permissionConverter.toPermission(permissionRequest)).thenReturn(permission);
    when(roleRepository.findByName(ROLE_ADMIN)).thenReturn(Optional.empty());
    when(roleRepository.findByName(ROLE_USER)).thenReturn(Optional.of(roleUser));
    when(permissionRepository.save(permission)).thenThrow(HibernateException.class);

    permissionService.createPermission(permissionRequest);
  }

  @Test(expected = InconsistentDataException.class)
  public void deletePermission_expectInconsistentDataException() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.empty());

    permissionService.deletePermission(permissionRequest);
  }

  @Test
  public void deletePermission_expectDeletePermission() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.of(permission));

    permissionService.deletePermission(permissionRequest);

    verify(permissionRepository).delete(permission);
  }

  @Test(expected = InsertFailedException.class)
  public void deletePermission_expectInsertFailedException() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.of(permission));
    doThrow(HibernateException.class).when(permissionRepository).delete(permission);

    permissionService.deletePermission(permissionRequest);
  }

  @Test(expected = InconsistentDataException.class)
  public void updatePermission_expectInconsistentDataException() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.empty());

    permissionService.updatePermission(permissionRequest);
  }

  @Test
  public void updatePermission_expectRoleUserNotFound() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.of(permission));
    when(roleRepository.findByName(ROLE_USER)).thenReturn(Optional.empty());
    when(roleRepository.findByName(ROLE_ADMIN)).thenReturn(Optional.of(roleUser));

    ErrorResponse response = permissionService.updatePermission(permissionRequest);

    verify(permissionRepository).save(permission);
    assertTrue(response.hasErrors());
    assertTrue(response.getMessage().contains("Role " + ROLE_USER + " not found"));
  }

  @Test(expected = InsertFailedException.class)
  public void updatePermission_expectInsertFailedException() {
    when(permissionRepository.findByName(PERMISSION_NAME)).thenReturn(Optional.of(permission));
    when(roleRepository.findByName(ROLE_USER)).thenReturn(Optional.empty());
    when(roleRepository.findByName(ROLE_ADMIN)).thenReturn(Optional.of(roleUser));
    when(permissionRepository.save(permission)).thenThrow(HibernateException.class);

    permissionService.updatePermission(permissionRequest);
  }
}
