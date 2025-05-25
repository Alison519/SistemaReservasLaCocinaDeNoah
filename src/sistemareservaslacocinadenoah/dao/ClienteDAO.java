package sistemareservaslacocinadenoah.dao;

/**
 *
 * @author aliso
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemareservaslacocinadenoah.model.Cliente;
import sistemareservaslacocinadenoah.util.ConnectionBD;

public class ClienteDAO {

    public int insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, apellido, telefono, email) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("No hay nada no?");
            }

            try (ResultSet generateKeys = stmt.getGeneratedKeys()) {
                if (generateKeys.next()) {
                    return generateKeys.getInt(1);
                } else {
                    throw new SQLException("El usuario no tiene ID, error en la creacion");
                }
            }
        }
    }

    public Cliente buscarId(int id) throws SQLException {

        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

        try (
                Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                    return cliente;
                }
                return null;
            }
        }
    }

    public List<Cliente> buscarTodos() throws SQLException {

        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();

        try (
                Connection conn = ConnectionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                cliente.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                clientes.add(cliente);
            }
        }

        return clientes;
    }

    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, telefono = ?, email = ? WHERE id_cliente = ?";

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, cliente.getIdCliente());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
