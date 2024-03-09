package com.rentspace.service;

import com.rentspace.DTO.CadastroDTO;
import com.rentspace.entity.Cadastro;
import com.rentspace.repository.CadastroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {

    private CadastroRepository cadastroRepository;

    @Autowired
    public CadastroService(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }

    public Cadastro salvaNovoCadastro(CadastroDTO cadastroDTO){
        Cadastro cadastro = new Cadastro(cadastroDTO.getNome(), cadastroDTO.getEmail(), cadastroDTO.getSenha());
        return cadastroRepository.save(cadastro);

    }
}
