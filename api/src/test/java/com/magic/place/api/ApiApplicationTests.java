package com.magic.place.api;

import com.magic.place.api.controller.UsuarioController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class ApiApplicationTests {

	@Autowired
	private UsuarioController usuarioController;

	@Test
	void contextLoads() {
		assertThat(usuarioController).isNotNull();
	}

}
