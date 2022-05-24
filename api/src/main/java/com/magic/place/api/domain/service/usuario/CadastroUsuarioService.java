package com.magic.place.api.domain.service.usuario;

import com.magic.place.api.representation.form.FormUsuario;
import com.magic.place.api.domain.exception.NegocioException;
import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public CadastroUsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
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
        usuario.setSenha(encoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }


    public void validarSenha(Usuario usuario, String senha) {
        boolean validar = encoder.matches(senha, usuario.getSenha());
        if(!validar){
            throw new NegocioException("Senha está incorreta!");
        }
    }
}
