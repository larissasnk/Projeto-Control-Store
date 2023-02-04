import com.projeto.gestaoloja.Connection.DBConnection;
import com.projeto.gestaoloja.Form.LoginForm;

public class Main {
    public static void main(String[] args) {
        DBConnection.createTableUsuario();
        DBConnection.createTableCliente();
        DBConnection.createTableFornecedor();
        DBConnection.createTableCategoria();
        DBConnection.createTableProduto();
        DBConnection.createTableVenda();
        DBConnection.createTableVendaItem();
        DBConnection.createTableCaixa();

        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        new LoginForm();
    }
}
