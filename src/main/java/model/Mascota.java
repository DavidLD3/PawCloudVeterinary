package model;

import java.time.LocalDate;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private int idCliente; // Clave foránea que apunta al ID del cliente
    private String microchip;
    private LocalDate fechaNacimiento;
    private String caracter;
    private String color;
    private String tipoPelo;
    private String sexo;
    private boolean esterilizado;
    
    
    

    // Constructor combinado
    public Mascota(int id, String nombre, String especie, String raza, int edad, int idCliente, 
            String microchip, LocalDate fechaNacimiento, String caracter, String color, 
            String tipoPelo, String string, boolean esterilizado) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.idCliente = idCliente;
        this.microchip = microchip;
        this.fechaNacimiento = fechaNacimiento;
        this.caracter = caracter;
        this.color = color;
        this.tipoPelo = tipoPelo;
        this.sexo = string;
        this.esterilizado = esterilizado;
    }

    // Getters y Setters combinados
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
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
    public enum Sexo {
        MACHO, HEMBRA
    }
    public static class MascotaContenedor {
        private Mascota mascota;

        public MascotaContenedor(Mascota mascota) {
            this.mascota = mascota;
        }

        public Mascota getMascota() {
            return mascota;
        }

        @Override
        public String toString() {
            return mascota.getNombre(); // Suponiendo que quieres mostrar solo el nombre en el comboBox
        }
    }




    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", idCliente=" + idCliente +
                ", microchip='" + microchip + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", caracter='" + caracter + '\'' +
                ", color='" + color + '\'' +
                ", tipoPelo='" + tipoPelo + '\'' +
                ", sexo='" + sexo + '\'' +
                ", esterilizado=" + esterilizado +
                '}';
    }

}
