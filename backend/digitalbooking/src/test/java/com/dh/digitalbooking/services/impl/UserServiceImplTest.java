package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.entities.UserEntity;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import com.dh.digitalbooking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static com.dh.digitalbooking.utils.UserUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailServiceImpl emailService;

    @Mock
    private ConfirmationTokenServiceImpl confirmationTokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void findAllShouldReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        when(userRepository.findAll(pageable)).thenReturn(usersPageBuilder());
        Page<UserEntity> page = userService.findAll(pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenUserThenReturnSuccessfullySavedObject() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(clientBuilderWithoutId());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        UserEntity user = clientBuilderWithoutId();
        String confirm = userService.save(user);
        assertNotNull(confirm);
    }

    @Test
    void testGivenValidIdThenReturnSuccessfullyFoundObject() throws UserNotFoundException {
        when(userRepository.findById(1)).thenReturn(Optional.of(userBuilder()));

        UserEntity expectedUser = userBuilder();
        UserEntity user = userService.findById(1);
        assertEquals(expectedUser.getId(), user.getId());
        assertEquals(expectedUser.getFirstName(), user.getFirstName());
        assertEquals(expectedUser.getLastName(), user.getLastName());
        assertEquals(expectedUser.getEmail(), user.getEmail());
    }

    @Test
    void testGivenValidUserThenReturnSuccessfullyUpdatedObject() throws UserNotFoundException {
        ClientEntity userInDatabase = clientBuilder();
        userInDatabase.setFirstName("Gabriel");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(userInDatabase));

        ClientEntity expectedUser = clientBuilder();
        when(userRepository.save(any(UserEntity.class))).thenReturn(expectedUser);

        UserEntity user = userService.update(expectedUser);

        assertEquals(expectedUser.getId(), user.getId());
        assertEquals(expectedUser.getFirstName(), user.getFirstName());
        assertEquals(expectedUser.getLastName(), user.getLastName());
        assertEquals(expectedUser.getEmail(), user.getEmail());
    }

    @Test
    void testGivenValidUserIdThenReturnSuccesOnDelete() throws UserNotFoundException {
        Integer deletedUserId = 1;
        UserEntity expectedUserToDelete = userBuilder();

        when(userRepository.findById(deletedUserId)).thenReturn(Optional.of(expectedUserToDelete));
        userService.deleteById(deletedUserId);

        verify(userRepository, times(1)).deleteById(deletedUserId);
    }
}