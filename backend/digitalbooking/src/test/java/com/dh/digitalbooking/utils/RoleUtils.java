package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

public class RoleUtils {
    public static RoleEntity roleBuilderWithoutId(){
        return new RoleEntity("ADMIN");
    }

    public static RoleEntity roleBuilder(){
        RoleEntity role = roleBuilderWithoutId();
        role.setId(1);
        return role;
    }

    public static Page<RoleEntity> rolesPageBuilder(){
        RoleEntity role1 = roleBuilder();
        RoleEntity role2 = new RoleEntity("CLIENT");
        role2.setId(2);
        return new PageImpl<>(Arrays.asList(role1,role2));
    }
}
