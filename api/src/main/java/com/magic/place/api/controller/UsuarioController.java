package com.magic.place.api.controller;

import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.service.usuario.AutenticacaoService;
import com.magic.place.api.representation.form.FormUsuario;
import com.magic.place.api.representation.form.LoginForm;
import com.magic.place.api.representation.model.TokenDto;
import com.magic.place.api.representation.model.UsuarioDetalhadoDTO;
import com.magic.place.api.domain.repository.UsuarioRepository;
import com.magic.place.api.domain.service.usuario.CadastroUsuarioService;
import com.magic.place.api.security.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final CadastroUsuarioService cadastroUsuarioService;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final AutenticacaoService autenticacaoService;

    public UsuarioController(UsuarioRepository usuarioRepository, CadastroUsuarioService cadastroUsuarioService,
                             AuthenticationManager authManager, TokenService tokenService,
                             AutenticacaoService autenticacaoService) {
        this.usuarioRepository = usuarioRepository;
        this.cadastroUsuarioService = cadastroUsuarioService;
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDetalhadoDTO> criarUsuario(@RequestBody @Valid FormUsuario formUsuario){

        UsuarioDetalhadoDTO usuarioDto = new UsuarioDetalhadoDTO(cadastroUsuarioService.salvar(formUsuario));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioDetalhadoDTO> logar(@RequestBody LoginForm loginForm){

        Usuario usuario = (Usuario) autenticacaoService.loadUserByUsername(loginForm.getEmail());
        cadastroUsuarioService.validarSenha(usuario, loginForm.getSenha());

        try{
            UsernamePasswordAuthenticationToken dadosLogin = loginForm.converter();
            Authentication usuarioAutenticado = authManager.authenticate(dadosLogin);

            String token = tokenService.gerarToken(usuarioAutenticado);

            UsuarioDetalhadoDTO usuarioDto = new UsuarioDetalhadoDTO(usuario);
            usuarioDto.setTokenDto(new TokenDto(token, "Bearer "));

            return ResponseEntity.ok(usuarioDto);
        }catch (AuthenticationException ex){
            return ResponseEntity.badRequest().build();
        }

    }

}
