package com.magic.place.api.representation.form;

import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.domain.model.Idioma;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class CartaForm {

    @NotBlank @Size(max = 60)
    private String nomeCarta;

    @NotBlank @Size(max = 30)
    private String edicao;

    @NotNull
    private Idioma idioma;

    @NotNull
    private boolean laminada;

    @NotNull @Positive
    private BigDecimal preco;

    @NotNull @Positive
    private int quantidade;

    public String getNomeCarta() {
        return nomeCarta;
    }

    public String getEdicao() {
        return edicao;
    }

    public Idioma getIdioma() {
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

    public Carta converterParaEntidade() {
        Carta carta = new Carta();
        carta.setNomeCarta(this.getNomeCarta());
        carta.setPreco(this.getPreco());
        carta.setEdicao(this.getEdicao());
        carta.setIdioma(this.getIdioma());
        carta.setQuantidade(this.getQuantidade());
        carta.setLaminada(this.isLaminada());

        return carta;
    }
}
