package com.magic.place.api.controller.dto;

import com.magic.place.api.domain.model.Carta;

import java.math.BigDecimal;

public class CartaDTO {

    private final String nomeCarta;

    private final String edicao;

    private final String idioma;

    private final boolean laminada;

    private final BigDecimal preco;

    private final int quantidade;

    public CartaDTO(Carta carta) {
        this.nomeCarta = carta.getNomeCarta();
        this.edicao = carta.getEdicao();
        this.idioma = carta.getIdioma().toString();
        this.laminada = carta.isLaminada();
        this.preco = carta.getPreco();
        this.quantidade = carta.getQuantidade();
    }

    public String getNomeCarta() {
        return nomeCarta;
    }

    public String getEdicao() {
        return edicao;
    }

    public String getIdioma() {
        return idioma;
    }

    public boolean isLaminada() {
        return laminada;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
