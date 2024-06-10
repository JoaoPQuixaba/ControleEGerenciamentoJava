package br.com.lojademovel.pedido;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogoEditarPedido extends JDialog {
    private final Pedido pedido;
    private final PedidoDAO pedidoDAO;

    private final JTextField txtDataPagamento;
    private final JTextField txtDataEntrega;
    private final JTextArea txtDescricao;

    public DialogoEditarPedido(JFrame parent, Pedido pedido, PedidoDAO pedidoDAO) {
        super(parent, "Editar Pedido", true);
        this.pedido = pedido;
        this.pedidoDAO = pedidoDAO;

        setLayout(new GridLayout(5, 2));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        txtDataPagamento = new JTextField();
        txtDataEntrega = new JTextField();
        txtDescricao = new JTextArea(pedido.getDescricao());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (pedido.getDataPagamento() != null) {
            txtDataPagamento.setText(sdf.format(pedido.getDataPagamento()));
        }

        if (pedido.getDataEntrega() != null) {
            txtDataEntrega.setText(sdf.format(pedido.getDataEntrega()));
        }

        add(new JLabel("Data de Pagamento (dd/MM/yyyy):"));
        add(txtDataPagamento);
        add(new JLabel("Data de Entrega (dd/MM/yyyy):"));
        add(txtDataEntrega);
        add(new JLabel("Descrição:"));
        add(new JScrollPane(txtDescricao));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarAlteracoes());
        add(btnSalvar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void salvarAlteracoes() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dataPagamento = sdf.parse(txtDataPagamento.getText());
            Date dataEntrega = sdf.parse(txtDataEntrega.getText());

            pedido.setDataPagamento(dataPagamento);
            pedido.setDataEntrega(dataEntrega);
            pedido.setDescricao(txtDescricao.getText());

            pedidoDAO.atualizar(pedido);

            JOptionPane.showMessageDialog(this, "Pedido atualizado com sucesso!");
            dispose();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy.");
        }
    }
}
