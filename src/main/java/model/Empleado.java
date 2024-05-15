package model;

import java.util.Date;

public class Empleado {
	private int id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private String email;
    private Date fechaContratacion;
    private String horarioTrabajo;

    public Empleado() {
    }

    public Empleado(String nombre, String apellidos, String dni, String telefono, String email, Date fechaContratacion, String horarioTrabajo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.fechaContratacion = fechaContratacion;
        this.horarioTrabajo = horarioTrabajo;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getHorarioTrabajo() {
        return horarioTrabajo;
    }

    public void setHorarioTrabajo(String horarioTrabajo) {
        this.horarioTrabajo = horarioTrabajo;
    }

    // Método toString para representar el objeto como un String
    @Override
    public String toString() {
        return String.format(
            "Empleado{id=%d, nombre='%s', apellidos='%s', dni='%s', telefono='%s', email='%s', fechaContratacion=%s, horarioTrabajo='%s'}",
            id, nombre, apellidos, dni, telefono, email, fechaContratacion, horarioTrabajo);
    }

    
    
}
