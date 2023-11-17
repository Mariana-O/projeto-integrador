package senac.java.Domain;

import org.json.JSONObject;

import java.sql.Date;
import java.util.List;


public class Sales {
    public int id = 0;
    public String user = "";
    public String products = "";
    public float value = 0;
    public boolean finishedSale = false;
    public float discount = 0;
    public String sale = "";


    public Sales() {
    }

    public Sales(String user, String products, float valor, boolean finishedSale, float discount, String sale) {
        this.user = user;
        this.products = products;
        this.value =  valor;
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

    public void setValor(float valor) {
        this.value = valor;
    }

    public boolean getFinishedSale() {
        return finishedSale;
    }

    public void setFinishedSale(boolean finishedSale) {
        this.finishedSale = finishedSale;
    }

    public double getDiscount(){return discount;}

    public void setDiscount(float discount){this.discount = discount;}

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
        json.put("finishedSale", finishedSale);
        json.put("discount", discount);
        json.put("sale", sale);

        return json;
    }

    public JSONObject arrayToJson(List<Sales> arraySalesList) {
        JSONObject json = new JSONObject();

        if (!arraySalesList.isEmpty()) {

            int keyjson = 0;
            for (Sales sales : arraySalesList) {
                JSONObject jsonSales = new JSONObject();

                jsonSales.put("user", user);
                jsonSales.put("products", products);
                jsonSales.put("value", value);
                jsonSales.put("finishedSale", finishedSale);
                jsonSales.put("discount", discount);
                jsonSales.put("sale", sale);

                json.put(String.valueOf(keyjson), jsonSales);
                keyjson++;

            }
            return json;
        } else {
            return null;
        }
    }

    public static Sales getSales(int index, List<Sales> salesList) {
        if (index >= 0 && index < salesList.size()) {
            return salesList.get(index);
        }else {
            return null;
        }
    }

        public static List<Sales> getAllSales(List<Sales> salesList) {return salesList;}
    }





