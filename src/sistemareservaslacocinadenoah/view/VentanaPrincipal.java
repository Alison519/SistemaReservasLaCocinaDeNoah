package sistemareservaslacocinadenoah.view;

/**
 *
 * @author aliso
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuClientes, menuMesas, menuReservas, menuReportes;
    private JMenuItem itemGestionClientes, itemGestionMesas, itemNuevaReserva, itemVerReservas, itemDisponibilidad, itemHistorial;

    public VentanaPrincipal() {
        initComponents();
        configurarVentana();
        crearMenu();
    }

    private void initComponents() {
        setTitle("Sistema de Reservas - La Cocina de Noah");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void configurarVentana() {

        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(220, 220, 220), 0, getHeight(), new Color(255, 255, 255));

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setOpaque(false);
        panelBienvenida.setLayout(new BoxLayout(panelBienvenida, BoxLayout.Y_AXIS));

        JLabel lbTitulo = new JLabel("La Cocina de Noah");
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lbTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbTitulo.setForeground(new Color(160, 82, 45));

        JLabel lblTitulo = new JLabel("Sistema de Reservas");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(160, 82, 45));

        panelBienvenida.add(Box.createVerticalGlue());
        panelBienvenida.add(lbTitulo);
        panelBienvenida.add(Box.createVerticalStrut(10));
        panelBienvenida.add(lblTitulo);
        panelBienvenida.add(Box.createHorizontalGlue());

        panelPrincipal.add(panelBienvenida, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void crearMenu() {
        menuBar = new JMenuBar();

        menuClientes = new JMenu("Clientes");
        itemGestionClientes = new JMenuItem("Gestión de Clientes");
        itemGestionClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionClientes();
            }
        });
        menuClientes.add(itemGestionClientes);

        menuMesas = new JMenu("Mesas");
        itemGestionMesas = new JMenuItem("Gestión de Mesas");
        itemGestionMesas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionMesas();
            }
        });
        menuMesas.add(itemGestionMesas);

        menuReservas = new JMenu("Reservas");

        itemNuevaReserva = new JMenuItem("Nueva Reserva");
        itemNuevaReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirNuevaReserva();
            }
        });

        itemVerReservas = new JMenuItem("Ver Reserva");
        itemVerReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVerReservas();
            }
        });

        itemDisponibilidad = new JMenuItem("Disponibilidad");
        itemDisponibilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDisponibilidad();
            }
        });

        menuReservas.add(itemNuevaReserva);
        menuReservas.add(itemVerReservas);
        menuReservas.add(itemDisponibilidad);

        menuReportes = new JMenu("Reportes");
        itemHistorial = new JMenuItem("Historial de Reservas");
        itemHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirHistorial();
            }
        });
        menuReportes.add(itemHistorial);

        menuBar.add(menuClientes);
        menuBar.add(menuMesas);
        menuBar.add(menuReservas);
        menuBar.add(menuReportes);

        setJMenuBar(menuBar);
    }

    private void abrirGestionClientes() {
        JOptionPane.showMessageDialog(this, "Proximante Clientes");
    }

    private void abrirGestionMesas() {
        JOptionPane.showMessageDialog(this, "Proximante Mesas");
    }

    private void abrirNuevaReserva() {
        JOptionPane.showMessageDialog(this, "Proximante nueva Reserva");
    }

    private void abrirVerReservas() {
        JOptionPane.showMessageDialog(this, "Proximante Ver Reserva");
    }

    private void abrirDisponibilidad() {
        JOptionPane.showMessageDialog(this, "Proximante Disponible");
    }

    private void abrirHistorial() {
        JOptionPane.showMessageDialog(this, "Proximante Historial");
    }

}
