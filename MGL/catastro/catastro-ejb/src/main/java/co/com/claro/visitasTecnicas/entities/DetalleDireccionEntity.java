package co.com.claro.visitasTecnicas.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@XmlRootElement
public class DetalleDireccionEntity implements Serializable, Cloneable {

   @XmlElement
    private BigDecimal id;
    @XmlElement
    private BigDecimal idsolicitud;
    @XmlElement 
    private String idtipodireccion;
    @XmlElement 
    private String dirprincalt;
    @XmlElement 
    private String barrio;
    @XmlElement 
    private String tipoviaprincipal;
    @XmlElement 
    private String numviaprincipal;
    @XmlElement 
    private String ltviaprincipal;
    @XmlElement 
    private String nlpostviap;
    @XmlElement 
    private String bisviaprincipal;
    @XmlElement 
    private String cuadviaprincipal;
    @XmlElement 
    private String tipoviageneradora;
    @XmlElement 
    private String numviageneradora;
    @XmlElement 
    private String ltviageneradora;
    @XmlElement 
    private String nlpostviag;
    @XmlElement 
    private String letra3g;
    @XmlElement 
    private String bisviageneradora;
    @XmlElement 
    private String cuadviageneradora;
    @XmlElement 
    private String placadireccion;
    @XmlElement 
    private String cptiponivel1;
    @XmlElement 
    private String cptiponivel2;
    @XmlElement 
    private String cptiponivel3;
    @XmlElement 
    private String cptiponivel4;
    @XmlElement 
    private String cptiponivel5;
    @XmlElement 
    private String cptiponivel6;
    @XmlElement 
    private String cpvalornivel1;
    @XmlElement 
    private String cpvalornivel2;
    @XmlElement 
    private String cpvalornivel3;
    @XmlElement 
    private String cpvalornivel4;
    @XmlElement 
    private String cpvalornivel5;
    @XmlElement 
    private String cpvalornivel6;
    @XmlElement 
    private String mztiponivel1;
    @XmlElement 
    private String mztiponivel2;
    @XmlElement 
    private String mztiponivel3;
    @XmlElement 
    private String mztiponivel4;
    @XmlElement 
    private String mztiponivel5;
    @XmlElement 
    private String mztiponivel6;
    @XmlElement 
    private String mzvalornivel1;
    @XmlElement 
    private String mzvalornivel2;
    @XmlElement 
    private String mzvalornivel3;
    @XmlElement 
    private String mzvalornivel4;
    @XmlElement 
    private String mzvalornivel5;
    @XmlElement 
    private String mzvalornivel6;
    @XmlElement 
    private String descTipoDir;
    @XmlElement 
    private String idDirCatastro;
    @XmlElement 
    private String itTipoPlaca;
    @XmlElement 
    private String itValorPlaca;
    @XmlElement 
    private String estrato;
    @XmlElement 
    private String estadoDir;
    @XmlElement 
    private String multiOrigen;

    public DetalleDireccionEntity() {
        this.id = BigDecimal.ZERO;
        this.idsolicitud = BigDecimal.ZERO;
        this.idtipodireccion = "";
        this.dirprincalt = "";
        this.barrio = barrio;
        this.tipoviaprincipal = "";
        this.numviaprincipal = "";
        this.ltviaprincipal = "";
        this.nlpostviap = "";
        this.bisviaprincipal = "";
        this.cuadviaprincipal = "";
        this.tipoviageneradora = "";
        this.numviageneradora = "";
        this.ltviageneradora = "";
        this.nlpostviag = "";
        this.letra3g = "";
        this.bisviageneradora = "";
        this.cuadviageneradora = "";
        this.placadireccion = "";
        this.cptiponivel1 = "";
        this.cptiponivel2 = "";
        this.cptiponivel3 = "";
        this.cptiponivel4 = "";
        this.cptiponivel5 = "";
        this.cptiponivel6 = "";
        this.cpvalornivel1 = "";
        this.cpvalornivel2 = "";
        this.cpvalornivel3 = "";
        this.cpvalornivel4 = "";
        this.cpvalornivel5 = "";
        this.cpvalornivel6 = "";
        this.mztiponivel1 = "";
        this.mztiponivel2 = "";
        this.mztiponivel3 = "";
        this.mztiponivel4 = "";
        this.mztiponivel5 = "";
        this.mzvalornivel1 = "";
        this.mzvalornivel2 = "";
        this.mzvalornivel3 = "";
        this.mzvalornivel4 = "";
        this.mzvalornivel5 = "";
        this.descTipoDir = "";
        this.idDirCatastro = "";
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(BigDecimal idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public String getIdtipodireccion() {
        return idtipodireccion;
    }

    public void setIdtipodireccion(String idtipodireccion) {
        this.idtipodireccion = idtipodireccion;
    }

    public String getDirprincalt() {
        return dirprincalt;
    }

    public void setDirprincalt(String dirprincalt) {
        this.dirprincalt = dirprincalt;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoviaprincipal() {
        return tipoviaprincipal;
    }

    public void setTipoviaprincipal(String tipoviaprincipal) {
        this.tipoviaprincipal = tipoviaprincipal;
    }

    public String getNumviaprincipal() {
        return numviaprincipal;
    }

    public void setNumviaprincipal(String numviaprincipal) {
        this.numviaprincipal = numviaprincipal;
    }

    public String getLtviaprincipal() {
        return ltviaprincipal;
    }

    public void setLtviaprincipal(String ltviaprincipal) {
        this.ltviaprincipal = ltviaprincipal;
    }

    public String getNlpostviap() {
        return nlpostviap;
    }

    public void setNlpostviap(String nlpostviap) {
        this.nlpostviap = nlpostviap;
    }

    public String getBisviaprincipal() {
        return bisviaprincipal;
    }

    public void setBisviaprincipal(String bisviaprincipal) {
        this.bisviaprincipal = bisviaprincipal;
    }

    public String getCuadviaprincipal() {
        return cuadviaprincipal;
    }

    public void setCuadviaprincipal(String cuadviaprincipal) {
        this.cuadviaprincipal = cuadviaprincipal;
    }

    public String getTipoviageneradora() {
        return tipoviageneradora;
    }

    public void setTipoviageneradora(String tipoviageneradora) {
        this.tipoviageneradora = tipoviageneradora;
    }

    public String getNumviageneradora() {
        return numviageneradora;
    }

    public void setNumviageneradora(String numviageneradora) {
        this.numviageneradora = numviageneradora;
    }

    public String getLtviageneradora() {
        return ltviageneradora;
    }

    public void setLtviageneradora(String ltviageneradora) {
        this.ltviageneradora = ltviageneradora;
    }

    public String getNlpostviag() {
        return nlpostviag;
    }

    public void setNlpostviag(String nlpostviag) {
        this.nlpostviag = nlpostviag;
    }

    public String getBisviageneradora() {
        return bisviageneradora;
    }

    public void setBisviageneradora(String bisviageneradora) {
        this.bisviageneradora = bisviageneradora;
    }

    public String getCuadviageneradora() {
        return cuadviageneradora;
    }

    public void setCuadviageneradora(String cuadviageneradora) {
        this.cuadviageneradora = cuadviageneradora;
    }

    public String getPlacadireccion() {
        return placadireccion;
    }

    public void setPlacadireccion(String placadireccion) {
        this.placadireccion = placadireccion;
    }

    public String getCptiponivel1() {
        return cptiponivel1;
    }

    public void setCptiponivel1(String cptiponivel1) {
        this.cptiponivel1 = cptiponivel1;
    }

    public String getCptiponivel2() {
        return cptiponivel2;
    }

    public void setCptiponivel2(String cptiponivel2) {
        this.cptiponivel2 = cptiponivel2;
    }

    public String getCptiponivel3() {
        return cptiponivel3;
    }

    public void setCptiponivel3(String cptiponivel3) {
        this.cptiponivel3 = cptiponivel3;
    }

    public String getCptiponivel4() {
        return cptiponivel4;
    }

    public void setCptiponivel4(String cptiponivel4) {
        this.cptiponivel4 = cptiponivel4;
    }

    public String getCptiponivel5() {
        return cptiponivel5;
    }

    public void setCptiponivel5(String cptiponivel5) {
        this.cptiponivel5 = cptiponivel5;
    }

    public String getCptiponivel6() {
        return cptiponivel6;
    }

    public void setCptiponivel6(String cptiponivel6) {
        this.cptiponivel6 = cptiponivel6;
    }

    public String getCpvalornivel1() {
        return cpvalornivel1;
    }

    public void setCpvalornivel1(String cpvalornivel1) {
        this.cpvalornivel1 = cpvalornivel1;
    }

    public String getCpvalornivel2() {
        return cpvalornivel2;
    }

    public void setCpvalornivel2(String cpvalornivel2) {
        this.cpvalornivel2 = cpvalornivel2;
    }

    public String getCpvalornivel3() {
        return cpvalornivel3;
    }

    public void setCpvalornivel3(String cpvalornivel3) {
        this.cpvalornivel3 = cpvalornivel3;
    }

    public String getCpvalornivel4() {
        return cpvalornivel4;
    }

    public void setCpvalornivel4(String cpvalornivel4) {
        this.cpvalornivel4 = cpvalornivel4;
    }

    public String getCpvalornivel5() {
        return cpvalornivel5;
    }

    public void setCpvalornivel5(String cpvalornivel5) {
        this.cpvalornivel5 = cpvalornivel5;
    }

    public String getCpvalornivel6() {
        return cpvalornivel6;
    }

    public void setCpvalornivel6(String cpvalornivel6) {
        this.cpvalornivel6 = cpvalornivel6;
    }

    public String getMztiponivel1() {
        return mztiponivel1;
    }

    public void setMztiponivel1(String mztiponivel1) {
        this.mztiponivel1 = mztiponivel1;
    }

    public String getMztiponivel2() {
        return mztiponivel2;
    }

    public void setMztiponivel2(String mztiponivel2) {
        this.mztiponivel2 = mztiponivel2;
    }

    public String getMztiponivel3() {
        return mztiponivel3;
    }

    public void setMztiponivel3(String mztiponivel3) {
        this.mztiponivel3 = mztiponivel3;
    }

    public String getMztiponivel4() {
        return mztiponivel4;
    }

    public void setMztiponivel4(String mztiponivel4) {
        this.mztiponivel4 = mztiponivel4;
    }

    public String getMztiponivel5() {
        return mztiponivel5;
    }

    public void setMztiponivel5(String mztiponivel5) {
        this.mztiponivel5 = mztiponivel5;
    }

    public String getMzvalornivel1() {
        return mzvalornivel1;
    }

    public void setMzvalornivel1(String mzvalornivel1) {
        this.mzvalornivel1 = mzvalornivel1;
    }

    public String getMzvalornivel2() {
        return mzvalornivel2;
    }

    public void setMzvalornivel2(String mzvalornivel2) {
        this.mzvalornivel2 = mzvalornivel2;
    }

    public String getMzvalornivel3() {
        return mzvalornivel3;
    }

    public void setMzvalornivel3(String mzvalornivel3) {
        this.mzvalornivel3 = mzvalornivel3;
    }

    public String getMzvalornivel4() {
        return mzvalornivel4;
    }

    public void setMzvalornivel4(String mzvalornivel4) {
        this.mzvalornivel4 = mzvalornivel4;
    }

    public String getMzvalornivel5() {
        return mzvalornivel5;
    }

    public void setMzvalornivel5(String mzvalornivel5) {
        this.mzvalornivel5 = mzvalornivel5;
    }

    public String getDescTipoDir() {
        return descTipoDir;
    }

    public void setDescTipoDir(String descTipoDir) {
        this.descTipoDir = descTipoDir;
    }

    public String getIdDirCatastro() {
        return idDirCatastro;
    }

    public void setIdDirCatastro(String idDirCatastro) {
        this.idDirCatastro = idDirCatastro;
    }

    public String getMztiponivel6() {
        return mztiponivel6;
    }

    public void setMztiponivel6(String mztiponivel6) {
        this.mztiponivel6 = mztiponivel6;
    }

    public String getMzvalornivel6() {
        return mzvalornivel6;
    }

    public void setMzvalornivel6(String mzvalornivel6) {
        this.mzvalornivel6 = mzvalornivel6;
    }

    public String getItTipoPlaca() {
        return itTipoPlaca;
    }

    public void setItTipoPlaca(String itTipoPlaca) {
        this.itTipoPlaca = itTipoPlaca;
    }

    public String getItValorPlaca() {
        return itValorPlaca;
    }

    public void setItValorPlaca(String itValorPlaca) {
        this.itValorPlaca = itValorPlaca;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getEstadoDir() {
        return estadoDir;
    }

    public void setEstadoDir(String estadoDir) {
        this.estadoDir = estadoDir;
    }

    public String getMultiOrigen() {
        return multiOrigen;
    }

    public void setMultiOrigen(String multiOrigen) {
        this.multiOrigen = multiOrigen;
    }

    @Override
    public DetalleDireccionEntity clone() throws CloneNotSupportedException {
        return (DetalleDireccionEntity) super.clone();
    }

    public String getLetra3g() {
        return letra3g;
    }

    public void setLetra3g(String letra3g) {
        this.letra3g = letra3g;
    }
    private static final String BIS = "BIS";

    public String getDirCkPlaca() {

        String dirResult = "";
        String txDirNum = (numviaprincipal == null) ? "" : numviaprincipal;
        String txDirGenNum = (numviageneradora == null) ? "" : numviageneradora;
        String txNumPlacaPrin = (placadireccion == null) ? "" : placadireccion;

        String tmp;
        tmp = tipoviaprincipal;
        if (tmp != null && !tmp.isEmpty() && txDirNum != null && !txDirNum.isEmpty()) {
            dirResult += tmp + " " + txDirNum + " ";
        }
        tmp = ltviaprincipal;
        if (tmp != null && !tmp.isEmpty()) {
            dirResult += tmp + " ";
        }
        tmp = nlpostviap;
        if (tmp != null && !tmp.isEmpty()) {
            dirResult += "- " + tmp + " ";
        }
        tmp = bisviaprincipal;
        if (tmp != null && !tmp.isEmpty()) {
            if (tmp.equalsIgnoreCase(BIS)) {
                dirResult += tmp + " ";
            } else {
                dirResult += BIS + " " + tmp + " ";
            }
        }
        tmp = cuadviaprincipal;
        if (tmp != null && !tmp.isEmpty()) {
            dirResult += tmp + " ";
        }
        if (txDirGenNum != null && !txDirGenNum.isEmpty()) {
            tmp = tipoviageneradora;
            if (tmp != null
                    && !tmp.isEmpty()
                    && !tmp.equalsIgnoreCase("VACIO")
                    && !tmp.equalsIgnoreCase("V")) {
                dirResult += tmp + " ";
            } else {
                dirResult += " # ";
            }
            dirResult += txDirGenNum + " ";
        }

        tmp = ltviageneradora;
        if (tmp != null && !tmp.isEmpty()) {
            dirResult += tmp + " ";
        }
        tmp = nlpostviag;
        if (tmp != null && !tmp.isEmpty()) {
            dirResult += "- " + tmp + " ";
        }
        tmp = letra3g;
        if (tmp != null && !tmp.isEmpty()) {
            dirResult += "- " + tmp + " ";
        }
        tmp = bisviageneradora;
        if (tmp != null && !tmp.isEmpty()) {
            if (tmp.equalsIgnoreCase(BIS)) {
                dirResult += tmp + " ";
            } else {
                dirResult += BIS + " " + tmp + " ";
            }
        }
        tmp = cuadviageneradora;
        if (tmp != null && !tmp.isEmpty()) {
            dirResult += tmp + " ";
        }
        if (txNumPlacaPrin != null && !txNumPlacaPrin.isEmpty()) {
            dirResult += txNumPlacaPrin;
        }
        return dirResult;
    }
}