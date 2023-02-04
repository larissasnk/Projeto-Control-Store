package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.controller.ClienteController;
import com.projeto.gestaoloja.modelo.entidades.Cliente;
import com.projeto.gestaoloja.util.MaskCampos;
import com.projeto.gestaoloja.util.tableModel.ClienteTableModel.ClienteTableModel;
import com.projeto.gestaoloja.util.tableModel.CellRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteForm extends JFrame{
    private JPanel main;
    private JPanel panelAdd;
    private JLabel lbNome;
    private JTextField txtNome;
    private JLabel lbCPF;
    private JFormattedTextField txtCPF;
    private JLabel lbEmail;
    private JLabel lbTelefone;
    private JFormattedTextField txtTelefone;
    private JLabel lbEndereco;
    private JTextField txtEndereco;
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
    private JTextField txtEmail;
    private List<Cliente> clienteList = new ClienteController().listarClientes();
    private Long idCliente;
    private MaskCampos maskCampos = new MaskCampos();
    public ClienteForm() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setBounds(650,450,250,250);
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        atualizarTabelaCliente(new ClienteController().listarClientes());
        enableFields(false);
        eventos();

    }

    private void novoCliente(){
        enableFields(true);
    }

    private void enableFields(boolean b){
        txtNome.setEnabled(b);
        txtCPF.setEnabled(b);
        txtEmail.setEnabled(b);
        txtTelefone.setEnabled(b);
        txtEndereco.setEnabled(b);
    }

    private void cancelarCliente(){
        txtNome.setText("");
        txtCPF.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        enableFields(false);
        table.getSelectionModel().clearSelection();
    }

    private void salvarCliente() {

        Cliente cliente = new Cliente();

        if(txtEmail.getText().length() > 0 && txtCPF.getText().length() > 0 && txtEndereco.getText().length() > 0 && txtNome.getText().length() > 0 && txtTelefone.getText().length() >0) {

        cliente.setNome(txtNome.getText());
        cliente.setCpf(txtCPF.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setTelefone(txtTelefone.getText());
        cliente.setEndereco(txtEndereco.getText());


        int result = 0;
        if(idCliente == null){
            result  = new ClienteController().cadastrarCliente(cliente);
        } else {
            cliente.setId(idCliente);
            result = new ClienteController().editarCliente(cliente);
            idCliente = null;
        }

        if(result == 1){
            JOptionPane.showMessageDialog(this, "Cliente inserido com sucesso!");
            enableFields(false);
            cancelarCliente();
            refreshTableCliente();
        } else{
            JOptionPane.showMessageDialog(this, "Cliente já existente, Tente novamente!");
        }

        }else{
            JOptionPane.showMessageDialog(ClienteForm.this,
                    "Todos os campos são obrigatorios",
                    "ATENÇÃO!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCliente() {
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o cliente a ser alterado!");
            return;
        }

        Cliente cliente = new ClienteTableModel(clienteList).get(rowIndex);

        idCliente = cliente.getId();

        txtNome.setText(cliente.getNome());
        txtCPF.setText(cliente.getCpf());
        txtEmail.setText(cliente.getEmail());
        txtTelefone.setText(cliente.getTelefone());
        txtEndereco.setText(cliente.getEndereco());

        enableFields(true);
        table.getSelectionModel().clearSelection();
    }

    private void deletarCliente() {
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o cliente a ser removido!");
            return;
        }

        Cliente cliente = new ClienteTableModel(clienteList).get(rowIndex);

        if(new ClienteController().listarClienteVenda(cliente.getId()) ==0) {

        int confirm = JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Deletar cliente", JOptionPane.YES_NO_OPTION);

        if (confirm != 0) {
            return;
        }

        int result = new ClienteController().deletar(cliente.getId());

        if (result == 1) {
            JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso!");
            atualizarTabelaCliente(new ClienteController().listarClientes());
        } else {
            JOptionPane.showMessageDialog(this, "Tente novamente!");
        }
        }else {
            JOptionPane.showMessageDialog(this, "Impossivel deletar, cliente salvo em vendas!");
        }
        table.getSelectionModel().clearSelection();
    }

    private void pesquisar() {
        String pesquisar = txtBusca.getText();

        if (pesquisar.isEmpty()) {
            atualizarTabelaCliente(new ClienteController().listarClientes());
        } else {
            List<Cliente> clientesTemp =new ClienteController().listarClientes()
                    .stream()
                    .filter((Cliente c) -> {
                        return c.getNome().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                c.getCpf().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                c.getEndereco().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                c.getEmail().toLowerCase().contains(pesquisar.toLowerCase());
                    })
                    .collect(Collectors.toList());

            atualizarTabelaCliente(clientesTemp);
        }
    }

    private void voltar(){
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.hide();
    }

    private void atualizarTabelaCliente(List<Cliente> clientes) {
        ClienteTableModel clienteTableModel = new ClienteTableModel(clientes);
        table.setModel(clienteTableModel);
        table.setDefaultRenderer(Object.class, new CellRenderer());

    }

    private void refreshTableCliente() {
        clienteList = new ClienteController().listarClientes();
        if (clienteList != null) {
            table.setModel(new ClienteTableModel(clienteList));
            table.setDefaultRenderer(Object.class, new CellRenderer());
        }
    }

    private void setMaskCampos(){
        try {
            new JFormattedTextField(maskCampos.maskCpf(txtCPF));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            new JFormattedTextField(maskCampos.maskTel(txtTelefone));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void eventos(){
        setMaskCampos();

        novoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                novoCliente();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarCliente();
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                salvarCliente();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCliente();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarCliente();
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
    }

    private void setIcon(){
        URL url = this.getClass() .getResource ("/imagens/carrinhoApp.png");
        Image imgTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this. setIconImage(imgTitulo);

    }
}
