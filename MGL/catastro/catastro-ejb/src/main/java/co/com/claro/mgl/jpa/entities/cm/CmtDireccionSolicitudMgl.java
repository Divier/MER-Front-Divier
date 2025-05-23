/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_DIRECCION_SOLICITUD
 *
 * @author yimy diaz
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_DIRECCION_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtDireccionSolicitudMgl.findAll",
            query = "SELECT d FROM CmtDireccionSolicitudMgl d ")})
public class CmtDireccionSolicitudMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtDireccionSolicitudMgl.cmtDireccionSolicitudMglSq",
            sequenceName = "MGL_SCHEME.CMT_DIRECCION_SOLICITUD_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtDireccionSolicitudMgl.cmtDireccionSolicitudMglSq")
    @Column(name = "DIRECCION_SOL_ID", nullable = false)
    private BigDecimal direccionSolId;
    @Column(name = "COD_TIPO_DIRECCION")
    private String codTipoDir;
    @Column(name = "TIPO_DIRECCION_ID")
    private BigDecimal tdiId;
    @Column(name = "DIVISION")
    private String division;
    @Column(name = "COMUNIDAD")
    private String comunidad;
    @Column(name = "GEOGRAFICO_PO_ID_DEPARTAMENTO")
    private BigDecimal gpoIdDepartamento;
    @Column(name = "GEOGRAFICO_PO_ID_MUNICIPIO")
    private BigDecimal gpoIdMunicipio;
    @Column(name = "GEOGRAFICO_PO_ID_POBLADO")
    private BigDecimal gpoIdCentroPoblado;
    @Column(name = "DIRECCION")
    private String direccion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_ID")
    private DireccionMgl direccionMgl;
    @Column(name = "BARRIO")
    private String barrio;
    @Column(name = "TIPO_VIA_PRINCIPAL")
    private String tipoViaPrincipal;
    @Column(name = "NUM_VIA_PRINCIPAL")
    private String numViaPrincipal;
    @Column(name = "LT_VIA_PRINCIPAL")
    private String ltViaPrincipal;
    @Column(name = "NL_POST_VIA_P")
    private String nlPostViaP;
    @Column(name = "BIS_VIA_PRINCIPAL")
    private String bisViaPrincipal;
    @Column(name = "CUAD_VIA_PRINCIPAL")
    private String cuadViaPrincipal;
    @Column(name = "TIPO_VIA_GENERADORA")
    private String tipoViaGeneradora;
    @Column(name = "NUM_VIA_GENERADORA")
    private String numViaGeneradora;
    @Column(name = "LT_VIA_GENERADORA")
    private String ltViaGeneradora;
    @Column(name = "NL_POST_VIA_G")
    private String nlPostViaG;
    @Column(name = "LETRA3G")
    private String letra3G;
    @Column(name = "BIS_VIA_GENERADORA")
    private String bisViaGeneradora;
    @Column(name = "CUAD_VIA_GENERADORA")
    private String cuadViaGeneradora;
    @Column(name = "PLACA_DIRECCION")
    private String placaDireccion;
    @Column(name = "CP_TIPO_NIVEL1")
    private String cpTipoNivel1;
    @Column(name = "CP_TIPO_NIVEL2")
    private String cpTipoNivel2;
    @Column(name = "CP_TIPO_NIVEL3")
    private String cpTipoNivel3;
    @Column(name = "CP_TIPO_NIVEL4")
    private String cpTipoNivel4;
    @Column(name = "CP_TIPO_NIVEL5")
    private String cpTipoNivel5;
    @Column(name = "CP_TIPO_NIVEL6")
    private String cpTipoNivel6;
    @Column(name = "CP_VALOR_NIVEL1")
    private String cpValorNivel1;
    @Column(name = "CP_VALOR_NIVEL2")
    private String cpValorNivel2;
    @Column(name = "CP_VALOR_NIVEL3")
    private String cpValorNivel3;
    @Column(name = "CP_VALOR_NIVEL4")
    private String cpValorNivel4;
    @Column(name = "CP_VALOR_NIVEL5")
    private String cpValorNivel5;
    @Column(name = "CP_VALOR_NIVEL6")
    private String cpValorNivel6;
    @Column(name = "MZ_TIPO_NIVEL1")
    private String mzTipoNivel1;
    @Column(name = "MZ_TIPO_NIVEL2")
    private String mzTipoNivel2;
    @Column(name = "MZ_TIPO_NIVEL3")
    private String mzTipoNivel3;
    @Column(name = "MZ_TIPO_NIVEL4")
    private String mzTipoNivel4;
    @Column(name = "MZ_TIPO_NIVEL5")
    private String mzTipoNivel5;
    @Column(name = "MZ_TIPO_NIVEL6")
    private String mzTipoNivel6;
    @Column(name = "MZ_VALOR_NIVEL1")
    private String mzValorNivel1;
    @Column(name = "MZ_VALOR_NIVEL2")
    private String mzValorNivel2;
    @Column(name = "MZ_VALOR_NIVEL3")
    private String mzValorNivel3;
    @Column(name = "MZ_VALOR_NIVEL4")
    private String mzValorNivel4;
    @Column(name = "MZ_VALOR_NIVEL5_ID")
    private String mzValorNivel5;
    @Column(name = "MZ_VALOR_NIVEL6")
    private String mzValorNivel6;
    @Column(name = "IT_TIPO_PLACA")
    private String itTipoPlaca;
    @Column(name = "IT_VALOR_PLACA")
    private String itValorPlaca;
    @Column(name = "ESTRATO")
    private BigDecimal estrato;
    @Column(name = "ESTADO_DIR_GEO")
    private String estadoDirGeo;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_CCMM_ID")
    private CmtDireccionMgl cmtDirObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID")
    private CmtSolicitudCmMgl solicitudCMObj;
    @Column(name = "DIR_FORMATO_IGAC")
    private String dirFormatoIgac;
    @Column(name = "CALLE_RR", nullable = false, length = 50)
    private String calleRr;
    @Column(name = "UNIDAD_RR", nullable = false, length = 10)
    private String unidadRr;
    @OneToOne(mappedBy = "direccionSolicitudObj", fetch = FetchType.LAZY)
    private CmtSolicitudSubEdificioMgl solicitudSubEdificioObj;
    @Column(name = "DIRECCION_ANTIGUA")
    private String direccionAntigua;

    @Transient
    private BigDecimal idDirCatastro;
    @Transient
    private boolean seleccionado;
    @Transient
    private boolean solicitudModificacion;
    @Transient
    private boolean validado;
    @Transient
    private boolean mostrarLinkOtraDireccion = false;
    @Transient
    private boolean otroBarrio = false;

    public void mapearCamposDrDireccion(DrDireccion dir) {

        this.setCodTipoDir(dir.getIdTipoDireccion());
        if (dir.getBarrio() != null && !dir.getBarrio().isEmpty()) {
            this.setBarrio(dir.getBarrio().toUpperCase());
        }
        this.setTipoViaPrincipal(dir.getTipoViaPrincipal());
        this.setNumViaPrincipal(dir.getNumViaPrincipal());
        this.setLtViaPrincipal(dir.getLtViaPrincipal());
        this.setNlPostViaP(dir.getNlPostViaP());
        this.setBisViaPrincipal(dir.getBisViaPrincipal());
        this.setCuadViaPrincipal(dir.getCuadViaPrincipal());
        this.setTipoViaGeneradora(dir.getTipoViaGeneradora());
        this.setNumViaGeneradora(dir.getNumViaGeneradora());
        this.setLtViaGeneradora(dir.getLtViaGeneradora());
        this.setNlPostViaG(dir.getNlPostViaG());
        this.setBisViaGeneradora(dir.getBisViaGeneradora());
        this.setCuadViaGeneradora(dir.getCuadViaGeneradora());
        this.setPlacaDireccion(dir.getPlacaDireccion());
        this.setCpTipoNivel1(dir.getCpTipoNivel1());
        this.setCpTipoNivel2(dir.getCpTipoNivel2());
        this.setCpTipoNivel3(dir.getCpTipoNivel3());
        this.setCpTipoNivel4(dir.getCpTipoNivel4());
        this.setCpTipoNivel5(dir.getCpTipoNivel5());
        this.setCpTipoNivel6(dir.getCpTipoNivel6());
        this.setCpValorNivel1(dir.getCpValorNivel1());
        this.setCpValorNivel2(dir.getCpValorNivel2());
        this.setCpValorNivel3(dir.getCpValorNivel3());
        this.setCpValorNivel4(dir.getCpValorNivel4());
        this.setCpValorNivel5(dir.getCpValorNivel5());
        this.setCpValorNivel6(dir.getCpValorNivel6());
        this.setMzTipoNivel1(dir.getMzTipoNivel1());
        this.setMzTipoNivel2(dir.getMzTipoNivel2());
        this.setMzTipoNivel3(dir.getMzTipoNivel3());
        this.setMzTipoNivel4(dir.getMzTipoNivel4());
        this.setMzTipoNivel5(dir.getMzTipoNivel5());
        this.setMzTipoNivel6(dir.getMzTipoNivel6());
        this.setMzValorNivel1(dir.getMzValorNivel1());
        this.setMzValorNivel2(dir.getMzValorNivel2());
        this.setMzValorNivel3(dir.getMzValorNivel3());
        this.setMzValorNivel4(dir.getMzValorNivel4());
        this.setMzValorNivel5(dir.getMzValorNivel5());
        this.setMzValorNivel6(dir.getMzValorNivel6());
        this.setEstadoDirGeo(dir.getEstadoDirGeo());
        this.setItTipoPlaca(dir.getItTipoPlaca());
        this.setItValorPlaca(dir.getItValorPlaca());

    }

    public DrDireccion getCamposDrDireccion() {
        DrDireccion dir = new DrDireccion();
        dir.setIdTipoDireccion(this.getCodTipoDir());
        dir.setBarrio(this.getBarrio());
        dir.setTipoViaPrincipal(this.getTipoViaPrincipal());
        dir.setNumViaPrincipal(this.getNumViaPrincipal());
        dir.setLtViaPrincipal(this.getLtViaPrincipal());
        dir.setNlPostViaP(this.getNlPostViaP());
        dir.setBisViaPrincipal(this.getBisViaPrincipal());
        dir.setCuadViaPrincipal(this.getCuadViaPrincipal());
        dir.setTipoViaGeneradora(this.getTipoViaGeneradora());
        dir.setNumViaGeneradora(this.getNumViaGeneradora());
        dir.setLtViaGeneradora(this.getLtViaGeneradora());
        dir.setNlPostViaG(this.getNlPostViaG());
        dir.setBisViaGeneradora(this.getBisViaGeneradora());
        dir.setCuadViaGeneradora(this.getCuadViaGeneradora());
        dir.setPlacaDireccion(this.getPlacaDireccion());
        dir.setCpTipoNivel1(this.getCpTipoNivel1());
        dir.setCpTipoNivel2(this.getCpTipoNivel2());
        dir.setCpTipoNivel3(this.getCpTipoNivel3());
        dir.setCpTipoNivel4(this.getCpTipoNivel4());
        dir.setCpTipoNivel5(this.getCpTipoNivel5());
        dir.setCpTipoNivel6(this.getCpTipoNivel6());
        dir.setCpValorNivel1(this.getCpValorNivel1());
        dir.setCpValorNivel2(this.getCpValorNivel2());
        dir.setCpValorNivel3(this.getCpValorNivel3());
        dir.setCpValorNivel4(this.getCpValorNivel4());
        dir.setCpValorNivel5(this.getCpValorNivel5());
        dir.setCpValorNivel6(this.getCpValorNivel6());
        dir.setMzTipoNivel1(this.getMzTipoNivel1());
        dir.setMzTipoNivel2(this.getMzTipoNivel2());
        dir.setMzTipoNivel3(this.getMzTipoNivel3());
        dir.setMzTipoNivel4(this.getMzTipoNivel4());
        dir.setMzTipoNivel5(this.getMzTipoNivel5());
        dir.setMzTipoNivel6(this.getMzTipoNivel6());
        dir.setMzValorNivel1(this.getMzValorNivel1());
        dir.setMzValorNivel2(this.getMzValorNivel2());
        dir.setMzValorNivel3(this.getMzValorNivel3());
        dir.setMzValorNivel4(this.getMzValorNivel4());
        dir.setMzValorNivel5(this.getMzValorNivel5());
        dir.setMzValorNivel6(this.getMzValorNivel6());
        dir.setEstadoDirGeo(this.getEstadoDirGeo());
        dir.setItTipoPlaca(this.getItTipoPlaca());
        dir.setItValorPlaca(this.getItValorPlaca());
        return dir;
    }

    public CmtDireccionMgl mapearCamposCmtDireccionMgl() {
        CmtDireccionMgl dirMgl = new CmtDireccionMgl();
        dirMgl.setCodTipoDir(this.getCodTipoDir());
        dirMgl.setBarrio(this.getBarrio());
        dirMgl.setTipoViaPrincipal(this.getTipoViaPrincipal());
        dirMgl.setNumViaPrincipal(this.getNumViaPrincipal());
        dirMgl.setLtViaPrincipal(this.getLtViaPrincipal());
        dirMgl.setNlPostViaP(this.getNlPostViaP());
        dirMgl.setBisViaPrincipal(this.getBisViaPrincipal());
        dirMgl.setCuadViaPrincipal(this.getCuadViaPrincipal());
        dirMgl.setTipoViaGeneradora(this.getTipoViaGeneradora());
        dirMgl.setNumViaGeneradora(this.getNumViaGeneradora());
        dirMgl.setLtViaGeneradora(this.getLtViaGeneradora());
        dirMgl.setNlPostViaG(this.getNlPostViaG());
        dirMgl.setBisViaGeneradora(this.getBisViaGeneradora());
        dirMgl.setCuadViaGeneradora(this.getCuadViaGeneradora());
        dirMgl.setPlacaDireccion(this.getPlacaDireccion());
        dirMgl.setCpTipoNivel1(this.getCpTipoNivel1());
        dirMgl.setCpTipoNivel2(this.getCpTipoNivel2());
        dirMgl.setCpTipoNivel3(this.getCpTipoNivel3());
        dirMgl.setCpTipoNivel4(this.getCpTipoNivel4());
        dirMgl.setCpTipoNivel5(this.getCpTipoNivel5());
        dirMgl.setCpTipoNivel6(this.getCpTipoNivel6());
        dirMgl.setCpValorNivel1(this.getCpValorNivel1());
        dirMgl.setCpValorNivel2(this.getCpValorNivel2());
        dirMgl.setCpValorNivel3(this.getCpValorNivel3());
        dirMgl.setCpValorNivel4(this.getCpValorNivel4());
        dirMgl.setCpValorNivel5(this.getCpValorNivel5());
        dirMgl.setCpValorNivel6(this.getCpValorNivel6());
        dirMgl.setMzTipoNivel1(this.getMzTipoNivel1());
        dirMgl.setMzTipoNivel2(this.getMzTipoNivel2());
        dirMgl.setMzTipoNivel3(this.getMzTipoNivel3());
        dirMgl.setMzTipoNivel4(this.getMzTipoNivel4());
        dirMgl.setMzTipoNivel5(this.getMzTipoNivel5());
        dirMgl.setMzTipoNivel6(this.getMzTipoNivel6());
        dirMgl.setMzValorNivel1(this.getMzValorNivel1());
        dirMgl.setMzValorNivel2(this.getMzValorNivel2());
        dirMgl.setMzValorNivel3(this.getMzValorNivel3());
        dirMgl.setMzValorNivel4(this.getMzValorNivel4());
        dirMgl.setMzValorNivel5(this.getMzValorNivel5());
        dirMgl.setMzValorNivel6(this.getMzValorNivel6());
        dirMgl.setEstadoDirGeo(this.getEstadoDirGeo());
        dirMgl.setItTipoPlaca(this.getItTipoPlaca());
        dirMgl.setItValorPlaca(this.getItValorPlaca());
        return dirMgl;
    }

    public boolean existeSolDirEliminar(List<CmtDireccionSolicitudMgl> list) {
        for (CmtDireccionSolicitudMgl sol : list) {
            if (sol.getCmtDirObj().getDireccionId().equals(this.getCmtDirObj().getDireccionId())) {
                return true;
            }
        }
        return false;
    }

    public List<CmtDireccionSolicitudMgl> removerSolicitudDireccion(List<CmtDireccionSolicitudMgl> list) {
        CmtDireccionSolicitudMgl cmtSolDir;
        for (int i = 0; i < list.size(); i++) {
            cmtSolDir = list.get(i);
            if (cmtSolDir.getCmtDirObj().getDireccionId().equals(this.getCmtDirObj().getDireccionId())) {
                list.remove(i);
            }
        }
        return list;
    }

    public String mostrarOtrobarrio() {
        otroBarrio = !otroBarrio;
        return null;
    }

    public BigDecimal getDireccionSolId() {
        return direccionSolId;
    }

    public void setDireccionSolId(BigDecimal direccionSolId) {
        this.direccionSolId = direccionSolId;
    }

    public String getCodTipoDir() {
        return codTipoDir;
    }

    public void setCodTipoDir(String codTipoDir) {
        this.codTipoDir = codTipoDir;
    }

    public BigDecimal getGpoIdDepartamento() {
        return gpoIdDepartamento;
    }

    public void setGpoIdDepartamento(BigDecimal gpoIdDepartamento) {
        this.gpoIdDepartamento = gpoIdDepartamento;
    }

    public BigDecimal getGpoIdMunicipio() {
        return gpoIdMunicipio;
    }

    public void setGpoIdMunicipio(BigDecimal gpoIdMunicipio) {
        this.gpoIdMunicipio = gpoIdMunicipio;
    }

    public BigDecimal getGpoIdCentroPoblado() {
        return gpoIdCentroPoblado;
    }

    public void setGpoIdCentroPoblado(BigDecimal gpoIdCentroPoblado) {
        this.gpoIdCentroPoblado = gpoIdCentroPoblado;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getCuadViaGeneradora() {
        return cuadViaGeneradora;
    }

    public void setCuadViaGeneradora(String cuadViaGeneradora) {
        this.cuadViaGeneradora = cuadViaGeneradora;
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

    public String getMzTipoNivel6() {
        return mzTipoNivel6;
    }

    public void setMzTipoNivel6(String mzTipoNivel6) {
        this.mzTipoNivel6 = mzTipoNivel6;
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

    public BigDecimal getEstrato() {
        return estrato;
    }

    public void setEstrato(BigDecimal estrato) {
        this.estrato = estrato;
    }

    public String getBisViaGeneradora() {
        return bisViaGeneradora;
    }

    public void setBisViaGeneradora(String bisViaGeneradora) {
        this.bisViaGeneradora = bisViaGeneradora;
    }

    public String getPlacaDireccion() {
        return placaDireccion;
    }

    public void setPlacaDireccion(String placaDireccion) {
        this.placaDireccion = placaDireccion;
    }

    public BigDecimal getTdiId() {
        return tdiId;
    }

    public void setTdiId(BigDecimal tdiId) {
        this.tdiId = tdiId;
    }

    public String getLetra3G() {
        return letra3G;
    }

    public void setLetra3G(String letra3G) {
        this.letra3G = letra3G;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public BigDecimal getIdDirCatastro() {
        return idDirCatastro;
    }

    public void setIdDirCatastro(BigDecimal idDirCatastro) {
        this.idDirCatastro = idDirCatastro;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
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

    public String getEstadoDirGeo() {
        return estadoDirGeo;
    }

    public void setEstadoDirGeo(String estadoDirGeo) {
        this.estadoDirGeo = estadoDirGeo;
    }

    public boolean isSolicitudModificacion() {
        return solicitudModificacion;
    }

    public void setSolicitudModificacion(boolean solicitudModificacion) {
        this.solicitudModificacion = solicitudModificacion;
    }

    public CmtSolicitudCmMgl getSolicitudCMObj() {
        return solicitudCMObj;
    }

    public void setSolicitudCMObj(CmtSolicitudCmMgl solicitudCMObj) {
        this.solicitudCMObj = solicitudCMObj;
    }

    public CmtDireccionMgl getCmtDirObj() {
        return cmtDirObj;
    }

    public void setCmtDirObj(CmtDireccionMgl cmtDirObj) {
        this.cmtDirObj = cmtDirObj;
    }

    public String getDirFormatoIgac() {
        return dirFormatoIgac;
    }

    public void setDirFormatoIgac(String dirFormatoIgac) {
        this.dirFormatoIgac = dirFormatoIgac;
    }

    public String getCalleRr() {
        return calleRr;
    }

    public void setCalleRr(String calleRr) {
        this.calleRr = calleRr;
    }

    public String getUnidadRr() {
        return unidadRr;
    }

    public void setUnidadRr(String unidadRr) {
        this.unidadRr = unidadRr;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public CmtSolicitudSubEdificioMgl getSolicitudSubEdificioObj() {
        return solicitudSubEdificioObj;
    }

    public void setSolicitudSubEdificioObj(CmtSolicitudSubEdificioMgl solicitudSubEdificioObj) {
        this.solicitudSubEdificioObj = solicitudSubEdificioObj;
    }

    public String getDireccionAntigua() {
        return direccionAntigua;
    }

    public void setDireccionAntigua(String direccionAntigua) {
        this.direccionAntigua = direccionAntigua;
    }

    public boolean isMostrarLinkOtraDireccion() {
        return mostrarLinkOtraDireccion;
    }

    public void setMostrarLinkOtraDireccion(boolean mostrarLinkOtraDireccion) {
        this.mostrarLinkOtraDireccion = mostrarLinkOtraDireccion;
    }

    public boolean isOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(boolean otroBarrio) {
        this.otroBarrio = otroBarrio;
    }

    public DireccionMgl getDireccionMgl() {
        return direccionMgl;
    }

    public void setDireccionMgl(DireccionMgl direccionMgl) {
        this.direccionMgl = direccionMgl;
    }    
    
}
