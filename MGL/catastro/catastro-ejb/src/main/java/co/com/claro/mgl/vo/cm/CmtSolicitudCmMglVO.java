package co.com.claro.mgl.vo.cm;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author GLAFH
 */
public class CmtSolicitudCmMglVO {

    private boolean existSol;
    private boolean existCM;
    private boolean existDirRR;
    private CityEntity cityEntityPrincipal;
    private String idDirCatastroPrincipal;
    private DrDireccion drDireccion;
    private List<UnidadStructPcml> nodosCercanosList;
    private String message;
    private String strMsn;
    private String rrRegional;
    private String usuarioVT;
    private BigDecimal gpoId;
    private BigDecimal geoGpoId;
    private BigDecimal idSolicitudCm;
    private BigDecimal gpoIdCity;
    private BigDecimal geoGpoIdPopulationCenter;

    /**
     * @return the existSol
     */
    public boolean isExistSol() {
        return existSol;
    }

    /**
     * @param existSol the existSol to set
     */
    public void setExistSol(boolean existSol) {
        this.existSol = existSol;
    }

    /**
     * @return the existCM
     */
    public boolean isExistCM() {
        return existCM;
    }

    /**
     * @param existCM the existCM to set
     */
    public void setExistCM(boolean existCM) {
        this.existCM = existCM;
    }

    /**
     * @return the existDirRR
     */
    public boolean isExistDirRR() {
        return existDirRR;
    }

    /**
     * @param existDirRR the existDirRR to set
     */
    public void setExistDirRR(boolean existDirRR) {
        this.existDirRR = existDirRR;
    }

    /**
     * @return the cityEntityPrincipal
     */
    public CityEntity getCityEntityPrincipal() {
        return cityEntityPrincipal;
    }

    /**
     * @param cityEntityPrincipal the cityEntityPrincipal to set
     */
    public void setCityEntityPrincipal(CityEntity cityEntityPrincipal) {
        this.cityEntityPrincipal = cityEntityPrincipal;
    }

    /**
     * @return the idDirCatastroPrincipal
     */
    public String getIdDirCatastroPrincipal() {
        return idDirCatastroPrincipal;
    }

    /**
     * @param idDirCatastroPrincipal the idDirCatastroPrincipal to set
     */
    public void setIdDirCatastroPrincipal(String idDirCatastroPrincipal) {
        this.idDirCatastroPrincipal = idDirCatastroPrincipal;
    }

    /**
     * @return the drDireccion
     */
    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    /**
     * @param drDireccion the drDireccion to set
     */
    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    /**
     * @return the nodosCercanosList
     */
    public List<UnidadStructPcml> getNodosCercanosList() {
        return nodosCercanosList;
    }

    /**
     * @param nodosCercanosList the nodosCercanosList to set
     */
    public void setNodosCercanosList(List<UnidadStructPcml> nodosCercanosList) {
        this.nodosCercanosList = nodosCercanosList;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the strMsn
     */
    public String getStrMsn() {
        return strMsn;
    }

    /**
     * @param strMsn the strMsn to set
     */
    public void setStrMsn(String strMsn) {
        this.strMsn = strMsn;
    }

    /**
     * @return the rrRegional
     */
    public String getRrRegional() {
        return rrRegional;
    }

    /**
     * @param rrRegional the rrRegional to set
     */
    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
    }

    /**
     * @return the usuarioVT
     */
    public String getUsuarioVT() {
        return usuarioVT;
    }

    /**
     * @param usuarioVT the usuarioVT to set
     */
    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    /**
     * @return the gpoId
     */
    public BigDecimal getGpoId() {
        return gpoId;
    }

    /**
     * @param gpoId the gpoId to set
     */
    public void setGpoId(BigDecimal gpoId) {
        this.gpoId = gpoId;
    }

    /**
     * @return the geoGpoId
     */
    public BigDecimal getGeoGpoId() {
        return geoGpoId;
    }

    /**
     * @param geoGpoId the geoGpoId to set
     */
    public void setGeoGpoId(BigDecimal geoGpoId) {
        this.geoGpoId = geoGpoId;
    }

    /**
     * @return the idSolicitudCm
     */
    public BigDecimal getIdSolicitudCm() {
        return idSolicitudCm;
    }

    /**
     * @param idSolicitudCm the idSolicitudCm to set
     */
    public void setIdSolicitudCm(BigDecimal idSolicitudCm) {
        this.idSolicitudCm = idSolicitudCm;
    }

    /**
     * @return the gpoIdCity
     */
    public BigDecimal getGpoIdCity() {
        return gpoIdCity;
    }

    /**
     * @param gpoIdCity the gpoIdCity to set
     */
    public void setGpoIdCity(BigDecimal gpoIdCity) {
        this.gpoIdCity = gpoIdCity;
    }

    /**
     * @return the geoGpoIdPopulationCenter
     */
    public BigDecimal getGeoGpoIdPopulationCenter() {
        return geoGpoIdPopulationCenter;
    }

    /**
     * @param geoGpoIdPopulationCenter the geoGpoIdPopulationCenter to set
     */
    public void setGeoGpoIdPopulationCenter(BigDecimal geoGpoIdPopulationCenter) {
        this.geoGpoIdPopulationCenter = geoGpoIdPopulationCenter;
    }
}
