import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registro_pacientes extends JFrame {
    private JTextField historialField;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField telefonoField;
    private JTextField edadField;
    private JTextField enfermedadField;

    public Registro_pacientes() {
        setTitle("Registrar Paciente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel historialLabel = new JLabel("Historial Clínico:");
        historialLabel.setBounds(10, 10, 120, 25);
        panel.add(historialLabel);

        historialField = new JTextField(20);
        historialField.setBounds(150, 10, 160, 25);
        panel.add(historialField);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 40, 120, 25);
        panel.add(nombreLabel);

        nombreField = new JTextField(20);
        nombreField.setBounds(150, 40, 160, 25);
        panel.add(nombreField);

        JLabel apellidoLabel = new JLabel("Apellido:");
        apellidoLabel.setBounds(10, 70, 120, 25);
        panel.add(apellidoLabel);

        apellidoField = new JTextField(20);
        apellidoField.setBounds(150, 70, 160, 25);
        panel.add(apellidoField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 100, 120, 25);
        panel.add(telefonoLabel);

        telefonoField = new JTextField(20);
        telefonoField.setBounds(150, 100, 160, 25);
        panel.add(telefonoField);

        JLabel edadLabel = new JLabel("Edad:");
        edadLabel.setBounds(10, 130, 120, 25);
        panel.add(edadLabel);

        edadField = new JTextField(20);
        edadField.setBounds(150, 130, 160, 25);
        panel.add(edadField);

        JLabel enfermedadLabel = new JLabel("Descripción de Enfermedad:");
        enfermedadLabel.setBounds(10, 160, 180, 25);
        panel.add(enfermedadLabel);

        enfermedadField = new JTextField(20);
        enfermedadField.setBounds(190, 160, 160, 25);
        panel.add(enfermedadField);

        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(10, 200, 150, 25);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registrarPaciente(
                            historialField.getText(),
                            nombreField.getText(),
                            apellidoField.getText(),
                            telefonoField.getText(),
                            edadField.getText(),
                            enfermedadField.getText()
                    );
                    JOptionPane.showMessageDialog(null, "Paciente registrado exitosamente");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton searchButton = new JButton("Buscar Paciente");
        searchButton.setBounds(200, 200, 150, 25);
        panel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Busqueda_pacientes();
            }
        });
    }

    private void registrarPaciente(String historial, String nombre, String apellido, String telefono, String edad, String enfermedad) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO PACIENTE (n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, historial);
        statement.setString(2, nombre);
        statement.setString(3, apellido);
        statement.setString(4, telefono);
        statement.setString(5, edad);
        statement.setString(6, enfermedad);
        statement.executeUpdate();

        statement.close();
        conn.close();
    }
}
