package com.projeto.gestaoloja.controller;

import com.projeto.gestaoloja.modelo.entidades.Categoria;
import java.util.List;

public class CategoriaController {

    public int cadastrarCategoria(Categoria categoria){
        categoria.cadastrarCategoria(categoria);
        return 1;
    }

    public int editarCategoria(Categoria categoria){
        categoria.editarCategoria(categoria);
        return 1;
    }

    public int deletarCategoria(int id){
        Categoria categoria = new Categoria();
        categoria.setId_categoria(id);
        categoria.deletarCategoria(id);
        return 1;
    }

    public List<Categoria> listarCategoria(){
        return new Categoria().listarCategoria();
    }

    public Categoria buscarCategoriaNome(String nome){
        return new Categoria().buscarCategoriaNome(nome);
    }

}
