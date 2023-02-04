package com.projeto.gestaoloja.dao;

import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.modelo.entidades.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaDao {

    private static final String SQL_INSERT =
            "INSERT INTO venda(total_venda, valor_pago, troco, desconto, cliente_id, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_ENTRADA =
            "SELECT sum(total_venda) AS Entrada FROM venda";

    private static final String SQL_ENTRADA_DIA =
            "SELECT  sum(total_venda) As VendaDiaria FROM venda\n" +
                    "WHERE DAY(data_hora_criacao) = ? AND Month(data_hora_criacao) = ? AND year(data_hora_criacao) = ?";

    private ProdutoDao produtoDao;
    public VendaDao() {
        produtoDao = new ProdutoDao();
    }

//  salva os registro da tabela venda no banco de dados
    public int salvar(Venda venda) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        try {
            pstm = conn.prepareStatement(SQL_INSERT);

            pstm.setBigDecimal(1, venda.getTotalVenda());
            pstm.setBigDecimal(2, venda.getValorPago());
            pstm.setBigDecimal(3,venda.getTroco());
            pstm.setBigDecimal(4, venda.getDesconto());
            pstm.setLong(5, venda.getCliente().getId());
            pstm.setLong(6, venda.getUsuario().getId());

            result = pstm.executeUpdate();

            if (result == 1) {
                Long idDaVenda = buscarIdDaUltimaVenda();
                System.out.println("ID DA VENDA: " + idDaVenda);

                venda.setId(idDaVenda);
                venda.getVendasDetalhes()
                        .values()
                        .stream()
                        .forEach(vd -> {
                            vd.setVenda(venda);
// a cada produto vendido, diminui a quantidade do mesmo no estoque

                            final int quantidade = vd.getProduto().getQuantidade_prod() - vd.getQuantidade();

                            String mensagem = produtoDao.atualizarQuantidade(vd.getProduto().getId_prod(),
                                    quantidade);

                            System.out.println(mensagem);
                            adicionarVendaItem(vd);
                        });
            }
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

//  salva os registro da tabela venda Item no banco de dados
    public void adicionarVendaItem(VendaItem vendaItem) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        String sql = "INSERT INTO venda_item(venda_id, produto_id, quantidade, total, desconto) VALUES(?, ?, ?, ?, ?)";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setLong(1, vendaItem.getVenda().getId());
            pstm.setLong(2, vendaItem.getProduto().getId_prod());
            pstm.setInt(3, vendaItem.getQuantidade());
            pstm.setBigDecimal(4, vendaItem.getTotal());
            pstm.setBigDecimal(5, vendaItem.getDesconto());

            int result = pstm.executeUpdate();

            String mensagem = result == 1 ? "Venda Detalhes adicionado com sucesso." : "Nao foi possivel adiconar Venda detalhes";
            System.out.println(mensagem);
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
    }

//  retorna a venda item em si do banco de dados para reaproveitar o código, utilizado em outros momentos
    public VendaItem getVendaDetalhes(ResultSet result) throws SQLException {
        VendaItem vendaItem = new VendaItem();
        Venda venda = new Venda();
        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();
        Produto produto = new Produto();

        cliente.setId(result.getLong("c.id"));
        cliente.setNome(result.getString("c.nome"));

        usuario.setId(result.getLong("u.id"));
        usuario.setNome(result.getString("u.nome"));

        venda.setId(result.getLong("v.id"));
        venda.setTotalVenda(result.getBigDecimal("v.total_venda"));
        venda.setValorPago(result.getBigDecimal("v.valor_pago"));
        venda.setTroco(result.getBigDecimal("v.troco"));
        venda.setDesconto(result.getBigDecimal("v.desconto"));
        venda.setDataHoraCriacao(result.getObject("v.data_hora_criacao", LocalDateTime.class));

        venda.setCliente(cliente);
        venda.setUsuario(usuario);

        produto.setId_prod(result.getLong("p.id_prod"));
        produto.setNome_prod(result.getString("p.nome_prod"));
        produto.setPrecoVenda_prod(result.getBigDecimal("p.precoVenda_prod"));

        vendaItem.setQuantidade(result.getInt("vi.quantidade"));
        vendaItem.setDesconto(result.getBigDecimal("vi.desconto"));
        vendaItem.setTotal(result.getBigDecimal("vi.total"));
        vendaItem.setVenda(venda);
        vendaItem.setProduto(produto);

        return vendaItem;
    }

//  faz uma contagem de registros da venda item pelo id do produto no banco de dados
    public Long listarProdutosVendaItem(Long id){
        String sql = String.format("SELECT count(*) FROM venda_item WHERE produto_id = %d", id);
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

//  lista todos os registro da tabela venda do banco de dados
    public List<Venda> listarTodasVendas() {
        String sql = "SELECT * FROM venda v, cliente c, usuario u WHERE v.cliente_id = c.id AND v.usuario_id = u.id ORDER BY v.id";
        List<Venda> vendas = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()) {
                vendas.add(getVenda(rs));
            }

        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

        return vendas;
    }

// faz uma busca do Id da ultima venda no banco de dados
    public Long buscarIdDaUltimaVenda() {
        String sql = "SELECT max(id) FROM venda";

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

//  retorna a venda em si do banco de dados para reaproveitar o código, utilizado em outros momentos
    public Venda getVenda(ResultSet result) throws SQLException {
        Venda venda = new Venda();
        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();

        cliente.setId(result.getLong("c.id"));
        cliente.setNome(result.getString("c.nome"));

        usuario.setId(result.getLong("u.id"));
        usuario.setNome(result.getString("u.nome"));

        venda.setId(result.getLong("v.id"));
        venda.setTotalVenda(result.getBigDecimal("v.total_venda"));
        venda.setValorPago(result.getBigDecimal("v.valor_pago"));
        venda.setTroco(result.getBigDecimal("v.troco"));
        venda.setDesconto(result.getBigDecimal("v.desconto"));
        venda.setDataHoraCriacao(result.getObject("v.data_hora_criacao", LocalDateTime.class));

        venda.setCliente(cliente);
        venda.setUsuario(usuario);

        return venda;
    }

//  lista todos os registro da tabela venda item do banco de dados
    public List<VendaItem> buscaDetalhesDaVendaPeloId(Long id) {
        String sql = String.format("select * from venda v, venda_item vi, produto p, cliente c, usuario u where v.cliente_id = c.id and v.usuario_id = u.id and vi.venda_id = v.id \n" +
                " and vi.produto_id = p.id_prod and v.id = %d", id);
        List<VendaItem> vendaDetalhes = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()) {
                vendaDetalhes.add(getVendaDetalhes(rs));
            }

        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

        return vendaDetalhes;
    }

// retorna o valor da entrada/ vendas total no banco de dados
    public BigDecimal RetornaEntradaTotal(){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        BigDecimal total = null;

        try{
            pstm = conn.prepareStatement(SQL_ENTRADA);
            rs = pstm.executeQuery();

            while (rs.next()){
                total = rs.getBigDecimal(1);
            }

            return total;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

// retorna o valor da entrada/ vendas por dia no banco de dados
    public Double RetornaEntradaPorDia(String diaVenda, String mesVenda, String anoVenda){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Double totalValorDia = null;
        try{
            pstm = conn.prepareStatement(SQL_ENTRADA_DIA);
            pstm.setString(1,diaVenda);
            pstm.setString(2,mesVenda);
            pstm.setString(3,anoVenda);
            rs = pstm.executeQuery();

            if(rs.next()){
                totalValorDia = rs.getDouble("VendaDiaria");
            }

            return totalValorDia;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
