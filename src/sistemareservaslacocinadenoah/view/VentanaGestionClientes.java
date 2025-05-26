package sistemareservaslacocinadenoah.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import sistemareservaslacocinadenoah.controller.ClienteController;
import sistemareservaslacocinadenoah.model.Cliente;

/**
 *
 * @author aliso
 */
public class VentanaGestionClientes extends JFrame {

    private ClienteController clienteController;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtApellido, txtTelefono, txtEmail;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnCerrar;
    private Cliente clienteSeleccionado = null;

    public VentanaGestionClientes() {
        clienteController = new ClienteController();
        initComponents();
        cargarClientes();
    }

    private void initComponents() {
        setTitle("Gestión de Clientes - La Cocina de Noah");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
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
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

  
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNombre = new JTextField(15);
        panel.add(txtNombre, gbc);


        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtApellido = new JTextField(15);
        panel.add(txtApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtTelefono = new JTextField(15);
        panel.add(txtTelefono, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtEmail = new JTextField(15);
        panel.add(txtEmail, gbc);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));

   
        String[] columnas = {"ID", "Nombre", "Apellido", "Teléfono", "Email", "Fecha Registro"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

  
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarCliente();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
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

    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = clienteController.buscarTodos();

        if (clientes != null) {
            for (Cliente cliente : clientes) {
                Object[] fila = {
                    cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getTelefono(),
                    cliente.getEmail(),
                    cliente.getFechaRegistro()
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    private void seleccionarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idCliente = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
            clienteSeleccionado = clienteController.buscarCliente(idCliente);

            if (clienteSeleccionado != null) {
                txtNombre.setText(clienteSeleccionado.getNombre());
                txtApellido.setText(clienteSeleccionado.getApellido());
                txtTelefono.setText(clienteSeleccionado.getTelefono());
                txtEmail.setText(clienteSeleccionado.getEmail());
            }
        }
    }

    private void agregarCliente() {
        if (!validarCampos()) return;

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(txtNombre.getText().trim());
        nuevoCliente.setApellido(txtApellido.getText().trim());
        nuevoCliente.setTelefono(txtTelefono.getText().trim());
        nuevoCliente.setEmail(txtEmail.getText().trim());
        nuevoCliente.setFechaRegistro(LocalDate.now());

        if (clienteController.guardarCliente(nuevoCliente)) {
            JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente");
            cargarClientes();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCliente() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar");
            return;
        }

        if (!validarCampos()) return;

        clienteSeleccionado.setNombre(txtNombre.getText().trim());
        clienteSeleccionado.setApellido(txtApellido.getText().trim());
        clienteSeleccionado.setTelefono(txtTelefono.getText().trim());
        clienteSeleccionado.setEmail(txtEmail.getText().trim());

        if (clienteController.guardarCliente(clienteSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente");
            cargarClientes();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el cliente: " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellido() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (clienteController.eliminarCliente(clienteSeleccionado.getIdCliente())) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                cargarClientes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        clienteSeleccionado = null;
        tablaClientes.clearSelection();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            txtNombre.requestFocus();
            return false;
        }
        if (txtApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El apellido es obligatorio");
            txtApellido.requestFocus();
            return false;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El teléfono es obligatorio");
            txtTelefono.requestFocus();
            return false;
        }
        return true;
    }
}
