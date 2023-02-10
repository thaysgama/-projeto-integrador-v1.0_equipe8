package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.FavoriteProductEntity;
import com.dh.digitalbooking.entities.FavoriteProductPK;
import com.dh.digitalbooking.entities.UserEntity;
import com.dh.digitalbooking.repository.FavoriteProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static com.dh.digitalbooking.utils.FavoriteProductUtils.favoriteProductBuilder;
import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.UserUtils.userBuilder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class FavoriteProductServiceImplTest {

    @Mock
    private FavoriteProductRepository favoriteProductRepository;

    @Autowired
    @InjectMocks
    private FavoriteProductServiceImpl favoriteProductService;


    @Test
    void testGivenFavoriteProductThenReturnSuccessfullySavedObject() {
        UserEntity user = userBuilder();
        user.setFavoriteProducts(Set.of(favoriteProductBuilder()));

        FavoriteProductEntity favoriteProductInDataBase = favoriteProductBuilder();
        when(favoriteProductRepository.saveAndFlush(any(FavoriteProductEntity.class))).thenReturn(favoriteProductInDataBase);

        FavoriteProductEntity favoriteProduct = new FavoriteProductEntity(new FavoriteProductPK());
        favoriteProduct.setProduct(productBuilder());
        favoriteProduct.setUser(userBuilder());
        UserEntity userOfFavoriteProduct = favoriteProductService.save(favoriteProduct);
        assertNotNull(userOfFavoriteProduct);
    }


}