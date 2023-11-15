package senac.java.DAL;


import java.sql.*;

public class UsersDal {

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
    public int inserirUsuario(String name, String lastName, String cpf, String email) throws SQLException {
        String sql = "INSERT INTO Users (name, lasName, cpf, email) VALUES(?,?,?,?)";
        int linhasAfetadas = 0;
        Connection conexao = conectar();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, cpf);
            statement.setString(4, email);

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
    public ResultSet listarUsuario() throws SQLException {
        String sql = "SELECT * FROM Users";
        ResultSet result = null;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
             result = statement.executeQuery();

            System.out.println("Listagem de usuários: ");

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String lastName = result.getString("lastName");
                String cpf = result.getString("cpf");
                String email = result.getString("email");

                System.out.println("id" + id);
                System.out.println("name" + name);
                System.out.println("lastName" + lastName);
                System.out.println("cpf" + cpf);
                System.out.println("email" + email);
                System.out.println("  ");
            }

            return result;

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi:" + e);

            return result;
        }
    }
    // UPDATE - Atualizar
    public int atualizarUsuario(String name, String lastName, String cpf, String email) throws SQLException{
        String sql = " UPDATE Users SET name = ?, lastName = ?; cpf = ?, email = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try(PreparedStatement statement = conectar().prepareStatement(sql)){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, cpf);
            statement.setString(4, email);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");

            return linhasAfetadas;

        }catch (SQLException e){

            System.out.println("O erro na atualização de dados foi:" + e);
        }
        return linhasAfetadas;
    }
    // DELETE - DELETAR
    public int deletarUsuario() throws SQLException{
        String sql = "DELETE FROM Users WHERE id = ?";
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
