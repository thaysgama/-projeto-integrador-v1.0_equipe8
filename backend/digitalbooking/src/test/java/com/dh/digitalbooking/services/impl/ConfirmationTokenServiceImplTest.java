package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.ConfirmationToken;
import com.dh.digitalbooking.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.dh.digitalbooking.utils.ConfirmationTokenUtils.confirmationTokenBuilderWithoutId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class ConfirmationTokenServiceImplTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    @InjectMocks
    private ConfirmationTokenServiceImpl confirmationTokenService;

    @Test
    public void saveConfirmationTokenShouldReturnSuccess() {
        ConfirmationToken confirmationToken = confirmationTokenBuilderWithoutId();
        ConfirmationToken expectedConfirmationToken = new ConfirmationToken(confirmationToken.getToken(), confirmationToken.getCreatedAt(), confirmationToken.getExpiresAt(), confirmationToken.getUser());
        expectedConfirmationToken.setId(1);
        when(confirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(expectedConfirmationToken);
        ConfirmationToken savedConfirmationToken = confirmationTokenService.saveConfirmationToken(confirmationToken);

        assertNotNull(savedConfirmationToken.getId());
    }

    @Test
    public void getTokenShouldReturnSuccess() {
        ConfirmationToken expectedConfirmationToken = confirmationTokenBuilderWithoutId();
        expectedConfirmationToken.setId(1);
        when(confirmationTokenRepository.findByToken(any(String.class))).thenReturn(Optional.of(expectedConfirmationToken));
        Optional<ConfirmationToken> confirmationToken = confirmationTokenService.getToken(expectedConfirmationToken.getToken());

        assertNotNull(confirmationToken);
    }

    @Test
    public void setConfirmedAtShouldReturnSuccess() {
        ConfirmationToken expectedConfirmationToken = confirmationTokenBuilderWithoutId();
        expectedConfirmationToken.setId(1);
        expectedConfirmationToken.setConfirmedAt(LocalDateTime.now());
        when(confirmationTokenRepository.updateConfirmedAt(any(String.class), any(LocalDateTime.class))).thenReturn(1);
        int updateResult = confirmationTokenService.setConfirmedAt(expectedConfirmationToken.getToken());

        assertEquals(1, updateResult);
    }


}