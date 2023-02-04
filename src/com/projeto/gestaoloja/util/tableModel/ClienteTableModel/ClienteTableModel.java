package com.projeto.gestaoloja.util.tableModel.ClienteTableModel;

import com.projeto.gestaoloja.modelo.entidades.Cliente;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel {

    // Definindo as posições de cada coluna na tabela
    private static final int COL_id = 0;
    private static final int COL_nome = 1;
    private static final int COL_cpf = 2;
    private static final int COL_email = 3;
    private static final int COL_telefone = 4;
    private static final int COL_endereco= 5;

    private List<Cliente> valores;

    public ClienteTableModel(List<Cliente> valores) {
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
        return 6;
    }

    //retorna o conteúdo da célula
    @Override
    public Object getValueAt(int linha, int coluna) {
        Cliente cliente = valores.get(linha);
        if (coluna == COL_id) {
            return cliente.getId();
        } else if (coluna == COL_nome) {
            return cliente.getNome();
        } else if (coluna == COL_cpf) {
            return cliente.getCpf();
        }else if (coluna == COL_email) {
            return cliente.getEmail();
        }else if (coluna == COL_telefone) {
            return cliente.getTelefone();
        }else if (coluna == COL_endereco) {
            return cliente.getEndereco();
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
            case COL_cpf:
                coluna = "CPF";
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
        } else if (columnIndex == COL_cpf) {
            return String.class;
        } else if (columnIndex == COL_email) {
            return String.class;
        } else if (columnIndex == COL_telefone) {
            return String.class;
        } else if (columnIndex == COL_endereco) {
            return String.class;
        }
        return null;
    }
    //retorna os dados da linha específicada
    public Cliente get(int linha){
        return valores.get(linha);
    }
}
