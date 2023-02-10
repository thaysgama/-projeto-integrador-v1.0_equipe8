package com.dh.digitalbooking.repository;

import com.dh.digitalbooking.entities.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(value = "classpath:application-test.properties")
@Sql(scripts = {"/data_test_categories.sql"})
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void saveShouldReturnSuccess() {
        CategoryEntity category = new CategoryEntity("Ecoturismo","destino na natureza", "www.image.com/eco");
        CategoryEntity categorySaved = categoryRepository.save(category);
        assertNotNull(categorySaved.getId());
    }

    @Test
    void updateShouldReturnSuccess() {
        int firstElementId = categoryRepository.findAll().get(0).getId();
        CategoryEntity category = new CategoryEntity("Ecoturismo","destino na natureza", "www.image.com/eco");
        category.setId(firstElementId);
        CategoryEntity categorySaved = categoryRepository.save(category);
        assertEquals(category.getId(), categorySaved.getId());
        assertEquals(category.getTitle(), categorySaved.getTitle());
        assertEquals(category.getDescription(), categorySaved.getDescription());
    }

    @Test
    void findByIdShouldReturnSuccess() {
        int firstElementId = categoryRepository.findAll().get(0).getId();
        CategoryEntity categoryFound = categoryRepository.findById(firstElementId).get();
        assertNotNull(categoryFound.getId());
    }

    @Test
    void findAllShouldReturnSuccess() {
        int listSize = categoryRepository.findAll().size();
        assertEquals(3, listSize);
    }

    @Test
    void deleteByIdShouldReturnSuccess() {
        int firstElementId = categoryRepository.findAll().get(0).getId();
        int listSize = categoryRepository.findAll().size();
        categoryRepository.deleteById(firstElementId);
        assertEquals(2, listSize-1);
    }

}