package com.magic.place.api.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "CARTA")
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCarta;

    private String edicao;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    private boolean laminada;

    private BigDecimal preco;

    private int quantidade;

    @ManyToOne @JoinColumn(name = "id_colecao_carta")
    private Colecao colecaoDaCarta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return laminada == carta.laminada && Objects.equals(id, carta.id) && Objects.equals(edicao, carta.edicao) && idioma == carta.idioma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, edicao, idioma, laminada);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCarta() {
        return nomeCarta;
    }

    public void setNomeCarta(String nomeCarta) {
        this.nomeCarta = nomeCarta;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public boolean isLaminada() {
        return laminada;
    }

    public void setLaminada(boolean laminada) {
        this.laminada = laminada;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Colecao getColecaoDaCarta() {
        return colecaoDaCarta;
    }

    public void setColecaoDaCarta(Colecao colecaoDaCarta) {
        this.colecaoDaCarta = colecaoDaCarta;
    }

}
