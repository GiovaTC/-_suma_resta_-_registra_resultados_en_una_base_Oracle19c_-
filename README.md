# -_suma_resta_-_registra_resultados_en_una_base_Oracle19c_- :. 
# 📘 Calculadora Swing + Oracle 19c :

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/a98e085d-cedf-4935-9534-8038e0914f5b" />  

Aplicación completa en **Java (IntelliJ)** con interfaz gráfica
**Swing**, que realiza **suma y resta** y además **registra las
operaciones en Oracle 19c** usando JDBC .

Incluye:

-   Interfaz gráfica (Swing)\
-   Campos Número 1, Número 2\
-   Botones Sumar, Restar, Guardar\
-   Tabla historial desde Oracle\
-   DAO JDBC para Oracle 19c\
-   Script SQL\
-   Código completo

------------------------------------------------------------------------

## 🚀 1. Main.java

``` java
public class Main {
    public static void main(String[] args) {
        CalculatorUI ui = new CalculatorUI();
        ui.setVisible(true);
    }
}
```

------------------------------------------------------------------------

## 🖥️ 2. CalculatorUI.java

``` java
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

    private void sumar(ActionEvent e) {
        double n1 = Double.parseDouble(txtNum1.getText());
        double n2 = Double.parseDouble(txtNum2.getText());
        txtResultado.setText(String.valueOf(n1 + n2));
    }

    private void restar(ActionEvent e) {
        double n1 = Double.parseDouble(txtNum1.getText());
        double n2 = Double.parseDouble(txtNum2.getText());
        txtResultado.setText(String.valueOf(n1 - n2));
    }

    private void guardar(ActionEvent e) {
        try {
            String op = txtResultado.getText().contains("-") ? "RESTA" : "SUMA";
            double n1 = Double.parseDouble(txtNum1.getText());
            double n2 = Double.parseDouble(txtNum2.getText());
            double res = Double.parseDouble(txtResultado.getText());

            OracleDAO dao = new OracleDAO();
            dao.insertarOperacion(op, n1, n2, res);

            JOptionPane.showMessageDialog(this, "Guardado en Oracle!");

            cargarHistorial();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cargarHistorial() {
        try {
            OracleDAO dao = new OracleDAO();
            model.setRowCount(0);
            dao.obtenerHistorial().forEach(op ->
                    model.addRow(new Object[]{
                            op.getId(), op.getOperacion(),
                            op.getNum1(), op.getNum2(), op.getResultado()
                    })
            );
        } catch (Exception ex) {
            System.out.println("No se pudo cargar historial: " + ex.getMessage());
        }
    }
}
```

------------------------------------------------------------------------

## 📌 3. Operacion.java

``` java
public class Operacion {
    private long id;
    private String operacion;
    private double num1;
    private double num2;
    private double resultado;

    public Operacion(long id, String operacion, double num1, double num2, double resultado) {
        this.id = id;
        this.operacion = operacion;
        this.num1 = num1;
        this.num2 = num2;
        this.resultado = resultado;
    }

    public long getId() { return id; }
    public String getOperacion() { return operacion; }
    public double getNum1() { return num1; }
    public double getNum2() { return num2; }
    public double getResultado() { return resultado; }
}
```

------------------------------------------------------------------------

## 🗄️ 4. OracleDAO.java

``` java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleDAO {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private static final String USER = "system";
    private static final String PASS = "oracle";

    private Connection conectar() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void insertarOperacion(String op, double n1, double n2, double res) throws Exception {
        String sql = "INSERT INTO CALC_OPERACIONES (OPERACION, NUM1, NUM2, RESULTADO) VALUES (?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, op);
            ps.setDouble(2, n1);
            ps.setDouble(3, n2);
            ps.setDouble(4, res);
            ps.executeUpdate();
        }
    }

    public List<Operacion> obtenerHistorial() throws Exception {
        String sql = "SELECT ID, OPERACION, NUM1, NUM2, RESULTADO FROM CALC_OPERACIONES ORDER BY ID DESC";
        List<Operacion> lista = new ArrayList<>();

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Operacion(
                        rs.getLong("ID"),
                        rs.getString("OPERACION"),
                        rs.getDouble("NUM1"),
                        rs.getDouble("NUM2"),
                        rs.getDouble("RESULTADO")
                ));
            }
        }
        return lista;
    }
}
```

------------------------------------------------------------------------

## 🛢️ 5. Script SQL

``` sql
CREATE TABLE CALC_OPERACIONES (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    OPERACION VARCHAR2(10),
    NUM1 NUMBER,
    NUM2 NUMBER,
    RESULTADO NUMBER,
    CREATED_AT DATE DEFAULT SYSDATE
);
```

## © 2025 Giovanny Alejandro Tapiero Cataño & chatGpt  
Este proyecto está protegido bajo las leyes internacionales de derechos de autor.  
Queda prohibida la reproducción, distribución o modificación total o parcial del contenido  
sin la autorización expresa del autor.

Este software se proporciona "tal cual", sin garantías de ningún tipo, expresas o implícitas.  
El autor no se hace responsable de daños derivados del uso de este proyecto :. .

------------------------------------------------------------------------
