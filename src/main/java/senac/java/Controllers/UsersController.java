package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.DAL.UsersDal;
import senac.java.Domain.Users;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UsersController {
    public static ResponseEndPoint res = new ResponseEndPoint();
    public static List<Users> userlist = new ArrayList<>();

    public static class UsersHandler implements HttpHandler {
        static Users users = new Users();
        static String response = "";

        @Override
        public void handle(HttpExchange exchange) throws IOException {
                try {
                    if("GET".equals(exchange.getRequestMethod()))
                    {
                        doGet(exchange);
                    }else if("POST".equals(exchange.getRequestMethod()))
                    {
                        doPost(exchange);
                    }else if("PUT".equals(exchange.getRequestMethod()))
                    {
                        doPut(exchange);
                    }else if("DELETE".equals(exchange.getRequestMethod()))
                    {
                        doDelete(exchange);
                    }else if("OPTIONS".equals(exchange.getRequestMethod())) {
                        doOptions(exchange);}
                } catch (Exception e) {
                    e.toString();
                    System.out.println("O erro foi " + e);
                }
            }

        public static void doGet(HttpExchange exchange) throws IOException {
            if ("GET".equals((exchange.getRequestMethod()))) {
                UsersDal userdal = new UsersDal();

                List<Users> getAllFromArray = Users.getAllUsers(userlist);// INSTANCIA
                if (!getAllFromArray.isEmpty()) {
                    for (Users users : getAllFromArray) {
                        System.out.println("Name: " + users.getName());
                        System.out.println("Last Name: " + users.getLastName());
                        System.out.println("Cpf: " + users.getCpf());
                        System.out.println("Email: " + users.getEmail());
                        System.out.println();
                        System.out.println("*-------------------------------------*");
                        System.out.println();

                        res.enviarResponseJson(exchange, users.arrayToJson(getAllFromArray), 200);

                    }
                }
                try {
                    userdal.listUser();

                } catch (Exception e) {
                    System.out.println("O erro foi: " + e);
                    String resposta = "Dados não encontrados";
                    res.enviarResponse(exchange, resposta, 200);
                }
            }
        }
        public static void doPost(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                UsersDal userdal = new UsersDal();

                try (InputStream requestBody = exchange.getRequestBody()) {
                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));
                    int resp = 0;
                    Users user = new Users(
                    json.getString("name"),
                    json.getString("last_name"),
                    json.getString("cpf"),
                    json.getString("email"));
                     userlist.add(0, user);
                     resp = userdal.insertUser(user.name, user.lastName, user.cpf, user.email);

                    res.enviarResponseJson(exchange, user.toJson(), 200);
                    if (resp == 0) {
                        response = "houve um problema ao criar o usuario";
                    } else {
                        response = "usuario criado com sucesso";
                        res.enviarResponse(exchange, String.valueOf(resp), 200);
                    }
                } catch (Exception e) {
                    String resposta = e.toString();
                    res.enviarResponse(exchange, resposta, 200);
                    System.out.println("O erro foi: " + e);
                }
            }
        }

        public static void doPut(HttpExchange exchange) throws IOException {
            if ("PUT".equals(exchange.getRequestMethod())) {

                UsersDal userdal = new UsersDal();

                try {
                    userdal.updateUser(users.id, users.name, users.lastName, users.cpf, users.email);

                } catch (Exception e) {
                    System.out.println("O erro foi: " + e);
                }

                res.enviarResponse(exchange, response, 200);
            }
        }

        public static void doDelete(HttpExchange exchange) throws IOException {
            if ("DELETE".equals(exchange.getRequestMethod())) {
                UsersDal userdal = new UsersDal();

                try {
                    userdal.deleteUser(users.id);
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









