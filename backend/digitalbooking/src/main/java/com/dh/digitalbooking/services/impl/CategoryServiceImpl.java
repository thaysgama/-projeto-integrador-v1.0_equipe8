package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.CategoryEntity;
import com.dh.digitalbooking.exceptions.CategoryNotFoundException;
import com.dh.digitalbooking.repository.CategoryRepository;
import com.dh.digitalbooking.services.ICategoryService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements ICategoryService<CategoryEntity> {

    @Autowired
    private CategoryRepository categoryRepository;
    private final Logger log = Logger.getLogger(CategoryServiceImpl.class);


    @Override
    public Page<CategoryEntity> findAll(Pageable pageable){
        log.info("Listing all categories.");
        return categoryRepository.findAll(pageable);
    }

    @Override
    public CategoryEntity save(CategoryEntity category){
        log.info("Saving category.");
        return categoryRepository.save(category);
    }

    @Override
    public CategoryEntity update(CategoryEntity category) throws CategoryNotFoundException {
        verifyIfExists(category.getId());
        log.info("Updating category with id = "+category.getId()+".");
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Integer id) throws CategoryNotFoundException {
        verifyIfExists(id);
        log.info("Deleting category with id = "+id+".");
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryEntity findById(Integer id) throws CategoryNotFoundException {
        CategoryEntity category = verifyIfExists(id);
        log.info("Finding category with id = "+id+".");
        return category;
    }

    private CategoryEntity verifyIfExists(Integer id) throws CategoryNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
