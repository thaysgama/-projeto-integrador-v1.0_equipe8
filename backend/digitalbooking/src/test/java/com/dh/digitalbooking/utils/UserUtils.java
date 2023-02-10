package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.*;
import com.dh.digitalbooking.services.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static com.dh.digitalbooking.utils.RoleUtils.roleBuilder;

public class UserUtils {

    public static UserEntity userBuilder(){
        RoleEntity role= roleBuilder();
        UserEntity user = new UserEntity("Thays","Souza","thays@gama.com","$2a$10$PmOfc2fCfFwxrX8UbQ4.h.26Ni/fv1OUIoRTAuKaqqQ3ID6NQ2Hia",role);
        user.setId(1);
        return user;
    }

    public static ProprietorEntity proprietorBuilder(){
        RoleEntity role= roleBuilder();
        ProprietorEntity  user = new ProprietorEntity ("Carla","Souza","carla@gama.com","$2a$10$PmOfc2fCfFwxrX8UbQ4.h.26Ni/fv1OUIoRTAuKaqqQ3ID6NQ2Hia",role);
        user.setId(1);
        return user;
    }

    public static ClientEntity clientBuilderWithoutId(){
        RoleEntity role= roleBuilder();
        ClientEntity user = new ClientEntity ("Maria","Souza","maria@gama.com","$2a$10$PmOfc2fCfFwxrX8UbQ4.h.26Ni/fv1OUIoRTAuKaqqQ3ID6NQ2Hia",role);
        return user;
    }

    public static ClientEntity clientBuilder(){
        ClientEntity user = clientBuilderWithoutId();
        user.setId(1);
        return user;
    }

    public static Page<UserEntity> usersPageBuilder(){
        UserEntity proprietor = proprietorBuilder();
        UserEntity client = clientBuilder();
        return new PageImpl<>(List.of(client,proprietor));
    }
}
