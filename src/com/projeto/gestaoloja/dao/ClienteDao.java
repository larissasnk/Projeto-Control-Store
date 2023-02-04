package com.projeto.gestaoloja.dao;

import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.modelo.entidades.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao{

    private static final String SQL_INSERT =
            "INSERT INTO cliente (nome, cpf, email, telefone, endereco) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE cliente SET nome = ?, cpf = ?, email = ?, telefone = ?, endereco = ? WHERE id = ?";

    private static final String SQL_REMOVE =
            "DELETE FROM cliente WHERE id = ?";

    private static final String SQL_FIND_ALL =
            "SELECT * FROM cliente";

//  salva os registros da tabela cliente no banco de dados
    public int salvar(Cliente cliente) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        try {
            pstm = conn.prepareStatement(SQL_INSERT);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setString(3, cliente.getEmail());
            pstm.setString(4, cliente.getTelefone());
            pstm.setString(5, cliente.getEndereco());
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

//  edita os registros da tabela cliente no banco de dados
    public int editar(Cliente cliente) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setString(3, cliente.getEmail());
            pstm.setString(4, cliente.getTelefone());
            pstm.setString(5, cliente.getEndereco());
            pstm.setLong(6, cliente.getId());
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

//  deleta os registros da tabela cliente no banco de dados
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

//  faz uma busca de todos aos clientes por nome no banco de dados
    public Cliente buscarClienteNome(String nome) {
        String sql = String.format("SELECT * FROM cliente WHERE nome = '%s'", nome);
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                return getCliente(rs);

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

//  lista todos os registros da tabela cliente do banco de dados
    public Long listarClienteVenda(Long id){
        String sql = String.format("SELECT count(*) FROM venda WHERE cliente_id = %d", id);
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

//  retorna o cliente em si do banco de dados para reaproveitar o código, utilizado em outros momentos
    private Cliente getCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setId(rs.getLong("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEndereco(rs.getString("endereco"));


        return cliente;
    }

//  lista todos os registros da tabela cliente do banco de dados
    public List<Cliente> listarCliente() {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        List<Cliente> clientes = new ArrayList<Cliente>();
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(SQL_FIND_ALL);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));

                clientes.add(cliente);

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
        return clientes;
    }
}
