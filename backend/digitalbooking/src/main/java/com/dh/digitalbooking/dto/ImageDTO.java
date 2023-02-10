package com.dh.digitalbooking.dto;

import com.dh.digitalbooking.entities.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private Integer id;

    @NotEmpty(message = "Title should not be empty.")
    private String title;

    @NotEmpty(message = "Url should not be empty.")
    private String url;


    public ImageDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public ImageDTO(ImageEntity image) {
        this.id = image.getId();
        this.title = image.getTitle();
        this.url = image.getUrl();
    }
}
