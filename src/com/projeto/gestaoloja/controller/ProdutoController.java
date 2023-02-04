package com.projeto.gestaoloja.controller;

import com.projeto.gestaoloja.modelo.entidades.Produto;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoController {

    public int cadastrarProduto(Produto produto){
        produto.cadastrarProduto(produto);
        return 1;
    }

    public int editarProduto(Produto produto){
        produto.editarProduto(produto);
        return 1;
    }

    public int deletarProduto(Long id){
        Produto produto = new Produto();
        produto.setId_prod(id);
        produto.deletarProduto(id);
        return 1;
    }

    public List<Produto> listarProduto(){
        return new Produto().listarProduto();
    }

    public int atualizarQuantidade(Long idProduto, Integer quantidade){
        new Produto().atualizarQuantidade(idProduto, quantidade);
        return 1;
    }

    public List<Produto> buscarProdutosPeloCategoria(String categoria){
        return new Produto().buscarProdutosPeloCategoria(categoria);

    }

    public Produto buscarProdutoPeloNome(String nome){
        return new Produto().buscarProdutoPeloNome(nome);
    }

    public BigDecimal RetornaDespesaTotal(){
        return new Produto().RetornaDespesaTotal();
    }

    public Double RetornaSaidaPorDia(String diaVenda, String mesVenda, String anoVenda){
        return new Produto().RetornaSaidaPorDia(diaVenda,mesVenda,anoVenda);
    }

}
