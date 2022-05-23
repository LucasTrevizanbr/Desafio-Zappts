package com.magic.place.api.controller;

import com.magic.place.api.controller.dto.CartaDTO;
import com.magic.place.api.controller.dto.CartaForm;
import com.magic.place.api.domain.service.CrudCartaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cartas")
public class CartaController {

    private CrudCartaService cartaService;

    public CartaController(CrudCartaService cartaService) {
        this.cartaService = cartaService;
    }

    @PostMapping("/criar-carta/{idUsuario}")
    public ResponseEntity<CartaDTO> registrarCarta(@PathVariable Long idUsuario,
                                                   @RequestBody @Valid CartaForm cartaForm){

        CartaDTO cartaDTO = new CartaDTO(cartaService.salvarCarta(cartaForm, idUsuario));

        return ResponseEntity.status(HttpStatus.CREATED).body(cartaDTO);
    }
}
