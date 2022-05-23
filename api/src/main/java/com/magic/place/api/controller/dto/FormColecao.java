package com.magic.place.api.controller.dto;

import com.magic.place.api.domain.model.Colecao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class FormColecao {

    @NotBlank @Size(max = 60)
    private String nomeColecao;

    @NotBlank @Size(max = 200)
    private String descricaoColecao;

    @NotNull @Positive
    private Long idDonoColecao;

    public String getNomeColecao() {
        return nomeColecao;
    }

    public String getDescricaoColecao() {
        return descricaoColecao;
    }

    public Long getIdDonoColecao() {
        return idDonoColecao;
    }

    public Colecao converterParaEntidade() {
        Colecao colecao = new Colecao();
        colecao.setNomeColecao(this.getNomeColecao());
        colecao.setDescricaoColecao(this.getDescricaoColecao());

        return colecao;
    }
}
