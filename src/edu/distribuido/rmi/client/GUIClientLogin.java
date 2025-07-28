package edu.distribuido.rmi.client;

import edu.distribuido.rmi.common.*;
import edu.distribuido.rmi.common.dto.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class GUIClientLogin extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextArea txtOutput;
    private JButton btnLogin;
    private JButton btnVerInscripciones;
    private JButton btnInscribir;
    private JButton btnSalir;

    private LoginService loginService;
    private InscripcionService inscripcionService;
    private String username;

    public GUIClientLogin() {
        setupUI();
        connectToServer();
    }

    private void setupUI() {
        setTitle("🔐 Sistema de Inscripciones - Cliente RMI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        pack();

        // Panel de login
        JPanel panelLogin = new JPanel(new GridLayout(3, 2, 5, 5));
        panelLogin.setBorder(BorderFactory.createTitledBorder("Inicio de Sesión"));
        panelLogin.add(new JLabel("Usuario (email):"));
        txtUsername = new JTextField("juan.perez@email.com");
        panelLogin.add(txtUsername);
        panelLogin.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField("pass123");
        panelLogin.add(txtPassword);
        btnLogin = new JButton("Iniciar Sesión");
        panelLogin.add(btnLogin);
        add(panelLogin, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelButtons = new JPanel(new FlowLayout());
        btnVerInscripciones = new JButton("Ver Inscripciones");
        btnInscribir = new JButton("Inscribirse");
        btnSalir = new JButton("Salir");
        panelButtons.add(btnVerInscripciones);
        panelButtons.add(btnInscribir);
        panelButtons.add(btnSalir);
        add(panelButtons, BorderLayout.SOUTH);

        // Área de salida
        txtOutput = new JTextArea(10, 40);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtOutput.setEditable(false);
        add(new JScrollPane(txtOutput), BorderLayout.CENTER);

        disableServiceButtons();

        // Eventos
        btnLogin.addActionListener(this::onLogin);
        btnVerInscripciones.addActionListener(e -> verInscripciones());
        btnInscribir.addActionListener(e -> inscribirse());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void connectToServer() {
        try {
            String host = System.getenv().getOrDefault("RMI_HOST", "192.168.193.238");
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            loginService = (LoginService) registry.lookup("LoginService");
            inscripcionService = (InscripcionService) registry.lookup("Inscripciones");
            appendOutput("🔌 Conectado al servidor RMI: " + host + ":1099\n");
        } catch (Exception e) {
            appendOutput("❌ Error al conectar: " + e.getMessage() + "\n");
            btnLogin.setEnabled(false);
            e.printStackTrace();
        }
    }

    private void onLogin(ActionEvent e) {
        username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            appendOutput("⚠️  Por favor ingresa usuario y contraseña.\n");
            return;
        }

        try {
            if (loginService.autenticar(username, password)) {
                appendOutput("✅ Login exitoso\n");
                String rol = loginService.obtenerRol(username);
                appendOutput("🔑 Rol: " + rol + "\n");

                if (!"alumno".equalsIgnoreCase(rol)) {
                    appendOutput("🔒 Acceso denegado al módulo de inscripciones.\n");
                    return;
                }

                enableServiceButtons();
            } else {
                appendOutput("❌ Credenciales incorrectas.\n");
            }
        } catch (Exception ex) {
            appendOutput("❌ Error de conexión: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    private void verInscripciones() {
        try {
            List<InscripcionDTO> lista = inscripcionService.listarInscripciones(username);
            appendOutput("\n📋 Inscripciones actuales:\n");
            if (lista.isEmpty()) {
                appendOutput("📭 No tienes inscripciones.\n");
            } else {
                for (InscripcionDTO ins : lista) {
                    appendOutput(" - " + ins.getNombreMateria() + " (Grupo: " + ins.getIdGrupo() + ")\n");
                }
            }
        } catch (Exception e) {
            appendOutput("❌ Error al obtener inscripciones: " + e.getMessage() + "\n");
        }
    }

    private void inscribirse() {
        String materia = JOptionPane.showInputDialog(this, "Nombre de la materia:", "MATEMATICAS RMI");
        if (materia == null || materia.trim().isEmpty()) return;

        String grupo = JOptionPane.showInputDialog(this, "Número de grupo:", "605 A");
        if (grupo == null || grupo.trim().isEmpty()) return;

        try {
            InscripcionDTO nueva = new InscripcionDTO(username, grupo);
            nueva.setNombreMateria(materia);
            boolean ok = inscripcionService.inscribirAlumno(nueva);
            if (ok) {
                appendOutput("✅ Inscripción exitosa en: " + materia + " (Grupo: " + grupo + ")\n");
            } else {
                appendOutput("❌ Ya estás inscrito en esa materia.\n");
            }
        } catch (Exception e) {
            appendOutput("❌ Error al inscribir: " + e.getMessage() + "\n");
        }
    }

    private void appendOutput(String text) {
        txtOutput.append(text);
    }

    private void enableServiceButtons() {
        btnVerInscripciones.setEnabled(true);
        btnInscribir.setEnabled(true);
        btnSalir.setEnabled(true);
    }

    private void disableServiceButtons() {
        btnVerInscripciones.setEnabled(false);
        btnInscribir.setEnabled(false);
        btnSalir.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GUIClientLogin().setVisible(true);
        });
    }
}
