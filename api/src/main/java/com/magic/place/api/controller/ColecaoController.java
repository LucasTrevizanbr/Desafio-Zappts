package com.magic.place.api.controller;

import com.magic.place.api.representation.model.ColecaoDTO;
import com.magic.place.api.representation.form.FormColecao;
import com.magic.place.api.domain.service.CrudColecaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/colecoes")
public class ColecaoController {

    private final CrudColecaoService colecaoService;

    public ColecaoController(CrudColecaoService colecaoService) {
        this.colecaoService = colecaoService;
    }


    @PostMapping("/criar-colecao")
    public ResponseEntity<ColecaoDTO> criarColecao(@RequestBody @Valid FormColecao formColecao){

        ColecaoDTO colecaoDTO = new ColecaoDTO(colecaoService.salvarColecao(formColecao));

        return ResponseEntity.status(HttpStatus.CREATED).body(colecaoDTO);
    }
}
