package co.com.claro.visitasTecnicas.entities;

import co.com.claro.mgl.facade.PcmlFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.telmex.catastro.data.AddressSuggested;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase ParamMultivalor
 * @author 	Diego Barrera
 * @version     version 1.2
 * @date        2013/09/12
 */
@XmlRootElement
public class CityEntity implements Serializable, Cloneable {
    
    private String codCity = "";
    private String cityName = "";
    private String dpto = "";
    private String codDane = "";
    private String pais = "";
    private String barrio ="";
    private String address = "";
    private String existencia="" ;
    private String direccion="" ;
    private String nodo1="" ;
    private String nodo2="" ;
    private String nodo3="" ;
    private String nodoDTH="" ;
    private String postalCode="" ;
    private String estratoDir=""; 
    private String estadoDir="";
    private String confiabilidadDir="";
    private String dirStandar="";
    private List<AddressSuggested> BarrioSugerido=new ArrayList<AddressSuggested>();
    private String fuente="";
    private String codigoDirccion="";
    private DetalleDireccionEntity detalleDireccionEntity=null;
    private DetalleDireccionEntity actualDetalleDireccionEntity=null;
    @XmlTransient
    private PcmlFacadeLocal pcmlFacadeLocal;
    private BigDecimal cityId; 
    private String existeRr="";
    private DireccionRREntity direccionRREntityAntigua;
    private DireccionRREntity direccionRREntityNueva;
    private String tipoSolictud="";
    private boolean cambioDireccion=false;
    private String dirIgacAntiguaStr="";
    private boolean existeHhppAntiguoNuevo = false;
    private String idUsuario;
    private String direccionSinApto;
    private String direccionAlternaAptoStr;
    private DrDireccion direccionAlterna;
    private DrDireccion direccionPrincipal;
    private DrDireccion direccionAntiguaGeo;  
    private DrDireccion direccionNuevaGeo;  
    private List<HhppMgl> unidadesAsociadasPredioAntiguasList;
    private List<UnidadStructPcml> unidadStructPcmlNuevasList;
    private List<UnidadStructPcml> unidadStructPcmlAntiguasList;
    private String message;
    private String direccionRespuestaGeo;
    private HhppMgl hhppMglCambioANueva;

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

    public String getDireccionSinApto() {
        return direccionSinApto;
    }

    public void setDireccionSinApto(String direccionSinApto) {
        this.direccionSinApto = direccionSinApto;
    }

    public String getDireccionAlternaAptoStr() {
        return direccionAlternaAptoStr;
    }

    public void setDireccionAlternaAptoStr(String direccionAlternaAptoStr) {
        this.direccionAlternaAptoStr = direccionAlternaAptoStr;
    }

    public DrDireccion getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(DrDireccion direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    public DrDireccion getDireccionAlterna() {
        return direccionAlterna;
    }

    public void setDireccionAlterna(DrDireccion direccionAlterna) {
        this.direccionAlterna = direccionAlterna;
    }

    public DrDireccion getDireccionAntiguaGeo() {
        return direccionAntiguaGeo;
    }

    public void setDireccionAntiguaGeo(DrDireccion direccionAntiguaGeo) {
        this.direccionAntiguaGeo = direccionAntiguaGeo;
    }

    public DrDireccion getDireccionNuevaGeo() {
        return direccionNuevaGeo;
    }

    public void setDireccionNuevaGeo(DrDireccion direccionNuevaGeo) {
        this.direccionNuevaGeo = direccionNuevaGeo;
    }   

    public List<HhppMgl> getUnidadesAsociadasPredioAntiguasList() {
        return unidadesAsociadasPredioAntiguasList;
    }

    public void setUnidadesAsociadasPredioAntiguasList(List<HhppMgl> unidadesAsociadasPredioAntiguasList) {
        this.unidadesAsociadasPredioAntiguasList = unidadesAsociadasPredioAntiguasList;
    }

    public List<UnidadStructPcml> getUnidadStructPcmlNuevasList() {
        return unidadStructPcmlNuevasList;
    }

    public void setUnidadStructPcmlNuevasList(List<UnidadStructPcml> unidadStructPcmlNuevasList) {
        this.unidadStructPcmlNuevasList = unidadStructPcmlNuevasList;
    }

    public List<UnidadStructPcml> getUnidadStructPcmlAntiguasList() {
        return unidadStructPcmlAntiguasList;
    }

    public void setUnidadStructPcmlAntiguasList(List<UnidadStructPcml> unidadStructPcmlAntiguasList) {
        this.unidadStructPcmlAntiguasList = unidadStructPcmlAntiguasList;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDireccionRespuestaGeo() {
        return direccionRespuestaGeo;
    }

    public void setDireccionRespuestaGeo(String direccionRespuestaGeo) {
        this.direccionRespuestaGeo = direccionRespuestaGeo;
    } 
    
    @Override
    public CityEntity clone() throws CloneNotSupportedException {
        return (CityEntity) super.clone();
    }

    public HhppMgl getHhppMglCambioANueva() {
        return hhppMglCambioANueva;
    }

    public void setHhppMglCambioANueva(HhppMgl hhppMglCambioANueva) {
        this.hhppMglCambioANueva = hhppMglCambioANueva;
    }
    
    

}
