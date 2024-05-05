package model;

import java.math.BigDecimal;
import java.sql.Timestamp;  // Cambio de Date a Timestamp

public class VentaDetalle {
    private int idVenta;
    private Timestamp fechaVenta;  // Cambio a Timestamp
    private String metodoPago;
    private BigDecimal total;
    private String producto;
    private int cantidad;
    private BigDecimal precioUnitario;

    // Constructor actualizado
    public VentaDetalle(int idVenta, Timestamp fechaVenta, String metodoPago, BigDecimal total, 
                        String producto, int cantidad, BigDecimal precioUnitario) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.metodoPago = metodoPago;
        this.total = total;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Timestamp getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
