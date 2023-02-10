package com.dh.digitalbooking.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avaliacoes")
public class RatingEntity {

    @EmbeddedId
    private RatingPK id;

    @Column(name = "score")
    private Integer score;


    public void setProduct(ProductEntity product){
        id.setProduct(product);
    }

    public void setUser(UserEntity user){
        id.setUser(user);
    }
}
