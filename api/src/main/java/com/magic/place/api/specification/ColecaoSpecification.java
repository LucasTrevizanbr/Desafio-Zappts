package com.magic.place.api.specification;

import com.magic.place.api.domain.model.Colecao;
import org.springframework.data.jpa.domain.Specification;

public class ColecaoSpecification {

    public static Specification<Colecao> nomeDaColecaoParecidoCom(String nomeColecao){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get("nomeColecao"), "%" + nomeColecao +"%");
    }

    public static Specification<Colecao> descDaColecaoParecidaCom(String descricaoColecao){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get("descricaoColecao"), "%" + descricaoColecao +"%");
    }
}
