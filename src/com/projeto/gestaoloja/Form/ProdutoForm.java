package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.controller.CategoriaController;
import com.projeto.gestaoloja.controller.FornecedorController;
import com.projeto.gestaoloja.controller.ProdutoController;
import com.projeto.gestaoloja.controller.VendaController;
import com.projeto.gestaoloja.modelo.entidades.Categoria;
import com.projeto.gestaoloja.modelo.entidades.Fornecedor;
import com.projeto.gestaoloja.modelo.entidades.Produto;
import com.projeto.gestaoloja.util.tableModel.ProdutoTableModel.ProdutoTableModel;
import com.projeto.gestaoloja.util.tableModel.CellRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoForm extends JFrame{
    private JPanel panelAdd;
    private JLabel lbNome;
    private JTextField txtNome;
    private JLabel lbPrecoCusto;
    private JTextField txtPrecoCusto;
    private JLabel lbPLucro;
    private JLabel lbQuantidade;
    private JLabel lbCategoria;
    private JLabel lbDescricao;
    private JTextField txtDescricao;
    private JPanel panelBotoes;
    private JButton cancelarButton;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton removerButton;
    private JButton novoButton;
    private JPanel panelTable;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel panelPesquisa;
    private JLabel lbBusca;
    private JTextField txtBusca;
    private JButton voltarButton;
    private JPanel main;
    private JSpinner spnrQuantidade;
    private JComboBox cBCategoria;
    private JTextField txtPLucro;
    private JLabel lbMarca;
    private JTextField txtMarca;
    private JButton adicionarNovaCategoriaButton;
    private JComboBox cBFornecedor;
    private JButton adicionarNovoFornecedorButton;
    private List<Produto> produtoList = new ProdutoController().listarProduto();
    private List<Categoria> categoriaList = new CategoriaController().listarCategoria();
    private List<Fornecedor> fornecedorList = new FornecedorController().listarFornecedor();
    private Long idProduto;

    public ProdutoForm() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setBounds(650,450,250,250);
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        //atualizarTabelaProduto(new ProdutoController().findProdutos());
        inicializarComboBoxCategoriaNoProduto();
        inicializarComboBoxFornecedorProduto();
        refreshTableProduto();
        enableFields(false);
        eventos();

    }

    private void inicializarComboBoxCategoriaNoProduto() {
        cBCategoria.removeAllItems();
        cBCategoria.addItem("Selecione a categoria");
        categoriaList.stream().forEach(c -> {
            cBCategoria.addItem(c.getNome_categoria());
        });
    }

    private void inicializarComboBoxFornecedorProduto() {
        cBFornecedor.removeAllItems();
        cBFornecedor.addItem("Selecione um fornecedor");
        fornecedorList.stream().forEach(f -> {
            cBFornecedor.addItem(f.getNome().concat(" - " + f.getCategoria()));
        });
    }

    private void novoProduto(){
        enableFields(true);
    }

    private void enableFields(boolean b){
        txtNome.setEnabled(b);
        txtPrecoCusto.setEnabled(b);
        txtPLucro.setEnabled(b);
        spnrQuantidade.setEnabled(b);
        cBCategoria.setEnabled(b);
        cBFornecedor.setEnabled(b);
        txtDescricao.setEnabled(b);
        txtMarca.setEnabled(b);
        adicionarNovaCategoriaButton.setEnabled(b);
        adicionarNovoFornecedorButton.setEnabled(b);
    }

    private void cancelarProduto(){
        txtNome.setText("");
        txtPrecoCusto.setText("");
        txtPLucro.setText("");
        spnrQuantidade.setValue(0);
        cBCategoria.setSelectedIndex(0);
        cBFornecedor.setSelectedIndex(0);
        txtDescricao.setText("");
        txtMarca.setText("");
        enableFields(false);
        table.getSelectionModel().clearSelection();
    }

    private void salvarProduto() {

     //   Produto produto = new Produto();
        if (txtNome.getText().length() > 0 && txtDescricao.getText().length() > 0 && txtMarca.getText().length() > 0 && txtPLucro.getText().length() > 0 && txtPrecoCusto.getText().length() > 0) {
        String nome = txtNome.getText();
        String quantidadeString = spnrQuantidade.getValue().toString();
        String marca = txtMarca.getText();
        String precoCustoString = txtPrecoCusto.getText();
        String pLucro = txtPLucro.getText();
        String descricao = txtDescricao.getText();
        String categoriaTemp = cBCategoria.getSelectedItem().toString();
        String fornecedorTemp = cBFornecedor.getSelectedItem().toString();


        Integer quantidade = Integer.valueOf(quantidadeString);
        BigDecimal precoCusto = new BigDecimal(precoCustoString);

        String[] fornecedorAux = fornecedorTemp.trim().split(" ");
        String fornecedorString = (fornecedorAux[0]);

        Categoria categoria = new CategoriaController().buscarCategoriaNome(categoriaTemp);
        Fornecedor fornecedor = new FornecedorController().buscarFornecedorNome(fornecedorString);

        if (fornecedor == null) {
            JOptionPane.showMessageDialog(this, "Selecione ou cadastre um fornecedor!");
        }

        if (categoria == null) {
            JOptionPane.showMessageDialog(this, "Selecione ou cadastre uma categoria!");
        }

        BigDecimal decimalLucro = new BigDecimal(pLucro);
        BigDecimal lucroProduto = precoCusto.multiply (decimalLucro.divide(BigDecimal.valueOf(100)));

        BigDecimal novoPrecoVenda = lucroProduto.add(precoCusto);
        int estoque_minimo = 1;


        Produto produto = new Produto(null,quantidade,estoque_minimo,marca,nome,descricao,novoPrecoVenda,precoCusto,decimalLucro,null,categoria, fornecedor);

        int result = 0;
        if(idProduto == null){
            result  = new FornecedorController().fornecer(produto);
        } else {
            produto.setId_prod(idProduto);
            result = new ProdutoController().editarProduto(produto);
            idProduto = null;
        }

        if(result == 1){
            JOptionPane.showMessageDialog(this, "Produto inserido com sucesso!");
            enableFields(false);
            cancelarProduto();
            refreshTableProduto();
        } else{
            JOptionPane.showMessageDialog(this, "Tente novamente!");
        }
        }else{
            JOptionPane.showMessageDialog(ProdutoForm.this,
                    "Todos os campos são obrigatorios",
                    "ATENÇÃO!!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void editarProduto() {
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o produto a ser alterado!");
            return;
        }

        Produto produto = new ProdutoTableModel(produtoList).get(rowIndex);

        idProduto = produto.getId_prod();

        txtNome.setText(produto.getNome_prod());
        txtPrecoCusto.setText(String.valueOf(produto.getPrecoCusto_prod()));
        txtPLucro.setText(String.valueOf(produto.getLucro_prod()));
        spnrQuantidade.setValue(produto.getQuantidade_prod());
        cBCategoria.setSelectedItem(produto.getCategoria().getNome_categoria());
        cBFornecedor.setSelectedItem(produto.getFornecedor().getNome().concat(" - " + produto.getFornecedor().getCategoria()));
        txtMarca.setText(produto.getMarca_prod());
        txtDescricao.setText(produto.getDescricao_prod());
        enableFields(true);
        table.getSelectionModel().clearSelection();
    }

    private void deletarProduto() {
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o produto a ser removido!");
            return;
        }
        VendaController venda = new VendaController();
        Produto produto = new ProdutoTableModel(produtoList).get(rowIndex);

        if(venda.listarProdutosVendaItem(produto.getId_prod()) ==0) {


        int confirm = JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Deletar produto", JOptionPane.YES_NO_OPTION);

        if (confirm != 0) {
            return;
        }

        int result = new ProdutoController().deletarProduto(produto.getId_prod());

            if (result == 1) {
                JOptionPane.showMessageDialog(this, "Produto deletado com sucesso!");
                atualizarTabelaProduto(new ProdutoController().listarProduto());
            } else {
                JOptionPane.showMessageDialog(this, "Tente novamente!");
            }
        }else {
            JOptionPane.showMessageDialog(this, "Impossivel deletar, produto salvo em vendas!");
        }
        table.getSelectionModel().clearSelection();
    }

    private void pesquisar() {
        String pesquisar = txtBusca.getText();

        if (pesquisar.isEmpty()) {
            atualizarTabelaProduto(new ProdutoController().listarProduto());
        } else {
            List<Produto> produtosTemp =new ProdutoController().listarProduto()
                    .stream()
                    .filter((Produto p) -> {
                        return p.getNome_prod().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                p.getMarca_prod().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                p.getCategoria().getNome_categoria().toLowerCase().contains(pesquisar.toLowerCase());
                    })
                    .collect(Collectors.toList());

            atualizarTabelaProduto(produtosTemp);
        }
    }

    private void voltar(){
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.hide();
    }

    private void atualizarTabelaProduto(List<Produto> produtos) {
        ProdutoTableModel produtoTableModel = new ProdutoTableModel(produtos);
        table.setModel(produtoTableModel);
        table.setDefaultRenderer(Object.class, new CellRenderer());

    }
    private void refreshTableProduto() {
        produtoList = new ProdutoController().listarProduto();
        if (produtoList != null) {
            table.setModel(new ProdutoTableModel(produtoList));
            table.setDefaultRenderer(Object.class, new CellRenderer());
        }
    }

    private void eventos(){
        adicionarNovaCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoriaForm telaCategoria = new CategoriaForm();
                telaCategoria.setVisible(true);
                hide();
            }
        });

        novoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                novoProduto();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarProduto();
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                salvarProduto();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProduto();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarProduto();
            }
        });

        txtBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                pesquisar();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });

        adicionarNovoFornecedorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FornecedorForm fornecedorForm = new FornecedorForm();
                fornecedorForm.setVisible(true);
                hide();
            }
        });
    }

    private void setIcon(){
        URL url = this.getClass() .getResource ("/imagens/carrinhoApp.png");
        Image imgTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this. setIconImage(imgTitulo);

    }
}
