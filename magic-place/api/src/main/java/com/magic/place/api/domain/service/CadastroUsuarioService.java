package com.magic.place.api.domain.service;

import com.magic.place.api.controller.dto.FormUsuario;
import com.magic.place.api.domain.exception.UsuarioJaCadastradoException;
import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {

    private UsuarioRepository usuarioRepository;

    public CadastroUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario salvar(FormUsuario formUsuario){

        if(usuarioRepository.existsByEmail(formUsuario.getEmail())){
            throw new UsuarioJaCadastradoException("Esse email já esta cadastrado no sistema");
        }

        Usuario usuario = formUsuario.converterParaEntidade();
        return usuarioRepository.save(usuario);
    }
}
