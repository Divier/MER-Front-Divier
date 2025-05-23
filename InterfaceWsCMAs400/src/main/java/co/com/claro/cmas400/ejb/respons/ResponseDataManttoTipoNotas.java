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
public class ResponseDataManttoTipoNotas {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String nombre = "";
    @XmlElement
    private String puntoInicial = "";
    @XmlElement
    private String tipoAcometida = "";
    @XmlElement
    private String alimentacionElectrica = "";
    @XmlElement
    private String tipoDistribucion = "";
    @XmlElement
    private String ubicacionCaja = "";

    public ResponseDataManttoTipoNotas() {
    }

    public ResponseDataManttoTipoNotas(String responseString)  {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigo = spliter[0];
            nombre = spliter[1];
            puntoInicial = spliter[2];
            tipoAcometida = spliter[3];
            alimentacionElectrica = spliter[4];
            tipoDistribucion = spliter[5];
            ubicacionCaja = spliter[6];
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuntoInicial() {
        return puntoInicial;
    }

    public void setPuntoInicial(String puntoInicial) {
        this.puntoInicial = puntoInicial;
    }

    public String getTipoAcometida() {
        return tipoAcometida;
    }

    public void setTipoAcometida(String tipoAcometida) {
        this.tipoAcometida = tipoAcometida;
    }

    public String getAlimentacionElectrica() {
        return alimentacionElectrica;
    }

    public void setAlimentacionElectrica(String alimentacionElectrica) {
        this.alimentacionElectrica = alimentacionElectrica;
    }

    public String getTipoDistribucion() {
        return tipoDistribucion;
    }

    public void setTipoDistribucion(String tipoDistribucion) {
        this.tipoDistribucion = tipoDistribucion;
    }

    public String getUbicacionCaja() {
        return ubicacionCaja;
    }

    public void setUbicacionCaja(String ubicacionCaja) {
        this.ubicacionCaja = ubicacionCaja;
    }
}
