package senac.java.DAL;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class UsersDal {

    //INSERIR - CREATE
    public void inserirUsuario(Connection conexao, String name, String lastName, String cpf, String email) throws SQLException {
        String sql = "INSERT INTO Users (name, lasName, cpf, email) VALUES(?,?,?,?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, cpf);
            statement.setString(4, email);

            int linhasAfetadas = statement.executeUpdate();
            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");
        } catch (SQLException e) {
            System.out.println("O erro na inserção de dados foi:" + e);

        }
    }
    //READ - LISTAR
    public void listarUsuario(Connection conexao) throws SQLException {
        String sql = "SELECT * FROM Users";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
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

        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi:" + e);

        }
    }
    // UPDATE - Atualizar
    public void atualizarUsuario(Connection conexao, String name, String lastName, String cpf, String email) throws SQLException{
        String sql = " UPDATE Users SET name = ?, lastName = ?; cpf = ?, email = ? WHERE id = ?";

        try(PreparedStatement statement = conexao.prepareStatement(sql)){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, cpf);
            statement.setString(4, email);

            int linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");
        }catch (SQLException e){
            System.out.println("O erro na atualização de dados foi:" + e);
        }

    }
    // DELETE - DELETAR
    public void deletarUsuario(Connection conexao, int id) throws SQLException{
        String sql = "DELETE FROM Users WHERE id = ?";

        try(PreparedStatement statement = conexao.prepareStatement(sql)){
          statement.setInt(1, id);

           int linhasAfetadas = statement.executeUpdate();

           System.out.println("Foram feitas " + linhasAfetadas + " modificações no banco de dados");

        }catch (SQLException e){
           System.out.println("O erro na exclusão de dados foi:" + e);
        }
    }
}
