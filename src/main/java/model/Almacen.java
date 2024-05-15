package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Almacen {
    private int idAlmacen;
    private String nombreProducto;
    private String descripcion;
    private Categoria categoria;
    private int cantidadStock;
    private BigDecimal precioBruto;
    private String proveedor;
    private LocalDate fechaUltimaCompra;
    private String numeroLote;
    private LocalDate fechaCaducidad;
    private String codigoBarras;
    private String observaciones;

    public enum Categoria {
        Normal, Cobertura, Servicio, Cargo, Estancia_Hospitalizacion, Estancia_Residencia,
        Peticion_Analitica, Intervencion, Vacuna, Alimento, Medicamento, Suplemento,
        Producto_Higienico, Accesorio, Alimento_Especializado, Equipamiento_Medico,
        Prueba_Diagnostica, Articulo_Educaional, Servicio_Estetico, Plan_Salud
    }

    public Almacen(int idAlmacen, String nombreProducto, String descripcion, Categoria categoria, 
                   int cantidadStock, BigDecimal precioBruto, String proveedor, 
                   LocalDate fechaUltimaCompra, String numeroLote, LocalDate fechaCaducidad, 
                   String codigoBarras, String observaciones) {
        this.idAlmacen = idAlmacen;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.cantidadStock = cantidadStock;
        this.precioBruto = precioBruto;
        this.proveedor = proveedor;
        this.fechaUltimaCompra = fechaUltimaCompra;
        this.numeroLote = numeroLote;
        this.fechaCaducidad = fechaCaducidad;
        this.codigoBarras = codigoBarras;
        this.observaciones = observaciones;
    }

    // Getters
    public int getIdAlmacen() {
        return idAlmacen;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public BigDecimal getPrecioBruto() {
        return precioBruto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public LocalDate getFechaUltimaCompra() {
        return fechaUltimaCompra;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getObservaciones() {
        return observaciones;
    }

    // Setters
    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public void setPrecioBruto(BigDecimal precioBruto) {
        this.precioBruto = precioBruto;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public void setFechaUltimaCompra(LocalDate fechaUltimaCompra) {
        this.fechaUltimaCompra = fechaUltimaCompra;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
