package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.CategoryEntity;
import com.dh.digitalbooking.exceptions.CategoryNotFoundException;
import com.dh.digitalbooking.repository.CategoryRepository;
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

import static com.dh.digitalbooking.utils.CategoryUtils.categoriesPageBuilder;
import static com.dh.digitalbooking.utils.CategoryUtils.categoryBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Autowired
    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    void findAllShouldReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        when(categoryRepository.findAll(pageable)).thenReturn(categoriesPageBuilder());
        Page<CategoryEntity> page = categoryService.findAll(pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenCategoryThenReturnSuccessfullySavedObject() {
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryBuilder());

        CategoryEntity category = new CategoryEntity("Cidade","destino urbano", "www.image.com/urb");
        CategoryEntity savedCategory = categoryService.save(category);
        assertNotNull(savedCategory.getId());
    }

    @Test
    void testGivenValidIdThenReturnSuccessfullyFoundObject() throws CategoryNotFoundException {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryBuilder()));

        CategoryEntity expectedCategory = categoryBuilder();
        CategoryEntity category = categoryService.findById(1);
        assertEquals(expectedCategory.getId(), category.getId());
        assertEquals(expectedCategory.getTitle(), category.getTitle());
        assertEquals(expectedCategory.getDescription(), category.getDescription());
        assertEquals(expectedCategory.getUrlImage(), category.getUrlImage());
    }

    @Test
    void testGivenValidCategoryThenReturnSuccessfullyUpdatedObject() throws CategoryNotFoundException {
        CategoryEntity categoryInDatabase = categoryBuilder();
        categoryInDatabase.setTitle("Praia");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryInDatabase));

        CategoryEntity expectedCategory = categoryBuilder();
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(expectedCategory);

        CategoryEntity category= categoryService.update(expectedCategory);

        assertEquals(expectedCategory.getId(), category.getId());
        assertEquals(expectedCategory.getTitle(), category.getTitle());
        assertEquals(expectedCategory.getDescription(), category.getDescription());
        assertEquals(expectedCategory.getUrlImage(), category.getUrlImage());
    }

    @Test
    void testGivenValidCategoryIdThenReturnSuccesOnDelete() throws CategoryNotFoundException {
        Integer deletedCategoryId = 1;
        CategoryEntity expectedCategoryToDelete = categoryBuilder();

        when(categoryRepository.findById(deletedCategoryId)).thenReturn(Optional.of(expectedCategoryToDelete));
        categoryService.deleteById(deletedCategoryId);

        verify(categoryRepository, times(1)).deleteById(deletedCategoryId);
    }
}