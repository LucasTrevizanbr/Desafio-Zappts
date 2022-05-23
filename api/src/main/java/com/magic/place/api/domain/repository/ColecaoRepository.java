package com.magic.place.api.domain.repository;

import com.magic.place.api.domain.model.Colecao;
import com.magic.place.api.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColecaoRepository extends JpaRepository<Colecao, Long> {

    boolean existsByNomeColecaoAndDonoColecao(String nomeColecao, Usuario usuario);
}
