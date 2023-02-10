package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.FavoriteProductEntity;
import com.dh.digitalbooking.entities.UserEntity;
import com.dh.digitalbooking.repository.FavoriteProductRepository;
import com.dh.digitalbooking.services.IFavoriteProductService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteProductServiceImpl implements IFavoriteProductService<FavoriteProductEntity> {

    @Autowired
    private FavoriteProductRepository favoriteProductRepository;

    private final Logger log = Logger.getLogger(FavoriteProductServiceImpl.class);

    @Override
    public UserEntity save(FavoriteProductEntity favoriteProductEntity) {
        log.info("Saving favorite product.");

        favoriteProductRepository.saveAndFlush(favoriteProductEntity);

        return favoriteProductEntity.getId().getUser();

    }
}
