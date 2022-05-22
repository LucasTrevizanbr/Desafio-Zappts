import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroComponent } from './cadastro/cadastro.component';
import { FeedUsuarioComponent } from './feed-usuario/feed-usuario.component';

const routes: Routes = [
  {path:'', component : CadastroComponent},
  {path:'feed-usuario', component: FeedUsuarioComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }
