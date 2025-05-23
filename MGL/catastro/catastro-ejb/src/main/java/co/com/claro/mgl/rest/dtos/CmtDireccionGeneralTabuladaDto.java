/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "direccionGeneralTabulada")
public class CmtDireccionGeneralTabuladaDto {

    @XmlElement
    private String idDireccionDetallada;
    @XmlElement
    private String dirPrincAlt;
    @XmlElement
    private String barrio;
    @XmlElement
    private String tipoViaPrincipal;
    @XmlElement
    private String numViaPrincipal;
    @XmlElement
    private String ltViaPrincipal;
    @XmlElement
    private String nlPostViaP;
    @XmlElement
    private String bisViaPrincipal;
    @XmlElement
    private String cuadViaPrincipal;
    @XmlElement
    private String tipoViaGeneradora;
    @XmlElement
    private String numViaGeneradora;
    @XmlElement
    private String ltViaGeneradora;
    @XmlElement
    private String nlPostViaG;
    @XmlElement
    private String bisViaGeneradora;
    @XmlElement
    private String cuadViaGeneradora;
    @XmlElement
    private String placaDireccion;
    @XmlElement
    private String cpTipoNivel1;
    @XmlElement
    private String cpTipoNivel2;
    @XmlElement
    private String cpTipoNivel3;
    @XmlElement
    private String cpTipoNivel4;
    @XmlElement
    private String cpTipoNivel5;
    @XmlElement
    private String cpTipoNivel6;
    @XmlElement
    private String cpValorNivel1;
    @XmlElement
    private String cpValorNivel2;
    @XmlElement
    private String cpValorNivel3;
    @XmlElement
    private String cpValorNivel4;
    @XmlElement
    private String cpValorNivel5;
    @XmlElement
    private String cpValorNivel6;
    @XmlElement
    private String mzTipoNivel1;
    @XmlElement
    private String mzTipoNivel2;
    @XmlElement
    private String mzTipoNivel3;
    @XmlElement
    private String mzTipoNivel4;
    @XmlElement
    private String mzTipoNivel5;
    @XmlElement
    private String mzValorNivel1;
    @XmlElement
    private String mzValorNivel2;
    @XmlElement
    private String mzValorNivel3;
    @XmlElement
    private String mzValorNivel4;
    @XmlElement
    private String mzValorNivel5;
    @XmlElement
    private String mzTipoNivel6;
    @XmlElement
    private String mzValorNivel6;
    @XmlElement
    private String itTipoPlaca;
    @XmlElement
    private String itValorPlaca;
    @XmlElement
    private String estadoDirGeo;
    @XmlElement
    private String letra3G;
    @XmlElement
    private String direccionTexto;
    @XmlTransient
    private BigDecimal direccionId;
    @XmlTransient
    private BigDecimal subdireccionId;
    

    public String getIdDireccionDetallada() {
        return idDireccionDetallada;
    }

    public void setIdDireccionDetallada(String idDireccionDetallada) {
        this.idDireccionDetallada = idDireccionDetallada;
    }

    public String getDirPrincAlt() {
        return dirPrincAlt;
    }

    public void setDirPrincAlt(String dirPrincAlt) {
        this.dirPrincAlt = dirPrincAlt;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoViaPrincipal() {
        return tipoViaPrincipal;
    }

    public void setTipoViaPrincipal(String tipoViaPrincipal) {
        this.tipoViaPrincipal = tipoViaPrincipal;
    }

    public String getNumViaPrincipal() {
        return numViaPrincipal;
    }

    public void setNumViaPrincipal(String numViaPrincipal) {
        this.numViaPrincipal = numViaPrincipal;
    }

    public String getLtViaPrincipal() {
        return ltViaPrincipal;
    }

    public void setLtViaPrincipal(String ltViaPrincipal) {
        this.ltViaPrincipal = ltViaPrincipal;
    }

    public String getNlPostViaP() {
        return nlPostViaP;
    }

    public void setNlPostViaP(String nlPostViaP) {
        this.nlPostViaP = nlPostViaP;
    }

    public String getBisViaPrincipal() {
        return bisViaPrincipal;
    }

    public void setBisViaPrincipal(String bisViaPrincipal) {
        this.bisViaPrincipal = bisViaPrincipal;
    }

    public String getCuadViaPrincipal() {
        return cuadViaPrincipal;
    }

    public void setCuadViaPrincipal(String cuadViaPrincipal) {
        this.cuadViaPrincipal = cuadViaPrincipal;
    }

    public String getTipoViaGeneradora() {
        return tipoViaGeneradora;
    }

    public void setTipoViaGeneradora(String tipoViaGeneradora) {
        this.tipoViaGeneradora = tipoViaGeneradora;
    }

    public String getNumViaGeneradora() {
        return numViaGeneradora;
    }

    public void setNumViaGeneradora(String numViaGeneradora) {
        this.numViaGeneradora = numViaGeneradora;
    }

    public String getLtViaGeneradora() {
        return ltViaGeneradora;
    }

    public void setLtViaGeneradora(String ltViaGeneradora) {
        this.ltViaGeneradora = ltViaGeneradora;
    }

    public String getNlPostViaG() {
        return nlPostViaG;
    }

    public void setNlPostViaG(String nlPostViaG) {
        this.nlPostViaG = nlPostViaG;
    }

    public String getBisViaGeneradora() {
        return bisViaGeneradora;
    }

    public void setBisViaGeneradora(String bisViaGeneradora) {
        this.bisViaGeneradora = bisViaGeneradora;
    }

    public String getCuadViaGeneradora() {
        return cuadViaGeneradora;
    }

    public void setCuadViaGeneradora(String cuadViaGeneradora) {
        this.cuadViaGeneradora = cuadViaGeneradora;
    }

    public String getPlacaDireccion() {
        return placaDireccion;
    }

    public void setPlacaDireccion(String placaDireccion) {
        this.placaDireccion = placaDireccion;
    }

    public String getCpTipoNivel1() {
        return cpTipoNivel1;
    }

    public void setCpTipoNivel1(String cpTipoNivel1) {
        this.cpTipoNivel1 = cpTipoNivel1;
    }

    public String getCpTipoNivel2() {
        return cpTipoNivel2;
    }

    public void setCpTipoNivel2(String cpTipoNivel2) {
        this.cpTipoNivel2 = cpTipoNivel2;
    }

    public String getCpTipoNivel3() {
        return cpTipoNivel3;
    }

    public void setCpTipoNivel3(String cpTipoNivel3) {
        this.cpTipoNivel3 = cpTipoNivel3;
    }

    public String getCpTipoNivel4() {
        return cpTipoNivel4;
    }

    public void setCpTipoNivel4(String cpTipoNivel4) {
        this.cpTipoNivel4 = cpTipoNivel4;
    }

    public String getCpTipoNivel5() {
        return cpTipoNivel5;
    }

    public void setCpTipoNivel5(String cpTipoNivel5) {
        this.cpTipoNivel5 = cpTipoNivel5;
    }

    public String getCpTipoNivel6() {
        return cpTipoNivel6;
    }

    public void setCpTipoNivel6(String cpTipoNivel6) {
        this.cpTipoNivel6 = cpTipoNivel6;
    }

    public String getCpValorNivel1() {
        return cpValorNivel1;
    }

    public void setCpValorNivel1(String cpValorNivel1) {
        this.cpValorNivel1 = cpValorNivel1;
    }

    public String getCpValorNivel2() {
        return cpValorNivel2;
    }

    public void setCpValorNivel2(String cpValorNivel2) {
        this.cpValorNivel2 = cpValorNivel2;
    }

    public String getCpValorNivel3() {
        return cpValorNivel3;
    }

    public void setCpValorNivel3(String cpValorNivel3) {
        this.cpValorNivel3 = cpValorNivel3;
    }

    public String getCpValorNivel4() {
        return cpValorNivel4;
    }

    public void setCpValorNivel4(String cpValorNivel4) {
        this.cpValorNivel4 = cpValorNivel4;
    }

    public String getCpValorNivel5() {
        return cpValorNivel5;
    }

    public void setCpValorNivel5(String cpValorNivel5) {
        this.cpValorNivel5 = cpValorNivel5;
    }

    public String getCpValorNivel6() {
        return cpValorNivel6;
    }

    public void setCpValorNivel6(String cpValorNivel6) {
        this.cpValorNivel6 = cpValorNivel6;
    }

    public String getMzTipoNivel1() {
        return mzTipoNivel1;
    }

    public void setMzTipoNivel1(String mzTipoNivel1) {
        this.mzTipoNivel1 = mzTipoNivel1;
    }

    public String getMzTipoNivel2() {
        return mzTipoNivel2;
    }

    public void setMzTipoNivel2(String mzTipoNivel2) {
        this.mzTipoNivel2 = mzTipoNivel2;
    }

    public String getMzTipoNivel3() {
        return mzTipoNivel3;
    }

    public void setMzTipoNivel3(String mzTipoNivel3) {
        this.mzTipoNivel3 = mzTipoNivel3;
    }

    public String getMzTipoNivel4() {
        return mzTipoNivel4;
    }

    public void setMzTipoNivel4(String mzTipoNivel4) {
        this.mzTipoNivel4 = mzTipoNivel4;
    }

    public String getMzTipoNivel5() {
        return mzTipoNivel5;
    }

    public void setMzTipoNivel5(String mzTipoNivel5) {
        this.mzTipoNivel5 = mzTipoNivel5;
    }

    public String getMzValorNivel1() {
        return mzValorNivel1;
    }

    public void setMzValorNivel1(String mzValorNivel1) {
        this.mzValorNivel1 = mzValorNivel1;
    }

    public String getMzValorNivel2() {
        return mzValorNivel2;
    }

    public void setMzValorNivel2(String mzValorNivel2) {
        this.mzValorNivel2 = mzValorNivel2;
    }

    public String getMzValorNivel3() {
        return mzValorNivel3;
    }

    public void setMzValorNivel3(String mzValorNivel3) {
        this.mzValorNivel3 = mzValorNivel3;
    }

    public String getMzValorNivel4() {
        return mzValorNivel4;
    }

    public void setMzValorNivel4(String mzValorNivel4) {
        this.mzValorNivel4 = mzValorNivel4;
    }

    public String getMzValorNivel5() {
        return mzValorNivel5;
    }

    public void setMzValorNivel5(String mzValorNivel5) {
        this.mzValorNivel5 = mzValorNivel5;
    }

    public String getMzTipoNivel6() {
        return mzTipoNivel6;
    }

    public void setMzTipoNivel6(String mzTipoNivel6) {
        this.mzTipoNivel6 = mzTipoNivel6;
    }

    public String getMzValorNivel6() {
        return mzValorNivel6;
    }

    public void setMzValorNivel6(String mzValorNivel6) {
        this.mzValorNivel6 = mzValorNivel6;
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

    public String getEstadoDirGeo() {
        return estadoDirGeo;
    }

    public void setEstadoDirGeo(String estadoDirGeo) {
        this.estadoDirGeo = estadoDirGeo;
    }

    public String getLetra3G() {
        return letra3G;
    }

    public void setLetra3G(String letra3G) {
        this.letra3G = letra3G;
    }

    public String getDireccionTexto() {
        return direccionTexto;
    }

    public void setDireccionTexto(String direccionTexto) {
        this.direccionTexto = direccionTexto;
    }

    public BigDecimal getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(BigDecimal direccionId) {
        this.direccionId = direccionId;
    }

    public BigDecimal getSubdireccionId() {
        return subdireccionId;
    }

    public void setSubdireccionId(BigDecimal subdireccionId) {
        this.subdireccionId = subdireccionId;
    }
    
}
