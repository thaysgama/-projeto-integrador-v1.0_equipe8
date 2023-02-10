package com.dh.digitalbooking.dto;

import com.dh.digitalbooking.entities.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductDTOResponse {

    private Integer id;
    private String name;
    private String description;
    private Double score;
    private String address;
    private Double latitude;
    private Double longitude;
    private String generalRules;
    private String safetyRules;
    private String cancellationRules;
    private Set<CharacteristicDTO> characteristics;
    private Set <ImageDTO> images;
    private CityDTO city;
    private CategoryDTO category;
    private Set<BookingDTO> bookings;
    private Set<RatingDTO> ratings;
    private Integer proprietorId;

    public ProductDTOResponse(ProductEntity product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.score = product.getScore();
        this.address = product.getAddress();
        this.latitude = product.getLatitude();
        this.longitude = product.getLongitude();
        this.generalRules = product.getGeneralRules();
        this.safetyRules = product.getSafetyRules();
        this.cancellationRules = product.getCancellationRules();
        this.characteristics = product.getCharacteristics().stream().map(CharacteristicDTO::new).collect(Collectors.toSet());
        this.images = product.getImages().stream().map(ImageDTO::new).collect(Collectors.toSet());
        this.city = new CityDTO(product.getCity());
        this.category = new CategoryDTO(product.getCategory());
        this.bookings = product.getBookings().stream().map(BookingDTO::new).collect(Collectors.toSet());
        this.ratings = product.getRatings().stream().map(RatingDTO::new).collect(Collectors.toSet());
        this.proprietorId = product.getProprietor().getId();
    }
}
