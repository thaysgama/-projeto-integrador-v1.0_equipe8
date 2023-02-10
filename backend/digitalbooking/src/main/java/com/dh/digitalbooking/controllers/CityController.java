package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.CityDTO;
import com.dh.digitalbooking.entities.CityEntity;
import com.dh.digitalbooking.exceptions.CityNotFoundException;
import com.dh.digitalbooking.services.impl.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/city")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class CityController {

    private final CityServiceImpl cityService;

    @Autowired
    public CityController(CityServiceImpl cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CityDTO> findAll(Pageable pageable){
        Page<CityEntity> cityList = cityService.findAll(pageable);
        return cityList.map(CityDTO::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO save(@RequestBody @Valid CityDTO cityDTO){
        return new CityDTO(cityService.save(new CityEntity(cityDTO)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityDTO findById(@PathVariable Integer id) throws CityNotFoundException {
        return new CityDTO(cityService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) throws CityNotFoundException {
        cityService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CityDTO update(@RequestBody @Valid CityDTO cityDTO) throws CityNotFoundException {
        CityEntity city = new CityEntity(cityDTO);
        return new CityDTO(cityService.update(city));
    }
}
