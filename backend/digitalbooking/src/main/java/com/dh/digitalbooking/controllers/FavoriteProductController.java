package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.FavoriteProductDTO;
import com.dh.digitalbooking.dto.UserDTOResponse;
import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.entities.FavoriteProductEntity;
import com.dh.digitalbooking.entities.FavoriteProductPK;
import com.dh.digitalbooking.entities.UserEntity;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import com.dh.digitalbooking.repository.ProductRepository;
import com.dh.digitalbooking.repository.UserRepository;
import com.dh.digitalbooking.services.impl.FavoriteProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favoriteProduct")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class FavoriteProductController {

    private final FavoriteProductServiceImpl favoriteProductService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FavoriteProductController(FavoriteProductServiceImpl favoriteProductService, UserRepository userRepository
            , ProductRepository productRepository) {
        this.favoriteProductService = favoriteProductService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PutMapping
    public UserDTOResponse save(@RequestBody FavoriteProductDTO favoriteProductDTO) throws UserNotFoundException, ProductNotFoundException {
        UserEntity user = userRepository.findById(favoriteProductDTO.getUserId())
                .orElseThrow(()->new UserNotFoundException(favoriteProductDTO.getUserId()));

        ProductEntity product = productRepository.findById(favoriteProductDTO.getProductId())
                .orElseThrow(()->new ProductNotFoundException(favoriteProductDTO.getProductId()));

        FavoriteProductEntity favoriteProduct = new FavoriteProductEntity(new FavoriteProductPK(product, user));

        return new UserDTOResponse(favoriteProductService.save(favoriteProduct));
    }
}
