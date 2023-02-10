package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

public class CityUtils {
    public static CityEntity cityBuilder(){
        CityEntity city = new CityEntity("Fortaleza", "CE", "Brasil");
        city.setId(1);
        return city;
    }

    public static Page<CityEntity> citiesPageBuilder(){
        CityEntity city1 = new CityEntity("Fortaleza", "CE", "Brasil");
        CityEntity city2 = new CityEntity("São Paulo", "SP", "Brasil");
        city1.setId(1);
        city2.setId(1);
        return new PageImpl<>(Arrays.asList(city1,city2));
    }
}
