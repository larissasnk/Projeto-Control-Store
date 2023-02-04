package com.projeto.gestaoloja.controller;

import com.projeto.gestaoloja.modelo.entidades.Venda;
import com.projeto.gestaoloja.modelo.entidades.VendaItem;
import java.math.BigDecimal;
import java.util.List;

public class VendaController {

    public int cadastrarVenda(Venda venda){
        new Venda().cadastrarVenda(venda);
        return 1;
    }

    public List<Venda> listarTodasVendas(){
        return new Venda().listarTodasVendas();
    }

    public Long buscarIdDaUltimaVenda(){
        return new Venda().buscarIdDaUltimaVenda();
    }

    public List<VendaItem> buscaDetalhesDaVendaPeloId(Long id){
        return new Venda().buscaDetalhesDaVendaPeloId(id);
    }

    public BigDecimal RetornaEntradaTotal(){
        return new Venda().RetornaEntradaTotal();
    }

    public Double RetornaEntradaPorDia(String diaVenda, String mesVenda, String anoVenda){
        return new Venda().RetornaEntradaPorDia(diaVenda, mesVenda, anoVenda);
    }
    public Long listarProdutosVendaItem(Long id){
        return new Venda().listarProdutosVendaItem(id);
    }
}
