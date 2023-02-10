package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.RoleEntity;
import com.dh.digitalbooking.exceptions.RoleNotFoundException;
import com.dh.digitalbooking.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static com.dh.digitalbooking.utils.RoleUtils.rolesPageBuilder;
import static com.dh.digitalbooking.utils.RoleUtils.roleBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Autowired
    @InjectMocks
    private RoleServiceImpl roleService;


    @Test
    void findAllShouldReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        when(roleRepository.findAll(pageable)).thenReturn(rolesPageBuilder());
        Page<RoleEntity> page = roleService.findAll(pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenRoleThenReturnSuccessfullySavedObject() {
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleBuilder());

        RoleEntity role = new RoleEntity("ADMIN");
        RoleEntity savedRole = roleService.save(role);
        assertNotNull(savedRole.getId());
    }

    @Test
    void testGivenValidIdThenReturnSuccessfullyFoundObject() throws RoleNotFoundException {
        when(roleRepository.findById(1)).thenReturn(Optional.of(roleBuilder()));

        RoleEntity expectedRole = roleBuilder();
        RoleEntity role = roleService.findById(1);
        assertEquals(expectedRole.getId(), role.getId());
        assertEquals(expectedRole.getName(), role.getName());
    }

    @Test
    void testGivenValidRoleThenReturnSuccessfullyUpdatedObject() throws RoleNotFoundException {
        RoleEntity roleInDatabase = roleBuilder();
        roleInDatabase.setName("CLIENT");

        when(roleRepository.findById(1)).thenReturn(Optional.of(roleInDatabase));

        RoleEntity expectedRole = roleBuilder();
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(expectedRole);

        RoleEntity role= roleService.update(expectedRole);

        assertEquals(expectedRole.getId(), role.getId());
        assertEquals(expectedRole.getName(), role.getName());
    }

    @Test
    void testGivenValidRoleIdThenReturnSuccesOnDelete() throws RoleNotFoundException {
        Integer deletedRoleId = 1;
        RoleEntity expectedRoleToDelete = roleBuilder();

        when(roleRepository.findById(deletedRoleId)).thenReturn(Optional.of(expectedRoleToDelete));
        roleService.deleteById(deletedRoleId);

        verify(roleRepository, times(1)).deleteById(deletedRoleId);
    }

}