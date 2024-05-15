package model;

import java.time.LocalDate;

public class HistorialMedico {
    private int id;
    private int idMascota;
    private LocalDate fecha;
    private String diagnostico;
    private String tratamiento;
    private String tipoIntervencion;

    // Constructor completo
    public HistorialMedico(int id, int idMascota, LocalDate fecha, String diagnostico, String tratamiento, String tipoIntervencion) {
        this.id = id;
        this.idMascota = idMascota;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.tipoIntervencion = tipoIntervencion;
    }

   
    public HistorialMedico(int idMascota, LocalDate fecha, String diagnostico, String tratamiento, String tipoIntervencion) {
        this.idMascota = idMascota;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.tipoIntervencion = tipoIntervencion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
    
    public String getTipoIntervencion() {
        return tipoIntervencion;
    }

    public void setTipoIntervencion(String tipoIntervencion) {
        this.tipoIntervencion = tipoIntervencion;
    }

    // Método toString() para representación textual del objeto
    @Override
    public String toString() {
        return "HistorialMedico{" +
                "id=" + id +
                ", idMascota=" + idMascota +
                ", fecha=" + fecha +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", tipoIntervencion='" + tipoIntervencion + '\'' +
                '}';
    }
}
