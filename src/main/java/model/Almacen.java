package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Almacen {
    private int idAlmacen;
    private String nombreProducto;
    private String descripcion;
    private Categoria categoria; // Utiliza el enum Categoria en lugar de String
    private int cantidadStock;
    private BigDecimal precioCompraSinIVA;
    private BigDecimal precioCompraConIVA;
    private BigDecimal precioVentaSinIVA;
    private BigDecimal precioVentaConIVA;
    private String proveedor;
    private LocalDate fechaUltimaCompra;
    private String numeroLote;
    private LocalDate fechaCaducidad;
    private String codigoBarras;
    private String observaciones;

    // Enumeración para categoría
    public enum Categoria {
        Normal, Cobertura, Servicio, Cargo, Estancia_Hospitalizacion, Estancia_Residencia, Peticion_Analitica, Intervencion, Vacuna, Alimento, Medicamento, Suplemento, Producto_Higienico, Accesorio, Alimento_Especializado, Equipamiento_Medico, Prueba_Diagnostica, Articulo_Educaional, Servicio_Estetico, Plan_Salud
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

    public BigDecimal getPrecioCompraSinIVA() {
        return precioCompraSinIVA;
    }

    public BigDecimal getPrecioCompraConIVA() {
        return precioCompraConIVA;
    }

    public BigDecimal getPrecioVentaSinIVA() {
        return precioVentaSinIVA;
    }

    public BigDecimal getPrecioVentaConIVA() {
        return precioVentaConIVA;
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

    public void setPrecioCompraSinIVA(BigDecimal precioCompraSinIVA) {
        this.precioCompraSinIVA = precioCompraSinIVA;
    }

    public void setPrecioCompraConIVA(BigDecimal precioCompraConIVA) {
        this.precioCompraConIVA = precioCompraConIVA;
    }

    public void setPrecioVentaSinIVA(BigDecimal precioVentaSinIVA) {
        this.precioVentaSinIVA = precioVentaSinIVA;
    }

    public void setPrecioVentaConIVA(BigDecimal precioVentaConIVA) {
        this.precioVentaConIVA = precioVentaConIVA;
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