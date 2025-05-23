package co.com.claro.mgl.dtos;


import co.com.claro.mgl.jpa.entities.DetalleFactibilidadMgl;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.rest.dtos.CmtCoverDto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object para el manejo de Direcci&oacute;n Detallada.
 * 
 * @author Victor Bocanegra (<i>bocanegravm</i>)
 */
public class CmtDireccionDetalladaMglDto {

    private static final long serialVersionUID = 1L;

    private BigDecimal direccionDetalladaId;
    private String direccionIx;
    private String idTipoDireccion;
    private String dirPrincAlt;
    private String barrio;
    private String tipoViaPrincipal;
    private String numViaPrincipal;
    private String ltViaPrincipal;
    private String nlPostViaP;
    private String bisViaPrincipal;
    private String cuadViaPrincipal;
    private String tipoViaGeneradora;
    private String numViaGeneradora;
    private String ltViaGeneradora;
    private String nlPostViaG;
    private String bisViaGeneradora;
    private String cuadViaGeneradora;
    private String placaDireccion;
    private String cpTipoNivel1;
    private String cpTipoNivel2;
    private String cpTipoNivel3;
    private String cpTipoNivel4;
    private String cpTipoNivel5;
    private String cpTipoNivel6;
    private String cpValorNivel1;
    private String cpValorNivel2;
    private String cpValorNivel3;
    private String cpValorNivel4;
    private String cpValorNivel5;
    private String cpValorNivel6;
    private String mzTipoNivel1;
    private String mzTipoNivel2;
    private String mzTipoNivel3;
    private String mzTipoNivel4;
    private String mzTipoNivel5;
    private String mzValorNivel1;
    private String mzValorNivel2;
    private String mzValorNivel3;
    private String mzValorNivel4;
    private String mzValorNivel5;
    private String idDirCatastro;
    private String mzTipoNivel6;
    private String mzValorNivel6;
    private String itTipoPlaca;
    private String itValorPlaca;
    private String estadoDirGeo;
    private String letra3G;
    private int estadoRegistro;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Long perfilCreacion;
    private Date fechaEdicion;
    private String usuarioEdicion;
    private int perfilEdicion;
    private String direccionTexto;
    private BigDecimal confiabilidad;
    
    //
    // Atributos calculados (transient en la entidad)
    //
    private transient BigDecimal direccionId;
    private transient BigDecimal subdireccionId;
    private transient boolean hhppExistente;
    private transient String departamento;
    private transient String ciudad;
    private transient String centroPoblado;
    private transient String numeroCuentaMatriz; 
    private transient HhppMgl hhppMgl; 
    private transient String nombreSubedificio;
    
    private transient String latitudDir;
    private transient String longitudDir;
    private transient List<CmtCoverDto> listCover;
    private transient BigDecimal factibilidadId;
    private transient String tipoDireccion;
    private transient List<HhppMgl> lstHhppMgl; 
    private transient CmtCuentaMatrizMgl cuentaMatrizMgl;
    private transient DireccionMgl direccionMgl;
    private transient SubDireccionMgl subDireccionMgl;
    private transient List<DetalleFactibilidadMgl> lstDetalleFactibilidadMgls;
    private transient String ubicacionIngresada;
    private transient String DivisionalNodo;
    private transient List<CmtTecnologiaSubMgl> lstTecnologiaSubMgls; 
    
    
    /**
     * Constructor.
     */
    public CmtDireccionDetalladaMglDto() {
    }

    public BigDecimal getDireccionDetalladaId() {
        return direccionDetalladaId;
    }

    public void setDireccionDetalladaId(BigDecimal direccionDetalladaId) {
        this.direccionDetalladaId = direccionDetalladaId;
    }

    public String getDireccionIx() {
        return direccionIx;
    }

    public void setDireccionIx(String direccionIx) {
        this.direccionIx = direccionIx;
    }

    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    public String getDirPrincAlt() {
        return dirPrincAlt;
    }

    public void setDirPrincAlt(String dirPrincAlt) {
        this.dirPrincAlt = dirPrincAlt;
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

    public String getBisViaGeneradora() {
        return bisViaGeneradora;
    }

    public void setBisViaGeneradora(String bisViaGeneradora) {
        this.bisViaGeneradora = bisViaGeneradora;
    }

    public String getCuadViaGeneradora() {
        return cuadViaGeneradora;
    }

    public void setCuadViaGeneradora(String cuadViaGeneradora) {
        this.cuadViaGeneradora = cuadViaGeneradora;
    }

    public String getPlacaDireccion() {
        return placaDireccion;
    }

    public void setPlacaDireccion(String placaDireccion) {
        this.placaDireccion = placaDireccion;
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

    public String getIdDirCatastro() {
        return idDirCatastro;
    }

    public void setIdDirCatastro(String idDirCatastro) {
        this.idDirCatastro = idDirCatastro;
    }

    public String getMzTipoNivel6() {
        return mzTipoNivel6;
    }

    public void setMzTipoNivel6(String mzTipoNivel6) {
        this.mzTipoNivel6 = mzTipoNivel6;
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

    public String getEstadoDirGeo() {
        return estadoDirGeo;
    }

    public void setEstadoDirGeo(String estadoDirGeo) {
        this.estadoDirGeo = estadoDirGeo;
    }

    public String getLetra3G() {
        return letra3G;
    }

    public void setLetra3G(String letra3G) {
        this.letra3G = letra3G;
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

    public Long getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(Long perfilCreacion) {
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

    public String getDireccionTexto() {
        return direccionTexto;
    }

    public void setDireccionTexto(String direccionTexto) {
        this.direccionTexto = direccionTexto;
    }

    public BigDecimal getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(BigDecimal direccionId) {
        this.direccionId = direccionId;
    }

    public BigDecimal getSubdireccionId() {
        return subdireccionId;
    }

    public void setSubdireccionId(BigDecimal subdireccionId) {
        this.subdireccionId = subdireccionId;
    }
    
    public boolean isHhppExistente() {
        return hhppExistente;
    }

    public void setHhppExistente(boolean hhppExistente) {
        this.hhppExistente = hhppExistente;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNumeroCuentaMatriz() {
        return numeroCuentaMatriz;
    }

    public void setNumeroCuentaMatriz(String numeroCuentaMatriz) {
        this.numeroCuentaMatriz = numeroCuentaMatriz;
    }

    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public String getLatitudDir() {
        return latitudDir;
    }

    public void setLatitudDir(String latitudDir) {
        this.latitudDir = latitudDir;
    }

    public String getLongitudDir() {
        return longitudDir;
    }

    public void setLongitudDir(String longitudDir) {
        this.longitudDir = longitudDir;
    }

    public List<CmtCoverDto> getListCover() {
        return listCover;
    }

    public void setListCover(List<CmtCoverDto> listCover) {
        this.listCover = listCover;
    }
    
    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    public String getNombreSubedificio() {
        return nombreSubedificio;
    }

    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public BigDecimal getFactibilidadId() {
        return factibilidadId;
    }

    public void setFactibilidadId(BigDecimal factibilidadId) {
        this.factibilidadId = factibilidadId;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public List<HhppMgl> getLstHhppMgl() {
        return lstHhppMgl;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizMgl() {
        return cuentaMatrizMgl;
    }

    public void setCuentaMatrizMgl(CmtCuentaMatrizMgl cuentaMatrizMgl) {
        this.cuentaMatrizMgl = cuentaMatrizMgl;
    }

    public void setLstHhppMgl(List<HhppMgl> lstHhppMgl) {
        this.lstHhppMgl = lstHhppMgl;
    }

    public BigDecimal getConfiabilidad() {
        return confiabilidad;
    }

    public void setConfiabilidad(BigDecimal confiabilidad) {
        this.confiabilidad = confiabilidad;
    }

    public DireccionMgl getDireccionMgl() {
        return direccionMgl;
    }

    public void setDireccionMgl(DireccionMgl direccionMgl) {
        this.direccionMgl = direccionMgl;
    }

    public SubDireccionMgl getSubDireccionMgl() {
        return subDireccionMgl;
    }

    public void setSubDireccionMgl(SubDireccionMgl subDireccionMgl) {
        this.subDireccionMgl = subDireccionMgl;
    }

    public List<DetalleFactibilidadMgl> getLstDetalleFactibilidadMgls() {
        return lstDetalleFactibilidadMgls;
    }

    public void setLstDetalleFactibilidadMgls(List<DetalleFactibilidadMgl> lstDetalleFactibilidadMgls) {
        this.lstDetalleFactibilidadMgls = lstDetalleFactibilidadMgls;
    }

    public String getUbicacionIngresada() {
        return ubicacionIngresada;
    }

    public void setUbicacionIngresada(String ubicacionIngresada) {
        this.ubicacionIngresada = ubicacionIngresada;
    }
    
    public String getDivisionalNodo() {
        return DivisionalNodo;
    }

    public void setDivisionalNodo(String DivisionalNodo) {
        this.DivisionalNodo = DivisionalNodo;
    }

    public List<CmtTecnologiaSubMgl> getLstTecnologiaSubMgls() {
        return lstTecnologiaSubMgls;
    }

    public void setLstTecnologiaSubMgls(List<CmtTecnologiaSubMgl> lstTecnologiaSubMgls) {
        this.lstTecnologiaSubMgls = lstTecnologiaSubMgls;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (direccionDetalladaId != null ? direccionDetalladaId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "datos.CmtDireccionDetalladaMgl[ direccionDetalladaId=" + direccionDetalladaId + " ]";
    }



}
