package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.CityEntity;
import com.dh.digitalbooking.exceptions.CityNotFoundException;
import com.dh.digitalbooking.repository.CityRepository;
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

import static com.dh.digitalbooking.utils.CityUtils.citiesPageBuilder;
import static com.dh.digitalbooking.utils.CityUtils.cityBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class CityServiceImplTest {
    @Mock
    private CityRepository cityRepository;

    @Autowired
    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void findAllShouldReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        when(cityRepository.findAll(pageable)).thenReturn(citiesPageBuilder());
        Page<CityEntity> page = cityService.findAll(pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenCityThenReturnSuccessfullySavedObject() {
        when(cityRepository.save(any(CityEntity.class))).thenReturn(cityBuilder());

        CityEntity city = new CityEntity("Fortaleza", "CE", "Brasil");
        CityEntity savedCity = cityService.save(city);
        assertNotNull(savedCity.getId());
    }

    @Test
    void testGivenValidIdThenReturnSuccessfullyFoundObject() throws CityNotFoundException {
        when(cityRepository.findById(1)).thenReturn(Optional.of(cityBuilder()));

        CityEntity expectedCity = cityBuilder();
        CityEntity city = cityService.findById(1);
        assertEquals(expectedCity.getId(), city.getId());
        assertEquals(expectedCity.getName(), city.getName());
        assertEquals(expectedCity.getUf(), city.getUf());
        assertEquals(expectedCity.getCountry(), city.getCountry());
    }

    @Test
    void testGivenValidCityThenReturnSuccessfullyUpdatedObject() throws CityNotFoundException {
        CityEntity cityInDatabase = cityBuilder();
        cityInDatabase.setName("Caucaia");

        when(cityRepository.findById(1)).thenReturn(Optional.of(cityInDatabase));

        CityEntity expectedCity = cityBuilder();
        when(cityRepository.save(any(CityEntity.class))).thenReturn(expectedCity);

        CityEntity city= cityService.update(expectedCity);

        assertEquals(expectedCity.getId(), city.getId());
        assertEquals(expectedCity.getName(), city.getName());
        assertEquals(expectedCity.getUf(), city.getUf());
        assertEquals(expectedCity.getCountry(), city.getCountry());
    }

    @Test
    void testGivenValidCityIdThenReturnSuccesOnDelete() throws CityNotFoundException {
        Integer deletedCityId = 1;
        CityEntity expectedCityToDelete = cityBuilder();

        when(cityRepository.findById(deletedCityId)).thenReturn(Optional.of(expectedCityToDelete));
        cityService.deleteById(deletedCityId);

        verify(cityRepository, times(1)).deleteById(deletedCityId);
    }
}