package com.projeto.gestaoloja.util.tableModel.VendaRegistroTableModel;

import com.projeto.gestaoloja.modelo.entidades.VendaItem;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.stream.Collectors;

public class VendaRegistroTableModel extends AbstractTableModel {

    // Definindo o nome de cada coluna na tabela
    private String [] colunas = {"PRODUTO", "PREÇO", "QUANTIDADE", "DESCONTO", "TOTAL"};
    private HashMap<String, VendaItem> vendaDetalhes;

    public VendaRegistroTableModel(HashMap<String, VendaItem> vendaDetalhes) {
        this.vendaDetalhes = vendaDetalhes;
    }

    // retorna a quantidade de linhas que a tabela irá possuir
    @Override
    public int getRowCount() {
        return vendaDetalhes.size();
    }

    //número de colunas da tabela
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //retorna o conteúdo da célula
    @Override
    public Object getValueAt(int linha, int coluna) {
        VendaItem vendaDetalhess = vendaDetalhes
                .values()
                .stream()
                .collect(Collectors.toList()).get(linha);

        switch(coluna) {
            case 0: return vendaDetalhess.getProduto().getNome_prod();
            case 1: return vendaDetalhess.getProduto().getPrecoVenda_prod();
            case 2: return vendaDetalhess.getQuantidade();
            case 3: return vendaDetalhess.getDesconto();
            case 4: return vendaDetalhess.getTotal();
            default: return "";
        }
    }

    //retorna o nome da coluna em determinado índice
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    //retorna se a célula é editável
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public HashMap<String, VendaItem> getVendaDetalhes() {
        return vendaDetalhes;
    }

}
