package com.magic.place.api.domain.service;

import com.magic.place.api.representation.form.FormUsuario;
import com.magic.place.api.domain.exception.NegocioException;
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

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() ->  new NegocioException("Não existe usuário com o ID informado!"));
    }

    @Transactional
    public Usuario salvar(FormUsuario formUsuario){

        if(usuarioRepository.existsByEmail(formUsuario.getEmail())){
            throw new NegocioException("Esse email já esta cadastrado no sistema");
        }

        Usuario usuario = formUsuario.converterParaEntidade();
        return usuarioRepository.save(usuario);
    }
}
