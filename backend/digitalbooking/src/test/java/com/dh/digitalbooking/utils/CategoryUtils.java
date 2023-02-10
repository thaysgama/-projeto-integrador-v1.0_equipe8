package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

public class CategoryUtils {

    public static CategoryEntity categoryBuilder(){
        CategoryEntity category = new CategoryEntity("Cidade","destino urbano", "www.image.com/urb");
        category.setId(1);
        return category;
    }

    public static Page<CategoryEntity> categoriesPageBuilder(){
        CategoryEntity category1 = new CategoryEntity("Cidade","destino urbano", "www.image.com/urb");
        CategoryEntity category2 = new CategoryEntity("Praia","destinos litorâneos", "www.image.com/praia");
        category1.setId(1);
        category2.setId(2);
        return new PageImpl<>(Arrays.asList(category1,category2));
    }
}
