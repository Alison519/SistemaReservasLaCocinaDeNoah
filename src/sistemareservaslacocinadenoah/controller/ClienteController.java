package sistemareservaslacocinadenoah.controller;

/**
 *
 * @author aliso
 */
import java.sql.SQLException;
import java.util.List;
import sistemareservaslacocinadenoah.dao.ClienteDAO;
import sistemareservaslacocinadenoah.model.Cliente;

public class ClienteController {

    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public boolean guardarCliente(Cliente cliente) {
        try {
            if (cliente.getIdCliente() == 0) {
                clienteDAO.insertar(cliente);
            } else {
                clienteDAO.actualizar(cliente);
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
            return false;
        }
    }

    public Cliente buscarCliente(int id) {
        try {
            return clienteDAO.buscarId(id);
        } catch (SQLException e) {
            System.out.println("Error al obtener clientes: " + e.getMessage());
            return null;
        }
    }

    public List<Cliente> buscarTodos() {
        try {
            return clienteDAO.buscarTodos();
        } catch (SQLException e) {
            System.out.println("Error al obtener clientes: " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarCliente(int id) {
        try {
            clienteDAO.eliminar(id);
            return true;
        } catch (SQLException alison) {
            System.out.println("Error al elminar cliente: " + alison.getMessage());
            return false;
        }
    }

    public boolean validarCliente(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return false;
        }
        if (cliente.getApellido() == null || cliente.getApellido().trim().isEmpty()) {
            return false;
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            return false;
        }
        return true;
    }

}
