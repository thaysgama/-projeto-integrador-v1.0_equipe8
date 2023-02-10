package com.dh.digitalbooking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "produtos")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "score")
    private Double score;

    @Column(name = "endereco")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "regras_gerais")
    private String generalRules;

    @Column(name = "regras_seguranca")
    private String safetyRules;

    @Column(name = "regras_cancelamento")
    private String cancellationRules;


    @ManyToMany (fetch = FetchType.LAZY, cascade ={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "produtos_caracteristicas",
            joinColumns = @JoinColumn(name = "id_produto"),
            inverseJoinColumns = @JoinColumn(name = "id_caracteristica")
    )
    private Set<CharacteristicEntity> characteristics  = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set <ImageEntity> images  = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cidade", nullable = false)
    private CityEntity city;

    @ManyToOne
    @JoinColumn(name = "id_proprietario")
    private ProprietorEntity proprietor;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<BookingEntity> bookings  = new HashSet<>();

    @OneToMany(mappedBy = "id.product",  cascade = CascadeType.REMOVE)
    private Set<RatingEntity> ratings = new HashSet<>();

    @OneToMany(mappedBy = "id.product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<FavoriteProductEntity> favoriteProducts;


    public ProductEntity(String name, String description, String address, Double latitude,
                         Double longitude, String generalRules, String safetyRules, String cancellationRules,
                         Set<CharacteristicEntity> characteristics, Set<ImageEntity> images, CategoryEntity category,
                         CityEntity city, ProprietorEntity proprietor) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.generalRules = generalRules;
        this.safetyRules = safetyRules;
        this.cancellationRules = cancellationRules;
        this.characteristics = characteristics;
        this.images = images;
        this.category = category;
        this.city = city;
        this.proprietor = proprietor;
    }
}
