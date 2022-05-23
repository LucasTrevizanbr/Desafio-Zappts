import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import {MatButtonToggleModule} from '@angular/material/button-toggle';

import { CadastroComponent } from './cadastro/cadastro.component';
import { UsuariosRoutingModule } from './usuarios-routing.module';
import { FeedUsuarioComponent } from './feed-usuario/feed-usuario.component';
import { ColecaoUsuarioComponent } from './colecao-usuario/colecao-usuario.component';


@NgModule({
  declarations: [
    CadastroComponent,
    FeedUsuarioComponent,
    ColecaoUsuarioComponent
  ],
  imports: [
    CommonModule,
    UsuariosRoutingModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatSnackBarModule,
    MatGridListModule,
    MatButtonToggleModule

  ]
})
export class UsuariosModule { }
