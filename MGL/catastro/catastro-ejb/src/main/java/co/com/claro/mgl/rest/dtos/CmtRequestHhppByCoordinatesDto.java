package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author mirandaca
 */
@XmlRootElement
public class CmtRequestHhppByCoordinatesDto {
    
    @XmlElement
    private String longitude;
    @XmlElement
    private String latitude;
    @XmlElement
    private int deviationMtr;
    @XmlElement
    private int unitsNumber;
    @XmlElement
    private String ciudad; //codigoDane
    
    @XmlTransient
    private String nombreCentro;
    @XmlTransient
    private String nombreCiudad;
    @XmlTransient
    private String nombreDpto;
    @XmlTransient
    private CmtDireccionDetalladaMgl detalladaMgl;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getDeviationMtr() {
        return deviationMtr;
    }

    public void setDeviationMtr(int deviationMtr) {
        this.deviationMtr = deviationMtr;
    }

    public int getUnitsNumber() {
        return unitsNumber;
    }

    public void setUnitsNumber(int unitsNumber) {
        this.unitsNumber = unitsNumber;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getNombreDpto() {
        return nombreDpto;
    }

    public void setNombreDpto(String nombreDpto) {
        this.nombreDpto = nombreDpto;
    }

    public CmtDireccionDetalladaMgl getDetalladaMgl() {
        return detalladaMgl;
    }

    public void setDetalladaMgl(CmtDireccionDetalladaMgl detalladaMgl) {
        this.detalladaMgl = detalladaMgl;
    }
 
}
