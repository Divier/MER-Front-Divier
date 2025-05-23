/*
 * Clase creada para ser la encargada y ser la unica en ser utilizada como parametro de validación
 * en los métodos que realizan validaciones parametrizadas. Al nacer una nueva validación basta con crear
 * atributos a esta clase los cuales seran cargados con la información que se desea validar y asi todos
 * los metodos recibir siempre el mismo tipo de dato.
 */
package co.com.claro.ejb.mgl.address.dto;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;

/**
 *
 * @author Juan David Hernandez
 */
public class ValidacionParametrizadaDto {
    
    //parametro general
    private String mensajeValidacion;
    private boolean resultadoValidacion;
    
    //Atributos para validacion de solicitud de Hhpp
    private DrDireccion drDireccion;
    private GeograficoPoliticoMgl centroPoblado;
    private GeograficoPoliticoMgl departamento;
    private CmtBasicaMgl tecnologiaBasicaId;

    //Atributos para una nueva validacion
    
    
    
    
     public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public GeograficoPoliticoMgl getDepartamento() {
        return departamento;
    }

    public void setDepartamento(GeograficoPoliticoMgl departamento) {
        this.departamento = departamento;
    }
    
    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public CmtBasicaMgl getTecnologiaBasicaId() {
        return tecnologiaBasicaId;
    }

    public void setTecnologiaBasicaId(CmtBasicaMgl tecnologiaBasicaId) {
        this.tecnologiaBasicaId = tecnologiaBasicaId;
    }

    public boolean isResultadoValidacion() {
        return resultadoValidacion;
    }

    public void setResultadoValidacion(boolean resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }
    
    
    
    
}
