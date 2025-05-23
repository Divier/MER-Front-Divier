/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement
public class ResponseDataManttoEdificio {

    @XmlElement
    private String nomEdificio = "";
    @XmlElement
    private String codCalle = "";
    @XmlElement
    private String descCalle = "";
    @XmlElement
    private String numCasa = "";
    @XmlElement
    private String codTipoEdificio = "";
    @XmlElement
    private String descTipoEdificio = "";
    @XmlElement
    private String telPorteria = "";
    @XmlElement
    private String codEstado = "";
    @XmlElement
    private String descEstad;
    @XmlElement
    private String telOtro;
    @XmlElement
    private String codDir1;
    @XmlElement
    private String descDir1;
    @XmlElement
    private String casaDir1;
    @XmlElement
    private String codDir2;
    @XmlElement
    private String descDir2;
    @XmlElement
    private String casaDir2;
    @XmlElement
    private String codDir3;
    @XmlElement
    private String descDir3;
    @XmlElement
    private String casaDir3;
    @XmlElement
    private String codDir4;
    @XmlElement
    private String descDir4;
    @XmlElement
    private String casaDir4;
    @XmlElement
    private String nomContacto;
    @XmlElement
    private String codAdministracion;
    @XmlElement
    private String descAdministracion;
    @XmlElement
    private String codCiaAscensores;
    @XmlElement
    private String descCiaAscensores;
    @XmlElement
    private String headEnd;
    @XmlElement
    private String tipo;
    @XmlElement
    private String nodo;
    @XmlElement
    private String totalUnidades;
    @XmlElement
    private String costoApartament;
    @XmlElement
    private String codEstrato;
    @XmlElement
    private String descEstrato;
    @XmlElement
    private String Codproducto;
    @XmlElement
    private String nomProducto;

    public ResponseDataManttoEdificio() {
    }

    public ResponseDataManttoEdificio(String responseString)  {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            nomEdificio = spliter[0];
            codCalle = spliter[1];
            descCalle = spliter[2];
            numCasa = spliter[3];
            codTipoEdificio = spliter[4];
            descTipoEdificio = spliter[5];
            telPorteria = spliter[6];
            codEstado = spliter[7];
            descEstad = spliter[8];
            telOtro = spliter[9];
            codDir1 = spliter[10];
            descDir1 = spliter[11];
            casaDir1 = spliter[12];
            codDir2 = spliter[13];
            descDir2 = spliter[14];
            casaDir2 = spliter[15];
            codDir3 = spliter[16];
            descDir3 = spliter[17];
            casaDir3 = spliter[18];
            codDir4 = spliter[19];
            descDir4 = spliter[20];
            casaDir4 = spliter[21];
            nomContacto = spliter[22];
            codAdministracion = spliter[23];
            descAdministracion = spliter[24];
            codCiaAscensores = spliter[25];
            descCiaAscensores = spliter[26];
            headEnd = spliter[27];
            tipo = spliter[28];
            nodo = spliter[29];
            totalUnidades = spliter[30];
            costoApartament = spliter[31];
            codEstrato = spliter[32];
            descEstrato = spliter[33];
            Codproducto = spliter[34];
            nomProducto = spliter[35];

        }
    }

    public String getNomEdificio() {
        return nomEdificio;
    }

    public void setNomEdificio(String nomEdificio) {
        this.nomEdificio = nomEdificio;
    }

    public String getCodCalle() {
        return codCalle;
    }

    public void setCodCalle(String codCalle) {
        this.codCalle = codCalle;
    }

    public String getDescCalle() {
        return descCalle;
    }

    public void setDescCalle(String descCalle) {
        this.descCalle = descCalle;
    }

    public String getNumCasa() {
        return numCasa;
    }

    public void setNumCasa(String numCasa) {
        this.numCasa = numCasa;
    }

    public String getCodTipoEdificio() {
        return codTipoEdificio;
    }

    public void setCodTipoEdificio(String codTipoEdificio) {
        this.codTipoEdificio = codTipoEdificio;
    }

    public String getDescTipoEdificio() {
        return descTipoEdificio;
    }

    public void setDescTipoEdificio(String descTipoEdificio) {
        this.descTipoEdificio = descTipoEdificio;
    }

    public String getTelPorteria() {
        return telPorteria;
    }

    public void setTelPorteria(String telPorteria) {
        this.telPorteria = telPorteria;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public String getDescEstad() {
        return descEstad;
    }

    public void setDescEstad(String descEstad) {
        this.descEstad = descEstad;
    }

    public String getTelOtro() {
        return telOtro;
    }

    public void setTelOtro(String telOtro) {
        this.telOtro = telOtro;
    }

    public String getCodDir1() {
        return codDir1;
    }

    public void setCodDir1(String codDir1) {
        this.codDir1 = codDir1;
    }

    public String getDescDir1() {
        return descDir1;
    }

    public void setDescDir1(String descDir1) {
        this.descDir1 = descDir1;
    }

    public String getCasaDir1() {
        return casaDir1;
    }

    public void setCasaDir1(String casaDir1) {
        this.casaDir1 = casaDir1;
    }

    public String getCodDir2() {
        return codDir2;
    }

    public void setCodDir2(String codDir2) {
        this.codDir2 = codDir2;
    }

    public String getDescDir2() {
        return descDir2;
    }

    public void setDescDir2(String descDir2) {
        this.descDir2 = descDir2;
    }

    public String getCasaDir2() {
        return casaDir2;
    }

    public void setCasaDir2(String casaDir2) {
        this.casaDir2 = casaDir2;
    }

    public String getCodDir3() {
        return codDir3;
    }

    public void setCodDir3(String codDir3) {
        this.codDir3 = codDir3;
    }

    public String getDescDir3() {
        return descDir3;
    }

    public void setDescDir3(String descDir3) {
        this.descDir3 = descDir3;
    }

    public String getCasaDir3() {
        return casaDir3;
    }

    public void setCasaDir3(String casaDir3) {
        this.casaDir3 = casaDir3;
    }

    public String getCodDir4() {
        return codDir4;
    }

    public void setCodDir4(String codDir4) {
        this.codDir4 = codDir4;
    }

    public String getDescDir4() {
        return descDir4;
    }

    public void setDescDir4(String descDir4) {
        this.descDir4 = descDir4;
    }

    public String getCasaDir4() {
        return casaDir4;
    }

    public void setCasaDir4(String casaDir4) {
        this.casaDir4 = casaDir4;
    }

    public String getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(String nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getCodAdministracion() {
        return codAdministracion;
    }

    public void setCodAdministracion(String codAdministracion) {
        this.codAdministracion = codAdministracion;
    }

    public String getDescAdministracion() {
        return descAdministracion;
    }

    public void setDescAdministracion(String descAdministracion) {
        this.descAdministracion = descAdministracion;
    }

    public String getCodCiaAscensores() {
        return codCiaAscensores;
    }

    public void setCodCiaAscensores(String codCiaAscensores) {
        this.codCiaAscensores = codCiaAscensores;
    }

    public String getDescCiaAscensores() {
        return descCiaAscensores;
    }

    public void setDescCiaAscensores(String descCiaAscensores) {
        this.descCiaAscensores = descCiaAscensores;
    }

    public String getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getTotalUnidades() {
        return totalUnidades;
    }

    public void setTotalUnidades(String totalUnidades) {
        this.totalUnidades = totalUnidades;
    }

    public String getCostoApartament() {
        return costoApartament;
    }

    public void setCostoApartament(String costoApartament) {
        this.costoApartament = costoApartament;
    }

    public String getCodEstrato() {
        return codEstrato;
    }

    public void setCodEstrato(String codEstrato) {
        this.codEstrato = codEstrato;
    }

    public String getDescEstrato() {
        return descEstrato;
    }

    public void setDescEstrato(String descEstrato) {
        this.descEstrato = descEstrato;
    }

    public String getCodproducto() {
        return Codproducto;
    }

    public void setCodproducto(String Codproducto) {
        this.Codproducto = Codproducto;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }
}
