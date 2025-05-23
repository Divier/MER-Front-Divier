package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase Comunidad
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class Comunidad implements Serializable {

    private static final long serialVersionUID = 1L;
    private String comId;
    private String comNombre;
    private String comZipcode;
    private GeograficoPolitico geograficoPolitico;
    private String comUsuarioCreacion;
    private Date comFechaCreacion;
    private Date comFechaModificacion;
    private String comUsuarioModificacion;

    /**
     * 
     * @return
     */
    public Date getComFechaCreacion() {
        return comFechaCreacion;
    }

    /**
     * 
     * @param comFechaCreacion
     */
    public void setComFechaCreacion(Date comFechaCreacion) {
        this.comFechaCreacion = comFechaCreacion;
    }

    /**
     * 
     * @return
     */
    public Date getComFechaModificacion() {
        return comFechaModificacion;
    }

    /**
     * 
     * @param comFechaModificacion
     */
    public void setComFechaModificacion(Date comFechaModificacion) {
        this.comFechaModificacion = comFechaModificacion;
    }

    /**
     * 
     * @return
     */
    public String getComId() {
        return comId;
    }

    /**
     * 
     * @param comId
     */
    public void setComId(String comId) {
        this.comId = comId;
    }

    /**
     * 
     * @return
     */
    public String getComNombre() {
        return comNombre;
    }

    /**
     * 
     * @return
     */
    public String getComZipcode() {
        return comZipcode;
    }

    /**
     * 
     * @param comZipcode
     */
    public void setComZipcode(String comZipcode) {
        this.comZipcode = comZipcode;
    }

    /**
     * 
     * @param comNombre
     */
    public void setComNombre(String comNombre) {
        this.comNombre = comNombre;
    }

    /**
     * 
     * @return
     */
    public String getComUsuarioCreacion() {
        return comUsuarioCreacion;
    }

    /**
     * 
     * @param comUsuarioCreacion
     */
    public void setComUsuarioCreacion(String comUsuarioCreacion) {
        this.comUsuarioCreacion = comUsuarioCreacion;
    }

    /**
     * 
     * @return
     */
    public String getComUsuarioModificacion() {
        return comUsuarioModificacion;
    }

    /**
     * 
     * @param comUsuarioModificacion
     */
    public void setComUsuarioModificacion(String comUsuarioModificacion) {
        this.comUsuarioModificacion = comUsuarioModificacion;
    }

    /**
     * 
     * @return
     */
    public GeograficoPolitico getGeograficoPolitico() {
        return geograficoPolitico;
    }

    /**
     * 
     * @param geograficoPolitico
     */
    public void setGeograficoPolitico(GeograficoPolitico geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    @Override
    public String toString() {
        return "Comunidad:" + "comId=" + comId + ", comNombre=" + comNombre
                + ", geograficoPolitico=" + geograficoPolitico + '.';
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "Comunidad:" + "comId=" + comId + ", comNombre=" + comNombre
                + ", geograficoPolitico=" + geograficoPolitico + '.';
    }
}