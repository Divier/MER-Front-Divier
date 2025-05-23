package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Entidad <b>Nodo Pol&iacute;gono</b>.<br>
 * Tabla: <i>TEC_NODO_POLIGONO</i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>)
 */
@Entity
@Table(name = "TEC_NODO_POLIGONO", schema = "MGL_SCHEME")
@NamedQueries({
    @NamedQuery(name = "NodoPoligono.findByNodoVertice", query = "SELECT n FROM NodoPoligono n WHERE n.nodoVertice.nodId = :nodId")})
public class NodoPoligono implements Serializable {
    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "NodoPoligono.seq_nodo",
            sequenceName = "MGL_SCHEME.TEC_NODO_POLIGONO_SQ", allocationSize = 1
    )
    @GeneratedValue(generator = "NodoPoligono.seq_nodo")
    @Column(name = "NODO_POLIGONO_ID", nullable = false)
    private BigDecimal nodoPoligonoId;
    
    @ManyToOne
    @JoinColumn(name = "NODO_ID_VERTICE", nullable = true)
    private NodoMgl nodoVertice;
    
    /**
     * Longitud.
     */
    @Column(name = "X", nullable = true, length = 15)
    private BigDecimal x;
    
    /**
     * Latitud.
     */
    @Column(name = "Y", nullable = true, length = 15)
    private BigDecimal y;
    
    
    @Transient
    private int distanciaMts;
    
    
    /**
     * @see java.lang.Object#toString() 
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        if (nodoVertice != null && nodoVertice.getNodNombre() != null) {
            builder.append("Vértice: ").append(nodoVertice.getNodNombre()).append(System.lineSeparator());
        }
        if (x != null) {
            builder.append("X: ").append(x).append(System.lineSeparator());
        }
        if (y != null) {
            builder.append("Y: ").append(y);
        }
        
        return builder.toString();
    }
    
    
    public BigDecimal getNodoPoligonoId() {
        return nodoPoligonoId;
    }

    public void setNodoPoligonoId(BigDecimal nodoPoligonoId) {
        this.nodoPoligonoId = nodoPoligonoId;
    }

    public NodoMgl getNodoVertice() {
        return nodoVertice;
    }

    public void setNodoVertice(NodoMgl nodoVertice) {
        this.nodoVertice = nodoVertice;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public int getDistanciaMts() {
        return distanciaMts;
    }

    public void setDistanciaMts(int distanciaMts) {
        this.distanciaMts = distanciaMts;
    }
}
