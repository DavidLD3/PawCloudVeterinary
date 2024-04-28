package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Almacen {
    private int idAlmacen;
    private String nombreProducto;
    private String descripcion;
    private int categoriaId;
    private int cantidadStock;
    private String unidadMedida;
    private int puntoReorden;
    private BigDecimal precioCompraSinIva;
    private BigDecimal precioCompraConIva;
    private BigDecimal precioVentaSinIva;
    private BigDecimal precioVentaConIva;
    private String proveedor;
    private LocalDate fechaUltimaCompra;
    private String numeroLote;
    private LocalDate fechaCaducidad;
    private String ubicacion;
    private String codigoBarras;
    private String observaciones;
    private String nombreCategoria;
    private boolean esServicio;
    // Constructor sin parámetros
    public Almacen() {
    }

    // Constructor con todos los parámetros
    public Almacen(int idAlmacen, String nombreProducto, String descripcion, int categoriaId, int cantidadStock, String unidadMedida, int puntoReorden, BigDecimal precioCompraSinIva, BigDecimal precioCompraConIva, BigDecimal precioVentaSinIva, BigDecimal precioVentaConIva, String proveedor, LocalDate fechaUltimaCompra, String numeroLote, LocalDate fechaCaducidad, String ubicacion, String codigoBarras, String observaciones) {
        this.idAlmacen = idAlmacen;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.categoriaId = categoriaId;
        this.cantidadStock = cantidadStock;
        this.unidadMedida = unidadMedida;
        this.puntoReorden = puntoReorden;
        this.precioCompraSinIva = precioCompraSinIva;
        this.precioCompraConIva = precioCompraConIva;
        this.precioVentaSinIva = precioVentaSinIva;
        this.precioVentaConIva = precioVentaConIva;
        this.proveedor = proveedor;
        this.fechaUltimaCompra = fechaUltimaCompra;
        this.numeroLote = numeroLote;
        this.fechaCaducidad = fechaCaducidad;
        this.ubicacion = ubicacion;
        this.codigoBarras = codigoBarras;
        this.observaciones = observaciones;
    }

    // Getters y Setters
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

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
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

    public BigDecimal getPrecioCompraSinIva() {
        return precioCompraSinIva;
    }

    public void setPrecioCompraSinIva(BigDecimal precioCompraSinIva) {
        this.precioCompraSinIva = precioCompraSinIva;
    }

    public BigDecimal getPrecioCompraConIva() {
        return precioCompraConIva;
    }

    public void setPrecioCompraConIva(BigDecimal precioCompraConIva) {
        this.precioCompraConIva = precioCompraConIva;
    }

    public BigDecimal getPrecioVentaSinIva() {
        return precioVentaSinIva;
    }

    public void setPrecioVentaSinIva(BigDecimal precioVentaSinIva) {
        this.precioVentaSinIva = precioVentaSinIva;
    }

    public BigDecimal getPrecioVentaConIva() {
        return precioVentaConIva;
    }

    public void setPrecioVentaConIva(BigDecimal precioVentaConIva) {
        this.precioVentaConIva = precioVentaConIva;
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
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public boolean isEsServicio() {
        return esServicio;
    }

    public void setEsServicio(boolean esServicio) {
        this.esServicio = esServicio;
    }
}