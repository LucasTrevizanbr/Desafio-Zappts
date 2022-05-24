package com.magic.place.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.magic.place.api.domain.model.Carta;
import com.magic.place.api.domain.model.Colecao;
import com.magic.place.api.domain.model.Idioma;
import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.repository.CartaRepository;
import com.magic.place.api.domain.repository.ColecaoRepository;
import com.magic.place.api.domain.service.carta.CrudCartaService;
import com.magic.place.api.domain.service.colecao.CrudColecaoService;
import com.magic.place.api.domain.service.usuario.CadastroUsuarioService;
import com.magic.place.api.representation.form.CartaForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@WithMockUser(username="admin",roles={"USER","ADMIN"})
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CartaControllerTest {

    public static final String CARTA_URI = "/api/cartas";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColecaoRepository colecaoRepository;

    @MockBean
    private CartaRepository cartaRepository;

    @MockBean
    private CadastroUsuarioService cadastroUsuarioService;

    @MockBean
    private CrudColecaoService colecaoService;

    @MockBean
    private CrudCartaService cartaService;

    private ObjectMapper objectMapper;

    private Carta carta;

    private CartaForm cartaFormValido;

    private CartaForm cartaFormInvalido;

    private Colecao colecao;

    private Usuario usuario;

    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();

        usuario = new Usuario();
        usuario.setNome("Testonildo da Silva");
        usuario.setEmail("testonildo@gmail.com");
        usuario.setTelefone("11 99889889");
        usuario.setId(2L);

        colecao = new Colecao();
        colecao.setNomeColecao("Minha favorita");
        colecao.setId(1L);
        colecao.setDescricaoColecao("Muito legal legal");
        colecao.setDonoColecao(usuario);

        carta = new Carta();
        carta.setPreco(new BigDecimal("189.00"));
        carta.setNomeCarta("Barão ruiz medeiros");
        carta.setEdicao("Ruas da capenna");
        carta.setQuantidade(2);
        carta.setLaminada(true);
        carta.setIdioma(Idioma.INGLES);
        carta.setColecaoDaCarta(colecao);
        carta.setId(3L);

        cartaFormValido = new CartaForm();
        cartaFormValido.setNomeCarta("Um nome perfeitamente cabivel");
        cartaFormValido.setLaminada(true);
        cartaFormValido.setIdioma(Idioma.JAPONES);
        cartaFormValido.setQuantidade(2);
        cartaFormValido.setEdicao("Ruas da Cappena maldita subjulgada");
        cartaFormValido.setPreco(new BigDecimal("168.99"));

        cartaFormInvalido = new CartaForm();
        cartaFormInvalido.setNomeCarta(" ");
        cartaFormInvalido.setEdicao(" ");
        cartaFormInvalido.setPreco(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Deve criar uma carta com sucesso e devolver sua representação")
    public void deveCriarUmaCartaComSuceso() throws Exception {

        String cartaJson = objectMapper.writeValueAsString(cartaFormValido);

        BDDMockito.given(cadastroUsuarioService.buscarPorId(anyLong())).willReturn(usuario);
        BDDMockito.given(colecaoService.buscarPorId(anyLong())).willReturn(colecao);
        BDDMockito.given(cartaRepository.save(any(Carta.class))).willReturn(carta);

        MockHttpServletRequestBuilder request = post(CARTA_URI +"/criar-carta/idUsuario="+usuario.getId()+
                "&idColecao="+colecao.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(cartaJson);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("nomeCarta").value("Barão ruiz medeiros"))
                .andExpect(jsonPath("edicao").value("Ruas da capenna"))
                .andExpect(jsonPath("idioma").value("INGLES"));

    }

    @Test
    @DisplayName("Deve retornar uma exceção com os campos inválidos do formulário")
    public void naoDeveCriarUmaCarta() throws Exception {
        String cartaJson = objectMapper.writeValueAsString(cartaFormInvalido);

        MockHttpServletRequestBuilder request = post(CARTA_URI +"/criar-carta/idUsuario="+usuario.getId()+
                "&idColecao="+colecao.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(cartaJson);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("titulo").value("Um ou mais campos estão inválidos"))
                .andExpect(jsonPath("campos").isNotEmpty());
    }

    @Test
    @DisplayName("Deve retornar uma exceção explicitando que o nome da carta esta em um idioma diferente do PT-BR")
    public void naoDeveCriarUmaCartaComNomeDiferenteDoIdiomaPortugues() throws Exception {
        cartaFormValido.setNomeCarta("evil acolyte");
        String cartaJson = objectMapper.writeValueAsString(cartaFormValido);

        BDDMockito.given(cadastroUsuarioService.buscarPorId(anyLong())).willReturn(usuario);
        BDDMockito.given(colecaoService.buscarPorId(anyLong())).willReturn(colecao);

        MockHttpServletRequestBuilder request = post(CARTA_URI +"/criar-carta/idUsuario="+usuario.getId()+
                "&idColecao="+colecao.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(cartaJson);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("titulo").value("O nome da carta deve ser no idioma PT-BR!"));
    }

    @Test
    @DisplayName("Deve atualizar uma carta com sucesso")
    public void deveAtualizarUmaCarta() throws Exception {

        String cartaJson = objectMapper.writeValueAsString(cartaFormValido);

        BDDMockito.given(cadastroUsuarioService.buscarPorId(anyLong())).willReturn(this.usuario);
        BDDMockito.given(colecaoService.buscarPorId(anyLong())).willReturn(this.colecao);

        MockHttpServletRequestBuilder request = put(CARTA_URI +"/atualizar-carta/idUsuario="+usuario.getId()+
                "&idCarta="+ carta.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(cartaJson);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("nomeCarta").value("Barão ruiz medeiros"))
                .andExpect(jsonPath("edicao").value("Ruas da capenna"))
                .andExpect(jsonPath("idioma").value("INGLES"));
    }

    @Test
    @DisplayName("Deverá devolver uma Page de cartas pertencentes a uma coleção")
    public void deveBuscarPaginaDeCarta() throws Exception {

        Long id = 1L;
        int numeroDaPagina = 0;
        int qtdPorPagina = 1;
        String criterioOrdenacao = "preco";

        List<Carta> cartas = Arrays.asList(carta);
        Page<Carta> cartasPage = new PageImpl<>(cartas);

        BDDMockito.given(colecaoRepository.existsById(anyLong())).willReturn(true);
        BDDMockito.given(cartaRepository.findAllByColecaoDaCartaId(anyLong(),any(PageRequest.class))).willReturn(cartasPage);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CARTA_URI +"/da-colecao/idColecao=" + id +
                        "&pagina="+numeroDaPagina+"&qtdPagina=" + qtdPorPagina+
                        "&ordenarPor="+criterioOrdenacao)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isNotEmpty())
                .andExpect(jsonPath("totalElements").value(1));
    }

    @Test
    @DisplayName("Deve retornar exceção ao tentar buscar as cartas de uma Coleção inexistente")
    public void naoDeveRetornarPaginaDeCarta() throws Exception {

        Long id = 1L;
        int numeroDaPagina = 0;
        int qtdPorPagina = 1;
        String criterioOrdenacao = "preco";

        BDDMockito.given(colecaoRepository.existsById(anyLong())).willReturn(false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CARTA_URI +"/da-colecao/idColecao=" + id +
                        "&pagina="+numeroDaPagina+"&qtdPagina=" + qtdPorPagina+
                        "&ordenarPor="+criterioOrdenacao)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("titulo").value("Não existe uma coleção com o ID informado!"));
    }

    @Test
    @DisplayName("Deve deletar uma carta com sucesso")
    public void deveDeletarUmaCarta() throws Exception {

        BDDMockito.given(cartaRepository.existsById(anyLong())).willReturn(true);
        BDDMockito.given(cartaRepository.findById(anyLong())).willReturn(Optional.of(carta));
        BDDMockito.given(cadastroUsuarioService.buscarPorId(anyLong())).willReturn(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CARTA_URI +"/deletar-carta/idUsuario="+usuario.getId()
                        +"&idCarta="+ carta.getId());


        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @DisplayName("Deve retornar uma Exception caso um usuario tente deletar uma carta que não é sua")
    public void naoDeveDeletarUmaCarta() throws Exception {

        Usuario usuarioErrado = new Usuario();
        usuarioErrado.setId(1L);

        BDDMockito.given(cartaRepository.existsById(anyLong())).willReturn(true);
        BDDMockito.given(cartaRepository.findById(anyLong())).willReturn(Optional.of(carta));
        BDDMockito.given(cadastroUsuarioService.buscarPorId(anyLong())).willReturn(usuarioErrado);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CARTA_URI +"/deletar-carta/idUsuario="+usuarioErrado.getId()
                        +"&idCarta="+ carta.getId());

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("titulo").value("Você não tem permissão para fazer isso!"));

    }

    @Test
    @DisplayName("Deve retornar exceção em caso de carta inexistente")
    public void naoDeveDeletarUmaCartaInexistente() throws Exception {
        BDDMockito.given(cartaRepository.existsById(anyLong())).willReturn(false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CARTA_URI +"/deletar-carta/idUsuario="+usuario.getId()
                        +"&idCarta="+ carta.getId());

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .   andExpect(jsonPath("titulo").value("Não existe carta com o id informado"));


    }
}
