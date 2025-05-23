package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase HhppConsulta
 * Implementa Serialización.
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class HhppConsulta implements Serializable {

    private BigDecimal identificador = null;
    private String estado = null;
    private String direccionEstandar = null;
    private String cuentaMatriz = null;
    private String idEstadoInicial = null;
    private BigDecimal idTipoRed = null;

    /**
     * Obtiene el estado Inicial.
     * @return Cadena con el estado inicial.
     */
    public String getIdEstadoInicial() {
        return idEstadoInicial;
    }

    /**
     * Establece el estado inicial.
     * @param idEstadoInicial Cadena con el estado inicial.
     */
    public void setIdEstadoInicial(String idEstadoInicial) {
        this.idEstadoInicial = idEstadoInicial;
    }

    /**
     * Obtiene la dirección estándar.
     * @return Cadena con la dirección estándar.
     */
    public String getDireccionEstandar() {
        return direccionEstandar;
    }

    /**
     * Establece la dirección estándar.
     * @param direccionEstandar Caden con la dirección estándar.
     */
    public void setDireccionEstandar(String direccionEstandar) {
        this.direccionEstandar = direccionEstandar;
    }

    /**
     * Obtiene el estado.
     * @return Cadena con el estado.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado.
     * @param estado Cadena con el estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el Id.
     * @return Entero con el Id.
     */
    public BigDecimal getIdentificador() {
        return identificador;
    }

    /**
     * Establece el Id.
     * @param identificador Entero con el Id.
     */
    public void setIdentificador(BigDecimal identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene la cuenta matriz.
     * @return Cadena con la cuenta matriz.
     */
    public String getCuentaMatriz() {
        return cuentaMatriz;
    }

    /**
     * Establece la cuenta matriz.
     * @param cuentaMatriz Cadena con la cuenta matriz.
     */
    public void setCuentaMatriz(String cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }

    /**
     * @return the idTipoRed
     */
    public BigDecimal getIdTipoRed() {
        return idTipoRed;
    }

    /**
     * @param idTipoRed the idTipoRed to set
     */
    public void setIdTipoRed(BigDecimal idTipoRed) {
        this.idTipoRed = idTipoRed;
    }
}
