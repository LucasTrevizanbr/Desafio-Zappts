# Desafio-Zappts
A proposta era criar uma API onde jogadores de Magic pudessem exibir suas coleções de cartas uns para os outros.
Pensando nisso eu montei meu sistema com base em usuarios que criam contas, fazem o login e então podem criar suas coleções e adicionar cartas a elas e também podem ver as coleçoes de outros jogadores. Se com essa introdução ficou confuso, talvez esse desenho ajude:
<div align- "center">
<img src="https://user-images.githubusercontent.com/72326473/170004604-4a1064c9-a819-49b2-be28-6134a9f54e4b.png" width="400px" />
</div>
- O Fluxo de uso é simples, crie uma conta, logue para receber o token, use o token recebido no header para ser autenticado e conseguir fazer as demais requisições:
<div align- "center">
<img src="https://user-images.githubusercontent.com/72326473/170011725-9dee5736-b085-40e7-93cd-da8051cbdaf6.png" width="700px" />
</div>


# Rodar Localmente
## você vai precisar:
- Ter o mysql instalado
- Ter as variáveis de ambiente apontando para o java 17 ou superior
- Ter o maven instalado
## Clone o projeto ou baixe o zip dele, diretório raiz = pasta "api"
- Abra um terminal no diretório raiz do projeto e digite `mvn clean`
- Depois `mvn install -DSkipTests`, isso vai gerar a pasta target que contém o `.jar` do projeto:
- <div align- "center"> <img src="https://user-images.githubusercontent.com/72326473/170024803-331abd2f-bc41-4d51-aa3f-829d2050760d.png" width="200px" /></div>
- Digite `cd target` no terminal para acessar a pasta `target`
- A aplicação está configurada com variáveis de ambiente para se conectar ao banco de dados, então é necessário que você passe os valores delas de acordo com seu ambiente pela linha de comando
- As variáveis são `DB_URL` , `DB_USERNAME`, `DB_PASSWORD`. Respectivamente `String de conexão`, `Seu usuário do banco de dados` e `sua senha do banco de dados`
- Então para eecutar o projeto digite `java -jar -DDB_URL=jdbc:mysql://localhost:3306/api_magic_place?createDatabaseIfNotExist=true -DDB_USERNAME=xxx -DDB_PASSWORD=xxx api-0.0.1-SNAPSHOT.jar` :
- <div align- "center"><img src="https://user-images.githubusercontent.com/72326473/170026541-955a05a7-e653-41a6-a054-d3438a8d0587.png" width="700px" /></div>
- A String de conexão você pode aproveitar, agora NÃO ESQUEÇA de substituir os valores do `username` e `senha` do seu banco de dados para que a conexão ocorra bem.
- Agora que o projeto está rodando na sua máquina, basta acessar o http://localhost:8080/swagger-ui.html#/ no navegador para usar a interface do swagger, ou consumir como desejar.

# Usar a API na nuvem
- se quiser usar a API na nuvem basta acessar https://magic-place-api.herokuapp.com/swagger-ui.html#/ , aqui tem a documentação detalhada dos endpoints.

# Para saber mais
Se quiser entender um pouco mais sobre o projeto ou como eu fui pensando na construção dele, basta olhar a pasta "processo de desenvolvimento". Lá tem alguns textos bem curtinhos sobre algumas etapas chave do desenvolvimento.
