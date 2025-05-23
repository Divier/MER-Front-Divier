
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author enriquedm
 */
public class FraudesHHPPMasivoDto {
    private BigDecimal idDireccionDetallada;
    private String departamento;
    private String municipio;
    private String centroPoblado;
    private String nodo;
    private String direccion;
    private String codMarca;
    private String codMarcaNuevo;
    private String detalle;
    private int linea;
    protected String fileName;
    protected String msgProceso;
    private boolean procesado;

    public FraudesHHPPMasivoDto() {
    }
    
    public BigDecimal getIdDireccionDetallada() {
        return idDireccionDetallada;
    }

    public void setIdDireccionDetallada(BigDecimal idDireccionDetallada) {
        this.idDireccionDetallada = idDireccionDetallada;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodMarca() {
        return codMarca;
    }

    public void setCodMarca(String codMarca) {
        this.codMarca = codMarca;
    }

    public String getCodMarcaNuevo() {
        return codMarcaNuevo;
    }

    public void setCodMarcaNuevo(String codMarcaNuevo) {
        this.codMarcaNuevo = codMarcaNuevo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public String getMsgProceso() {
        return msgProceso;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }
}
