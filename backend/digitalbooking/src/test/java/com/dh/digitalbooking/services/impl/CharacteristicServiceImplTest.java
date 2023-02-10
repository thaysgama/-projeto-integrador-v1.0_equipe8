package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.CharacteristicEntity;
import com.dh.digitalbooking.exceptions.CharacteristicNotFoundException;
import com.dh.digitalbooking.repository.CharacteristicRepository;
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

import static com.dh.digitalbooking.utils.CharacteristicUtils.characteristicBuilder;
import static com.dh.digitalbooking.utils.CharacteristicUtils.characteristicsPageBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class CharacteristicServiceImplTest {

    @Mock
    private CharacteristicRepository characteristicRepository;

    @Autowired
    @InjectMocks
    private CharacteristicServiceImpl characteristicService;


    @Test
    void findAllShouldReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        when(characteristicRepository.findAll(pageable)).thenReturn(characteristicsPageBuilder());
        Page<CharacteristicEntity> page = characteristicService.findAll(pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenCategoryThenReturnSuccessfullySavedObject() {
        when(characteristicRepository.save(any(CharacteristicEntity.class))).thenReturn(characteristicBuilder());

        CharacteristicEntity characteristic = new CharacteristicEntity("wifi", "www.image.com/wifi");
        CharacteristicEntity savedCharacteristic = characteristicService.save(characteristic);
        assertNotNull(savedCharacteristic.getId());
    }

    @Test
    void testGivenValidIdThenReturnSuccessfullyFoundObject() throws CharacteristicNotFoundException {
        when(characteristicRepository.findById(1)).thenReturn(Optional.of(characteristicBuilder()));

        CharacteristicEntity expectedCharacteristic = characteristicBuilder();
        CharacteristicEntity characteristic = characteristicService.findById(1);
        assertEquals(expectedCharacteristic.getId(), characteristic.getId());
        assertEquals(expectedCharacteristic.getName(), characteristic.getName());
        assertEquals(expectedCharacteristic.getIcon(), characteristic.getIcon());
    }

    @Test
    void testGivenValidCategoryThenReturnSuccessfullyUpdatedObject() throws CharacteristicNotFoundException {
        CharacteristicEntity characteristicInDatabase = characteristicBuilder();
        characteristicInDatabase.setName("Caucaia");

        when(characteristicRepository.findById(1)).thenReturn(Optional.of(characteristicInDatabase));

        CharacteristicEntity expectedCharacteristic = characteristicBuilder();
        when(characteristicRepository.save(any(CharacteristicEntity.class))).thenReturn(expectedCharacteristic);

        CharacteristicEntity characteristic= characteristicService.update(expectedCharacteristic);

        assertEquals(expectedCharacteristic.getId(), characteristic.getId());
        assertEquals(expectedCharacteristic.getName(), characteristic.getName());
        assertEquals(expectedCharacteristic.getIcon(), characteristic.getIcon());
    }

    @Test
    void testGivenValidCategoryIdThenReturnSuccesOnDelete() throws CharacteristicNotFoundException {
        Integer deletedCharacteristicId = 1;
        CharacteristicEntity expectedCharacteristicToDelete = characteristicBuilder();

        when(characteristicRepository.findById(deletedCharacteristicId)).thenReturn(Optional.of(expectedCharacteristicToDelete));
        characteristicService.deleteById(deletedCharacteristicId);

        verify(characteristicRepository, times(1)).deleteById(deletedCharacteristicId);
    }
}