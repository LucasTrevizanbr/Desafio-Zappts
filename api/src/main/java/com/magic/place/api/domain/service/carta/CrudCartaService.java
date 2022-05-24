package com.magic.place.api.domain.service.carta;

import com.detectlanguage.Result;
import com.detectlanguage.errors.APIError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magic.place.api.domain.service.colecao.CrudColecaoService;
import com.magic.place.api.domain.service.usuario.CadastroUsuarioService;
import com.magic.place.api.representation.form.CartaForm;
import com.magic.place.api.domain.exception.NegocioException;
import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.domain.model.Colecao;
import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.repository.CartaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.detectlanguage.DetectLanguage;
import org.springframework.util.ReflectionUtils;

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

    public Carta buscarPorId(Long idCarta) {
        return cartaRepository.findById(idCarta)
                .orElseThrow(() -> new NegocioException("Não existe carta para o ID informado"));
    }

    @Transactional
    public Carta salvarCarta(CartaForm cartaForm, Long idUsuario, Long idColecao) {

        Colecao colecao = colecaoService.buscarPorId(idColecao);
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        this.checarAutoridadeSobreACarta(colecao, usuario);
        this.verificarIdiomaDoNome(cartaForm.getNomeCarta());
        this.checarExistenciaDaCarta(cartaForm);

        Carta carta = cartaForm.converterParaEntidade();
        carta.setColecaoDaCarta(colecao);

        return cartaRepository.save(carta);
    }

    @Transactional
    public Carta atualizarCarta(Long idCarta, Map<String, Object> propriedadesDaAtualizacao, Long idUsuario) {
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        ObjectMapper objectMapper = new ObjectMapper();
        Carta cartaAtual = this.buscarPorId(idCarta);
        Carta cartaAtualizada = objectMapper.convertValue(propriedadesDaAtualizacao, Carta.class);

        propriedadesDaAtualizacao.keySet().forEach((nomeCampo) ->{
            Field campoDaClasse = ReflectionUtils.findField(Carta.class, nomeCampo);
            campoDaClasse.setAccessible(true);

            Object valorCampo = ReflectionUtils.getField(campoDaClasse, cartaAtualizada);
            ReflectionUtils.setField(campoDaClasse, cartaAtual, valorCampo);
        });

        this.verificarIdiomaDoNome(cartaAtual.getNomeCarta());
        this.checarAutoridadeSobreACarta(cartaAtual.getColecaoDaCarta(), usuario);

        return cartaRepository.save(cartaAtual);
    }

    @Transactional
    public void deletarCarta(Long idUsuario, Long idCarta) {

        if(!cartaRepository.existsById(idCarta)){
            throw new NegocioException("Não existe carta com o id informado");
        }

        Colecao colecao = cartaRepository.findById(idCarta).get().getColecaoDaCarta();
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        this.checarAutoridadeSobreACarta(colecao, usuario);

        cartaRepository.deleteById(idCarta);
    }

    private void verificarIdiomaDoNome(String nomeCarta) {
        String idioma = this.identificarIdiomaDoNomeDaCarta(nomeCarta);
        if(!idioma.equals("pt")){
            throw new NegocioException("O nome da carta deve ser no idioma PT-BR!");
        }
    }

    private void checarAutoridadeSobreACarta(Colecao colecao, Usuario usuario) {
        if(!usuario.equals(colecao.getDonoColecao())){
            throw new NegocioException("Você não tem permissão para fazer isso!");
        }
    }

    private void checarExistenciaDaCarta(CartaForm cartaForm) {
        if(cartaRepository.existsByEdicaoAndIdiomaAndLaminada(cartaForm.getEdicao(), cartaForm.getIdioma(),
                cartaForm.isLaminada()) ){
            throw new NegocioException("Sua coleção já tem uma carta com essas caracteristicas, " +
                    "tente atualizar a quantidade da carta já existente");
        }
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
