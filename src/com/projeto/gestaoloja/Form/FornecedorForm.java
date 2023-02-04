package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.controller.FornecedorController;
import com.projeto.gestaoloja.modelo.entidades.Fornecedor;
import com.projeto.gestaoloja.util.MaskCampos;
import com.projeto.gestaoloja.util.tableModel.FornecedorTableModel.FornecedorTableModel;
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

public class FornecedorForm extends JFrame{
    private JPanel panelAdd;
    private JLabel lbNome;
    private JTextField txtNome;
    private JLabel lbEmpresa;
    private JTextField txtEmpresa;
    private JLabel lbCNPJ;
    private JFormattedTextField txtCNPJ;
    private JLabel lbTelefone;
    private JFormattedTextField txtTelefone;
    private JLabel lbEndereco;
    private JTextField txtEndereco;
    private JLabel lbEmail;
    private JTextField txtEmail;
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
    private JTextField txtCategoria;
    private JTextField txtDescricao;
    private JLabel lbCategoria;
    private JLabel lbDescricao;
    private List<Fornecedor> fornecedorList = new FornecedorController().listarFornecedor();
    private Long idFornecedor;
    private MaskCampos maskCampos = new MaskCampos();

    public FornecedorForm() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setBounds(650, 450, 250, 250);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        atualizarTabelaFornecedor(new FornecedorController().listarFornecedor());
        enableFields(false);
        eventos();
    }

    private void novoFornecedor(){
        enableFields(true);
    }

    private void enableFields(boolean b){
        txtNome.setEnabled(b);
        txtEmpresa.setEnabled(b);
        txtCNPJ.setEnabled(b);
        txtTelefone.setEnabled(b);
        txtEndereco.setEnabled(b);
        txtEmail.setEnabled(b);
        txtCategoria.setEnabled(b);
        txtDescricao.setEnabled(b);

    }

    private void cancelarFornecedor(){
        txtNome.setText("");
        txtEmpresa.setText("");
        txtCNPJ.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtEmail.setText("");
        txtCategoria.setText("");
        txtDescricao.setText("");
        enableFields(false);
        table.getSelectionModel().clearSelection();
    }

    private void salvarFornecedor() {
        Fornecedor fornecedor = new Fornecedor();

        if(txtNome.getText().length() > 0 && txtEmpresa.getText().length() > 0 && txtCNPJ.getText().length() > 0 && txtTelefone.getText().length() > 0 && txtEndereco.getText().length() > 0
                && txtEmail.getText().length() > 0 && txtCategoria.getText().length() > 0 && txtDescricao.getText().length() > 0) {

        fornecedor.setNome(txtNome.getText());
        fornecedor.setEmpresa(txtEmpresa.getText());
        fornecedor.setCnpj(txtCNPJ.getText());
        fornecedor.setTelefone(txtTelefone.getText());
        fornecedor.setEndereco(txtEndereco.getText());
        fornecedor.setEmail(txtEmail.getText());
        fornecedor.setCategoria(txtCategoria.getText());
        fornecedor.setDescricao(txtDescricao.getText());

        int result = 0;
        if(idFornecedor == null){
            result  = new FornecedorController().cadastrarFornecedor(fornecedor);
        } else {
            fornecedor.setId(idFornecedor);
            result = new FornecedorController().editarFornecedor(fornecedor);
            idFornecedor = null;
        }

        if(result == 1){
            JOptionPane.showMessageDialog(this, "Fornecedor inserido com sucesso!");
            enableFields(false);
            cancelarFornecedor();
            refreshTableFornecedor();
        } else{
            JOptionPane.showMessageDialog(this, "Fornecedor já existente, Tente novamente!");
        }
        }else{
            JOptionPane.showMessageDialog(FornecedorForm.this,
                    "Todos os campos são obrigatorios",
                    "ATENÇÃO!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarFornecedor() {
       // table.setEnabled(true);
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o fornecedor a ser alterado!");
            return;
        }

        Fornecedor fornecedor = new FornecedorTableModel(fornecedorList).get(rowIndex);

        idFornecedor = fornecedor.getId();

        txtNome.setText(fornecedor.getNome());
        txtEmpresa.setText(fornecedor.getEmpresa());
        txtCNPJ.setText(fornecedor.getCnpj());
        txtTelefone.setText(fornecedor.getTelefone());
        txtEndereco.setText(fornecedor.getEndereco());
        txtEmail.setText(fornecedor.getEmail());
        txtCategoria.setText(fornecedor.getCategoria());
        txtDescricao.setText(fornecedor.getDescricao());

        enableFields(true);
        table.getSelectionModel().clearSelection();
     //   table.setEnabled(false);
    }

    private void deletarFornecedor() {
     //   table.setEnabled(true);
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o fornecedor a ser removido!");
            return;
        }

        Fornecedor fornecedor = new FornecedorTableModel(fornecedorList).get(rowIndex);

        if(new FornecedorController().listarFornecedorProduto(fornecedor.getId()) == 0) {
        int confirm = JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Deletar fornecedor", JOptionPane.YES_NO_OPTION);

        if (confirm != 0) {
            return;
        }

        int result = new FornecedorController().deletar(fornecedor.getId());

        if (result == 1) {
            JOptionPane.showMessageDialog(this, "Fornecedor deletado com sucesso!");
            atualizarTabelaFornecedor(new FornecedorController().listarFornecedor());
        } else {
            JOptionPane.showMessageDialog(this, "Tente novamente!");
        }
        }else {
            JOptionPane.showMessageDialog(this, "Impossivel deletar, fornecedor salvo em produtos!");
        }
        table.getSelectionModel().clearSelection();
    }

    private void pesquisar() {
        String pesquisar = txtBusca.getText();

        if (pesquisar.isEmpty()) {
            atualizarTabelaFornecedor(new FornecedorController().listarFornecedor());
        } else {
            List<Fornecedor> fornecedorTemp = new FornecedorController().listarFornecedor()
                    .stream()
                    .filter((Fornecedor f) -> {
                        return f.getNome().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                f.getEmpresa().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                f.getEndereco().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                f.getEmail().toLowerCase().contains(pesquisar.toLowerCase());
                    })
                    .collect(Collectors.toList());

            atualizarTabelaFornecedor(fornecedorTemp);
        }
    }

    private void voltar(){
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.hide();
    }

    private void atualizarTabelaFornecedor(List<Fornecedor> fornecedores) {
        FornecedorTableModel fornecedorTableModel = new FornecedorTableModel(fornecedores);
        table.setModel(fornecedorTableModel);
        table.setDefaultRenderer(Object.class, new CellRenderer());

    }

    private void refreshTableFornecedor() {
        fornecedorList = new FornecedorController().listarFornecedor();
        if (fornecedorList != null) {
            table.setModel(new FornecedorTableModel(fornecedorList));
            table.setDefaultRenderer(Object.class, new CellRenderer());
        }
    }

    private void setMaskCampos(){
        try {
            new JFormattedTextField(maskCampos.maskCnpj(txtCNPJ));
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
                novoFornecedor();
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarFornecedor();
            }
        });
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFornecedor();
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarFornecedor();
            }
        });
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarFornecedor();
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
