package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.Domain.Stock;
import senac.java.Domain.Users;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StockController {

    static ResponseEndPoint res = new ResponseEndPoint();
    private static List<Stock> stocklist = new ArrayList<>();

    public static class StockHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = "";

            if ("GET".equals((exchange.getRequestMethod()))) {
                List<Stock> getAllFromArray = Stock.getAllStocks(stocklist);

                Stock stocks = new Stock();
                if (!getAllFromArray.isEmpty()) {
                    for (Stock stock : getAllFromArray) {
                        System.out.println("Name: " + stock.getName());
                        System.out.println("Factory: " + stock.getFactory());
                        System.out.println("Quantity: " + stock.getQuantity());
                        System.out.println();
                        System.out.println("*-------------------------------------*");
                        System.out.println();
                    }
                    response = "Essa é a rota de estoque - GET";
                    res.enviarResponse(exchange, response, 200);
                }else{
                    String  resposta = "Dados não encontrados";
                    res.enviarResponse(exchange, resposta, 200);
                }
            } else if ("POST".equals(exchange.getRequestMethod())) {
                try (InputStream requestBody = exchange.getRequestBody()) {
                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                   Stock stocks = new Stock(
                            json.getString("name"),
                            json.getString("factory"),
                            json.getInt("quantity")
                    );
                    stocklist.add(0, stocks);

                    System.out.println("StockList contem: " + stocks.toJson());
                    res.enviarResponseJson(exchange, stocks.toJson(), 200);

                } catch(Exception e){
                    String resposta = e.toString();
                    res.enviarResponse(exchange, resposta, 201);
                    System.out.println("O erro foi: " + e);}

            }else if ("PUT".equals(exchange.getRequestMethod())) {
                response = "Essa é a rota de estoque  - PUT";
                res.enviarResponse(exchange, response, 200);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                response = "Essa é a rota de estoque - DELETE";
                res.enviarResponse(exchange, response, 200);
            } else if ("OPTIONS".equals(exchange.getRequestMethod())){
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            } else {
                response = "Essa é a rota de estoque - método não disponivel" +
                        "O método utilizado foi: " + exchange.getRequestMethod();
                res.enviarResponse(exchange, response, 200);
            }

        }
    }
}
