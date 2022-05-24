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


# Usar a API na nuvem
- se quiser usar a API na nuvem basta acessar https://magic-place-api.herokuapp.com/swagger-ui.html#/ , aqui tem a documentação detalhada dos endpoints.
