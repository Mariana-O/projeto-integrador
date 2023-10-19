package senac.java.Domain;

import org.json.JSONObject;

import java.util.List;

public class Users {
    int id = 0;
    String name = "";
    String lastName = "";
    String cpf = "";
    String email = "";


    public Users() {
    }

    public Users(String name, String lastName, String cpf, String email) {
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("last_name", lastName);
        json.put("cpf", cpf);
        json.put("email", email);



        return json;
    }


    public JSONObject arrayToJson(List<Users> arrayUserList) {
        JSONObject json = new JSONObject();

        if (!arrayUserList.isEmpty()) {

           int keyjson = 0;
            for (Users users : arrayUserList) {
                JSONObject jsonUser = new JSONObject();

                jsonUser.put("name", name = users.getName());
                jsonUser.put("last_name", lastName = users.getLastName());
                jsonUser.put("cpf", cpf = users.getCpf());
                jsonUser.put("email", email = users.getEmail());

                keyjson++;
                json.put(String.valueOf(keyjson), jsonUser);

            }


            return json;
        } else {
            return null;
        }
    }

        public static Users getUser ( int index, List<Users > usersList){
            if (index >= 0 && index < usersList.size()) {
                return usersList.get(index);
            } else {
                return null;
            }
        }

        public static List<Users> getAllUsers (List < Users > usersList) {
            return usersList;
        }
    }