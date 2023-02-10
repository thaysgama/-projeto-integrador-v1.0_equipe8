package com.dh.digitalbooking.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RatingPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity user;

}
