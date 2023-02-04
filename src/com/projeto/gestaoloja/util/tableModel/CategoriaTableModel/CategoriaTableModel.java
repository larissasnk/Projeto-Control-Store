package com.projeto.gestaoloja.util.tableModel.CategoriaTableModel;

import com.projeto.gestaoloja.modelo.entidades.Categoria;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CategoriaTableModel extends AbstractTableModel {

    // Definindo as posições de cada coluna na tabela
    private static final int COL_ID = 0;
    private static final int COL_NOME = 1;
    private static final int COL_DESCRICAO = 2;
    private List<Categoria> valores;

    public CategoriaTableModel(List<Categoria> valores) {
        this.valores = valores;
    }

    // retorna a quantidade de linhas que a tabela irá possuir
    @Override
    public int getRowCount() {
        return valores.size();
    }

    //número de colunas da tabela
    @Override
    public int getColumnCount() {
        return 3;
    }

    //retorna o conteúdo da célula
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Categoria categoria = valores.get(rowIndex);
        if(columnIndex == COL_ID){
            return categoria.getId_categoria();
        }else if(columnIndex == COL_NOME){
            return categoria.getNome_categoria();
        }else if(columnIndex == COL_DESCRICAO){
            return categoria.getDescricao_categoria();
        }
        return null;
    }

    //retorna o nome da coluna em determinado índice
    @Override
    public String getColumnName(int column){
        String coluna = "";
        switch (column){
            case COL_ID:
                coluna = "Id";
                break;
            case COL_NOME:
                coluna = "Nome";
                break;
            case COL_DESCRICAO:
                coluna = "Descrição";
                break;
            default:
                throw new IllegalArgumentException("Coluna Inválida!");
        }
        return coluna;
    }

    //retorna a classe da coluna através do seu índice
    @Override
    public Class<?> getColumnClass(int columnIndex){
        if(columnIndex == COL_ID){
            return int.class;
        }else if(columnIndex == COL_NOME){
            return String.class;
        }else if(columnIndex == COL_DESCRICAO){
            return String.class;
        }
        return null;
    }

    //retorna os dados da linha específicada
    public Categoria get(int row){
        return valores.get(row);
    }
}
