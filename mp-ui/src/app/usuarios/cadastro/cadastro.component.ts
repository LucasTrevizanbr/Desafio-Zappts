import { UsuarioService } from './../service/usuario.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent implements OnInit {

  formulario: FormGroup;
  hide = true;

  constructor(private formBuilder : FormBuilder, private service : UsuarioService,
    private snackBar: MatSnackBar) {

    this.formulario = this.formBuilder.group({
        nome: [null],
        email: [null],
        telefone: [null],
        senha: [null],
        verificacaoSenha: [null]
    })
  }

  ngOnInit(): void {
  }

  cadastrar(){

    if(this.formulario.controls['senha'].value !== this.formulario.controls['verificacaoSenha'].value){
      this.snackBar.open('Senha de confirmação não bate com a senha!', 'Entendi')

    }else{

      this.service.salvar(this.formulario.value).subscribe(result => {
        this.snackBar.open("Cadastrado com sucesso!", 'Entendi', {duration: 5000})
      }, error =>{
        this.snackBar.open(error.error.titulo, 'Entendi', {duration: 5000});
      })

    }

  }

}
