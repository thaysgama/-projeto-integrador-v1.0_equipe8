package com.dh.digitalbooking.dto;

import com.dh.digitalbooking.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Integer id;

    @NotEmpty
    @Size(min = 2, message = "Title should have at least 2 characters.")
    private String title;

    @NotEmpty(message = "Description should not be empty.")
    private String description;

    @NotEmpty(message = "Image url should not be empty.")
    private String urlImage;

    public CategoryDTO(CategoryEntity category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.description = category.getDescription();
        this.urlImage = category.getUrlImage();
    }

    public CategoryDTO(String title, String description, String urlImage) {
        this.title = title;
        this.description = description;
        this.urlImage = urlImage;
    }
}
