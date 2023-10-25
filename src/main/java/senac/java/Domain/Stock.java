package senac.java.Domain;

import org.json.JSONObject;

import java.util.List;

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
    public JSONObject arrayToJson(List<Stock> arrayStockList) {
        JSONObject json = new JSONObject();

        if (!arrayStockList.isEmpty()) {

            int keyjson = 0;
            for (Stock stock : arrayStockList) {
                JSONObject jsonStock = new JSONObject();

                jsonStock.put("name", name = stock.getName());
                jsonStock.put("factory", factory = stock.getFactory());
                jsonStock.put("quantity", quantity = stock.getQuantity());

                json.put(String.valueOf(keyjson), jsonStock);
                keyjson++;

            }


            return json;
        } else {
            return null;
        }
    }

    public static Stock getStock ( int index, List<Stock> stockList){
        if (index >= 0 && index < stockList.size()) {
            return stockList.get(index);
        } else {
            return null;
        }
    }

    public static List<Stock> getAllStocks (List < Stock > stockList) {
        return stockList;
    }
}


