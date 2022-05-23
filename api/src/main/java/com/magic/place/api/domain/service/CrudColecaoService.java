package com.magic.place.api.domain.service;

import com.magic.place.api.representation.form.FormColecao;
import com.magic.place.api.domain.exception.NegocioException;
import com.magic.place.api.domain.model.Colecao;
import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.repository.ColecaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrudColecaoService {

    private final ColecaoRepository colecaoRepository;
    private final CadastroUsuarioService usuarioService;

    public CrudColecaoService(ColecaoRepository colecaoRepository, CadastroUsuarioService usuarioService) {
        this.colecaoRepository = colecaoRepository;
        this.usuarioService = usuarioService;
    }

    public Colecao buscarPorId(Long id){
        return colecaoRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Não existe uma coleção com o ID informado!"));
    }

    @Transactional
    public Colecao salvarColecao(FormColecao formColecao, Long idUsuario){

        Usuario usuario =  usuarioService.buscarPorId(idUsuario);

        if(colecaoRepository.existsByNomeColecaoAndDonoColecao(formColecao.getNomeColecao(), usuario)){
                throw new NegocioException("Você Já tem uma coleção com esse nome!");
        }

        Colecao colecao = formColecao.converterParaEntidade();
        colecao.setDonoColecao(usuario);

        return colecaoRepository.save(colecao);

    }

    @Transactional
    public Colecao atualizarColecao(Long idColecao, Long idUsuario, FormColecao formColecao) {
        Colecao colecao = this.buscarPorId(idColecao);
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        this.verificarAutoridadeSobreAColecao(colecao, usuario);

        colecao.setNomeColecao(formColecao.getNomeColecao());
        colecao.setDescricaoColecao(formColecao.getDescricaoColecao());

        return colecaoRepository.save(colecao);
    }

    @Transactional
    public void deletarColecao(Long idUsuario, Long idColecao) {
        Colecao colecao = this.buscarPorId(idColecao);
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        this.verificarAutoridadeSobreAColecao(colecao, usuario);

        colecaoRepository.delete(colecao);
    }

    private void verificarAutoridadeSobreAColecao(Colecao colecao, Usuario usuario) {
        if(!colecao.getDonoColecao().equals(usuario)){
            throw new NegocioException("Você não tem permissão para realizar essa ação!");
        }
    }
}
