package com.magic.place.api.domain.exception;

public class UsuarioJaCadastradoException extends RuntimeException {

    public UsuarioJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
