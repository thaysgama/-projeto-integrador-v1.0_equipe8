package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.ProductDTOResponse;
import com.dh.digitalbooking.dto.RatingDTO;
import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.entities.RatingEntity;
import com.dh.digitalbooking.entities.RatingPK;
import com.dh.digitalbooking.entities.UserEntity;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import com.dh.digitalbooking.repository.ProductRepository;
import com.dh.digitalbooking.repository.UserRepository;
import com.dh.digitalbooking.services.impl.RatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class RatingController {

    private final RatingServiceImpl ratingService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public RatingController(RatingServiceImpl ratingService, UserRepository userRepository,
                            ProductRepository productRepository) {
        this.ratingService = ratingService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PutMapping
    public ProductDTOResponse save(@RequestBody RatingDTO ratingDTO) throws UserNotFoundException, ProductNotFoundException {
        UserEntity user = userRepository.findById(ratingDTO.getUserId())
                .orElseThrow(()->new UserNotFoundException(ratingDTO.getUserId()));

        ProductEntity product = productRepository.findById(ratingDTO.getProductId())
                .orElseThrow(()->new ProductNotFoundException(ratingDTO.getProductId()));

        RatingEntity rating = new RatingEntity(new RatingPK(product, user), ratingDTO.getScore());

        return new ProductDTOResponse(ratingService.save(rating));
    }
}
