package senac.java.Domain;

import org.json.JSONObject;

import java.sql.Date;

public class Sales {
    int id = 0;
    String user = "";
    String products = "";
    double value = 0;
    boolean finishedSale = false;
    double discount = 0;
    String sale = "";


    public Sales() {
    }

    public Sales(String user, String products, double valor, boolean finishedSale, double discount, String sale) {
        this.user = user;
        this.products = products;
        this.value = valor;
        this.finishedSale = finishedSale;
        this.discount = discount;
        this.sale = sale;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public double getValor() {
        return value;
    }

    public void setValor(double valor) {
        this.value = valor;
    }

    public boolean getFinishedSale() {
        return finishedSale;
    }

    public void setFinishedSale(boolean finishedSale) {
        this.finishedSale = finishedSale;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("user", user);
        json.put("products", products);
        json.put("value", value);
        json.put("finishedsale", finishedSale);
        json.put("discount", discount);
        json.put("sale", sale);

        return json;
    }
}



