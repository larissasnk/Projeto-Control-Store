package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.controller.*;
import com.projeto.gestaoloja.modelo.entidades.*;
import com.projeto.gestaoloja.exception.NegocioException;
import com.projeto.gestaoloja.util.tableModel.VendaRegistroTableModel.VendaRegistroCellRenderer;
import com.projeto.gestaoloja.util.tableModel.VendaRegistroTableModel.VendaRegistroTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrarVendaForm extends JFrame {
    private JPanel main;
    private JTable table;
    private JButton adicionarButton;
    private JButton venderButton;
    private JButton voltarButton;
    private JComboBox comboBoxCategoria;
    private JComboBox comboBoxProduto;
    private JSpinner spinnerVendaQuanti;
    private JButton removerButton;
    private JTextField txtValorPago;
    private JTextField txtDesconto;
    private JCheckBox checkBoxEnableDesconto;
    private JButton limparButton;
    private JLabel lbPreco;
    private JLabel lbProdutoNome;
    private JLabel lbProdutoQuant;
    private JLabel lbVendaQuanti;
    private JLabel lbValorPago;
    private JLabel lbDesconto;
    private JLabel lbDescTotalVenda;
    private JLabel lbDescValorPago;
    private JLabel lbDescDesconto;
    private JLabel lbValorTotalCalculado;
    private JLabel lbValorPagoCalculado;
    private JLabel lbDescontoCalculado;
    private JLabel lbMensagem;
    private JScrollPane Jtable;
    private JPanel panelMensagem;
    private JLabel lbProdutoPrecoDetalhe;
    private JLabel lbProdutoNomeDetalhe;
    private JLabel lbProdutoQuantDetalhe;
    private JLabel lbDescTroco;
    private JLabel lbTrocoCalculado;
    private JComboBox comboBoxCliente;
    private JComboBox comboBoxFuncionario;
    private Produto produto;
    private CategoriaController categoriaController;
    private List<Produto> produtoList = new ProdutoController().listarProduto();
    private List<Cliente> clienteList = new ClienteController().listarClientes();
    private List<Usuario> usuarioList = new UsuarioController().listarUsuario();
    private HashMap<String, VendaItem> vendaDetalhesCarrinho;
    private ProdutoController produtoController;
    private String nomeDoProduto;

    public RegistrarVendaForm() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setBounds(650,450,250,250);
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        VendaController vendaController = new VendaController();
        produtoController = new ProdutoController();
        inicializarComboBoxCategoria();
        inicializarComboBoxProduto();
        inicializarComBoXCliente();
        inicializarComBoxFuncionario();
        vendaDetalhesCarrinho= new HashMap<>();
        atualizarCarrinho(vendaDetalhesCarrinho);
        enableFields(false);
        eventos();

    }

    private void inicializarComboBoxCategoria() {
        categoriaController = new CategoriaController();

        comboBoxCategoria.removeAllItems();
        comboBoxCategoria.addItem("Selecione");

        inicializarComboBoxProduto();

        categoriaController.listarCategoria()
                .stream()
                .forEach(c -> comboBoxCategoria
                        .addItem(c.getNome_categoria()));

    }

    private void inicializarComboBoxProduto() {
        comboBoxProduto.removeAllItems();
        comboBoxProduto.addItem("Selecione");
    }

    private void inicializarComBoXCliente(){
        comboBoxCliente.removeAllItems();
        comboBoxCliente.addItem("Selecione o cliente");
        clienteList.stream().forEach(c -> {
            comboBoxCliente.addItem(c.getNome().concat(" de CPF :" + c.getCpf()));
        });
    }

    private void inicializarComBoxFuncionario(){
        comboBoxFuncionario.removeAllItems();
        comboBoxFuncionario.addItem("Selecione o funcionario");
        usuarioList.stream().forEach(u -> {
            comboBoxFuncionario.addItem(u.getNome().concat(" - " + u.getCargo()));
        });
    }


    private void pesquisarProdutoPeloCategoria() {
        inicializarComboBoxProduto();
        String categoria = comboBoxCategoria.getSelectedItem().toString();

        if(!categoria.equals("Selecione")) {
            List<Produto> produtosTemp = produtoController.buscarProdutosPeloCategoria(categoria);
            produtosTemp
                    .stream()
                    .forEach(p -> comboBoxProduto.addItem(p.getNome_prod()));
        }
    }

    private void selecionarProdutoNaComboBox() {
        if(comboBoxProduto.getSelectedIndex() > 0) {
            String produtoSelecionado = comboBoxProduto.getSelectedItem().toString();
            this.produto = produtoController.buscarProdutoPeloNome(produtoSelecionado);
            if(produto != null)
                detalhesDoProduto();
        }
    }
    private void detalhesDoProduto() {
        lbProdutoPrecoDetalhe.setText(this.produto.getPrecoVenda_prod().toString());
        lbProdutoNomeDetalhe.setText(this.produto.getNome_prod().toString());
        lbProdutoQuantDetalhe.setText(produto.getQuantidade_prod().toString());
    }

    private void informacaoPadraoDaLabelVendaProduto() {
        lbProdutoPrecoDetalhe.setText("0.00");
        lbProdutoQuantDetalhe.setText("0");
        lbProdutoNomeDetalhe.setText("");
        spinnerVendaQuanti.setValue(1);
        txtDesconto.setText("0");
        lbDescontoCalculado.setText("0.00");
        lbValorPagoCalculado.setText("0.00");
        lbValorTotalCalculado.setText("0.00");
        lbTrocoCalculado.setText("0.00");
        this.produto = null;
    }

    private void adicionarProdutoNoCarrinho(){
        if(produto != null) {
            int quantidadeExistente = 0;

            if(vendaDetalhesCarrinho.containsKey(this.produto.getNome_prod())) {
                quantidadeExistente = vendaDetalhesCarrinho.get(this.produto.getNome_prod()).getQuantidade();
            }

            VendaItem vendaItemTemp = new VendaItem();
            String quantidadeString = spinnerVendaQuanti.getValue().toString();
            String descontoString = txtDesconto.getText();

           validacaoDoCampo(quantidadeString, "quantidade");

            Integer quantidade = validacaoDaQuantidadeSeENumero(quantidadeString);
            quantidade += quantidadeExistente;

            validacaoDaQuantidade(quantidade);
            validacaoDoQuantidadeDoProdutoMaiorQueSolicitado(quantidade);

            BigDecimal desconto = validacaoDaPrecoSeENumero(descontoString);
            BigDecimal total = this.produto.getPrecoVenda_prod()
                    .multiply(BigDecimal.valueOf((quantidade))).subtract(desconto);


            vendaItemTemp.setProduto(this.produto);
            vendaItemTemp.setQuantidade(quantidade);
//            vendaDetalhesTemp.setDesconto(desconto.multiply(BigDecimal.valueOf(quantidade)));
            vendaItemTemp.setDesconto(desconto);
            vendaItemTemp.setTotal(total);

            this.vendaDetalhesCarrinho.put(this.produto.getNome_prod(), vendaItemTemp);

            atualizarCarrinho(vendaDetalhesCarrinho);
            atualizarTotalDaVenda();

        } else {
            mensagemNaTela("Não tem produto selecionado", Color.RED);
        }
    }

    private void atualizarCarrinho(HashMap<String, VendaItem> vendaDetalhess) {
        VendaRegistroTableModel vendaRegistroTableModel = new VendaRegistroTableModel(vendaDetalhess);
        table.setModel(vendaRegistroTableModel);
        table.setDefaultRenderer(Object.class, new VendaRegistroCellRenderer());
    }


    private void atualizarTotalDaVenda() {
        double totalVenda = this.vendaDetalhesCarrinho.values()
                .stream()
                .collect(Collectors.summingDouble(v -> v.getTotal().doubleValue()));

        double totalDesconto = this.vendaDetalhesCarrinho.values()
                .stream()
                .collect(Collectors.summingDouble(v -> v.getDesconto().doubleValue()));

        lbValorTotalCalculado.setText(new BigDecimal(totalVenda).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
        lbDescontoCalculado.setText(new BigDecimal(totalDesconto).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
    }

    private void removerProdutoDoCarrinho() {
        int linhaSelecionada = table.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o produto a ser removido!");
            return;
        }
        this.nomeDoProduto = (String) table.getModel().getValueAt(linhaSelecionada, 0);
            vendaDetalhesCarrinho.remove(this.nomeDoProduto);
            atualizarTotalDaVenda();
            atualizarCarrinho(vendaDetalhesCarrinho);

    }
    private void enableFields(boolean b){
        txtDesconto.setEnabled(b);
    }

    private void voltar() {
        VendaForm telaVenda = new VendaForm();
        telaVenda.setVisible(true);
        this.hide();
    }

    private void validacaoDoCampo(String campo, String nomeDaVariavel) {
        if(campo.isEmpty()) {
            String mensagem = String.format("Deve preencher o campo %s", nomeDaVariavel);
            mensagemNaTela(mensagem, Color.RED);
            throw new NegocioException(mensagem);
        }
    }

    private void validacaoDaQuantidade(Integer quantidade) {
        if(quantidade <= 0) {
            String mensagem = String.format("A quantidade não pode ser um numero negativo ou menor que zero");
            mensagemNaTela(mensagem, Color.RED);
            throw new NegocioException(mensagem);
        }
    }

    private void validacaoDoQuantidadeDoProdutoMaiorQueSolicitado(int quantidade) {
        if(this.produto.getQuantidade_prod() < quantidade) {
            mensagemNaTela("Quantidade indisponivel", Color.RED);
            throw new NegocioException("Quantidade indisponivel");
        }
    }

    private Integer validacaoDaQuantidadeSeENumero(String quantidadeString) {
        try {
            Integer quantidade = Integer.valueOf(quantidadeString);
            return quantidade;
        } catch (NumberFormatException e) {
            mensagemNaTela("Deve inserir apenas numero.", Color.RED);
            return 0;
        }
    }

    private BigDecimal validacaoDaPrecoSeENumero(String precoString) {
        try {
            BigDecimal preco = new BigDecimal(precoString).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            return preco;
        } catch (Exception e) {
            mensagemNaTela("Deve inserir apenas numeros.", Color.RED);
            return BigDecimal.ONE;
        }
    }

    private void mensagemNaTela(String mensagem, Color color) {
        panelMensagem.setBackground(color);
        lbMensagem.setBackground(Color.WHITE);
        lbMensagem.setText(mensagem);
    }

    private void limparCampo() {
        spinnerVendaQuanti.setValue(1);
        txtDesconto.setText("0");
        enableFields(false);
        txtValorPago.setText("");
        this.vendaDetalhesCarrinho= new HashMap<>();
        this.produto = null;
        mensagemNaTela(String.format(""), Color.white);

    }

    private void vender() {

        String valorPagoString = txtValorPago.getText();
        String totalDaVendaString = lbValorTotalCalculado.getText();
        String descontoTotalString = lbDescontoCalculado.getText();
        String trocoString = lbTrocoCalculado.getText();
        String clienteString = comboBoxCliente.getSelectedItem().toString();
        String usuarioString = comboBoxFuncionario.getSelectedItem().toString();

        String[] clienteAux = clienteString.trim().split(" ");
        String[] usuarioAux = usuarioString.trim().split(" ");


        BigDecimal valorPago = validacaoDaPrecoSeENumero(valorPagoString);
        BigDecimal totalDaVenda = validacaoDaPrecoSeENumero(totalDaVendaString);
        BigDecimal descontoTotal = new BigDecimal(descontoTotalString).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal trocoDecimal = new BigDecimal(trocoString).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        String clienteTemp = (clienteAux[0]);
        String usuarioTemp = (usuarioAux[0]);

        Cliente cliente = new ClienteController().buscarClienteNome(clienteTemp);
        Usuario usuario = new UsuarioController().buscarUsuarioNome(usuarioTemp);

        if(vendaDetalhesCarrinho.isEmpty()) {
            mensagemNaTela("Não há produtos na lista", Color.RED);
            throw new NegocioException("Não há produtos na lista");
        }

        validacaoDoCampo(valorPagoString, "valor pago");
        validacaoDoCampo(clienteTemp, "buscar cliente");
        if (cliente == null) {
            mensagemNaTela(String.format(" Insira um cliente"), Color.RED);
            throw new NegocioException("Cliente não existe");
        }

        if (usuario == null) {
            mensagemNaTela(String.format(" Insira um funcionario"), Color.RED);
            throw new NegocioException("Funcionario não existe");
        }

        if(valorPago.doubleValue() >= totalDaVenda.doubleValue()) {

            trocoDecimal = valorPago.subtract(totalDaVenda);

            Venda venda = new Venda(null, cliente, usuario, totalDaVenda, valorPago, trocoDecimal, descontoTotal, LocalDateTime.now(), vendaDetalhesCarrinho);


            System.out.println(venda);

            int result = 0;

            result  = new UsuarioController().vender(venda);

            if(result == 1){
                JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!");

            } else{
                JOptionPane.showMessageDialog(this, "Tente novamente!");
            }

            lbValorPagoCalculado.setText(valorPago.toString());
            lbTrocoCalculado.setText(trocoDecimal.toString());
            mensagemNaTela(String.format(""), Color.white);

        }else {
            mensagemNaTela("Valor pago não pode ser menor que o total da venda", Color.RED);
        }

    }

    private void eventos() {
        informacaoPadraoDaLabelVendaProduto();

        checkBoxEnableDesconto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBoxEnableDesconto.isSelected()){
                    enableFields(true);
                }
                else {
                    enableFields(false);
                    txtDesconto.setText("0");
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProdutoNoCarrinho();
            }
        });
        comboBoxCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisarProdutoPeloCategoria();
            }
        });
        comboBoxProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarProdutoNaComboBox();
            }
        });
        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampo();
            }
        });
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProdutoDoCarrinho();
            }
        });
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vender();
            }
        });
    }

    private void setIcon(){
        URL url = this.getClass() .getResource ("/imagens/carrinhoApp.png");
        Image imgTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this. setIconImage(imgTitulo);

    }
}
