package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Set;

import static com.dh.digitalbooking.utils.CategoryUtils.categoryBuilder;
import static com.dh.digitalbooking.utils.CharacteristicUtils.characteristicBuilder;
import static com.dh.digitalbooking.utils.CityUtils.cityBuilder;
import static com.dh.digitalbooking.utils.UserUtils.proprietorBuilder;

public class ProductUtils {

    public static ProductEntity productBuilder(){
        CharacteristicEntity characteristic = characteristicBuilder();
        CityEntity city = cityBuilder();
        CategoryEntity category = categoryBuilder();
        ProprietorEntity proprietor = proprietorBuilder();
        ProductEntity product = new ProductEntity();
        ImageEntity image = new ImageEntity("Sala-1", "www.image.com/imagem1", product);
        image.setId(1);
        product = new ProductEntity("Flat", "Esta acomodação está a beira mar.", "Rua do amor, 88",
                -3.71852584954214,-38.520076257714315,"Não aceita fumantes",
                "Comprovante de vacinação","Grátis", Set.of(characteristic), Set.of(image),  category, city,proprietor);
        product.setId(1);
        return product;
    }

    public static ProductEntity productBuilderWithoutId(){
        CharacteristicEntity characteristic = new CharacteristicEntity("televisão","tv");
        characteristic.setId(1);
        CityEntity city = new CityEntity("Caucaia", "CE", "Brasil");
        city.setId(1);
        CategoryEntity category = new CategoryEntity("Ecoturismo","destinos na natureza", "www.image.com/natureza");
        category.setId(1);
        ProprietorEntity proprietor = proprietorBuilder();
        ProductEntity product = new ProductEntity();
        ImageEntity image = new ImageEntity("Quarto-1", "www.image.com/imagem1", product);
        product = new ProductEntity("Apt", "Esta acomodação está a beira do rio.","Rua da alegria, 88",
                -3.71852584954214,-38.520076257714315,"Não aceita fumantes",
                "Comprovante de vacinação","Grátis", Set.of(characteristic), Set.of(image), category,city,proprietor);
        return product;
    }


    public static Page<ProductEntity> productsPageBuilder(){
        ProductEntity productBuilder = productBuilder();

        ProductEntity productBuilder2 = productBuilderWithoutId();
        productBuilder2.getImages().forEach(image -> image.setId(1));
        productBuilder2.setId(1);

        return new PageImpl<>(Arrays.asList(productBuilder, productBuilder2));
    }
}
