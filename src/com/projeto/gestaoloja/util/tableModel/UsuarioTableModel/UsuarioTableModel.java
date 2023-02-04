package com.projeto.gestaoloja.util.tableModel.UsuarioTableModel;

import com.projeto.gestaoloja.modelo.entidades.Usuario;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsuarioTableModel extends AbstractTableModel {

    // Definindo as posições de cada coluna na tabela
    private static final int COL_id = 0;
    private static final int COL_nome = 1;
    private static final int COL_usuario = 2;
    private static final int COL_telefone = 3;
    private static final int COL_endereco= 4;
    private static final int COL_cargo = 5;
    private List<Usuario> valores;

    public UsuarioTableModel(List<Usuario> valores) {
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
        Usuario usuario = valores.get(linha);
        if (coluna == COL_id) {
            return usuario.getId();
        } else if (coluna == COL_nome) {
            return usuario.getNome();
        } else if (coluna == COL_usuario) {
            return usuario.getUsuario();
        }else if (coluna == COL_telefone) {
            return usuario.getTelefone();
        }else if (coluna == COL_endereco) {
            return usuario.getEndereco();
        }else if (coluna == COL_cargo) {
            return usuario.getCargo();
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
            case COL_usuario:
                coluna = "Usuario";
                break;
            case COL_telefone:
                coluna = "Telefone";
                break;
            case COL_endereco:
                coluna = "Endereço";
                break;
            case COL_cargo:
                coluna = "Cargo";
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
        } else if (columnIndex == COL_usuario) {
            return String.class;
        } else if (columnIndex == COL_telefone) {
            return String.class;
        } else if (columnIndex == COL_endereco) {
            return String.class;
        } else if (columnIndex == COL_cargo) {
            return String.class;
        }
        return null;
    }

    //retorna os dados da linha específicada
    public Usuario get(int linha){
        return valores.get(linha);
    }
}
