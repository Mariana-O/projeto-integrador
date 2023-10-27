package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.Domain.Products;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductsController {

    static ResponseEndPoint res = new ResponseEndPoint();
    private static List<Products> productslist = new ArrayList<>();

    public static class ProductsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = "";

            if ("GET".equals((exchange.getRequestMethod()))) {
                List<Products> getAllFromArray = Products.getAllProducts(productslist);

                if (!getAllFromArray.isEmpty()) {
                    for (Products products : getAllFromArray) {
                        System.out.println("Nome: " + products.getName());
                        System.out.println("Descricao: " + products.getDescription());
                        System.out.println("Preco: " + products.getPrice());
                        System.out.println();
                        System.out.println("*-------------------------------------*");
                        System.out.println();
                        res.enviarResponseJson(exchange, products.arrayToJson(getAllFromArray), 200);
                    }
                }else{
                    String  resposta = "Dados não encontrados";
                    res.enviarResponse(exchange, resposta, 200);
                }
            } else if ("POST".equals(exchange.getRequestMethod())) {
                try (InputStream requestBody = exchange.getRequestBody()) {
                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                   Products products = new Products(
                            json.getString("name"),
                            json.getString("description"),
                            json.getInt("price")
                    );
                    productslist.add(0, products);

                    System.out.println("StockList contem: " + products.toJson());
                    res.enviarResponseJson(exchange, products.toJson(), 200);

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
