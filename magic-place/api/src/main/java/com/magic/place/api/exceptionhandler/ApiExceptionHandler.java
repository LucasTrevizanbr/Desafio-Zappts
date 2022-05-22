package com.magic.place.api.exceptionhandler;

import com.magic.place.api.domain.exception.UsuarioJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Erro handler(MethodArgumentNotValidException exception) {

        List<Erro.Campo> errosDto = new ArrayList<>();
        List<FieldError> errosCampos = exception.getBindingResult().getFieldErrors();

        errosCampos.stream().forEach(erroCampoX -> {
            String mensagemErro = messageSource.getMessage(erroCampoX, LocaleContextHolder.getLocale());
            errosDto.add(new Erro.Campo(erroCampoX.getField(), mensagemErro));
        });

        Erro erroValidacao = new Erro();
        erroValidacao.setTitulo("Um ou mais campos estão inválidos");
        erroValidacao.setDataHoraErro(LocalDateTime.now());
        erroValidacao.setStatus(HttpStatus.BAD_REQUEST.value());
        erroValidacao.setCampos(errosDto);

        return erroValidacao;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public Erro handler(UsuarioJaCadastradoException exception) {
        Erro erro = new Erro();
        erro.setTitulo(exception.getMessage());
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        erro.setDataHoraErro(LocalDateTime.now());

        return erro;
    }
}
