package br.com.lojademovel.cliente;

import java.util.List;

public interface ClienteDAO {
    void inserir(Cliente cliente);
    Cliente buscarPorId(int id);
    void atualizar(Cliente cliente);
    void remover(int id);
    List<Cliente> listarTodos();
    List<Cliente> buscarPorNome(String nome);
}
