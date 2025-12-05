import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalculatorUI extends JFrame {

    private JTextField txtNum1;
    private JTextField txtNum2;
    private JTextField txtResultado;
    private DefaultTableModel model;

    public CalculatorUI() {
        setTitle("Calculadora Oracle 19c");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Número 1:"));
        txtNum1 = new JTextField();
        panel.add(txtNum1);

        panel.add(new JLabel("Número 2:"));
        txtNum2 = new JTextField();
        panel.add(txtNum2);

        panel.add(new JLabel("Resultado:"));
        txtResultado = new JTextField();
        txtResultado.setEditable(false);
        panel.add(txtResultado);

        JButton btnSum = new JButton("Sumar");
        btnSum.addActionListener(this::sumar);
        panel.add(btnSum);

        JButton btnRest = new JButton("Restar");
        btnRest.addActionListener(this::restar);
        panel.add(btnRest);

        JButton btnGuardar = new JButton("Guardar en Oracle");
        btnGuardar.addActionListener(this::guardar);
        panel.add(btnGuardar);

        add(panel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Op", "Num1", "Num2", "Resultado"}, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        cargarHistorial();
    }

    private void cargarHistorial() {
    }

    private void guardar(ActionEvent actionEvent) {
    }

    private void restar(ActionEvent actionEvent) {

    }

    private void sumar(ActionEvent actionEvent) {
    }
}
