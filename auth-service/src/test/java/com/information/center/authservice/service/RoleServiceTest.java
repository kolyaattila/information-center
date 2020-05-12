package com.information.center.authservice.service;

import com.information.center.authservice.convert.RoleConverter;
import com.information.center.authservice.entity.PermissionEntity;
import com.information.center.authservice.entity.RoleEntity;
import com.information.center.authservice.model.RoleRequest;
import com.information.center.authservice.repository.PermissionRepository;
import com.information.center.authservice.repository.RoleRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import model.ErrorResponse;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    private static final String ROLE_NAME = "role";
    private static final String PERMISSION_2 = "permission2";
    private static final String PERMISSION_1 = "permission1";
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleConverter roleConverter;
    @Mock
    private PermissionRepository permissionRepository;
    @InjectMocks
    private RoleService roleService;
    @Captor
    ArgumentCaptor<RoleEntity> argumentCaptor;
    private RoleRequest roleRequest;
    private RoleEntity role;
    private PermissionEntity permission1;
    private PermissionEntity permission2;

    @Before
    public void setUp() {
        roleRequest = RoleRequest.builder()
                .name(ROLE_NAME)
                .permissions(Arrays.asList(PERMISSION_1, PERMISSION_2))
                .build();
        role = new RoleEntity();
        permission1 = new PermissionEntity();
        permission2 = new PermissionEntity();
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
        when(permissionRepository.findByName(PERMISSION_1)).thenReturn(Optional.of(permission1));
        when(permissionRepository.findByName(PERMISSION_2)).thenReturn(Optional.empty());

        ErrorResponse response = roleService.saveRole(roleRequest);

        verify(roleRepository).save(role);
        assertTrue(response.hasErrors());
        assertTrue(response.getMessage().contains("Permission " + PERMISSION_2 + " not found"));
    }

    @Test(expected = InsertFailedException.class)
    public void saveRole_expectInsertFailedException() {
        when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.empty());
        when(roleConverter.toRole(roleRequest)).thenReturn(role);
        when(permissionRepository.findByName(PERMISSION_1)).thenReturn(Optional.of(permission1));
        when(permissionRepository.findByName(PERMISSION_2)).thenReturn(Optional.empty());
        when(roleRepository.save(role)).thenThrow(HibernateException.class);

        roleService.saveRole(roleRequest);
    }

    @Test(expected = InsertFailedException.class)
    public void updateRole_expectInsertFailedException() {
        when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.of(role));
        when(permissionRepository.findByName(PERMISSION_2)).thenReturn(Optional.of(permission2));
        when(permissionRepository.findByName(PERMISSION_1)).thenReturn(Optional.empty());
        when(roleRepository.save(role)).thenThrow(HibernateException.class);
        when(roleConverter.toRole(roleRequest, 0)).thenReturn(role);

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
        when(permissionRepository.findByName(PERMISSION_2)).thenReturn(Optional.of(permission2));
        when(permissionRepository.findByName(PERMISSION_1)).thenReturn(Optional.empty());
        when(roleConverter.toRole(roleRequest, 0)).thenReturn(role);

        ErrorResponse response = roleService.updateRole(roleRequest);

        verify(roleRepository).save(role);
        assertTrue(response.hasErrors());
        assertTrue(response.getMessage().contains("Permission " + PERMISSION_1 + " not found"));
    }

    @Test
    public void deleteRole_deleteRoleWithoutErrors() {
        when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.of(role));
        roleService.deleteRole(ROLE_NAME);
        verify(roleRepository).delete(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), role);
    }
}
