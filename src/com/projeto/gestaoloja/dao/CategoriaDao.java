package com.projeto.gestaoloja.dao;

import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.modelo.entidades.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao {
    private static final String SQL_INSERT =
            "INSERT INTO categoria (nome_categoria, descricao_categoria) VALUES (?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE categoria SET nome_categoria = ?, descricao_categoria = ? WHERE id_categoria = ?";

    private static final String SQL_REMOVE =
            "DELETE FROM categoria WHERE id_categoria = ?";

    private static final String SQL_FIND_ALL =
            "SELECT * FROM categoria";

//  salva os registro da tabela categoria no banco de dados
    public int salvar(Categoria categoria){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_INSERT);
            pstm.setString(1, categoria.getNome_categoria());
            pstm.setString(2, categoria.getDescricao_categoria());
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

//  edita os registro da tabela categoria no banco de dados
    public int editar(Categoria categoria) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, categoria.getNome_categoria());
            pstm.setString(2, categoria.getDescricao_categoria());
            pstm.setInt(3,categoria.getId_categoria());
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

//  deleta os registro da tabela categoria no banco de dados
    public int deletar(int id_categoria) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_REMOVE);

            pstm.setLong(1, id_categoria);
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

//  lista todos os registro da tabela categoria do banco de dados
    public List<Categoria> listarCategoria() {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        List<Categoria> categorias = new ArrayList<Categoria>();
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(SQL_FIND_ALL);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId_categoria(rs.getInt("id_categoria"));
                categoria.setNome_categoria(rs.getString("nome_categoria"));
                categoria.setDescricao_categoria(rs.getString("descricao_categoria"));
                categorias.add(categoria);

            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBConnection.close(conn, pstm, rs);
            }
            e.printStackTrace();
        }
        return categorias;
    }

//  faz uma busca de todas as categorias por nome no banco de dados
    public Categoria buscarCategoriaNome(String nome) {
        String sql = String.format("SELECT * FROM categoria WHERE nome_categoria = '%s'", nome);
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm =conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                return getCategoria(rs);

            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBConnection.close(conn, pstm, rs);
            }
            e.printStackTrace();
        }
        return null;
    }

//  retorna a categoria em si do banco de dados para reaproveitar o código, utilizado em outros momentos
    private Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();

        categoria.setId_categoria(rs.getInt("id_categoria"));
        categoria.setNome_categoria(rs.getString("nome_categoria"));
        categoria.setDescricao_categoria(rs.getString("descricao_categoria"));

        return categoria;
    }


}
