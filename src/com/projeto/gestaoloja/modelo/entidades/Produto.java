package com.projeto.gestaoloja.modelo.entidades;

import com.projeto.gestaoloja.dao.ProdutoDao;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Produto {
    private Long id_prod;
    private Integer quantidade_prod, estoqueMinimo_prod;
    private String marca_prod, nome_prod, descricao_prod;
    private BigDecimal precoVenda_prod, precoCusto_prod, lucro_prod;
    private LocalDateTime dataHoraCompra;
    private Fornecedor fornecedor;
    private Categoria categoria;


    public Produto() {
    }

    public Produto(Long id_prod, Integer quantidade_prod, Integer estoqueMinimo_prod, String marca_prod, String nome_prod, String descricao_prod, BigDecimal precoVenda_prod, BigDecimal precoCusto_prod, BigDecimal lucro_prod, LocalDateTime dataHoraCompra, Categoria categoria, Fornecedor fornecedor) {
        this.id_prod = id_prod;
        this.quantidade_prod = quantidade_prod;
        this.estoqueMinimo_prod = estoqueMinimo_prod;
        this.marca_prod = marca_prod;
        this.nome_prod = nome_prod;
        this.descricao_prod = descricao_prod;
        this.precoVenda_prod = precoVenda_prod;
        this.precoCusto_prod = precoCusto_prod;
        this.lucro_prod = lucro_prod;
        this.dataHoraCompra = dataHoraCompra;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    public BigDecimal getLucro_prod() {
        return lucro_prod;
    }

    public void setLucro_prod(BigDecimal lucro_prod) {
        this.lucro_prod = lucro_prod;
    }

    public Long getId_prod() {
        return id_prod;
    }

    public void setId_prod(Long id_prod) {
        this.id_prod = id_prod;
    }

    public BigDecimal getPrecoCusto_prod() {
        return precoCusto_prod;
    }

    public void setPrecoCusto_prod(BigDecimal precoCusto_prod) {
        this.precoCusto_prod = precoCusto_prod;
    }

    public BigDecimal getPrecoVenda_prod() {
        return precoVenda_prod;
    }

    public void setPrecoVenda_prod(BigDecimal precoVenda_prod) {
        this.precoVenda_prod = precoVenda_prod;
    }

    public Integer getQuantidade_prod() {
        return quantidade_prod;
    }

    public void setQuantidade_prod(Integer quantidade_prod) {
        this.quantidade_prod = quantidade_prod;
    }

    public Integer getEstoqueMinimo_prod() {
        return estoqueMinimo_prod;
    }

    public void setEstoqueMinimo_prod(Integer estoqueMinimo_prod) {
        this.estoqueMinimo_prod = estoqueMinimo_prod;
    }

    public String getMarca_prod() {
        return marca_prod;
    }

    public void setMarca_prod(String marca_prod) {
        this.marca_prod = marca_prod;
    }

    public String getNome_prod() {
        return nome_prod;
    }

    public void setNome_prod(String nome_prod) {
        this.nome_prod = nome_prod;
    }

    public String getDescricao_prod() {
        return descricao_prod;
    }

    public void setDescricao_prod(String descricao_prod) {
        this.descricao_prod = descricao_prod;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public LocalDateTime getDataHoraCompra() {
        return dataHoraCompra;
    }

    public void setDataHoraCompra(LocalDateTime dataHoraCompra) {
        this.dataHoraCompra = dataHoraCompra;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void atualizarQuantidade(Long idProduto, Integer quantidade){
        new ProdutoDao().atualizarQuantidade(idProduto, quantidade);
    }

    public List<Produto> buscarProdutosPeloCategoria(String categoria){
        return new ProdutoDao().buscarProdutosPeloCategoria(categoria);
    }

    public Produto buscarProdutoPeloNome(String nome){
        return new ProdutoDao().buscarProdutoPeloNome(nome);
    }

    public BigDecimal RetornaDespesaTotal(){
        return new ProdutoDao().RetornaDespesaTotal();
    }
    public void cadastrarProduto(Produto produto){
        new ProdutoDao().salvar(produto);
    }

    public void editarProduto(Produto produto){
        new ProdutoDao().editar(produto);
    }

    public Double RetornaSaidaPorDia(String diaVenda, String mesVenda, String anoVenda){
        return new ProdutoDao().RetornaSaidaPorDia(diaVenda,mesVenda,anoVenda);
    }

    public void deletarProduto(Long id) {
        new ProdutoDao().deletar(id);
    }

    public List<Produto> listarProduto(){
        return new ProdutoDao().listarProduto();
    }

    @Override
    public String toString() {
        return "Produto{" +
                "Marca='" + marca_prod + '\'' +
                ", Nome do produto='" + nome_prod + '\'' +
                ", Quantidade '" + quantidade_prod + '\'' +
                " Valor " + precoVenda_prod + '\'' +
                '}';
    }
}
