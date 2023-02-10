package com.dh.digitalbooking.entities;

import com.dh.digitalbooking.dto.FavoriteProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos_favoritos")
public class FavoriteProductEntity {

    @EmbeddedId
    private FavoriteProductPK id;

    public void setProduct(ProductEntity product){
        id.setProduct(product);
    }

    public void setUser(UserEntity user){
        id.setUser(user);
    }


}
