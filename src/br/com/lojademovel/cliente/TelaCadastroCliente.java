package br.com.lojademovel.cliente;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import org.json.JSONObject;
import br.com.lojademovel.util.CepAPI;

public class TelaCadastroCliente extends JFrame {

    private final JTextField txtNome;
    private final JTextField txtLogradouro;
    private final JTextField txtNumero;
    private final JTextField txtComplemento;
    private final JTextField txtBairro;
    private final JTextField txtCidade;
    private final JTextField txtEstado;
    private final JTextField txtCEP;
    private final JTextField txtTelefone;
    private final ClienteDAO clienteDAO;
    private final Cliente clienteExistente;

    public TelaCadastroCliente(TelaConsultaClientes telaConsultaClientes) {
        this(telaConsultaClientes, null);
    }

    public TelaCadastroCliente(TelaConsultaClientes telaConsultaClientes, Cliente cliente) {
        this.clienteExistente = cliente;
        clienteDAO = new ClienteDAOImpl();

        setTitle(cliente == null ? "Cadastro de Cliente" : "Editar Cliente");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(11, 2));

        txtNome = new JTextField();
        txtCEP = new JTextField();
        txtLogradouro = new JTextField();
        txtNumero = new JTextField();
        txtComplemento = new JTextField();
        txtBairro = new JTextField();
        txtCidade = new JTextField();
        txtEstado = new JTextField();
        txtTelefone = new JTextField();

        JButton btnSalvar = new JButton(cliente == null ? "Salvar" : "Atualizar");
        JButton btnCancelar = new JButton("Cancelar");

        add(new JLabel("Nome:"));
        add(txtNome);
        add(new JLabel("CEP:"));
        add(txtCEP);
        add(new JLabel("Rua:"));
        add(txtLogradouro);
        add(new JLabel("Número:"));
        add(txtNumero);
        add(new JLabel("Complemento:"));
        add(txtComplemento);
        add(new JLabel("Bairro:"));
        add(txtBairro);
        add(new JLabel("Cidade:"));
        add(txtCidade);
        add(new JLabel("Estado:"));
        add(txtEstado);
        add(new JLabel("Telefone:"));
        add(txtTelefone);
        add(btnSalvar);
        add(btnCancelar);

        if (cliente != null) {
            txtNome.setText(cliente.getNome());
            txtLogradouro.setText(cliente.getLogradouro());
            txtNumero.setText(cliente.getNumero());
            txtComplemento.setText(cliente.getComplemento());
            txtBairro.setText(cliente.getBairro());
            txtCidade.setText(cliente.getCidade());
            txtEstado.setText(cliente.getEstado());
            txtCEP.setText(cliente.getCep());
            txtTelefone.setText(cliente.getTelefone());
        }

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String logradouro = txtLogradouro.getText();
            String numero = txtNumero.getText();
            String complemento = txtComplemento.getText();
            String bairro = txtBairro.getText();
            String cidade = txtCidade.getText();
            String estado = txtEstado.getText();
            String cep = txtCEP.getText();
            String telefone = txtTelefone.getText();

            if (clienteExistente == null) {
                Cliente novoCliente = new Cliente();
                novoCliente.setNome(nome);
                novoCliente.setLogradouro(logradouro);
                novoCliente.setNumero(numero);
                novoCliente.setComplemento(complemento);
                novoCliente.setBairro(bairro);
                novoCliente.setCidade(cidade);
                novoCliente.setEstado(estado);
                novoCliente.setCep(cep);
                novoCliente.setTelefone(telefone);

                clienteDAO.inserir(novoCliente);

                JOptionPane.showMessageDialog(TelaCadastroCliente.this, "Cliente salvo com sucesso!");
            } else {
                clienteExistente.setNome(nome);
                clienteExistente.setLogradouro(logradouro);
                clienteExistente.setNumero(numero);
                clienteExistente.setComplemento(complemento);
                clienteExistente.setBairro(bairro);
                clienteExistente.setCidade(cidade);
                clienteExistente.setEstado(estado);
                clienteExistente.setCep(cep);
                clienteExistente.setTelefone(telefone);

                clienteDAO.atualizar(clienteExistente);

                JOptionPane.showMessageDialog(TelaCadastroCliente.this, "Cliente atualizado com sucesso!");
            }

            telaConsultaClientes.carregarDadosClientes();
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        txtCEP.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String cep = txtCEP.getText();
                if (!cep.isEmpty()) {
                    JSONObject dadosCep = CepAPI.buscarCep(cep);
                    if (dadosCep != null) {
                        txtLogradouro.setText(dadosCep.optString("logradouro", ""));
                        txtBairro.setText(dadosCep.optString("bairro", ""));
                        txtCidade.setText(dadosCep.optString("localidade", ""));
                        txtEstado.setText(dadosCep.optString("uf", ""));
                    } else {
                        JOptionPane.showMessageDialog(TelaCadastroCliente.this, "CEP não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
