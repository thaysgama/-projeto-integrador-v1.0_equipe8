package com.dh.digitalbooking.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "confirmacao_token")
public class ConfirmationToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @Column(name = "expira_em")
    private LocalDateTime expiresAt;

    @Column(name = "confirmado_em")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity user;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             UserEntity user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
