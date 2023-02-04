package com.projeto.gestaoloja.modelo.entidades;

import com.projeto.gestaoloja.controller.ProdutoController;
import com.projeto.gestaoloja.controller.VendaController;
import com.projeto.gestaoloja.dao.CaixaDao;
import com.projeto.gestaoloja.dao.ProdutoDao;
import com.projeto.gestaoloja.dao.VendaDao;
import com.projeto.gestaoloja.util.Overload;
import com.toedter.calendar.JDateChooser;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Caixa {

    private double valor_diario;
    private double valor_mensal;
    private String tipo;
    private Date data_inicial;
    private Date data_final;

    public Caixa() {
    }

    public Caixa(double valor_diario, double valor_mensal, String tipo, Date data_inicial, Date data_final) {
        this.valor_diario = valor_diario;
        this.valor_mensal = valor_mensal;
        this.tipo = tipo;
        this.data_inicial = data_inicial;
        this.data_final = data_final;
    }

    public double getValor_diario() {
        return valor_diario;
    }

    public void setValor_diario(double valor_diario) {
        this.valor_diario = valor_diario;
    }

    public double getValor_mensal() {
        return valor_mensal;
    }

    public void setValor_mensal(double valor_mensal) {
        this.valor_mensal = valor_mensal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData_inicial() {
        return data_inicial;
    }

    public void setData_inicial(Date data_inicial) {
        this.data_inicial = data_inicial;
    }

    public Date getData_final() {
        return data_final;
    }

    public void setData_final(Date data_final) {
        this.data_final = data_final;
    }

    public void salvar(Caixa caixa) {
        new CaixaDao().salvar(caixa);
    }

//  consulta o valor total da entrada/ vendas por dia
    public void consultarEntrada(JDateChooser data) {
        Double totalVendaDia = null;
        try {
// Recebe o dia, o mês e o ano separadamente do calendario
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados

            int dataMesAux = data.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia = String.valueOf(data.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes = String.valueOf(dataMesAux);
            String dataAno = String.valueOf(data.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia + "/" + dataMes + "/" + dataAno);

            totalVendaDia = new VendaController().RetornaEntradaPorDia(dataDia, dataMes, dataAno);

            setValor_diario(totalVendaDia);

            setValor_mensal(0.00);

            System.out.println(totalVendaDia);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//  consulta o valor total da saída/ custo do produto por dia
    public void consultarSaida(JDateChooser data) {
        Double totalVendaDia = null;
        try{
// Recebe o dia, o mês e o ano separadamente do calendario
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados

            int dataMesAux = data.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia = String.valueOf(data.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes = String.valueOf(dataMesAux);
            String dataAno = String.valueOf(data.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia +"/"+ dataMes +"/"+ dataAno);

            totalVendaDia = new ProdutoController().RetornaSaidaPorDia(dataDia,  dataMes , dataAno);

            setValor_diario(totalVendaDia);

            setValor_mensal(0.00);

            System.out.println(totalVendaDia);
        } catch (Exception e){
            System.out.println(e);
        }
    }

//  consulta o valor total do lucro em reais por dia
    public void consultarLucro(JDateChooser dateChooser1) {

        Double totalVendaDia = null;
        Double totalDespesaDia = null;
        Double totalLucro = null;
        try {
// Recebe o dia, o mês e o ano separadamente do calendario
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados

            int dataMesAux = dateChooser1.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia = String.valueOf(dateChooser1.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes = String.valueOf(dataMesAux);
            String dataAno = String.valueOf(dateChooser1.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia + "/" + dataMes + "/" + dataAno);

            BigDecimal totalVenda = new VendaDao().RetornaEntradaTotal();
            BigDecimal totalDespesa = new ProdutoDao().RetornaDespesaTotal();

// converte os valores bigDecimal para double
            double  totalVendaD =totalVenda.doubleValue();
            double  totalDespesaD =totalDespesa.doubleValue();

            System.out.println(totalLucro);

            totalVendaDia = new VendaController().RetornaEntradaPorDia(dataDia, dataMes, dataAno);
            totalDespesaDia = new  ProdutoController().RetornaSaidaPorDia(dataDia, dataMes, dataAno);

// calculo o valor do lucro, subtraindo a quantidade de vendas pela quantidade de gasto com os produtos
            totalLucro = totalVendaDia-totalDespesaDia;

            setValor_diario(totalLucro);

            setValor_mensal(0.00);

            System.out.println(totalLucro);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

// Aqui há um polimorfismo de sobrecarga, onde se difere do primeiro método a quantidade de parâmetros
//  consulta o valor total da entrada/ vendas entre duas datas
    @Overload
    public void consultarEntrada(JDateChooser data2, JDateChooser data3) {

        try {
// Recebe o dia, o mês e o ano separadamente do calendario para a primeira
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados

            int dataMesAux1 = data2.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia1 = String.valueOf(data2.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes1 = String.valueOf(dataMesAux1);
            String dataAno1 = String.valueOf(data2.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia1 + "/" + dataMes1 + "/" + dataAno1);
            String dataStringAux1 = (dataDia1 + "/" + dataMes1 + "/" + dataAno1);

// Recebe o dia, o mês e o ano separadamente do calendario para a segunda
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados
            int dataMesAux2 = data3.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia2 = String.valueOf(data3.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes2 = String.valueOf(dataMesAux2);
            String dataAno2 = String.valueOf(data3.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia2 + "/" + dataMes2 + "/" + dataAno2);
            String dataStringAux2 = (dataDia2 + "/" + dataMes2 + "/" + dataAno2);

// formada a data do calendario em um novo formato, para poder obter as datas acimas com o mesmo formato
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = df.parse(dataStringAux1);
            Date dt2 = df.parse(dataStringAux2);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt1);
            Double totalVendaDiaMes = 0.00;

// o for percorre a primeira data informada acima até a segunda data, assim listando todas as datas possíveis entre esse intervalo
            for (Date dt = dt1; dt.compareTo(dt2) <= 0; ) {
                cal.add(Calendar.DATE, +1);
                dt = cal.getTime();

// separa o dia, o mês e o ano isoladamente
                String dateParts[] = df.format(dt).split("/");

// Obtendo dia, mês e ano da data separados para fazer a consulta no sql
                String dia = dateParts[0];
                String mes = dateParts[1];
                String ano = dateParts[2];

// quando recebe o dia separadamente o seu valor sempre é um número a mais, portanto, a linha abaixo converte para o dia atual
                int diaAux = Integer.parseInt(dia) - 1;

                String diaString = String.valueOf(diaAux);

                double VendaDiaMes = 0;

                VendaDiaMes = new VendaDao().RetornaEntradaPorDia(diaString, mes, ano);
                System.out.println("Dia: " + (diaString) + ", Mês: " + mes + ", Ano: " + ano + " Venda = R$ " + VendaDiaMes);

// recebe a cada for percorrido a soma total de vendas de cada dia
                totalVendaDiaMes = totalVendaDiaMes + VendaDiaMes;

            }

            setValor_diario(0.00);

            setValor_mensal(totalVendaDiaMes);

            System.out.println(totalVendaDiaMes);

        } catch (Exception e){
            System.out.println(e);
        }
    }

// Aqui há um polimorfismo de sobrecarga, onde se difere do primeiro método a quantidade de parâmetros
//  consulta o valor total da saída/ custo do produto entre duas datas
    @Overload
    public void consultarSaida(JDateChooser data2, JDateChooser data3) {
        try {
// Recebe o dia, o mês e o ano separadamente do calendario para a primeira
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados

            int dataMesAux1 = data2.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia1 = String.valueOf(data2.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes1 = String.valueOf(dataMesAux1);
            String dataAno1 = String.valueOf(data2.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia1 + "/" + dataMes1 + "/" + dataAno1);
            String dataStringAux1 = (dataDia1 + "/" + dataMes1 + "/" + dataAno1);

// Recebe o dia, o mês e o ano separadamente do calendario para a segunda
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados

            int dataMesAux2 = data3.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia2 = String.valueOf(data3.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes2 = String.valueOf(dataMesAux2);
            String dataAno2 = String.valueOf(data3.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia2 + "/" + dataMes2 + "/" + dataAno2);
            String dataStringAux2 = (dataDia2 + "/" + dataMes2 + "/" + dataAno2);

// formada a data do calendario em um novo formato, para poder obter as datas acimas com o mesmo formato
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = df.parse(dataStringAux1);
            Date dt2 = df.parse(dataStringAux2);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt1);
            Double totalVendaDiaMes = 0.00;

// o for percorre a primeira data informada acima até a segunda data, assim listando todas as datas possíveis entre esse intervalo
            for (Date dt = dt1; dt.compareTo(dt2) <= 0; ) {
                cal.add(Calendar.DATE, +1);
                dt = cal.getTime();

// separa o dia, o mês e o ano isoladamente
                String dateParts[] = df.format(dt).split("/");

// Obtendo dia, mês e ano da data separados para fazer a consulta no sql
                String dia = dateParts[0];
                String mes = dateParts[1];
                String ano = dateParts[2];

// quando recebe o dia separadamente o seu valor sempre é um número a mais, portanto, a linha abaixo converte para o dia atual
                int diaAux = Integer.parseInt(dia) - 1;

                String diaString = String.valueOf(diaAux);

                double VendaDiaMes = 0;

                VendaDiaMes = new ProdutoController().RetornaSaidaPorDia(diaString, mes, ano);
                System.out.println("Dia: " + (diaString) + ", Mês: " + mes + ", Ano: " + ano + " Venda = R$ " + VendaDiaMes);

                totalVendaDiaMes = totalVendaDiaMes + VendaDiaMes;

            }

            setValor_diario(0.00);

            setValor_mensal(totalVendaDiaMes);

            System.out.println(totalVendaDiaMes);

        } catch (Exception e){
            System.out.println(e);
        }
    }

// Aqui há um polimorfismo de sobrecarga, onde se difere do primeiro método a quantidade de parâmetros
//  consulta o valor total do lucro em reais entre duas datas
    @Overload
    public void consultarLucro(JDateChooser dateChooser2, JDateChooser dateChooser3) {
        try {
// Recebe o dia, o mês e o ano separadamente do calendario para a primeira
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados

            int dataMesAux1 = dateChooser2.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia1 = String.valueOf(dateChooser2.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes1 = String.valueOf(dataMesAux1);
            String dataAno1 = String.valueOf(dateChooser2.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia1 + "/" + dataMes1 + "/" + dataAno1);
            String dataStringAux1 = (dataDia1 + "/" + dataMes1 + "/" + dataAno1);

// Recebe o dia, o mês e o ano separadamente do calendario para a primeira
// Transfoma eles em string para poder fazer a consulta com o sql no banco de dados

            int dataMesAux2 = dateChooser3.getCalendar().get(Calendar.MONTH) + 1;

            String dataDia2 = String.valueOf(dateChooser3.getCalendar().get(Calendar.DAY_OF_MONTH));
            String dataMes2 = String.valueOf(dataMesAux2);
            String dataAno2 = String.valueOf(dateChooser3.getCalendar().get(Calendar.YEAR));

// printa no terminal a data para monitoramento
            System.out.println(dataDia2 + "/" + dataMes2 + "/" + dataAno2);
            String dataStringAux2 = (dataDia2 + "/" + dataMes2 + "/" + dataAno2);

// formada a data do calendario em um novo formato, para poder obter as datas acimas com o mesmo formato
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = df.parse(dataStringAux1);
            Date dt2 = df.parse(dataStringAux2);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt1);
            Double totalLucroDiaMes = 0.00;

// o for percorre a primeira data informada acima até a segunda data, assim listando todas as datas possíveis entre esse intervalo
            for (Date dt = dt1; dt.compareTo(dt2) <= 0; ) {
                cal.add(Calendar.DATE, +1);
                dt = cal.getTime();

// separa o dia, o mês e o ano isoladamente
                String dateParts[] = df.format(dt).split("/");

// Obtendo dia, mês e ano da data separados para fazer a consulta no sql
                String dia = dateParts[0];
                String mes = dateParts[1];
                String ano = dateParts[2];

// quando recebe o dia separadamente o seu valor sempre é um número a mais, portanto, a linha abaixo converte para o dia atual
                int diaAux = Integer.parseInt(dia) - 1;

                String diaString = String.valueOf(diaAux);

                double lucroDiaMes = 0;
                double despesaDiaMes = 0;
                double totalDiaMes = 0 ;

                lucroDiaMes = new VendaController().RetornaEntradaPorDia(diaString, mes, ano);
                despesaDiaMes = new ProdutoController().RetornaSaidaPorDia(diaString, mes, ano);


                totalDiaMes = lucroDiaMes-despesaDiaMes;


                System.out.println("Dia: " + (diaString) + ", Mês: " + mes + ", Ano: " + ano + " Venda = R$ " + totalDiaMes);

                totalLucroDiaMes = totalLucroDiaMes + totalDiaMes;

            }

            setValor_diario(0.00);

            setValor_mensal(totalLucroDiaMes);

            System.out.println(totalLucroDiaMes);

        } catch (Exception e){
            System.out.println(e);
        }
    }

}
