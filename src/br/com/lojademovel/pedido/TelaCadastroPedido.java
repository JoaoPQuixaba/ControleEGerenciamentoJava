package br.com.lojademovel.pedido;

import br.com.lojademovel.cliente.Cliente;
import br.com.lojademovel.cliente.ClienteDAO;
import br.com.lojademovel.cliente.ClienteDAOImpl;
import br.com.lojademovel.movel.Movel;
import br.com.lojademovel.movel.MovelDAO;
import br.com.lojademovel.movel.MovelDAOImpl;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class TelaCadastroPedido extends JFrame {
    private final JComboBox<Cliente> comboBoxCliente;
    private final JComboBox<Movel> comboBoxMovel;
    private final JTextField txtQuantidade;
    private final JTextArea textAreaItensPedido;
    private final JLabel lblTotal;

    private final MovelDAO movelDAO;
    private final PedidoDAO pedidoDAO;
    private final TelaConsultaPedidos telaConsultaPedidos;

    private final ArrayList<ItemPedido> itensPedido;

    public TelaCadastroPedido(TelaConsultaPedidos telaConsultaPedidos) {
        ClienteDAO clienteDAO = new ClienteDAOImpl();
        movelDAO = new MovelDAOImpl();
        pedidoDAO = new PedidoDAOImpl();
        this.telaConsultaPedidos = telaConsultaPedidos;
        itensPedido = new ArrayList<>();

        setTitle("Cadastro de Pedido");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        comboBoxCliente = new JComboBox<>(clienteDAO.listarTodos().toArray(new Cliente[0]));
        comboBoxMovel = new JComboBox<>(movelDAO.listarTodos().toArray(new Movel[0]));
        txtQuantidade = new JTextField();
        JButton btnAdicionarItem = new JButton("Adicionar Item");
        JButton btnFinalizarPedido = new JButton("Finalizar Pedido");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnVerItens = new JButton("Ver Itens");
        textAreaItensPedido = new JTextArea();
        textAreaItensPedido.setEditable(false);
        lblTotal = new JLabel("Total: R$ 0.00");

        add(new JLabel("Cliente:"));
        add(comboBoxCliente);
        add(new JLabel("Móvel:"));
        add(comboBoxMovel);
        add(new JLabel("Quantidade:"));
        add(txtQuantidade);
        add(btnAdicionarItem);
        add(new JLabel());
        add(new JScrollPane(textAreaItensPedido));
        add(lblTotal);
        add(btnFinalizarPedido);
        add(btnCancelar);
        add(btnVerItens);

        btnAdicionarItem.addActionListener(e -> adicionarItemPedido());

        btnFinalizarPedido.addActionListener(e -> finalizarPedido());

        btnCancelar.addActionListener(e -> dispose());

        btnVerItens.addActionListener(e -> PedidoItemViewer.showItems(itensPedido));
    }

    private void adicionarItemPedido() {
        Movel movel = (Movel) comboBoxMovel.getSelectedItem();
        int quantidade;

        try {
            quantidade = Integer.parseInt(txtQuantidade.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número inteiro.");
            return;
        }

        if (movel == null || quantidade <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um móvel e insira uma quantidade válida.");
            return;
        }

        if (movel.getQuantidadeEstoque() < quantidade) {
            JOptionPane.showMessageDialog(this, "Quantidade em estoque insuficiente.");
            return;
        }

        movel.setQuantidadeEstoque(movel.getQuantidadeEstoque() - quantidade);
        movelDAO.atualizar(movel);

        ItemPedido item = new ItemPedido();
        item.setMovel(movel);
        item.setQuantidade(quantidade);
        itensPedido.add(item);

        atualizarTextArea();
        calcularTotal();
    }

    private void finalizarPedido() {
        Cliente cliente = (Cliente) comboBoxCliente.getSelectedItem();

        if (cliente == null || itensPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente e adicione ao menos um item ao pedido.");
            return;
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItens(itensPedido);
        pedido.setDataPedido(new Date());

        pedidoDAO.inserir(pedido);
        telaConsultaPedidos.adicionarPedidoNaTabela(pedido);

        JOptionPane.showMessageDialog(this, "Pedido realizado com sucesso!");

        dispose();
    }

    private void atualizarTextArea() {
        StringBuilder builder = new StringBuilder();
        for (ItemPedido item : itensPedido) {
            builder.append(item.getMovel().getNome())
                    .append(" - Quantidade: ")
                    .append(item.getQuantidade())
                    .append("\n");
        }
        textAreaItensPedido.setText(builder.toString());
    }

    private void calcularTotal() {
        double total = 0.0;
        for (ItemPedido item : itensPedido) {
            total += item.getMovel().getPreco() * item.getQuantidade();
        }
        lblTotal.setText("Total: R$ " + String.format("%.2f", total));
    }
}
