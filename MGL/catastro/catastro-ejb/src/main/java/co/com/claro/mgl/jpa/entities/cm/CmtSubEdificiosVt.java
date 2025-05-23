package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_SUBEDIFICIOS_VT
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_SUBEDIFICIOS_VT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtSubEdificiosVt.findAll", query = "SELECT c FROM CmtSubEdificiosVt c"),
    @NamedQuery(name = "CmtSubEdificiosVt.findByVt", query = "SELECT c FROM CmtSubEdificiosVt c WHERE c.vtObj = :vtObj AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtSubEdificiosVt.getCountByVt", query = "SELECT COUNT(1) FROM CmtSubEdificiosVt c WHERE c.vtObj = :vtObj AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtSubEdificiosVt.findVtByIdSubEdificioAndLastVt", query = "SELECT s FROM CmtSubEdificiosVt s JOIN s.vtObj v WHERE s.vtObj = :vtObj AND s.estadoRegistro = 1 AND v.estadoVisitaTecnica= :estadoVisitaTecnica"),
    @NamedQuery(name = "CmtSubEdificiosVt.findByIdSubEdificio", query = "SELECT c FROM CmtSubEdificiosVt c WHERE c.subEdificioObj.subEdificioId = :subEdificioId AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtSubEdificiosVt.findByVtAndAcometida", query = "SELECT c FROM CmtSubEdificiosVt c WHERE c.vtObj = :vtObj AND c.otAcometidaId IS NULL   AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtSubEdificiosVt.findByIdOtAcometida", query = "SELECT c FROM CmtSubEdificiosVt c WHERE c.otAcometidaId = :otAcometidaId  AND c.generaAcometida = 'Y'  AND c.estadoRegistro = 1")})
public class CmtSubEdificiosVt implements Serializable, Comparable<CmtSubEdificiosVt> {

    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER = LogManager.getLogger(CmtSubEdificiosVt.class);
    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CmtSubEdificiosVt.CmtSubedificiosVtSq",
            sequenceName = "MGL_SCHEME.CMT_SUBEDIFICIOS_VT_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtSubEdificiosVt.CmtSubedificiosVtSq")
    @Column(name = "SUBEDIFICIO_VT_ID", nullable = false)
    private BigDecimal idEdificioVt;
    @JoinColumn(name = "VT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtVisitaTecnicaMgl vtObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID", referencedColumnName = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subEdificioObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_NIVEL1", referencedColumnName = "BASICA_ID", nullable = true)
    private CmtBasicaMgl tipoNivel1;
    @Column(name = "VALOR_NIVEL1", nullable = true, length = 50)
    private String valorNivel1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_NIVEL2", referencedColumnName = "BASICA_ID")
    private CmtBasicaMgl tipoNivel2;
    @Column(name = "VALOR_NIVEL2", nullable = true, length = 50)
    private String valorNivel2;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_NIVEL3", referencedColumnName = "BASICA_ID")
    private CmtBasicaMgl tipoNivel3;
    @Column(name = "VALOR_NIVEL3", nullable = true, length = 50)
    private String valorNivel3;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_NIVEL4", referencedColumnName = "BASICA_ID")
    private CmtBasicaMgl tipoNivel4;
    @Column(name = "VALOR_NIVEL4", nullable = true, length = 50)
    private String valorNivel4;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_NIVEL5", referencedColumnName = "BASICA_ID")
    private CmtBasicaMgl tipoNivel5;
    @Column(name = "VALOR_NIVEL5", nullable = true, length = 50)
    private String valorNivel5;
    @Column(name = "NUMERO_PISOS_CASAS", nullable = false)
    private int numeroPisosCasas;
    @Column(name = "DIRECCION_SUBEDIFICIO", nullable = true, length = 150)
    private String direccionSubEdificio;
    @Column(name = "NOMBRAMIENTO_RR", nullable = true, length = 200)
    private String nombramientoRr;
    @Column(name = "NOMBRE_SUBEDIFICIO", nullable = true, length = 150)
    private String nombreSubEdificio;
    @Column(name = "ID_TIPO_DIRECCION")
    private String idTipoDireccion;
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
    @Column(name = "MZ_VALOR_NIVEL5")
    private String mzValorNivel5;
    @Column(name = "MZ_VALOR_NIVEL6")
    private String mzValorNivel6;
    @Column(name = "ESTADO_DIR_GEO")
    private String estadoDirGeo;
    @Column(name = "IT_TIPO_PLACA")
    private String itTipoPlaca;
    @Column(name = "CALLE_RR", nullable = true, length = 50)
    private String calleRr;
    @Column(name = "UNIDAD_RR", nullable = true, length = 10)
    private String unidadRr;
    @Column(name = "CODIGO_RR", nullable = false, length = 20)
    private String codigoRr;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false, length = 20)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 20)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @OneToMany(mappedBy = "subEdificioVtObj", fetch = FetchType.LAZY)
    private List<CmtHhppVtMgl> listTecnologiaHabilitada;
    @NotNull
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private NodoMgl nodo;
    @OneToMany(mappedBy = "cmtSubEdificiosVtObj", fetch = FetchType.LAZY)
    private List<CmtOrdenTrabajoInventarioMgl> listCmtOrdenTrabajoInventarioMgl;
    @Column(name = "OT_ACOMETIDA_ID")
    private BigDecimal otAcometidaId;
    @Column(name = "GENERAR_ACOMETIDA",length = 1)
    private String generaAcometida;
    @Transient
    private String codigoNodo;
    @Transient
    private boolean genAco;
    @Transient
    private boolean clickValidarDir = false;
    @Transient
    private String barrioDireccionSubEdi;
    @Transient
    private BigDecimal optipoNivel1;
    @Transient
    private BigDecimal optipoNivel2;
    @Transient
    private BigDecimal optipoNivel3;
    @Transient
    private BigDecimal optipoNivel4;
     @Transient
    private List<DrDireccion> listaDrdireccionSubedif;

    public void mapearCamposDrDireccion(DrDireccion dir) {

        this.setIdTipoDireccion(dir.getIdTipoDireccion());
        this.setBarrio(dir.getBarrio());
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

    }

    public void mapearCamposFromDetalleDireccionEntity(DetalleDireccionEntity dir) {
        this.setIdTipoDireccion(dir.getIdtipodireccion());
        this.setBarrio(dir.getBarrio());
        this.setTipoViaPrincipal(dir.getTipoviaprincipal());
        this.setNumViaPrincipal(dir.getNumviaprincipal());
        this.setLtViaPrincipal(dir.getLtviaprincipal());
        this.setNlPostViaP(dir.getNlpostviap());
        this.setBisViaPrincipal(dir.getBisviaprincipal());
        this.setCuadViaPrincipal(dir.getCuadviaprincipal());
        this.setTipoViaGeneradora(dir.getTipoviageneradora());
        this.setNumViaGeneradora(dir.getNumviageneradora());
        this.setLtViaGeneradora(dir.getLtviageneradora());
        this.setNlPostViaG(dir.getNlpostviag());
        this.setBisViaGeneradora(dir.getBisviageneradora());
        this.setCuadViaGeneradora(dir.getCuadviageneradora());
        this.setPlacaDireccion(dir.getPlacadireccion());
        this.setCpTipoNivel1(dir.getCptiponivel1());
        this.setCpTipoNivel2(dir.getCptiponivel2());
        this.setCpTipoNivel3(dir.getCptiponivel3());
        this.setCpTipoNivel4(dir.getCptiponivel4());
        this.setCpTipoNivel5(dir.getCptiponivel5());
        this.setCpTipoNivel6(dir.getCptiponivel6());
        this.setCpValorNivel1(dir.getCpvalornivel1());
        this.setCpValorNivel2(dir.getCpvalornivel2());
        this.setCpValorNivel3(dir.getCpvalornivel3());
        this.setCpValorNivel4(dir.getCpvalornivel4());
        this.setCpValorNivel5(dir.getCpvalornivel5());
        this.setCpValorNivel6(dir.getCpvalornivel6());
        this.setMzTipoNivel1(dir.getMztiponivel1());
        this.setMzTipoNivel2(dir.getMztiponivel2());
        this.setMzTipoNivel3(dir.getMztiponivel3());
        this.setMzTipoNivel4(dir.getMztiponivel4());
        this.setMzTipoNivel5(dir.getMztiponivel5());
        this.setMzTipoNivel6(dir.getMztiponivel6());
        this.setMzValorNivel1(dir.getMzvalornivel1());
        this.setMzValorNivel2(dir.getMzvalornivel2());
        this.setMzValorNivel3(dir.getMzvalornivel3());
        this.setMzValorNivel4(dir.getMzvalornivel4());
        this.setMzValorNivel5(dir.getMzvalornivel5());
        this.setMzValorNivel6(dir.getMzvalornivel6());
        this.setEstadoDirGeo(dir.getEstadoDir());
        this.setItTipoPlaca(dir.getItTipoPlaca());
    }

    public CmtSubEdificioMgl mapearCamposCmtSubEdificioMgl(
            CmtSubEdificiosVt subEdiVt, CmtBasicaMgl estadoSubEdi)
            throws ApplicationException {
        
        try {
            CmtSubEdificioMgl subEdiMgl = new CmtSubEdificioMgl();

            if (subEdiVt.getSubEdificioObj() != null
                    && subEdiVt.getSubEdificioObj().getSubEdificioId() != null) {
                subEdiMgl = subEdiVt.getSubEdificioObj();
                subEdiMgl.setNodoObj(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ?
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getNodoObj(): null);
                subEdiMgl.setTipoEdificioObj(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getTipoEdificioObj(): null);
                if (subEdiVt.getVtObj().getAlimentElectObj() != null) {
                    subEdiMgl.setConexionCorriente(subEdiVt.getVtObj().getAlimentElectObj().getDescripcion());
                }
                if (!subEdiVt.getVtObj().getOtObj().getCmObj().isUnicoSubEdificioBoolean()) {
                    subEdiMgl.setEstadoSubEdificioObj(estadoSubEdi);
                    subEdiMgl.setNombreSubedificio(subEdiVt.getNombreSubEdificio());
                    if (subEdiVt.getDireccionSubEdificio() != null
                            && !subEdiVt.getDireccionSubEdificio().trim().isEmpty()) {
                        subEdiMgl.setDireccion(subEdiVt.getDireccionSubEdificio());
                    }
                }
                if (subEdiVt.getVtObj().getEstratoObj() != null
                        && subEdiVt.getVtObj().getEstratoObj().getBasicaId() != null
                        && (subEdiVt.getVtObj().getEstratoObj().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                    subEdiMgl.setEstrato(subEdiVt.getVtObj().getEstratoObj());
                } else {
                    subEdiMgl.setEstrato(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                            subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstrato(): null);
                }
                subEdiMgl.setTelefonoPorteria(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getTelefonoPorteria() : "NA");
                subEdiMgl.setTelefonoPorteria2(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getTelefonoPorteria2(): "NA");
                subEdiMgl.setTipoProyectoObj(subEdiVt.getVtObj().getTipoProyecto());
                subEdiMgl.setCompaniaConstructoraObj(subEdiVt.getVtObj().getCompaniaConstructora());
                subEdiMgl.setCompaniaAdministracionObj(subEdiVt.getVtObj().getAdministracionObj());
                subEdiMgl.setTipoAcometidaObj(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getTipoAcometidaObj(): null);
                subEdiMgl.setTipoProyectoObj(subEdiVt.getVtObj().getTipoProyecto());
                subEdiMgl.setOrigenDatosObj(subEdiVt.getVtObj().getOrigenDatos());
                subEdiMgl.setPisos(subEdiVt.getNumeroPisosCasas());
                subEdiMgl.setReDiseno("N");
                subEdiMgl.setVisitaTecnica("N");
                subEdiMgl.setConexionCorriente("N");
                subEdiMgl.setReDiseno("N");
                subEdiMgl.setTipoLoc("ND");//TODO: 
                subEdiMgl.setHeadEnd("01");//TODO:
                subEdiMgl.setCierre("N");
                subEdiMgl.setPlanos("N");
            } else {
                subEdiMgl.setNodoObj(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getNodoObj(): null);
                subEdiMgl.setTipoEdificioObj(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getTipoEdificioObj());
                if (subEdiVt.getVtObj().getAlimentElectObj() != null) {
                    subEdiMgl.setConexionCorriente(subEdiVt.getVtObj().getAlimentElectObj().getDescripcion());
                }
                subEdiMgl.setTipoProyectoObj(subEdiVt.getVtObj().getTipoProyecto());
                subEdiMgl.setOrigenDatosObj(subEdiVt.getVtObj().getOrigenDatos());
                subEdiMgl.setReDiseno("N");
                subEdiMgl.setVisitaTecnica("N");
                subEdiMgl.setConexionCorriente("N");
                subEdiMgl.setReDiseno("N");
                subEdiMgl.setTipoLoc("ND");//TODO: 
                subEdiMgl.setHeadEnd("01");//TODO:
                subEdiMgl.setEstadoSubEdificioObj(estadoSubEdi);
                subEdiMgl.setCierre("N");
                subEdiMgl.setCuentaMatrizObj(subEdiVt.getVtObj().getOtObj().getCmObj());
                if (subEdiVt.getVtObj().getEstratoObj() != null
                        && subEdiVt.getVtObj().getEstratoObj().getBasicaId() != null
                        && (subEdiVt.getVtObj().getEstratoObj().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                    subEdiMgl.setEstrato(subEdiVt.getVtObj().getEstratoObj());
                } else {
                    subEdiMgl.setEstrato(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstrato());
                }
                subEdiMgl.setPlanos("N");
                subEdiMgl.setTelefonoPorteria(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getTelefonoPorteria() : "NA");
                subEdiMgl.setTelefonoPorteria2(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getTelefonoPorteria2(): "NA");
                subEdiMgl.setTipoProyectoObj(subEdiVt.getVtObj().getTipoProyecto());

                subEdiMgl.setCompaniaConstructoraObj(subEdiVt.getVtObj().getCompaniaConstructora());
                subEdiMgl.setCompaniaAdministracionObj(subEdiVt.getVtObj().getAdministracionObj());
                subEdiMgl.setTipoAcometidaObj(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getTipoAcometidaObj(): null);
                subEdiMgl.setCodigoRr("0");
                subEdiMgl.setPisos(subEdiVt.getNumeroPisosCasas());
                subEdiMgl.setAdministrador(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null ? 
                        subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getAdministrador(): null);
                subEdiMgl.setNombreSubedificio(subEdiVt.getNombreSubEdificio());
                CmtSubEdificioMgl subEdif = new CmtSubEdificioMgl();
                subEdif.setSubEdificioId(null);

                if (subEdiVt.getDireccionSubEdificio() != null
                        && !subEdiVt.getDireccionSubEdificio().trim().isEmpty()) {
                    subEdiMgl.setDireccion(subEdiVt.getDireccionSubEdificio());
                }
            }

            return subEdiMgl;
            
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de mapear los campos del SubEdificio: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            String msg = "Error al momento de mapear los campos del SubEdificio: " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }

    }

    public CmtDireccionMgl mapearCamposCmtDireccion(CmtSubEdificiosVt subEdiVt) throws ApplicationException {

        try {
            CmtDireccionMgl dirSubEdi;

            if (subEdiVt.subEdificioObj == null) {
                dirSubEdi = new CmtDireccionMgl();
                dirSubEdi.setBarrio(subEdiVt.getBarrio());
                dirSubEdi.setTipoViaPrincipal(subEdiVt.getTipoViaPrincipal());
                dirSubEdi.setNumViaPrincipal(subEdiVt.getNumViaPrincipal());
                dirSubEdi.setLtViaPrincipal(subEdiVt.getLtViaPrincipal());
                dirSubEdi.setNlPostViaP(subEdiVt.getNlPostViaP());
                dirSubEdi.setBisViaPrincipal(subEdiVt.getNlPostViaP());
                dirSubEdi.setCuadViaPrincipal(subEdiVt.getCuadViaPrincipal());
                dirSubEdi.setTipoViaGeneradora(subEdiVt.getTipoViaGeneradora());
                dirSubEdi.setNumViaGeneradora(subEdiVt.getNumViaGeneradora());
                dirSubEdi.setLtViaGeneradora(subEdiVt.getLtViaGeneradora());
                dirSubEdi.setNlPostViaG(subEdiVt.getNlPostViaG());
                dirSubEdi.setBisViaGeneradora(subEdiVt.getBisViaGeneradora());
                dirSubEdi.setCuadViaGeneradora(subEdiVt.getCuadViaGeneradora());
                dirSubEdi.setPlacaDireccion(subEdiVt.getPlacaDireccion());
                dirSubEdi.setCodTipoDir(subEdiVt.getIdTipoDireccion());
                if (subEdiVt.getTipoNivel1() != null) {
                    dirSubEdi.setCpTipoNivel1(subEdiVt.getTipoNivel1().getNombreBasica());
                }
                if (subEdiVt.getTipoNivel2() != null) {
                    dirSubEdi.setCpTipoNivel2(subEdiVt.getTipoNivel2().getNombreBasica());
                }
                if (subEdiVt.getTipoNivel3() != null) {
                    dirSubEdi.setCpTipoNivel3(subEdiVt.getTipoNivel3().getNombreBasica());
                }
                if (subEdiVt.getTipoNivel4() != null) {
                    dirSubEdi.setCpTipoNivel4(subEdiVt.getTipoNivel4().getNombreBasica());
                }
                if (subEdiVt.getTipoNivel5() != null) {
                    dirSubEdi.setCpTipoNivel5(subEdiVt.getTipoNivel5().getNombreBasica());
                }
                if (subEdiVt.getCpTipoNivel6() != null) {
                    dirSubEdi.setCpTipoNivel6(subEdiVt.getCpTipoNivel6());
                }
                dirSubEdi.setCpValorNivel1(subEdiVt.getValorNivel1());
                dirSubEdi.setCpValorNivel2(subEdiVt.getValorNivel2());
                dirSubEdi.setCpValorNivel3(subEdiVt.getValorNivel3());
                dirSubEdi.setCpValorNivel4(subEdiVt.getValorNivel4());
                dirSubEdi.setCpValorNivel5(subEdiVt.getCpValorNivel5());
                dirSubEdi.setCpValorNivel6(subEdiVt.getCpValorNivel6());
                dirSubEdi.setMzTipoNivel1(subEdiVt.getMzTipoNivel1());
                dirSubEdi.setMzTipoNivel2(subEdiVt.getMzTipoNivel2());
                dirSubEdi.setMzTipoNivel3(subEdiVt.getMzTipoNivel3());
                dirSubEdi.setMzTipoNivel4(subEdiVt.getMzTipoNivel4());
                dirSubEdi.setMzTipoNivel5(subEdiVt.getMzTipoNivel5());
                dirSubEdi.setMzTipoNivel6(subEdiVt.getMzTipoNivel6());
                dirSubEdi.setMzValorNivel1(subEdiVt.getMzValorNivel1());
                dirSubEdi.setMzValorNivel2(subEdiVt.getMzValorNivel2());
                dirSubEdi.setMzValorNivel3(subEdiVt.getMzValorNivel3());
                dirSubEdi.setMzValorNivel4(subEdiVt.getMzValorNivel4());
                dirSubEdi.setMzValorNivel5(subEdiVt.getMzValorNivel5());
                dirSubEdi.setMzValorNivel6(subEdiVt.getMzValorNivel6());
                dirSubEdi.setEstadoDirGeo(subEdiVt.getEstadoDirGeo());
                dirSubEdi.setItTipoPlaca(subEdiVt.getItTipoPlaca());
                dirSubEdi.setCalleRr(subEdiVt.getCalleRr());
                dirSubEdi.setUnidadRr(subEdiVt.getUnidadRr());
                dirSubEdi.setTdiId(3);
            } else if (subEdiVt.isHasOwnAddress()) {
                dirSubEdi = subEdiVt.getSubEdificioObj().getListDireccionesMgl().get(0);
            } else {
                dirSubEdi = new CmtDireccionMgl();
                dirSubEdi.setBarrio(subEdiVt.getBarrio());
                dirSubEdi.setTipoViaPrincipal(subEdiVt.getTipoViaPrincipal());
                dirSubEdi.setNumViaPrincipal(subEdiVt.getNumViaPrincipal());
                dirSubEdi.setLtViaPrincipal(subEdiVt.getLtViaPrincipal());
                dirSubEdi.setNlPostViaP(subEdiVt.getNlPostViaP());
                dirSubEdi.setBisViaPrincipal(subEdiVt.getNlPostViaP());
                dirSubEdi.setCuadViaPrincipal(subEdiVt.getCuadViaPrincipal());
                dirSubEdi.setTipoViaGeneradora(subEdiVt.getTipoViaGeneradora());
                dirSubEdi.setNumViaGeneradora(subEdiVt.getNumViaGeneradora());
                dirSubEdi.setLtViaGeneradora(subEdiVt.getLtViaGeneradora());
                dirSubEdi.setNlPostViaG(subEdiVt.getNlPostViaG());
                dirSubEdi.setBisViaGeneradora(subEdiVt.getBisViaGeneradora());
                dirSubEdi.setCuadViaGeneradora(subEdiVt.getCuadViaGeneradora());
                dirSubEdi.setPlacaDireccion(subEdiVt.getPlacaDireccion());
                dirSubEdi.setCodTipoDir(subEdiVt.getIdTipoDireccion());
                if (subEdiVt.getTipoNivel1() != null) {
                    dirSubEdi.setCpTipoNivel1(subEdiVt.getTipoNivel1().getNombreBasica());
                }
                if (subEdiVt.getTipoNivel2() != null) {
                    dirSubEdi.setCpTipoNivel2(subEdiVt.getTipoNivel2().getNombreBasica());
                }
                if (subEdiVt.getTipoNivel3() != null) {
                    dirSubEdi.setCpTipoNivel3(subEdiVt.getTipoNivel3().getNombreBasica());
                }
                if (subEdiVt.getTipoNivel4() != null) {
                    dirSubEdi.setCpTipoNivel4(subEdiVt.getTipoNivel4().getNombreBasica());
                }
                if (subEdiVt.getTipoNivel5() != null) {
                    dirSubEdi.setCpTipoNivel5(subEdiVt.getTipoNivel5().getNombreBasica());
                }
                if (subEdiVt.getCpTipoNivel6() != null) {
                    dirSubEdi.setCpTipoNivel6(subEdiVt.getCpTipoNivel6());
                }
                dirSubEdi.setCpValorNivel1(subEdiVt.getValorNivel1());
                dirSubEdi.setCpValorNivel2(subEdiVt.getValorNivel2());
                dirSubEdi.setCpValorNivel3(subEdiVt.getValorNivel3());
                dirSubEdi.setCpValorNivel4(subEdiVt.getValorNivel4());
                dirSubEdi.setCpValorNivel5(subEdiVt.getCpValorNivel5());
                dirSubEdi.setCpValorNivel6(subEdiVt.getCpValorNivel6());
                dirSubEdi.setMzTipoNivel1(subEdiVt.getMzTipoNivel1());
                dirSubEdi.setMzTipoNivel2(subEdiVt.getMzTipoNivel2());
                dirSubEdi.setMzTipoNivel3(subEdiVt.getMzTipoNivel3());
                dirSubEdi.setMzTipoNivel4(subEdiVt.getMzTipoNivel4());
                dirSubEdi.setMzTipoNivel5(subEdiVt.getMzTipoNivel5());
                dirSubEdi.setMzTipoNivel6(subEdiVt.getMzTipoNivel6());
                dirSubEdi.setMzValorNivel1(subEdiVt.getMzValorNivel1());
                dirSubEdi.setMzValorNivel2(subEdiVt.getMzValorNivel2());
                dirSubEdi.setMzValorNivel3(subEdiVt.getMzValorNivel3());
                dirSubEdi.setMzValorNivel4(subEdiVt.getMzValorNivel4());
                dirSubEdi.setMzValorNivel5(subEdiVt.getMzValorNivel5());
                dirSubEdi.setMzValorNivel6(subEdiVt.getMzValorNivel6());
                dirSubEdi.setEstadoDirGeo(subEdiVt.getEstadoDirGeo());
                dirSubEdi.setItTipoPlaca(subEdiVt.getItTipoPlaca());
                dirSubEdi.setCalleRr(subEdiVt.getCalleRr());
                dirSubEdi.setUnidadRr(subEdiVt.getUnidadRr());
                dirSubEdi.setTdiId(3);

            }
            return dirSubEdi;
            
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de mapear los campos de la dirección: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            String msg = "Error al momento de mapear los campos de la dirección: " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * @return the idEdificioVt
     */
    public BigDecimal getIdEdificioVt() {
        return idEdificioVt;
    }

    /**
     * @param idEdificioVt the idEdificioVt to set
     */
    public void setIdEdificioVt(BigDecimal idEdificioVt) {
        this.idEdificioVt = idEdificioVt;
    }

    /**
     * @return the vtObj
     */
    public CmtVisitaTecnicaMgl getVtObj() {
        return vtObj;
    }

    /**
     * @param vtObj the vtObj to set
     */
    public void setVtObj(CmtVisitaTecnicaMgl vtObj) {
        this.vtObj = vtObj;
    }

    /**
     * @return the subEdificioObj
     */
    public CmtSubEdificioMgl getSubEdificioObj() {
        return subEdificioObj;
    }

    /**
     * @param subEdificioObj the subEdificioObj to set
     */
    public void setSubEdificioObj(CmtSubEdificioMgl subEdificioObj) {
        this.subEdificioObj = subEdificioObj;
    }

    /**
     * @return the tipoNivel1
     */
    public CmtBasicaMgl getTipoNivel1() {
        return tipoNivel1;
    }

    /**
     * @param tipoNivel1 the tipoNivel1 to set
     */
    public void setTipoNivel1(CmtBasicaMgl tipoNivel1) {
        this.tipoNivel1 = tipoNivel1;
    }

    /**
     * @return the valorNivel1
     */
    public String getValorNivel1() {
        return valorNivel1;
    }

    /**
     * @param valorNivel1 the valorNivel1 to set
     */
    public void setValorNivel1(String valorNivel1) {
        this.valorNivel1 = valorNivel1;
    }

    /**
     * @return the tipoNivel2
     */
    public CmtBasicaMgl getTipoNivel2() {
        return tipoNivel2;
    }

    /**
     * @param tipoNivel2 the tipoNivel2 to set
     */
    public void setTipoNivel2(CmtBasicaMgl tipoNivel2) {
        this.tipoNivel2 = tipoNivel2;
    }

    /**
     * @return the valorNivel2
     */
    public String getValorNivel2() {
        return valorNivel2;
    }

    /**
     * @param valorNivel2 the valorNivel2 to set
     */
    public void setValorNivel2(String valorNivel2) {
        this.valorNivel2 = valorNivel2;
    }

    /**
     * @return the tipoNivel3
     */
    public CmtBasicaMgl getTipoNivel3() {
        return tipoNivel3;
    }

    /**
     * @param tipoNivel3 the tipoNivel3 to set
     */
    public void setTipoNivel3(CmtBasicaMgl tipoNivel3) {
        this.tipoNivel3 = tipoNivel3;
    }

    /**
     * @return the valorNivel3
     */
    public String getValorNivel3() {
        return valorNivel3;
    }

    /**
     * @param valorNivel3 the valorNivel3 to set
     */
    public void setValorNivel3(String valorNivel3) {
        this.valorNivel3 = valorNivel3;
    }

    /**
     * @return the tipoNivel4
     */
    public CmtBasicaMgl getTipoNivel4() {
        return tipoNivel4;
    }

    /**
     * @param tipoNivel4 the tipoNivel4 to set
     */
    public void setTipoNivel4(CmtBasicaMgl tipoNivel4) {
        this.tipoNivel4 = tipoNivel4;
    }

    /**
     * @return the valorNivel4
     */
    public String getValorNivel4() {
        return valorNivel4;
    }

    /**
     * @param valorNivel4 the valorNivel4 to set
     */
    public void setValorNivel4(String valorNivel4) {
        this.valorNivel4 = valorNivel4;
    }

    /**
     * @return the tipoNivel5
     */
    public CmtBasicaMgl getTipoNivel5() {
        return tipoNivel5;
    }

    /**
     * @param tipoNivel5 the tipoNivel5 to set
     */
    public void setTipoNivel5(CmtBasicaMgl tipoNivel5) {
        this.tipoNivel5 = tipoNivel5;
    }

    /**
     * @return the valorNivel5
     */
    public String getValorNivel5() {
        return valorNivel5;
    }

    /**
     * @param valorNivel5 the valorNivel5 to set
     */
    public void setValorNivel5(String valorNivel5) {
        this.valorNivel5 = valorNivel5;
    }

    /**
     * @return the numeroPisosCasas
     */
    public int getNumeroPisosCasas() {
        
        if (tipoNivel1 != null) {
            if (Constant.BASICA_CONJUNTO_CASAS_PROPIA_DIRECCION.equals(tipoNivel1.getIdentificadorInternoApp()) || 
                    Constant.BASICA_CAMPUS_BATALLON.equals(tipoNivel1.getIdentificadorInternoApp())) {
                return 1;
            } else {
                return numeroPisosCasas;
            }
        } else {
            return numeroPisosCasas;
        }

    }

    /**
     * @param numeroPisosCasas the numeroPisosCasas to set
     */
    public void setNumeroPisosCasas(int numeroPisosCasas) {
        this.numeroPisosCasas = numeroPisosCasas;
    }

    /**
     * @return the direccionSubEdificio
     */
    public String getDireccionSubEdificio() {
        return direccionSubEdificio;
    }

    /**
     * @param direccionSubEdificio the direccionSubEdificio to set
     */
    public void setDireccionSubEdificio(String direccionSubEdificio) {
        this.direccionSubEdificio = direccionSubEdificio;
    }

    /**
     * @return the nombramientoRr
     */
    public String getNombramientoRr() {
        return nombramientoRr;
    }

    /**
     * @param nombramientoRr the nombramientoRr to set
     */
    public void setNombramientoRr(String nombramientoRr) {
        this.nombramientoRr = nombramientoRr;
    }

    /**
     * @return the nombreSubEdificio
     */
    public String getNombreSubEdificio() {
        return nombreSubEdificio;
    }

    /**
     * @param nombreSubEdificio the nombreSubEdificio to set
     */
    public void setNombreSubEdificio(String nombreSubEdificio) {
        this.nombreSubEdificio = nombreSubEdificio;
    }

    /**
     * @return the idTipoDireccion
     */
    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

    /**
     * @param idTipoDireccion the idTipoDireccion to set
     */
    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    /**
     * @return the barrio
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * @param barrio the barrio to set
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     * @return the tipoViaPrincipal
     */
    public String getTipoViaPrincipal() {
        return tipoViaPrincipal;
    }

    /**
     * @param tipoViaPrincipal the tipoViaPrincipal to set
     */
    public void setTipoViaPrincipal(String tipoViaPrincipal) {
        this.tipoViaPrincipal = tipoViaPrincipal;
    }

    /**
     * @return the numViaPrincipal
     */
    public String getNumViaPrincipal() {
        return numViaPrincipal;
    }

    /**
     * @param numViaPrincipal the numViaPrincipal to set
     */
    public void setNumViaPrincipal(String numViaPrincipal) {
        this.numViaPrincipal = numViaPrincipal;
    }

    /**
     * @return the ltViaPrincipal
     */
    public String getLtViaPrincipal() {
        return ltViaPrincipal;
    }

    /**
     * @param ltViaPrincipal the ltViaPrincipal to set
     */
    public void setLtViaPrincipal(String ltViaPrincipal) {
        this.ltViaPrincipal = ltViaPrincipal;
    }

    /**
     * @return the nlPostViaP
     */
    public String getNlPostViaP() {
        return nlPostViaP;
    }

    /**
     * @param nlPostViaP the nlPostViaP to set
     */
    public void setNlPostViaP(String nlPostViaP) {
        this.nlPostViaP = nlPostViaP;
    }

    /**
     * @return the bisViaPrincipal
     */
    public String getBisViaPrincipal() {
        return bisViaPrincipal;
    }

    /**
     * @param bisViaPrincipal the bisViaPrincipal to set
     */
    public void setBisViaPrincipal(String bisViaPrincipal) {
        this.bisViaPrincipal = bisViaPrincipal;
    }

    /**
     * @return the cuadViaPrincipal
     */
    public String getCuadViaPrincipal() {
        return cuadViaPrincipal;
    }

    /**
     * @param cuadViaPrincipal the cuadViaPrincipal to set
     */
    public void setCuadViaPrincipal(String cuadViaPrincipal) {
        this.cuadViaPrincipal = cuadViaPrincipal;
    }

    /**
     * @return the tipoViaGeneradora
     */
    public String getTipoViaGeneradora() {
        return tipoViaGeneradora;
    }

    /**
     * @param tipoViaGeneradora the tipoViaGeneradora to set
     */
    public void setTipoViaGeneradora(String tipoViaGeneradora) {
        this.tipoViaGeneradora = tipoViaGeneradora;
    }

    /**
     * @return the numViaGeneradora
     */
    public String getNumViaGeneradora() {
        return numViaGeneradora;
    }

    /**
     * @param numViaGeneradora the numViaGeneradora to set
     */
    public void setNumViaGeneradora(String numViaGeneradora) {
        this.numViaGeneradora = numViaGeneradora;
    }

    /**
     * @return the ltViaGeneradora
     */
    public String getLtViaGeneradora() {
        return ltViaGeneradora;
    }

    /**
     * @param ltViaGeneradora the ltViaGeneradora to set
     */
    public void setLtViaGeneradora(String ltViaGeneradora) {
        this.ltViaGeneradora = ltViaGeneradora;
    }

    /**
     * @return the nlPostViaG
     */
    public String getNlPostViaG() {
        return nlPostViaG;
    }

    /**
     * @param nlPostViaG the nlPostViaG to set
     */
    public void setNlPostViaG(String nlPostViaG) {
        this.nlPostViaG = nlPostViaG;
    }

    /**
     * @return the bisViaGeneradora
     */
    public String getBisViaGeneradora() {
        return bisViaGeneradora;
    }

    /**
     * @param bisViaGeneradora the bisViaGeneradora to set
     */
    public void setBisViaGeneradora(String bisViaGeneradora) {
        this.bisViaGeneradora = bisViaGeneradora;
    }

    /**
     * @return the cuadViaGeneradora
     */
    public String getCuadViaGeneradora() {
        return cuadViaGeneradora;
    }

    /**
     * @param cuadViaGeneradora the cuadViaGeneradora to set
     */
    public void setCuadViaGeneradora(String cuadViaGeneradora) {
        this.cuadViaGeneradora = cuadViaGeneradora;
    }

    /**
     * @return the placaDireccion
     */
    public String getPlacaDireccion() {
        return placaDireccion;
    }

    /**
     * @param placaDireccion the placaDireccion to set
     */
    public void setPlacaDireccion(String placaDireccion) {
        this.placaDireccion = placaDireccion;
    }

    /**
     * @return the cpTipoNivel1
     */
    public String getCpTipoNivel1() {
        return cpTipoNivel1;
    }

    /**
     * @param cpTipoNivel1 the cpTipoNivel1 to set
     */
    public void setCpTipoNivel1(String cpTipoNivel1) {
        this.cpTipoNivel1 = cpTipoNivel1;
    }

    /**
     * @return the cpTipoNivel2
     */
    public String getCpTipoNivel2() {
        return cpTipoNivel2;
    }

    /**
     * @param cpTipoNivel2 the cpTipoNivel2 to set
     */
    public void setCpTipoNivel2(String cpTipoNivel2) {
        this.cpTipoNivel2 = cpTipoNivel2;
    }

    /**
     * @return the cpTipoNivel3
     */
    public String getCpTipoNivel3() {
        return cpTipoNivel3;
    }

    /**
     * @param cpTipoNivel3 the cpTipoNivel3 to set
     */
    public void setCpTipoNivel3(String cpTipoNivel3) {
        this.cpTipoNivel3 = cpTipoNivel3;
    }

    /**
     * @return the cpTipoNivel4
     */
    public String getCpTipoNivel4() {
        return cpTipoNivel4;
    }

    /**
     * @param cpTipoNivel4 the cpTipoNivel4 to set
     */
    public void setCpTipoNivel4(String cpTipoNivel4) {
        this.cpTipoNivel4 = cpTipoNivel4;
    }

    /**
     * @return the cpTipoNivel5
     */
    public String getCpTipoNivel5() {
        return cpTipoNivel5;
    }

    /**
     * @param cpTipoNivel5 the cpTipoNivel5 to set
     */
    public void setCpTipoNivel5(String cpTipoNivel5) {
        this.cpTipoNivel5 = cpTipoNivel5;
    }

    /**
     * @return the cpTipoNivel6
     */
    public String getCpTipoNivel6() {
        return cpTipoNivel6;
    }

    /**
     * @param cpTipoNivel6 the cpTipoNivel6 to set
     */
    public void setCpTipoNivel6(String cpTipoNivel6) {
        this.cpTipoNivel6 = cpTipoNivel6;
    }

    /**
     * @return the cpValorNivel1
     */
    public String getCpValorNivel1() {
        return cpValorNivel1;
    }

    /**
     * @param cpValorNivel1 the cpValorNivel1 to set
     */
    public void setCpValorNivel1(String cpValorNivel1) {
        this.cpValorNivel1 = cpValorNivel1;
    }

    /**
     * @return the cpValorNivel2
     */
    public String getCpValorNivel2() {
        return cpValorNivel2;
    }

    /**
     * @param cpValorNivel2 the cpValorNivel2 to set
     */
    public void setCpValorNivel2(String cpValorNivel2) {
        this.cpValorNivel2 = cpValorNivel2;
    }

    /**
     * @return the cpValorNivel3
     */
    public String getCpValorNivel3() {
        return cpValorNivel3;
    }

    /**
     * @param cpValorNivel3 the cpValorNivel3 to set
     */
    public void setCpValorNivel3(String cpValorNivel3) {
        this.cpValorNivel3 = cpValorNivel3;
    }

    /**
     * @return the cpValorNivel4
     */
    public String getCpValorNivel4() {
        return cpValorNivel4;
    }

    /**
     * @param cpValorNivel4 the cpValorNivel4 to set
     */
    public void setCpValorNivel4(String cpValorNivel4) {
        this.cpValorNivel4 = cpValorNivel4;
    }

    /**
     * @return the cpValorNivel5
     */
    public String getCpValorNivel5() {
        return cpValorNivel5;
    }

    /**
     * @param cpValorNivel5 the cpValorNivel5 to set
     */
    public void setCpValorNivel5(String cpValorNivel5) {
        this.cpValorNivel5 = cpValorNivel5;
    }

    /**
     * @return the cpValorNivel6
     */
    public String getCpValorNivel6() {
        return cpValorNivel6;
    }

    /**
     * @param cpValorNivel6 the cpValorNivel6 to set
     */
    public void setCpValorNivel6(String cpValorNivel6) {
        this.cpValorNivel6 = cpValorNivel6;
    }

    /**
     * @return the mzTipoNivel1
     */
    public String getMzTipoNivel1() {
        return mzTipoNivel1;
    }

    /**
     * @param mzTipoNivel1 the mzTipoNivel1 to set
     */
    public void setMzTipoNivel1(String mzTipoNivel1) {
        this.mzTipoNivel1 = mzTipoNivel1;
    }

    /**
     * @return the mzTipoNivel2
     */
    public String getMzTipoNivel2() {
        return mzTipoNivel2;
    }

    /**
     * @param mzTipoNivel2 the mzTipoNivel2 to set
     */
    public void setMzTipoNivel2(String mzTipoNivel2) {
        this.mzTipoNivel2 = mzTipoNivel2;
    }

    /**
     * @return the mzTipoNivel3
     */
    public String getMzTipoNivel3() {
        return mzTipoNivel3;
    }

    /**
     * @param mzTipoNivel3 the mzTipoNivel3 to set
     */
    public void setMzTipoNivel3(String mzTipoNivel3) {
        this.mzTipoNivel3 = mzTipoNivel3;
    }

    /**
     * @return the mzTipoNivel4
     */
    public String getMzTipoNivel4() {
        return mzTipoNivel4;
    }

    /**
     * @param mzTipoNivel4 the mzTipoNivel4 to set
     */
    public void setMzTipoNivel4(String mzTipoNivel4) {
        this.mzTipoNivel4 = mzTipoNivel4;
    }

    /**
     * @return the mzTipoNivel5
     */
    public String getMzTipoNivel5() {
        return mzTipoNivel5;
    }

    /**
     * @param mzTipoNivel5 the mzTipoNivel5 to set
     */
    public void setMzTipoNivel5(String mzTipoNivel5) {
        this.mzTipoNivel5 = mzTipoNivel5;
    }

    /**
     * @return the mzValorNivel1
     */
    public String getMzValorNivel1() {
        return mzValorNivel1;
    }

    /**
     * @param mzValorNivel1 the mzValorNivel1 to set
     */
    public void setMzValorNivel1(String mzValorNivel1) {
        this.mzValorNivel1 = mzValorNivel1;
    }

    /**
     * @return the mzValorNivel2
     */
    public String getMzValorNivel2() {
        return mzValorNivel2;
    }

    /**
     * @param mzValorNivel2 the mzValorNivel2 to set
     */
    public void setMzValorNivel2(String mzValorNivel2) {
        this.mzValorNivel2 = mzValorNivel2;
    }

    /**
     * @return the mzValorNivel3
     */
    public String getMzValorNivel3() {
        return mzValorNivel3;
    }

    /**
     * @param mzValorNivel3 the mzValorNivel3 to set
     */
    public void setMzValorNivel3(String mzValorNivel3) {
        this.mzValorNivel3 = mzValorNivel3;
    }

    /**
     * @return the mzValorNivel4
     */
    public String getMzValorNivel4() {
        return mzValorNivel4;
    }

    /**
     * @param mzValorNivel4 the mzValorNivel4 to set
     */
    public void setMzValorNivel4(String mzValorNivel4) {
        this.mzValorNivel4 = mzValorNivel4;
    }

    /**
     * @return the mzValorNivel5
     */
    public String getMzValorNivel5() {
        return mzValorNivel5;
    }

    /**
     * @param mzValorNivel5 the mzValorNivel5 to set
     */
    public void setMzValorNivel5(String mzValorNivel5) {
        this.mzValorNivel5 = mzValorNivel5;
    }

    /**
     * @return the mzTipoNivel6
     */
    public String getMzTipoNivel6() {
        return mzTipoNivel6;
    }

    /**
     * @param mzTipoNivel6 the mzTipoNivel6 to set
     */
    public void setMzTipoNivel6(String mzTipoNivel6) {
        this.mzTipoNivel6 = mzTipoNivel6;
    }

    /**
     * @return the mzValorNivel6
     */
    public String getMzValorNivel6() {
        return mzValorNivel6;
    }

    /**
     * @param mzValorNivel6 the mzValorNivel6 to set
     */
    public void setMzValorNivel6(String mzValorNivel6) {
        this.mzValorNivel6 = mzValorNivel6;
    }

    /**
     * @return the estadoDirGeo
     */
    public String getEstadoDirGeo() {
        return estadoDirGeo;
    }

    /**
     * @param estadoDirGeo the estadoDirGeo to set
     */
    public void setEstadoDirGeo(String estadoDirGeo) {
        this.estadoDirGeo = estadoDirGeo;
    }

    public String getItTipoPlaca() {
        return itTipoPlaca;
    }

    public void setItTipoPlaca(String itTipoPlaca) {
        this.itTipoPlaca = itTipoPlaca;
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

    public String getCodigoRr() {
        return codigoRr;
    }

    public void setCodigoRr(String codigoRr) {
        this.codigoRr = codigoRr;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the perfilCreacion
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * @param perfilCreacion the perfilCreacion to set
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * @return the fechaEdicion
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * @param fechaEdicion the fechaEdicion to set
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * @return the usuarioEdicion
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * @param usuarioEdicion the usuarioEdicion to set
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * @return the perfilEdicion
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * @param perfilEdicion the perfilEdicion to set
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    /**
     * @return the estadoRegistro
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro the estadoRegistro to set
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * @return the listTecnologiaHabilitada
     */
    public List<CmtHhppVtMgl> getListTecnologiaHabilitada() {
        return listTecnologiaHabilitada;
    }

    /**
     * @param listTecnologiaHabilitada the listTecnologiaHabilitada to set
     */
    public void setListTecnologiaHabilitada(List<CmtHhppVtMgl> listTecnologiaHabilitada) {
        this.listTecnologiaHabilitada = listTecnologiaHabilitada;
    }

    public NodoMgl getNodo() {
        return nodo;
    }

    public void setNodo(NodoMgl nodo) {
        this.nodo = nodo;
    }

    public String getCodigoNodo() {
        return codigoNodo;
    }

    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    public List<CmtOrdenTrabajoInventarioMgl> getListCmtOrdenTrabajoInventarioMgl() {
        return listCmtOrdenTrabajoInventarioMgl;
    }

    public void setListCmtOrdenTrabajoInventarioMgl(List<CmtOrdenTrabajoInventarioMgl> listCmtOrdenTrabajoInventarioMgl) {
        this.listCmtOrdenTrabajoInventarioMgl = listCmtOrdenTrabajoInventarioMgl;
    }

    public String getGeneraAcometida() {
        
        return generaAcometida;
    }

    public void setGeneraAcometida(String generaAcometida) {

        this.generaAcometida = generaAcometida;
    }
    
    public BigDecimal getOtAcometidaId() {
        return otAcometidaId;
    }

    public void setOtAcometidaId(BigDecimal otAcometidaId) {
        this.otAcometidaId = otAcometidaId;
    }

    public boolean isGenAco() {
        return genAco;
    }

    public void setGenAco(boolean genAco) {
        this.genAco = genAco;
    }
    
    
    
    

    public String getNombreFromLevels() {
        StringBuilder nombreSubTemp = new StringBuilder();
        if (tipoNivel1 != null && tipoNivel1.getBasicaId() != null
                && tipoNivel1.getNombreBasica() != null
                && !tipoNivel1.getNombreBasica().trim().isEmpty()
                && valorNivel1 != null && !valorNivel1.trim().isEmpty()) {
            if (nombreSubTemp.toString().trim().length() > 0) {
                nombreSubTemp.append(" ");
            }
            nombreSubTemp.append(tipoNivel1.getNombreBasica().trim());
            nombreSubTemp.append(" ");
            nombreSubTemp.append(valorNivel1.trim());
        }

        if (tipoNivel2 != null && tipoNivel2.getBasicaId() != null
                && tipoNivel2.getNombreBasica() != null
                && !tipoNivel2.getNombreBasica().trim().isEmpty()
                && valorNivel2 != null && !valorNivel2.trim().isEmpty()) {
            if (nombreSubTemp.toString().trim().length() > 0) {
                nombreSubTemp.append(" ");
            }
            nombreSubTemp.append(tipoNivel2.getNombreBasica().trim());
            nombreSubTemp.append(" ");
            nombreSubTemp.append(valorNivel2.trim());
        }

        if (tipoNivel3 != null && tipoNivel3.getBasicaId() != null
                && tipoNivel3.getNombreBasica() != null
                && !tipoNivel3.getNombreBasica().trim().isEmpty()
                && valorNivel3 != null && !valorNivel3.trim().isEmpty()) {
            if (nombreSubTemp.toString().trim().length() > 0) {
                nombreSubTemp.append(" ");
            }
            nombreSubTemp.append(tipoNivel3.getNombreBasica().trim());
            nombreSubTemp.append(" ");
            nombreSubTemp.append(valorNivel3.trim());
        }

        if (tipoNivel4 != null && tipoNivel4.getBasicaId() != null
                && tipoNivel4.getNombreBasica() != null
                && !tipoNivel4.getNombreBasica().trim().isEmpty()
                && valorNivel4 != null && !valorNivel4.trim().isEmpty()) {
            if (nombreSubTemp.toString().trim().length() > 0) {
                nombreSubTemp.append(" ");
            }
            nombreSubTemp.append(tipoNivel4.getNombreBasica().trim());
            nombreSubTemp.append(" ");
            nombreSubTemp.append(valorNivel4.trim());
        }

        if (tipoNivel5 != null && tipoNivel5.getBasicaId() != null
                && tipoNivel5.getNombreBasica() != null
                && !tipoNivel5.getNombreBasica().trim().isEmpty()
                && valorNivel5 != null && !valorNivel5.trim().isEmpty()) {
            if (nombreSubTemp.toString().trim().length() > 0) {
                nombreSubTemp.append(" ");
            }
            nombreSubTemp.append(tipoNivel5.getNombreBasica().trim());
            nombreSubTemp.append(" ");
            nombreSubTemp.append(valorNivel5.trim());
        }

        return nombreSubTemp.toString();

    }

    /**
     * @return boolean the isProssesedAnnyHhpp
     */
    public boolean isProssesedAnnyHhpp() {

        if (listTecnologiaHabilitada == null || listTecnologiaHabilitada.isEmpty()) {
            return false;
        }
        for (CmtHhppVtMgl tecnologiaHabilitada : listTecnologiaHabilitada) {
            if (tecnologiaHabilitada.getProcesado() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return boolean the isProssesedAnnyHhpp
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean isHasOwnAddress() throws ApplicationException {
        int cont = 0;
        boolean hay = false;
        if (subEdificioObj == null) {
            return false;
        } else if (subEdificioObj.getListDireccionesMgl() == null
                || subEdificioObj.getListDireccionesMgl().isEmpty()) {
            return false;
        } else {
            for (CmtDireccionMgl cdm : subEdificioObj.getListDireccionesMgl()) {
                if (cdm.getEstadoRegistro() == 1
                        && cdm.getTdiId() == 3) {
                    if (cdm.getDireccionId() != null) {
                        cont++;
                        hay = true;
                    }
                }
            }
            if (cont > 1) {
                throw new ApplicationException("Hay mas de una direccion propia para el sub edificio:".concat(toString()));
            }
        }
        return hay;
    }

    public boolean isClickValidarDir() {
        return clickValidarDir;
    }

    public void setClickValidarDir(boolean clickValidarDir) {
        this.clickValidarDir = clickValidarDir;
    }

    public String getBarrioDireccionSubEdi() {
        return barrioDireccionSubEdi;
    }

    public void setBarrioDireccionSubEdi(String barrioDireccionSubEdi) {
        this.barrioDireccionSubEdi = barrioDireccionSubEdi;
    }

    public BigDecimal getOptipoNivel1() {
        return optipoNivel1;
    }

    public void setOptipoNivel1(BigDecimal optipoNivel1) {
        this.optipoNivel1 = optipoNivel1;
    }

    public BigDecimal getOptipoNivel2() {
        return optipoNivel2;
    }

    public void setOptipoNivel2(BigDecimal optipoNivel2) {
        this.optipoNivel2 = optipoNivel2;
    }

    public BigDecimal getOptipoNivel3() {
        return optipoNivel3;
    }

    public void setOptipoNivel3(BigDecimal optipoNivel3) {
        this.optipoNivel3 = optipoNivel3;
    }

    public BigDecimal getOptipoNivel4() {
        return optipoNivel4;
    }

    public void setOptipoNivel4(BigDecimal optipoNivel4) {
        this.optipoNivel4 = optipoNivel4;
    }
    
      /**
     * Compara 2 instancias del objeto.
     *
     * @param o Objeto a comparar.
     * @return Realiza la comparaci&oacute;n por ID de subedificio de manera
     * ascendente.
     */
    @Override
    public int compareTo(CmtSubEdificiosVt o) {
        return ( this.nombreSubEdificio.compareTo(o.nombreSubEdificio) );
    }
    
    
      /**
     * Obtiene la direcci&oacute;n del subedificio.
     * 
     * @return Direcci&oacute;n.
     */
    @Transient
    public String getDireccionSubEdificioVt() {
        String direccionSub = "";
        
        if (this.getNombreSubEdificio().equals("")) {
            if(this.getSubEdificioObj()!= null){
                 CmtDireccionMgl cmtDireccionMgl = this.getSubEdificioObj().getListDireccionesMgl().get(0);
            direccionSub = cmtDireccionMgl.getCalleRr()+" " +cmtDireccionMgl.getUnidadRr();
            }
        }
        return (direccionSub);
    }
    
        /**
     * Obtiene la direcci&oacute;n del subedificio.
     * 
     * @return Direcci&oacute;n.
     */
    @Transient
    public String getDirSubEdificio() {
        String direccionSub = "";
        if (this.getSubEdificioObj() != null) {
            if (this.getSubEdificioObj().getListDireccionesMgl() != null && !this.getSubEdificioObj().getListDireccionesMgl().isEmpty()) {
                CmtDireccionMgl cmtDireccionMgl = this.getSubEdificioObj().getListDireccionesMgl().get(0);
                direccionSub = cmtDireccionMgl.getCalleRr() + " " + cmtDireccionMgl.getUnidadRr();
            }
        }
 
        return (direccionSub);
    }
    
            /**
     * Obtiene la direcci&oacute;n del subedificio.
     * 
     * @return Direcci&oacute;n.
     */
    @Transient
    public String getDirSubEdificioVT() {
        String direccionSub = "";
        
        if (this.getDireccionSubEdificio() != null) {
            direccionSub = this.getDireccionSubEdificio();
        } else {
            direccionSub = this.getNombreSubEdificio();
        }
        
        return (direccionSub);
    }


    public List<DrDireccion> getListaDrdireccionSubedif() {
        return listaDrdireccionSubedif;
    }

    public void setListaDrdireccionSubedif(List<DrDireccion> listaDrdireccionSubedif) {
        this.listaDrdireccionSubedif = listaDrdireccionSubedif;
    }
    
    
    
}
