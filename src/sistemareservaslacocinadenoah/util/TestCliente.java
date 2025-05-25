
package sistemareservaslacocinadenoah.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author aliso
 */
public class TestCliente {

    String driver = "com.mysql.jdbc.Driver";
    String URL = "jdbc:mysql://localhost:3306/la_cocina_de_noah_db?serverTimezone=UTC";
    String User = "root";
    String Pass = "1234";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public TestCliente() throws ClassNotFoundException, SQLException {
        try{
        Class.forName(driver);
        conn = DriverManager.getConnection(URL, User, Pass);
        ps = conn.prepareStatement("INSERT INTO clientes VALUES(?, ?, ?)");
        ps.setInt(1, 9);
        ps.setString(1, driver);
        ps.setString(2, driver);
        ps.setString(3, driver);
        ps.executeUpdate();
        System.out.println("Conexion exitosa");
        while (rs.next()){
            int id_Cliente = rs.getInt("id_Cliente");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            System.out.println("");
        }
        }catch (ClassNotFoundException ex)
                {
                System.out.println("Error");
                }catch  (SQLException ex){
                    System.out.println("Nose ha podido realizar");
                }finally{
            try{
                if(conn != null){
                    conn.close();
                }
                
            }catch (SQLException ex){
                    
            }
        }
        
        
        
    }
}
