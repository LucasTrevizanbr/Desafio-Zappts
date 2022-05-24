package com.magic.place.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magic.place.api.domain.model.Usuario;
import com.magic.place.api.domain.repository.UsuarioRepository;
import com.magic.place.api.domain.service.usuario.AutenticacaoService;
import com.magic.place.api.representation.form.FormUsuario;
import com.magic.place.api.representation.form.LoginForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@WithMockUser(username="admin",roles={"USER","ADMIN"})
@ActiveProfiles("test")
public class UsuarioControllerTest {

    public static final String USUARIO_URI = "/api/usuarios";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private AutenticacaoService autenticacaoService;

    private FormUsuario usuarioForm;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        usuarioForm = new FormUsuario();
        usuarioForm.setEmail("usuarioteste@hotmail.com");
        usuarioForm.setNome("Testonildo da Silva");
        usuarioForm.setSenha("QualquerCoisa");
        usuarioForm.setTelefone("11 99967898");

        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve cadastrar usuario com sucesso e devolver sua representação")
    public void deveCadastrarUsuario() throws Exception {

        Usuario usuario = usuarioForm.converterParaEntidade();
        usuario.setId(1L);

        String usuarioFormJson = objectMapper.writeValueAsString(usuarioForm);

        BDDMockito.given(usuarioRepository.findByEmail(anyString())).willReturn(Optional.empty());
        BDDMockito.given(usuarioRepository.save(any(Usuario.class))).willReturn(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_URI +"/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(usuarioFormJson);


        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value("Testonildo da Silva"))
                .andExpect(MockMvcResultMatchers.jsonPath("telefone").value("11 99967898"))
                .andExpect(jsonPath("email").value("usuarioteste@hotmail.com"));
    }

    @Test
    @DisplayName("Deve devolver exceção de usuario ja cadastrado")
    public void naoDeveCadastrarUsuario() throws Exception {

        Usuario usuario = usuarioForm.converterParaEntidade();
        usuario.setId(1L);

        String usuarioFormJson = objectMapper.writeValueAsString(usuarioForm);

        BDDMockito.given(usuarioRepository.existsByEmail(anyString())).willReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_URI +"/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(usuarioFormJson);


        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("titulo").value("Esse email já esta cadastrado no sistema"));
    }

    @Test
    @DisplayName("Deve logar o usuario com sucesso e devolver o token de autenticação")
    public void deveLogar() throws Exception {
        LoginForm login = new LoginForm();
        login.setEmail("usuarioteste@hotmail.com");
        login.setSenha("QualquerCoisa");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(login.getSenha());

        Usuario usuario = usuarioForm.converterParaEntidade();
        usuario.setId(1L);
        usuario.setSenha(senhaCriptografada);

        String loginJson = objectMapper.writeValueAsString(login);

        BDDMockito.given(autenticacaoService.loadUserByUsername(anyString())).willReturn(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_URI +"/logar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(loginJson);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("nome").value("Testonildo da Silva"))
                .andExpect(jsonPath("tokenDto").isNotEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção de senha invalida ")
    public void naoDeveLogar() throws Exception {
        LoginForm login = new LoginForm();
        login.setEmail("usuarioteste@hotmail.com");
        login.setSenha("QualquerCoisa");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode("blavlasedw22wds");

        Usuario usuario = usuarioForm.converterParaEntidade();
        usuario.setId(1L);
        usuario.setSenha(senhaCriptografada);

        String loginJson = objectMapper.writeValueAsString(login);

        BDDMockito.given(autenticacaoService.loadUserByUsername(anyString())).willReturn(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_URI +"/logar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(loginJson);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("titulo").value("Senha está incorreta!"));
    }


}
