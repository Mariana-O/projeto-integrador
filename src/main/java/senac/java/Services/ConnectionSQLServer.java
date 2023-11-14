package senac.java.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQLServer {
    public void conectar() {
        Connection conexao = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi";

            String usuario = "116128412023.1";
            String senha = "senac@12841";

            conexao = DriverManager.getConnection(url, usuario, senha);

            if (conexao != null) {
                System.out.println("Conexão com o banco realizada com sucesso");
            }

        } catch(ClassNotFoundException | SQLException e) {
            System.out.println("o erro foi:" + e);

        }finally{

            try{

                if(conexao != null && !conexao.isClosed()){
                    conexao.close();
                }

            }catch(SQLException e){
                System.out.println("o erro no finally foi:" + e);
            }

        }

    }
}
