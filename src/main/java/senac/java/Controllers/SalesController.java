package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.DAL.SalesDal;
import senac.java.Domain.Sales;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SalesController {
    public static ResponseEndPoint res = new ResponseEndPoint();
    public static List<Sales> saleslist = new ArrayList<>();

    public static class SalesHandler implements HttpHandler {
        static Sales sales = new Sales();
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
            SalesDal salesdal = new SalesDal();
            List<Sales> getAllFromArray = Sales.getAllSales(saleslist);

            if (!getAllFromArray.isEmpty()) {
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
                    res.enviarResponseJson(exchange, sales.arrayToJson(getAllFromArray), 200);
                }

            } else {
                String resposta = "Dados não foram enviados";
                res.enviarResponse(exchange, resposta, 400);
            }
            try {
                salesdal.listSales();

            } catch (Exception e) {
                System.out.println("O erro foi: " + e);
                String resposta = "Dados não encontrados";
                res.enviarResponse(exchange, resposta, 200);
            }

        }
    }



            public static void doPost(HttpExchange exchange) throws IOException {
                if ("POST".equals(exchange.getRequestMethod())) {
                    SalesDal salesdal = new SalesDal();
                    try (InputStream requestBody = exchange.getRequestBody()) {
                        JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));
                        int resp = 0;
                        Sales sales = new Sales(
                                json.getString("user"),
                                json.getString("products"),
                                json.getFloat("value"),
                                json.getBoolean("finishedSale"),
                                json.getFloat("discount"),
                                json.getString("sale")

                        );
                        saleslist.add(0, sales);
                        resp = salesdal.insertSales(sales.user, sales.products, sales.value, sales.finishedSale, sales.discount, sales.sale);

                        res.enviarResponseJson(exchange, sales.toJson(), 400);
                        System.out.println("SalesList contem: " + sales.toJson());

                    } catch (Exception e) {
                        String resposta = e.toString();
                        System.out.println(response);
                        System.out.println("----------------------");
                        res.enviarResponse(exchange, resposta, 201);
                        System.out.println("O erro foi: " + e);
                    }
                }
            }

            public static void doPut(HttpExchange exchange) throws IOException {
                if ("PUT".equals(exchange.getRequestMethod())) {

                    SalesDal salesdal = new SalesDal();

                    try {
                        salesdal.updateSales(sales.id,sales.user, sales.products, sales.value, sales.finishedSale, sales.discount, sales.sale);

                    } catch (Exception e) {
                        System.out.println("O erro foi: " + e);
                    }

                    res.enviarResponse(exchange, response, 200);
                }
            }

            public static void doDelete(HttpExchange exchange) throws IOException {
                if ("DELETE".equals(exchange.getRequestMethod())) {
                    SalesDal salesdal = new SalesDal();

                    try {
                        salesdal.deleteSales(sales.id);
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



