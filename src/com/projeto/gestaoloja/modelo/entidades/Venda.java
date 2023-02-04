package com.projeto.gestaoloja.modelo.entidades;

import com.projeto.gestaoloja.dao.VendaDao;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Venda {

    private Long id;
    private Cliente cliente;
    private Usuario usuario;
    private BigDecimal totalVenda;
    private BigDecimal valorPago;
    private BigDecimal troco;
    private BigDecimal desconto;
    private LocalDateTime dataHoraCriacao;
    private HashMap<String, VendaItem> vendasDetalhes;

    public Venda() {
    }

    public Venda(Long id, Cliente cliente, Usuario usuario, BigDecimal totalVenda, BigDecimal valorPago, BigDecimal troco, BigDecimal desconto, LocalDateTime dataHoraCriacao, HashMap<String, VendaItem> vendasDetalhes) {
        this.id = id;
        this.cliente = cliente;
        this.usuario = usuario;
        this.totalVenda = totalVenda;
        this.valorPago = valorPago;
        this.troco = troco;
        this.desconto = desconto;
        this.dataHoraCriacao = dataHoraCriacao;
        this.vendasDetalhes = vendasDetalhes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(BigDecimal totalVenda) {
        this.totalVenda = totalVenda;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public HashMap<String, VendaItem> getVendasDetalhes() {
        return vendasDetalhes;
    }

    public void setVendasDetalhes(HashMap<String, VendaItem> vendasDetalhes) {
        this.vendasDetalhes = vendasDetalhes;
    }

    public void cadastrarVenda(Venda venda){
        new VendaDao().salvar(venda);
    }

    public void adicionarVendaItem(VendaItem vendaItem){
        new VendaDao().adicionarVendaItem(vendaItem);
    }

    public List<Venda> listarTodasVendas(){
        return new VendaDao().listarTodasVendas();
    }

    public Long buscarIdDaUltimaVenda(){
        return new VendaDao().buscarIdDaUltimaVenda();
    }

    public List<VendaItem> buscaDetalhesDaVendaPeloId(Long id){
        return new VendaDao().buscaDetalhesDaVendaPeloId(id);
    }

    public BigDecimal RetornaEntradaTotal(){
        return new VendaDao().RetornaEntradaTotal();
    }

    public Double RetornaEntradaPorDia(String diaVenda, String mesVenda, String anoVenda){
        return new VendaDao().RetornaEntradaPorDia(diaVenda, mesVenda, anoVenda);
    }

    public Long listarProdutosVendaItem(Long id){
        return new VendaDao().listarProdutosVendaItem(id);
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", usuario=" + usuario +
                ", totalVenda=" + totalVenda +
                ", valorPago=" + valorPago +
                ", troco=" + troco +
                ", desconto=" + desconto +
                ", dataHoraCriacao=" + dataHoraCriacao +
                ", vendasDetalhes=" + vendasDetalhes +
                '}';
    }
}
