package br.com.lojademovel.movel;

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

public class TelaConsultaMoveis extends JFrame {
    private final JTable tabelaMoveis;
    private final JTextField txtPesquisa;
    private final DefaultTableModel modeloTabela;
    private final MovelDAO movelDAO;

    public TelaConsultaMoveis() {
        movelDAO = new MovelDAOImpl();

        setTitle("Consulta de Móveis");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] colunas = {"ID", "Nome", "Quantidade em Estoque", "Marca", "Fornecedor", "Preço"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaMoveis = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaMoveis);
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

        btnAdicionar.addActionListener(e -> adicionarMovel());

        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaMoveis.getSelectedRow();
            if (selectedRow != -1) {
                int movelId = (int) modeloTabela.getValueAt(selectedRow, 0);
                editarMovel(movelId);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um móvel para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaMoveis.getSelectedRow();
            if (selectedRow != -1) {
                int movelId = (int) modeloTabela.getValueAt(selectedRow, 0);
                excluirMovel(movelId);
                modeloTabela.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um móvel para excluir.");
            }
        });

        btnPesquisar.addActionListener(e -> {
            String nome = txtPesquisa.getText();
            carregarDadosMoveis(nome);
        });

        btnSair.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });

        carregarDadosMoveis();
    }

    public void carregarDadosMoveis() {
        carregarDadosMoveis(null);
    }

    public void carregarDadosMoveis(String nome) {
        modeloTabela.setRowCount(0); // Limpa a tabela
        List<Movel> moveis = (nome == null || nome.isEmpty()) ? movelDAO.listarTodos() : movelDAO.buscarPorNome(nome);
        for (Movel movel : moveis) {
            Object[] dados = {movel.getId(), movel.getNome(), movel.getQuantidadeEstoque(), movel.getMarca(),
                    movel.getFornecedor(), movel.getPreco()};
            modeloTabela.addRow(dados);
        }
    }

    private void adicionarMovel() {
        SwingUtilities.invokeLater(() -> new TelaCadastroMovel(TelaConsultaMoveis.this).setVisible(true));
    }

    private void editarMovel(int movelId) {
        Movel movel = movelDAO.buscarPorId(movelId);
        if (movel != null) {
            SwingUtilities.invokeLater(() -> new TelaCadastroMovel(TelaConsultaMoveis.this, movel).setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Móvel não encontrado.");
        }
    }

    private void excluirMovel(int movelId) {
        movelDAO.remover(movelId);
        JOptionPane.showMessageDialog(this, "Móvel excluído com sucesso.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaConsultaMoveis().setVisible(true));
    }
}
