package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.ImageDTO;
import com.dh.digitalbooking.dto.ProductDTORequest;
import com.dh.digitalbooking.dto.ProductDTOResponse;
import com.dh.digitalbooking.entities.*;
import com.dh.digitalbooking.exceptions.CategoryNotFoundException;
import com.dh.digitalbooking.exceptions.CityNotFoundException;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import com.dh.digitalbooking.services.impl.*;
import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.NominatimClient;
import fr.dudie.nominatim.model.Address;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class ProductController {

    private final String endpointUrl = "https://nominatim.openstreetmap.org/";
    private final ProductServiceImpl productService;
    private final CategoryServiceImpl categoryService;
    private final CharacteristicServiceImpl characteristicService;
    private final CityServiceImpl cityService;
    private final UserServiceImpl userService;

    @Autowired
    public ProductController(ProductServiceImpl productService, CategoryServiceImpl categoryService,
                             CharacteristicServiceImpl characteristicService, CityServiceImpl cityService,
                             UserServiceImpl userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTOResponse> findAll(Pageable pageable){
        Page<ProductEntity> productsList = productService.findAll(pageable);
        return productsList.map(ProductDTOResponse::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTOResponse save(@RequestBody @Valid ProductDTORequest productDTORequest) throws
            CategoryNotFoundException, CityNotFoundException, UserNotFoundException, IOException {

        ProprietorEntity proprietor = (ProprietorEntity) userService.findById(productDTORequest.getProprietorId());
        Set<ImageEntity> imagesSet = new HashSet<>();
        Set<CharacteristicEntity> characteristicsSet = new HashSet<>();
        CityEntity city = cityService.findById(productDTORequest.getCityId());
        CategoryEntity category = categoryService.findById(productDTORequest.getCategoryId());

        NominatimClient nominatimClient = new JsonNominatimClient(HttpClientBuilder.create().build(), "thays.s.gama@gmail.com");

        if(productDTORequest.getLatitude() == null || productDTORequest.getLongitude() == null){
            final List<Address> addresses = nominatimClient.search(productDTORequest.getAddress()+" "+city.getName());
            productDTORequest.setLatitude(addresses.get(0).getLatitude());
            productDTORequest.setLongitude(addresses.get(0).getLongitude());
        }

        productDTORequest.getCharacteristicIds().forEach(id -> {
                CharacteristicEntity characteristic = characteristicService.findByIdOrNull(id);
                if(characteristic!=null) characteristicsSet.add(characteristic);
        });

        ProductEntity product = new ProductEntity(productDTORequest.getName(), productDTORequest.getDescription(),
                productDTORequest.getAddress(), productDTORequest.getLatitude(), productDTORequest.getLongitude(),
                productDTORequest.getGeneralRules(), productDTORequest.getSafetyRules(), productDTORequest.getCancellationRules(),
                characteristicsSet, imagesSet, category, city,proprietor);

        imagesSet = productDTORequest.getImageList().stream()
                .map(img -> new ImageEntity(img.getTitle(), img.getUrl(), product))
                .collect(Collectors.toSet());

        product.setImages(imagesSet);

        return new ProductDTOResponse(productService.save(product));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTOResponse findById(@PathVariable Integer id) throws ProductNotFoundException {
        return new ProductDTOResponse(productService.findById(id));
    }

    @GetMapping("/{id}/images")
    @ResponseStatus(HttpStatus.OK)
    public Page<ImageDTO> findAllImagesOfProduct(@PathVariable Integer id, Pageable pageable) throws ProductNotFoundException {
        Page<ImageEntity> imagesList = productService.findAllProductImages(id,pageable);
        return imagesList.map(ImageDTO::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) throws ProductNotFoundException {
        productService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDTOResponse update(@RequestBody @Valid ProductDTORequest productDTORequest) throws
            CategoryNotFoundException, CityNotFoundException, ProductNotFoundException, IOException {
        ProductEntity product = productService.findById(productDTORequest.getId());
        Set<ImageEntity> imagesSet;
        Set<CharacteristicEntity> characteristicsSet = new HashSet<>();
        CityEntity city = cityService.findById(productDTORequest.getCityId());
        CategoryEntity category = categoryService.findById(productDTORequest.getCategoryId());

        NominatimClient nominatimClient = new JsonNominatimClient(HttpClientBuilder.create().build(), "thays.s.gama@gmail.com");

        if(productDTORequest.getLatitude() == null || productDTORequest.getLongitude() == null){
            final List<Address> addresses = nominatimClient.search(productDTORequest.getAddress()+" "+city.getName());
            productDTORequest.setLatitude(addresses.get(0).getLatitude());
            productDTORequest.setLongitude(addresses.get(0).getLongitude());
        }

        productDTORequest.getCharacteristicIds().forEach(id -> {
            CharacteristicEntity characteristic = characteristicService.findByIdOrNull(id);
            if(characteristic!=null) characteristicsSet.add(characteristic);
        });

        imagesSet = productDTORequest.getImageList().stream()
                .map(img -> new ImageEntity(img.getId(), img.getTitle(), img.getUrl(), product))
                .collect(Collectors.toSet());

        product.setName(productDTORequest.getName());
        product.setDescription(productDTORequest.getDescription());
        product.setAddress(productDTORequest.getAddress());
        product.setLatitude(productDTORequest.getLatitude());
        product.setLongitude(productDTORequest.getLongitude());
        product.setGeneralRules(productDTORequest.getGeneralRules());
        product.setSafetyRules(productDTORequest.getSafetyRules());
        product.setCancellationRules(productDTORequest.getCancellationRules());
        product.setCharacteristics(characteristicsSet);
        product.setCity(city);
        product.setCategory(category);
        product.setImages(imagesSet);

        return new ProductDTOResponse(productService.update(product));
    }

    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTOResponse> findAllByCategory(@PathVariable Integer id, Pageable pageable) throws CategoryNotFoundException {
        CategoryEntity category = categoryService.findById(id);
        Page<ProductEntity> productsList = productService.findAllByCategory(category, pageable);
        return productsList.map(ProductDTOResponse::new);
    }

    @GetMapping("/city/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTOResponse> findAllByCity(@PathVariable Integer id, Pageable pageable) throws CityNotFoundException {
        CityEntity city = cityService.findById(id);
        Page<ProductEntity> productsList = productService.findAllByCity(city, pageable);
        return productsList.map(ProductDTOResponse::new);
    }

    @GetMapping("/city/{idCity}/category/{idCategory}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTOResponse> findAllByCategoryAndByCity(@PathVariable Integer idCity, @PathVariable Integer idCategory, Pageable pageable)
            throws CategoryNotFoundException, CityNotFoundException {
        CategoryEntity category = categoryService.findById(idCategory);
        CityEntity city = cityService.findById(idCity);
        Page<ProductEntity> productsList = productService.findAllByCityAndCategory(city,category, pageable);
        return productsList.map(ProductDTOResponse::new);
    }

    @GetMapping("/city/{cityId}/date/{checkIn}/{checkOut}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTOResponse> findAllByCityAndDate(@PathVariable Integer cityId, @PathVariable String checkIn, @PathVariable  String checkOut, Pageable pageable) throws CityNotFoundException {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        Page<ProductEntity> productsList = productService.findAllByCityAndDate(cityId, checkInDate,checkOutDate, pageable);
        return productsList.map(ProductDTOResponse::new);
    }

    @GetMapping("/date/{checkIn}/{checkOut}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTOResponse> findAllByDate(@PathVariable String checkIn, @PathVariable  String checkOut, Pageable pageable) throws CityNotFoundException {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        Page<ProductEntity> productsList = productService.findAllByDate(checkInDate,checkOutDate, pageable);
        return productsList.map(ProductDTOResponse::new);
    }

    @GetMapping("/proprietor/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTOResponse> findAllByProprietor(@PathVariable Integer id, Pageable pageable) throws UserNotFoundException {
        ProprietorEntity proprietor = (ProprietorEntity) userService.findById(id);
        Page<ProductEntity> productsList = productService.findAllByProprietor(proprietor, pageable);
        return productsList.map(ProductDTOResponse::new);
    }

}
