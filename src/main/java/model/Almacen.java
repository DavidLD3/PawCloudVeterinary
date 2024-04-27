package model;

import java.time.LocalDate;

public class Almacen {

    private int idAlmacen;
    private String nombreProducto;
    private String descripcion;
    private String categoria;
    private int cantidadStock;
    private String unidadMedida;
    private int puntoReorden;
    private double precioCompraSinIVA;
    private double precioCompraConIVA;
    private double precioVentaSinIVA;
    private double precioVentaConIVA;
    private String proveedor;
    private LocalDate fechaUltimaCompra;
    private String numeroLote;
    private LocalDate fechaCaducidad;
    private String ubicacion;
    private String codigoBarras;
    private String observaciones;

    // Constructor
    public Almacen(int idAlmacen, String nombreProducto, String descripcion, String categoria, int cantidadStock,
            String unidadMedida, int puntoReorden, double precioCompraSinIVA, double precioCompraConIVA,
            double precioVentaSinIVA, double precioVentaConIVA, String proveedor, LocalDate fechaUltimaCompra,
            String numeroLote, LocalDate fechaCaducidad, String ubicacion, String codigoBarras, String observaciones) {
 this.idAlmacen = idAlmacen;
 this.nombreProducto = nombreProducto;
 this.descripcion = descripcion;
 this.categoria = categoria;
 this.cantidadStock = cantidadStock;
 this.unidadMedida = unidadMedida;
 this.puntoReorden = puntoReorden;
 this.precioCompraSinIVA = precioCompraSinIVA;
 this.precioCompraConIVA = precioCompraConIVA;
 this.precioVentaSinIVA = precioVentaSinIVA;
 this.precioVentaConIVA = precioVentaConIVA;
 this.proveedor = proveedor;
 this.fechaUltimaCompra = fechaUltimaCompra;
 this.numeroLote = numeroLote;
 this.fechaCaducidad = fechaCaducidad;
 this.ubicacion = ubicacion;
 this.codigoBarras = codigoBarras;
 this.observaciones = observaciones;
}

    // Getters and Setters
    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getPuntoReorden() {
        return puntoReorden;
    }

    public void setPuntoReorden(int puntoReorden) {
        this.puntoReorden = puntoReorden;
    }

    public double getPrecioCompraSinIVA() {
        return precioCompraSinIVA;
    }

    public void setPrecioCompraSinIVA(double precioCompraSinIVA) {
        this.precioCompraSinIVA = precioCompraSinIVA;
    }

    public double getPrecioCompraConIVA() {
        return precioCompraConIVA;
    }

    public void setPrecioCompraConIVA(double precioCompraConIVA) {
        this.precioCompraConIVA = precioCompraConIVA;
    }

    public double getPrecioVentaSinIVA() {
        return precioVentaSinIVA;
    }

    public void setPrecioVentaSinIVA(double precioVentaSinIVA) {
        this.precioVentaSinIVA = precioVentaSinIVA;
    }

    public double getPrecioVentaConIVA() {
        return precioVentaConIVA;
    }

    public void setPrecioVentaConIVA(double precioVentaConIVA) {
        this.precioVentaConIVA = precioVentaConIVA;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFechaUltimaCompra() {
        return fechaUltimaCompra;
    }

    public void setFechaUltimaCompra(LocalDate fechaUltimaCompra) {
        this.fechaUltimaCompra = fechaUltimaCompra;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}