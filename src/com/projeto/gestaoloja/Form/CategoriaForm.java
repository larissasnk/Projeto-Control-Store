package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.controller.CategoriaController;
import com.projeto.gestaoloja.modelo.entidades.Categoria;
import com.projeto.gestaoloja.util.tableModel.CategoriaTableModel.CategoriaTableModel;
import com.projeto.gestaoloja.util.tableModel.CellRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class CategoriaForm extends JFrame{
    private JPanel panelAdd;
    private JLabel lbNome;
    private JTextField txtNome;
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
    private JButton voltarButton;
    private JPanel panelPesquisa;
    private JLabel lbBusca;
    private JTextField txtBusca;
    private JPanel main;
    private List<Categoria> categoriaList = new CategoriaController().listarCategoria();
    private int idCategoria;

    public CategoriaForm() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setBounds(650,450,250,250);
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        refreshTableCategoria();
        enableFields(false);
        eventos();
    }

    private void novaCategoria(){
        enableFields(true);
    }

    private void enableFields(boolean b){
        txtNome.setEnabled(b);
        txtDescricao.setEnabled(b);
    }

    private void cancelarCategoria(){
        txtNome.setText("");
        txtDescricao.setText("");
        enableFields(false);
    }

    private void salvarCategoria() {
        Categoria categoria = new Categoria();

        categoria.setNome_categoria(txtNome.getText());
        categoria.setDescricao_categoria(txtDescricao.getText());

        int result = 0;
        if(idCategoria == 0){
            result  = new CategoriaController().cadastrarCategoria(categoria);
        } else {
            categoria.setId_categoria(idCategoria);
            result = new CategoriaController().editarCategoria(categoria);
            idCategoria = 0;
        }

        if(result == 1){
            JOptionPane.showMessageDialog(this, "Categoria inserida com sucesso!");
            enableFields(false);
            cancelarCategoria();
            refreshTableCategoria();
        } else{
            JOptionPane.showMessageDialog(this, "Tente novamente!");
        }
    }

    private void editarCategoria() {
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione a categoria a ser alterada!");
            return;
        }

        Categoria categoria = new CategoriaTableModel(categoriaList).get(rowIndex);

        idCategoria = categoria.getId_categoria();

        txtNome.setText(categoria.getNome_categoria());
        txtDescricao.setText(categoria.getDescricao_categoria());
        enableFields(true);
        table.getSelectionModel().clearSelection();
    }

    private void deletarCategoria() {
        int rowIndex = table.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione a categoria a ser removida!");
            return;
        }

        Categoria categoria = new CategoriaTableModel(categoriaList).get(rowIndex);

        int confirm = JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Deletar categoria", JOptionPane.YES_NO_OPTION);

        if (confirm != 0) {
            return;
        }

        int result = new CategoriaController().deletarCategoria(categoria.getId_categoria());

        if (result == 1) {
            JOptionPane.showMessageDialog(this, "Categoria deletada com sucesso!");
            atualizarTabelaCategoria(new CategoriaController().listarCategoria());
        } else {
            JOptionPane.showMessageDialog(this, "Tente novamente!");
        }
        table.getSelectionModel().clearSelection();
    }

    private void pesquisar() {
        String pesquisar = txtBusca.getText();

        if (pesquisar.isEmpty()) {
            atualizarTabelaCategoria(new CategoriaController().listarCategoria());
        } else {
            List<Categoria> categoriaTemp = new CategoriaController().listarCategoria()
                    .stream()
                    .filter((Categoria c) -> {
                        return c.getNome_categoria().toLowerCase().contains(pesquisar.toLowerCase());
                    })
                    .collect(Collectors.toList());

            atualizarTabelaCategoria(categoriaTemp);
        }
    }

    private void voltar(){
        ProdutoForm produtoForm = new ProdutoForm();
        produtoForm.setVisible(true);
        this.hide();
    }

    private void atualizarTabelaCategoria(List<Categoria> categorias) {
        CategoriaTableModel categoriaTableModel = new CategoriaTableModel(categorias);
        table.setModel(categoriaTableModel);
        table.setDefaultRenderer(Object.class, new CellRenderer());

    }

    private void refreshTableCategoria() {
        categoriaList = new CategoriaController().listarCategoria();
        if (categoriaList != null) {
            table.setModel(new CategoriaTableModel(categoriaList));
            table.setDefaultRenderer(Object.class, new CellRenderer());
        }
    }

    private void eventos(){
        novoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                novaCategoria();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarCategoria();
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                salvarCategoria();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCategoria();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarCategoria();
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
