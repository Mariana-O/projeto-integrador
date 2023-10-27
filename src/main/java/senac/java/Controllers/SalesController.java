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
            String response = "cheguei no exception";

            if ("GET".equals((exchange.getRequestMethod()))) {
             List<Sales> getAllFromArray = Sales.getAllSales(saleslist);

             if(!getAllFromArray.isEmpty()) {
                 for (Sales sales : getAllFromArray) {
                     System.out.println("Usuário: " + sales.getUser());
                     System.out.println("Produto: " + sales.getProducts());
                     System.out.println("Preço: " + sales.getValor());
                     System.out.println("Valor final: " + sales.getFinishedSale());
                     System.out.println("Desconto: " + sales.getDiscount());
                     System.out.println("Venda : " + sales.getSale());
                     System.out.println();
                     System.out.println("*-------------------------------------*");
                     System.out.println();
                     res.enviarResponseJson(exchange, sales.arrayToJson(getAllFromArray), 200);}

             }else {
                 String resposta = "Dados não foram enviados";
                 res.enviarResponse(exchange, resposta, 400);
             }

            } else if ("POST".equals(exchange.getRequestMethod())) {
                try (InputStream requestBody = exchange.getRequestBody()) {
                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                   Sales sales = new Sales(
                           json.getString("user"),
                           json.getString("products"),
                           json.getDouble("value"),
                           json.getBoolean("finishedSale"),
                           json.getDouble("discount"),
                           json.getString("sale")

                    );
                    saleslist.add(0, sales);

                   res.enviarResponseJson(exchange, sales.toJson(), 400);
                   System.out.println("SalesList contem: " + sales.toJson());

                } catch(Exception e){
                    String resposta = e.toString();
                    System.out.println(response);
                    System.out.println("----------------------");
                    res.enviarResponse(exchange, resposta, 201);
                    System.out.println("O erro foi: " + e);}
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                response = "Essa é a rota venda  - PUT";
                res.enviarResponse(exchange, response, 200);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                response = "Essa é a rota venda - DELETE";
                res.enviarResponse(exchange, response, 200);
            }else if ("OPTIONS".equals(exchange.getRequestMethod())){
                    exchange.sendResponseHeaders(204, -1);
                    exchange.close();
                    return;
            } else {
                response = "Essa é a rota de venda - método não disponivel" +
                        "O método utilizado foi: " + exchange.getRequestMethod();
                res.enviarResponse(exchange, response, 200);
            }
        }
    }
}


