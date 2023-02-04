package com.projeto.gestaoloja.modelo.entidades;

import com.projeto.gestaoloja.dao.FornecedorDao;
import com.projeto.gestaoloja.dao.PessoaDao;
import com.projeto.gestaoloja.dao.ProdutoDao;

import java.util.ArrayList;
import java.util.List;

public class Fornecedor extends Pessoa{

    private String empresa;
    private String cnpj;
    private String email;
    private String categoria;
    private String descricao;
    private ArrayList<Produto> produtoLista= new ArrayList<>();

    public Fornecedor() {
        super();
    }

    public Fornecedor(Long id, String nome, String telefone, String endereco, String empresa, String cnpj, String email, String categoria, String descricao) {
        super(id, nome, telefone, endereco);
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.email = email;
        this.categoria = categoria;
        this.descricao = descricao;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Produto> getProdutoLista() {
        return produtoLista;
    }

    public void setProdutoLista(ArrayList<Produto> produtoLista) {
        this.produtoLista = produtoLista;
    }

    public void cadastrarFornecedor(Fornecedor fornecedor){
        new FornecedorDao().salvar(fornecedor);
    }

    public void editarFornecedor(Fornecedor fornecedor){
        new FornecedorDao().editar(fornecedor);
    }

// Aqui há um polimorfismo de sobreescrita, herdando da classe pai (Pessoa) o método deletar;
    @Override
    public void deletar(Long id) {
        new FornecedorDao().deletar(id);
    }

    public List<Fornecedor> listarFornecedor(){
        return new FornecedorDao().listarFornecedor();
    }

    public Fornecedor buscarFornecedorNome(String nome){
        return new FornecedorDao().buscarFornecedorNome(nome);
    }

    public Long listarFornecedorProduto(Long id){
        return new FornecedorDao().listarFornecedorProduto(id);
    }

    public void fornecer(Produto produto) {
        new ProdutoDao().salvar(produto);
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "empresa='" + empresa + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
