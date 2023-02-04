package com.projeto.gestaoloja.dao;

import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.modelo.entidades.Categoria;
import com.projeto.gestaoloja.modelo.entidades.Fornecedor;
import com.projeto.gestaoloja.modelo.entidades.Produto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao{

    private static final String SQL_INSERT =
            "INSERT INTO produto (nome_prod, descricao_prod, precoCusto_prod, lucro_prod, precoVenda_prod, quantidade_prod, estoqueMinimo_prod, marca_prod, id_categoria, id_fornecedor) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE produto SET nome_prod = ?, descricao_prod = ?, precoCusto_prod = ?, lucro_prod = ?, precoVenda_prod = ?, quantidade_prod = ?, estoqueMinimo_prod = ?, marca_prod = ?, id_categoria = ?, id_fornecedor = ? WHERE id_prod = ?";
    private static final String SQL_REMOVE =
            "DELETE FROM produto WHERE id_prod = ?";
    private static final String SQL_UPDATE_QUANT_PROD =
             "UPDATE produto SET quantidade_prod = ? WHERE id_prod = ?";
    private static final String SQL_FIND_ALL =
            "SELECT * FROM produto p, categoria c, fornecedor f WHERE p.id_categoria = c.id_categoria AND p.id_fornecedor = f.id";

    private static final String SQL_DESPESA =
            "SELECT sum(precoCusto_prod) AS Saida FROM produto";

    private static final String SQL_SAIDA_DIA =
            "SELECT  sum(precoCusto_prod) As SaidaDiaria FROM produto\n" +
                    "WHERE DAY(data_hora_compra) = ? AND Month(data_hora_compra) = ? AND year(data_hora_compra) = ?";

//  salva os registro da tabela produto no banco de dados
    public int salvar(Produto produto){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        try {
            pstm = conn.prepareStatement(SQL_INSERT);
            pstm.setString(1, produto.getNome_prod());
            pstm.setString(2, produto.getDescricao_prod());
            pstm.setBigDecimal(3, produto.getPrecoCusto_prod());
            pstm.setBigDecimal(4,produto.getLucro_prod());
            pstm.setBigDecimal(5, produto.getPrecoVenda_prod());
            pstm.setInt(6, produto.getQuantidade_prod());
            pstm.setInt(7, produto.getEstoqueMinimo_prod());
            pstm.setString(8, produto.getMarca_prod());
            pstm.setInt(9,produto.getCategoria().getId_categoria());
            pstm.setLong(10,produto.getFornecedor().getId());
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

//  edita os registro da tabela produto no banco de dados
    public int editar(Produto produto) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, produto.getNome_prod());
            pstm.setString(2, produto.getDescricao_prod());
            pstm.setBigDecimal(3, produto.getPrecoCusto_prod());
            pstm.setBigDecimal(4,produto.getLucro_prod());
            pstm.setBigDecimal(5, produto.getPrecoVenda_prod());
            pstm.setInt(6, produto.getQuantidade_prod());
            pstm.setInt(7, produto.getEstoqueMinimo_prod());
            pstm.setString(8, produto.getMarca_prod());
            pstm.setInt(9,produto.getCategoria().getId_categoria());
            pstm.setLong(10,produto.getFornecedor().getId());
            pstm.setLong(11, produto.getId_prod());
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

//  deleta os registro da tabela produto no banco de dados
    public int deletar(Long id_prod) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        try {
            pstm = conn.prepareStatement(SQL_REMOVE);

            pstm.setLong(1, id_prod);
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

//  atualiza a quantidade de produtos no banco de dados
    public String atualizarQuantidade(Long idProduto, Integer quantidade) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        System.out.println("ID do produto: " + idProduto + " Quantidade: " + quantidade);
        try {
            pstm = conn.prepareStatement(SQL_UPDATE_QUANT_PROD);

            pstm.setInt(1, quantidade);
            pstm.setLong(2, idProduto);

            int result = pstm.executeUpdate();

            return result == 1 ? "Quantidade do produto editado com sucesso" : "Não foi possivel editar quantidade do produto";

        } catch (SQLException e) {
            e.printStackTrace();
            return String.format("Error: %s", e.getMessage());
        }
    }

//  faz uma busca no banco de dados do produto pela categoria
    public List<Produto> buscarProdutosPeloCategoria(String categoria) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = String.format("SELECT * FROM produto p, categoria c, fornecedor f WHERE p.id_categoria = c.id_categoria AND p.id_fornecedor = f.id AND c.nome_categoria = '%s'", categoria);
        List<Produto> produtos = new ArrayList<>();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while(rs.next()) {
                produtos.add(getProduto(rs));
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        return produtos;
    }

//  faz uma busca no banco de dados do produto pelo nome
    public Produto buscarProdutoPeloNome(String nome) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = String.format("SELECT * FROM produto p, categoria c, fornecedor f WHERE p.id_categoria = c.id_categoria AND p.id_fornecedor = f.id AND p.nome_prod = '%s'", nome);

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            if(rs.next()) {
                return getProduto(rs);
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
        return null;
    }

//  retorna o produto em si do banco de dados para reaproveitar o código, utilizado em outros momentos
    private Produto getProduto(ResultSet result) throws SQLException {
        Produto produto = new Produto();
        Categoria categoria = new Categoria();
        Fornecedor fornecedor = new Fornecedor();

        categoria.setId_categoria(result.getInt("c.id_categoria"));
        categoria.setNome_categoria(result.getString("c.nome_categoria"));
        categoria.setDescricao_categoria(result.getString("c.descricao_categoria"));

        fornecedor.setId(result.getLong("f.id"));
        fornecedor.setNome(result.getString("f.nome"));
        fornecedor.setEmpresa(result.getString("f.nomeEmpresa"));
        fornecedor.setCnpj(result.getString("f.cnpj"));
        fornecedor.setEmail(result.getString("f.email"));
        fornecedor.setTelefone(result.getString("f.telefone"));
        fornecedor.setEndereco(result.getString("f.endereco"));
        fornecedor.setCategoria(result.getString("f.categoria"));
        fornecedor.setDescricao(result.getString("f.descricao"));

        produto.setId_prod(result.getLong("p.id_prod"));
        produto.setNome_prod(result.getString("p.nome_prod"));
        produto.setDescricao_prod(result.getString("p.descricao_prod"));
        produto.setPrecoVenda_prod(result.getBigDecimal("p.precoVenda_prod"));
        produto.setQuantidade_prod(result.getInt("p.quantidade_prod"));


        produto.setId_prod(result.getLong("p.id_prod"));
        produto.setNome_prod(result.getString("p.nome_prod"));
        produto.setDescricao_prod(result.getString("p.descricao_prod"));
        produto.setPrecoCusto_prod(result.getBigDecimal("p.precoCusto_prod"));
        produto.setLucro_prod(result.getBigDecimal("lucro_prod"));
        produto.setPrecoVenda_prod(result.getBigDecimal("precoVenda_prod"));
        produto.setQuantidade_prod(result.getInt("quantidade_prod"));
        produto.setEstoqueMinimo_prod(result.getInt("estoqueMinimo_prod"));
        produto.setMarca_prod(result.getString("marca_prod"));

        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);

        return produto;
    }

//  lista todos os registro da tabela produto do banco de dados
    public List<Produto> listarProduto() {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        List<Produto> produtos = new ArrayList<Produto>();
        ResultSet result = null;
        try {
            pstm = conn.prepareStatement(SQL_FIND_ALL);
            result = pstm.executeQuery();
            while (result.next()) {
                produtos.add(getProduto(result));
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBConnection.close(conn, pstm, result);
            }
            e.printStackTrace();
        }
        return produtos;
    }

//  retorna o valor de custo total dos produtos do banco de dados
    public BigDecimal RetornaDespesaTotal(){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        BigDecimal total = null;

        try{
            pstm = conn.prepareStatement(SQL_DESPESA);
            rs = pstm.executeQuery();

            while (rs.next()){

                total = rs.getBigDecimal(1);
            }

            return total;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//  retorna o valor de custo por dia dos produtos do banco de dados
    public Double RetornaSaidaPorDia(String diaVenda, String mesVenda, String anoVenda){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Double totalValorDia = null;
        try{
            pstm = conn.prepareStatement(SQL_SAIDA_DIA);
            pstm.setString(1,diaVenda);
            pstm.setString(2,mesVenda);
            pstm.setString(3,anoVenda);
            rs = pstm.executeQuery();

            if(rs.next()){
                totalValorDia = rs.getDouble("SaidaDiaria");
            }
            return totalValorDia;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
