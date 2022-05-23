package com.magic.place.api.specification;

import com.magic.place.api.domain.model.Carta;
import org.springframework.data.jpa.domain.Specification;

public class CartaSpecification {

    public static Specification<Carta> nomeDaCartaParecidoCom(String nomeCarta){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get("nomeCarta"), "%" + nomeCarta +"%");
    }

    public static Specification<Carta> edicaoDaCartaParecidaCom(String edicaoCarta){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get("edicao"), "%" + edicaoCarta +"%");
    }
}
