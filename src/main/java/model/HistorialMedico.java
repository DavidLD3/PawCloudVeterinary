package model;

import java.time.LocalDate;

public class HistorialMedico {
    private int id;
    private int idMascota;
    private LocalDate fecha;
    private String diagnostico;
    private String tratamiento;

    // Constructor completo
    public HistorialMedico(int id, int idMascota, LocalDate fecha, String diagnostico, String tratamiento) {
        this.id = id;
        this.idMascota = idMascota;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    // Constructor para inserción (sin ID, ya que se genera automáticamente en la base de datos)
    public HistorialMedico(int idMascota, LocalDate fecha, String diagnostico, String tratamiento) {
        this.idMascota = idMascota;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
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

    // Método toString() para representación textual del objeto
    @Override
    public String toString() {
        return "HistorialMedico{" +
                "id=" + id +
                ", idMascota=" + idMascota +
                ", fecha=" + fecha +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                '}';
    }
}
