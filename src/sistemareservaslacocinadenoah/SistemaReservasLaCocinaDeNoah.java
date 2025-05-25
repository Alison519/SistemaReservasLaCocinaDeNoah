package sistemareservaslacocinadenoah;

/**
 *
 * @author aliso
 */
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import sistemareservaslacocinadenoah.view.VentanaPrincipal;

public class SistemaReservasLaCocinaDeNoah {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se establecio" + e.getMessage());
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });

    }

}
