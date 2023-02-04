package com.projeto.gestaoloja.dao;

import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.modelo.entidades.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDao {

    private static final String SQL_INSERT =
            "INSERT INTO fornecedor (nome, nomeEmpresa, cnpj, email, telefone, endereco, categoria, descricao) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE fornecedor SET nome = ?, nomeEmpresa = ?, cnpj = ?, email = ?, telefone = ?, endereco = ?, categoria = ?, descricao = ? WHERE id = ?";

    private static final String SQL_REMOVE =
            "DELETE FROM fornecedor WHERE id = ?";

    private static final String SQL_FIND_ALL =
            "SELECT * FROM fornecedor";

//  salva os registro da tabela fornecedor no banco de dados
    public int salvar(Fornecedor fornecedor) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        try {
            pstm = conn.prepareStatement(SQL_INSERT);
            pstm.setString(1, fornecedor.getNome());
            pstm.setString(2, fornecedor.getEmpresa());
            pstm.setString(3, fornecedor.getCnpj());
            pstm.setString(4, fornecedor.getEmail());
            pstm.setString(5, fornecedor.getTelefone());
            pstm.setString(6, fornecedor.getEndereco());
            pstm.setString(7, fornecedor.getCategoria());
            pstm.setString(8, fornecedor.getDescricao());
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

//  edita os registro da tabela fornecedor no banco de dados
    public int editar(Fornecedor fornecedor) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        try {
            pstm = conn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, fornecedor.getNome());
            pstm.setString(2, fornecedor.getEmpresa());
            pstm.setString(3, fornecedor.getCnpj());
            pstm.setString(4, fornecedor.getEmail());
            pstm.setString(5, fornecedor.getTelefone());
            pstm.setString(6, fornecedor.getEndereco());
            pstm.setString(7, fornecedor.getCategoria());
            pstm.setString(8, fornecedor.getDescricao());
            pstm.setLong(9, fornecedor.getId());
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

//  deleta os registro da tabela fornecedor no banco de dados
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

//  lista todos os registro da tabela fornecedor do banco de dados
    public List<Fornecedor> listarFornecedor() {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        List<Fornecedor> fornecedors = new ArrayList<Fornecedor>();
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(SQL_FIND_ALL);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getLong("id"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setEmpresa(rs.getString("nomeEmpresa"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setCategoria(rs.getString("categoria"));
                fornecedor.setDescricao(rs.getString("descricao"));

                fornecedors.add(fornecedor);

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
        return fornecedors;
    }

//  faz uma busca de todos os fornecedores por nome no banco de dados
    public Fornecedor buscarFornecedorNome(String nome) {
        String sql = String.format("SELECT * FROM fornecedor WHERE nome = '%s'", nome);
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm =conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                return getFornecedor(rs);

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

//  retorna o fornecedor em si do banco de dados para reaproveitar o código, utilizado em outros momentos
    private Fornecedor getFornecedor(ResultSet rs) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setId(rs.getLong("id"));
        fornecedor.setNome(rs.getString("nome"));
        fornecedor.setEmpresa(rs.getString("nomeEmpresa"));
        fornecedor.setCnpj(rs.getString("cnpj"));
        fornecedor.setEmail(rs.getString("email"));
        fornecedor.setTelefone(rs.getString("telefone"));
        fornecedor.setEndereco(rs.getString("endereco"));
        fornecedor.setCategoria(rs.getString("categoria"));
        fornecedor.setDescricao(rs.getString("descricao"));

        return fornecedor;
    }

//  faz uma contagem de registros do fornecedor pelo id no banco de dados
    public Long listarFornecedorProduto(Long id){
        String sql = String.format("SELECT count(*) FROM produto WHERE id_fornecedor = %d", id);
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
}
