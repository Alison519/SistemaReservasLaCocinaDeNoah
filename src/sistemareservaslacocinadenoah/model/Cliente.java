package sistemareservaslacocinadenoah.model;


import java.time.LocalDate;
/**
 *
 * @author aliso
 */
public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDate fechaRegistro;
    
    public Cliente(){
        
    }
    
    public Cliente (
            int idCliente,
            String nombre,
            String apellido,
            String telefono,
            String email,
            LocalDate fechaRegistro
    ){
        
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
    }
    
    
    // Getters and Setters
    
    public int getIdCliente(){
        return idCliente;
    }
    
    public void setIdCliente(int idCliente){
        this.idCliente = idCliente;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido (String apellido){
        this.apellido = apellido;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono (String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
        
    }
    
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro (LocalDate fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }
    
    
    @Override
    public String toString(){
        return nombre + " " + apellido;
    }
}
