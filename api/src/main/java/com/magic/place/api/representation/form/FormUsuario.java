package com.magic.place.api.representation.form;

import com.magic.place.api.domain.model.Usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FormUsuario {

    @NotBlank @Size(max = 60)
    private String nome;

    @NotBlank @Size(max = 50) @Email
    private String email;

    @NotBlank @Size(max = 20)
    private String telefone;

    @NotBlank @Size(min = 8, max = 30)
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario converterParaEntidade() {
        Usuario usuario = new Usuario();
        usuario.setEmail(this.getEmail());
        usuario.setNome(this.getNome());
        usuario.setSenha(this.getSenha());
        usuario.setTelefone(this.getTelefone());

        return usuario;
    }
}
