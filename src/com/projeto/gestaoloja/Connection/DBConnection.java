package com.projeto.gestaoloja.Connection;

import java.sql.*;

public class DBConnection {

    private static final String URL_MYSQL = "jdbc:mysql://localhost/loja";
    private static final String DRIVER_CLASS_MYSQL = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASS = "";

//  Conexão com o banco de dados
    public static Connection getConnection() {
        System.out.println("Conectando ao Banco de Dados");
        try {
            Class.forName(DRIVER_CLASS_MYSQL);
            return DriverManager.getConnection(URL_MYSQL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

//  Fechar conexão com o banco de dados
    public static void close(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//  Criação da tabela Usuario no banco de dados
    public static void createTableUsuario() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;

        String sql = "create table if not exists usuario(" +
                "id bigint primary key auto_increment," +
                "nome varchar(75) not null," +
                "usuario varchar(50) not null unique," +
                "senha varchar(75) not null," +
                "telefone varchar(75) not null," +
                "endereco varchar(100) not null," +
                "cargo varchar(10) not null" +
                ")";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
            System.out.println("Create Tables Usuario Ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, stmt, null);
        }
    }

//  Criação da tabela Cliente no banco de dados
    public static void createTableCliente() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;

        String sql = "create table if not exists cliente(" +
                "id bigint primary key auto_increment," +
                "nome varchar(75) not null," +
                "cpf varchar(50) not null," +
                "email varchar(75) not null," +
                "telefone varchar(50) not null," +
                "endereco varchar(75) not null" +
                ")";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
            System.out.println("Create Tables Cliente Ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, stmt, null);
        }
    }

//  Criação da tabela Fornecedor no banco de dados
    public static void createTableFornecedor() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;

        String sql = "create table if not exists fornecedor(" +
                "id bigint primary key auto_increment," +
                "nome varchar(75) not null," +
                "nomeEmpresa varchar(75) not null," +
                "cnpj varchar(50) not null unique," +
                "email varchar(75) not null," +
                "telefone varchar(75) not null," +
                "endereco varchar(50) not null," +
                "categoria varchar(75) not null," +
                "descricao varchar(400) not null" +
                ")";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
            System.out.println("Create Tables Fornecedor Ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, stmt, null);
        }
    }

//  Criação da tabela Categoria no banco de dados
    public static void createTableCategoria() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;

        String sql = "create table if not exists categoria(" +
                "id_categoria int primary key auto_increment," +
                "nome_categoria varchar(75)," +
                "descricao_categoria varchar(100)" +
                ")";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
            System.out.println("Create Tables Categoria Ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, stmt, null);
        }
    }

//  Criação da tabela Produto no banco de dados
    public static void createTableProduto() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;

        String sql = "create table if not exists produto(" +
                "id_prod bigint primary key auto_increment," +
                "nome_prod varchar(75) not null," +
                "quantidade_prod int null," +
                "estoqueMinimo_prod int null," +
                "precoCusto_prod decimal(10,2) not null," +
                "lucro_prod decimal(10,2) not null," +
                "precoVenda_prod decimal(10,2) not null," +
                "marca_prod varchar(100) not null," +
                "descricao_prod varchar(400)," +
                "id_categoria int not null," +
                "id_fornecedor bigint not null," +
                "data_hora_compra datetime default current_timestamp," +
                "constraint produto_categoria foreign key produto_categoria (id_categoria) references categoria(id_categoria)," +
                "constraint produto_fornecedor foreign key produto_fornecedor (id_fornecedor) references fornecedor (id)" +
                ")";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
            System.out.println("Create Tables Produto Ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, stmt, null);
        }
    }

//  Criação da tabela Venda no banco de dados
    public static void createTableVenda() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;

        String sql = "create table if not exists venda(" +
                "id int primary key auto_increment," +
                "total_venda decimal(10,2) not null," +
                "valor_pago decimal(10,2) not null," +
                "troco decimal(10,2)," +
                "desconto decimal(10,2)," +
                "cliente_id bigint not null," +
                "usuario_id bigint not null," +
                "data_hora_criacao datetime  default current_timestamp," +
                "constraint fk_venda_cliente foreign key fk_venda_cliente (cliente_id) references cliente(id)," +
                "constraint fk_venda_usuario foreign key fk_venda_usuario (usuario_id) references usuario(id)" +
                ")";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
            System.out.println("Create Tables Venda Ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, stmt, null);
        }
    }

//  Criação da tabela Venda Item no banco de dados
    public static void createTableVendaItem() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;

        String sql = "create table if not exists venda_item(" +
                "venda_id int not null," +
                "produto_id bigint not null," +
                "quantidade int not null," +
                "total decimal(10,2) not null," +
                "desconto decimal(10,2)," +
                "constraint fk_venda_item_venda foreign key fk_venda_item_venda (venda_id) references venda(id)," +
                "constraint fk_venda_item_produto foreign key fk_venda_item_produto (produto_id) references produto(id_prod)" +
                ")";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.   execute();
            System.out.println("Create Tables VendaItem Ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, stmt, null);
        }
    }

//  Criação da tabela Caixa no banco de dados
    public static void createTableCaixa() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;

        String sql = "create table if not exists caixa(" +
                "id_movimentacao int primary key auto_increment," +
                "valor_diario decimal(10,2) null," +
                "tipo varchar(50) null," +
                "data_inicial date null," +
                "data_final date null," +
                "valor_mensal decimal(10,2) null" +
                ")";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
            System.out.println("Create Tables Caixa Ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, stmt, null);
        }
    }
}
