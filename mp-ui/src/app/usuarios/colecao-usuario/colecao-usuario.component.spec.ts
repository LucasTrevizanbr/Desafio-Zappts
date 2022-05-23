import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColecaoUsuarioComponent } from './colecao-usuario.component';

describe('ColecaoUsuarioComponent', () => {
  let component: ColecaoUsuarioComponent;
  let fixture: ComponentFixture<ColecaoUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ColecaoUsuarioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ColecaoUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
