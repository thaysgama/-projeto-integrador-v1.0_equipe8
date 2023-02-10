package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.entities.RatingEntity;
import com.dh.digitalbooking.entities.RatingPK;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.repository.ProductRepository;
import com.dh.digitalbooking.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.Set;

import static com.dh.digitalbooking.utils.RatingUtils.ratingBuilder;
import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.UserUtils.userBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private ProductRepository productRepository;

    @Autowired
    @InjectMocks
    private RatingServiceImpl ratingService;


    @Test
    void testGivenRatingThenReturnSuccessfullySavedObject() throws ProductNotFoundException {
        ProductEntity product = productBuilder();
        product.setRatings(Set.of(ratingBuilder()));

        RatingEntity ratingInDataBase = ratingBuilder();
        when(ratingRepository.saveAndFlush(any(RatingEntity.class))).thenReturn(ratingInDataBase);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(productBuilder()));

        RatingEntity rating = new RatingEntity(new RatingPK(), 5);
        rating.setProduct(productBuilder());
        rating.setUser(userBuilder());
        ProductEntity productOfRating = ratingService.save(rating);
        assertNotNull(productOfRating.getScore());
    }

}