/**
 *
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author victor bocanegra-HITSS
 *
 */
package co.com.claro.mgw.agenda.hardclose;

import co.com.claro.mgw.agenda.user.UserAut;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para orderCompleteRequest complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="orderCompleteRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user"
 *          type="{http://ws.ordermanager.globalhitss.com.co/}userAut"/&gt;
 *         &lt;element name="IdMensaje"
 *          type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="idOT"
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Origen"
 *           type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IdTecnico"
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="coordenadas"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="recaudo"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valorRecaudado"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notasRecaudo"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notasFirma"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="razon"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RespuestaEncuesta"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fechaSalidaTecnico"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="equiposADevolver"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="iims1"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="iims2"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="iims3"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="estadoVisita"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderCompleteRequest", propOrder = {
    "user",
    "idMensaje",
    "idOT",
    "origen",
    "idTecnico",
    "coordenadas",
    "recaudo",
    "valorRecaudado",
    "notasRecaudo",
    "notasFirma",
    "razon",
    "respuestaEncuesta",
    "fechaSalidaTecnico",
    "equiposADevolver",
    "iims1",
    "iims2",
    "iims3",
    "tecnologias"
})
public class OrderCompleteRequest {

    @XmlElement(required = true)
    protected UserAut user;
    @XmlElement(name = "IdMensaje")
    protected int idMensaje;
    @XmlElement(required = true)
    protected String idOT;
    @XmlElement(name = "Origen", required = true)
    protected String origen;
    @XmlElement(name = "IdTecnico", required = true)
    protected String idTecnico;
    protected String coordenadas;
    protected String recaudo;
    protected String valorRecaudado;
    protected String notasRecaudo;
    protected String notasFirma;
    protected String razon;
    @XmlElement(name = "RespuestaEncuesta")
    protected String respuestaEncuesta;
    protected String fechaSalidaTecnico;
    protected String equiposADevolver;
    protected String iims1;
    protected String iims2;
    protected String iims3;

    public OrderCompleteRequest() {
    }

    public OrderCompleteRequest(
            UserAut user, int idMensaje, String idOT,
            String origen, String idTecnico, String coordenadas, String recaudo,
            String valorRecaudado, String notasRecaudo, String notasFirma,
            String razon, String respuestaEncuesta, String fechaSalidaTecnico,
            String equiposADevolver, String iims1, String iims2, String iims3,
            String estadoVisita
    ) {
        this.user = user;
        this.idMensaje = idMensaje;
        this.idOT = idOT;
        this.origen = origen;
        this.idTecnico = idTecnico;
        this.coordenadas = coordenadas;
        this.recaudo = recaudo;
        this.valorRecaudado = valorRecaudado;
        this.notasRecaudo = notasRecaudo;
        this.notasFirma = notasFirma;
        this.razon = razon;
        this.respuestaEncuesta = respuestaEncuesta;
        this.fechaSalidaTecnico = fechaSalidaTecnico;
        this.equiposADevolver = equiposADevolver;
        this.iims1 = iims1;
        this.iims2 = iims2;
        this.iims3 = iims3;
    }

    /**
     * Obtiene el valor de la propiedad user.
     *
     * @return possible object is {@link UserAut }
     *
     */
    public UserAut getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     *
     * @param value allowed object is {@link UserAut }
     *
     */
    public void setUser(UserAut value) {
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad idMensaje.
     *
     * @return
     */
    public int getIdMensaje() {
        return idMensaje;
    }

    /**
     * Define el valor de la propiedad idMensaje.
     *
     * @param value
     */
    public void setIdMensaje(int value) {
        this.idMensaje = value;
    }

    /**
     * Obtiene el valor de la propiedad idOT.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIdOT() {
        return idOT;
    }

    /**
     * Define el valor de la propiedad idOT.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIdOT(String value) {
        this.idOT = value;
    }

    /**
     * Obtiene el valor de la propiedad origen.
     *
     * @return possible object is {@link String }
     *
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Define el valor de la propiedad origen.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setOrigen(String value) {
        this.origen = value;
    }

    /**
     * Obtiene el valor de la propiedad idTecnico.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIdTecnico() {
        return idTecnico;
    }

    /**
     * Define el valor de la propiedad idTecnico.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIdTecnico(String value) {
        this.idTecnico = value;
    }

    /**
     * Obtiene el valor de la propiedad coordenadas.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCoordenadas() {
        return coordenadas;
    }

    /**
     * Define el valor de la propiedad coordenadas.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCoordenadas(String value) {
        this.coordenadas = value;
    }

    /**
     * Obtiene el valor de la propiedad recaudo.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRecaudo() {
        return recaudo;
    }

    /**
     * Define el valor de la propiedad recaudo.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRecaudo(String value) {
        this.recaudo = value;
    }

    /**
     * Obtiene el valor de la propiedad valorRecaudado.
     *
     * @return possible object is {@link String }
     *
     */
    public String getValorRecaudado() {
        return valorRecaudado;
    }

    /**
     * Define el valor de la propiedad valorRecaudado.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setValorRecaudado(String value) {
        this.valorRecaudado = value;
    }

    /**
     * Obtiene el valor de la propiedad notasRecaudo.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNotasRecaudo() {
        return notasRecaudo;
    }

    /**
     * Define el valor de la propiedad notasRecaudo.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNotasRecaudo(String value) {
        this.notasRecaudo = value;
    }

    /**
     * Obtiene el valor de la propiedad notasFirma.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNotasFirma() {
        return notasFirma;
    }

    /**
     * Define el valor de la propiedad notasFirma.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNotasFirma(String value) {
        this.notasFirma = value;
    }

    /**
     * Obtiene el valor de la propiedad razon.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRazon() {
        return razon;
    }

    /**
     * Define el valor de la propiedad razon.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRazon(String value) {
        this.razon = value;
    }

    /**
     * Obtiene el valor de la propiedad respuestaEncuesta.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRespuestaEncuesta() {
        return respuestaEncuesta;
    }

    /**
     * Define el valor de la propiedad respuestaEncuesta.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRespuestaEncuesta(String value) {
        this.respuestaEncuesta = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaSalidaTecnico.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFechaSalidaTecnico() {
        return fechaSalidaTecnico;
    }

    /**
     * Define el valor de la propiedad fechaSalidaTecnico.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFechaSalidaTecnico(String value) {
        this.fechaSalidaTecnico = value;
    }

    /**
     * Obtiene el valor de la propiedad equiposADevolver.
     *
     * @return possible object is {@link String }
     *
     */
    public String getEquiposADevolver() {
        return equiposADevolver;
    }

    /**
     * Define el valor de la propiedad equiposADevolver.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setEquiposADevolver(String value) {
        this.equiposADevolver = value;
    }

    /**
     * Obtiene el valor de la propiedad iims1.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIims1() {
        return iims1;
    }

    /**
     * Define el valor de la propiedad iims1.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIims1(String value) {
        this.iims1 = value;
    }

    /**
     * Obtiene el valor de la propiedad iims2.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIims2() {
        return iims2;
    }

    /**
     * Define el valor de la propiedad iims2.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIims2(String value) {
        this.iims2 = value;
    }

    /**
     * Obtiene el valor de la propiedad iims3.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIims3() {
        return iims3;
    }

    /**
     * Define el valor de la propiedad iims3.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIims3(String value) {
        this.iims3 = value;
    }

}
