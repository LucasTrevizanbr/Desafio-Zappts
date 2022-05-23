package com.magic.place.api.domain.service;

import com.detectlanguage.Result;
import com.detectlanguage.errors.APIError;
import com.magic.place.api.controller.dto.CartaForm;
import com.magic.place.api.domain.exception.NegocioException;
import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.domain.model.Colecao;
import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.repository.CartaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.detectlanguage.DetectLanguage;

@Service
public class CrudCartaService {

    private final CartaRepository cartaRepository;
    private final CrudColecaoService colecaoService;
    private final CadastroUsuarioService usuarioService;

    public CrudCartaService(CartaRepository cartaRepository, CrudColecaoService colecaoService,
                            CadastroUsuarioService usuarioService) {
        this.cartaRepository = cartaRepository;
        this.colecaoService = colecaoService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Carta salvarCarta(CartaForm cartaForm, Long idUsuario) {

        Colecao colecao = colecaoService.buscarPorId(cartaForm.getIdColecao());
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        String idiomaNomeDaCarta = identificarIdiomaDoNomeDaCarta(cartaForm.getNomeCarta());

        if(!idiomaNomeDaCarta.equals("pt")){
            throw new NegocioException("O nome da carta deve ser no idioma PT-BR!");
        }

        if(!usuario.equals(colecao.getDonoColecao())){
            throw new NegocioException("Você não pode registrar uma carta em uma coleção que não é sua!");
        }

        if(cartaRepository.existsByEdicaoAndIdiomaAndLaminada(cartaForm.getEdicao(), cartaForm.getIdioma(),
                cartaForm.isLaminada()) ){
            throw new NegocioException("Sua coleção já tem uma carta com essas caracteristicas, " +
                    "tente atualizar a quantidade da carta já existente");
        }

        Carta carta = cartaForm.converterParaEntidade();
        carta.setColecaoDaCarta(colecao);

        return cartaRepository.save(carta);
    }


    private String identificarIdiomaDoNomeDaCarta(String nomeCarta) {

        String idioma = "";

        try {

            DetectLanguage.apiKey = "0e5df3aa4657287504479eddae9db7e1";
            List<Result> resultado = DetectLanguage.detect(nomeCarta);
            idioma = resultado.get(0).language;

        } catch (APIError e) {
            e.printStackTrace();
        }

        return idioma;
    }


}
