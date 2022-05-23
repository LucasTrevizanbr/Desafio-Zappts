package com.magic.place.api.controller;

import com.magic.place.api.controller.dto.FormUsuario;
import com.magic.place.api.controller.dto.UsuarioDetalhadoDTO;
import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.repository.UsuarioRepository;
import com.magic.place.api.domain.service.CadastroUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;
    private CadastroUsuarioService cadastroUsuarioService;

    public UsuarioController(UsuarioRepository usuarioRepository, CadastroUsuarioService cadastroUsuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.cadastroUsuarioService = cadastroUsuarioService;
    }

    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<UsuarioDetalhadoDTO> criarUsuario(@RequestBody @Valid FormUsuario formUsuario){

        UsuarioDetalhadoDTO usuarioDto = new UsuarioDetalhadoDTO(cadastroUsuarioService.salvar(formUsuario));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
    }



}
