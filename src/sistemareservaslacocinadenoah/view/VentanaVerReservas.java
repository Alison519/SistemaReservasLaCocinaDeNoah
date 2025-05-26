package sistemareservaslacocinadenoah.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import sistemareservaslacocinadenoah.controller.ReservaController;
import sistemareservaslacocinadenoah.model.Reservas;

/**
 *
 * @author aliso
 */
public class VentanaVerReservas extends JFrame {

    private ReservaController reservaController;
    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    private JTextField txtFechaFiltro;
    private JComboBox<String> cmbEstadoFiltro;
    private JButton btnFiltrar, btnTodas, btnCambiarEstado, btnEliminar, btnCerrar;
    private Reservas reservaSeleccionada = null;

    public VentanaVerReservas() {
        reservaController = new ReservaController();
        initComponents();
        cargarTodasReservas();
    }

    private void initComponents() {
        setTitle("Ver Reservas - La Cocina de Noah");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelFiltros = crearPanelFiltros();
        add(panelFiltros, BorderLayout.NORTH);

        JPanel panelTabla = crearPanelTabla();
        add(panelTabla, BorderLayout.CENTER);

        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Filtros"));

        panel.add(new JLabel("Fecha:"));
        txtFechaFiltro = new JTextField(10);
        txtFechaFiltro.setText(LocalDate.now().toString());
        panel.add(txtFechaFiltro);

        panel.add(new JLabel("Estado:"));
        String[] estados = {"Todos", "Confirmada", "Cancelada", "Culminada"};
        cmbEstadoFiltro = new JComboBox<>(estados);
        panel.add(cmbEstadoFiltro);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> filtrarReservas());
        panel.add(btnFiltrar);

        btnTodas = new JButton("Ver Todas");
        btnTodas.addActionListener(e -> cargarTodasReservas());
        panel.add(btnTodas);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Reservas"));

        String[] columnas = {
            "ID", "Cliente", "Mesa", "Fecha", "Hora Inicio", 
            "Hora Fin", "Personas", "Estado", "Observaciones"
        };
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaReservas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarReserva();
            }
        });

        tablaReservas.getColumnModel().getColumn(0).setPreferredWidth(50);  
        tablaReservas.getColumnModel().getColumn(1).setPreferredWidth(150); 
        tablaReservas.getColumnModel().getColumn(2).setPreferredWidth(60);
        tablaReservas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaReservas.getColumnModel().getColumn(4).setPreferredWidth(80); 
        tablaReservas.getColumnModel().getColumn(5).setPreferredWidth(80); 
        tablaReservas.getColumnModel().getColumn(6).setPreferredWidth(70); 
        tablaReservas.getColumnModel().getColumn(7).setPreferredWidth(100);
        tablaReservas.getColumnModel().getColumn(8).setPreferredWidth(200); 

        tablaReservas.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String estado = (String) table.getValueAt(row, 7); 
                    switch (estado) {
                        case "Confirmada":
                            c.setBackground(new Color(144, 238, 144)); 
                            break;
                        case "Cancelada":
                            c.setBackground(new Color(255, 182, 193));
                            break;
                        case "Culminada":
                            c.setBackground(new Color(173, 216, 230));
                            break;
                        default:
                            c.setBackground(Color.WHITE);
                    }
                } else {
                    c.setBackground(table.getSelectionBackground());
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        btnCambiarEstado = new JButton("Cambiar Estado");
        btnCambiarEstado.addActionListener(e -> cambiarEstadoReserva());
        btnCambiarEstado.setEnabled(false);

        btnEliminar = new JButton("Eliminar Reserva");
        btnEliminar.addActionListener(e -> eliminarReserva());
        btnEliminar.setEnabled(false);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnCambiarEstado);
        panel.add(btnEliminar);
        panel.add(btnCerrar);

        return panel;
    }

    private void cargarTodasReservas() {
        modeloTabla.setRowCount(0);
        List<Reservas> reservas = reservaController.obtenerTodasReservas();
        cargarReservasEnTabla(reservas);
    }

    private void filtrarReservas() {
        try {
            String fechaTexto = txtFechaFiltro.getText().trim();
            String estadoSeleccionado = (String) cmbEstadoFiltro.getSelectedItem();

            if (!fechaTexto.isEmpty()) {
                LocalDate fecha = LocalDate.parse(fechaTexto);
                List<Reservas> reservas = reservaController.obtenerReservasPorFecha(fecha);

                if (!"Todos".equals(estadoSeleccionado)) {
                    reservas.removeIf(reserva -> !reserva.getEstado().equals(estadoSeleccionado));
                }
                
                cargarReservasEnTabla(reservas);
            } else {
                cargarTodasReservas();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error en el formato de fecha. Use: YYYY-MM-DD", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarReservasEnTabla(List<Reservas> reservas) {
        modeloTabla.setRowCount(0);
        
        if (reservas != null) {
            for (Reservas reserva : reservas) {
                Object[] fila = {
                    reserva.getIdReserva(),
                    reserva.getCliente().getNombre() + " " + reserva.getCliente().getApellido(),
                    "Mesa " + reserva.getMesa().getNumeroMesa(),
                    reserva.getFechaReserva(),
                    reserva.getHoraInicio(),
                    reserva.getHoraFin(),
                    reserva.getNumPersonas(),
                    reserva.getEstado(),
                    reserva.getObservaciones()
                };
                modeloTabla.addRow(fila);
            }
        }

        setTitle("Ver Reservas - La Cocina de Noah (" + 
                (reservas != null ? reservas.size() : 0) + " reservas)");
    }

    private void seleccionarReserva() {
        int filaSeleccionada = tablaReservas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idReserva = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
            reservaSeleccionada = reservaController.buscarReserva(idReserva);
            
            btnCambiarEstado.setEnabled(true);
            btnEliminar.setEnabled(true);
        } else {
            reservaSeleccionada = null;
            btnCambiarEstado.setEnabled(false);
            btnEliminar.setEnabled(false);
        }
    }

    private void cambiarEstadoReserva() {
        if (reservaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva");
            return;
        }

        String[] estados = {"Confirmada", "Cancelada", "Culminada"};
        String nuevoEstado = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione el nuevo estado para la reserva:",
            "Cambiar Estado",
            JOptionPane.QUESTION_MESSAGE,
            null,
            estados,
            reservaSeleccionada.getEstado()
        );

        if (nuevoEstado != null && !nuevoEstado.equals(reservaSeleccionada.getEstado())) {
            reservaSeleccionada.setEstado(nuevoEstado);
            
            if (reservaController.guardarReserva(reservaSeleccionada)) {
                JOptionPane.showMessageDialog(this, "Estado actualizado exitosamente");
                cargarTodasReservas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar estado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarReserva() {
        if (reservaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar la reserva de " + 
            reservaSeleccionada.getCliente().getNombre() + " " + 
            reservaSeleccionada.getCliente().getApellido() + 
            " para el " + reservaSeleccionada.getFechaReserva() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (reservaController.eliminarReserva(reservaSeleccionada.getIdReserva())) {
                JOptionPane.showMessageDialog(this, "Reserva eliminada exitosamente");
                cargarTodasReservas();
                reservaSeleccionada = null;
                btnCambiarEstado.setEnabled(false);
                btnEliminar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar reserva", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}