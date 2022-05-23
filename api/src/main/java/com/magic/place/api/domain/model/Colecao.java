package com.magic.place.api.domain.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COLECAO")
public class Colecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeColecao;

    private String descricaoColecao;

    @ManyToOne @JoinColumn(name = "id_dono_colecao")
    private Usuario donoColecao;

    @OneToMany(mappedBy = "colecaoDaCarta", cascade = CascadeType.ALL)
    private List<Carta> cartas = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colecao colecao = (Colecao) o;
        return Objects.equals(id, colecao.id) && Objects.equals(donoColecao, colecao.donoColecao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, donoColecao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeColecao() {
        return nomeColecao;
    }

    public void setNomeColecao(String nomeColecao) {
        this.nomeColecao = nomeColecao;
    }

    public String getDescricaoColecao() {
        return descricaoColecao;
    }

    public void setDescricaoColecao(String descricaoColecao) {
        this.descricaoColecao = descricaoColecao;
    }

    public Usuario getDonoColecao() {
        return donoColecao;
    }

    public void setDonoColecao(Usuario donoColecao) {
        this.donoColecao = donoColecao;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}
