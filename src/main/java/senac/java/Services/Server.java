package senac.java.Services;

import com.sun.net.httpserver.HttpServer;

import senac.java.Controllers.SalesController;
import senac.java.Controllers.StockController;
import senac.java.Controllers.UsersController;


import java.io.IOException;
import java.net.InetSocketAddress;

public class Server{

    public static void lojaServer() throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(4000), 0);
        server.createContext("/api/user", new UsersController.UsersHandler());
        server.createContext("/api/products", new StockController.StockHandler());
        server.createContext("/api/sales", new SalesController.SalesHandler());

        server.setExecutor(null);
        System.out.println("Servidor Iniciado");
        server.start();

    }
}
