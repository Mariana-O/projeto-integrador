package senac.java;

import senac.java.Services.ConnectionSQLServer;
import senac.java.Services.Server;

import java.io.IOException;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws IOException {
//        Server server = new Server();
//        server.lojaServer();

        ConnectionSQLServer conexao = new ConnectionSQLServer();

        conexao.conectar();


    }
}
