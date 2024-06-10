package br.com.lojademovel.pedido;

import java.util.List;

public interface PedidoDAO {
    void inserir(Pedido pedido);
    void atualizar(Pedido pedido);
    void remover(int id);
    Pedido buscarPorId(int id);
    List<Pedido> listarTodos();

    List<Pedido> buscarPorNomeCliente(String nomeCliente); // Novo método
}
