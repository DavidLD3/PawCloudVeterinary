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

	

    // Constructor
    public Hospitalizacion(int id, int idMascota, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, String motivo, String tratamiento, String estado, String notas) {
        this.id = id;
        this.idMascota = idMascota;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.motivo = motivo;
        this.tratamiento = tratamiento;
        this.estado = estado;
        this.notas = notas;
    }

   
}
