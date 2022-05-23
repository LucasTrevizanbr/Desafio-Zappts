package com.magic.place.api.controller.dto;

import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.domain.model.Colecao;

import java.math.BigDecimal;

public class ColecaoDTO {

    private String nomeColecao;

    private String descricaoColecao;

    private int quantidadeCartasDaColecao;

    private BigDecimal totalEmReaisDaColecao;

    private String donoDaColecao;

    public ColecaoDTO(Colecao colecao) {
        this.nomeColecao = colecao.getNomeColecao();
        this.descricaoColecao = colecao.getDescricaoColecao();
        this.quantidadeCartasDaColecao = colecao.getCartas().size();
        this.totalEmReaisDaColecao = colecao.getCartas().stream().map(Carta::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.donoDaColecao = colecao.getDonoColecao().getNome();
    }

    public String getNomeColecao() {
        return nomeColecao;
    }

    public String getDescricaoColecao() {
        return descricaoColecao;
    }

    public int getQuantidadeCartasDaColecao() {
        return quantidadeCartasDaColecao;
    }

    public BigDecimal getTotalEmReaisDaColecao() {
        return totalEmReaisDaColecao;
    }

    public String getDonoDaColecao() {
        return donoDaColecao;
    }
}
