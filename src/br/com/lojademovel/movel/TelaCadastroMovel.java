package br.com.lojademovel.movel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroMovel extends JFrame {

    private final JTextField txtNome;
    private final JTextField txtQuantidadeEstoque;
    private final JTextField txtMarca;
    private final JTextField txtFornecedor;
    private final JTextField txtPreco;
    private final MovelDAO movelDAO;
    private final Movel movelExistente;

    public TelaCadastroMovel(TelaConsultaMoveis telaConsultaMoveis) {
        this(telaConsultaMoveis, null);
    }

    public TelaCadastroMovel(TelaConsultaMoveis telaConsultaMoveis, Movel movel) {
        this.movelExistente = movel;
        movelDAO = new MovelDAOImpl();

        setTitle(movel == null ? "Cadastro de Móvel" : "Editar Móvel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        txtNome = new JTextField();
        txtQuantidadeEstoque = new JTextField();
        txtMarca = new JTextField();
        txtFornecedor = new JTextField();
        txtPreco = new JTextField();

        JButton btnSalvar = new JButton(movel == null ? "Salvar" : "Atualizar");
        JButton btnCancelar = new JButton("Cancelar");

        add(new JLabel("Nome:"));
        add(txtNome);
        add(new JLabel("Quantidade em Estoque:"));
        add(txtQuantidadeEstoque);
        add(new JLabel("Marca:"));
        add(txtMarca);
        add(new JLabel("Fornecedor:"));
        add(txtFornecedor);
        add(new JLabel("Preço:"));
        add(txtPreco);
        add(btnSalvar);
        add(btnCancelar);

        if (movel != null) {
            txtNome.setText(movel.getNome());
            txtQuantidadeEstoque.setText(String.valueOf(movel.getQuantidadeEstoque()));
            txtMarca.setText(movel.getMarca());
            txtFornecedor.setText(movel.getFornecedor());
            txtPreco.setText(String.valueOf(movel.getPreco()));
        }

        btnSalvar.addActionListener(e -> {
            if (txtNome.getText().trim().isEmpty() || txtQuantidadeEstoque.getText().trim().isEmpty() ||
                    txtMarca.getText().trim().isEmpty() || txtFornecedor.getText().trim().isEmpty() ||
                    txtPreco.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(TelaCadastroMovel.this, "Todos os campos são obrigatórios.");
                return;
            }

            String nome = txtNome.getText();
            int quantidadeEstoque;
            try {
                quantidadeEstoque = Integer.parseInt(txtQuantidadeEstoque.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TelaCadastroMovel.this, "Quantidade em Estoque deve ser um número inteiro.");
                return;
            }

            String marca = txtMarca.getText();
            String fornecedor = txtFornecedor.getText();
            double preco;
            try {
                preco = Double.parseDouble(txtPreco.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TelaCadastroMovel.this, "Preço deve ser um número.");
                return;
            }

            if (movelExistente == null) {
                Movel novoMovel = new Movel();
                novoMovel.setNome(nome);
                novoMovel.setQuantidadeEstoque(quantidadeEstoque);
                novoMovel.setMarca(marca);
                novoMovel.setFornecedor(fornecedor);
                novoMovel.setPreco(preco);

                movelDAO.inserir(novoMovel);

                JOptionPane.showMessageDialog(TelaCadastroMovel.this, "Móvel salvo com sucesso!");
            } else {
                movelExistente.setNome(nome);
                movelExistente.setQuantidadeEstoque(quantidadeEstoque);
                movelExistente.setMarca(marca);
                movelExistente.setFornecedor(fornecedor);
                movelExistente.setPreco(preco);

                movelDAO.atualizar(movelExistente);

                JOptionPane.showMessageDialog(TelaCadastroMovel.this, "Móvel atualizado com sucesso!");
            }

            telaConsultaMoveis.carregarDadosMoveis();
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
