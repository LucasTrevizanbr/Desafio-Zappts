package com.magic.place.api.controller;

import com.magic.place.api.domain.exception.NegocioException;
import com.magic.place.api.domain.model.Colecao;
import com.magic.place.api.domain.repository.ColecaoRepository;
import com.magic.place.api.representation.model.ColecaoDTO;
import com.magic.place.api.representation.form.FormColecao;
import com.magic.place.api.domain.service.CrudColecaoService;
import com.magic.place.api.specification.ColecaoSpecification;
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



@RestController
@RequestMapping("/api/colecoes")
public class ColecaoController {

    private final CrudColecaoService colecaoService;
    private final ColecaoRepository colecaoRepository;

    public ColecaoController(CrudColecaoService colecaoService, ColecaoRepository colecaoRepository) {
        this.colecaoService = colecaoService;
        this.colecaoRepository = colecaoRepository;
    }

    @GetMapping("/{idColecao}")
    public ResponseEntity<ColecaoDTO> buscarUmaColecaoEspecifica(@PathVariable Long idColecao){

       Colecao colecao = colecaoService.buscarPorId(idColecao);
       ColecaoDTO colecaoDTO = new ColecaoDTO(colecao);

        return ResponseEntity.ok(colecaoDTO);
    }

    @GetMapping("/por-nome-descricao/{filtroDinamico}")
    public ResponseEntity<Page<ColecaoDTO>> buscarColecoesPorNomeOuDesc(@PathVariable String filtroDinamico,
    @PageableDefault(sort = "nomeColecao", direction = Sort.Direction.ASC,page = 0, size = 5) Pageable paginacao){

        Page<Colecao> paginaColecao = colecaoRepository.findAll(Specification.
                        where(ColecaoSpecification.nomeDaColecaoParecidoCom(filtroDinamico)).
                        or(ColecaoSpecification.descDaColecaoParecidaCom(filtroDinamico)), paginacao);

        Page<ColecaoDTO> paginaDtoDeColecao = paginaColecao.map(ColecaoDTO::new);

        return ResponseEntity.ok(paginaDtoDeColecao);
    }

    @GetMapping("/todas-do-usuario/idUsuario={idUsuario}&pagina={pagina}&qtdPorPagina={qtdPorPagina}")
    public ResponseEntity<Page<ColecaoDTO>> buscarTodasAsColecoesDeUmUsuario(@PathVariable Long idUsuario, @PathVariable int pagina,
                                                                  @PathVariable int qtdPorPagina){

        Pageable paginacao = PageRequest.of(pagina, qtdPorPagina, Sort.Direction.ASC, "nomeColecao" );
        Page<Colecao> paginaColecao = colecaoRepository.findAllByDonoColecaoId(idUsuario, paginacao);
        Page<ColecaoDTO> paginaDtoDeColecao = paginaColecao.map(ColecaoDTO::new);

        return ResponseEntity.ok(paginaDtoDeColecao);
    }

    @GetMapping("/todas/pagina={pagina}&qtdPorPagina={qtdPorPagina}")
    public ResponseEntity<Page<ColecaoDTO>> buscarTodasAsColecoes(@PathVariable int pagina,
                                                                  @PathVariable int qtdPorPagina){

        Pageable paginacao = PageRequest.of(pagina, qtdPorPagina, Sort.Direction.ASC, "nomeColecao" );
        Page<Colecao> paginaColecao = colecaoRepository.findAll(paginacao);
        Page<ColecaoDTO> paginaDtoDeColecao = paginaColecao.map(ColecaoDTO::new);

        return ResponseEntity.ok(paginaDtoDeColecao);
    }

    @PostMapping("/criar/{idUsuario}")
    public ResponseEntity<ColecaoDTO> criarColecao(@PathVariable Long idUsuario,
                                                   @RequestBody @Valid FormColecao formColecao){

        ColecaoDTO colecaoDTO = new ColecaoDTO(colecaoService.salvarColecao(formColecao, idUsuario));

        return ResponseEntity.status(HttpStatus.CREATED).body(colecaoDTO);
    }

    @PutMapping("/atualizar/idUsuario={idUsuario}&idColecao={idColecao}")
    public ResponseEntity<ColecaoDTO> criarColecao(@PathVariable Long idUsuario, @PathVariable Long idColecao,
                                                   @RequestBody @Valid FormColecao formColecao){

        ColecaoDTO colecaoDTO = new ColecaoDTO(colecaoService.atualizarColecao(idColecao, idUsuario, formColecao));

        return ResponseEntity.status(HttpStatus.CREATED).body(colecaoDTO);
    }

    @DeleteMapping("/deletar/idUsuario={idUsuario}&idColecao={idColecao}")
    public ResponseEntity<Void> deletarUmaColecao(@PathVariable Long idUsuario, @PathVariable Long idColecao){
        colecaoService.deletarColecao(idUsuario, idColecao);
        return ResponseEntity.noContent().build();
    }
}
