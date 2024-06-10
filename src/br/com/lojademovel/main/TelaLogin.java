package br.com.lojademovel.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;

public class TelaLogin extends JFrame {
    private final JTextField txtUsuario;
    private final JPasswordField txtSenha;

    public TelaLogin() {
        setTitle("Tela de Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        JLabel lblUsuario = new JLabel("Usuário:");
        JLabel lblSenha = new JLabel("Senha:");

        txtUsuario = new JTextField();
        txtSenha = new JPasswordField();
        JButton btnLogin = new JButton("Login");
        JButton btnSair = new JButton("Sair");
        add(lblUsuario);
        add(txtUsuario);
        add(lblSenha);
        add(txtSenha);
        add(btnLogin);
        add(btnSair);

        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String senha = new String(txtSenha.getPassword());


            if (autenticar(usuario, senha)) {
                JOptionPane.showMessageDialog(TelaLogin.this, "Login bem-sucedido!");
                dispose();
                TelaInicial telaInicial = new TelaInicial();
                telaInicial.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(TelaLogin.this, "Usuário ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSair.addActionListener(e -> System.exit(0));
    }

    private boolean autenticar(String usuario, String senha) {
        return "admin".equals(usuario) && "admin".equals(senha);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
