package com.magic.place.api.domain.repository;

import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.domain.model.Idioma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> , JpaSpecificationExecutor<Carta> {

    boolean existsByEdicaoAndIdiomaAndLaminada(String edicao, Idioma idioma, boolean laminada);

    Page<Carta> findAllByColecaoDaCartaId(Long id, Pageable paginacao);
}
