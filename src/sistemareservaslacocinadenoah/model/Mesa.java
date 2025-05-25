package sistemareservaslacocinadenoah.model;

/**
 *
 * @author aliso
 */
public class Mesa {
    private int idMesa;
    private int numeroMesa;
    private int capacidad;
    private String estado;
   
    
    public Mesa(){
        
    }
    
    public Mesa(
            int idMesa,
            int numeroMesa,
            int capacidad,
            String estado
    ) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estado = estado;         
    }

    // Getters and Setters
    
    public int getIdMesa (){
        return idMesa;
    }
    
    public void setIdMesa (int idMesa){
        this.idMesa = idMesa;
    }
    
    public int getNumeroMesa(){
        return numeroMesa;
    }
    
    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        
    }
    
    public  int getCapacidad () {
        return capacidad;
    
    }
    
    public void setCapacidad (int capacidad){
        this.capacidad = capacidad;
    }
    
    public String getEstado() {
        return estado;
    }
 
    public void setEstado( String estado){
        this.estado = estado;
    }
    
    @Override
    public String toString (){
        return "mesa" + numeroMesa + "(cap: " + capacidad + ")";
    }
    
}

