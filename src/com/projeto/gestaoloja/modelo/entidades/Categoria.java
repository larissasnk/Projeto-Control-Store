package com.projeto.gestaoloja.modelo.entidades;

import com.projeto.gestaoloja.dao.CategoriaDao;
import java.util.List;

public class Categoria {
    private int id_categoria;
    private String nome_categoria;
    private String descricao_categoria;

    public Categoria() {
    }

    public Categoria(int id_categoria, String nome_categoria, String descricao_categoria) {
        this.id_categoria = id_categoria;
        this.nome_categoria = nome_categoria;
        this.descricao_categoria = descricao_categoria;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.nome_categoria = nome_categoria;
    }

    public String getDescricao_categoria() {
        return descricao_categoria;
    }

    public void setDescricao_categoria(String descricao_categoria) {
        this.descricao_categoria = descricao_categoria;
    }

    public void cadastrarCategoria(Categoria categoria){
        new CategoriaDao().salvar(categoria);
    }

    public void editarCategoria(Categoria categoria){
        new CategoriaDao().editar(categoria);
    }

    public void deletarCategoria(int id) {
        new CategoriaDao().deletar(id);
    }

    public List<Categoria> listarCategoria(){
        return new CategoriaDao().listarCategoria();
    }

    public Categoria buscarCategoriaNome(String nome){
       return new CategoriaDao().buscarCategoriaNome(nome);
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + id_categoria + ", name=" + nome_categoria + ", descricao=" + descricao_categoria + '}';
    }
}
