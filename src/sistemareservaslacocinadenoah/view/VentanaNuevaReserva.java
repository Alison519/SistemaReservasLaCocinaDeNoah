package sistemareservaslacocinadenoah.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import sistemareservaslacocinadenoah.controller.ClienteController;
import sistemareservaslacocinadenoah.controller.MesaController;
import sistemareservaslacocinadenoah.controller.ReservaController;
import sistemareservaslacocinadenoah.model.Cliente;
import sistemareservaslacocinadenoah.model.Mesa;
import sistemareservaslacocinadenoah.model.Reservas;

/**
 *
 * @author aliso
 */
public class VentanaNuevaReserva extends JFrame {

    private ClienteController clienteController;
    private MesaController mesaController;
    private ReservaController reservaController;

    private JComboBox<Cliente> cmbClientes;
    private JTextField txtFecha, txtHoraInicio, txtHoraFin;
    private JSpinner spnNumPersonas;
    private JComboBox<Mesa> cmbMesasDisponibles;
    private JTextArea txtObservaciones;
    private JButton btnBuscarMesas, btnCrearReserva, btnNuevoCliente, btnCerrar;

    public VentanaNuevaReserva() {
        clienteController = new ClienteController();
        mesaController = new MesaController();
        reservaController = new ReservaController();
        initComponents();
        cargarClientes();
    }

    private void initComponents() {
        setTitle("Nueva Reserva - La Cocina de Noah");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cmbClientes = new JComboBox<>();
        cmbClientes.setPreferredSize(new Dimension(200, 25));
        panelPrincipal.add(cmbClientes, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        btnNuevoCliente = new JButton("Nuevo Cliente");
        btnNuevoCliente.addActionListener(e -> abrirNuevoCliente());
        panelPrincipal.add(btnNuevoCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtFecha = new JTextField();
        txtFecha.setText(LocalDate.now().toString());
        panelPrincipal.add(txtFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelPrincipal.add(new JLabel("Hora Inicio (HH:MM):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtHoraInicio = new JTextField();
        txtHoraInicio.setText("19:00");
        panelPrincipal.add(txtHoraInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelPrincipal.add(new JLabel("Hora Fin (HH:MM):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtHoraFin = new JTextField();
        txtHoraFin.setText("21:00");
        panelPrincipal.add(txtHoraFin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelPrincipal.add(new JLabel("Número de Personas:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        spnNumPersonas = new JSpinner(new SpinnerNumberModel(2, 1, Integer.MAX_VALUE, 1));
        panelPrincipal.add(spnNumPersonas, gbc);

        JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) spnNumPersonas.getEditor()).getTextField();
        spinnerTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarMesasDisponibles();
            }
        });

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        btnBuscarMesas = new JButton("Buscar Mesas");
        btnBuscarMesas.addActionListener(e -> buscarMesasDisponibles());
        panelPrincipal.add(btnBuscarMesas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelPrincipal.add(new JLabel("Mesa:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cmbMesasDisponibles = new JComboBox<>();
        cmbMesasDisponibles.setEnabled(false);
        panelPrincipal.add(cmbMesasDisponibles, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelPrincipal.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        txtObservaciones = new JTextArea(4, 20);
        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        panelPrincipal.add(scrollObservaciones, gbc);

        add(panelPrincipal, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrearReserva = new JButton("Crear Reserva");
        btnCrearReserva.addActionListener(e -> crearReserva());
        btnCrearReserva.setEnabled(false);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnCrearReserva);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarClientes() {
        cmbClientes.removeAllItems();
        List<Cliente> clientes = clienteController.buscarTodos();

        if (clientes != null) {
            for (Cliente cliente : clientes) {
                cmbClientes.addItem(cliente);
            }
        }
    }

    private void buscarMesasDisponibles() {
        try {

            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime horaInicio = LocalTime.parse(txtHoraInicio.getText().trim());
            LocalTime horaFin = LocalTime.parse(txtHoraFin.getText().trim());
            int numPersonas = (Integer) spnNumPersonas.getValue();

            if (horaInicio.isAfter(horaFin)) {
                JOptionPane.showMessageDialog(this, "La hora de inicio debe ser anterior a la hora de fin");
                return;
            }

            List<Mesa> todasLasMesas = mesaController.buscarTodasLasMesas();
            cmbMesasDisponibles.removeAllItems();

            if (todasLasMesas != null) {
                for (Mesa mesa : todasLasMesas) {
                    if (mesa.getCapacidad() >= numPersonas && mesa.getEstado().equals("Disponible")) {
                        cmbMesasDisponibles.addItem(mesa);
                    }
                }
            }

            if (cmbMesasDisponibles.getItemCount() > 0) {
                cmbMesasDisponibles.setEnabled(true);
                btnCrearReserva.setEnabled(true);
                JOptionPane.showMessageDialog(this,
                        "Se encontraron " + cmbMesasDisponibles.getItemCount()
                        + " mesas disponibles para " + numPersonas + " personas");
            } else {
                cmbMesasDisponibles.setEnabled(false);
                btnCrearReserva.setEnabled(false);
                JOptionPane.showMessageDialog(this,
                        "No hay mesas disponibles para " + numPersonas + " personas.\n"
                        + "Intente con menos personas o revise la disponibilidad de mesas.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error en el formato de fecha/hora. Use:\nFecha: YYYY-MM-DD\nHora: HH:MM",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearReserva() {
        try {
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            Mesa mesa = (Mesa) cmbMesasDisponibles.getSelectedItem();
            int numPersonas = (Integer) spnNumPersonas.getValue();

            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente");
                return;
            }

            if (mesa == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una mesa");
                return;
            }

            if (mesa.getCapacidad() < numPersonas) {
                JOptionPane.showMessageDialog(this,
                        "Error: La mesa " + mesa.getNumeroMesa() + " tiene capacidad para "
                        + mesa.getCapacidad() + " personas, pero seleccionó " + numPersonas + " personas.\n"
                        + "Por favor, busque mesas nuevamente.",
                        "Capacidad Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Reservas nuevaReserva = new Reservas();
            nuevaReserva.setCliente(cliente);
            nuevaReserva.setMesa(mesa);
            nuevaReserva.setFechaReserva(LocalDate.parse(txtFecha.getText().trim()));
            nuevaReserva.setHoraInicio(LocalTime.parse(txtHoraInicio.getText().trim()));
            nuevaReserva.setHoraFin(LocalTime.parse(txtHoraFin.getText().trim()));
            nuevaReserva.setNumPersonas(numPersonas);
            nuevaReserva.setEstado("Confirmada");
            nuevaReserva.setObservaciones(txtObservaciones.getText().trim());

            if (reservaController.guardarReserva(nuevaReserva)) {
                JOptionPane.showMessageDialog(this,
                        "Reserva creada exitosamente\n"
                        + "Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\n"
                        + "Mesa: " + mesa.getNumeroMesa() + " (Capacidad: " + mesa.getCapacidad() + ")\n"
                        + "Personas: " + numPersonas + "\n"
                        + "Fecha: " + nuevaReserva.getFechaReserva() + "\n"
                        + "Hora: " + nuevaReserva.getHoraInicio() + " - " + nuevaReserva.getHoraFin());

                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear la reserva", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirNuevoCliente() {
        new VentanaGestionClientes().setVisible(true);
        Timer timer = new Timer(1000, e -> cargarClientes());
        timer.setRepeats(false);
        timer.start();
    }

    private void limpiarFormulario() {
        txtFecha.setText(LocalDate.now().toString());
        txtHoraInicio.setText("19:00");
        txtHoraFin.setText("21:00");
        spnNumPersonas.setValue(2);
        cmbMesasDisponibles.removeAllItems();
        cmbMesasDisponibles.setEnabled(false);
        btnCrearReserva.setEnabled(false);
        txtObservaciones.setText("");
        if (cmbClientes.getItemCount() > 0) {
            cmbClientes.setSelectedIndex(0);
        }
    }
}
