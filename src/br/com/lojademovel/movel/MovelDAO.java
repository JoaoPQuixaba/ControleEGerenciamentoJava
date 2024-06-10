package br.com.lojademovel.movel;

import java.util.List;

public interface MovelDAO {
    void inserir(Movel movel);
    Movel buscarPorId(int id);
    void atualizar(Movel movel);
    void remover(int id);
    List<Movel> listarTodos();
    List<Movel> buscarPorNome(String nome); // Add this line
}
