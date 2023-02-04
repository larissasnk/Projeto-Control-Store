package com.projeto.gestaoloja.controller;

import com.projeto.gestaoloja.modelo.entidades.Fornecedor;
import com.projeto.gestaoloja.modelo.entidades.Produto;

import java.util.List;

public class FornecedorController {

    public int cadastrarFornecedor(Fornecedor fornecedor){
        fornecedor.cadastrarFornecedor(fornecedor);
        return 1;
    }

    public int editarFornecedor(Fornecedor fornecedor){
        fornecedor.editarFornecedor(fornecedor);
        return 1;
    }

    public int deletar(Long id){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        fornecedor.deletar(id);
        return 1;
    }

    public List<Fornecedor> listarFornecedor(){
        return new Fornecedor().listarFornecedor();
    }

    public Fornecedor buscarFornecedorNome(String nome){
        return new Fornecedor().buscarFornecedorNome(nome);
    }

    public Long listarFornecedorProduto(Long id){
        return new Fornecedor().listarFornecedorProduto(id);
    }

    public int fornecer(Produto produto){
        new Fornecedor().fornecer(produto);
        return 1;
        }
}
