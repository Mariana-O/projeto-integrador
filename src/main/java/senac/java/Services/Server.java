package senac.java.Services;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import senac.java.Controllers.SalesController;
import senac.java.Controllers.ProductsController;
import senac.java.Controllers.UsersController;


import java.io.IOException;
import java.net.InetSocketAddress;

public class Server{

    public void lojaServer() throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(4000), 0);

        HttpHandler userHandler = new UsersController.UsersHandler();
        HttpHandler stockHandler = new ProductsController.ProductsHandler();
        HttpHandler salesHandler = new SalesController.SalesHandler();

        server.createContext("/api/user", exchange -> {
            configureCorsHeaders(exchange);
            userHandler.handle(exchange);
        });

        server.createContext("/api/products", exchange -> {
            configureCorsHeaders(exchange);
            stockHandler.handle(exchange);
        });

        server.createContext("/api/sales",exchange -> {
             configureCorsHeaders(exchange);
              salesHandler.handle(exchange);
        });

        server.setExecutor(null);
        System.out.println("Servidor Iniciado");
        server.start();

    }

    private void configureCorsHeaders(HttpExchange exchange){
        Headers headers = exchange.getResponseHeaders();
        String requestOrigin = exchange.getRequestHeaders().getFirst("Origin");
        if(requestOrigin != null){
            headers.set("Access-Control-Allow-Origin", requestOrigin);
        }
        headers.set("Access-Control-Allow-Methods", "GET, OPTIONS, POST, PUT, DELETE");
        headers.set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        headers.set("Access-Control-Allow-Credentials", "True");
        headers.set("Acess-Control-Max-Age", "3600");
    }
}
