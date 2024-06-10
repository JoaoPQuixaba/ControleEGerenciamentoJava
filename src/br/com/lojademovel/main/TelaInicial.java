package br.com.lojademovel.main;

import br.com.lojademovel.cliente.TelaConsultaClientes;
import br.com.lojademovel.movel.TelaConsultaMoveis;
import br.com.lojademovel.pedido.TelaConsultaPedidos;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        setTitle("Loja PrintMoveis - Tela Inicial");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton btnClientes = createButton("Clientes", new ImageIcon("icons/clienteicon.png"), e -> {
            new TelaConsultaClientes().setVisible(true);
            dispose();
        });

        JButton btnMoveis = createButton("Móveis", new ImageIcon("icons/moveis.png"), e -> {
            new TelaConsultaMoveis().setVisible(true);
            dispose();
        });

        JButton btnPedidos = createButton("Pedidos", new ImageIcon("icons/pedidos.png"), e -> {
            new TelaConsultaPedidos().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnClientes);
        buttonPanel.add(btnMoveis);
        buttonPanel.add(btnPedidos);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private JButton createButton(String text, ImageIcon icon, ActionListener actionListener) {
        JButton button = new JButton(text, icon);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.addActionListener(actionListener);

        button.setBackground(new Color(255, 140, 0));
        button.setForeground(Color.DARK_GRAY);
        button.setBorder(new RoundedBorder(20));
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 160, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 140, 0));
            }
        });

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 120, 0));
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 160, 0));
            }
        });

        return button;
    }

    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(c.getBackground());
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = radius + 1;
            return insets;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
