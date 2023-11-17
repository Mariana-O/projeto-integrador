package senac.java.DAL;

import java.sql.*;

public class SalesDal {
    public Connection conectar() {
        Connection conexao = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi";

            String usuario = "116128412023.1";
            String senha = "senac@12841";

            conexao = DriverManager.getConnection(url, usuario, senha);

            if (conexao != null) {
                return conexao;
            }


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("o erro foi:" + e);

        } finally {

            try {

                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println("o erro no finally foi:" + e);

            }

        }
        return conexao;

    }

    public int insertSales(String user, String products, float value, boolean finishedSale, float discount, String sale) throws SQLException {
        String sql = "INSERT INTO Sales (user, products, value, finishedSale, discount, sale) VALUES(?,?,?,?,?,?)";
        int linhasAfetadas = 0;
        Connection conexao = conectar();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, user);
            statement.setString(2, products);
            statement.setFloat(3, value);
            statement.setBoolean(4, finishedSale);
            statement.setFloat(5, discount);
            statement.setString(6, sale);
            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");
            conexao.close();
            return linhasAfetadas;

        } catch (SQLException e) {
            System.out.println("O erro na inserção de dados foi:" + e);
            conexao.close();
        }
        conexao.close();
        return linhasAfetadas;
    }

    //READ - LISTAR
    public ResultSet listSales() throws SQLException {
        String sql = "SELECT * FROM Sales";
        ResultSet result = null;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            result = statement.executeQuery();

            System.out.println("Listagem de Vendas: ");

            while (result.next()) {
                int id = result.getInt("id");
                String user = result.getString("user");
                String products = result.getString("products");
                float value = Float.parseFloat(result.getString("value"));
                boolean finishedSale = Boolean.parseBoolean(result.getString("finishedSale"));
                float discount = Float.parseFloat(result.getString("discount"));
                String sale = result.getString("sale");

                System.out.println("id" + id);
                System.out.println("name" + user);
                System.out.println("products" + products);
                System.out.println("value" + value);
                System.out.println("finishedSale" + finishedSale);
                System.out.println("discount" + discount);
                System.out.println("sale" + sale);
                System.out.println("  ");
            }
            return result;

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi:" + e);

            return result;
        }
    }

    // UPDATE - Atualizar
    public int updateSales(int id, String user, String products, float value, boolean finishedSale, float discount, String sale) throws SQLException {
        String sql = " UPDATE Sales SET name = ?, lastName = ?; cpf = ?, email = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, user);
            statement.setString(3, products);
            statement.setFloat(4, value);
            statement.setBoolean(5, finishedSale);
            statement.setFloat(6, discount);
            statement.setString(7, sale);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");

            return linhasAfetadas;

        } catch (SQLException e) {

            System.out.println("O erro na atualização de dados foi:" + e);
        }
        return linhasAfetadas;
    }

    // DELETE - DELETAR
    public int deleteSales(int id) throws SQLException {
        String sql = "DELETE FROM Sales WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
//          statement.setInt(1, id);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");
            return linhasAfetadas;

        } catch (SQLException e) {
            System.out.println("O erro na exclusão de dados foi:" + e);
        }
        return linhasAfetadas;
    }
}
