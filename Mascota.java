package model;

public class Mascota {
 
	


	public String microchip;
	public String raza;
	public String fechaNacimiento;
	public String caracter;
	public String color;
	public String tipoPelo;
	public String sexo;
    boolean esterilizado;
    public String nombre;
    public String especie;
	public Mascota(String microchip, String raza, String fechaNacimiento, String caracter, String color,
			String tipoPelo, String sexo, boolean esterilizado, String nombre, String especie) {
		super();
		this.microchip = microchip;
		this.raza = raza;
		this.fechaNacimiento = fechaNacimiento;
		this.caracter = caracter;
		this.color = color;
		this.tipoPelo = tipoPelo;
		this.sexo = sexo;
		this.esterilizado = esterilizado;
		this.nombre = nombre;
		this.especie = especie;
	}
 
    public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getMicrochip() {
		return microchip;
	}
	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}
	public String getRaza() {
		return raza;
	}
	public void setRaza(String raza) {
		this.raza = raza;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getCaracter() {
		return caracter;
	}
	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTipoPelo() {
		return tipoPelo;
	}
	public void setTipoPelo(String tipoPelo) {
		this.tipoPelo = tipoPelo;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public boolean isEsterilizado() {
		return esterilizado;
	}
	public void setEsterilizado(boolean esterilizado) {
		this.esterilizado = esterilizado;
	}
    // Constructor, getters y setters
}