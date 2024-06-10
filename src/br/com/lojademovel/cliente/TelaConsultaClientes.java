package br.com.lojademovel.cliente;

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
import java.util.List;

public class TelaConsultaClientes extends JFrame {
    private final JTable tabelaClientes;
    private final JTextField txtPesquisa;
    private final DefaultTableModel modeloTabela;
    private final ClienteDAO clienteDAO;

    public TelaConsultaClientes() {
        clienteDAO = new ClienteDAOImpl();

        setTitle("Consulta de Clientes");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] colunas = {"ID", "Nome", "Rua", "Número", "Complemento", "Bairro", "Cidade", "Estado", "CEP", "Telefone"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaClientes = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnSair = new JButton("Sair");
        txtPesquisa = new JTextField(20);
        JButton btnPesquisar = new JButton("Pesquisar");

        painelBotoes.add(new JLabel("Pesquisar:"));
        painelBotoes.add(txtPesquisa);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnSair);
        add(painelBotoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> adicionarCliente());

        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaClientes.getSelectedRow();
            if (selectedRow != -1) {
                int clienteId = (int) modeloTabela.getValueAt(selectedRow, 0);
                editarCliente(clienteId);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um cliente para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaClientes.getSelectedRow();
            if (selectedRow != -1) {
                int clienteId = (int) modeloTabela.getValueAt(selectedRow, 0);
                excluirCliente(clienteId);
                modeloTabela.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um cliente para excluir.");
            }
        });

        btnPesquisar.addActionListener(e -> {
            String nome = txtPesquisa.getText();
            carregarDadosClientes(nome);
        });

        btnSair.addActionListener(e -> sair());

        carregarDadosClientes();
    }

    public void carregarDadosClientes() {
        carregarDadosClientes(null);
    }

    public void carregarDadosClientes(String nome) {
        modeloTabela.setRowCount(0); // Limpa a tabela
        List<Cliente> clientes = (nome == null || nome.isEmpty()) ? clienteDAO.listarTodos() : clienteDAO.buscarPorNome(nome);
        for (Cliente cliente : clientes) {
            Object[] dados = {cliente.getId(), cliente.getNome(), cliente.getLogradouro(), cliente.getNumero(),
                    cliente.getComplemento(), cliente.getBairro(), cliente.getCidade(),
                    cliente.getEstado(), cliente.getCep(), cliente.getTelefone()};
            modeloTabela.addRow(dados);
        }
    }

    private void adicionarCliente() {
        SwingUtilities.invokeLater(() -> new TelaCadastroCliente(TelaConsultaClientes.this).setVisible(true));
    }

    private void editarCliente(int clienteId) {
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente != null) {
            SwingUtilities.invokeLater(() -> new TelaCadastroCliente(TelaConsultaClientes.this, cliente).setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
        }
    }

    private void excluirCliente(int clienteId) {
        clienteDAO.remover(clienteId);
        JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
    }

    private void sair() {
        SwingUtilities.invokeLater(() -> {
            new TelaInicial().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaConsultaClientes().setVisible(true));
    }
}
