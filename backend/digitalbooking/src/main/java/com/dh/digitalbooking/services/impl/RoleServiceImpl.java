package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.RoleEntity;
import com.dh.digitalbooking.exceptions.RoleNotFoundException;
import com.dh.digitalbooking.repository.RoleRepository;
import com.dh.digitalbooking.services.IRoleService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService<RoleEntity> {

    @Autowired
    private RoleRepository roleRepository;
    private final Logger log = Logger.getLogger(RoleServiceImpl.class);


    @Override
    public Page<RoleEntity> findAll(Pageable pageable){
        log.info("Listing all roles.");
        return roleRepository.findAll(pageable);
    }

    @Override
    public RoleEntity save(RoleEntity role){
        log.info("Saving role.");
        return roleRepository.save(role);
    }

    @Override
    public RoleEntity update(RoleEntity role) throws RoleNotFoundException {
        verifyIfExists(role.getId());
        log.info("Updating role with id = "+role.getId()+".");
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) throws RoleNotFoundException {
        verifyIfExists(id);
        log.info("Deleting role with id = "+id+".");
        roleRepository.deleteById(id);
    }

    @Override
    public RoleEntity findById(Integer id) throws RoleNotFoundException {
        RoleEntity role = verifyIfExists(id);
        log.info("Finding role with id = "+id+".");
        return role;
    }

    private RoleEntity verifyIfExists(Integer id) throws RoleNotFoundException {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }
}
