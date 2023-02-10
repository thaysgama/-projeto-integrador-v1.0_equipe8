package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.entities.RatingEntity;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.repository.ProductRepository;
import com.dh.digitalbooking.repository.RatingRepository;
import com.dh.digitalbooking.services.IRatingService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements IRatingService<RatingEntity> {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RatingRepository ratingRepository;

    private final Logger log = Logger.getLogger(RatingServiceImpl.class);

    @Override
    public ProductEntity save(RatingEntity ratingEntity) throws ProductNotFoundException {
        log.info("Saving rating.");

        ratingRepository.saveAndFlush(ratingEntity);

        ProductEntity product = productRepository.findById(ratingEntity.getId().getProduct().getId())
                .orElseThrow(()->new ProductNotFoundException(ratingEntity.getId().getProduct().getId()));

        double sum = 0.0;
        for (RatingEntity rating: product.getRatings()) {
            sum += rating.getScore();
        }
        double avg = sum / product.getRatings().size();
        product.setScore(avg);

        productRepository.save(product);
        return product;

    }
}
