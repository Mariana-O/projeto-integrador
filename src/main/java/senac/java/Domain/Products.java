package senac.java.Domain;

import org.json.JSONObject;

import java.util.List;

public class Products {

    int id = 0;
    String name = "";
    String description = "";
    int price = 0;


    public Products() {
    }

    public Products(String name, String description, int price) {
        this.name = name;
        this.description  = description ;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void set(String name) {
        this.name = name;
    }

    public String getDescription () {
        return description ;
    }

    public void setDescription (String description ) {
        this.description  = description ;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("description", description );
        json.put("price", price);

        return json;
    }
    public JSONObject arrayToJson(List<Products> arrayProductsList) {
        JSONObject json = new JSONObject();

        if (!arrayProductsList.isEmpty()) {

            int keyjson = 0;
            for (Products products : arrayProductsList) {
                JSONObject jsonStock = new JSONObject();

                jsonStock.put("name", name = products.getName());
                jsonStock.put("description", description  = products.getDescription());
                jsonStock.put("price", price = products.getPrice());

                json.put(String.valueOf(keyjson), jsonStock);
                keyjson++;

            }


            return json;
        } else {
            return null;
        }
    }

    public static Products getProducts (int index, List<Products> productsList){
        if (index >= 0 && index < productsList.size()) {
            return productsList.get(index);
        } else {
            return null;
        }
    }

    public static List<Products> getAllProducts (List <Products> productsList) {
        return productsList;
    }
}


