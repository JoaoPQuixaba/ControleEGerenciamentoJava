package br.com.lojademovel.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } else {
                System.out.println("Falha ao estabelecer conexão com o banco de dados.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar conectar ao banco de dados: " + e.getMessage());
        }
    }
}
