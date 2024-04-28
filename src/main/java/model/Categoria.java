package model;

public class Categoria {
    private int id;
    private boolean tipo; // true para producto, false para servicio
    private String nombreCategoria;

    // Constructor
    public Categoria(int id, boolean tipo, String nombreCategoria) {
        this.id = id;
        this.tipo = tipo;
        this.nombreCategoria = nombreCategoria;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}