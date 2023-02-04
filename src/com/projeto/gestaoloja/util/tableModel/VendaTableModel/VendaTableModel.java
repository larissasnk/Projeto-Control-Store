package com.projeto.gestaoloja.util.tableModel.VendaTableModel;

import com.projeto.gestaoloja.modelo.entidades.Venda;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaTableModel extends AbstractTableModel {

    // Definindo as posições de cada coluna na tabela
    private static final int COL_ID = 0;
    private static final int COL_CLIENTE = 1;
    private static final int COL_VALOR_TOTAL = 2;
    private static final int COL_VALOR_PAGO = 3;
    private static final int COL_DESCONTO = 4;
    private static final int COL_TROCO = 5;
    private static final int COL_DATA_VENDA = 6;
    private static final int COL_VENDIDO_POR = 7;
    private List<Venda> vendas;

    public VendaTableModel(List<Venda> vendas) {
        this.vendas = vendas;
    }

    // retorna a quantidade de linhas que a tabela irá possuir
    @Override
    public int getRowCount() {
        return vendas.size();
    }

    //número de colunas da tabela
    @Override
    public int getColumnCount() {
        return 8;
    }

    //retorna o conteúdo da célula
    @Override
    public Object getValueAt(int linha, int coluna) {
        Venda venda = vendas.get(linha);
        if (coluna == COL_ID) {
            return venda.getId();
        } else if (coluna == COL_CLIENTE) {
            return venda.getCliente().getNome();
        } else if (coluna == COL_VALOR_TOTAL) {
            return venda.getTotalVenda();
        } else if (coluna == COL_VALOR_PAGO) {
            return venda.getValorPago();
        } else if (coluna == COL_DESCONTO) {
            return venda.getDesconto();
        }else if (coluna == COL_TROCO) {
            return venda.getTroco();
        }else if (coluna == COL_DATA_VENDA) {
            return venda.getDataHoraCriacao();
        }else if (coluna == COL_VENDIDO_POR) {
            return venda.getUsuario().getNome();
        }
        return null;
    }

    //retorna o nome da coluna em determinado índice
    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_ID:
                coluna = "Id";
                break;
            case COL_CLIENTE:
                coluna = "Cliente";
                break;
            case COL_VALOR_TOTAL:
                coluna = "Valor total";
                break;
            case COL_VALOR_PAGO:
                coluna = "Valor pago";
                break;
            case COL_DESCONTO:
                coluna = "Desconto";
                break;
            case COL_TROCO:
                coluna = "Troco";
                break;
            case COL_DATA_VENDA:
                coluna = "Data da Venda";
                break;
            case COL_VENDIDO_POR:
                coluna = "Vendido por";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    //retorna a classe da coluna através do seu índice
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_ID) {
            return Long.class;
        } else if (columnIndex == COL_CLIENTE) {
            return String.class;
        } else if (columnIndex == COL_VALOR_TOTAL) {
            return BigDecimal.class;
        } else if (columnIndex == COL_VALOR_PAGO) {
            return BigDecimal.class;
        } else if (columnIndex == COL_DESCONTO) {
            return BigDecimal.class;
        } else if (columnIndex == COL_TROCO) {
            return BigDecimal.class;
        } else if (columnIndex == COL_DATA_VENDA) {
            return LocalDateTime.class;
        }else if (columnIndex == COL_VENDIDO_POR) {
            return String.class;
        }
        return null;
    }

    //retorna se a célula é editável
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    //retorna os dados da linha específicada
    public Venda get(int linha){
        return vendas.get(linha);
    }

    public List<Venda> getVendas() {
        return vendas;
    }

}
