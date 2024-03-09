package com.rentspace.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CadastroDTO {
    private String nome;
    private String email;
    private String senha;
}
