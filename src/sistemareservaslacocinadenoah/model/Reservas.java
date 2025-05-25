package sistemareservaslacocinadenoah.model;

/**
 *
 * @author aliso
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Reservas {

    private int idReserva;
    private Cliente cliente;
    private Mesa mesa;
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int numPersonas;
    private String estado;
    private String observaciones;
    private LocalDateTime fechaCreacion;

    public Reservas() {
    }

    public Reservas(
            int idReserva,
            Cliente cliente,
            Mesa mesa,
            LocalDate fechaReserva,
            LocalTime horaInicio,
            LocalTime horaFin,
            int numPersonas,
            String estado,
            String observaciones,
            LocalDateTime fechaCreacion
    ) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.mesa = mesa;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.numPersonas = numPersonas;
        this.estado = estado;
        this.observaciones = observaciones;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public LocalDateTime getFechaCreacion(){
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }
}
