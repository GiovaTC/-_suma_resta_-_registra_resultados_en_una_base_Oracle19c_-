import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleDAO {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USER = "system";
    private static final String PASS = "Tapiero123" ;

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
    public List<Operacion> obtenerHistorial () throws Exception {
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
