package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.modelo.entidades.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;

public class LoginForm extends JFrame{
    private JPanel main;
    private JButton btnOK;
    private JButton btnCancelar;
    private JPasswordField pfSenha;
    private JTextField tfUsuario;


    public LoginForm() {
        setIcon();
        setContentPane(main);
        setTitle("Control Store");
        setBounds(650,450,250,250);
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        eventos();

    }


    public Usuario user;

    private Usuario getAuthenticatedUser(String usuario, String senha){
        Usuario user = null;

        final String DB_URL = "jdbc:mysql://localhost/loja";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new Usuario();
                user.setUsuario(resultSet.getString("Usuario"));
            }
            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;

    }
    private void eventos() {
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = tfUsuario.getText();
                String senha = String.valueOf(pfSenha.getPassword());

                user =  getAuthenticatedUser(usuario, senha);

                if(user != null){
                    Dashboard dashboard = new Dashboard();
                    dashboard.setVisible(true);
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Usuario ou Senha invalidas!",
                            "Tente novamente",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    private void setIcon(){
        URL url = this.getClass() .getResource ("/imagens/carrinhoApp.png");
        Image imgTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this. setIconImage(imgTitulo);

    }
}
