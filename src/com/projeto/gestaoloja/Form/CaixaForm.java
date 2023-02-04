package com.projeto.gestaoloja.Form;

import com.projeto.gestaoloja.controller.CaixaController;
import com.projeto.gestaoloja.controller.ProdutoController;
import com.projeto.gestaoloja.controller.VendaController;
import com.projeto.gestaoloja.modelo.entidades.Caixa;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.net.URL;
import java.text.*;
import java.util.Calendar;
import java.util.Date;

public class CaixaForm extends JFrame{
    private JPanel main;
    private JPanel panelAdd;
    private JLabel lbNome;
    private JLabel lbCPF;
    private JPanel JCalendarDiario;
    private JTextField txtData;
    private JButton ConsultarDiaButton;
    private JPanel JCalendarMensalDe;
    private JPanel JCalendarMensalAte;
    private JPanel Caixa;
    private JPanel panelPesquisa;
    private JButton voltarButton;
    private JButton lucrosButton;
    private JButton despesasButton;
    private JLabel lbValorEntrada;
    private JLabel lbValorSaida;
    private JLabel lbValorCaixaDiario;
    private JButton consultarMesButtton;
    private JLabel lbValorCaixaMensal;
    private JComboBox comboxTipo;
    private JLabel lbLucroTotal;
    private JLabel lbValorLucroTotal;
    private JLabel lbMargemLucroTotal;
    private JLabel lbMargemValorLucroTotal;
    private Calendar calendar = Calendar.getInstance();
    private JDateChooser dateChooser1 = new JDateChooser(calendar.getTime());
    private JDateChooser dateChooser2 = new JDateChooser(calendar.getTime());
    private JDateChooser dateChooser3 = new JDateChooser(calendar.getTime());
    private com.projeto.gestaoloja.modelo.entidades.Caixa caixa = new Caixa();

    public CaixaForm( ) {
        setIcon();
        setContentPane(main); //define o painel principal para exibição
        setTitle("Control Store"); // define o título da janela
        setSize(1000,700); //define o tamanho
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //fecha a janela

        //formata todas as datas do formulario, para poder serem enviadas no sql
        dateChooser1.setDateFormatString("dd/MM/yyyy");
        dateChooser2.setDateFormatString("dd/MM/yyyy");
        dateChooser3.setDateFormatString("dd/MM/yyyy");

        //adiciona as datas formatadas no calendario
        JCalendarDiario.add(dateChooser1);
        JCalendarMensalDe.add(dateChooser2);
        JCalendarMensalAte.add(dateChooser3);

        //muda a cor do calendario
        dateChooser1.setBackground(Color.WHITE);
        dateChooser2.setBackground(Color.WHITE);
        dateChooser3.setBackground(Color.WHITE);
        //setResizable(false);
        setLocationRelativeTo(null); //define a posição
        setVisible(true); // torna visível
        inicializarComboBoxTipo(); // inicializa o combo box
        eventos();
    }


    private void consultarDia(){
        if (comboxTipo.getSelectedItem().toString().equals("Entrada")){

            caixa.consultarEntrada(dateChooser1);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dataRecebida = df.format(dateChooser1.getDate());

            Date dataFormatada = null;
            try {
                dataFormatada = df.parse(dataRecebida);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            caixa.setValor_diario(caixa.getValor_diario());
            caixa.setTipo(comboxTipo.getSelectedItem().toString());
            caixa.setData_inicial(dataFormatada);
            caixa.setData_final(dataFormatada);
            caixa.setValor_mensal(0.00);

            if (caixa.getValor_diario() != 0) {
                lbValorCaixaDiario.setText(" R$ " + caixa.getValor_diario());
            } else {
                lbValorCaixaDiario.setText("0.00");
            }
            new CaixaController().salvar(caixa);

        }else if(comboxTipo.getSelectedItem().toString().equals("Saída")){

            caixa.consultarSaida(dateChooser1);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dataRecebida = df.format(dateChooser1.getDate());

            Date dataFormatada = null;
            try {
                dataFormatada = df.parse(dataRecebida);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            caixa.setValor_diario(caixa.getValor_diario());
            caixa.setTipo(comboxTipo.getSelectedItem().toString());
            caixa.setData_inicial(dataFormatada);
            caixa.setData_final(dataFormatada);
            caixa.setValor_mensal(0.00);

            if(caixa.getValor_diario() != 0){
                lbValorCaixaDiario.setText(" R$ " + caixa.getValor_diario());
            }else {
                lbValorCaixaDiario.setText("0.00");
            }
            new CaixaController().salvar(caixa);

        } else if(comboxTipo.getSelectedItem().toString().equals("Lucro")){
            caixa.consultarLucro(dateChooser1);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dataRecebida = df.format(dateChooser1.getDate());

            Date dataFormatada = null;
            try {
                dataFormatada = df.parse(dataRecebida);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            caixa.setValor_diario(caixa.getValor_diario());
            caixa.setTipo(comboxTipo.getSelectedItem().toString());
            caixa.setData_inicial(dataFormatada);
            caixa.setData_final(dataFormatada);
            caixa.setValor_mensal(0.00);

            if (caixa.getValor_diario() != 0) {
                lbValorCaixaDiario.setText(" R$ " + caixa.getValor_diario());
            } else {
                lbValorCaixaDiario.setText("0.00");
            }

            new CaixaController().salvar(caixa);

        }else{
            JOptionPane.showMessageDialog(null, "Informe um tipo de consulta");
        }
    }

    private void consultarMes() {
        if (comboxTipo.getSelectedItem().toString().equals("Entrada")) {

            caixa.consultarEntrada(dateChooser2, dateChooser3);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dataRecebida1 = df.format(dateChooser1.getDate());
            String dataRecebida2 = df.format(dateChooser2.getDate());

            Date dataFormatada1 = null;
            Date dataFormatada2 = null;
            try {
                dataFormatada1 = df.parse(dataRecebida1);
                dataFormatada2 = df.parse(dataRecebida2);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            caixa.setValor_diario(caixa.getValor_diario());
            caixa.setTipo(comboxTipo.getSelectedItem().toString());
            caixa.setData_inicial(dataFormatada1);
            caixa.setData_final(dataFormatada2);
            caixa.setValor_mensal(caixa.getValor_mensal());

            if(caixa.getValor_mensal() != 0){
                lbValorCaixaMensal.setText(" R$ " + caixa.getValor_mensal());
            }else {
                lbValorCaixaMensal.setText("0.00");
            }

            new CaixaController().salvar(caixa);

        } else if(comboxTipo.getSelectedItem().toString().equals("Saída")){

            caixa.consultarSaida(dateChooser2, dateChooser3);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dataRecebida1 = df.format(dateChooser2.getDate());
            String dataRecebida2 = df.format(dateChooser3.getDate());

            Date dataFormatada1 = null;
            Date dataFormatada2 = null;
            try {
                dataFormatada1 = df.parse(dataRecebida1);
                dataFormatada2 = df.parse(dataRecebida2);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            caixa.setValor_diario(caixa.getValor_diario());
            caixa.setTipo(comboxTipo.getSelectedItem().toString());
            caixa.setData_inicial(dataFormatada1);
            caixa.setData_final(dataFormatada2);
            caixa.setValor_mensal(caixa.getValor_mensal());

            int result = 0;

            if(caixa.getValor_mensal() != 0){
                lbValorCaixaMensal.setText(" R$ " + caixa.getValor_mensal());
            }else {
                lbValorCaixaMensal.setText("0.00");
            }

            new CaixaController().salvar(caixa);

        }else if(comboxTipo.getSelectedItem().toString().equals("Lucro")){
            caixa.consultarLucro(dateChooser2, dateChooser3);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dataRecebida1 = df.format(dateChooser2.getDate());
            String dataRecebida2 = df.format(dateChooser3.getDate());

            Date dataFormatada1 = null;
            Date dataFormatada2 = null;
            try {
                dataFormatada1 = df.parse(dataRecebida1);
                dataFormatada2 = df.parse(dataRecebida2);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            caixa.setValor_diario(caixa.getValor_diario());
            caixa.setTipo(comboxTipo.getSelectedItem().toString());
            caixa.setData_inicial(dataFormatada1);
            caixa.setData_final(dataFormatada2);
            caixa.setValor_mensal(caixa.getValor_mensal());

            int result = 0;

            if(caixa.getValor_mensal() != 0){
                lbValorCaixaMensal.setText(" R$ " + caixa.getValor_mensal());
            }else {
                lbValorCaixaMensal.setText("0.00");
            }

            new CaixaController().salvar(caixa);
        }else{
            JOptionPane.showMessageDialog(null, "Informe um tipo de consulta");
        }
    }

        private void eventos () {

            retornaEntrada();
            retornaSaida();
            retornaLucro();

            ConsultarDiaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    consultarDia();
                }

            });
            consultarMesButtton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    consultarMes();
                }
            });
            voltarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    voltar();
                    System.out.println(e.getActionCommand());
                }
            });
        }

    public void retornaEntrada () {
        NumberFormat formatter = new DecimalFormat("#0.00");
        try {
            BigDecimal totalEntrada = new VendaController().RetornaEntradaTotal();

            if (totalEntrada != null) {
                lbValorEntrada.setText(" R$ " + formatter.format(totalEntrada));
            } else {
                lbValorEntrada.setText(" R$ 0.00");
            }
        }catch (Exception e){
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
    }

    public void retornaSaida () {
        NumberFormat formatter = new DecimalFormat("#0.00");
        try {
            BigDecimal totalDespesa = new ProdutoController().RetornaDespesaTotal();

            if (totalDespesa != null) {
                lbValorSaida.setText(" R$ " + formatter.format(totalDespesa));
            } else {
                lbValorSaida.setText(" R$ 0.00");
            }
        } catch (Exception e){
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

    }

    public void retornaLucro () {
        try {
            NumberFormat formatter = new DecimalFormat("#0.00");

            BigDecimal totalVenda = new VendaController().RetornaEntradaTotal();
            BigDecimal totalDespesa = new ProdutoController().RetornaDespesaTotal();

            double  totalVendaD =totalVenda.doubleValue();
            double  totalDespesaD =totalDespesa.doubleValue();

            double totalLucro;
            double totalMargem;

            totalLucro = totalVendaD-totalDespesaD;

            totalMargem = (totalLucro/totalVendaD) * 100;

            System.out.println(totalLucro);
            System.out.println(totalMargem);

            lbValorLucroTotal.setText(" R$ " + formatter.format(totalLucro) );
            lbMargemValorLucroTotal.setText(formatter.format(totalMargem) + " %");
        }catch (Exception e){
            System.out.println(String.format("Error: %s", e.getMessage()));
        }

    }

        private void voltar () {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
            this.hide();

        }

        private void inicializarComboBoxTipo () {
            comboxTipo.removeAllItems();
            comboxTipo.addItem("Selecione um tipo");
            comboxTipo.addItem("Entrada");
            comboxTipo.addItem("Saída");
            comboxTipo.addItem("Lucro");
        }

    private void setIcon(){
        URL url = this.getClass() .getResource ("/imagens/carrinhoApp.png");
        Image imgTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this. setIconImage(imgTitulo);

    }
    }

