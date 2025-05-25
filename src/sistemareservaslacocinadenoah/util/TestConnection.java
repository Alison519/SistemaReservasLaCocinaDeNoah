package sistemareservaslacocinadenoah.util;


import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author aliso
 */
public class TestConnection {
    
    public static void main(String[] args) {
        try{
            System.out.println("Intentado conectar");
            
            java.sql.Connection conn = ConnectionBD.getConnection();
            
            System.out.println("Conexion exitosa");
            System.out.println("Base de datos");
            
            consultarMesas(conn);
            
            
            ConnectionBD.cerrarConexion(conn);
            System.out.println("Conexion Cerrada");
        }catch(SQLException ex){
            System.err.println("Error al conectar");
            System.err.println("Mensaje: " + ex.getMessage());
            
            ex.printStackTrace();
        }
    }
    
    public static void consultarMesas(Connection conn) throws SQLException{
        
        String sql = "SELECT * FROM mesas";
        
        try(Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
                ){
            System.out.printf("Id", "numero", "capacidad");
            
            while(rs.next()){
                int idMesa = rs.getInt("id_mesa");
                int numMesa = rs.getInt("numero_mesa");
                int capacidad = rs.getInt("capacidad");
                String estado = rs.getString("estado");
                
               System.out.printf(" " + idMesa + " " + numMesa + " " + capacidad + " " + estado);
            }
           
        }
        
    }
}
