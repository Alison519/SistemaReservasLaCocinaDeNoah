package sistemareservaslacocinadenoah;


/**
 *
 * @author aliso
 */
public class SistemaReservasLaCocinaDeNoah {

    private void mostrarMensaje() {
        System.out.print( "mesa" + numeroMesa + "(cap: " + capacidad + ")");
    }
    int numeroMesa = 0;
    int capacidad = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SistemaReservasLaCocinaDeNoah manzana = new SistemaReservasLaCocinaDeNoah();
        manzana.mostrarMensaje();
        
                
        
    }
    
}
