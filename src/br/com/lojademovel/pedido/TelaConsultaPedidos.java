package br.com.lojademovel.pedido;

import br.com.lojademovel.main.TelaInicial;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaConsultaPedidos extends JFrame {
    private final JTable table;
    private final DefaultTableModel model;
    private final JTextField txtPesquisa;
    private final PedidoDAO pedidoDAO;

    public TelaConsultaPedidos() {
        pedidoDAO = new PedidoDAOImpl();
        setTitle("Consulta de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel para os botões
        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Pedido");
        JButton btnSair = new JButton("Sair");
        txtPesquisa = new JTextField(20);
        JButton btnPesquisar = new JButton("Pesquisar");

        painelBotoes.add(new JLabel("Pesquisar:"));
        painelBotoes.add(txtPesquisa);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnSair);
        add(painelBotoes, BorderLayout.SOUTH);

        // Configuração da tabela
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Cliente");
        model.addColumn("Data do Pedido");
        model.addColumn("Data do Pagamento");
        model.addColumn("Data da Entrega");
        model.addColumn("Descrição");
        model.addColumn("Itens");
        model.addColumn("Editar");
        model.addColumn("Deletar"); // Nova coluna

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
        table.getColumnModel().getColumn(8).setPreferredWidth(50); // Largura da nova coluna

        // Listener para clique nas colunas "Itens", "Editar" e "Deletar"
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 6) {
                    int pedidoId = (int) model.getValueAt(row, 0);
                    Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
                    if (pedido != null) {
                        PedidoItemViewer.showItems(pedido.getItens());
                    }
                } else if (col == 7) {
                    int pedidoId = (int) model.getValueAt(row, 0);
                    Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
                    if (pedido != null) {
                        editarPedido(pedido);
                    }
                } else if (col == 8) {
                    int pedidoId = (int) model.getValueAt(row, 0);
                    int resposta = JOptionPane.showConfirmDialog(
                            null,
                            "Tem certeza que deseja deletar este pedido?",
                            "Confirmação de Deleção",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (resposta == JOptionPane.YES_OPTION) {
                        pedidoDAO.remover(pedidoId);
                        carregarPedidos(); // Atualiza a tabela após deletar
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Ação dos botões
        btnAdicionar.addActionListener(e -> adicionarPedido());

        btnSair.addActionListener(e -> sair());

        btnPesquisar.addActionListener(e -> {
            String nome = txtPesquisa.getText();
            carregarPedidos(nome);
        });

        carregarPedidos();
    }

    public void carregarPedidos() {
        carregarPedidos(null);
    }

    public void carregarPedidos(String nomeCliente) {
        model.setRowCount(0); // Limpa a tabela
        List<Pedido> pedidos = (nomeCliente == null || nomeCliente.isEmpty()) ? pedidoDAO.listarTodos() : pedidoDAO.buscarPorNomeCliente(nomeCliente);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Pedido pedido : pedidos) {
            String dataPedido = (pedido.getDataPedido() != null) ? sdf.format(pedido.getDataPedido()) : "N/A";
            String dataPagamento = (pedido.getDataPagamento() != null) ? sdf.format(pedido.getDataPagamento()) : "N/A";
            String dataEntrega = (pedido.getDataEntrega() != null) ? sdf.format(pedido.getDataEntrega()) : "N/A";

            model.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getCliente().getNome(),
                    dataPedido,
                    dataPagamento,
                    dataEntrega,
                    pedido.getDescricao(),
                    "Itens",
                    "Editar",
                    "Deletar" // Nova célula
            });
        }
    }

    private void adicionarPedido() {
        SwingUtilities.invokeLater(() -> new TelaCadastroPedido(TelaConsultaPedidos.this).setVisible(true));
    }

    private void editarPedido(Pedido pedido) {
        DialogoEditarPedido dialogo = new DialogoEditarPedido(this, pedido, pedidoDAO);
        dialogo.setVisible(true);
        carregarPedidos();  // Atualiza a tabela após edição
    }

    public void adicionarPedidoNaTabela(Pedido pedido) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataPedido = (pedido.getDataPedido() != null) ? sdf.format(pedido.getDataPedido()) : "N/A";
        String dataPagamento = (pedido.getDataPagamento() != null) ? sdf.format(pedido.getDataPagamento()) : "N/A";
        String dataEntrega = (pedido.getDataEntrega() != null) ? sdf.format(pedido.getDataEntrega()) : "N/A";

        model.addRow(new Object[]{
                pedido.getId(),
                pedido.getCliente().getNome(),
                dataPedido,
                dataPagamento,
                dataEntrega,
                pedido.getDescricao(),
                "Itens",
                "Editar",
                "Deletar" // Nova célula
        });
    }

    private void sair() {
        SwingUtilities.invokeLater(() -> {
            new TelaInicial().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaConsultaPedidos().setVisible(true));
    }
}
