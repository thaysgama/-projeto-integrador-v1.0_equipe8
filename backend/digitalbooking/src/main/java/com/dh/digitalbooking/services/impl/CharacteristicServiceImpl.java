package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.CharacteristicEntity;
import com.dh.digitalbooking.exceptions.CharacteristicNotFoundException;
import com.dh.digitalbooking.repository.CharacteristicRepository;
import com.dh.digitalbooking.services.ICharacteristicService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CharacteristicServiceImpl implements ICharacteristicService<CharacteristicEntity> {

    @Autowired
    private CharacteristicRepository characteristicRepository;
    private final Logger log = Logger.getLogger(CharacteristicServiceImpl.class);

    @Override
    public CharacteristicEntity save(CharacteristicEntity characteristic) {
        log.info("Saving characteristic.");
        return characteristicRepository.save(characteristic);
    }

    @Override
    public CharacteristicEntity update(CharacteristicEntity characteristic) throws CharacteristicNotFoundException {
        verifyIfExists(characteristic.getId());
        log.info("Updating characteristic with id = "+characteristic.getId()+".");
        return characteristicRepository.save(characteristic);
    }

    @Override
    public void deleteById(Integer id) throws CharacteristicNotFoundException {
        verifyIfExists(id);
        log.info("Deleting characteristic with id = "+id+".");
        characteristicRepository.deleteById(id);
    }

    @Override
    public CharacteristicEntity findById(Integer id) throws CharacteristicNotFoundException {
        CharacteristicEntity characteristic = verifyIfExists(id);
        log.info("Finding characteristic with id = "+id+".");
        return characteristic;
    }

    @Override
    public CharacteristicEntity findByIdOrNull(Integer id)  {
        CharacteristicEntity characteristic = characteristicRepository.findById(id).orElse(null);
        log.info("Finding characteristic with id = "+id+".");
        return characteristic;
    }

    @Override
    public Page<CharacteristicEntity> findAll(Pageable pageable) {
        log.info("Listing all characteristics.");
        return characteristicRepository.findAll(pageable);
    }

    private CharacteristicEntity verifyIfExists(Integer id) throws CharacteristicNotFoundException {
        return characteristicRepository.findById(id)
                .orElseThrow(() -> new CharacteristicNotFoundException(id));
    }
}
