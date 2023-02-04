package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.controller.ClienteController;
import com.projeto.gestaoloja.controller.ProdutoController;
import com.projeto.gestaoloja.controller.VendaController;
import com.projeto.gestaoloja.dao.VendaDao;
import com.projeto.gestaoloja.modelo.entidades.Cliente;
import com.projeto.gestaoloja.modelo.entidades.Produto;
import com.projeto.gestaoloja.modelo.entidades.Venda;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class Dashboard extends JFrame{
    private JButton produtosButton;
    private JButton clientesButton;
    private JButton vendasButton;
    private JButton usuariosButton;
    private JButton sairButton;
    private JPanel main;
    private JButton caixaButton;
    private JButton fornecedorButton;
    private JLabel lbHomeProduto;
    private JLabel lbHomeClientes;
    private JLabel lbHomeVendas;

    private List<Cliente> clientes = new ClienteController().listarClientes();
    private List<Produto> produtos = new ProdutoController().listarProduto();
    List<Venda> vendas = new VendaController().listarTodasVendas();

    public Dashboard() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        eventos();

    }

    private void eventos(){

        caixaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                CaixaForm telaCaixa = new CaixaForm();
                telaCaixa.setVisible(true);
                hide();


            }
        });
        produtosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProdutoForm telaProduto = new ProdutoForm();
                telaProduto.setVisible(true);
                hide();
            }
        });

        vendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendaForm telaVenda = new VendaForm();
                telaVenda.setVisible(true);
                hide();
            }
        });

        usuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioForm telaUsuario = new UsuarioForm();
                telaUsuario.setVisible(true);
                hide();
            }
        });

        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirma = JOptionPane.showConfirmDialog(null, "Tens certeza que desejas sair?","Sair do login", JOptionPane.YES_NO_OPTION);

                if(confirma == JOptionPane.YES_OPTION) System.exit(0);
            }
        });

        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteForm telaCliente = new ClienteForm();
                telaCliente.setVisible(true);
                hide();
            }
        });

        fornecedorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FornecedorForm telaFornecedor = new FornecedorForm();
                telaFornecedor.setVisible(true);
                hide();
            }
        });


        lbHomeClientes.setText(String.format("%d",clientes.size()));
        lbHomeProduto.setText(String.format("%d",produtos.size()));
        lbHomeVendas.setText(String.format("%d",vendas.size()));
    }

    private void setIcon(){
        URL url = this.getClass() .getResource ("/imagens/carrinhoApp.png");
        Image imgTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this. setIconImage(imgTitulo);

    }
}
