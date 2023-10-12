package senac.java.Domain;

import org.json.JSONObject;

public class Stock {

    int id = 0;
    String name = "";
    String factory = "";
    int quantity = 0;


    public Stock() {
    }

    public Stock(String name, String factory, int quantity) {
        this.name = name;
        this.factory = factory;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void set(String name) {
        this.name = name;
    }

    public String getFactory() {
        return factory;
    }

    public void setfactory(String factory) {
        this.factory = factory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("factory", factory);
        json.put("quantity", quantity);

        return json;
    }
}


