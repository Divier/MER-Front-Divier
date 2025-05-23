/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.facade.PcmlFacadeLocal;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressSuggested;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "cmtCityEntity")
public class CmtCityEntityDto extends CmtDefaultBasicResponse {
    
    @XmlElement
    private String codCity = "";
    @XmlElement
    private String cityName = "";
    @XmlElement 
    private String dpto = "";
    @XmlElement 
    private String codDane = "";
    @XmlElement 
    private String pais = "";
    @XmlElement
    private String barrio ="";
    @XmlElement 
    private String address = "";
    @XmlElement 
    private String existencia="" ;
    @XmlElement 
    private String direccion="" ;
    @XmlElement 
    private String nodo1="" ;
    @XmlElement 
    private String nodo2="" ;
    @XmlElement 
    private String nodo3="" ;
    @XmlElement 
    private String nodoDTH="" ;
    @XmlElement 
    private String postalCode="" ;
    @XmlElement 
    private String estratoDir=""; 
    @XmlElement 
    private String estadoDir="";
    @XmlElement 
    private String confiabilidadDir="";
    @XmlElement 
    private String dirStandar="";
    @XmlElement 
    private List<AddressSuggested> BarrioSugerido=new ArrayList<AddressSuggested>();
    @XmlElement 
    private String fuente="";
    @XmlElement
    private String codigoDirccion="";
    @XmlElement 
    private DetalleDireccionEntity detalleDireccionEntity=null;
    @XmlElement
    private DetalleDireccionEntity actualDetalleDireccionEntity=null;
    @XmlTransient
    private PcmlFacadeLocal pcmlFacadeLocal;
    @XmlElement 
    private BigDecimal cityId; 
    @XmlElement
    private String existeRr="";
    @XmlElement 
    private DireccionRREntity direccionRREntityAntigua;
    @XmlElement 
    private DireccionRREntity direccionRREntityNueva;
    @XmlElement 
    private String tipoSolictud="";
    @XmlElement 
    private boolean cambioDireccion=false;
    @XmlElement 
    private String dirIgacAntiguaStr="";
    @XmlElement 
    private boolean existeHhppAntiguoNuevo = false;
    @XmlElement 
    private String idUsuario;

    public List<AddressSuggested> getBarrioSugerido() {
        return BarrioSugerido;
    }
    
    public void setBarrioSugerido(List<AddressSuggested> BarrioSugerido) {
        this.BarrioSugerido = BarrioSugerido;
    }

    public String getCodCity() {
        return codCity;
    }

    public void setCodCity(String codCity) {
        this.codCity = codCity;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getCodDane() {
        return codDane;
    }

    public void setCodDane(String codDane) {
        this.codDane = codDane;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNodo1() {
        return nodo1;
    }

    public void setNodo1(String nodo1) {
        this.nodo1 = nodo1;
    }

    public String getNodo2() {
        return nodo2;
    }

    public void setNodo2(String nodo2) {
        this.nodo2 = nodo2;
    }

    public String getNodo3() {
        return nodo3;
    }

    public void setNodo3(String nodo3) {
        this.nodo3 = nodo3;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEstratoDir() {
        return estratoDir;
    }

    public void setEstratoDir(String estratoDir) {
        this.estratoDir = estratoDir;
    }

    public String getEstadoDir() {
        return estadoDir;
    }

    public void setEstadoDir(String estadoDir) {
        this.estadoDir = estadoDir;
    }    


    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getCodigoDirccion() {
        return codigoDirccion;
    }

    public void setCodigoDirccion(String codigoDirccion) {
        this.codigoDirccion = codigoDirccion;
    }

    public DetalleDireccionEntity getDetalleDireccionEntity() {
        return detalleDireccionEntity;
    }

    public void setDetalleDireccionEntity(DetalleDireccionEntity detalleDireccionEntity) {
        this.detalleDireccionEntity = detalleDireccionEntity;
    }

    public DetalleDireccionEntity getActualDetalleDireccionEntity() {
        return actualDetalleDireccionEntity;
    }

    public void setActualDetalleDireccionEntity(DetalleDireccionEntity actualDetalleDireccionEntity) {
        this.actualDetalleDireccionEntity = actualDetalleDireccionEntity;
    }

    public PcmlFacadeLocal getPcmlFacadeLocal() {
        return pcmlFacadeLocal;
    }

    public void setPcmlFacadeLocal(PcmlFacadeLocal pcmlFacadeLocal) {
        this.pcmlFacadeLocal = pcmlFacadeLocal;
    }

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public String getExisteRr() {
        return existeRr;
    }

    public void setExisteRr(String existeRr) {
        this.existeRr = existeRr;
    }

    public DireccionRREntity getDireccionRREntityAntigua() {
        return direccionRREntityAntigua;
    }

    public void setDireccionRREntityAntigua(DireccionRREntity direccionRREntityAntigua) {
        this.direccionRREntityAntigua = direccionRREntityAntigua;
    }

    public String getTipoSolictud() {
        return tipoSolictud;
    }

    public void setTipoSolictud(String tipoSolictud) {
        this.tipoSolictud = tipoSolictud;
    }
    
	 public String getConfiabilidadDir() {
        return confiabilidadDir;
    }

    public void setConfiabilidadDir(String confiabilidadDir) {
        this.confiabilidadDir = confiabilidadDir;
    }

    public boolean isCambioDireccion() {
        return cambioDireccion;
    }

    public void setCambioDireccion(boolean cambioDireccion) {
        this.cambioDireccion = cambioDireccion;
    }

    public DireccionRREntity getDireccionRREntityNueva() {
        return direccionRREntityNueva;
    }

    public void setDireccionRREntityNueva(DireccionRREntity direccionRREntityNueva) {
        this.direccionRREntityNueva = direccionRREntityNueva;
    }

    public String getDirIgacAntiguaStr() {
        return dirIgacAntiguaStr;
    }

    public void setDirIgacAntiguaStr(String dirIgacAntiguaStr) {
        this.dirIgacAntiguaStr = dirIgacAntiguaStr;
    }

    public boolean isExisteHhppAntiguoNuevo() {
        return existeHhppAntiguoNuevo;
    }

    public void setExisteHhppAntiguoNuevo(boolean existeHhppAntiguoNuevo) {
        this.existeHhppAntiguoNuevo = existeHhppAntiguoNuevo;
    }


    public String getDirStandar() {
        return dirStandar;
    }

    public void setDirStandar(String dirStandar) {
        this.dirStandar = dirStandar;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNodoDTH() {
        return nodoDTH;
}

    public void setNodoDTH(String nodoDTH) {
        this.nodoDTH = nodoDTH;
    }
    
    

}
