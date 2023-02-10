package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.*;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.repository.ImageRepository;
import com.dh.digitalbooking.repository.ProductRepository;
import com.dh.digitalbooking.services.IProductService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductServiceImpl implements IProductService<ProductEntity> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    private final Logger log = Logger.getLogger(ProductEntity.class);

    @Override
    public ProductEntity save(ProductEntity product) {
        ProductEntity productSaved = productRepository.save(product);
        log.info("Saving produto.");
        return productSaved;
    }

    @Override
    public ProductEntity update(ProductEntity product) throws ProductNotFoundException {
        verifyIfProductExists(product.getId());
        ProductEntity productSaved = productRepository.save(product);
        log.info("Updating product with id = "+product.getId()+".");
        return productSaved;
    }

    @Override
    public void deleteById(Integer id) throws ProductNotFoundException {
        ProductEntity product = verifyIfProductExists(id);
        log.info("Deleting product with id = "+id+".");
        productRepository.deleteById(id);
    }

    @Override
    public ProductEntity findById(Integer id) throws ProductNotFoundException {
        ProductEntity product = verifyIfProductExists(id);
        log.info("Finding product with id = "+id+".");
        return product;
    }

    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        log.info("Listing all products.");
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ImageEntity> findAllProductImages(Integer id, Pageable pageable) throws ProductNotFoundException {
        ProductEntity product = verifyIfProductExists(id);
        log.info("Listing all images of product.");
        return imageRepository.findAllImagesOfProduct(id, pageable);
    }

    @Override
    public Page<ProductEntity> findAllByCity(CityEntity city, Pageable pageable) {
        log.info("Listing all products of "+ city.getName()+".");
        return productRepository.findAllByCity(city, pageable);
    }

    @Override
    public Page<ProductEntity> findAllByCategory(CategoryEntity category, Pageable pageable) {
        log.info("Listing all products of category = "+ category.getTitle()+".");
        return productRepository.findAllByCategory(category, pageable);
    }

    @Override
    public Page<ProductEntity> findAllByCityAndCategory(CityEntity city, CategoryEntity category, Pageable pageable) {
        log.info("Listing all products of city = "+ city.getName()+" and category = "+category.getTitle()+".");
        return productRepository.findAllByCityAndCategory(city, category, pageable);
    }


    @Override
    public Page<ProductEntity> findAllByDate(LocalDate checkIn, LocalDate checkOut, Pageable pageable) {

        return productRepository.findAllByDate(checkIn,checkOut, pageable);
    }

    @Override
    public Page<ProductEntity> findAllByCityAndDate(Integer cityId, LocalDate checkIn, LocalDate checkOut, Pageable pageable) {

        return productRepository.findAllByCityAndDate(cityId,checkIn, checkOut, pageable);
    }

    @Override
    public Page<ProductEntity> findAllByProprietor(ProprietorEntity proprietor, Pageable pageable) {
        return productRepository.findAllByProprietor(proprietor, pageable);
    }

    private ProductEntity verifyIfProductExists(Integer id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


}
