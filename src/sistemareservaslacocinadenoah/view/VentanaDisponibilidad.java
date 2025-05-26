package sistemareservaslacocinadenoah.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import sistemareservaslacocinadenoah.controller.MesaController;
import sistemareservaslacocinadenoah.controller.ReservaController;
import sistemareservaslacocinadenoah.model.Mesa;
import sistemareservaslacocinadenoah.model.Reservas;

/**
 *
 * @author aliso
 */
public class VentanaDisponibilidad extends JFrame {

    private MesaController mesaController;
    private ReservaController reservaController;
    
    private JTextField txtFecha;
    private JButton btnConsultar, btnHoy, btnCerrar;
    private JPanel panelMesas, panelReservas;
    private JTable tablaReservasDia;
    private DefaultTableModel modeloTablaReservas;
    
    public VentanaDisponibilidad() {
        mesaController = new MesaController();
        reservaController = new ReservaController();
        initComponents();
        cargarDisponibilidadHoy();
    }

    private void initComponents() {
        setTitle("Disponibilidad de Mesas - La Cocina de Noah");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelSuperior = crearPanelFecha();
        add(panelSuperior, BorderLayout.NORTH);

 
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JPanel panelIzquierdo = crearPanelEstadoMesas();
        splitPane.setLeftComponent(panelIzquierdo);

        JPanel panelDerecho = crearPanelReservasDia();
        splitPane.setRightComponent(panelDerecho);
        
        splitPane.setDividerLocation(450);
        add(splitPane, BorderLayout.CENTER);

        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelFecha() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Consultar Disponibilidad"));

        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtFecha = new JTextField(12);
        txtFecha.setText(LocalDate.now().toString());
        panel.add(txtFecha);

        btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(e -> consultarDisponibilidad());
        panel.add(btnConsultar);

        btnHoy = new JButton("Hoy");
        btnHoy.addActionListener(e -> {
            txtFecha.setText(LocalDate.now().toString());
            consultarDisponibilidad();
        });
        panel.add(btnHoy);

        return panel;
    }

    private JPanel crearPanelEstadoMesas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Estado de las Mesas"));

        panelMesas = new JPanel(new GridLayout(0, 3, 10, 10));
        panelMesas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollMesas = new JScrollPane(panelMesas);
        panel.add(scrollMesas, BorderLayout.CENTER);

        JPanel panelLeyenda = new JPanel(new FlowLayout());
        panelLeyenda.add(new JLabel("Leyenda:"));
        
        JLabel lblDisponible = new JLabel("■ Disponible");
        lblDisponible.setForeground(new Color(34, 139, 34));
        panelLeyenda.add(lblDisponible);
        
        JLabel lblReservada = new JLabel("■ Reservada");
        lblReservada.setForeground(new Color(255, 140, 0));
        panelLeyenda.add(lblReservada);
        
        JLabel lblOcupada = new JLabel("■ Ocupada");
        lblOcupada.setForeground(new Color(220, 20, 60));
        panelLeyenda.add(lblOcupada);

        panel.add(panelLeyenda, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelReservasDia() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Reservas del Día"));

        String[] columnas = {"Hora", "Mesa", "Cliente", "Personas", "Estado"};
        modeloTablaReservas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaReservasDia = new JTable(modeloTablaReservas);
        tablaReservasDia.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaReservasDia.getColumnModel().getColumn(1).setPreferredWidth(60);
        tablaReservasDia.getColumnModel().getColumn(2).setPreferredWidth(120); 
        tablaReservasDia.getColumnModel().getColumn(3).setPreferredWidth(70);
        tablaReservasDia.getColumnModel().getColumn(4).setPreferredWidth(90); 

        JScrollPane scrollReservas = new JScrollPane(tablaReservasDia);
        panel.add(scrollReservas, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnCerrar);

        return panel;
    }

    private void cargarDisponibilidadHoy() {
        consultarDisponibilidad();
    }

    private void consultarDisponibilidad() {
        try {
            LocalDate fechaConsulta = LocalDate.parse(txtFecha.getText().trim());
            
            setTitle("Disponibilidad de Mesas - " + fechaConsulta + " - La Cocina de Noah");
            
            cargarEstadoMesas(fechaConsulta);

            cargarReservasDia(fechaConsulta);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error en el formato de fecha. Use: YYYY-MM-DD", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEstadoMesas(LocalDate fecha) {
        panelMesas.removeAll();

        List<Mesa> mesas = mesaController.buscarTodasLasMesas();

        List<Reservas> reservasDelDia = reservaController.obtenerReservasPorFecha(fecha);
        
        if (mesas != null) {
            for (Mesa mesa : mesas) {
                JPanel panelMesa = crearPanelMesa(mesa, reservasDelDia);
                panelMesas.add(panelMesa);
            }
        }
        
        panelMesas.revalidate();
        panelMesas.repaint();
    }

    private JPanel crearPanelMesa(Mesa mesa, List<Reservas> reservasDelDia) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.setPreferredSize(new Dimension(120, 80));

        String estadoMesa = determinarEstadoMesa(mesa, reservasDelDia);

        Color colorFondo;
        switch (estadoMesa) {
            case "Disponible":
                colorFondo = new Color(144, 238, 144);
                break;
            case "Reservada":
                colorFondo = new Color(255, 218, 185);
                break;
            case "Ocupada":
                colorFondo = new Color(255, 182, 193);
                break;
            default:
                colorFondo = new Color(211, 211, 211); 
        }
        
        panel.setBackground(colorFondo);

        JLabel lblNumero = new JLabel("Mesa " + mesa.getNumeroMesa(), JLabel.CENTER);
        lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblCapacidad = new JLabel("Cap: " + mesa.getCapacidad(), JLabel.CENTER);
        lblCapacidad.setFont(new Font("Arial", Font.PLAIN, 10));
        
        JLabel lblEstado = new JLabel(estadoMesa, JLabel.CENTER);
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 10));
        lblEstado.setForeground(Color.DARK_GRAY);

        panel.add(lblNumero, BorderLayout.NORTH);
        panel.add(lblCapacidad, BorderLayout.CENTER);
        panel.add(lblEstado, BorderLayout.SOUTH);

        if (!reservasDelDia.isEmpty()) {
            StringBuilder tooltip = new StringBuilder("<html>Mesa " + mesa.getNumeroMesa() + "<br>");
            for (Reservas reserva : reservasDelDia) {
                if (reserva.getMesa().getIdMesa() == mesa.getIdMesa()) {
                    tooltip.append(reserva.getHoraInicio()).append("-").append(reserva.getHoraFin())
                           .append(": ").append(reserva.getCliente().getNombre()).append("<br>");
                }
            }
            tooltip.append("</html>");
            panel.setToolTipText(tooltip.toString());
        }

        return panel;
    }

    private String determinarEstadoMesa(Mesa mesa, List<Reservas> reservasDelDia) {
        if ("Mantenimiento".equals(mesa.getEstado()) || "En espera".equals(mesa.getEstado())) {
            return mesa.getEstado();
        }

        LocalTime ahora = LocalTime.now();
        for (Reservas reserva : reservasDelDia) {
            if (reserva.getMesa().getIdMesa() == mesa.getIdMesa() && 
                "Confirmada".equals(reserva.getEstado())) {
                
                if (ahora.isAfter(reserva.getHoraInicio()) && ahora.isBefore(reserva.getHoraFin())) {
                    return "Ocupada";
                } else if (ahora.isBefore(reserva.getHoraInicio())) {
                    return "Reservada";
                }
            }
        }

        return "Disponible";
    }

    private void cargarReservasDia(LocalDate fecha) {
        modeloTablaReservas.setRowCount(0);
        
        List<Reservas> reservas = reservaController.obtenerReservasPorFecha(fecha);
        
        if (reservas != null) {
            reservas.sort((r1, r2) -> r1.getHoraInicio().compareTo(r2.getHoraInicio()));
            
            for (Reservas reserva : reservas) {
                Object[] fila = {
                    reserva.getHoraInicio() + " - " + reserva.getHoraFin(),
                    "Mesa " + reserva.getMesa().getNumeroMesa(),
                    reserva.getCliente().getNombre() + " " + reserva.getCliente().getApellido(),
                    reserva.getNumPersonas(),
                    reserva.getEstado()
                };
                modeloTablaReservas.addRow(fila);
            }
        }
    }
}