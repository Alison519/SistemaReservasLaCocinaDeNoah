package sistemareservaslacocinadenoah.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import sistemareservaslacocinadenoah.controller.ReservaController;
import sistemareservaslacocinadenoah.controller.ClienteController;
import sistemareservaslacocinadenoah.controller.MesaController;
import sistemareservaslacocinadenoah.model.Reservas;
import sistemareservaslacocinadenoah.model.Cliente;
import sistemareservaslacocinadenoah.model.Mesa;

/**
 *
 * @author aliso
 */
public class VentanaHistorial extends JFrame {

    private ReservaController reservaController;
    private ClienteController clienteController;
    private MesaController mesaController;
    
    private JTextField txtFechaInicio, txtFechaFin;
    private JButton btnGenerar, btnUltimaSemana, btnUltimoMes, btnCerrar;
    private JTable tablaHistorial, tablaEstadisticas;
    private DefaultTableModel modeloHistorial, modeloEstadisticas;
    private JTextArea txtResumen;
    private JLabel lblTotalReservas, lblReservasConfirmadas, lblReservasCanceladas;
    
    public VentanaHistorial() {
        reservaController = new ReservaController();
        clienteController = new ClienteController();
        mesaController = new MesaController();
        initComponents();
        generarReporteUltimaSemana();
    }

    private void initComponents() {
        setTitle("Historial y Reportes - La Cocina de Noah");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelFiltros = crearPanelFiltros();
        add(panelFiltros, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panelHistorial = crearPanelHistorial();
        tabbedPane.addTab("Historial de Reservas", panelHistorial);

        JPanel panelEstadisticas = crearPanelEstadisticas();
        tabbedPane.addTab("Estadísticas", panelEstadisticas);

        JPanel panelResumen = crearPanelResumen();
        tabbedPane.addTab("Resumen", panelResumen);
        
        add(tabbedPane, BorderLayout.CENTER);

        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Rango de Fechas"));

        panel.add(new JLabel("Desde:"));
        txtFechaInicio = new JTextField(12);
        txtFechaInicio.setText(LocalDate.now().minusDays(7).toString());
        panel.add(txtFechaInicio);

        panel.add(new JLabel("Hasta:"));
        txtFechaFin = new JTextField(12);
        txtFechaFin.setText(LocalDate.now().toString());
        panel.add(txtFechaFin);

        btnGenerar = new JButton("Generar Reporte");
        btnGenerar.addActionListener(e -> generarReporte());
        panel.add(btnGenerar);

        btnUltimaSemana = new JButton("Última Semana");
        btnUltimaSemana.addActionListener(e -> generarReporteUltimaSemana());
        panel.add(btnUltimaSemana);

        btnUltimoMes = new JButton("Último Mes");
        btnUltimoMes.addActionListener(e -> generarReporteUltimoMes());
        panel.add(btnUltimoMes);

        return panel;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {
            "ID", "Fecha", "Cliente", "Mesa", "Hora", "Personas", "Estado", "Creada"
        };
        
        modeloHistorial = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.getColumnModel().getColumn(0).setPreferredWidth(50);   
        tablaHistorial.getColumnModel().getColumn(1).setPreferredWidth(100);  
        tablaHistorial.getColumnModel().getColumn(2).setPreferredWidth(150);  
        tablaHistorial.getColumnModel().getColumn(3).setPreferredWidth(70);   
        tablaHistorial.getColumnModel().getColumn(4).setPreferredWidth(120);  
        tablaHistorial.getColumnModel().getColumn(5).setPreferredWidth(70);   
        tablaHistorial.getColumnModel().getColumn(6).setPreferredWidth(90);   
        tablaHistorial.getColumnModel().getColumn(7).setPreferredWidth(150);  

        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        panel.add(scrollHistorial, BorderLayout.CENTER);

        JPanel panelStats = new JPanel(new GridLayout(1, 3));
        
        lblTotalReservas = new JLabel("Total: 0", JLabel.CENTER);
        lblTotalReservas.setBorder(BorderFactory.createTitledBorder("Total Reservas"));
        lblTotalReservas.setFont(new Font("Arial", Font.BOLD, 16));
        
        lblReservasConfirmadas = new JLabel("Confirmadas: 0", JLabel.CENTER);
        lblReservasConfirmadas.setBorder(BorderFactory.createTitledBorder("Confirmadas"));
        lblReservasConfirmadas.setFont(new Font("Arial", Font.BOLD, 16));
        lblReservasConfirmadas.setForeground(new Color(34, 139, 34));
        
        lblReservasCanceladas = new JLabel("Canceladas: 0", JLabel.CENTER);
        lblReservasCanceladas.setBorder(BorderFactory.createTitledBorder("Canceladas"));
        lblReservasCanceladas.setFont(new Font("Arial", Font.BOLD, 16));
        lblReservasCanceladas.setForeground(new Color(220, 20, 60));

        panelStats.add(lblTotalReservas);
        panelStats.add(lblReservasConfirmadas);
        panelStats.add(lblReservasCanceladas);

        panel.add(panelStats, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"Concepto", "Cantidad", "Porcentaje"};
        
        modeloEstadisticas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstadisticas = new JTable(modeloEstadisticas);
        tablaEstadisticas.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablaEstadisticas.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaEstadisticas.getColumnModel().getColumn(2).setPreferredWidth(100);

        JScrollPane scrollEstadisticas = new JScrollPane(tablaEstadisticas);
        panel.add(scrollEstadisticas, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new BorderLayout());

        txtResumen = new JTextArea();
        txtResumen.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResumen.setEditable(false);
        txtResumen.setBackground(new Color(245, 245, 245));

        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        panel.add(scrollResumen, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnExportar = new JButton("Exportar a Texto");
        btnExportar.addActionListener(e -> exportarReporte());

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnExportar);
        panel.add(btnCerrar);

        return panel;
    }

    private void generarReporteUltimaSemana() {
        txtFechaInicio.setText(LocalDate.now().minusDays(7).toString());
        txtFechaFin.setText(LocalDate.now().toString());
        generarReporte();
    }

    private void generarReporteUltimoMes() {
        txtFechaInicio.setText(LocalDate.now().minusMonths(1).toString());
        txtFechaFin.setText(LocalDate.now().toString());
        generarReporte();
    }

    private void generarReporte() {
        try {
            LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText().trim());
            LocalDate fechaFin = LocalDate.parse(txtFechaFin.getText().trim());

            if (fechaInicio.isAfter(fechaFin)) {
                JOptionPane.showMessageDialog(this, "La fecha de inicio debe ser anterior a la fecha fin");
                return;
            }

            List<Reservas> todasReservas = reservaController.obtenerTodasReservas();
            List<Reservas> reservasFiltradas = todasReservas.stream()
                .filter(r -> !r.getFechaReserva().isBefore(fechaInicio) && 
                            !r.getFechaReserva().isAfter(fechaFin))
                .toList();

            cargarHistorial(reservasFiltradas);
            cargarEstadisticas(reservasFiltradas);
            generarResumen(reservasFiltradas, fechaInicio, fechaFin);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error en el formato de fecha. Use: YYYY-MM-DD", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHistorial(List<Reservas> reservas) {
        modeloHistorial.setRowCount(0);

        int total = reservas.size();
        int confirmadas = (int) reservas.stream().filter(r -> "Confirmada".equals(r.getEstado())).count();
        int canceladas = (int) reservas.stream().filter(r -> "Cancelada".equals(r.getEstado())).count();

        lblTotalReservas.setText("Total: " + total);
        lblReservasConfirmadas.setText("Confirmadas: " + confirmadas);
        lblReservasCanceladas.setText("Canceladas: " + canceladas);

        for (Reservas reserva : reservas) {
            Object[] fila = {
                reserva.getIdReserva(),
                reserva.getFechaReserva(),
                reserva.getCliente().getNombre() + " " + reserva.getCliente().getApellido(),
                "Mesa " + reserva.getMesa().getNumeroMesa(),
                reserva.getHoraInicio() + " - " + reserva.getHoraFin(),
                reserva.getNumPersonas(),
                reserva.getEstado(),
                reserva.getFechaCreacion()
            };
            modeloHistorial.addRow(fila);
        }
    }

    private void cargarEstadisticas(List<Reservas> reservas) {
        modeloEstadisticas.setRowCount(0);
        
        if (reservas.isEmpty()) {
            return;
        }

        int total = reservas.size();

        Map<String, Integer> estadoCount = new HashMap<>();
        for (Reservas reserva : reservas) {
            estadoCount.put(reserva.getEstado(), 
                estadoCount.getOrDefault(reserva.getEstado(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : estadoCount.entrySet()) {
            double porcentaje = (entry.getValue() * 100.0) / total;
            Object[] fila = {
                "Reservas " + entry.getKey(),
                entry.getValue(),
                String.format("%.1f%%", porcentaje)
            };
            modeloEstadisticas.addRow(fila);
        }

        Map<Integer, Integer> mesaCount = new HashMap<>();
        for (Reservas reserva : reservas) {
            int numeroMesa = reserva.getMesa().getNumeroMesa();
            mesaCount.put(numeroMesa, mesaCount.getOrDefault(numeroMesa, 0) + 1);
        }

        int mesaMasPopular = mesaCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(0);
        
        if (mesaMasPopular > 0) {
            Object[] filaMesa = {
                "Mesa más popular (Mesa " + mesaMasPopular + ")",
                mesaCount.get(mesaMasPopular),
                String.format("%.1f%%", (mesaCount.get(mesaMasPopular) * 100.0) / total)
            };
            modeloEstadisticas.addRow(filaMesa);
        }

        double promedioPersonas = reservas.stream()
            .mapToInt(Reservas::getNumPersonas)
            .average()
            .orElse(0.0);
            
        Object[] filaPromedio = {
            "Promedio personas/reserva",
            String.format("%.1f", promedioPersonas),
            "-"
        };
        modeloEstadisticas.addRow(filaPromedio);
    }

    private void generarResumen(List<Reservas> reservas, LocalDate fechaInicio, LocalDate fechaFin) {
        StringBuilder resumen = new StringBuilder();
        
        resumen.append("REPORTE DE RESERVAS - LA COCINA DE NOAH\n");
        resumen.append("=====================================\n\n");
        resumen.append("Período: ").append(fechaInicio).append(" al ").append(fechaFin).append("\n");
        resumen.append("Fecha de generación: ").append(LocalDate.now()).append("\n\n");
        
        if (reservas.isEmpty()) {
            resumen.append("No se encontraron reservas en el período seleccionado.\n");
        } else {
            resumen.append("RESUMEN GENERAL:\n");
            resumen.append("- Total de reservas: ").append(reservas.size()).append("\n");
            
            long confirmadas = reservas.stream().filter(r -> "Confirmada".equals(r.getEstado())).count();
            long canceladas = reservas.stream().filter(r -> "Cancelada".equals(r.getEstado())).count();
            long culminadas = reservas.stream().filter(r -> "Culminada".equals(r.getEstado())).count();
            
            resumen.append("- Confirmadas: ").append(confirmadas).append("\n");
            resumen.append("- Canceladas: ").append(canceladas).append("\n");
            resumen.append("- Culminadas: ").append(culminadas).append("\n\n");
            

            int totalPersonas = reservas.stream()
                .filter(r -> !"Cancelada".equals(r.getEstado()))
                .mapToInt(Reservas::getNumPersonas)
                .sum();
            resumen.append("- Total personas atendidas: ").append(totalPersonas).append("\n\n");
            
            resumen.append("DETALLES ADICIONALES:\n");
            resumen.append("- Promedio personas por reserva: ");
            resumen.append(String.format("%.1f", reservas.stream()
                .mapToInt(Reservas::getNumPersonas).average().orElse(0.0))).append("\n");

            Map<String, Long> clienteCount = reservas.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    r -> r.getCliente().getNombre() + " " + r.getCliente().getApellido(),
                    java.util.stream.Collectors.counting()));
                    
            clienteCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(entry -> resumen.append("- Cliente más frecuente: ")
                    .append(entry.getKey()).append(" (").append(entry.getValue()).append(" reservas)\n"));
        }
        
        txtResumen.setText(resumen.toString());
        txtResumen.setCaretPosition(0);
    }

    private void exportarReporte() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte");
        fileChooser.setSelectedFile(new java.io.File("reporte_reservas_" + LocalDate.now() + ".txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile());
                writer.write(txtResumen.getText());
                writer.close();
                JOptionPane.showMessageDialog(this, "Reporte exportado exitosamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}