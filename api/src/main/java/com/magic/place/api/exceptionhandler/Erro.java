package com.magic.place.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Erro {

    private Integer status;

    private String titulo;

    private LocalDateTime dataHoraErro;

    private List<Campo> campos;

    public static class Campo{

        private String campo;

        private String erro;

        public Campo(String campo, String erro) {
            this.campo = campo;
            this.erro = erro;
        }

        public String getCampo() {
            return campo;
        }

        public String getErro() {
            return erro;
        }
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getDataHoraErro() {
        return dataHoraErro;
    }

    public void setDataHoraErro(LocalDateTime dataHoraErro) {
        this.dataHoraErro = dataHoraErro;
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }
}
