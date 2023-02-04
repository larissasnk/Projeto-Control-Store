package com.projeto.gestaoloja.util.tableModel.FornecedorTableModel;

import com.projeto.gestaoloja.modelo.entidades.Fornecedor;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FornecedorTableModel extends AbstractTableModel {

    // Definindo as posições de cada coluna na tabela
    private static final int COL_id = 0;
    private static final int COL_nome = 1;
    private static final int COL_nomeEmpresa = 2;
    private static final int COL_cnpj = 3;
    private static final int COL_email = 4;
    private static final int COL_telefone= 5;
    private static final int COL_endereco= 6;
    private static final int COL_categoria= 7;
    private static final int COL_descricao= 8;

    private List<Fornecedor> valores;

    public FornecedorTableModel(List<Fornecedor> valores) {
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
        Fornecedor fornecedor = valores.get(linha);
        if (coluna == COL_id) {
            return fornecedor.getId();
        } else if (coluna == COL_nome) {
            return fornecedor.getNome();
        } else if (coluna == COL_nomeEmpresa) {
            return fornecedor.getEmpresa();
        }else if (coluna == COL_cnpj) {
            return fornecedor.getCnpj();
        }else if (coluna == COL_email) {
            return fornecedor.getEmail();
        }else if (coluna == COL_telefone) {
            return fornecedor.getTelefone();
        }else if (coluna == COL_endereco) {
            return fornecedor.getEndereco();
        }else if (coluna == COL_categoria) {
            return fornecedor.getCategoria();
        }else if (coluna == COL_descricao) {
            return fornecedor.getDescricao();
        }
        return null;
    }

    //retorna o nome da coluna em determinado índice
    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_id:
                coluna = "ID";
                break;
            case COL_nome:
                coluna = "Nome";
                break;
            case COL_nomeEmpresa:
                coluna = "Empresa";
                break;
            case COL_cnpj:
                coluna = "CNPJ";
                break;
            case COL_email:
                coluna = "Email";
                break;
            case COL_telefone:
                coluna = "Telefone";
                break;
            case COL_endereco:
                coluna = "Endereço";
                break;
            case COL_categoria:
                coluna = "Categoria";
                break;
            case COL_descricao:
                coluna = "Descrição";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    //retorna a classe da coluna através do seu índice
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_id) {
            return Long.class;
        } else if (columnIndex == COL_nome) {
            return String.class;
        } else if (columnIndex == COL_nomeEmpresa) {
            return String.class;
        } else if (columnIndex == COL_cnpj) {
            return String.class;
        } else if (columnIndex == COL_email) {
            return String.class;
        } else if (columnIndex == COL_telefone) {
            return String.class;
        }else if (columnIndex == COL_endereco) {
            return String.class;
        }else if (columnIndex == COL_categoria) {
            return String.class;
        }else if (columnIndex == COL_descricao) {
            return String.class;
        }
        return null;
    }

    //retorna os dados da linha específicada
    public Fornecedor get(int linha){
        return valores.get(linha);
    }
}
