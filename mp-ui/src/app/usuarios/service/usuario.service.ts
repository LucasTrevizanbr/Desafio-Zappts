import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CadastroUsuario } from '../model/cadastro-usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private readonly API = 'http://localhost:8080/api/usuarios'

  constructor(private httpClient : HttpClient) { }

  salvar(registro : CadastroUsuario){

    return this.httpClient.post<CadastroUsuario>(`${this.API}/cadastrar-usuario`, registro);

  }
}
