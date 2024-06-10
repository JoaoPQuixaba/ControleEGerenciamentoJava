package br.com.lojademovel.pedido;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private final List<Pedido> pedidos;
    private static final String FILE_NAME = "pedidos.dat";

    public PedidoDAOImpl() {
        pedidos = carregarPedidos();
    }

    @Override
    public void inserir(Pedido pedido) {
        pedido.setId(pedidos.size() + 1);
        pedidos.add(pedido);
        salvarPedidos();
    }

    @Override
    public void atualizar(Pedido pedido) {
        int index = pedidos.indexOf(buscarPorId(pedido.getId()));
        if (index >= 0) {
            pedidos.set(index, pedido);
            salvarPedidos();
        }
    }

    @Override
    public void remover(int id) {
        pedidos.removeIf(pedido -> pedido.getId() == id);
        salvarPedidos();
    }

    @Override
    public Pedido buscarPorId(int id) {
        return pedidos.stream().filter(pedido -> pedido.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }

    @Override
    public List<Pedido> buscarPorNomeCliente(String nomeCliente) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getNome().equalsIgnoreCase(nomeCliente)) {
                resultado.add(pedido);
            }
        }
        return resultado;
    }

    private void salvarPedidos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(pedidos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Pedido> carregarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            pedidos = (List<Pedido>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Se o arquivo não existir ou não puder ser lido, retornamos uma lista vazia
        }
        return pedidos;
    }
}
