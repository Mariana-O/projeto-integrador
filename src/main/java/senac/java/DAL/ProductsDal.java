package senac.java.DAL;

import java.sql.*;

public class ProductsDal {

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

    //INSERIR - CREATE
    public int insertProducts(String name, String description, int price) throws SQLException {
        String sql = "INSERT INTO Products (name, description, price) VALUES(?,?,?)";
        int linhasAfetadas = 0;
        Connection conexao = conectar();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, price);


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
    public ResultSet listProducts() throws SQLException {
        String sql = "SELECT * FROM Products";
        ResultSet result = null;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            result = statement.executeQuery();

            System.out.println("Listagem de usuários: ");

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                String price = String.valueOf(result.getInt("price"));



                System.out.println("id" + id);
                System.out.println("name" + name);
                System.out.println("lastName" + description);
                System.out.println("cpf" + price);
                System.out.println("  ");
            }

            return result;

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi:" + e);

            return result;
        }
    }
    // UPDATE - Atualizar
    public int updateProducts(int id, String name, String description, int price) throws SQLException{
        String sql = " UPDATE Products SET name = ?, lastName = ?; cpf = ?, email = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try(PreparedStatement statement = conectar().prepareStatement(sql)){
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setInt(4, price);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");

            return linhasAfetadas;

        }catch (SQLException e){

            System.out.println("O erro na atualização de dados foi:" + e);
        }
        return linhasAfetadas;
    }
    // DELETE - DELETAR
    public int deleteProducts(int id) throws SQLException{
        String sql = "DELETE FROM Products WHERE id = ?";
        int linhasAfetadas = 0;

        try(PreparedStatement statement = conectar().prepareStatement(sql)){
//          statement.setInt(1, id);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");
            return linhasAfetadas;

        }catch (SQLException e){
            System.out.println("O erro na exclusão de dados foi:" + e);
        }
        return linhasAfetadas;
    }
}


