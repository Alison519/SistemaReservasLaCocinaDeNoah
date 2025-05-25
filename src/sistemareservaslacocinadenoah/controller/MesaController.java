package sistemareservaslacocinadenoah.controller;

/**
 *
 * @author aliso
 */
import java.sql.SQLException;
import java.util.List;
import sistemareservaslacocinadenoah.dao.MesaDAO;
import sistemareservaslacocinadenoah.model.Mesa;

public class MesaController {

    private MesaDAO mesaDAO;

    public MesaController() {
        this.mesaDAO = new MesaDAO();
    }

    public boolean guardarMesa(Mesa mesa) {
        try {
            if (mesa.getIdMesa() == 0) {
                mesaDAO.insertar(mesa);
            } else {
                mesaDAO.actualizar(mesa);
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar" + e.getMessage());
            return false;
        }
    }

    public Mesa buscarMesa(int id) {
        try {
            return mesaDAO.buscarId(id);
        } catch (SQLException e) {
            System.out.println("Error al buscar mesa: " + e.getMessage());
            return null;
        }
    }

    public List<Mesa> buscarTodasLasMesas() {
        try {
            return mesaDAO.buscarTodos();
        } catch (SQLException e) {
            System.out.println("Error al obtener mesa: " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarMesa(int id) {
        try {
            mesaDAO.eliminar(id);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al elminiar: " + e.getMessage());
            return false;
        }
    }
    
    public boolean cambiarEstadoMesa(int idMesa, String estado){
        try{
            mesaDAO.actualizarEstado(idMesa, estado);
            return true;
        }catch(SQLException e){
            System.out.println("Error al cambiar estado" + e.getMessage());
            return false;
        }
    }
    
    public boolean validarMesa(Mesa mesa){
        if(mesa.getNumeroMesa() <= 0){
            return false;
        }
        if(mesa.getCapacidad() <=0){
            return false;
        }
        if(mesa.getEstado() == null || mesa.getEstado().trim().isEmpty()){
            return false;
        }
        return true;
    }
}
