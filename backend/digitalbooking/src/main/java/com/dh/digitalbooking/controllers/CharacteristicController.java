package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.CharacteristicDTO;
import com.dh.digitalbooking.entities.CharacteristicEntity;
import com.dh.digitalbooking.exceptions.CharacteristicNotFoundException;
import com.dh.digitalbooking.services.impl.CharacteristicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/characteristic")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class CharacteristicController {

    private final CharacteristicServiceImpl characteristicService;

    @Autowired
    public CharacteristicController(CharacteristicServiceImpl characteristicService) {
        this.characteristicService = characteristicService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CharacteristicDTO> findAll(Pageable pageable){
        Page<CharacteristicEntity> characteristics = characteristicService.findAll(pageable);
        return characteristics.map(CharacteristicDTO::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacteristicDTO save(@RequestBody @Valid CharacteristicDTO characteristicDTO){
        CharacteristicEntity characteristic = new CharacteristicEntity(characteristicDTO);
        return new CharacteristicDTO(characteristicService.save(characteristic));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CharacteristicDTO findById(@PathVariable Integer id) throws CharacteristicNotFoundException {
        return new CharacteristicDTO(characteristicService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) throws CharacteristicNotFoundException {
        characteristicService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CharacteristicDTO update(@RequestBody @Valid CharacteristicDTO characteristicDTO) throws CharacteristicNotFoundException {
        CharacteristicEntity characteristic = new CharacteristicEntity(characteristicDTO);
        return new CharacteristicDTO(characteristicService.update(characteristic));
    }
}
