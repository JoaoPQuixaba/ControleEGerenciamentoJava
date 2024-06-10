package br.com.lojademovel.pedido;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.List;

public class PedidoItemViewer extends JFrame {
    public PedidoItemViewer(List<ItemPedido> itens) {
        setTitle("Itens do Pedido");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crie o painel e adicione-o à janela
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (ItemPedido item : itens) {
            JLabel label = new JLabel(item.toString());
            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void showItems(List<ItemPedido> itens) {
        PedidoItemViewer viewer = new PedidoItemViewer(itens);
        viewer.setVisible(true);
    }
}
