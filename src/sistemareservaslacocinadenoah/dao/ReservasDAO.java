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
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import sistemareservaslacocinadenoah.model.Cliente;
import sistemareservaslacocinadenoah.model.Mesa;
import sistemareservaslacocinadenoah.model.Reservas;
import sistemareservaslacocinadenoah.util.ConnectionBD;

public class ReservasDAO {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private MesaDAO mesaDAO = new MesaDAO();

    public int insertar(Reservas reserva) throws SQLException {
        String sql = "INSERT INTO reservas (id_cliente, id_mesa, fecha_reserva, hora_inicio, hora_fin, num_personas, estado, observaciones) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reserva.getCliente().getIdCliente());
            stmt.setInt(2, reserva.getMesa().getIdMesa());
            stmt.setObject(3, reserva.getFechaReserva());
            stmt.setObject(4, reserva.getHoraInicio());
            stmt.setObject(5, reserva.getHoraFin());
            stmt.setInt(6, reserva.getNumPersonas());
            stmt.setString(7, reserva.getEstado());
            stmt.setString(8, reserva.getObservaciones());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("No hay datos");
            }

            try (ResultSet generateKeys = stmt.getGeneratedKeys()) {
                if (generateKeys.next()) {
                    return generateKeys.getInt(1);
                } else {
                    throw new SQLException("Error con los Id");
                }
            }

        }
    }

    public Reservas buscarId(int id) throws SQLException {
        String sql = "SELECT * FROM reservas WHERE id_reserva = ?";

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Reservas reserva = new Reservas();
                    reserva.setIdReserva(rs.getInt("id_reserva"));

                    Cliente cliente = clienteDAO.buscarId(rs.getInt("id_cliente"));
                    reserva.setCliente(cliente);

                    Mesa mesa = mesaDAO.buscarId(rs.getInt("id_mesa"));
                    reserva.setMesa(mesa);

                    reserva.setFechaReserva(rs.getObject("fecha_reserva", LocalDate.class));
                    reserva.setHoraInicio(rs.getObject("hora_inicio", LocalTime.class));
                    reserva.setHoraFin(rs.getObject("hora_fin", LocalTime.class));
                    reserva.setNumPersonas(rs.getInt("num_personas"));
                    reserva.setEstado(rs.getString("estado"));
                    reserva.setObservaciones(rs.getString("observaciones"));
                    reserva.setFechaCreacion(rs.getObject("fecha_creacion", LocalDateTime.class));

                    return reserva;
                }

                return null;
            }
        }
    }

    public List<Reservas> buscarTodas() throws SQLException {
        String sql = "SELECT * FROM reservas";
        List<Reservas> reservas = new ArrayList<>();

        try (Connection conn = ConnectionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reservas reserva = new Reservas();
                reserva.setIdReserva(rs.getInt("id_reserva"));

                Cliente cliente = clienteDAO.buscarId(rs.getInt("id_cliente"));
                reserva.setCliente(cliente);

                Mesa mesa = mesaDAO.buscarId(rs.getInt("id_mesa"));
                reserva.setMesa(mesa);

                reserva.setFechaReserva(rs.getObject("fecha_reserva", LocalDate.class));
                reserva.setHoraInicio(rs.getObject("hora_inicio", LocalTime.class));
                reserva.setHoraFin(rs.getObject("hora_fin", LocalTime.class));
                reserva.setNumPersonas(rs.getInt("num_personas"));
                reserva.setEstado(rs.getString("estado"));
                reserva.setObservaciones(rs.getString("observaciones"));
                reserva.setFechaCreacion(rs.getObject("fecha_creacion", LocalDateTime.class));

                reservas.add(reserva);
            }
        }

        return reservas;
    }

    public List<Reservas> buscarPorFecha(LocalDate fecha) throws SQLException {
        String sql = "SELECT * FROM reservas WHERE fecha_reserva = ?";
        List<Reservas> reservas = new ArrayList<>();

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, fecha);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservas reserva = new Reservas();
                    reserva.setIdReserva(rs.getInt("id_reserva"));

                    Cliente cliente = clienteDAO.buscarId(rs.getInt("id_cliente"));
                    reserva.setCliente(cliente);

                    Mesa mesa = mesaDAO.buscarId(rs.getInt("id_mesa"));
                    reserva.setMesa(mesa);

                    reserva.setFechaReserva(rs.getObject("fecha_reserva", LocalDate.class));
                    reserva.setHoraInicio(rs.getObject("hora_inicio", LocalTime.class));
                    reserva.setHoraFin(rs.getObject("hora_fin", LocalTime.class));
                    reserva.setNumPersonas(rs.getInt("num_personas"));
                    reserva.setEstado(rs.getString("estado"));
                    reserva.setObservaciones(rs.getString("observaciones"));
                    reserva.setFechaCreacion(rs.getObject("fecha_creacion", LocalDateTime.class));

                    reservas.add(reserva);
                }
            }
        }
        return reservas;
    }

    public void actualizar(Reservas reserva) throws SQLException {
        String sql = "UPDATE reservas SET id_cliente = ?, id_mesa = ?, fecha_reserva = ?, hora_inicio = ?, hora_fin = ?, num_personas = ?, estado = ?, observaciones = ? WHERE id_reserva = ?";

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getCliente().getIdCliente());
            stmt.setInt(2, reserva.getMesa().getIdMesa());
            stmt.setObject(3, reserva.getFechaReserva());
            stmt.setObject(4, reserva.getHoraInicio());
            stmt.setObject(5, reserva.getHoraFin());
            stmt.setInt(6, reserva.getNumPersonas());
            stmt.setString(7, reserva.getEstado());
            stmt.setString(8, reserva.getObservaciones());
            stmt.setInt(9, reserva.getIdReserva());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id_reserva = ?";
        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
