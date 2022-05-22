CREATE TABLE USUARIO (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NOT NULL,
  email VARCHAR(50) NOT NULL,
  telefone VARCHAR(20) NOT NULL,
  senha VARCHAR(30) NOT NULL,
  primary key(id)
)engine = InnoDB default charset = utf8;