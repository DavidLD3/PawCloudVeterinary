package model;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private int idCliente; // Clave foránea que apunta al ID del cliente

    // Constructor vacío
    public Mascota() {}

    // Constructor con todos los campos
    public Mascota(int id, String nombre, String especie, String raza, int edad, int idCliente) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.idCliente = idCliente;
    }

    // Getters y Setters
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
    @Override
    public String toString() {
        return this.nombre; // Esto hará que solo el nombre del cliente se muestre en el combobox
    }
}
