package co.com.telmex.catastro.data;

import co.com.claro.mgl.jpa.entities.Solicitud;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase Hhpp Implementa Serialización.
 *
 * @author Deiver Rovira
 * @version	1.0
 */
public class DrDireccion implements Serializable {

    private BigDecimal Id;
    private Solicitud IdSolicitud;
    private String idTipoDireccion;
    private String DirPrincAlt;
    private String Barrio;
    private String TipoViaPrincipal;
    private String NumViaPrincipal;
    private String LtViaPrincipal;
    private String NlPostViaP;
    private String BisViaPrincipal;
    private String CuadViaPrincipal;
    private String TipoViaGeneradora;
    private String NumViaGeneradora;
    private String LtViaGeneradora;
    private String NlPostViaG;
    private String BisViaGeneradora;
    private String CuadViaGeneradora;
    private String PlacaDireccion;
    private String CpTipoNivel1;
    private String CpTipoNive2;
    private String CpTipoNivel3;
    private String CpTipoNivel4;
    private String CpTipoNivel5;
    private String CpTipoNivel6;
    private String CpValorNivel1;
    private String CpValorNivel2;
    private String CpValorNivel3;
    private String CpValorNivel4;
    private String CpValorNivel5;
    private String CpValorNivel6;
    private String MzTipoNivel1;
    private String MzTipoNivel2;
    private String MzTipoNivel3;
    private String MzTipoNivel4;
    private String MzTipoNivel5;
    private String MzValorNivel1;
    private String MzValorNivel2;
    private String MzValorNivel3;
    private String MzValorNivel4;
    private String MzValorNivel5;
    private String IdDirCatastro;
    private String MzTipoNivel6;
    private String MzValorNivel6;
    private String ItTipoPlaca;
    private String ItValorPlaca;
    private String Estrato;
    private String EstadoDirGeo;

    /**
     * Constructor.
     */
    public DrDireccion() {
    }

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

    public Solicitud getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(Solicitud IdSolicitud) {
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
}
