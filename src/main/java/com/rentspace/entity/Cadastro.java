package com.rentspace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cadastro {

    private String nome;
    private String email;
    private String senha;
    private String endereço;
    private String telefone;
    @Id
    private Long id;

    public Cadastro(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}
