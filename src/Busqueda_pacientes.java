import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Busqueda_pacientes extends JFrame {
    private JTextField cedulaField;

    public Busqueda_pacientes() {
        setTitle("Buscar Paciente");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaLabel.setBounds(10, 10, 80, 25);
        panel.add(cedulaLabel);

        cedulaField = new JTextField(20);
        cedulaField.setBounds(100, 10, 160, 25);
        panel.add(cedulaField);

        JButton searchButton = new JButton("Buscar");
        searchButton.setBounds(10, 50, 150, 25);
        panel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarPaciente(cedulaField.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton registerButton = new JButton("Registrar Paciente");
        registerButton.setBounds(200, 50, 150, 25);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Registro_pacientes();
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(200, 80, 150, 25);
        panel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
    }

    private void buscarPaciente(String cedula) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM PACIENTE WHERE cedula = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, cedula);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String pacienteInfo = "Nombre: " + resultSet.getString("nombre") + " " +
                    resultSet.getString("apellido") + "\nTeléfono: " + resultSet.getString("telefono") +
                    "\nEdad: " + resultSet.getInt("edad") + "\nDescripción: " + resultSet.getString("descripcion_enfermedad");
            JOptionPane.showMessageDialog(null, pacienteInfo);
        } else {
            JOptionPane.showMessageDialog(null, "Paciente no encontrado");
        }

        resultSet.close();
        statement.close();
        conn.close();
    }
}
