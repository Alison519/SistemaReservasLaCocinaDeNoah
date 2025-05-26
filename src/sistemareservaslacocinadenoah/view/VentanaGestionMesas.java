package sistemareservaslacocinadenoah.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import sistemareservaslacocinadenoah.controller.MesaController;
import sistemareservaslacocinadenoah.model.Mesa;

/**
 *
 * @author aliso
 */
public class VentanaGestionMesas extends JFrame {

    private MesaController mesaController;
    private JTable tablaMesas;
    private DefaultTableModel modeloTabla;
    private JTextField txtNumeroMesa, txtCapacidad;
    private JComboBox<String> cmbEstado;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnCerrar;
    private Mesa mesaSeleccionada = null;

    public VentanaGestionMesas() {
        mesaController = new MesaController();
        initComponents();
        cargarMesas();
    }

    private void initComponents() {
        setTitle("Gestión de Mesas - La Cocina de Noah");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelFormulario = crearPanelFormulario();
        add(panelFormulario, BorderLayout.NORTH);

        JPanel panelTabla = crearPanelTabla();
        add(panelTabla, BorderLayout.CENTER);

        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Mesa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Número de Mesa:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNumeroMesa = new JTextField(10);
        panel.add(txtNumeroMesa, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCapacidad = new JTextField(10);
        panel.add(txtCapacidad, gbc);

        gbc.gridx = 4; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] estados = {"Disponible", "Ocupada", "Reservada", "Mantenimiento"};
        cmbEstado = new JComboBox<>(estados);
        panel.add(cmbEstado, gbc);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Mesas"));

        String[] columnas = {"ID", "Número Mesa", "Capacidad", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMesas = new JTable(modeloTabla);
        tablaMesas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMesas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarMesa();
            }
        });

        tablaMesas.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String estado = (String) table.getValueAt(row, 3);
                    switch (estado) {
                        case "Disponible":
                            c.setBackground(new Color(144, 238, 144));
                            break;
                        case "Ocupada":
                            c.setBackground(new Color(255, 182, 193));
                            break;
                        case "Reservada":
                            c.setBackground(new Color(255, 255, 224));
                            break;
                        case "Mantenimiento":
                            c.setBackground(new Color(211, 211, 211));
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

        JScrollPane scrollPane = new JScrollPane(tablaMesas);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarMesa();
            }
        });

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarMesa();
            }
        });

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarMesa();
            }
        });

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(btnAgregar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnCerrar);

        return panel;
    }

    private void cargarMesas() {
        modeloTabla.setRowCount(0);
        List<Mesa> mesas = mesaController.buscarTodasLasMesas();

        if (mesas != null) {
            for (Mesa mesa : mesas) {
                Object[] fila = {
                    mesa.getIdMesa(),
                    mesa.getNumeroMesa(),
                    mesa.getCapacidad(),
                    mesa.getEstado()
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    private void seleccionarMesa() {
        int filaSeleccionada = tablaMesas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idMesa = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
            mesaSeleccionada = mesaController.buscarMesa(idMesa);

            if (mesaSeleccionada != null) {
                txtNumeroMesa.setText(String.valueOf(mesaSeleccionada.getNumeroMesa()));
                txtCapacidad.setText(String.valueOf(mesaSeleccionada.getCapacidad()));
                cmbEstado.setSelectedItem(mesaSeleccionada.getEstado());
            }
        }
    }

    private void agregarMesa() {
        if (!validarCampos()) return;

        Mesa nuevaMesa = new Mesa();
        nuevaMesa.setNumeroMesa(Integer.parseInt(txtNumeroMesa.getText().trim()));
        nuevaMesa.setCapacidad(Integer.parseInt(txtCapacidad.getText().trim()));
        nuevaMesa.setEstado((String) cmbEstado.getSelectedItem());

        if (mesaController.guardarMesa(nuevaMesa)) {
            JOptionPane.showMessageDialog(this, "Mesa agregada exitosamente");
            cargarMesas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar mesa", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarMesa() {
        if (mesaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una mesa para actualizar");
            return;
        }

        if (!validarCampos()) return;

        mesaSeleccionada.setNumeroMesa(Integer.parseInt(txtNumeroMesa.getText().trim()));
        mesaSeleccionada.setCapacidad(Integer.parseInt(txtCapacidad.getText().trim()));
        mesaSeleccionada.setEstado((String) cmbEstado.getSelectedItem());

        if (mesaController.guardarMesa(mesaSeleccionada)) {
            JOptionPane.showMessageDialog(this, "Mesa actualizada exitosamente");
            cargarMesas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar mesa", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarMesa() {
        if (mesaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una mesa para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar la Mesa " + mesaSeleccionada.getNumeroMesa() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (mesaController.eliminarMesa(mesaSeleccionada.getIdMesa())) {
                JOptionPane.showMessageDialog(this, "Mesa eliminada exitosamente");
                cargarMesas();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar mesa", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtNumeroMesa.setText("");
        txtCapacidad.setText("");
        cmbEstado.setSelectedIndex(0);
        mesaSeleccionada = null;
        tablaMesas.clearSelection();
    }

    private boolean validarCampos() {
        try {
            if (txtNumeroMesa.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El número de mesa es obligatorio");
                txtNumeroMesa.requestFocus();
                return false;
            }

            int numeroMesa = Integer.parseInt(txtNumeroMesa.getText().trim());
            if (numeroMesa <= 0) {
                JOptionPane.showMessageDialog(this, "El número de mesa debe ser mayor a 0");
                txtNumeroMesa.requestFocus();
                return false;
            }

            if (txtCapacidad.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La capacidad es obligatoria");
                txtCapacidad.requestFocus();
                return false;
            }

            int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            if (capacidad <= 0) {
                JOptionPane.showMessageDialog(this, "La capacidad debe ser mayor a 0");
                txtCapacidad.requestFocus();
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese solo números en los campos numéricos");
            return false;
        }
    }
}