package com.dh.digitalbooking.entities;

import com.dh.digitalbooking.dto.CategoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "categorias")
public class CategoryEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo")
    private String title;

    @Column(name = "descricao")
    private String description;

    @Column(name = "url_imagem")
    private String urlImage;

    @OneToMany (mappedBy = "category",fetch = FetchType.LAZY)
    private Set<ProductEntity> products;

    public CategoryEntity(CategoryDTO categoryDTO) {
        this.id = categoryDTO.getId();
        this.title = categoryDTO.getTitle();
        this.description = categoryDTO.getDescription();
        this.urlImage = categoryDTO.getUrlImage();
    }

    public CategoryEntity(String title, String description, String urlImage) {
        this.title = title;
        this.description = description;
        this.urlImage = urlImage;
    }
}
