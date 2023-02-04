package com.projeto.gestaoloja.dao;

import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.modelo.entidades.Caixa;
import java.sql.*;

public class CaixaDao {

    private static final String SQL_INSERT=
            "INSERT INTO caixa (valor_diario, tipo, data_inicial, data_final, valor_mensal) VALUES (?,?,?,?,?)";

//  salvar os registro da tabela caixa no banco de dados
    public int salvar(Caixa caixa) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        try {
            pstm = conn.prepareStatement(SQL_INSERT);
            pstm.setDouble(1, caixa.getValor_diario());
            pstm.setString(2, caixa.getTipo());
            pstm.setDate(3,  new Date(caixa.getData_inicial().getTime()));
            pstm.setDate(4,  new Date(caixa.getData_final().getTime()));
            pstm.setDouble(5, caixa.getValor_diario());
            result = pstm.executeUpdate();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBConnection.close(conn, pstm, null);
            }
            e.printStackTrace();
        }
        return result;
    }

}
