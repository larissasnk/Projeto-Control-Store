package com.projeto.gestaoloja.dao;

import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.modelo.entidades.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDao {

    private static final String SQL_INSERT =
            "INSERT INTO pessoa (nome, telefone, endereco) VALUES (?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE pessoa SET nome = ?, telefone = ?, endereco = ? WHERE id = ?";

    private static final String SQL_REMOVE =
            "DELETE FROM pessoa WHERE id = ?";

    private static final String SQL_FIND_ALL =
            "SELECT * FROM pessoa";

    public int salvar(Pessoa pessoa) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        try {
            pstm = conn.prepareStatement(SQL_INSERT);
            pstm.setString(1, pessoa.getNome());
            pstm.setString(2, pessoa.getTelefone());
            pstm.setString(3, pessoa.getEndereco());
            result = pstm.executeUpdate();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBConnection.close(conn, pstm, null);
            }
            e.printStackTrace();
        }
        return result;
    }


    public int editar(Pessoa pessoa) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, pessoa.getNome());
            pstm.setString(2, pessoa.getTelefone());
            pstm.setString(3, pessoa.getEndereco());
            pstm.setLong(4, pessoa.getId());
            result = pstm.executeUpdate();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBConnection.close(conn, pstm, null);
            }
            e.printStackTrace();
        }
        return result;
    }

    public int deletar(Long id) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_REMOVE);

            pstm.setLong(1, id);
            result = pstm.executeUpdate();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBConnection.close(conn, pstm, null);
            }
            e.printStackTrace();
        }
        return result;
    }

}
