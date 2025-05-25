package sistemareservaslacocinadenoah.dao;

/**
 *
 * @author aliso
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import sistemareservaslacocinadenoah.model.Mesa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sistemareservaslacocinadenoah.util.ConnectionBD;

public class MesaDAO {

    public int insertar(Mesa mesa) throws SQLException {
        String sql = "INSERT INTO mesas (numero_mesa, capacidad, estado) VALUES (?,?,?)";
        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, mesa.getNumeroMesa());
            stmt.setInt(2, mesa.getCapacidad());
            stmt.setString(3, mesa.getEstado());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("No hay nada");
            }
            try (ResultSet generateKeys = stmt.getGeneratedKeys()) {
                if (generateKeys.next()) {
                    return generateKeys.getInt(1);
                } else {
                    throw new SQLException("Error");
                }
            }
        }
    }

    public Mesa buscarId(int id) throws SQLException {
        String sql = "SELECT * FROM mesas WHERE id_mesa = ?";
        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Mesa mesa = new Mesa();
                    mesa.setIdMesa(rs.getInt("id_mesa"));
                    mesa.setNumeroMesa(rs.getInt("numero_mesa"));
                    mesa.setCapacidad(rs.getInt("capacidad"));
                    mesa.setEstado(rs.getString("estado"));
                    return mesa;
                }
                return null;
            }

        }

    }

    public List<Mesa> buscarTodos() throws SQLException {

        String sql = "SELECT * FROM mesas";
        List<Mesa> mesas = new ArrayList<>();

        try (
                Connection conn = ConnectionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("id_mesa"));
                mesa.setNumeroMesa(rs.getInt("numero_mesa"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setEstado(rs.getString("estado"));
                mesas.add(mesa);
            }
        }

        return mesas;
    }

    public void actualizar(Mesa mesa) throws SQLException {
        String sql = "UPDATE mesas SET numero_mesa = ?, capacidad = ?, estado= ? WHERE id_mesa = ?";

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mesa.getNumeroMesa());
            stmt.setInt(2, mesa.getCapacidad());
            stmt.setString(3, mesa.getEstado());
            stmt.setInt(4, mesa.getIdMesa());

            stmt.executeUpdate();
        }
    }

    public void actualizarEstado(int idMesa, String estado) throws SQLException {
        String sql = "UPDATE mesas SET esto = ?, WHERE id_mesa = ?";

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado);
            stmt.setInt(2, idMesa);

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM mesas WHERE id_cliente = ?";
        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
