CREATE TABLE COLECAO (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome_colecao VARCHAR(60) NOT NULL,
  descricao_colecao VARCHAR(200) NOT NULL,
  id_dono_colecao BIGINT NOT NULL,
  primary key(id),
  CONSTRAINT FOREIGN KEY fk_dono_colecao (id_dono_colecao) REFERENCES USUARIO (id)
)engine = InnoDB default charset = utf8;