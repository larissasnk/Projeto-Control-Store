package com.projeto.gestaoloja.modelo.entidades;

import java.math.BigDecimal;

public class VendaItem {

    private Venda venda;
    private Produto produto;
    private int quantidade;
    private BigDecimal desconto;
    private BigDecimal total;

    public VendaItem() {
    }

    public VendaItem(Venda venda, Produto produto, int quantidade, BigDecimal desconto, BigDecimal total) {
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.total = total;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
