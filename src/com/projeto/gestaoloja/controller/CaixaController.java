package com.projeto.gestaoloja.controller;

import com.projeto.gestaoloja.modelo.entidades.Caixa;
public class CaixaController {

    public int salvar(Caixa caixa) {
       caixa.salvar(caixa);
       return 1;
    }
}

