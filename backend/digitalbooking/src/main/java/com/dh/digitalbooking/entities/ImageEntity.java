package com.dh.digitalbooking.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "imagens")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo")
    private String title;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey (name = "fk_produto_img"))
    private ProductEntity product;

    public ImageEntity(String title, String url, ProductEntity product) {
        this.title = title;
        this.url = url;
        this.product = product;
    }

}
