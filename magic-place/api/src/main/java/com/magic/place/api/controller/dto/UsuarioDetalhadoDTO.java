package com.magic.place.api.controller.dto;

import com.magic.place.api.domain.model.Usuario;

public class UsuarioDetalhadoDTO {

    private final Long id;

    private final String nome;

    private final String email;

    private final String telefone;


    public UsuarioDetalhadoDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}
