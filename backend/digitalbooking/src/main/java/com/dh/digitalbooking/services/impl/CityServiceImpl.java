package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.CityEntity;
import com.dh.digitalbooking.exceptions.CityNotFoundException;
import com.dh.digitalbooking.repository.CityRepository;
import com.dh.digitalbooking.services.ICityService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CityServiceImpl implements ICityService<CityEntity> {

    @Autowired
    private CityRepository cityRepository;
    private final Logger log = Logger.getLogger(CityServiceImpl.class);


    @Override
    public CityEntity save(CityEntity city) {
        log.info("Saving city.");
        return cityRepository.save(city);
    }

    @Override
    public CityEntity update(CityEntity city) throws CityNotFoundException{
        verifyIfExists(city.getId());
        log.info("Updating city with id = "+city.getId()+".");
        return cityRepository.save(city);
    }

    @Override
    public void deleteById(Integer id) throws CityNotFoundException {
        verifyIfExists(id);
        log.info("Deleting city with id = "+id+".");
        cityRepository.deleteById(id);
    }

    @Override
    public CityEntity findById(Integer id) throws CityNotFoundException {
        CityEntity city = verifyIfExists(id);
        log.info("Finding city with id = "+id+".");
        return city;
    }

    @Override
    public Page<CityEntity> findAll(Pageable pageable) {
        log.info("Listing all cities.");
        return cityRepository.findAll(pageable);
    }

    private CityEntity verifyIfExists(Integer id) throws CityNotFoundException {
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }
}
