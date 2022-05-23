SET foreign_key_checks = 0;

DELETE FROM CARTA;
DELETE FROM COLECAO;
DELETE FROM USUARIO;

SET foreign_key_checks = 1;

ALTER TABLE CARTA AUTO_INCREMENT = 1;
ALTER TABLE COLECAO AUTO_INCREMENT = 1;
ALTER TABLE USUARIO AUTO_INCREMENT = 1;

INSERT INTO usuario (id, nome, email, telefone, senha) VALUES (1, 'Jorge jesus', 'jorginho@gmail.com', '11 699345567', 'elGajo');
INSERT INTO usuario (id, nome, email, telefone, senha) VALUES (2, 'Andrea Pirlo', 'pirlo@gmail.com', '11 699345567', '1234567');
INSERT INTO usuario (id, nome, email, telefone, senha) VALUES (3, 'Marco Zambrotta', 'mz@gmail.com', '11 699345567', '1234567');

INSERT INTO COLECAO (id, nome_colecao, descricao_colecao, id_dono_colecao) VALUES (1, 'As mais raras de 2002', 'as cartas mais raras das expansões de 2002', 1);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (1, 'Barão setentrional', 'Noite nas arábias', 'PORTUGUES', false, '120.50', 1, 1);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (2, 'Mago mental', 'Commander Legends: Batalha por Portal de Baldur', 'PORTUGUES', false, '337.20', 3, 1);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (3, 'Beco da morte', 'Ruas de nova Capenna', 'JAPONES', false, '66.50', 2, 1);

INSERT INTO COLECAO (id, nome_colecao, descricao_colecao, id_dono_colecao) VALUES (2, 'Minhas favoritas', 'as cartas que eu mais gosto', 1);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (4, 'Gnomo zureta', 'A guerra da Centelha', 'JAPONES', true, '300.50', 1, 2);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (5, 'Luz final mortal', 'A guerra da Centelha', 'JAPONES', false, '200.50', 3, 2);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (6, 'Beco da morte', 'Ruas de nova Capenna', 'JAPONES', true, '66.50', 1, 2);

INSERT INTO COLECAO (id, nome_colecao, descricao_colecao, id_dono_colecao) VALUES (3, 'Minhas laminadas', 'todas as minhas cartas laminadas', 1);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (7, 'Gnomo zureta', 'A guerra da Centelha', 'JAPONES', true, '300.50', 1, 3);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (8, 'Cavaleiro sombrio', 'Alara reunida', 'INGLES', true, '175.50', 3, 3);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (9, 'Beco da morte', 'Ruas de nova Capenna', 'JAPONES', true, '66.50', 1, 3);

INSERT INTO COLECAO (id, nome_colecao, descricao_colecao, id_dono_colecao) VALUES (4, 'Só as baratinhas', 'Cartas de diversas edições', 2);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (10, 'Barão setentrional', 'Noite nas arábias', 'PORTUGUES', false, '32.50', 7, 4);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (11, 'Serpente divina', 'Commander Legends: Batalha por Portal de Baldur', 'INGLES', false, '40.20', 8, 4);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (12, 'Arruaceiro da avenida ', 'Ruas de nova Capenna', 'INGLES', false, '20.00', 22, 4);

INSERT INTO COLECAO (id, nome_colecao, descricao_colecao, id_dono_colecao) VALUES (5, 'Kamigawa full Japones', 'Cartas em Japones da edição campeões de Kamigawa', 2);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (13, 'Samurai centenário', 'Campeões de Kamigawa', 'JAPONES', true, '355.50', 1, 5);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (14, 'Monstro abissal de Tokyo', 'Campeões de Kamigawa', 'JAPONES', false, '200.50', 3, 5);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (15, 'Cerejeira cegante', 'Campeões de Kamigawa', 'JAPONES', false, '245.50', 1, 5);

INSERT INTO COLECAO (id, nome_colecao, descricao_colecao, id_dono_colecao) VALUES (6, 'Guerra da Centelha - Todas em BR', 'cartas da edição guerra da centelha, só português', 2);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (16, 'Gnomo zureta', 'A guerra da Centelha', 'PORTUGUES', false, '43.50', 2, 6);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (17, 'Orc biruta das ideia', 'A guerra da Centelha', 'PORTUGUES', false, '33.50', 5, 6);
INSERT INTO CARTA (id, nome_carta, edicao, idioma, laminada, preco, quantidade, id_colecao_carta) VALUES (18, 'Mago serelepe', 'A guerra da Centelha', 'PORTUGUES', false, '66.50', 1, 6);
