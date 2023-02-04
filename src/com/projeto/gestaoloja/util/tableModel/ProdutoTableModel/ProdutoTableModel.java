package com.projeto.gestaoloja.util.tableModel.ProdutoTableModel;

import com.projeto.gestaoloja.modelo.entidades.Produto;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoTableModel extends AbstractTableModel {

    // Definindo as posições de cada coluna na tabela
    private static final int COL_ID_PROD = 0;
    private static final int COL_NOME_PROD = 1;
    private static final int COL_QUANTIDADE_PROD = 2;
    private static final int COL_PRECOCUSTO_PROD = 3;
    private static final int COL_LUCRO_PROD = 4;
    private static final int COL_PRECOVENDA_PROD = 5;
    private static final int COL_CATEGORIA_PROD = 6;
    private static final int COL_MARCA_PROD = 7;
    private static final int COL_DESCRICAO_PROD = 8;
    private List<Produto> valores;

    public ProdutoTableModel(List<Produto> valores) {
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
        return 9;
    }

    //retorna o conteúdo da célula
    @Override
    public Object getValueAt(int linha, int coluna) {
        Produto produto = valores.get(linha);

        if(coluna == COL_ID_PROD){
            return produto.getId_prod();
        }else if(coluna == COL_NOME_PROD){
            return produto.getNome_prod();
        }else if(coluna == COL_QUANTIDADE_PROD){
            return produto.getQuantidade_prod();
        }else if(coluna == COL_PRECOCUSTO_PROD){
            return produto.getPrecoCusto_prod();
        }else if(coluna == COL_LUCRO_PROD){
            return produto.getLucro_prod();
        }else if(coluna == COL_PRECOVENDA_PROD){
            return produto.getPrecoVenda_prod();
        }else if(coluna == COL_CATEGORIA_PROD){
            return produto.getCategoria().getNome_categoria();
        }else if(coluna == COL_MARCA_PROD){
            return produto.getMarca_prod();
        }else if(coluna == COL_DESCRICAO_PROD){
            return produto.getDescricao_prod();
        }
        return null;
    }

    //retorna o nome da coluna em determinado índice
    @Override
    public String getColumnName(int column){
        String coluna = "";

        switch (column){
            case COL_ID_PROD:
                coluna = "Id";
                break;
            case COL_NOME_PROD:
                coluna = "Nome";
                break;
            case COL_QUANTIDADE_PROD:
                coluna = "Estoque atual";
                break;
            case COL_PRECOCUSTO_PROD:
                coluna = "Preço de custo";
                break;
            case COL_LUCRO_PROD:
                coluna = "Lucro em %";
                break;
            case COL_PRECOVENDA_PROD:
                coluna = "Preço de venda";
                break;
            case COL_CATEGORIA_PROD:
                coluna = "Categoria";
                break;
            case COL_MARCA_PROD:
                coluna = "Marca";
                break;
            case COL_DESCRICAO_PROD:
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
        if(columnIndex == COL_ID_PROD){
            return Long.class;
        }else if(columnIndex == COL_NOME_PROD){
            return String.class;
        }else if(columnIndex == COL_QUANTIDADE_PROD){
            return int.class;
        }else if(columnIndex == COL_PRECOCUSTO_PROD){
            return BigDecimal.class;
        }else if(columnIndex == COL_LUCRO_PROD){
            return BigDecimal.class;
        }else if(columnIndex == COL_PRECOVENDA_PROD){
            return BigDecimal.class;
        }else if(columnIndex == COL_CATEGORIA_PROD){
            return String.class;
        }else if(columnIndex == COL_MARCA_PROD){
            return String.class;
        }else if(columnIndex == COL_DESCRICAO_PROD){
            return String.class;
        }
        return null;
    }

    //retorna os dados da linha específicada
    public Produto get(int linha){
        return valores.get(linha);
    }
}
