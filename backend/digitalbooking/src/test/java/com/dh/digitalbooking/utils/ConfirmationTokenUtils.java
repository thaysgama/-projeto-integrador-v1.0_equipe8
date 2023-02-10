package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.ConfirmationToken;
import com.dh.digitalbooking.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.dh.digitalbooking.utils.UserUtils.userBuilder;

public class ConfirmationTokenUtils {

    public static ConfirmationToken confirmationTokenBuilderWithoutId(){
        UserEntity user = userBuilder();
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationToken.setId(1);
        return confirmationToken;
    }


}
