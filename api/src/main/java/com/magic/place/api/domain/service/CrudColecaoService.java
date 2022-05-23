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
    public Colecao salvarColecao(FormColecao formColecao){

        Usuario usuario =  usuarioService.buscarPorId(formColecao.getIdDonoColecao());

        if(colecaoRepository.existsByNomeColecaoAndDonoColecao(formColecao.getNomeColecao(), usuario)){
                throw new NegocioException("Você Já tem uma coleção com esse nome!");
        }

        Colecao colecao = formColecao.converterParaEntidade();
        colecao.setDonoColecao(usuario);

        return colecaoRepository.save(colecao);

    }


}
