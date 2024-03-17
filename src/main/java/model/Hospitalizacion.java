package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Hospitalizacion {
	
    private int id;
    private int idMascota;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private String motivo;
    private String tratamiento;
    private String estado;
    private String notas;
    private int idVeterinario;
    private String nombreMascota;
    
    
    public int getIdveterinario() {
		return idVeterinario;
	}

	public void setIdveterinario(int idveterinario) {
		this.idVeterinario = idveterinario;
	}

	
    
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

	public LocalDateTime getFechaIngreso() {
	    return fechaIngreso;
	}

	public void setFechaIngreso(LocalDateTime fechaIngreso) {
	    this.fechaIngreso = fechaIngreso;
	}


	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}
	
	public String getNombreMascota() {
	    return nombreMascota;
	}

	public void setNombreMascota(String nombreMascota) {
	    this.nombreMascota = nombreMascota;
	}


	// Constructor sin el nombre de la mascota
	public Hospitalizacion(int id, int idMascota, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, String motivo, String tratamiento, String estado, String notas) {
	    this.id = id;
	    this.idMascota = idMascota;
	    this.fechaIngreso = fechaIngreso;
	    this.fechaSalida = fechaSalida;
	    this.motivo = motivo;
	    this.tratamiento = tratamiento;
	    this.estado = estado;
	    this.notas = notas;
	    this.idVeterinario = idVeterinario;
	}

	// Constructor con el nombre de la mascota
	public Hospitalizacion(int id, int idMascota, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, String motivo, String tratamiento, String estado, String notas, String nombreMascota) {
	    this(id, idMascota, fechaIngreso, fechaSalida, motivo, tratamiento, estado, notas); // Llama al constructor sin el nombre de la mascota
	    this.nombreMascota = nombreMascota; // Establece el nuevo campo
	}

	// Constructor vacío
	public Hospitalizacion() {
	    
	}

    
   
}
