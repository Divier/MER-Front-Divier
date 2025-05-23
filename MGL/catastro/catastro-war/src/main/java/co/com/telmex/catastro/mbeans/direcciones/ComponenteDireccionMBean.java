/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.businessmanager.address.ComponenteDireccionesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ComponenteDireccionFacadeLocal;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.DrDireccionMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.mbeans.direcciones.common.Constantes;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.DataSourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Parzifal de León
 */
@ViewScoped
@ManagedBean
public class ComponenteDireccionMBean implements Serializable {

    private static final long serialVersionUID = 1L;
    String tipoDireccion = "CK";
    String dirEstado;
    //
    String dirVia;
    String dirNum;
    String dirLetra;
    String dirLetra2;
    String dirBis;
    String dirCuadrante;
    //
    String dirGenVia;
    String dirGenNum;
    String dirGenLetra;
    String dirGenLetra2;
    String dirGenLetra3;
    String dirGenBis;
    String dirGenCuadrante;
    String dirGenPlaca;
    //
    String nivel1;
    String nivel2;
    String nivel3;
    String nivel4;
    String nivel5;
    String nivel6;
    String nivelTxt1;
    String nivelTxt2;
    String nivelTxt3;
    String nivelTxt4;
    String nivelTxt5;
    String nivelTxt6;
    //
    String barrioBM;
    String barrioTxtBM;
    String nivel1BM;
    String nivel2BM;
    String nivel3BM;
    String nivel4BM;
    String nivel5BM;
    String nivel6BM;
    String nivelTxt1BM;
    String nivelTxt2BM;
    String nivelTxt3BM;
    String nivelTxt4BM;
    String nivelTxt5BM;
    String nivelTxt6BM;
    //
    String placaIT;
    String placaTxtIT;
    private boolean readOnly = false;
    private boolean disableViaGen = true;
    private boolean verificacionCasa = false;
    private boolean nivel5plus = true;
    public boolean subENiveles = true;
    String dirPrint;
    ComponenteDireccionesManager componenteDireccionesManager = new ComponenteDireccionesManager();
    DrDireccion drDirec = new DrDireccion();
    @EJB
    ComponenteDireccionFacadeLocal componenteDireccionFacadeLocal;
    CmtDireccionMgl cmtDireccionMgl;
    private static final Logger LOGGER = LogManager.getLogger(ComponenteDireccionMBean.class);
    @EJB
    ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    DrDireccionFacadeLocal drDireccionFacade;
    @EJB
    CmtDireccionMglFacadeLocal cmtDireccionMglFacadeLocal;

    public List<ParametrosCalles> obtenerDireccionPorTipo(String tipo) throws ApplicationException {
        List<ParametrosCalles> parametrosCalles = new ArrayList<ParametrosCalles>();
        try {
            return parametrosCallesFacade.findByTipo(tipo);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerDireccionPorTipo" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerDireccionPorTipo" + e.getMessage() , e, LOGGER);
        }
        return parametrosCalles;
    }

    public List<ParametrosCalles> optionBisDir(String tipo) throws ApplicationException {

        List<ParametrosCalles> result = new ArrayList<ParametrosCalles>();
        ParametrosCalles pc = new ParametrosCalles();
        pc.setIdParametro("BIS");
        pc.setDescripcion("BIS");
        pc.setIdTipo("BIS");
        result.add(pc);
        result.addAll(parametrosCallesFacade.findByTipo(tipo));

        return result;
    }

    public void isNivel5Plus() {
        nivel5plus = true;
        if (nivel5.contains("+")) {
            nivel5plus = false;
        }
    }

    /**
     * Metodo qeu retorna la direccion dependiendo del tipo seleccionado + el
     * complemento
     *
     * @return
     */
    public String getDireccion() {
        if (tipoDireccion != null && !tipoDireccion.equalsIgnoreCase("")) {
            Constantes.TYPO_DIR valida = Constantes.TYPO_DIR.valueOf(tipoDireccion);
            switch (valida) {
                case CK:
                    return getPricipalDireccion() + " " + getComplementoDireccion();
                case BM:
                    return getManzanaDireccion() + " " + getComplementoDireccion();
                case IT:
                    return getIntraducibleDireccion() + " " + getComplementoDireccion() + " " + getPlacaIt();
                default:
                    break;
            }
        }
        LOGGER.warn("[-998] No se entontro tipo de Direccion");
        return "";
    }

    /**
     * Retorna la direccion principal
     *
     * @return
     */
    public String getPricipalDireccion() {

        String dirResult = "";

        if (dirVia != null && !dirVia.isEmpty() && dirNum != null && !dirNum.isEmpty()) {
            dirResult += dirVia + " " + dirNum + " ";
        }

        if (dirLetra != null && !dirLetra.isEmpty()) {
            dirResult += dirLetra + " ";
        }

        if (dirLetra2 != null && !dirLetra2.isEmpty()) {
            dirResult += "- " + dirLetra2 + " ";
        }
        if (dirBis != null && !dirBis.isEmpty()) {
            if (dirBis.equalsIgnoreCase(Constantes.OPTION_BIS)) {
                dirResult += dirBis + " ";
            } else {
                dirResult += "Bis" + " " + dirBis + " ";
            }
        }

        if (dirCuadrante != null && !dirCuadrante.isEmpty()) {
            dirResult += dirCuadrante + " ";
        }
        if (dirGenNum != null && !dirGenNum.isEmpty()) {
            if (dirGenVia != null
                    && !dirGenVia.isEmpty()
                    && !dirGenVia.equalsIgnoreCase(Constantes.VACIO)
                    && !dirGenVia.equalsIgnoreCase(Constantes.V_VACIO)) {
                dirResult += dirGenVia + " ";
            } else {
                dirResult += " # ";
            }
            dirResult += dirGenNum + " ";
        }

        if (dirGenLetra != null && !dirGenLetra.isEmpty()) {
            dirResult += dirGenLetra + " ";
        }

        if (dirGenLetra2 != null && !dirGenLetra2.isEmpty()) {
            dirResult += "- " + dirGenLetra2 + " ";
        }
        if (dirGenLetra3 != null && !dirGenLetra3.isEmpty()) {
            dirResult += "- " + dirGenLetra3 + " ";
        }
        if (dirGenBis != null && !dirGenBis.isEmpty()) {
            if (dirGenBis.equalsIgnoreCase(Constantes.OPTION_BIS)) {
                dirResult += dirGenBis + " ";
            } else {
                dirResult += "Bis" + " " + dirGenBis + " ";
            }
        }

        if (dirGenCuadrante != null && !dirGenCuadrante.isEmpty()) {
            dirResult += dirGenCuadrante + " ";
        }
        if (dirGenPlaca != null && !dirGenPlaca.isEmpty()) {
            dirResult += dirGenPlaca;
        }
        return dirResult;
    }

    /**
     * Retorna el complemento de la direccion, o direccion intraducible.
     *
     * @return
     */
    public String getComplementoDireccion() {
        String dirResult = "";

        if (StringUtils.isNotBlank(nivel1) && StringUtils.isNotBlank(nivelTxt1)) {
            dirResult += nivel1 + " " + nivelTxt1 + " ";
        }

        if (StringUtils.isNotBlank(nivel2) && StringUtils.isNotBlank(nivelTxt2)) {
            dirResult += nivel2 + " " + nivelTxt2 + " ";
        }

        if (nivel3 != null && !nivel3.isEmpty() && nivelTxt3 != null && !nivelTxt3.isEmpty()) {
            dirResult += nivel3 + " " + nivelTxt3 + " ";
        }

        if (nivel4 != null && !nivel4.isEmpty() && nivelTxt4 != null && !nivelTxt4.isEmpty()) {
            dirResult += nivel4 + " " + nivelTxt4 + " ";
        }

        if (nivel5 != null && !nivel5.isEmpty() && nivelTxt5 != null && !nivelTxt5.isEmpty()) {
            if (nivel5.equalsIgnoreCase(Constantes.OPT_CASA_PISO)) {
                nivel5 = "CASA";
            } else if (nivel5.equalsIgnoreCase(Constantes.OPT_PISO_INTERIOR)
                    || nivel5.equalsIgnoreCase(Constantes.OPT_PISO_LOCAL)
                    || nivel5.equalsIgnoreCase(Constantes.OPT_PISO_APTO)) {
                nivel5 = "PISO";
            }
            dirResult += nivel5 + " " + nivelTxt5 + " ";
        }

        if (nivel6 != null && !nivel6.isEmpty() && nivelTxt6 != null && !nivelTxt6.isEmpty()) {
            dirResult += nivel6 + " " + nivelTxt6 + " ";
        }
        return dirResult;
    }

    /**
     * Retorna el tipo de direccion Manazana casa
     *
     * @return
     */
    public String getManzanaDireccion() {


        String dirResult = "";

        if (nivel1BM != null && !nivel1BM.isEmpty() && nivelTxt1BM != null && !nivelTxt1BM.isEmpty()) {
            dirResult += nivel1BM + " " + nivelTxt1BM + " ";
        }

        if (nivel2BM != null && !nivel2BM.isEmpty() && nivelTxt2BM != null && !nivelTxt2BM.isEmpty()) {
            dirResult += nivel2BM + " " + nivelTxt2BM + " ";
        }

        if (nivel3BM != null && !nivel3BM.isEmpty() && nivelTxt3BM != null && !nivelTxt3BM.isEmpty()) {
            dirResult += nivel3BM + " " + nivelTxt3BM + " ";
        }

        if (nivel4BM != null && !nivel4BM.isEmpty() && nivelTxt4BM != null && !nivelTxt4BM.isEmpty()) {
            dirResult += nivel4BM + " " + nivelTxt4BM + " ";
        }

        if (nivel5BM != null && !nivel5BM.isEmpty() && nivelTxt5BM != null && !nivelTxt5BM.isEmpty()) {
            dirResult += nivel5BM + " " + nivelTxt5BM + " ";
        }

        return dirResult;
    }

    /**
     * Retorna el tipo de direccion Intraducible
     *
     * @return
     */
    public String getIntraducibleDireccion() {
        String dirResult = "";
        //obtenemos los primeros 5 niveles
        dirResult = getManzanaDireccion();
        //adicionamos el nivel 6
        if (nivel6BM != null && !nivel6BM.isEmpty() && nivelTxt6BM != null && !nivelTxt6BM.isEmpty()) {
            dirResult += nivel6BM + " " + nivelTxt6BM + " ";
        }
        return dirResult;

    }

    /**
     * Retorna la Placa de la direccion intraducible.
     *
     * @return
     */
    public String getPlacaIt() {
        String dirResult = "";

        if (placaIT != null && !placaIT.isEmpty() && placaTxtIT != null && !placaTxtIT.isEmpty()) {

            if (placaIT.trim().equalsIgnoreCase("MANZANA-CASA")
                    || placaIT.trim().equalsIgnoreCase("VP-PLACA")) {
                dirResult += placaTxtIT + " ";
            } else {
                dirResult += placaIT + " " + placaTxtIT + " ";
            }
        }
        return dirResult;
    }

    public String obtenerBarrio() {
        String value = tipoDireccion;

        if (value != null && !value.equalsIgnoreCase("")) {
            Constantes.TYPO_DIR valida = Constantes.TYPO_DIR.valueOf(value);
            switch (valida) {
                case BM:
                    String barrio = "";

                    if (nivel1 != null
                            && !nivel1.equals(Constantes.VACIO)
                            && !nivel1.equals(co.com.telmex.catastro.services.comun.Constantes.V_VACIO)
                            && !nivel1.trim().isEmpty()
                            && nivelTxt1 != null
                            && !nivelTxt1.trim().isEmpty()) {
                        barrio = nivel1 + " " + nivelTxt1;
                    } else {
                        if (barrioBM != null && !barrioBM.isEmpty()) {
                            if (barrioBM.equalsIgnoreCase("1")) {
                                barrio = barrioTxtBM;
                            } else {
                                barrio = barrioBM;
                            }
                        } else {
                            barrio = barrioTxtBM == null ? "" : barrioTxtBM;
                        }
                    }
                    return barrio;
                case IT:
                    String barrioIT = " ";
                    if (nivel1 != null
                            && !nivel1.equals(Constantes.VACIO)
                            && !nivel1.equals(Constantes.V_VACIO)
                            && !nivel1.trim().isEmpty()
                            && nivel1.trim().equalsIgnoreCase("BARRIO")
                            && nivelTxt1 != null
                            && !nivelTxt1.trim().isEmpty()) {
                        barrioIT = nivelTxt1;
                    } else {
                        if (barrioBM != null && !barrioBM.isEmpty()) {
                            if (barrioBM.equalsIgnoreCase("1")) {
                                barrioIT = barrioTxtBM;
                            } else {
                                barrioIT = barrioBM;
                            }
                        } else {
                            barrioIT = barrioTxtBM == null ? "" : barrioTxtBM;
                        }
                    }

                    return barrioIT;
                default:

                    if (barrioBM != null && !barrioBM.isEmpty()) {
                        if (barrioBM.equalsIgnoreCase("1")) {
                            barrio = barrioTxtBM;
                        } else {
                            barrio = barrioBM;
                        }
                    } else {
                        barrio = barrioTxtBM == null ? "" : barrioTxtBM;
                    }
                    return barrio;
            }
        }
        LOGGER.warn("[-1998] Se fue posible obtener el Barrio");
        return "";
    }

    public String validaDireccion(String address, CityEntity cityEntityRequest, String tipoDir) throws ApplicationException {
        String idCastro = "";
        CityEntity cityEntity = new CityEntity();
        cityEntity = componenteDireccionFacadeLocal.getCityData(cityEntityRequest.getCodCity());
        if (cityEntity != null
                && cityEntity.getCityName() != null && !cityEntity.getCityName().isEmpty()
                && cityEntity.getDpto() != null && !cityEntity.getDpto().isEmpty()) {
            cityEntityRequest.setCityName(cityEntity.getCityName());
            cityEntityRequest.setDpto(cityEntity.getDpto());
            cityEntityRequest.setPais(cityEntity.getPais());
            cityEntityRequest.setBarrioSugerido(cityEntity.getBarrioSugerido());
        } else {
            throw new ApplicationException("La Ciudad no esta configurada en Direcciones");
        }
       
        idCastro = componenteDireccionFacadeLocal.validarDir(cityEntityRequest);
        return idCastro;

    }

    /**
     * @param address
     * @param cityEntityRequest
     * @param tipoDir
     * @return AddressService
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public AddressService validaDireccionAlterna(String address, CityEntity cityEntityRequest, String tipoDir) throws ApplicationException {
        return componenteDireccionFacadeLocal.validarDirAlterna(cityEntityRequest);
    }

    public BigDecimal isSolicitudInProcess(String idDirCatastro, String comunidad) {
        BigDecimal result = BigDecimal.ZERO;
        if (idDirCatastro == null
                || idDirCatastro.equals(Constantes.ID_DIR_REPO_NULL)
                || idDirCatastro.isEmpty()) {
            return BigDecimal.ZERO;
        }
        DetalleDireccionEntity entityDetalle = new DetalleDireccionEntity();
        entityDetalle.setIdDirCatastro(idDirCatastro);
        entityDetalle.setIdtipodireccion(tipoDireccion);
        entityDetalle.setBarrio((barrioTxtBM == null) ? "" : barrioTxtBM.toString());
        if (entityDetalle.getIdtipodireccion().equals("CK")) {
            //Direccion Calle - Carrera
            entityDetalle.setTipoviaprincipal(dirVia);
            entityDetalle.setNumviaprincipal((dirNum == null) ? "" : dirNum.toString());
            entityDetalle.setLtviaprincipal(dirLetra);
            entityDetalle.setNlpostviap(dirLetra2);
            entityDetalle.setBisviaprincipal(dirBis);
            entityDetalle.setCuadviaprincipal(dirCuadrante);
            entityDetalle.setPlacadireccion((dirGenPlaca == null) ? "" : dirGenPlaca.toString());
            //Direccion Generadora de Calle - carrera
            entityDetalle.setTipoviageneradora(dirGenVia);
            entityDetalle.setNumviageneradora((dirGenNum == null) ? "" : dirGenNum.toString());
            entityDetalle.setLtviageneradora(dirGenLetra);
            entityDetalle.setNlpostviag(dirGenLetra2);
            entityDetalle.setLetra3g(dirGenLetra3);
            entityDetalle.setBisviageneradora(dirGenBis);
            entityDetalle.setCuadviageneradora(dirGenCuadrante);
        }
        if (entityDetalle.getIdtipodireccion().equals("BM")
                || entityDetalle.getIdtipodireccion().equals("IT")) {
            //Direccion Manzana-Casa
            entityDetalle.setMztiponivel1(nivel1BM);
            entityDetalle.setMztiponivel2(nivel2BM);
            entityDetalle.setMztiponivel3(nivel3BM);
            entityDetalle.setMztiponivel4(nivel4BM);
            entityDetalle.setMztiponivel5(nivel5BM);
            entityDetalle.setMzvalornivel1((nivelTxt1BM == null) ? "" : nivelTxt1BM.toString());
            entityDetalle.setMzvalornivel2((nivelTxt2BM == null) ? "" : nivelTxt2BM.toString());
            entityDetalle.setMzvalornivel3((nivelTxt3BM == null) ? "" : nivelTxt3BM.toString());
            entityDetalle.setMzvalornivel4((nivelTxt4BM == null) ? "" : nivelTxt4BM.toString());
            entityDetalle.setMzvalornivel5((nivelTxt5BM == null) ? "" : nivelTxt5BM.toString());
        }
        if (entityDetalle.getIdtipodireccion().equals("IT")
                || entityDetalle.getIdtipodireccion().equals("CK")) {
            //Complemeto o intraduciple
            entityDetalle.setCptiponivel1(nivel1);
            entityDetalle.setCptiponivel2(nivel2);
            entityDetalle.setCptiponivel3(nivel3);
            entityDetalle.setCptiponivel4(nivel4);
            entityDetalle.setCptiponivel5(nivel5);
            entityDetalle.setCptiponivel6(nivel6);

            entityDetalle.setCpvalornivel1((nivelTxt1 == null) ? "" : nivelTxt1.toString());
            entityDetalle.setCpvalornivel2((nivelTxt2 == null) ? "" : nivelTxt2.toString());
            entityDetalle.setCpvalornivel3((nivelTxt3 == null) ? "" : nivelTxt3.toString());
            entityDetalle.setCpvalornivel4((nivelTxt4 == null) ? "" : nivelTxt4.toString());
            entityDetalle.setCpvalornivel5((nivelTxt5 == null) ? "" : nivelTxt5.toString());
            entityDetalle.setCpvalornivel6((nivelTxt6 == null) ? "" : nivelTxt6.toString());
        }
        if (entityDetalle != null) {
            result = componenteDireccionFacadeLocal.findSolicitudInProcess(entityDetalle, comunidad);
        }

        return result;
    }

    public DetalleDireccionEntity fillDetalleDireccionEntity() {

        DetalleDireccionEntity direccionEntity = new DetalleDireccionEntity();
        direccionEntity.setIdtipodireccion(tipoDireccion);
        direccionEntity.setBarrio((barrioTxtBM == null) ? "" : barrioTxtBM);
        direccionEntity.setMztiponivel6("");
        direccionEntity.setMzvalornivel6("");
        direccionEntity.setItTipoPlaca("");
        direccionEntity.setItValorPlaca("");
        if (direccionEntity.getIdtipodireccion().equals("CK")) {
            //Direccion Calle - Carrera
            direccionEntity.setTipoviaprincipal(dirVia);
            direccionEntity.setNumviaprincipal((dirNum == null) ? "" : dirNum);
            direccionEntity.setLtviaprincipal(dirLetra);
            direccionEntity.setNlpostviap(dirLetra2);
            direccionEntity.setBisviaprincipal(dirBis);
            direccionEntity.setCuadviaprincipal(dirCuadrante);
            direccionEntity.setPlacadireccion((dirGenPlaca == null) ? "" : dirGenPlaca);
            //Direccion Generadora de Calle - carrera
            direccionEntity.setTipoviageneradora(dirGenVia);
            direccionEntity.setNumviageneradora((dirGenNum == null) ? "" : dirGenNum);
            direccionEntity.setLtviageneradora(dirGenLetra);
            direccionEntity.setNlpostviag(dirGenLetra2);
            direccionEntity.setLetra3g(dirGenLetra3);
            direccionEntity.setBisviageneradora(dirGenBis);
            direccionEntity.setCuadviageneradora(dirGenCuadrante);
        }
        if (direccionEntity.getIdtipodireccion().equals("BM")
                || direccionEntity.getIdtipodireccion().equals("IT")) {
            //Direccion Manzana-Casa
            direccionEntity.setMztiponivel1(nivel1BM);
            direccionEntity.setMztiponivel2(nivel2BM);
            direccionEntity.setMztiponivel3(nivel3BM);
            direccionEntity.setMztiponivel4(nivel4BM);
            direccionEntity.setMztiponivel5(nivel5BM);
            direccionEntity.setMzvalornivel1((nivelTxt1BM == null) ? "" : nivelTxt1BM);
            direccionEntity.setMzvalornivel2((nivelTxt2BM == null) ? "" : nivelTxt2BM);
            direccionEntity.setMzvalornivel3((nivelTxt3BM == null) ? "" : nivelTxt3BM);
            direccionEntity.setMzvalornivel4((nivelTxt4BM == null) ? "" : nivelTxt4BM);
            direccionEntity.setMzvalornivel5((nivelTxt5BM == null) ? "" : nivelTxt5BM);
        }
        if (direccionEntity.getIdtipodireccion().equals("CK")
                || direccionEntity.getIdtipodireccion().equals("BM")
                || direccionEntity.getIdtipodireccion().equals("IT")) {
            //Complemento o intraducible
            direccionEntity.setCptiponivel1(nivel1);
            direccionEntity.setCptiponivel2(nivel2);
            direccionEntity.setCptiponivel3(nivel3);
            direccionEntity.setCptiponivel4(nivel4);
            direccionEntity.setCptiponivel5(nivel5);
            direccionEntity.setCptiponivel6(nivel6);

            direccionEntity.setCpvalornivel1((nivelTxt1 == null) ? "" : nivelTxt1);
            direccionEntity.setCpvalornivel2((nivelTxt2 == null) ? "" : nivelTxt2);
            direccionEntity.setCpvalornivel3((nivelTxt3 == null) ? "" : nivelTxt3);
            direccionEntity.setCpvalornivel4((nivelTxt4 == null) ? "" : nivelTxt4);
            direccionEntity.setCpvalornivel5((nivelTxt5 == null) ? "" : nivelTxt5);
            direccionEntity.setCpvalornivel6((nivelTxt6 == null) ? "" : nivelTxt6);
        }
        if (direccionEntity.getIdtipodireccion().equals("IT")) {
            direccionEntity.setMztiponivel6(nivel6BM);
            direccionEntity.setMzvalornivel6((nivelTxt6BM == null) ? "" : nivelTxt6BM);
            direccionEntity.setItTipoPlaca(placaIT);
            direccionEntity.setItValorPlaca((placaTxtIT == null) ? "" : placaTxtIT);

        }
        direccionEntity.setEstadoDir(dirEstado == null ? "" : dirEstado);
        direccionEntity.setEstrato("0");
        return direccionEntity;
    }

    public boolean validarMultiorigen(String codCity) {
        boolean isMultiOrigen = false;
        Connection miConexion = null;
        PreparedStatement miPreparedStatement = null;
        ResultSet miResultSet = null;
        String query = "select gp.gpo_multiorigen from rr_dane rrd left join geografico_politico gp "
                + " on Substr(Substr('00'||rrd.codigo,-8,length('00'||rrd.codigo)),1,8)=gp.geo_codigodane "
                + " where rrd.codciudad = ? and gp.gpo_tipo = ?";
        try {
            miConexion = DataSourceFactory.getOracleConnection();
            miPreparedStatement = miConexion.prepareStatement(query);
            miPreparedStatement.setString(1, codCity);
            miPreparedStatement.setString(2, "CIUDAD");
            miResultSet = miPreparedStatement.executeQuery();
            if (miResultSet != null) {
                miResultSet.next();
                isMultiOrigen = miResultSet.getBoolean("gpo_multiorigen");
            }
        } catch (SQLException | NamingException e) {
            FacesUtil.mostrarMensajeError("Ha ocurrido un error obteniendo la informacion multiorigen en Dircomponent" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ha ocurrido un error obteniendo la informacion multiorigen en Dircomponent" + e.getMessage() , e, LOGGER);
        } finally {
            if (miResultSet != null) {
                try {
                    miResultSet.close();
                } catch (SQLException e) {
                    FacesUtil.mostrarMensajeError("Error cerrando el miResultSet en Dircomponent" + e.getMessage() , e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error cerrando el miResultSet en Dircomponent" + e.getMessage() , e, LOGGER);
                }
            }
            if (miPreparedStatement != null) {
                try {
                    miPreparedStatement.close();
                } catch (SQLException e) {
                    FacesUtil.mostrarMensajeError("Error cerrando el miPreparedStatement en Dircomponent" + e.getMessage() , e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error cerrando el miPreparedStatement en Dircomponent" + e.getMessage() , e, LOGGER);
                }
            }
            if (miConexion != null) {
                try {
                    miConexion.close();
                } catch (SQLException e) {
                    FacesUtil.mostrarMensajeError("Error cerrando el miConexion en Dircomponent" + e.getMessage() , e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error cerrando el miConexion en Dircomponent" + e.getMessage() , e, LOGGER);
                }
            }
        }
        return isMultiOrigen;
    }

    public String guardaDireccionRepoCatastro(CityEntity cityEntityRequest, String tipoDir) throws ApplicationException {
        CityEntity cityEntity = componenteDireccionFacadeLocal.getCityData(cityEntityRequest.getCodCity());

        if (cityEntity != null
                && cityEntity.getCityName() != null && !cityEntity.getCityName().isEmpty()
                && cityEntity.getDpto() != null && !cityEntity.getDpto().isEmpty()) {
            cityEntityRequest.setCityName(cityEntity.getCityName());
            cityEntityRequest.setDpto(cityEntity.getDpto());
            cityEntityRequest.setPais(cityEntity.getPais());
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String usuario = (String) session.getAttribute("usuarioDir");
     
        return componenteDireccionFacadeLocal.guardarDireccionRepo(cityEntityRequest.getAddress(),
                cityEntityRequest.getCityName(), cityEntityRequest.getDpto(), cityEntityRequest.getBarrio(), usuario,
                cityEntityRequest.getCodDane());
    }

    /**
     * Metodo que realiza el insert en la tabla dirDireccion
     *
     * @param idsolici               {@link String}
     * @param idDirCatastroPrincipal {@link String}
     * @param estrato                {@link String}
     * @return {@code boolean}
     * @throws ApplicationException Excepción en caso de error al registrar la dirección.
     */
    public boolean insertDirDireccion(String idsolici, String idDirCatastroPrincipal, String estrato) throws ApplicationException {
        boolean isInsert = false;
        String dd = (idsolici == null) ? "" : idsolici;
        String rr = (idDirCatastroPrincipal == null) ? "" : idDirCatastroPrincipal;
        if (!dd.equalsIgnoreCase("")) {
            BigDecimal sPrice = new BigDecimal(dd);
            isInsert = createRequestDirecciones(sPrice, rr, estrato);
        }
        return isInsert;
    }

    public boolean insertDirDireccionAlterna(String idCm, String idDirCatastroPrincipal, String estrato) throws ApplicationException {
        boolean isInsert = false;
        String dd = (idCm == null) ? "" : idCm;
        String rr = (idDirCatastroPrincipal == null) ? "" : idDirCatastroPrincipal;
        if (!dd.equalsIgnoreCase("")) {
            BigDecimal sPrice = new BigDecimal(dd);
            isInsert = createRequestDireccionesAlternas(sPrice, rr, estrato);
        }
        return isInsert;
    }

    public boolean createRequestDirecciones(BigDecimal idSolicitud, String idDirCatastroPrincipal, String estrato) throws ApplicationException {
        boolean flag = false;
        String estratoDir = estrato == null ? "-1" : estrato;
        drDirec = new DrDireccion();
        drDirec.setDirPrincAlt("P");
        drDirec.setIdSolicitud(idSolicitud);
        drDirec.setIdDirCatastro(idDirCatastroPrincipal);
        drDirec.setEstrato(estratoDir);
        createEntityDirDetalle();
        try {
            drDireccionFacade.create(drDirec);
            flag = true;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en createRequestDirecciones" + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en createRequestDirecciones" + ex.getMessage() , ex, LOGGER);
        }

        return flag;

    }

    public boolean createRequestDireccionesAlternas(BigDecimal idCm, String idDirCatastroPrincipal, String estrato) throws ApplicationException {
        boolean flag = false;

        return flag;

    }

    public DrDireccion createEntityDirDetalle() {
        drDirec.setIdTipoDireccion(tipoDireccion);
        drDirec.setBarrio(barrioTxtBM);
        if (drDirec.getIdTipoDireccion().equals("CK")) {
            //Direccion Calle - Carrera
            drDirec.setTipoViaPrincipal(dirVia);
            drDirec.setNumViaPrincipal(dirNum);
            drDirec.setLtViaPrincipal(dirLetra);
            drDirec.setNlPostViaP(dirLetra2);
            drDirec.setBisViaPrincipal(dirBis);
            drDirec.setCuadViaPrincipal(dirCuadrante);
            drDirec.setPlacaDireccion(dirGenPlaca);
            //Direccion Generadora de Calle - carrera
            drDirec.setTipoViaGeneradora(dirGenVia);
            drDirec.setNumViaGeneradora(dirGenNum);
            drDirec.setLtViaGeneradora(dirGenLetra);
            drDirec.setNlPostViaG(dirGenLetra2);
            drDirec.setLetra3G(dirGenLetra3);
            drDirec.setBisViaGeneradora(dirGenBis);
            drDirec.setCuadViaGeneradora(dirGenCuadrante);
        }
        if (drDirec.getIdTipoDireccion().equals("BM")
                || drDirec.getIdTipoDireccion().equals("IT")) {
            //Direccion Manzana-Casa
            drDirec.setMzTipoNivel1(nivel1BM);
            drDirec.setMzTipoNivel2(nivel2BM);
            drDirec.setMzTipoNivel3(nivel3BM);
            drDirec.setMzTipoNivel4(nivel4BM);
            drDirec.setMzTipoNivel5(nivel5BM);
            drDirec.setMzValorNivel1(nivelTxt1BM);
            drDirec.setMzValorNivel2(nivelTxt2BM);
            drDirec.setMzValorNivel3(nivelTxt3BM);
            drDirec.setMzValorNivel4(nivelTxt4BM);
            drDirec.setMzValorNivel5(nivelTxt5BM);
        }
        if (drDirec.getIdTipoDireccion().equals("CK")
                || drDirec.getIdTipoDireccion().equals("BM")
                || drDirec.getIdTipoDireccion().equals("IT")) {
            //Complemeto o intraduciple
            drDirec.setCpTipoNivel1(nivel1);
            drDirec.setCpTipoNivel2(nivel2);
            drDirec.setCpTipoNivel3(nivel3);
            drDirec.setCpTipoNivel4(nivel4);
            drDirec.setCpTipoNivel5(nivel5);
            drDirec.setCpTipoNivel6(nivel6);

            drDirec.setCpValorNivel1(nivelTxt1);
            drDirec.setCpValorNivel2(nivelTxt2);
            drDirec.setCpValorNivel3(nivelTxt3);
            drDirec.setCpValorNivel4(nivelTxt4);
            drDirec.setCpValorNivel5(nivelTxt5);
            drDirec.setCpValorNivel6(nivelTxt6);
        }
        if (drDirec.getIdTipoDireccion().equals("IT")) {
            drDirec.setMzTipoNivel6(nivel6BM);
            drDirec.setMzValorNivel6(nivelTxt6BM);
            drDirec.setItTipoPlaca(placaIT);
            drDirec.setItValorPlaca(placaTxtIT);

        }
        drDirec.setEstadoDirGeo(dirEstado);

        return drDirec;
    }

    /**
     * El metodo createEntityDirDetalleAlterna se encarga de crear un objeto de
     * tipo CmtDireccionMgl con los datos que se obtienen del componente.
     *
     * @author alejandro.martine.ext@claro.com.co
     *
     * @param cmtDireccion
     * @return cmtDireccion
     */
    public CmtDireccionMgl createEntityDirDetalleAlterna(CmtDireccionMgl cmtDireccion) {


        return cmtDireccion;
    }

    public DetalleDireccionEntity createEntityDirDetalleAlterna(DetalleDireccionEntity dtlDireccionEntity) {
        dtlDireccionEntity.setIdtipodireccion(tipoDireccion);
        dtlDireccionEntity.setBarrio(barrioTxtBM);
        if (dtlDireccionEntity.getIdtipodireccion().equals("CK")) {
            //Direccion Calle - Carrera
            dtlDireccionEntity.setTipoviaprincipal(dirVia);
            dtlDireccionEntity.setNumviaprincipal(dirNum);
            dtlDireccionEntity.setLtviaprincipal(dirLetra);
            dtlDireccionEntity.setNlpostviap(dirLetra2);
            dtlDireccionEntity.setBisviaprincipal(dirBis);
            dtlDireccionEntity.setCuadviaprincipal(dirCuadrante);
            dtlDireccionEntity.setPlacadireccion(dirGenPlaca);
            //Direccion Generadora de Calle - carrera
            dtlDireccionEntity.setTipoviageneradora(dirGenVia);
            dtlDireccionEntity.setNumviageneradora(dirGenNum);
            dtlDireccionEntity.setLtviageneradora(dirGenLetra);
            dtlDireccionEntity.setNlpostviag(dirGenLetra2);
            dtlDireccionEntity.setBisviageneradora(dirGenBis);
            dtlDireccionEntity.setCuadviageneradora(dirGenCuadrante);
        }
        if (dtlDireccionEntity.getIdtipodireccion().equals("BM")
                || dtlDireccionEntity.getIdtipodireccion().equals("IT")) {
            //Direccion Manzana-Casa
            dtlDireccionEntity.setMztiponivel1(nivel1BM);
            dtlDireccionEntity.setMztiponivel2(nivel2BM);
            dtlDireccionEntity.setMztiponivel3(nivel3BM);
            dtlDireccionEntity.setMztiponivel4(nivel4BM);
            dtlDireccionEntity.setMztiponivel5(nivel5BM);
            dtlDireccionEntity.setMzvalornivel1(nivelTxt1BM);
            dtlDireccionEntity.setMzvalornivel2(nivelTxt2BM);
            dtlDireccionEntity.setMzvalornivel3(nivelTxt3BM);
            dtlDireccionEntity.setMzvalornivel4(nivelTxt4BM);
            dtlDireccionEntity.setMzvalornivel5(nivelTxt5BM);
        }
        if (dtlDireccionEntity.getIdtipodireccion().equals("CK")
                || dtlDireccionEntity.getIdtipodireccion().equals("BM")
                || dtlDireccionEntity.getIdtipodireccion().equals("IT")) {
            //Complemeto o intraduciple
            dtlDireccionEntity.setCptiponivel1(nivel1);
            dtlDireccionEntity.setCptiponivel2(nivel2);
            dtlDireccionEntity.setCptiponivel3(nivel3);
            dtlDireccionEntity.setCptiponivel4(nivel4);
            dtlDireccionEntity.setCptiponivel5(nivel5);
            dtlDireccionEntity.setCptiponivel6(nivel6);

            dtlDireccionEntity.setCpvalornivel1(nivelTxt1);
            dtlDireccionEntity.setCpvalornivel2(nivelTxt2);
            dtlDireccionEntity.setCpvalornivel3(nivelTxt3);
            dtlDireccionEntity.setCpvalornivel4(nivelTxt4);
            dtlDireccionEntity.setCpvalornivel5(nivelTxt5);
            dtlDireccionEntity.setCpvalornivel6(nivelTxt6);
        }
        if (dtlDireccionEntity.getIdtipodireccion().equals("IT")) {
            dtlDireccionEntity.setMztiponivel6(nivel6BM);
            dtlDireccionEntity.setMzvalornivel6(nivelTxt6BM);
            dtlDireccionEntity.setItTipoPlaca(placaIT);
            dtlDireccionEntity.setItValorPlaca(placaTxtIT);

        }
        dtlDireccionEntity.setEstadoDir(dirEstado);

        return dtlDireccionEntity;
    }

    public void loadDirDetalle(DrDireccion drDireccion) {
        drDirec = drDireccion;
        tipoDireccion = drDirec.getIdTipoDireccion();
        barrioTxtBM = drDirec.getBarrio();
        dirEstado = drDireccion.getEstadoDirGeo();
        if (drDirec.getIdTipoDireccion().equals("CK")) {
            //Direccion Calle - Carrera
            dirVia = drDirec.getTipoViaPrincipal();
            dirNum = drDirec.getNumViaPrincipal();
            dirLetra = drDirec.getLtViaPrincipal();
            dirLetra2 = drDirec.getNlPostViaP();
            dirBis = drDirec.getBisViaPrincipal();
            dirCuadrante = drDirec.getCuadViaPrincipal();
            dirGenPlaca = drDirec.getPlacaDireccion();
            //Direccion Generadora de Calle - carrera
            dirGenVia = drDirec.getTipoViaGeneradora();
            dirGenNum = drDirec.getNumViaGeneradora();
            dirGenLetra = drDirec.getLtViaGeneradora();
            dirGenLetra2 = drDirec.getNlPostViaG();
            dirGenLetra3 = drDirec.getLetra3G();
            dirGenBis = drDirec.getBisViaGeneradora();
            dirGenCuadrante = drDirec.getCuadViaGeneradora();
        }
        if (drDirec.getIdTipoDireccion().equals("BM")
                || drDirec.getIdTipoDireccion().equals("IT")) {
            //Direccion Manzana-Casa
            nivel1BM = drDirec.getMzTipoNivel1();
            nivel2BM = drDirec.getMzTipoNivel2();
            nivel3BM = drDirec.getMzTipoNivel3();
            nivel4BM = drDirec.getMzTipoNivel4();
            nivel5BM = drDirec.getMzTipoNivel5();
            nivelTxt1BM = drDirec.getMzValorNivel1();
            nivelTxt2BM = drDirec.getMzValorNivel2();
            nivelTxt3BM = drDirec.getMzValorNivel3();
            nivelTxt4BM = drDirec.getMzValorNivel4();
            nivelTxt5BM = drDirec.getMzValorNivel5();
        }
        if (drDirec.getIdTipoDireccion().equals("CK")
                || drDirec.getIdTipoDireccion().equals("BM")
                || drDirec.getIdTipoDireccion().equals("IT")) {
            //Complemeto o intraduciple
            nivel1 = drDirec.getCpTipoNivel1();
            nivel2 = drDirec.getCpTipoNivel2();
            nivel3 = drDirec.getCpTipoNivel3();
            nivel4 = drDirec.getCpTipoNivel4();
            nivel5 = drDirec.getCpTipoNivel5();
            nivel6 = drDirec.getCpTipoNivel6();

            nivelTxt1 = drDirec.getCpValorNivel1();
            nivelTxt2 = drDirec.getCpValorNivel2();
            nivelTxt3 = drDirec.getCpValorNivel3();
            nivelTxt4 = drDirec.getCpValorNivel4();
            nivelTxt5 = drDirec.getCpValorNivel5();
            nivelTxt6 = drDirec.getCpValorNivel6();
        }
        if (drDirec.getIdTipoDireccion().equals("IT")) {
            nivel6BM = drDirec.getMzTipoNivel6();
            nivelTxt6BM = drDirec.getMzValorNivel6();
            placaIT = drDirec.getItTipoPlaca();
            placaTxtIT = drDirec.getItValorPlaca();

        }
    }

    public void loadDirDetalleAlterna(CmtDireccionMgl cmtDireccion) {
        cmtDireccionMgl = cmtDireccion;

        cmtDireccionMgl.setEstadoDirGeo(dirEstado);
    }

    /**
     * Metodo que realiza el insert en la tabla dirDireccionCM
     *
     * @author yimy diaz
     * @param drDirec objeto de tipo direccion
     * @return drDirec
     */
    public DrDireccion createEntityDirDetalle(DrDireccion drDirec) {
        drDirec.setIdTipoDireccion(tipoDireccion);
        drDirec.setBarrio(barrioTxtBM);
        if (drDirec.getIdTipoDireccion().equals("CK")) {
            //Direccion Calle - Carrera
            drDirec.setTipoViaPrincipal(dirVia);
            drDirec.setNumViaPrincipal(dirNum);
            drDirec.setLtViaPrincipal(dirLetra);
            drDirec.setNlPostViaP(dirLetra2);
            drDirec.setBisViaPrincipal(dirBis);
            drDirec.setCuadViaPrincipal(dirCuadrante);
            drDirec.setPlacaDireccion(dirGenPlaca);
            //Direccion Generadora de Calle - carrera
            drDirec.setTipoViaGeneradora(dirGenVia);
            drDirec.setNumViaGeneradora(dirGenNum);
            drDirec.setLtViaGeneradora(dirGenLetra);
            drDirec.setNlPostViaG(dirGenLetra2);
            drDirec.setBisViaGeneradora(dirGenBis);
            drDirec.setCuadViaGeneradora(dirGenCuadrante);
        }
        if (drDirec.getIdTipoDireccion().equals("BM")
                || drDirec.getIdTipoDireccion().equals("IT")) {
            //Direccion Manzana-Casa
            drDirec.setMzTipoNivel1(nivel1BM);
            drDirec.setMzTipoNivel2(nivel2BM);
            drDirec.setMzTipoNivel3(nivel3BM);
            drDirec.setMzTipoNivel4(nivel4BM);
            drDirec.setMzTipoNivel5(nivel5BM);
            drDirec.setMzValorNivel1(nivelTxt1BM);
            drDirec.setMzValorNivel2(nivelTxt2BM);
            drDirec.setMzValorNivel3(nivelTxt3BM);
            drDirec.setMzValorNivel4(nivelTxt4BM);
            drDirec.setMzValorNivel5(nivelTxt5BM);
        }
        if (drDirec.getIdTipoDireccion().equals("CK")
                || drDirec.getIdTipoDireccion().equals("BM")
                || drDirec.getIdTipoDireccion().equals("IT")) {
            //Complemeto o intraduciple
            drDirec.setCpTipoNivel1(nivel1);
            drDirec.setCpTipoNivel2(nivel2);
            drDirec.setCpTipoNivel3(nivel3);
            drDirec.setCpTipoNivel4(nivel4);
            drDirec.setCpTipoNivel5(nivel5);
            drDirec.setCpTipoNivel6(nivel6);

            drDirec.setCpValorNivel1(nivelTxt1);
            drDirec.setCpValorNivel2(nivelTxt2);
            drDirec.setCpValorNivel3(nivelTxt3);
            drDirec.setCpValorNivel4(nivelTxt4);
            drDirec.setCpValorNivel5(nivelTxt5);
            drDirec.setCpValorNivel6(nivelTxt6);
        }
        if (drDirec.getIdTipoDireccion().equals("IT")) {
            drDirec.setMzTipoNivel6(nivel6BM);
            drDirec.setMzValorNivel6(nivelTxt6BM);
            drDirec.setItTipoPlaca(placaIT);
            drDirec.setItValorPlaca(placaTxtIT);

        }
        drDirec.setEstadoDirGeo(dirEstado);

        return drDirec;
    }

    /**
     * Metodo que realiza la actualizacion de la direccion mostrada
     *
     *
     */
    public void printDirPrint() {
        dirPrint = getDireccion();
    }

    /**
     * Metodo que realiza el borrado de la direccion mostrada
     *
     *
     */
    public void clearprintDirPrint() {

        if (tipoDireccion != null && !tipoDireccion.equalsIgnoreCase("")) {
            if (tipoDireccion.equals("BM")
                    || tipoDireccion.equals("IT")) {
                //Direccion Manzana-Casa
                nivel1BM = null;
                nivel2BM = null;
                nivel3BM = null;
                nivel4BM = null;
                nivel5BM = null;
                nivel6BM = null;
                nivelTxt1BM = null;
                nivelTxt2BM = null;
                nivelTxt3BM = null;
                nivelTxt4BM = null;
                nivelTxt5BM = null;
                nivelTxt6BM = null;
            }
            if (tipoDireccion.equals("IT")
                    || tipoDireccion.equals("CK")) {
                //Complemeto o intraduciple
                nivel1 = null;
                nivel2 = null;
                nivel3 = null;
                nivel4 = null;
                nivel5 = null;
                nivel6 = null;

                nivelTxt1 = null;
                nivelTxt2 = null;
                nivelTxt3 = null;
                nivelTxt4 = null;
                nivelTxt5 = null;
                nivelTxt6 = null;
            }
        }
        printDirPrint();
    }

    /**
     * Metodo que realiza el insert en la tabla dirDireccionCM
     *
     * @author yimy diaz
     * @param drDireccion objeto de tipo direccion
     */
    public void loadDirAlternaDetalle(CmtDireccionSolicitudMgl drDireccion) {
        CmtDireccionSolicitudMgl drDirec = drDireccion;
        tipoDireccion = drDirec.getCodTipoDir();
        barrioTxtBM = drDirec.getBarrio();
        if (drDirec.getCodTipoDir().equals("CK")) {
            //Direccion Calle - Carrera
            dirVia = drDirec.getTipoViaPrincipal();
            dirNum = drDirec.getNumViaPrincipal();
            dirLetra = drDirec.getLtViaPrincipal();
            dirLetra2 = drDirec.getNlPostViaP();
            dirBis = drDirec.getBisViaPrincipal();
            dirCuadrante = drDirec.getCuadViaPrincipal();
            dirGenPlaca = drDirec.getPlacaDireccion();
            //Direccion Generadora de Calle - carrera
            dirGenVia = drDirec.getTipoViaGeneradora();
            dirGenNum = drDirec.getNumViaGeneradora();
            dirGenLetra = drDirec.getLtViaGeneradora();
            dirGenLetra2 = drDirec.getNlPostViaG();
            dirGenLetra3 = drDirec.getLetra3G();
            dirGenBis = drDirec.getBisViaGeneradora();
            dirGenCuadrante = drDirec.getCuadViaGeneradora();
        }
        if (drDirec.getCodTipoDir().equals("BM")
                || drDirec.getCodTipoDir().equals("IT")) {
            //Direccion Manzana-Casa
            nivel1BM = drDirec.getMzTipoNivel1();
            nivel2BM = drDirec.getMzTipoNivel2();
            nivel3BM = drDirec.getMzTipoNivel3();
            nivel4BM = drDirec.getMzTipoNivel4();
            nivel5BM = drDirec.getMzTipoNivel5();
            nivelTxt1BM = drDirec.getMzValorNivel1();
            nivelTxt2BM = drDirec.getMzValorNivel2();
            nivelTxt3BM = drDirec.getMzValorNivel3();
            nivelTxt4BM = drDirec.getMzValorNivel4();
            nivelTxt5BM = drDirec.getMzValorNivel5();
        }
        if (drDirec.getCodTipoDir().equals("CK")
                || drDirec.getCodTipoDir().equals("BM")
                || drDirec.getCodTipoDir().equals("IT")) {
            //Complemeto o intraduciple
            nivel1 = drDirec.getCpTipoNivel1();
            nivel2 = drDirec.getCpTipoNivel2();
            nivel3 = drDirec.getCpTipoNivel3();
            nivel4 = drDirec.getCpTipoNivel4();
            nivel5 = drDirec.getCpTipoNivel5();
            nivel6 = drDirec.getCpTipoNivel6();

            nivelTxt1 = drDirec.getCpValorNivel1();
            nivelTxt2 = drDirec.getCpValorNivel2();
            nivelTxt3 = drDirec.getCpValorNivel3();
            nivelTxt4 = drDirec.getCpValorNivel4();
            nivelTxt5 = drDirec.getCpValorNivel5();
            nivelTxt6 = drDirec.getCpValorNivel6();
        }
        if (drDirec.getCodTipoDir().equals("IT")) {
            nivel6BM = drDirec.getMzTipoNivel6();
            nivelTxt6BM = drDirec.getMzValorNivel6();
            placaIT = drDirec.getItTipoPlaca();
            placaTxtIT = drDirec.getItValorPlaca();

        }
        drDirec.setEstadoDirGeo(dirEstado);
    }

    public String getBarrioTxtBM() {
        return barrioTxtBM;
    }

    public void setBarrioTxtBM(String barrioTxtBM) {
        this.barrioTxtBM = barrioTxtBM;
    }

    public String getBarrioBM() {
        return barrioBM;
    }

    public void setBarrioBM(String barrioBM) {
        this.barrioBM = barrioBM;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getDirVia() {
        return dirVia;
    }

    public void setDirVia(String dirVia) {
        this.dirVia = dirVia;
    }

    public String getDirNum() {
        return dirNum;
    }

    public void setDirNum(String dirNum) {
        this.dirNum = dirNum;
    }

    public String getDirLetra() {
        return dirLetra;
    }

    public void setDirLetra(String dirLetra) {
        this.dirLetra = dirLetra;
    }

    public String getDirLetra2() {
        return dirLetra2;
    }

    public void setDirLetra2(String dirLetra2) {
        this.dirLetra2 = dirLetra2;
    }

    public String getDirBis() {
        return dirBis;
    }

    public void setDirBis(String dirBis) {
        this.dirBis = dirBis;
    }

    public String getDirCuadrante() {
        return dirCuadrante;
    }

    public void setDirCuadrante(String dirCuadrante) {
        this.dirCuadrante = dirCuadrante;
    }

    public String getDirGenVia() {
        return dirGenVia;
    }

    public void setDirGenVia(String dirGenVia) {
        this.dirGenVia = dirGenVia;
    }

    public String getDirGenNum() {
        return dirGenNum;
    }

    public void setDirGenNum(String dirGenNum) {
        this.dirGenNum = dirGenNum;
    }

    public String getDirGenLetra() {
        return dirGenLetra;
    }

    public void setDirGenLetra(String dirGenLetra) {
        this.dirGenLetra = dirGenLetra;
    }

    public String getDirGenLetra2() {
        return dirGenLetra2;
    }

    public void setDirGenLetra2(String dirGenLetra2) {
        this.dirGenLetra2 = dirGenLetra2;
    }

    public String getDirGenBis() {
        return dirGenBis;
    }

    public void setDirGenBis(String dirGenBis) {
        this.dirGenBis = dirGenBis;
    }

    public String getDirGenCuadrante() {
        return dirGenCuadrante;
    }

    public void setDirGenCuadrante(String dirGenCuadrante) {
        this.dirGenCuadrante = dirGenCuadrante;
    }

    public String getDirGenPlaca() {
        return dirGenPlaca;
    }

    public void setDirGenPlaca(String dirGenPlaca) {
        this.dirGenPlaca = dirGenPlaca;
    }

    public String getNivel1() {
        return nivel1;
    }

    public void setNivel1(String nivel1) {
        this.nivel1 = nivel1;
    }

    public String getNivel2() {
        return nivel2;
    }

    public void setNivel2(String nivel2) {
        this.nivel2 = nivel2;
    }

    public String getNivel3() {
        return nivel3;
    }

    public void setNivel3(String nivel3) {
        this.nivel3 = nivel3;
    }

    public String getNivel4() {
        return nivel4;
    }

    public void setNivel4(String nivel4) {
        this.nivel4 = nivel4;
    }

    public String getNivel5() {
        return nivel5;
    }

    public void setNivel5(String nivel5) {
        this.nivel5 = nivel5;
    }

    public String getNivel6() {
        return nivel6;
    }

    public void setNivel6(String nivel6) {
        this.nivel6 = nivel6;
    }

    public String getNivelTxt1() {
        return nivelTxt1;
    }

    public void setNivelTxt1(String nivelTxt1) {
        this.nivelTxt1 = nivelTxt1;
    }

    public String getNivelTxt2() {
        return nivelTxt2;
    }

    public void setNivelTxt2(String nivelTxt2) {
        this.nivelTxt2 = nivelTxt2;
    }

    public String getNivelTxt3() {
        return nivelTxt3;
    }

    public void setNivelTxt3(String nivelTxt3) {
        this.nivelTxt3 = nivelTxt3;
    }

    public String getNivelTxt4() {
        return nivelTxt4;
    }

    public void setNivelTxt4(String nivelTxt4) {
        this.nivelTxt4 = nivelTxt4;
    }

    public String getNivelTxt5() {
        return nivelTxt5;
    }

    public void setNivelTxt5(String nivelTxt5) {
        this.nivelTxt5 = nivelTxt5;
    }

    public String getNivelTxt6() {
        return nivelTxt6;
    }

    public void setNivelTxt6(String nivelTxt6) {
        this.nivelTxt6 = nivelTxt6;
    }

    public String getNivel1BM() {
        return nivel1BM;
    }

    public void setNivel1BM(String nivel1BM) {
        this.nivel1BM = nivel1BM;
    }

    public String getNivel2BM() {
        return nivel2BM;
    }

    public void setNivel2BM(String nivel2BM) {
        this.nivel2BM = nivel2BM;
    }

    public String getNivel3BM() {
        return nivel3BM;
    }

    public void setNivel3BM(String nivel3BM) {
        this.nivel3BM = nivel3BM;
    }

    public String getNivel4BM() {
        return nivel4BM;
    }

    public void setNivel4BM(String nivel4BM) {
        this.nivel4BM = nivel4BM;
    }

    public String getNivel5BM() {
        return nivel5BM;
    }

    public void setNivel5BM(String nivel5BM) {
        this.nivel5BM = nivel5BM;
    }

    public String getNivel6BM() {
        return nivel6BM;
    }

    public void setNivel6BM(String nivel6BM) {
        this.nivel6BM = nivel6BM;
    }

    public String getNivelTxt1BM() {
        return nivelTxt1BM;
    }

    public void setNivelTxt1BM(String nivelTxt1BM) {
        this.nivelTxt1BM = nivelTxt1BM;
    }

    public String getNivelTxt2BM() {
        return nivelTxt2BM;
    }

    public void setNivelTxt2BM(String nivelTxt2BM) {
        this.nivelTxt2BM = nivelTxt2BM;
    }

    public String getNivelTxt3BM() {
        return nivelTxt3BM;
    }

    public void setNivelTxt3BM(String nivelTxt3BM) {
        this.nivelTxt3BM = nivelTxt3BM;
    }

    public String getNivelTxt4BM() {
        return nivelTxt4BM;
    }

    public void setNivelTxt4BM(String nivelTxt4BM) {
        this.nivelTxt4BM = nivelTxt4BM;
    }

    public String getNivelTxt5BM() {
        return nivelTxt5BM;
    }

    public void setNivelTxt5BM(String nivelTxt5BM) {
        this.nivelTxt5BM = nivelTxt5BM;
    }

    public String getNivelTxt6BM() {
        return nivelTxt6BM;
    }

    public void setNivelTxt6BM(String nivelTxt6BM) {
        this.nivelTxt6BM = nivelTxt6BM;
    }

    public String getPlacaIT() {
        return placaIT;
    }

    public void setPlacaIT(String placaIT) {
        this.placaIT = placaIT;
    }

    public String getPlacaTxtIT() {
        return placaTxtIT;
    }

    public void setPlacaTxtIT(String placaTxtIT) {
        this.placaTxtIT = placaTxtIT;
    }

    public String getDirEstado() {
        return dirEstado;
    }

    public void setDirEstado(String dirEstado) {
        this.dirEstado = dirEstado;
    }

    public boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    void loadDirDetalle(DrDireccionMgl drDirec) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isDisableViaGen() {
        return disableViaGen;
    }

    public void setDisableViaGen(boolean disableViaGen) {
        this.disableViaGen = disableViaGen;
    }

    public boolean isVerificacionCasa() {
        return verificacionCasa;
    }

    public void setVerificacionCasa(boolean verificacionCasa) {
        this.verificacionCasa = verificacionCasa;
    }

    public boolean isNivel5plus() {
        return nivel5plus;
    }

    public void setNivel5plus(boolean nivel5plus) {
        this.nivel5plus = nivel5plus;
    }

    public String getDirGenLetra3() {
        return dirGenLetra3;
    }

    public void setDirGenLetra3(String dirGenLetra3) {
        this.dirGenLetra3 = dirGenLetra3;
    }

    public boolean isSubENiveles() {
        return subENiveles;
    }

    public void setSubENiveles(boolean subENiveles) {
        this.subENiveles = subENiveles;
    }

    /**
     * @return the dirPrint
     */
    public String getDirPrint() {
        return dirPrint;
    }

    /**
     * @param dirPrint the dirPrint to set
     */
    public void setDirPrint(String dirPrint) {
        this.dirPrint = dirPrint;
    }
}
