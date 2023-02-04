package com.projeto.gestaoloja.Form;


import com.projeto.gestaoloja.controller.VendaController;
import com.projeto.gestaoloja.dao.VendaDao;
import com.projeto.gestaoloja.modelo.entidades.Venda;
import com.projeto.gestaoloja.modelo.entidades.VendaItem;
import com.projeto.gestaoloja.util.tableModel.CellRenderer;
import com.projeto.gestaoloja.util.tableModel.VendaTableModel.VendaTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class VendaForm extends JFrame{
    private JButton voltarButton;
    private JPanel panelPesquisa;
    private JLabel lbBusca;
    private JTextField txtBusca;
    private JPanel panelBotoes;
    private JButton detalhesButton;
    private JButton novoButton;
    private JPanel panelTable;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel main;
    private VendaDao vendaDao;
    List<Venda> vendasList = new VendaController().listarTodasVendas();
    private List<VendaItem> vendaDetalhes;
    private VendaTableModel vendaTableModel;


    public VendaForm() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setBounds(650,450,250,250);
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        refreshTableVendas();
//        atualizarTabelaVenda();
//        atualizarTabelaCliente(new ClienteController().findClientes());
//        enableFields(false);
        eventos();
    }

    private void atualizarTabelaVendas(List<Venda> vendas) {
        VendaTableModel vendaTableModel = new VendaTableModel(vendas);
        table.setModel(vendaTableModel);
        table.setDefaultRenderer(Object.class, new CellRenderer());

    }

    private void refreshTableVendas() {
        vendasList = new VendaController().listarTodasVendas();
        if (vendasList != null) {
            table.setModel(new VendaTableModel(vendasList));
            table.setDefaultRenderer(Object.class, new CellRenderer());
        }
    }

    private void voltar() {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.hide();

    }

    private void pesquisar(){
        String pesquisar = txtBusca.getText();

        List<Venda> vendaList =new VendaController().listarTodasVendas()
                .stream()
                .filter((Venda v) -> {
                    return v.getCliente().getNome().toLowerCase().contains(pesquisar.toLowerCase())||
                            v.getUsuario().getNome().toLowerCase().contains(pesquisar.toLowerCase());

                })
                .collect(Collectors.toList());

        atualizarTabelaVendas(vendaList);
    }

    private void detalhes(){
        if(this.vendaDetalhes != null) {
            StringBuilder produtoDaVenda = new StringBuilder();

            vendaDetalhes.stream().forEach(v -> {
                produtoDaVenda.append(String.format("%s - ", v.getProduto().getNome_prod()));
                produtoDaVenda.append(String.format("%s   -     ", v.getProduto().getPrecoVenda_prod().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
                produtoDaVenda.append(String.format("%d   -     ", v.getQuantidade()));
                produtoDaVenda.append(String.format("%s   -     ", v.getDesconto().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
                produtoDaVenda.append(String.format("%s  ", v.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
                produtoDaVenda.append("\n");
            });

            JOptionPane.showMessageDialog(this,
                    String.format("Detalhes da venda com ID: %d \n\n"
                                    + "__________________________________________\n"
                                    + "Nome do cliente: %s \n"
                                    + "Total da venda: %s \n"
                                    + "Data da venda: %s \n"
                                    + "Vendido por: %s \n"
                                    + "__________________________________________\n"
                                    + "Produto - Preço - Quantidade - Desconto - Total \n"
                                    + "__________________________________________\n"
                                    + "%s",
                            this.vendaDetalhes.get(0).getVenda().getId(),
                            this.vendaDetalhes.get(0).getVenda().getCliente().getNome(),
                            this.vendaDetalhes.get(0).getVenda().getTotalVenda(),
                            this.vendaDetalhes.get(0).getVenda().getDataHoraCriacao(),
                            this.vendaDetalhes.get(0).getVenda().getUsuario().getNome(),
                            produtoDaVenda.toString()
                    )
            );
        }else {
            JOptionPane.showMessageDialog(this, "Seleciona um elemento na tabela", "Sem venda selecionada", 0);
        }
    }

    private void eventos() {
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });

        novoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrarVendaForm registrarVendaForm = new RegistrarVendaForm();
                registrarVendaForm.setVisible(true);
                hide();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowIndex = table.getSelectedRow();
                Long idVenda = new VendaTableModel(vendasList).get(rowIndex).getId();
                vendaDetalhes = new VendaController().buscaDetalhesDaVendaPeloId(idVenda);
                System.out.println(vendaDetalhes);;

            }
        });

        txtBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                pesquisar();

            }
        });
        detalhesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detalhes();
            }
        });
    }

    private void setIcon(){
        URL url = this.getClass() .getResource ("/imagens/carrinhoApp.png");
        Image imgTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this. setIconImage(imgTitulo);

    }
}
