package br.com.lojademovel.movel;

import br.com.lojademovel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovelDAOImpl implements MovelDAO {

    @Override
    public void inserir(Movel movel) {
        String sql = "INSERT INTO Movel (nome, quantidadeEstoque, marca, fornecedor, preco) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movel.getNome());
            pstmt.setInt(2, movel.getQuantidadeEstoque());
            pstmt.setString(3, movel.getMarca());
            pstmt.setString(4, movel.getFornecedor());
            pstmt.setDouble(5, movel.getPreco());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Movel buscarPorId(int id) {
        String sql = "SELECT * FROM Movel WHERE id = ?";
        Movel movel = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                movel = new Movel();
                movel.setId(rs.getInt("id"));
                movel.setNome(rs.getString("nome"));
                movel.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                movel.setMarca(rs.getString("marca"));
                movel.setFornecedor(rs.getString("fornecedor"));
                movel.setPreco(rs.getDouble("preco"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movel;
    }

    @Override
    public void atualizar(Movel movel) {
        String sql = "UPDATE Movel SET nome = ?, quantidadeEstoque = ?, marca = ?, fornecedor = ?, preco = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movel.getNome());
            pstmt.setInt(2, movel.getQuantidadeEstoque());
            pstmt.setString(3, movel.getMarca());
            pstmt.setString(4, movel.getFornecedor());
            pstmt.setDouble(5, movel.getPreco());
            pstmt.setInt(6, movel.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM Movel WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movel> listarTodos() {
        String sql = "SELECT * FROM Movel";
        List<Movel> moveis = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Movel movel = new Movel();
                movel.setId(rs.getInt("id"));
                movel.setNome(rs.getString("nome"));
                movel.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                movel.setMarca(rs.getString("marca"));
                movel.setFornecedor(rs.getString("fornecedor"));
                movel.setPreco(rs.getDouble("preco"));
                moveis.add(movel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moveis;
    }

    @Override
    public List<Movel> buscarPorNome(String nome) { // Add this method
        String sql = "SELECT * FROM Movel WHERE nome LIKE ?";
        List<Movel> moveis = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nome + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Movel movel = new Movel();
                movel.setId(rs.getInt("id"));
                movel.setNome(rs.getString("nome"));
                movel.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                movel.setMarca(rs.getString("marca"));
                movel.setFornecedor(rs.getString("fornecedor"));
                movel.setPreco(rs.getDouble("preco"));
                moveis.add(movel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moveis;
    }
}
