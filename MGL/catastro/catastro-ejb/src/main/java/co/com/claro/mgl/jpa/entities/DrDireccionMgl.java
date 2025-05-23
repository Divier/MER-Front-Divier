/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "TEC_SOLICITUD_DIRECCION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DrDireccionMgl.findAll", query = "SELECT s FROM DrDireccionMgl s")})
public class DrDireccionMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "DrDireccionMgl.drDireccionMglSeq",
            sequenceName = "MGL_SCHEME.TEC_SOLICITUD_DIRECCION_SQ", allocationSize = 1)
    @GeneratedValue(generator = "DrDireccionMgl.drDireccionMglSeq")
    @Column(name = "SOLICITUD_DIRECCION_ID", nullable = false)
    private BigDecimal Id;
    @Column(name = "SOL_TEC_HABILITADA_ID")
    private String IdSolicitud;
    @Column(name = "COD_TIPO_DIRECCION")
    private String idTipoDireccion;
    @Column(name = "DIR_PRINC_ALT")
    private String DirPrincAlt;
    @Column(name = "BARRIO")
    private String Barrio;
    @Column(name = "TIPO_VIA_PRINCIPAL")
    private String TipoViaPrincipal;
    @Column(name = "NUM_VIA_PRINCIPAL")
    private String NumViaPrincipal;
    @Column(name = "LT_VIA_PRINCIPAL")
    private String LtViaPrincipal;
    @Column(name = "NL_POST_VIA_P")
    private String NlPostViaP;
    @Column(name = "BIS_VIA_PRINCIPAL")
    private String BisViaPrincipal;
    @Column(name = "CUAD_VIA_PRINCIPAL")
    private String CuadViaPrincipal;
    @Column(name = "TIPO_VIA_GENERADORA")
    private String TipoViaGeneradora;
    @Column(name = "NUM_VIA_GENERADORA")
    private String NumViaGeneradora;
    @Column(name = "LT_VIA_GENERADORA")
    private String LtViaGeneradora;
    @Column(name = "NL_POST_VIA_G")
    private String NlPostViaG;
    @Column(name = "BIS_VIA_GENERADORA")
    private String BisViaGeneradora;
    @Column(name = "CUAD_VIA_GENERADORA")
    private String CuadViaGeneradora;
    @Column(name = "PLACA_DIRECCION")
    private String PlacaDireccion;
    @Column(name = "CP_TIPO_NIVEL1")
    private String CpTipoNivel1;
    @Column(name = "CP_TIPO_NIVEL2")
    private String CpTipoNive2;
    @Column(name = "CP_TIPO_NIVEL3")
    private String CpTipoNivel3;
    @Column(name = "CP_TIPO_NIVEL4")
    private String CpTipoNivel4;
    @Column(name = "CP_TIPO_NIVEL5")
    private String CpTipoNivel5;
    @Column(name = "CP_TIPO_NIVEL6")
    private String CpTipoNivel6;
    @Column(name = "CP_VALOR_NIVEL1")
    private String CpValorNivel1;
    @Column(name = "CP_VALOR_NIVEL2")
    private String CpValorNivel2;
    @Column(name = "CP_VALOR_NIVEL3")
    private String CpValorNivel3;
    @Column(name = "CP_VALOR_NIVEL4")
    private String CpValorNivel4;
    @Column(name = "CP_VALOR_NIVEL5")
    private String CpValorNivel5;
    @Column(name = "CP_VALOR_NIVEL6")
    private String CpValorNivel6;
    @Column(name = "MZ_TIPO_NIVEL1")
    private String MzTipoNivel1;
    @Column(name = "MZ_TIPO_NIVEL2")
    private String MzTipoNivel2;
    @Column(name = "MZ_TIPO_NIVEL3")
    private String MzTipoNivel3;
    @Column(name = "MZ_TIPO_NIVEL4")
    private String MzTipoNivel4;
    @Column(name = "MZ_TIPO_NIVEL5")
    private String MzTipoNivel5;
    @Column(name = "MZ_VALOR_NIVEL1")
    private String MzValorNivel1;
    @Column(name = "MZ_VALOR_NIVEL2")
    private String MzValorNivel2;
    @Column(name = "MZ_VALOR_NIVEL3")
    private String MzValorNivel3;
    @Column(name = "MZ_VALOR_NIVEL4")
    private String MzValorNivel4;
    @Column(name = "MZ_VALOR_NIVEL5")
    private String MzValorNivel5;
    @Column(name = "ID_DIR_CATASTRO")
    private String IdDirCatastro;
    @Column(name = "MZ_TIPO_NIVEL6")
    private String MzTipoNivel6;
    @Column(name = "MZ_VALOR_NIVEL6")
    private String MzValorNivel6;
    @Column(name = "IT_TIPO_PLACA")
    private String ItTipoPlaca;
    @Column(name = "IT_VALOR_PLACA")
    private String ItValorPlaca;
    @Column(name = "ESTRATO")
    private String Estrato;
    @Column(name = "ESTADO_DIR_GEO")
    private String EstadoDirGeo;
    @Column(name = "LETRA3G")
    private String letra3g;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = false)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;


    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String Barrio) {
        this.Barrio = Barrio;
    }

    public String getBisViaGeneradora() {
        return BisViaGeneradora;
    }

    public void setBisViaGeneradora(String BisViaGeneradora) {
        this.BisViaGeneradora = BisViaGeneradora;
    }

    public String getBisViaPrincipal() {
        return BisViaPrincipal;
    }

    public void setBisViaPrincipal(String BisViaPrincipal) {
        this.BisViaPrincipal = BisViaPrincipal;
    }

    public String getCpTipoNive2() {
        return CpTipoNive2;
    }

    public void setCpTipoNive2(String CpTipoNive2) {
        this.CpTipoNive2 = CpTipoNive2;
    }

    public String getCpTipoNivel1() {
        return CpTipoNivel1;
    }

    public void setCpTipoNivel1(String CpTipoNivel1) {
        this.CpTipoNivel1 = CpTipoNivel1;
    }

    public String getCpTipoNivel3() {
        return CpTipoNivel3;
    }

    public void setCpTipoNivel3(String CpTipoNivel3) {
        this.CpTipoNivel3 = CpTipoNivel3;
    }

    public String getCpTipoNivel4() {
        return CpTipoNivel4;
    }

    public void setCpTipoNivel4(String CpTipoNivel4) {
        this.CpTipoNivel4 = CpTipoNivel4;
    }

    public String getCpTipoNivel5() {
        return CpTipoNivel5;
    }

    public void setCpTipoNivel5(String CpTipoNivel5) {
        this.CpTipoNivel5 = CpTipoNivel5;
    }

    public String getCpTipoNivel6() {
        return CpTipoNivel6;
    }

    public void setCpTipoNivel6(String CpTipoNivel6) {
        this.CpTipoNivel6 = CpTipoNivel6;
    }

    public String getCpValorNivel1() {
        return CpValorNivel1;
    }

    public void setCpValorNivel1(String CpValorNivel1) {
        this.CpValorNivel1 = CpValorNivel1;
    }

    public String getCpValorNivel2() {
        return CpValorNivel2;
    }

    public void setCpValorNivel2(String CpValorNivel2) {
        this.CpValorNivel2 = CpValorNivel2;
    }

    public String getCpValorNivel3() {
        return CpValorNivel3;
    }

    public void setCpValorNivel3(String CpValorNivel3) {
        this.CpValorNivel3 = CpValorNivel3;
    }

    public String getCpValorNivel4() {
        return CpValorNivel4;
    }

    public void setCpValorNivel4(String CpValorNivel4) {
        this.CpValorNivel4 = CpValorNivel4;
    }

    public String getCpValorNivel5() {
        return CpValorNivel5;
    }

    public void setCpValorNivel5(String CpValorNivel5) {
        this.CpValorNivel5 = CpValorNivel5;
    }

    public String getCpValorNivel6() {
        return CpValorNivel6;
    }

    public void setCpValorNivel6(String CpValorNivel6) {
        this.CpValorNivel6 = CpValorNivel6;
    }

    public String getCuadViaGeneradora() {
        return CuadViaGeneradora;
    }

    public void setCuadViaGeneradora(String CuadViaGeneradora) {
        this.CuadViaGeneradora = CuadViaGeneradora;
    }

    public String getCuadViaPrincipal() {
        return CuadViaPrincipal;
    }

    public void setCuadViaPrincipal(String CuadViaPrincipal) {
        this.CuadViaPrincipal = CuadViaPrincipal;
    }

    public String getDirPrincAlt() {
        return DirPrincAlt;
    }

    public void setDirPrincAlt(String DirPrincAlt) {
        this.DirPrincAlt = DirPrincAlt;
    }

    public String getEstadoDirGeo() {
        return EstadoDirGeo;
    }

    public void setEstadoDirGeo(String EstadoDirGeo) {
        this.EstadoDirGeo = EstadoDirGeo;
    }

    public String getEstrato() {
        return Estrato;
    }

    public void setEstrato(String Estrato) {
        this.Estrato = Estrato;
    }

    public BigDecimal getId() {
        return Id;
    }

    public void setId(BigDecimal Id) {
        this.Id = Id;
    }

    public String getIdDirCatastro() {
        return IdDirCatastro;
    }

    public void setIdDirCatastro(String IdDirCatastro) {
        this.IdDirCatastro = IdDirCatastro;
    }

    public String getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(String IdSolicitud) {
        this.IdSolicitud = IdSolicitud;
    }

    public String getItTipoPlaca() {
        return ItTipoPlaca;
    }

    public void setItTipoPlaca(String ItTipoPlaca) {
        this.ItTipoPlaca = ItTipoPlaca;
    }

    public String getItValorPlaca() {
        return ItValorPlaca;
    }

    public void setItValorPlaca(String ItValorPlaca) {
        this.ItValorPlaca = ItValorPlaca;
    }

    public String getLtViaGeneradora() {
        return LtViaGeneradora;
    }

    public void setLtViaGeneradora(String LtViaGeneradora) {
        this.LtViaGeneradora = LtViaGeneradora;
    }

    public String getLtViaPrincipal() {
        return LtViaPrincipal;
    }

    public void setLtViaPrincipal(String LtViaPrincipal) {
        this.LtViaPrincipal = LtViaPrincipal;
    }

    public String getMzTipoNivel1() {
        return MzTipoNivel1;
    }

    public void setMzTipoNivel1(String MzTipoNivel1) {
        this.MzTipoNivel1 = MzTipoNivel1;
    }

    public String getMzTipoNivel2() {
        return MzTipoNivel2;
    }

    public void setMzTipoNivel2(String MzTipoNivel2) {
        this.MzTipoNivel2 = MzTipoNivel2;
    }

    public String getMzTipoNivel3() {
        return MzTipoNivel3;
    }

    public void setMzTipoNivel3(String MzTipoNivel3) {
        this.MzTipoNivel3 = MzTipoNivel3;
    }

    public String getMzTipoNivel4() {
        return MzTipoNivel4;
    }

    public void setMzTipoNivel4(String MzTipoNivel4) {
        this.MzTipoNivel4 = MzTipoNivel4;
    }

    public String getMzTipoNivel5() {
        return MzTipoNivel5;
    }

    public void setMzTipoNivel5(String MzTipoNivel5) {
        this.MzTipoNivel5 = MzTipoNivel5;
    }

    public String getMzTipoNivel6() {
        return MzTipoNivel6;
    }

    public void setMzTipoNivel6(String MzTipoNivel6) {
        this.MzTipoNivel6 = MzTipoNivel6;
    }

    public String getMzValorNivel1() {
        return MzValorNivel1;
    }

    public void setMzValorNivel1(String MzValorNivel1) {
        this.MzValorNivel1 = MzValorNivel1;
    }

    public String getMzValorNivel2() {
        return MzValorNivel2;
    }

    public void setMzValorNivel2(String MzValorNivel2) {
        this.MzValorNivel2 = MzValorNivel2;
    }

    public String getMzValorNivel3() {
        return MzValorNivel3;
    }

    public void setMzValorNivel3(String MzValorNivel3) {
        this.MzValorNivel3 = MzValorNivel3;
    }

    public String getMzValorNivel4() {
        return MzValorNivel4;
    }

    public void setMzValorNivel4(String MzValorNivel4) {
        this.MzValorNivel4 = MzValorNivel4;
    }

    public String getMzValorNivel5() {
        return MzValorNivel5;
    }

    public void setMzValorNivel5(String MzValorNivel5) {
        this.MzValorNivel5 = MzValorNivel5;
    }

    public String getMzValorNivel6() {
        return MzValorNivel6;
    }

    public void setMzValorNivel6(String MzValorNivel6) {
        this.MzValorNivel6 = MzValorNivel6;
    }

    public String getNlPostViaG() {
        return NlPostViaG;
    }

    public void setNlPostViaG(String NlPostViaG) {
        this.NlPostViaG = NlPostViaG;
    }

    public String getNlPostViaP() {
        return NlPostViaP;
    }

    public void setNlPostViaP(String NlPostViaP) {
        this.NlPostViaP = NlPostViaP;
    }

    public String getNumViaGeneradora() {
        return NumViaGeneradora;
    }

    public void setNumViaGeneradora(String NumViaGeneradora) {
        this.NumViaGeneradora = NumViaGeneradora;
    }

    public String getNumViaPrincipal() {
        return NumViaPrincipal;
    }

    public void setNumViaPrincipal(String NumViaPrincipal) {
        this.NumViaPrincipal = NumViaPrincipal;
    }

    public String getPlacaDireccion() {
        return PlacaDireccion;
    }

    public void setPlacaDireccion(String PlacaDireccion) {
        this.PlacaDireccion = PlacaDireccion;
    }

    public String getTipoViaGeneradora() {
        return TipoViaGeneradora;
    }

    public void setTipoViaGeneradora(String TipoViaGeneradora) {
        this.TipoViaGeneradora = TipoViaGeneradora;
    }

    public String getTipoViaPrincipal() {
        return TipoViaPrincipal;
    }

    public void setTipoViaPrincipal(String TipoViaPrincipal) {
        this.TipoViaPrincipal = TipoViaPrincipal;
    }

    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    public String getLetra3g() {
        return letra3g;
    }

    public void setLetra3g(String letra3g) {
        this.letra3g = letra3g;
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
  
}
