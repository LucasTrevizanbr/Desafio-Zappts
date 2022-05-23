package com.magic.place.api.controller;

import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.representation.model.CartaDTO;
import com.magic.place.api.representation.form.CartaForm;
import com.magic.place.api.domain.service.CrudCartaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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

    @PatchMapping("/atualizar-carta/idUsuario={idUsuario}&idCarta={idCarta}")
    public ResponseEntity<CartaDTO> atualizarCarta(@PathVariable Long idCarta, @PathVariable Long idUsuario,
                                                   @RequestBody Map<String, Object> propriedadesDaAtualizacao){

        CartaDTO cartaDTO = new CartaDTO(cartaService.atualizarCarta(idCarta, propriedadesDaAtualizacao, idUsuario));

        return ResponseEntity.ok(cartaDTO);
    }

    @DeleteMapping("/deletar-carta/idUsuario={idUsuario}&idCarta={idCarta}")
    public ResponseEntity<Void> deletarCarta(@PathVariable Long idUsuario, @PathVariable Long idCarta){

        cartaService.deletarCarta(idUsuario, idCarta);

        return ResponseEntity.noContent().build();
    }
}
