package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.DAL.ProductsDal;
import senac.java.Domain.Products;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductsController {
    public static ResponseEndPoint res = new ResponseEndPoint();
    public static List<Products> productslist = new ArrayList<>();

    public static class ProductsHandler implements HttpHandler {
        static Products products = new Products();
        static String response = "";

            @Override
            public void handle(HttpExchange exchange) throws IOException {
                try {
                    if ("GET".equals(exchange.getRequestMethod())) {
                        doGet(exchange);
                    } else if ("POST".equals(exchange.getRequestMethod())) {
                        doPost(exchange);
                    } else if ("PUT".equals(exchange.getRequestMethod())) {
                        doPut(exchange);
                    } else if ("DELETE".equals(exchange.getRequestMethod())) {
                        doDelete(exchange);
                    } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                        doOptions(exchange);
                    }
                } catch (Exception e) {
                    e.toString();
                    System.out.println("O erro foi " + e);
                }
            }

            public static void doGet(HttpExchange exchange) throws IOException {
                if ("GET".equals((exchange.getRequestMethod()))) {
                    ProductsDal productsdal = new ProductsDal();
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
                    }

                    try {
                        productsdal.listProducts();

                    } catch (Exception e) {
                        System.out.println("O erro foi: " + e);
                        String resposta = "Dados não encontrados";
                        res.enviarResponse(exchange, resposta, 200);
                    }
                }
            }

            public static void doPost(HttpExchange exchange) throws IOException {
                try (InputStream requestBody = exchange.getRequestBody()) {
                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                    Products products = new Products(
                            json.getString("name"),
                            json.getString("description"),
                            json.getInt("price")
                    );
                    productslist.add(0, products);


                    res.enviarResponseJson(exchange, products.toJson(), 400);
                    System.out.println("SalesList contem: " + products.toJson());

                } catch (Exception e) {
                    String resposta = e.toString();
                    System.out.println(response);
                    System.out.println("----------------------");
                    res.enviarResponse(exchange, resposta, 201);
                    System.out.println("O erro foi: " + e);
                }
            }

            public static void doPut(HttpExchange exchange) throws IOException {
                if ("PUT".equals(exchange.getRequestMethod())) {

                    ProductsDal productsdal = new ProductsDal();

                    try {
                        productsdal.updateProducts(products.id, products.name, products.description, products.price);

                    } catch (Exception e) {
                        System.out.println("O erro foi: " + e);
                    }

                    res.enviarResponse(exchange, response, 200);
                }
            }


        public static void doDelete(HttpExchange exchange) throws IOException {
            if ("DELETE".equals(exchange.getRequestMethod())) {
                ProductsDal productsDal = new ProductsDal();

                try {
                    productsDal.deleteProducts(products.id);
                } catch (Exception e) {
                    System.out.println("O erro foi: " + e);
                }
                res.enviarResponse(exchange, response, 200);

            }
        }


        public static void doOptions(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            } else {
                response = "Essa é a rota de usuario - método não disponivel" +
                        "O método utilizado foi: " + exchange.getRequestMethod();
                res.enviarResponse(exchange, response, 200);
            }
        }
    }
}

