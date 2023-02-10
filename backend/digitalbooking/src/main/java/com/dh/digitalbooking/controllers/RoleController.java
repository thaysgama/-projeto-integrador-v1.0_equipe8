package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.RoleDTO;
import com.dh.digitalbooking.entities.RoleEntity;
import com.dh.digitalbooking.exceptions.RoleNotFoundException;
import com.dh.digitalbooking.services.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/role")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class RoleController {

    private final RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<RoleDTO> findAll(Pageable pageable){
        Page<RoleEntity> categoriesList = roleService.findAll(pageable);
        return categoriesList.map(RoleDTO::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO save(@RequestBody @Valid RoleDTO roleDTO){
        RoleEntity role = new RoleEntity(roleDTO);
        return new RoleDTO(roleService.save(role));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO findById(@PathVariable Integer id) throws RoleNotFoundException {
        return new RoleDTO(roleService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) throws RoleNotFoundException {
        roleService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO update(@RequestBody @Valid RoleDTO roleDTO) throws RoleNotFoundException {
        RoleEntity role = new RoleEntity(roleDTO);
        return new RoleDTO(roleService.update(role));
    }
}
