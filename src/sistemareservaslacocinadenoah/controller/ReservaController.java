package sistemareservaslacocinadenoah.controller;

/**
 *
 * @author aliso
 */
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import sistemareservaslacocinadenoah.dao.ReservasDAO;
import sistemareservaslacocinadenoah.model.Reservas;

public class ReservaController {

    private ReservasDAO reservaDAO;

    public ReservaController() {
        this.reservaDAO = new ReservasDAO();
    }

    public boolean guardarReserva(Reservas reserva) {
        try {
            if (reserva.getIdReserva() == 0) {
                reservaDAO.insertar(reserva);
            } else {
                reservaDAO.actualizar(reserva);
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    public Reservas buscarReserva(int id) {
        try {
            return reservaDAO.buscarId(id);
        } catch (SQLException e) {
            System.out.println("Error al buscar" + e.getMessage());
            return null;
        }
    }

    public List<Reservas> obtenerTodasReservas() {
        try {
            return reservaDAO.buscarTodas();
        } catch (SQLException e) {
            System.out.println("Error al buscar" + e.getMessage());
            return null;
        }
    }

    public List<Reservas> obtenerReservasPorFecha(LocalDate fecha) {
        try {
            return reservaDAO.buscarPorFecha(fecha);
        } catch (SQLException e) {
            System.out.println("Error al buscar por fecha: " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarReserva(int id) {
        try {
            reservaDAO.eliminar(id);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar" + e.getMessage());
            return false;
        }
    }

    public boolean validarReserva(Reservas reserva) {
        if (reserva.getCliente() == null) {
            return false;
        }
        if (reserva.getMesa() == null) {
            return false;
        }
        if (reserva.getFechaReserva() == null) {
            return false;
        }
        if (reserva.getHoraInicio() == null || reserva.getHoraFin() == null) {
            return false;
        }
        return reserva.getNumPersonas() > 0;
    }

}
