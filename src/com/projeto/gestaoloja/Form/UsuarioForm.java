package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.controller.UsuarioController;
import com.projeto.gestaoloja.modelo.entidades.Usuario;
import com.projeto.gestaoloja.util.MaskCampos;
import com.projeto.gestaoloja.util.tableModel.CellRenderer;
import com.projeto.gestaoloja.util.tableModel.UsuarioTableModel.UsuarioTableModel;
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

public class UsuarioForm extends JFrame {
    private JTextField txtNome;
    private JTextField txtUsuario;
    private JPanel panelAdd;
    private JLabel lbNome;
    private JLabel lbUsuario;
    private JLabel lbSenha;
    private JPanel main;
    private JTextField txtCargo;
    private JButton novoButton;
    private JButton cancelarButton;
    private JButton salvarButton;
    private JButton editarButton;
    private JPanel panelBotoes;
    private JPanel panelTable;
    private JTable table;
    private JButton removerButton;
    private JLabel lbCargo;
    private JPasswordField txtSenha;
    private JLabel lbTelefone;
    private JFormattedTextField txtTelefone;
    private JLabel lbEndereco;
    private JTextField txtEndereco;
    private JScrollPane scrollPane;
    private JPanel panelPesquisa;
    private JTextField txtBusca;
    private JLabel lbBusca;
    private JButton voltarButton;
    private List<Usuario> usuarioList = new UsuarioController().listarUsuario();
    private Long idUsuario;
    private MaskCampos maskCampos = new MaskCampos();

    public UsuarioForm() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setBounds(650, 450, 250, 250);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        atualizarTabelaUsuario(new UsuarioController().listarUsuario());
        enableFields(false);
        eventos();
    }

    private void novoUsuario() {
        enableFields(true);
    }

    private void enableFields(boolean b) {
        txtNome.setEnabled(b);
        txtUsuario.setEnabled(b);
        txtSenha.setEnabled(b);
        txtTelefone.setEnabled(b);
        txtEndereco.setEnabled(b);
        txtCargo.setEnabled(b);
    }

    private void cancelarUsuario() {
        txtNome.setText("");
        txtUsuario.setText("");
        txtSenha.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtCargo.setText("");
        enableFields(false);
        table.getSelectionModel().clearSelection();
    }

    private void salvarUsuario() {
        Usuario usuario = new Usuario();

        if (txtNome.getText().length() > 0 && txtUsuario.getText().length() > 0 && txtSenha.getText().length() > 0 && txtTelefone.getText().length() > 0 && txtEndereco.getText().length() > 0 && txtCargo.getText().length() > 0) {
            usuario.setNome(txtNome.getText());
            usuario.setUsuario(txtUsuario.getText());
            usuario.setSenha(txtSenha.getText());
            usuario.setTelefone(txtTelefone.getText());
            usuario.setEndereco(txtEndereco.getText());
            usuario.setCargo(txtCargo.getText());

        int result = 0;
        if (idUsuario == null) {
            result = new UsuarioController().cadastrarUsuario(usuario);
        } else {
            usuario.setId(idUsuario);
            result = new UsuarioController().editarUsuario(usuario);
            idUsuario = null;
        }

        if (result == 1) {
            JOptionPane.showMessageDialog(this, "Usuario inserido com sucesso!");
            enableFields(false);
            cancelarUsuario();
            refreshTableUsuario();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario já existente, Tente novamente!");
        }
        }else{
            JOptionPane.showMessageDialog(this,
                    "Todos os campos são obrigatorios",
                    "ATENÇÃO!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarUsuario() {
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o usuario a ser alterado!");
            return;
        }

        Usuario usuario = new UsuarioTableModel(usuarioList).get(rowIndex);

        idUsuario = usuario.getId();

        txtNome.setText(usuario.getNome());
        txtUsuario.setText(usuario.getUsuario());
        txtSenha.setText(usuario.getSenha());
        txtTelefone.setText(usuario.getTelefone());
        txtEndereco.setText(usuario.getEndereco());
        txtCargo.setText(usuario.getCargo());

        enableFields(true);
        table.getSelectionModel().clearSelection();
    }

    private void deletarUsuario() {
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o usuario a ser removido!");
            return;
        }

        Usuario usuario = new UsuarioTableModel(usuarioList).get(rowIndex);

        if(new UsuarioController().listarUsuarioVenda(usuario.getId()) ==0) {

        int confirm = JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Deletar usuario", JOptionPane.YES_NO_OPTION);

        if (confirm != 0) {
            return;
        }

        int result = new UsuarioController().deletar(usuario.getId());

        if (result == 1) {
            JOptionPane.showMessageDialog(this, "Usuario deletado com sucesso!");
            atualizarTabelaUsuario(new UsuarioController().listarUsuario());
        } else {
            JOptionPane.showMessageDialog(this, "Tente novamente!");
        }

        }else {
            JOptionPane.showMessageDialog(this, "Impossivel deletar, usuario salvo em vendas!");
        }
        table.getSelectionModel().clearSelection();
    }

    private void pesquisar() {
        String pesquisar = txtBusca.getText();

        if (pesquisar.isEmpty()) {
            atualizarTabelaUsuario(new UsuarioController().listarUsuario());
        } else {
            List<Usuario> usuariosTemp = new UsuarioController().listarUsuario()
                    .stream()
                    .filter((Usuario u) -> {
                        return u.getNome().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                u.getUsuario().toLowerCase().contains(pesquisar.toLowerCase()) ||
                                u.getCargo().toLowerCase().contains(pesquisar.toLowerCase());
                    })
                    .collect(Collectors.toList());

            atualizarTabelaUsuario(usuariosTemp);
        }
    }

    private void voltar() {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.hide();
    }

    private void atualizarTabelaUsuario(List<Usuario> usuarios) {
        UsuarioTableModel usuarioTableModel = new UsuarioTableModel(usuarios);
        table.setModel(usuarioTableModel);
        table.setDefaultRenderer(Object.class, new CellRenderer());

    }
    private void refreshTableUsuario() {
        usuarioList = new UsuarioController().listarUsuario();
        if (usuarioList != null) {
            table.setModel(new UsuarioTableModel(usuarioList));
            table.setDefaultRenderer(Object.class, new CellRenderer());
        }
    }

    private void setMaskCampos(){
        try {
            new JFormattedTextField(maskCampos.maskTel(txtTelefone));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void eventos() {
        setMaskCampos();

        novoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                novoUsuario();
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                salvarUsuario();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarUsuario();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario();

            }
        });
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarUsuario();
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



