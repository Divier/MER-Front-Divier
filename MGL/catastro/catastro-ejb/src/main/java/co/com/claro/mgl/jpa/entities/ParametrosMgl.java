/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "MGL_PARAMETROS", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametrosMgl.findAll",
            query = "SELECT s FROM ParametrosMgl s"),
    @NamedQuery(name = "ParametrosMgl.findparAcronimo",
            query = "SELECT s FROM ParametrosMgl s WHERE s.parAcronimo=:parAcronimo")})
public class ParametrosMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ACRONIMO", nullable = false)
    private String parAcronimo;
    @Column(name = "VALOR")
    private String parValor;
    @Column(name = "DESCRIPCION")
    private String parDescripcion;

    @Column(name = "TIPO_PARAMETRO")
    private String parTipoParametro;

    public String getParAcronimo() {
        return parAcronimo;
    }

    public void setParAcronimo(String parAcronimo) {
        this.parAcronimo = parAcronimo;
    }

    public String getParValor() {
        return parValor;
    }

    public void setParValor(String parValor) {
        this.parValor = parValor;
    }

    public String getParDescripcion() {
        return parDescripcion;
    }

    public void setParDescripcion(String parDescripcion) {
        this.parDescripcion = parDescripcion;
    }

    public String getParTipoParametro() {
        return parTipoParametro;
    }

    public void setParTipoParametro(String parTipoParametro) {
        this.parTipoParametro = parTipoParametro;
    }
    
    
    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.ParametrosMgl[ "
                + "id=" + parAcronimo + ", PAR_DESCRIPCION = "
                + parDescripcion + "  ]";
    }

}
