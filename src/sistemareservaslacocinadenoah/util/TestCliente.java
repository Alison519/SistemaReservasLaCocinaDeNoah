
package sistemareservaslacocinadenoah.util;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;



/**
 *
 * @author aliso
 */
public class TestCliente {

    public static void main(String[] args) {
        try{
            System.out.println("Intentado conectar");
            
            java.sql.Connection conn = ConnectionBD.getConnection();
            
            System.out.println("Conexion exitosa");
            System.out.println("Base de datos");
            
            consultarCliente(conn);
            
            
            ConnectionBD.cerrarConexion(conn);
            System.out.println("Conexion Cerrada");
        }catch(SQLException ex){
            System.err.println("Error al conectar");
            System.err.println("Mensaje: " + ex.getMessage());
            
            ex.printStackTrace();
        }
    }
   public static void insertarCliente (Connection conn) throws SQLException{
       String sql = "INSERT INTO clientes (nombre, apellido, telefono, email) VALUES (?,?,?,?)";
       try (
           
               PreparedStatement stmt = conn.prepareStatement(sql)){
           
           stmt.setString(1, "alison");
            stmt.setString(2, "castaneda");
            stmt.setString(3, "555-3112");
            stmt.setString(4, "alisoncastaneda@gmail.com");
            stmt.executeUpdate();
           System.out.println("se inserto correctamente");
           
       }
   }
    public static void consultarCliente(Connection conn) throws SQLException{
        
        String sql = "SELECT * FROM clientes";
        
        try(Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
                ){
            System.out.printf("Id", "nombre", "apellido", "telefono", "email", "fecha_registro");
            
            while(rs.next()){
                int idcliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                LocalDate fecha_registro = (LocalDate) rs.getObject("fecha_registro");
                
               System.out.printf(" " + idcliente + " " + nombre + " " + apellido + " " + telefono + " " + email + " " + fecha_registro);
            }
           
        }
        
    }
}

    
        
        
        
    

