package com.magic.place.api.controller;

import com.magic.place.api.domain.exception.NegocioException;
import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.domain.repository.CartaRepository;
import com.magic.place.api.domain.repository.ColecaoRepository;
import com.magic.place.api.representation.model.CartaDTO;
import com.magic.place.api.representation.form.CartaForm;
import com.magic.place.api.domain.service.CrudCartaService;
import com.magic.place.api.specification.CartaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/cartas")
public class CartaController {

    private final CrudCartaService cartaService;
    private final ColecaoRepository colecaoRepository;
    private final CartaRepository cartaRepository;

    public CartaController(CrudCartaService cartaService, ColecaoRepository colecaoRepository,
                           CartaRepository cartaRepository) {
        this.cartaService = cartaService;
        this.colecaoRepository = colecaoRepository;
        this.cartaRepository = cartaRepository;
    }

    @GetMapping("/da-colecao/idColecao={id}&pagina={pg}&qtdPagina={qtd}&ordenarPor={ordenacao}")
    public ResponseEntity<Page<CartaDTO>> buscarCartasDeUmaColecao(@PathVariable Long id,
                                                                   @PathVariable int pg, @PathVariable int qtd,
                                                                   @PathVariable String ordenacao){

        if(!colecaoRepository.existsById(id)){
            throw new NegocioException("Não existe uma coleção com o ID informado!");
        }

        Pageable paginacao = PageRequest.of(pg, qtd, Sort.Direction.ASC, ordenacao );
        Page<Carta> cartasDaColecao = cartaRepository.findAllByColecaoDaCartaId(id, paginacao);
        Page<CartaDTO> cartas = cartasDaColecao.map(CartaDTO::new);

        return ResponseEntity.ok(cartas);

    }

    @GetMapping("/por-nome-edicao/{filtroDinamico}")
    public ResponseEntity<Page<CartaDTO>> buscarCartasPorNomeOuEdicao(@PathVariable String filtroDinamico,
      @PageableDefault(sort = "nomeCarta", direction = Sort.Direction.ASC,page = 0, size = 5) Pageable paginacao){

        Page<Carta> paginaCartas = cartaRepository.findAll(Specification.
                where(CartaSpecification.nomeDaCartaParecidoCom(filtroDinamico)).
                or(CartaSpecification.edicaoDaCartaParecidaCom(filtroDinamico)), paginacao);

        Page<CartaDTO> paginaCartaDto = paginaCartas.map(CartaDTO::new);

        return ResponseEntity.ok(paginaCartaDto);
    }

    @PostMapping("/criar-carta/idUsuario={idUsuario}&idColecao={idColecao}")
    public ResponseEntity<CartaDTO> registrarCarta(@PathVariable Long idUsuario, @PathVariable Long idColecao,
                                                   @RequestBody @Valid CartaForm cartaForm){

        CartaDTO cartaDTO = new CartaDTO(cartaService.salvarCarta(cartaForm, idUsuario, idColecao));

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
