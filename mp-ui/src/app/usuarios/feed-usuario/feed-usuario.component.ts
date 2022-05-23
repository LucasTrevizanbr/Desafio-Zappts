import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-feed-usuario',
  templateUrl: './feed-usuario.component.html',
  styleUrls: ['./feed-usuario.component.scss']
})
export class FeedUsuarioComponent implements OnInit {

  constructor(
    private route: Router,
    private rotaAtiva: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  irParaColecao(){
    console.log('indo para coleção')
    this.route.navigate(['minha-colecao'], {relativeTo: this.rotaAtiva.parent})
  }
}
