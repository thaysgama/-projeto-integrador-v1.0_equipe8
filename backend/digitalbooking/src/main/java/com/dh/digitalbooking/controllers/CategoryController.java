package com.dh.digitalbooking.controllers;


import com.dh.digitalbooking.dto.CategoryDTO;
import com.dh.digitalbooking.entities.CategoryEntity;
import com.dh.digitalbooking.exceptions.CategoryNotFoundException;
import com.dh.digitalbooking.services.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoryDTO> findAll(Pageable pageable){
        Page<CategoryEntity> categoriesList = categoryService.findAll(pageable);
        return categoriesList.map(CategoryDTO::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO save(@RequestBody @Valid CategoryDTO categoryDTO){
        CategoryEntity category = new CategoryEntity(categoryDTO);
        return new CategoryDTO(categoryService.save(category));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO findById(@PathVariable Integer id) throws CategoryNotFoundException {
        return new CategoryDTO(categoryService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) throws CategoryNotFoundException {
        categoryService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO update(@RequestBody @Valid CategoryDTO categoryDTO) throws CategoryNotFoundException {
        CategoryEntity category = new CategoryEntity(categoryDTO);
        return new CategoryDTO(categoryService.update(category));
    }
}
