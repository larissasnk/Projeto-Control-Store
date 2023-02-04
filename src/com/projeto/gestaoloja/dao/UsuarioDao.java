package com.projeto.gestaoloja.dao;

import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.modelo.entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao  {

    private static final String SQL_INSERT =
            "INSERT INTO usuario (nome, usuario, senha, telefone, endereco, cargo) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE usuario SET nome = ?, usuario = ?, senha = ?, telefone = ?, endereco = ?, cargo = ? WHERE id = ?";
    private static final String SQL_REMOVE =
            "DELETE FROM usuario WHERE id = ?";
    private static final String SQL_FIND_ALL =
            "SELECT * FROM usuario";

//  salva os registro da tabela usuario no banco de dados
    public int salvar(Usuario usuario) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        try {
            pstm = conn.prepareStatement(SQL_INSERT);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getUsuario());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getTelefone());
            pstm.setString(5, usuario.getEndereco());
            pstm.setString(6, usuario.getCargo());
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

//  edita os registro da tabela usuario no banco de dados
    public int editar(Usuario usuario) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getUsuario());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getTelefone());
            pstm.setString(5, usuario.getEndereco());
            pstm.setString(6, usuario.getCargo());
            pstm.setLong(7, usuario.getId());
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

//  deleta os registro da tabela usuario no banco de dados
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

//  lista todos os registro da tabela usuario do banco de dados
    public List<Usuario> listarUsuario() {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(SQL_FIND_ALL);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setEndereco(rs.getString("endereco"));
                usuario.setCargo(rs.getString("cargo"));

                usuarios.add(usuario);

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
        return usuarios;
    }

//  faz uma contagem de registros do usuario pelo id no banco de dados
    public Long listarUsuarioVenda(Long id){
        String sql = String.format("SELECT count(*) FROM venda WHERE usuario_id = %d", id);
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            if(rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

        return null;
    }

//  faz uma busca de todos aos usuarios por nome no banco de dados
    public Usuario buscarUsuarioNome(String nome) {
        String sql = String.format("SELECT * FROM usuario WHERE nome = '%s'", nome);
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                return getUsuario(rs);

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

//  retorna o usuario em si do banco de dados para reaproveitar o código, utilizado em outros momentos
    private Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setUsuario(rs.getString("usuario"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setTelefone(rs.getString("telefone"));
        usuario.setEndereco(rs.getString("endereco"));
        usuario.setCargo(rs.getString("cargo"));

        return usuario;
    }
}
