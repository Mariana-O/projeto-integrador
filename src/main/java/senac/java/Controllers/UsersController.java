package senac.java.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import senac.java.Domain.Users;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UsersController {

    static ResponseEndPoint res = new ResponseEndPoint();
    public static List<Users> userlist = new ArrayList<>();

    public static class UsersHandler implements HttpHandler {
        Users users = new Users();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";

            if ("GET".equals((exchange.getRequestMethod()))) {
                List<Users> getAllFromArray = Users.getAllUsers(userlist); // INSTANCIA

                Users user = new Users();

                if (!getAllFromArray.isEmpty()) {
                    for (Users users : getAllFromArray) {
                        System.out.println("Name: " + users.getName());
                        System.out.println("Last Name: " + users.getLastName());
                        System.out.println("Cpf: " + users.getCpf());
                        System.out.println("Email: " + users.getEmail());
                        System.out.println();
                        System.out.println("*-------------------------------------*");
                        System.out.println();}

                   String  resposta = "Dados encontrados com sucesso";
                    res.enviarResponseJson(exchange, user.arrayToJson(getAllFromArray),  200);

                } else {
                    String  resposta = "Dados não encontrados";
                    res.enviarResponse(exchange, resposta, 200);
                }

            } else if ("POST".equals(exchange.getRequestMethod())) {
                try (InputStream requestBody = exchange.getRequestBody()) {
                    JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

                    Users user = new Users(
                            json.getString("name"),
                            json.getString("last_name"),
                            json.getString("cpf"),
                            json.getString("email")
                    );
                    userlist.add(0, user);

                    System.out.println("UserList contem: " + user.toJson());

                    String resposta = "POST:" + user.toJson();
                    res.enviarResponseJson(exchange, user.toJson(), 200);

                } catch (Exception e) {
                    String resposta = e.toString();
                    res.enviarResponse(exchange, resposta, 200);
                    System.out.println("O erro foi: " + e);
                }
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                response = "Essa é a rota usuario  - PUT";
                res.enviarResponse(exchange, response, 200);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                response = "Essa é a rota usuario - DELETE";
                res.enviarResponse(exchange, response, 200);
            } else {
                response = "Essa é a rota de usuario - método não disponivel" +
                        "O método utilizado foi: " + exchange.getRequestMethod();
                res.enviarResponse(exchange, response, 200);
            }
        }
    }
}


