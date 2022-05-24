CREATE TABLE CARTA (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome_carta VARCHAR(60) NOT NULL,
  edicao VARCHAR(150) NOT NULL,
  idioma VARCHAR(15) NOT NULL,
  laminada BOOLEAN NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  quantidade INT NOT NULL,
  id_colecao_carta BIGINT NOT NULL,
  primary key(id),
  CONSTRAINT FOREIGN KEY fk_colecao_carta (id_colecao_carta) REFERENCES COLECAO (id)
)engine = InnoDB default charset = utf8;