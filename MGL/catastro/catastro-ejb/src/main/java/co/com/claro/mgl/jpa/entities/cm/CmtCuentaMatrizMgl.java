package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.utils.CmtUtilidadesCM;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_CUENTA_MATRIZ", schema = "MGL_SCHEME")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "CmtCuentaMatrizMgl.findAll",
            query = "SELECT c FROM CmtCuentaMatrizMgl c "),
     @NamedQuery(name = "CmtCuentaMatrizMgl.findByNumeroCuentaMatriz", query = "SELECT s FROM CmtCuentaMatrizMgl s WHERE s.numeroCuenta = :numeroCuenta and s.estadoRegistro = 1"),
     @NamedQuery(name = "CmtCuentaMatrizMgl.findByIdCM", query = "SELECT s FROM CmtCuentaMatrizMgl s WHERE s.cuentaMatrizId = :cuentaMatrizId"),
     @NamedQuery(name = "CmtCuentaMatrizMgl.findByMaximoSecCm",
            query = "SELECT MAX(a.secArchivo) FROM CmtCuentaMatrizMgl a WHERE a.cuentaMatrizId = :cuentaMatrizId"),
      @NamedQuery(name = "CmtCuentaMatrizMgl.findCMById", query = "SELECT s FROM CmtCuentaMatrizMgl s WHERE s.cuentaMatrizId = :cuentaMatrizId  and s.estadoRegistro = 1")
})
public class CmtCuentaMatrizMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(CmtCuentaMatrizMgl.class);
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtCuentaMatrizMgl.CmtCuentaMatrizMglSq",
            sequenceName = "MGL_SCHEME.CMT_CUENTA_MATRIZ_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtCuentaMatrizMgl.CmtCuentaMatrizMglSq")
    @Column(name = "CUENTAMATRIZ_ID", nullable = false)
    private BigDecimal cuentaMatrizId;

    @Column(name = "NUMERO_CUENTA")
    private BigDecimal numeroCuenta;

    @Column(name = "NOMBRE_CUENTA")
    private String nombreCuenta;

    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;

    @Column(name = "COMUNIDAD")
    private String comunidad;

    @Column(name = "DIVISION")
    private String division;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;

    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GPO_ID_DEPARTAMENTO", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GPO_ID_MUNICIPIO", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl municipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GPO_ID_CENTRO_POBLADO", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl centroPoblado;

    @OneToMany(mappedBy = "cuentaMatrizObj", fetch = FetchType.LAZY)
    private List<CmtSubEdificioMgl> listCmtSubEdificioMgl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cmObj")
    private List<CmtOrdenTrabajoMgl> ordenesTrabajoList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuentaMatrizObj")
    private List<CmtDireccionMgl> direccionesList;
    
    @Column(name = "IMAGEN_CUENTA")
    private String imgCuenta;
    
    @Column(name = "SECUENCIA_ARCHIVO")
    private int secArchivo;
    
    @Column(name = "ORIGEN_FICHA")
    private String origenFicha;
        
    @Transient
    private List<String> coberturasList;
    @Transient
    private String unicoEdif = null;
    @Transient
    private String cuentaId;
    @Transient
    private boolean corregimientoAplicado;

    /**
     * @return the cuentaMatrizId
     */
    public BigDecimal getCuentaMatrizId() {
        return cuentaMatrizId;
    }

    /**
     * @param cuentaMatrizId the cuentaMatrizId to set
     */
    public void setCuentaMatrizId(BigDecimal cuentaMatrizId) {
        this.cuentaMatrizId = cuentaMatrizId;
    }

    /**
     * @return the numeroCuenta
     */
    public BigDecimal getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta the numeroCuenta to set
     */
    public void setNumeroCuenta(BigDecimal numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the nombreCuenta
     */
    public String getNombreCuenta() {
        return nombreCuenta;
    }

    /**
     * @param nombreCuenta the nombreCuenta to set
     */
    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
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
     * @return the fechaEdicion
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * @param fechaModificacion
     */
    public void setFechaEdicion(Date fechaModificacion) {
        this.fechaEdicion = fechaModificacion;
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
     * @return the comunidad
     */
    public String getComunidad() {
        return comunidad;
    }

    /**
     * @param comunidad the comunidad to set
     */
    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
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
     * @return the departamento
     */
    public GeograficoPoliticoMgl getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(GeograficoPoliticoMgl departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the municipio
     */
    public GeograficoPoliticoMgl getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(GeograficoPoliticoMgl municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the centroPoblado
     */
    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    /**
     * @param centroPoblado the centroPoblado to set
     */
    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    /**
     * @return the listCmtSubEdificioMgl
     */
    public List<CmtSubEdificioMgl> getListCmtSubEdificioMgl() {
        return listCmtSubEdificioMgl;
    }

    /**
     * @return the imgCuenta
     */
    public String getImgCuenta() {
        return imgCuenta;
    }

    /**
     * @param imgCuenta the imgCuenta to set
     */
    public void setImgCuenta(String imgCuenta) {
        this.imgCuenta = imgCuenta;
    }

    /**
     * @return the listCmtSubEdificioMgl
     */
    public List<CmtSubEdificioMgl> getListCmtSubEdificioMglActivos() {
        List<CmtSubEdificioMgl> result = new ArrayList<CmtSubEdificioMgl>();
        if (listCmtSubEdificioMgl != null) {
            for (CmtSubEdificioMgl ed : listCmtSubEdificioMgl) {
                if (ed.getEstadoRegistro() != 0) {
                    result.add(ed);
                }
            }
            
        }
           Collections.sort(result, new Comparator<CmtSubEdificioMgl>() {
                @Override
                public int compare(CmtSubEdificioMgl a, CmtSubEdificioMgl b) {
                    return a.getSubEdificioId().compareTo(b.getSubEdificioId());
                }
            });

        return result;
    }

    /**
     * @param listCmtSubEdificioMgl the listCmtSubEdificioMgl to set
     */
    public void setListCmtSubEdificioMgl(List<CmtSubEdificioMgl> listCmtSubEdificioMgl) {
        this.listCmtSubEdificioMgl = listCmtSubEdificioMgl;
    }

    public List<CmtOrdenTrabajoMgl> getOrdenesTrabajoList() {
        return ordenesTrabajoList;
    }

    public void setOrdenesTrabajoList(List<CmtOrdenTrabajoMgl> ordenesTrabajoList) {
        this.ordenesTrabajoList = ordenesTrabajoList;
    }

    public List<CmtDireccionMgl> getDireccionesList() {
        return direccionesList;
    }

    public void setDireccionesList(List<CmtDireccionMgl> direccionesList) {
        this.direccionesList = direccionesList;
    }

    public CmtSubEdificioMgl getSubEdificioGeneral() throws ApplicationException {
       
        CmtSubEdificioMgl cmtSubEdificioMgl = null;
        try {
            if (listCmtSubEdificioMgl != null) {
                for (CmtSubEdificioMgl subEIterator : listCmtSubEdificioMgl) {
                    if (subEIterator.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null) {
                        if (subEIterator.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                                .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO) && subEIterator.getEstadoRegistro() == 1) {
                            return subEIterator;
                        }
                    }
                }
                for (CmtSubEdificioMgl subEIterator : listCmtSubEdificioMgl) {
                    if (subEIterator.getEstadoRegistro() != 0) {
                        return subEIterator;
                    }
                }
            }
        } catch (NullPointerException ex) {
            LOGGER.error("Error al momento de obtener el subedificio. EX000: " + ex.getMessage(), ex);
        }
        return cmtSubEdificioMgl;
    }

    public List<CmtSubEdificioMgl> getSubEdificiosMglNoGeneral() throws ApplicationException {
        List<CmtSubEdificioMgl> listNoGeneral = new ArrayList<CmtSubEdificioMgl>();
        CmtSubEdificioMgl subEdificioMgl = getSubEdificioGeneral();
        if (subEdificioMgl != null) {
            if (subEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null && 
                    subEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                    .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                for (CmtSubEdificioMgl subEIterator : listCmtSubEdificioMgl) {
                    if (!subEIterator.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                            .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)
                            && subEIterator.getEstadoRegistro() == 1) {
                        listNoGeneral.add(subEIterator);
                    }
                }
            }
        }
        return listNoGeneral;
    }
    
        public List<CmtSubEdificioMgl> getSubEdificiosValidacionesHhpp() throws ApplicationException {
        List<CmtSubEdificioMgl> listNoGeneral = new ArrayList<CmtSubEdificioMgl>();
        if (getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                && getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp()
                .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
            if(listCmtSubEdificioMgl.size()== 1){
                 listNoGeneral.add(listCmtSubEdificioMgl.get(0));
            }else{
                for (CmtSubEdificioMgl subEIterator : listCmtSubEdificioMgl) {
                    if (subEIterator.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                            && !subEIterator.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                            .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)
                            && subEIterator.getEstadoRegistro() == 1) {
                        listNoGeneral.add(subEIterator);
                    }
                }
            }

            } else {
                CmtSubEdificioMgl subEdificioMgl = getSubEdificioGeneral();
                listNoGeneral.add(subEdificioMgl);
            }
        return listNoGeneral;
    }

    public CmtDireccionMgl getDireccionPrincipal() throws ApplicationException {
        CmtDireccionMgl direccionPrincipal = null;
        try {
            if (direccionesList != null) {
                for (CmtDireccionMgl dir : direccionesList) {
                    if (dir.getDireccionObj() != null) {
                        if (dir.getTdiId() == Constant.ID_TIPO_DIRECCION_PRINCIPAL && dir.getEstadoRegistro() == 1) {
                            direccionPrincipal = dir;
                            break;
                        }
                    }

                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la Dirección Principal. EX000 " + ex.getMessage(), ex);
        }
        return direccionPrincipal;
    }

    public String getUnicoSubEdificio() {
        if (listCmtSubEdificioMgl.size() == 1) {
            unicoEdif = "Torre 1";
        }
        return unicoEdif;
    }

    /**
     * Metodo que identifica si es unico edificio
     *
     * @return boolean
     * @author Carlos Leonardo Villamil Hitss
     */
    public boolean isUnicoSubEdificioBoolean() {
        if (listCmtSubEdificioMgl.size() == 1) {
            CmtSubEdificioMgl temp = listCmtSubEdificioMgl.get(0);
            if (temp.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && !temp.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                    .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                return true;
            }
        }else{
            List<CmtSubEdificioMgl> listaSubEdificios = new ArrayList<CmtSubEdificioMgl>();
            for (CmtSubEdificioMgl ed : listCmtSubEdificioMgl) {
                if (ed.getEstadoRegistro() != 0) {
                    listaSubEdificios.add(ed);
                }
            }
            
            // Ordena la lista por ID de subedificio ascendente.
            Collections.sort(listaSubEdificios, new Comparator<CmtSubEdificioMgl>() {
                @Override
                public int compare(CmtSubEdificioMgl a, CmtSubEdificioMgl b) {
                    return a.getSubEdificioId().compareTo(b.getSubEdificioId());
                }
            });
            
            
            CmtSubEdificioMgl temp = listaSubEdificios.get(0);
            if (temp.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && !temp.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                    .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                return true;
            }
        }
        return false;
    }

    public List<CmtDireccionMgl> getDireccionAlternaList() throws ApplicationException {
        List<CmtDireccionMgl> direccionAlternaList = new ArrayList<CmtDireccionMgl>();
        for (CmtDireccionMgl dir : direccionesList) {
            if (dir.getTdiId() == Constant.ID_TIPO_DIRECCION_ALTERNA && dir.getEstadoRegistro() == 1) {
                direccionAlternaList.add(dir);
            }
        }
        return direccionAlternaList;
    }

    public boolean existeSubEdiDireccionPropia() throws ApplicationException {
        boolean existe = false;
        for (CmtSubEdificioMgl subEIterator : listCmtSubEdificioMgl) {
            if (subEIterator.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && !subEIterator.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                    .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)
                    && subEIterator.getEstadoRegistro() == 1) {
                if (subEIterator.getListDireccionesMgl() != null && subEIterator.getListDireccionesMgl().size() > 0) {
                    for (CmtDireccionMgl dirIterator : subEIterator.getListDireccionesMgl()) {
                        if (Constant.ID_TIPO_DIRECCION_SUBEDIFICIO == dirIterator.getTdiId()) {
                            return true;
                        }
                    }
                }
            }
        }
        return existe;
    }
    
    public boolean existeSubEdiDireccionPropia(CmtSubEdificioMgl subEdificioMgl) throws ApplicationException {
        boolean existe = false;

        if (subEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                && !subEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)
                && subEdificioMgl.getEstadoRegistro() == 1) {
            if (subEdificioMgl.getListDireccionesMgl() != null && subEdificioMgl.getListDireccionesMgl().size() > 0) {
                for (CmtDireccionMgl dirIterator : subEdificioMgl.getListDireccionesMgl()) {
                    if (Constant.ID_TIPO_DIRECCION_SUBEDIFICIO == dirIterator.getTdiId()) {
                        return true;
                    }
                }
            }
        }

        return existe;
    }


    @Override
    public String toString() {
        return "[" + cuentaMatrizId + "]" + nombreCuenta;
    }

    public List<String> getCoberturasList() {
        return coberturasList;
    }

    public void pagesNotas(int i) {
        coberturasList.get(i);

    }

    public void setCoberturasList(List<String> coberturasList) {
        this.coberturasList = coberturasList;
    }

    public String getUnicoEdif() {
        return unicoEdif;
    }

    public void setUnicoEdif(String unicoEdif) {
        this.unicoEdif = unicoEdif;
    }

    public String getCuentaId() {
        if (this.cuentaMatrizId != null) {
            cuentaId = CmtUtilidadesCM.getIdCuentaMatriz(this.cuentaMatrizId);
        }
        return cuentaId;
    }

    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
    }

    public int getSecArchivo() {
        return secArchivo;
    }

    public void setSecArchivo(int secArchivo) {
        this.secArchivo = secArchivo;
    }
    
    

    /**
     * Metodo que trae el subedificio unico cuando la cuentamatriz es Unico
     * edificio
     *
     * @return CmtSubEdificioMgl
     * @author Carlos Leonardo Villamil Hitss
     */
    public CmtSubEdificioMgl getSubedificioUnicoSubedificio() {
        CmtSubEdificioMgl cmtSubEdificioMglUnicoEdificio = null;
        if (listCmtSubEdificioMgl.size() == 1) {
            if (!(listCmtSubEdificioMgl.get(0).getEstadoSubEdificioObj()
                    .getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO))) {
                cmtSubEdificioMglUnicoEdificio = listCmtSubEdificioMgl.get(0);
            }
        }else{
              CmtSubEdificioMgl temp = listCmtSubEdificioMgl.get(0);
            if (temp.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && !temp.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                    .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                 cmtSubEdificioMglUnicoEdificio = listCmtSubEdificioMgl.get(0);
            }
        }
        return cmtSubEdificioMglUnicoEdificio;
    }

    public String getOrigenFicha() {
        return origenFicha;
    }

    public void setOrigenFicha(String origenFicha) {
        this.origenFicha = origenFicha;
    }
    
    public boolean isCorregimientoAplicado() {
        return corregimientoAplicado;
    }

    public void setCorregimientoAplicado(boolean corregimientoAplicado) {
        this.corregimientoAplicado = corregimientoAplicado;
    }
    
}
