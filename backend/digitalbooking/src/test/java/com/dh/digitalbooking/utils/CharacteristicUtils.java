package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.CharacteristicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

public class CharacteristicUtils {

    public static CharacteristicEntity characteristicBuilder(){
        CharacteristicEntity characteristic = new CharacteristicEntity("wifi", "www.image.com/wifi");
        characteristic.setId(1);
        return characteristic;
    }

    public static Page<CharacteristicEntity> characteristicsPageBuilder(){
        CharacteristicEntity characteristic1 = new CharacteristicEntity("wifi", "www.image.com/wifi");
        CharacteristicEntity characteristic2 = new CharacteristicEntity("televisão", "www.image.com/tv");
        characteristic1.setId(1);
        characteristic2.setId(1);
        return new PageImpl<>(Arrays.asList(characteristic1,characteristic2));
    }
}
