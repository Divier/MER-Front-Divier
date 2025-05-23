/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad Configuracion creacion Auto de HHPP. Entidad de base de datos
 * Configuracion creacion Auto de HHPP.
 *
 * @author Johnnatan Ortiz
 * @versión 1.00.000
 */
@Entity
@Table(name = "TEC_CREATECHABAUTO_CONF", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfCreahppAuto.findAll",
            query = "SELECT d FROM ConfCreahppAuto d"),
    @NamedQuery(name = "ConfCreahppAuto.findById",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.id = :id"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoA",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoA = :estadoGeoA"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoB",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoB = :estadoGeoB"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoC",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoC = :estadoGeoC"),
    @NamedQuery(name = "ConfCreahppAuto.findByChastadoGeoD",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoD = :estadoGeoD"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoE",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoE = :estadoGeoE"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoF",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoF = :estadoGeoF"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoG",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoG = :estadoGeoG"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoH",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoH = :estadoGeoH"),
    @NamedQuery(name = "ConfCreahppAuto.findByChastadoGeoI",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoI = :estadoGeoI"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoJ",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoJ = :estadoGeoJ"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoL",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoL = :estadoGeoL"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoM",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoM = :estadoGeoM"),
    @NamedQuery(name = "ConfCreahppAuto.findByaEstadoGeoN",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoN = :estadoGeoN"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoR",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoR = :estadoGeoR"),
    @NamedQuery(name = "ConfCreahppAuto.findByaEstadoGeoW",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoW = :estadoGeoW"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoX",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoX = :estadoGeoX"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoY",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoY = :estadoGeoY"),
    @NamedQuery(name = "ConfCreahppAuto.findByEstadoGeoZ",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.estadoGeoZ = :estadoGeoZ"),
    @NamedQuery(name = "ConfCreahppAuto.findByPvalGeo90",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.porcentajeValGeo90 = :porcentajeValGeo90"),
    @NamedQuery(name = "ConfCreahppAuto.findByPvalGeo95",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.porcentajevalGeo95 = :porcentajeValGeo95"),
    @NamedQuery(name = "ConfCreahppAuto.findByPvalGeo100",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.porcentajevalGeo100 = :porcentajeValGeo100"),
    @NamedQuery(name = "ConfCreahppAuto.findByIdCity",
            query = "SELECT d FROM ConfCreahppAuto d WHERE d.gpoId = :gpoId")})
public class ConfCreahppAuto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ConfCreahppAuto.creaHHPPAutoSq",
            sequenceName = "MGL_SCHEME.TEC_CREATECHABAUTO_CONF_SQ",
            allocationSize = 10)
    @GeneratedValue(generator = "ConfCreahppAuto.creaHHPPAutoSq")
    @Column(name = "CREATECHABAUTO_CONF_ID", nullable = false)
    private BigDecimal id;
    @Column(name = "ESTADO_GEO_A", nullable = false)
    private short estadoGeoA;
    @Column(name = "ESTADO_GEO_B", nullable = false)
    private short estadoGeoB;
    @Column(name = "ESTADO_GEO_C", nullable = false)
    private short estadoGeoC;
    @Column(name = "ESTADO_GEO_D", nullable = false)
    private short estadoGeoD;
    @Column(name = "ESTADO_GEO_E", nullable = false)
    private short estadoGeoE;
    @Column(name = "ESTADO_GEO_F", nullable = false)
    private short estadoGeoF;
    @Column(name = "ESTADO_GEO_G", nullable = false)
    private short estadoGeoG;
    @Column(name = "ESTADO_GEO_H", nullable = false)
    private short estadoGeoH;
    @Column(name = "ESTADO_GEO_I", nullable = false)
    private short estadoGeoI;
    @Column(name = "ESTADO_GEO_J", nullable = false)
    private short estadoGeoJ;
    @Column(name = "ESTADO_GEO_L", nullable = false)
    private short estadoGeoL;
    @Column(name = "ESTADO_GEO_M", nullable = false)
    private short estadoGeoM;
    @Column(name = "ESTADO_GEO_N", nullable = false)
    private short estadoGeoN;
    @Column(name = "ESTADO_GEO_R", nullable = false)
    private short estadoGeoR;
    @Column(name = "ESTADO_GEO_W", nullable = false)
    private short estadoGeoW;
    @Column(name = "ESTADO_GEO_X", nullable = false)
    private short estadoGeoX;
    @Column(name = "ESTADO_GEO_Y", nullable = false)
    private short estadoGeoY;
    @Column(name = "ESTADO_GEO_Z", nullable = false)
    private short estadoGeoZ;
    @Column(name = "PVAL_GEO_90", nullable = false)
    private short porcentajeValGeo90;
    @Column(name = "PVAL_GEO_95", nullable = false)
    private short porcentajevalGeo95;
    @Column(name = "PVAL_GEO_100", nullable = false)
    private short porcentajevalGeo100;
    @JoinColumn(name = "GPO_ID_CIUDAD", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GeograficoPoliticoMgl gpoId;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP) 
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP) 
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = false)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;

    public ConfCreahppAuto() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public short getEstadoGeoA() {
        return estadoGeoA;
    }

    public void setEstadoGeoA(short estadoGeoA) {
        this.estadoGeoA = estadoGeoA;
    }

    public short getEstadoGeoB() {
        return estadoGeoB;
    }

    public void setEstadoGeoB(short estadoGeoB) {
        this.estadoGeoB = estadoGeoB;
    }

    public short getEstadoGeoC() {
        return estadoGeoC;
    }

    public void setEstadoGeoC(short estadoGeoC) {
        this.estadoGeoC = estadoGeoC;
    }

    public short getEstadoGeoD() {
        return estadoGeoD;
    }

    public void setEstadoGeoD(short estadoGeoD) {
        this.estadoGeoD = estadoGeoD;
    }

    public short getEstadoGeoE() {
        return estadoGeoE;
    }

    public void setEstadoGeoE(short estadoGeoE) {
        this.estadoGeoE = estadoGeoE;
    }

    public short getEstadoGeoF() {
        return estadoGeoF;
    }

    public void setEstadoGeoF(short estadoGeoF) {
        this.estadoGeoF = estadoGeoF;
    }

    public short getEstadoGeoG() {
        return estadoGeoG;
    }

    public void setEstadoGeoG(short estadoGeoG) {
        this.estadoGeoG = estadoGeoG;
    }

    public short getEstadoGeoH() {
        return estadoGeoH;
    }

    public void setEstadoGeoH(short estadoGeoH) {
        this.estadoGeoH = estadoGeoH;
    }

    public short getEstadoGeoI() {
        return estadoGeoI;
    }

    public void setEstadoGeoI(short estadoGeoI) {
        this.estadoGeoI = estadoGeoI;
    }

    public short getEstadoGeoJ() {
        return estadoGeoJ;
    }

    public void setEstadoGeoJ(short estadoGeoJ) {
        this.estadoGeoJ = estadoGeoJ;
    }

    public short getEstadoGeoL() {
        return estadoGeoL;
    }

    public void setEstadoGeoL(short estadoGeoL) {
        this.estadoGeoL = estadoGeoL;
    }

    public short getEstadoGeoM() {
        return estadoGeoM;
    }

    public void setEstadoGeoM(short estadoGeoM) {
        this.estadoGeoM = estadoGeoM;
    }

    public short getEstadoGeoN() {
        return estadoGeoN;
    }

    public void setEstadoGeoN(short estadoGeoN) {
        this.estadoGeoN = estadoGeoN;
    }

    public short getEstadoGeoR() {
        return estadoGeoR;
    }

    public void setEstadoGeoR(short estadoGeoR) {
        this.estadoGeoR = estadoGeoR;
    }

    public short getEstadoGeoW() {
        return estadoGeoW;
    }

    public void setEstadoGeoW(short estadoGeoW) {
        this.estadoGeoW = estadoGeoW;
    }

    public short getEstadoGeoX() {
        return estadoGeoX;
    }

    public void setEstadoGeoX(short estadoGeoX) {
        this.estadoGeoX = estadoGeoX;
    }

    public short getEstadoGeoY() {
        return estadoGeoY;
    }

    public void setEstadoGeoY(short estadoGeoY) {
        this.estadoGeoY = estadoGeoY;
    }

    public short getEstadoGeoZ() {
        return estadoGeoZ;
    }

    public void setEstadoGeoZ(short estadoGeoZ) {
        this.estadoGeoZ = estadoGeoZ;
    }

    public short getPorcentajeValGeo90() {
        return porcentajeValGeo90;
    }

    public void setPorcentajeValGeo90(short porcentajeValGeo90) {
        this.porcentajeValGeo90 = porcentajeValGeo90;
    }

    public short getPorcentajevalGeo95() {
        return porcentajevalGeo95;
    }

    public void setPorcentajevalGeo95(short porcentajevalGeo95) {
        this.porcentajevalGeo95 = porcentajevalGeo95;
    }

    public short getPorcentajevalGeo100() {
        return porcentajevalGeo100;
    }

    public void setPorcentajevalGeo100(short porcentajevalGeo100) {
        this.porcentajevalGeo100 = porcentajevalGeo100;
    }

    public GeograficoPoliticoMgl getGpoId() {
        return gpoId;
    }

    public void setGpoId(GeograficoPoliticoMgl gpoId) {
        this.gpoId = gpoId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
    
    

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.DirCreahhppautoConf[ chaId=" + id + " ]";
    }

    public String getAllEstadosStr() {
        String estadosStr = "";
        if (estadoGeoA == 1) {
            estadosStr += "A";
        }
        if (estadoGeoB == 1) {
            estadosStr += "B";
        }
        if (estadoGeoC == 1) {
            estadosStr += "C";
        }
        if (estadoGeoD == 1) {
            estadosStr += "D";
        }
        if (estadoGeoE == 1) {
            estadosStr += "E";
        }
        if (estadoGeoF == 1) {
            estadosStr += "F";
        }
        if (estadoGeoG == 1) {
            estadosStr += "G";
        }
        if (estadoGeoH == 1) {
            estadosStr += "H";
        }
        if (estadoGeoI == 1) {
            estadosStr += "I";
        }
        if (estadoGeoJ == 1) {
            estadosStr += "J";
        }
        if (estadoGeoL == 1) {
            estadosStr += "L";
        }
        if (estadoGeoM == 1) {
            estadosStr += "M";
        }
        if (estadoGeoN == 1) {
            estadosStr += "N";
        }
        if (estadoGeoR == 1) {
            estadosStr += "R";
        }
        if (estadoGeoW == 1) {
            estadosStr += "W";
        }
        if (estadoGeoX == 1) {
            estadosStr += "X";
        }
        if (estadoGeoY == 1) {
            estadosStr += "Y";
        }
        if (estadoGeoZ == 1) {
            estadosStr += "Z";
        }
        return estadosStr;
    }
 
}
