package com.projeto.gestaoloja.modelo.entidades;

import com.projeto.gestaoloja.dao.PessoaDao;

public abstract class Pessoa {
    private Long id;
    private String nome;
    private  String telefone;
    private String endereco;

    public Pessoa(Long id, String nome, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;                           
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Pessoa() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void deletar(Long id){
        new PessoaDao().deletar(id);
    }
}
