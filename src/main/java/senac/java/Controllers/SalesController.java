package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.Domain.Sales;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class SalesController {
    static ResponseEndPoint res = new ResponseEndPoint();
    private static List<Sales> saleslist = new ArrayList<>();

    public static class SalesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";

            if ("GET".equals((exchange.getRequestMethod()))) {
//                response = "Essa é a rota venda - GET";
                res.enviarResponse(exchange, response, 200);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                try (InputStream requestBody = exchange.getRequestBody()) {
                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                   Sales sales = new Sales(
                           json.getString("user"),
                           json.getString("products"),
                           json.getDouble("value"),
                           json.getBoolean("finishedsale"),
                           json.getDouble("discount"),
                           json.getString("sale")

                    );
                    saleslist.add(sales);

                    System.out.println("SalesList contem: " + sales.toJson());

                } catch(Exception e){
                    String resposta = e.toString();
                    res.enviarResponse(exchange, resposta, 201);
                    System.out.println("O erro foi: " + e);}
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                response = "Essa é a rota venda  - PUT";
                res.enviarResponse(exchange, response, 200);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                response = "Essa é a rota venda - DELETE";
                res.enviarResponse(exchange, response, 200);
            } else {
                response = "Essa é a rota de venda - método não disponivel" +
                        "O método utilizado foi: " + exchange.getRequestMethod();
                res.enviarResponse(exchange, response, 200);
            }
        }
    }
}


