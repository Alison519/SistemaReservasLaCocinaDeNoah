package sistemareservaslacocinadenoah.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aliso
 */
public class ConnectionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/la_cocina_de_noah_db?serverTimezone=UTC";

    private static final String User = "root";

    private static final String Pass = "1234";

    public static Connection getConnection() throws SQLException {
        try {
            //Invoca la libreria
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, User, Pass);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver de MySQL", ex);
        }
    }

    ;
    
    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar" + ex.getMessage());
        }
    }
}