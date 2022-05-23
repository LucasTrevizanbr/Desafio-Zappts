package com.magic.place.api.domain.repository;

import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.domain.model.Colecao;
import com.magic.place.api.domain.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> {

    boolean existsByEdicaoAndIdiomaAndLaminada(String edicao, Idioma idioma, boolean laminada);

}
