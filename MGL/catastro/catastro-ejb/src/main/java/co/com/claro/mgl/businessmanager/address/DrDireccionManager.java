/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mer.address.standardize.application.services.StandardizeItAddressService;
import co.com.claro.mer.address.standardize.application.usecases.StandardizeAddressItUseCaseImpl;
import co.com.claro.mer.address.standardize.application.usecases.StandardizeComplementUseCaseImpl;
import co.com.claro.mer.address.standardize.domain.models.dto.ItAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressItUseCase;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeComplementUseCase;
import co.com.claro.mer.address.standardize.mapper.DrDireccionToItAddressMapper;
import co.com.claro.mgl.businessmanager.cm.CmtComponenteDireccionesMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtComunidadRrManager;
import co.com.claro.mgl.businessmanager.cm.CmtValidadorDireccionesManager;
import co.com.claro.mgl.dao.impl.DrDireccionDaoImpl;
import co.com.claro.mgl.dtos.DireccionSubDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.rest.dtos.CmtRequestConstruccionDireccionDto;
import co.com.claro.mgl.rest.dtos.CmtResponseConstruccionDireccionDto;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.DireccionSubDireccionEnum;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseMesaje;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import lombok.NoArgsConstructor;

import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.BuildSQL;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Parzifal de León
 */
@NoArgsConstructor
public class DrDireccionManager {

    DrDireccionDaoImpl drDireccionDaoImpl = new DrDireccionDaoImpl();
    DrDireccion drDirec;
    private String divisores = "";
    private ParametrosMgl parametroDirCK = null;
    private ParametrosMgl parametroDirPlacaCK = null;
    private List<String> divLst;  
    private static final Logger LOGGER = LogManager.getLogger(DrDireccionManager.class);
    
    public DrDireccion create(DrDireccion drDirec) throws ApplicationException, ExceptionDB {

        DrDireccion resutl = drDireccionDaoImpl.create(drDirec);
        BuildSQL.load();
        return resutl;
    }

    public DrDireccion createCM(DrDireccion drDirec, String usuarioCrea, int perfilCrea) throws ApplicationException, ExceptionDB {

        DrDireccion resutl = drDireccionDaoImpl.createCm(drDirec, usuarioCrea, perfilCrea);
        BuildSQL.load();
        return resutl;
    }

    public DrDireccion update(DrDireccion drDirec) throws ApplicationException, ExceptionDB {

        DrDireccion resutl = drDireccionDaoImpl.update(drDirec);
        BuildSQL.load();
        return resutl;
    }

    public boolean delete(DrDireccion drDirec) throws ApplicationException, ExceptionDB {

        boolean resutl = drDireccionDaoImpl.delete(drDirec);
        BuildSQL.load();
        return resutl;
    }

    public DrDireccion findById(BigDecimal id) throws ApplicationException {
        return (DrDireccion) drDireccionDaoImpl.find(id);
    }

    public DrDireccion findByIdSolicitud(BigDecimal id) throws ApplicationException {
        return drDireccionDaoImpl.findByIdSolicitud(id);
    }

    /**
     * Retorna la informacion de direcciones
     *
     * @author gilaj
     * @param id
     * @return DrDireccion
     * @throws ApplicationException
     */
    public DrDireccion findSolicitudCm(BigDecimal id)
            throws ApplicationException {
        return drDireccionDaoImpl.findSolicitudCm(id);
    }

    public List<DrDireccion> findAll() throws ApplicationException {
        List<DrDireccion> result;
        result = drDireccionDaoImpl.findAll();
        return result;
    }

    public DrDireccion findByRequest(String idRequest) throws ApplicationException {
        return drDireccionDaoImpl.findByRequest(idRequest);

    }

    /**
     * Metodo qeu retorna la direccion dependiendo del tipo seleccionado + el
     * complemento
     *
     * @param drDirecion
     * @return
     */
    public String getDireccion(DrDireccion drDirecion) {
        this.drDirec = drDirecion;
        if (drDirec != null && drDirec.getIdTipoDireccion() != null 
                && !drDirec.getIdTipoDireccion().equalsIgnoreCase("")) {
            Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(drDirec.getIdTipoDireccion());
            switch (valida) {
                case CK:
                    return (getPricipalDireccion() + " " + getComplementoDireccion()).toUpperCase();
                case BM:
                    return (getManzanaDireccion() + " " + getComplementoDireccion()).toUpperCase();
                case IT:
                    IStandardizeComplementUseCase standardizeComplementUseCase = new StandardizeComplementUseCaseImpl();
                    IStandardizeAddressItUseCase standardizeAddressItUseCase = new StandardizeAddressItUseCaseImpl();
                    StandardizeItAddressService standardizeItAddress = new StandardizeItAddressService(standardizeAddressItUseCase, standardizeComplementUseCase);
                    DrDireccionToItAddressMapper itMapper = new DrDireccionToItAddressMapper();
                    ItAddressStructureDto itAddressStructureDto = itMapper.apply(drDirecion);
                    return standardizeItAddress.standardizeItAddress(itAddressStructureDto);
                default:
                    break;
            }
        }
        return "";
    }
    
    
    /**
     * Metodo que retorna la direccion dependiendo del tipo seleccionado + el
     * complemento
     *
     * @param drDirecion
     * @return
     */
    public String getDireccionSinComplemento(DrDireccion drDirecion) {
        this.drDirec = drDirecion;
        if (drDirec.getIdTipoDireccion() != null && !drDirec.getIdTipoDireccion().equalsIgnoreCase("")) {
            Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(drDirec.getIdTipoDireccion());
            switch (valida) {
                case CK:
                    return (getPricipalDireccion() ).toUpperCase();
                case BM:
                    return (getManzanaDireccion() ).toUpperCase();
                case IT:
                    return (getIntraducibleDireccion() + " " + getPlacaIt()).toUpperCase();
                default:
                    break;
            }
        }
        return "";
    }
   
    /**
     * valbuenayf Metodo que retorna la direccion dependiendo del tipo
     * seleccionado y la subdireccion
     *
     * @param drDirecion
     * @return
     */
    public DireccionSubDireccionDto getDireccionSubDireccion(DrDireccion drDirecion) {
        DireccionSubDireccionDto dirSubDir = null;
        Integer aux;
        this.drDirec = drDirecion;
        if (drDirec.getIdTipoDireccion() != null && !drDirec.getIdTipoDireccion().equalsIgnoreCase("")) {
            Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(drDirec.getIdTipoDireccion());
            switch (valida) {
                case CK:
                    dirSubDir = new DireccionSubDireccionDto();
                    dirSubDir.setDireccion(getPricipalDireccion().toUpperCase());
                    dirSubDir.setSubDireccion(getComplementoDireccion().toUpperCase());
                    aux = (getComplementoDireccion() != null && !getComplementoDireccion().equals(""))
                            ? DireccionSubDireccionEnum.DIRECCION_SUB_DIRECCION.getCodgo()
                            : DireccionSubDireccionEnum.DIRECCION.getCodgo();

                    dirSubDir.setDireccionSubDireccion(aux);
                    dirSubDir.setIdTipoDireccion(valida.name());
                    return dirSubDir;
                case BM:
                    dirSubDir = new DireccionSubDireccionDto();
                    dirSubDir.setDireccion(getManzanaDireccion().toUpperCase());
                    dirSubDir.setSubDireccion(getComplementoDireccion().toUpperCase());
                    aux = (getComplementoDireccion() != null && !getComplementoDireccion().equals(""))
                            ? DireccionSubDireccionEnum.DIRECCION_SUB_DIRECCION.getCodgo()
                            : DireccionSubDireccionEnum.DIRECCION.getCodgo();
                    dirSubDir.setDireccionSubDireccion(aux);
                    dirSubDir.setIdTipoDireccion(valida.name());
                    return dirSubDir;
                case IT:
                    dirSubDir = new DireccionSubDireccionDto();
                    dirSubDir.setDireccion(getIntraducibleDireccion().toUpperCase());
                    dirSubDir.setSubDireccion((getComplementoDireccion() + " " + getPlacaIt()).toUpperCase());
                    aux = (getComplementoDireccion() != null && !getComplementoDireccion().equals(""))
                            ? DireccionSubDireccionEnum.DIRECCION_SUB_DIRECCION.getCodgo()
                            : DireccionSubDireccionEnum.DIRECCION.getCodgo();
                    dirSubDir.setDireccionSubDireccion(aux);
                    dirSubDir.setIdTipoDireccion(valida.name());
                    return dirSubDir;
                default:
                    break;
            }
        }
        return dirSubDir;
    }

    /**
     * Retorna la direccion principal
     *
     * @return
     */
    public String getPricipalDireccion() {

        String dirResult = "";

        if (drDirec.getTipoViaPrincipal() != null
                && !drDirec.getTipoViaPrincipal().isEmpty() && drDirec.getNumViaPrincipal() != null
                && !drDirec.getNumViaPrincipal().isEmpty()) {
            dirResult += drDirec.getTipoViaPrincipal() + " " + drDirec.getNumViaPrincipal() + " ";
        }

        if (drDirec.getLtViaPrincipal() != null && !drDirec.getLtViaPrincipal().isEmpty()) {
            dirResult += drDirec.getLtViaPrincipal() + " ";
        }

        if (drDirec.getNlPostViaP() != null && !drDirec.getNlPostViaP().isEmpty()) {
            dirResult += "- " + drDirec.getNlPostViaP() + " ";
        }
        if (drDirec.getBisViaPrincipal() != null && !drDirec.getBisViaPrincipal().isEmpty()) {
            if (drDirec.getBisViaPrincipal().equalsIgnoreCase(Constant.OPTION_BIS)) {
                dirResult += drDirec.getBisViaPrincipal() + " ";
            } else {
                dirResult += Constant.OPTION_BIS + " " + drDirec.getBisViaPrincipal() + " ";
            }
        }

        if (drDirec.getCuadViaPrincipal() != null && !drDirec.getCuadViaPrincipal().isEmpty()) {
            dirResult += drDirec.getCuadViaPrincipal() + " ";
        }
        if (drDirec.getNumViaGeneradora() != null && !drDirec.getNumViaGeneradora().isEmpty()) {
            if (drDirec.getTipoViaGeneradora() != null
                    && !drDirec.getTipoViaGeneradora().isEmpty()
                    && !drDirec.getTipoViaGeneradora().equalsIgnoreCase(Constant.VACIO)
                    && !drDirec.getTipoViaGeneradora().equalsIgnoreCase(Constant.V_VACIO)) {
                dirResult += drDirec.getTipoViaGeneradora() + " ";
            } else {
                dirResult += " # ";
            }
            dirResult += drDirec.getNumViaGeneradora() + " ";
        }

        if (drDirec.getLtViaGeneradora() != null && !drDirec.getLtViaGeneradora().isEmpty()) {
            dirResult += drDirec.getLtViaGeneradora() + " ";
        }

        if (drDirec.getNlPostViaG() != null && !drDirec.getNlPostViaG().isEmpty()) {
            dirResult += "- " + drDirec.getNlPostViaG() + " ";
        }
        if (drDirec.getLetra3G() != null && !drDirec.getLetra3G().isEmpty()) {
            dirResult += "- " + drDirec.getLetra3G() + " ";
        }
        if (drDirec.getBisViaGeneradora() != null && !drDirec.getBisViaGeneradora().isEmpty()) {
            if (drDirec.getBisViaGeneradora().equalsIgnoreCase(Constant.OPTION_BIS)) {
                dirResult += drDirec.getBisViaGeneradora() + " ";
            } else {
                dirResult += Constant.OPTION_BIS + " " + drDirec.getBisViaGeneradora() + " ";
            }
        }

        if (drDirec.getCuadViaGeneradora() != null && !drDirec.getCuadViaGeneradora().isEmpty()) {
            dirResult += drDirec.getCuadViaGeneradora() + " ";
        }
        if (drDirec.getPlacaDireccion() != null && !drDirec.getPlacaDireccion().isEmpty()) {
            dirResult += drDirec.getPlacaDireccion();
        }
		//si la placa de la direccion viene con CD construimos la direccion principal como intraducible
        if(dirResult.indexOf("CD") == 0 
                && drDirec.getItValorPlaca().indexOf("CD") == 0){
            dirResult = getIntraducibleDireccion().trim() + " "+ drDirec.getPlacaDireccion();
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

        if (drDirec.getCpTipoNivel1() != null && !drDirec.getCpTipoNivel1().isEmpty()
                && drDirec.getCpValorNivel1() != null && !drDirec.getCpValorNivel1().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel1() + " " + drDirec.getCpValorNivel1() + " ";
        }

        if (drDirec.getCpTipoNivel2() != null && !drDirec.getCpTipoNivel2().isEmpty()
                && drDirec.getCpValorNivel2() != null && !drDirec.getCpValorNivel2().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel2() + " " + drDirec.getCpValorNivel2() + " ";
        }

        if (drDirec.getCpTipoNivel3() != null && !drDirec.getCpTipoNivel3().isEmpty()
                && drDirec.getCpValorNivel3() != null && !drDirec.getCpValorNivel3().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel3() + " " + drDirec.getCpValorNivel3() + " ";
        }

        if (drDirec.getCpTipoNivel4() != null && !drDirec.getCpTipoNivel4().isEmpty()
                && drDirec.getCpValorNivel4() != null && !drDirec.getCpValorNivel4().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel4() + " " + drDirec.getCpValorNivel4() + " ";
        }

        if (drDirec.getCpTipoNivel5() != null && !drDirec.getCpTipoNivel5().isEmpty()
                && drDirec.getCpValorNivel5() != null && !drDirec.getCpValorNivel5().isEmpty()) {

            if (drDirec.getCpTipoNivel5().equalsIgnoreCase(Constant.OPT_CASA_PISO)) {

                dirResult += "CASA" + " " + drDirec.getCpValorNivel5() + " ";

            } else if (drDirec.getCpTipoNivel5().equalsIgnoreCase(Constant.OPT_PISO_INTERIOR)
                    || drDirec.getCpTipoNivel5().equalsIgnoreCase(Constant.OPT_PISO_LOCAL)
                    || drDirec.getCpTipoNivel5().equalsIgnoreCase(Constant.OPT_PISO_APTO)) {

                dirResult += "PISO" + " " + drDirec.getCpValorNivel5() + " ";
            } else if (drDirec.getCpTipoNivel5().equalsIgnoreCase(Constant.OPT_PISO_CAJERO)) {
                Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(drDirec.getIdTipoDireccion());

                switch (valida) {
                    case CK:
                        dirResult += "PI" + " " + drDirec.getCpValorNivel5() + " ";
                        dirResult += "CAJ" + " " + drDirec.getCpValorNivel6() + " ";
                        return dirResult;
                    case BM:
                        dirResult += "CAJ" + " " + drDirec.getCpValorNivel6() + " ";
                        dirResult += "PI" + " " + drDirec.getCpValorNivel5() + " ";
                        return dirResult;
                    case IT:
                        dirResult += "PISO" + " " + drDirec.getCpValorNivel5() + " ";
                        dirResult += drDirec.getCpTipoNivel6() + " " + drDirec.getCpValorNivel6() + " ";
                        return dirResult;
                    default:
                        break;
                }
            } else {
                dirResult += drDirec.getCpTipoNivel5() + " " + drDirec.getCpValorNivel5() + " ";
            }

        }
        // @author Juan David Hernandez 
        if (drDirec.getCpTipoNivel5() != null && !drDirec.getCpTipoNivel5().isEmpty()
                && (drDirec.getCpValorNivel5() == null || drDirec.getCpValorNivel5().isEmpty())
                && drDirec.getCpTipoNivel5().equalsIgnoreCase("CASA")) {
            dirResult += "CASA";
        }
        if (drDirec.getCpTipoNivel5() != null && !drDirec.getCpTipoNivel5().isEmpty()
                && (drDirec.getCpValorNivel5() == null || drDirec.getCpValorNivel5().isEmpty())
                && drDirec.getCpTipoNivel5().equalsIgnoreCase("RECEPTOR")) {
            dirResult += "RECEPTOR";
        }
        if (drDirec.getCpTipoNivel5() != null && !drDirec.getCpTipoNivel5().isEmpty()
                && (drDirec.getCpValorNivel5() == null || drDirec.getCpValorNivel5().isEmpty())
                && drDirec.getCpTipoNivel5().equalsIgnoreCase("FUENTE")) {
            dirResult += "FUENTE";
        }
        if (drDirec.getBarrioTxtBM() != null && !drDirec.getBarrioTxtBM().isEmpty()) {
            dirResult += "BARRIO" + " " + drDirec.getBarrioTxtBM();
        }

        if (drDirec.getCpTipoNivel6() != null && !drDirec.getCpTipoNivel6().isEmpty()
                && drDirec.getCpValorNivel6() != null && !drDirec.getCpValorNivel6().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel6() + " " + drDirec.getCpValorNivel6() + " ";
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

        if (drDirec.getMzTipoNivel1() != null && !drDirec.getMzTipoNivel1().isEmpty()
                && drDirec.getMzValorNivel1() != null && !drDirec.getMzValorNivel1().isEmpty()) {
            dirResult += drDirec.getMzTipoNivel1() + " " + drDirec.getMzValorNivel1() + " ";
        } else {
            if (drDirec.getBarrio() != null && !drDirec.getBarrio().isEmpty()) {
                dirResult = "BARRIO ";
                dirResult += drDirec.getBarrio() + " ";
            }
        }

        if (drDirec.getMzTipoNivel2() != null && !drDirec.getMzTipoNivel2().isEmpty()
                && drDirec.getMzValorNivel2() != null && !drDirec.getMzValorNivel2().isEmpty()) {
            dirResult += drDirec.getMzTipoNivel2() + " " + drDirec.getMzValorNivel2() + " ";
        }

        if (drDirec.getMzTipoNivel3() != null && !drDirec.getMzTipoNivel3().isEmpty()
                && drDirec.getMzValorNivel3() != null && !drDirec.getMzValorNivel3().isEmpty()) {
            dirResult += drDirec.getMzTipoNivel3() + " " + drDirec.getMzValorNivel3() + " ";
        }

        if (drDirec.getMzTipoNivel4() != null && !drDirec.getMzTipoNivel4().isEmpty()
                && drDirec.getMzValorNivel4() != null && !drDirec.getMzValorNivel4().isEmpty()) {
            dirResult += drDirec.getMzTipoNivel4() + " " + drDirec.getMzValorNivel4() + " ";
        }

        if (drDirec.getMzTipoNivel5() != null && !drDirec.getMzTipoNivel5().isEmpty()
                && drDirec.getMzValorNivel5() != null && !drDirec.getMzValorNivel5().isEmpty()) {
            dirResult += drDirec.getMzTipoNivel5() + " " + drDirec.getMzValorNivel5() + " ";
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
        if (drDirec.getMzTipoNivel6() != null && !drDirec.getMzTipoNivel6().isEmpty()
                && drDirec.getMzValorNivel6() != null && !drDirec.getMzValorNivel6().isEmpty()) {
            dirResult += drDirec.getMzTipoNivel6() + " " + drDirec.getMzValorNivel6() + " ";
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

        if (drDirec.getItTipoPlaca() != null && !drDirec.getItTipoPlaca().isEmpty()
                && drDirec.getItValorPlaca() != null && !drDirec.getItValorPlaca().isEmpty()) {

            //JDHT
            if (drDirec.getItTipoPlaca().trim().equalsIgnoreCase("MANZANA-CASA")
                    || drDirec.getItTipoPlaca().trim().equalsIgnoreCase("VP-PLACA")
                    || drDirec.getItTipoPlaca().equalsIgnoreCase("KDX")) {
                dirResult += drDirec.getItTipoPlaca() + " " + drDirec.getItValorPlaca() + " ";
            } else {
                dirResult += drDirec.getItValorPlaca() + " ";

            }
            //JDHT
            if (drDirec.getItTipoPlaca().equalsIgnoreCase("CONTADOR") || drDirec.getItTipoPlaca().equalsIgnoreCase("KDX") ) {
                dirResult = drDirec.getItTipoPlaca() + " " + drDirec.getItValorPlaca() + " ";
            }
        }
        return dirResult;
    }

    public String obtenerBarrio(DrDireccion drDirecion) {
        this.drDirec = drDirecion;
        String value = drDirec.getIdTipoDireccion();

        if (value != null && !value.equalsIgnoreCase("")) {
            Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(value);
            switch (valida) {
                case BM:
                    String barrio = drDirec.getBarrio();
                    if (drDirec.getMzTipoNivel1() != null
                            && !drDirec.getMzTipoNivel1().equals(Constant.VACIO)
                            && !drDirec.getMzTipoNivel1().equals(co.com.telmex.catastro.services.comun.Constantes.V_VACIO)
                            && !drDirec.getMzTipoNivel1().trim().isEmpty()
                            && drDirec.getMzValorNivel1() != null
                            && !drDirec.getMzValorNivel1().trim().isEmpty()) {
                        barrio = drDirec.getMzTipoNivel1() + " " + drDirec.getMzValorNivel1();
                    } else {
                        barrio = drDirec.getBarrio() == null ? "" : drDirec.getBarrio();
                    }
                    return barrio;
                case IT:
                    String barrioIT = "";
                    if (drDirec.getCpTipoNivel1() != null
                            && !drDirec.getCpTipoNivel1().equals(Constant.VACIO)
                            && !drDirec.getCpTipoNivel1().equals(Constant.V_VACIO)
                            && !drDirec.getCpTipoNivel1().trim().isEmpty()
                            && drDirec.getCpTipoNivel1().trim().equalsIgnoreCase("BARRIO")
                            && drDirec.getCpValorNivel1() != null
                            && !drDirec.getCpValorNivel1().trim().isEmpty()) {
                        barrioIT = drDirec.getCpValorNivel1();

                    } else if (drDirec.getMzTipoNivel1() != null
                            && !drDirec.getMzTipoNivel1().equals(Constant.VACIO)
                            && !drDirec.getMzTipoNivel1().equals(Constant.V_VACIO)
                            && !drDirec.getMzTipoNivel1().trim().isEmpty()
                            && drDirec.getMzTipoNivel1().trim().equalsIgnoreCase("BARRIO")
                            && drDirec.getMzValorNivel1() != null
                            && !drDirec.getMzValorNivel1().trim().isEmpty()) {
                        barrioIT = drDirec.getMzValorNivel1();
                    } else {
                         barrioIT = drDirec.getBarrioTxtBM();
                    }
                    return barrioIT;
                default:
                     barrio = drDirec.getBarrio() == null ? "" : drDirec.getBarrio();
                    return barrio;
            }
        }
        return "";
    }

    /**
     * @param drDirecion
     * @return
     */
    public DetalleDireccionEntity fillDetalleDireccionEntity(DrDireccion drDirecion) {
        this.drDirec = drDirecion;

        DetalleDireccionEntity direccionEntity = new DetalleDireccionEntity();
        direccionEntity.setIdtipodireccion(drDirec.getIdTipoDireccion());
        direccionEntity.setBarrio((drDirec.getBarrioTxtBM() == null) ? "" : drDirec.getBarrioTxtBM());
        direccionEntity.setMztiponivel6("");
        direccionEntity.setMzvalornivel6("");
        direccionEntity.setItTipoPlaca("");
        direccionEntity.setItValorPlaca("");
        if (direccionEntity.getIdtipodireccion().equals("CK")) {
            //Direccion Calle - Carrera
            direccionEntity.setTipoviaprincipal(drDirec.getTipoViaPrincipal());
            direccionEntity.setNumviaprincipal((drDirec.getNumViaPrincipal() == null) ? "" : drDirec.getNumViaPrincipal());
            direccionEntity.setLtviaprincipal(drDirec.getLtViaPrincipal());
            direccionEntity.setNlpostviap(drDirec.getNlPostViaP());
            direccionEntity.setBisviaprincipal(drDirec.getBisViaPrincipal());
            direccionEntity.setCuadviaprincipal(drDirec.getCuadViaPrincipal());
            direccionEntity.setPlacadireccion((drDirec.getPlacaDireccion() == null) ? "" : drDirec.getPlacaDireccion());
            //Direccion Generadora de Calle - carrera
            direccionEntity.setTipoviageneradora(drDirec.getTipoViaGeneradora());
            direccionEntity.setNumviageneradora((drDirec.getNumViaGeneradora() == null) ? "" : drDirec.getNumViaGeneradora());
            direccionEntity.setLtviageneradora(drDirec.getLtViaGeneradora());
            direccionEntity.setNlpostviag(drDirec.getNlPostViaG());
            direccionEntity.setLetra3g(drDirec.getLetra3G());
            direccionEntity.setBisviageneradora(drDirec.getBisViaGeneradora());
            direccionEntity.setCuadviageneradora(drDirec.getCuadViaGeneradora());
        }
        if (direccionEntity.getIdtipodireccion().equals("BM")
                || direccionEntity.getIdtipodireccion().equals("IT")) {
            //Direccion Manzana-Casa
            direccionEntity.setMztiponivel1(drDirec.getMzTipoNivel1());
            direccionEntity.setMztiponivel2(drDirec.getMzTipoNivel2());
            direccionEntity.setMztiponivel3(drDirec.getMzTipoNivel3());
            direccionEntity.setMztiponivel4(drDirec.getMzTipoNivel4());
            direccionEntity.setMztiponivel5(drDirec.getMzTipoNivel5());
            direccionEntity.setMzvalornivel1((drDirec.getMzValorNivel1() == null) ? "" : drDirec.getMzValorNivel1());
            direccionEntity.setMzvalornivel2((drDirec.getMzValorNivel2() == null) ? "" : drDirec.getMzValorNivel2());
            direccionEntity.setMzvalornivel3((drDirec.getMzValorNivel3() == null) ? "" : drDirec.getMzValorNivel3());
            direccionEntity.setMzvalornivel4((drDirec.getMzValorNivel4() == null) ? "" : drDirec.getMzValorNivel4());
            direccionEntity.setMzvalornivel5((drDirec.getMzValorNivel5() == null) ? "" : drDirec.getMzValorNivel5());
        }
        if (direccionEntity.getIdtipodireccion().equals("CK")
                || direccionEntity.getIdtipodireccion().equals("BM")
                || direccionEntity.getIdtipodireccion().equals("IT")) {
            //Complemento o intraducible
            direccionEntity.setCptiponivel1(drDirec.getCpTipoNivel1());
            direccionEntity.setCptiponivel2(drDirec.getCpTipoNivel2());
            direccionEntity.setCptiponivel3(drDirec.getCpTipoNivel3());
            direccionEntity.setCptiponivel4(drDirec.getCpTipoNivel4());
            direccionEntity.setCptiponivel5(drDirec.getCpTipoNivel5());
            direccionEntity.setCptiponivel6(drDirec.getCpTipoNivel6());

            direccionEntity.setCpvalornivel1((drDirec.getCpValorNivel1() == null) ? "" : drDirec.getCpValorNivel1());
            direccionEntity.setCpvalornivel2((drDirec.getCpValorNivel2() == null) ? "" : drDirec.getCpValorNivel2());
            direccionEntity.setCpvalornivel3((drDirec.getCpValorNivel3() == null) ? "" : drDirec.getCpValorNivel3());
            direccionEntity.setCpvalornivel4((drDirec.getCpValorNivel4() == null) ? "" : drDirec.getCpValorNivel4());
            direccionEntity.setCpvalornivel5((drDirec.getCpValorNivel5() == null) ? "" : drDirec.getCpValorNivel5());
            direccionEntity.setCpvalornivel6((drDirec.getCpValorNivel6() == null) ? "" : drDirec.getCpValorNivel6());
        }
        if (direccionEntity.getIdtipodireccion().equals("IT")) {
            direccionEntity.setMztiponivel6(drDirec.getMzTipoNivel6());
            direccionEntity.setMzvalornivel6((drDirec.getMzValorNivel6() == null) ? "" : drDirec.getMzValorNivel6());
            direccionEntity.setItTipoPlaca(drDirec.getItTipoPlaca());
            direccionEntity.setItValorPlaca((drDirec.getItValorPlaca() == null) ? "" : drDirec.getItValorPlaca());

        }
        direccionEntity.setEstadoDir(drDirec.getIdTipoDireccion() == null ? "" : drDirec.getIdTipoDireccion());
        direccionEntity.setEstrato("0");
        return direccionEntity;
    }

    /**
     * El metodo createEntityDirDetalleAlterna se encarga de crear un objeto de
     * tipo CmtDireccionMgl con los datos que se obtienen del componente.
     *
     * @author alejandro.martine.ext@claro.com.co
     *
     * @param cmtDireccion
     * @param drDirecion
     * @return cmtDireccion
     */
    public CmtDireccionMgl createEntityDirDetalleAlterna(CmtDireccionMgl cmtDireccion, DrDireccion drDirecion) {
        this.drDirec = drDirecion;
        cmtDireccion.setCodTipoDir(drDirec.getIdTipoDireccion());
        cmtDireccion.setBarrio(drDirec.getBarrioTxtBM());
        if (cmtDireccion.getCodTipoDir().equals("CK")) {
            //Direccion Calle - Carrera
            cmtDireccion.setTipoViaPrincipal(drDirec.getTipoViaPrincipal());
            cmtDireccion.setNumViaPrincipal(drDirec.getNumViaPrincipal());
            cmtDireccion.setLtViaPrincipal(drDirec.getLtViaPrincipal());
            cmtDireccion.setNlPostViaP(drDirec.getNlPostViaP());
            cmtDireccion.setBisViaPrincipal(drDirec.getBisViaPrincipal());
            cmtDireccion.setCuadViaPrincipal(drDirec.getCuadViaPrincipal());
            cmtDireccion.setPlacaDireccion(drDirec.getPlacaDireccion());
            //Direccion Generadora de Calle - carrera
            cmtDireccion.setTipoViaGeneradora(drDirec.getTipoViaGeneradora());
            cmtDireccion.setNumViaGeneradora(drDirec.getNumViaGeneradora());
            cmtDireccion.setLtViaGeneradora(drDirec.getLtViaGeneradora());
            cmtDireccion.setNlPostViaG(drDirec.getNlPostViaG());
            cmtDireccion.setBisViaGeneradora(drDirec.getBisViaGeneradora());
            cmtDireccion.setCuadViaGeneradora(drDirec.getCuadViaGeneradora());
        }
        if (cmtDireccion.getCodTipoDir().equals("BM")
                || cmtDireccion.getCodTipoDir().equals("IT")) {
            //Direccion Manzana-Casa
            cmtDireccion.setMzTipoNivel1(drDirec.getMzTipoNivel1());
            cmtDireccion.setMzTipoNivel2(drDirec.getMzTipoNivel2());
            cmtDireccion.setMzTipoNivel3(drDirec.getMzTipoNivel3());
            cmtDireccion.setMzTipoNivel4(drDirec.getMzTipoNivel4());
            cmtDireccion.setMzTipoNivel5(drDirec.getMzTipoNivel5());
            cmtDireccion.setMzValorNivel1(drDirec.getMzValorNivel1());
            cmtDireccion.setMzValorNivel2(drDirec.getMzValorNivel2());
            cmtDireccion.setMzValorNivel3(drDirec.getMzValorNivel3());
            cmtDireccion.setMzValorNivel4(drDirec.getMzValorNivel4());
            cmtDireccion.setMzValorNivel5(drDirec.getMzValorNivel5());
        }
        if (cmtDireccion.getCodTipoDir().equals("CK")
                || cmtDireccion.getCodTipoDir().equals("BM")
                || cmtDireccion.getCodTipoDir().equals("IT")) {
            //Complemeto o intraduciple
            cmtDireccion.setCpTipoNivel1(drDirec.getCpTipoNivel1());
            cmtDireccion.setCpTipoNivel2(drDirec.getCpTipoNivel2());
            cmtDireccion.setCpTipoNivel3(drDirec.getCpTipoNivel3());
            cmtDireccion.setCpTipoNivel4(drDirec.getCpTipoNivel4());
            cmtDireccion.setCpTipoNivel5(drDirec.getCpTipoNivel5());
            cmtDireccion.setCpTipoNivel6(drDirec.getCpTipoNivel6());

            cmtDireccion.setCpValorNivel1(drDirec.getCpValorNivel1());
            cmtDireccion.setCpValorNivel2(drDirec.getCpValorNivel2());
            cmtDireccion.setCpValorNivel3(drDirec.getCpValorNivel3());
            cmtDireccion.setCpValorNivel4(drDirec.getCpValorNivel4());
            cmtDireccion.setCpValorNivel5(drDirec.getCpValorNivel5());
            cmtDireccion.setCpValorNivel6(drDirec.getCpValorNivel6());
        }
        if (cmtDireccion.getCodTipoDir().equals("IT")) {
            cmtDireccion.setMzTipoNivel6(drDirec.getMzTipoNivel6());
            cmtDireccion.setMzValorNivel6(drDirec.getMzValorNivel6());
            cmtDireccion.setItTipoPlaca(drDirec.getItTipoPlaca());
            cmtDireccion.setItValorPlaca(drDirec.getItValorPlaca());

        }
        cmtDireccion.setEstadoDirGeo(drDirec.getIdTipoDireccion());

        return cmtDireccion;
    }

    public List<Solicitud> findByDrDireccion(DrDireccion drDireccion, String centroPoblado)
            throws ApplicationException {
        return drDireccionDaoImpl.findByDrDireccion(drDireccion, centroPoblado);
    }

    /**
     * Construye la direccion para una Solicitud.Permite crear una direccion
 detallada campo a campo para luego se usada en una solicitud.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud
     * @return respuesta con el proceso de creacion de la solicitud
     * @throws ApplicationException
     *
     */
    public ResponseConstruccionDireccion construirDireccionSolicitud(
            RequestConstruccionDireccion request) throws ApplicationException {
        try {
            ResponseConstruccionDireccion response = null;
            if (request.getDrDireccion() == null) {
                throw new ApplicationException("El atributo DrDireccion no "
                        + "puede ser null");
            }
            String value = request.getDrDireccion().getIdTipoDireccion();
            if (value == null || value.trim().isEmpty()) {
                throw new ApplicationException("Se debe especificar el tipo de "
                        + "direccion (CK,BM,IT) en el atributo DrDireccion");
            }

            if (request.getTipoAdicion() == null
                    || request.getTipoAdicion().trim().isEmpty()) {
                throw new ApplicationException(
                        "Debe indicar que tipo de nivel. "
                        + "P:Principa : C-Complemento : A-Apartamento : N-No"
                        + " Complemento");
            }

            //Validamos si se esta agregando un nivel ya existente en la direccion
            validaDuplicidadNivelDireccion(request.getDrDireccion(),
                    request.getTipoAdicion(),
                    request.getTipoNivel());

            Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(value);
            switch (valida) {
                case CK:
                    response = construirDireccionCkSolicitud(request);
                    break;
                case BM:
                    response = construirDireccionBmItSolicitud(request);
                    break;
                case IT:
                    response = construirDireccionBmItSolicitud(request);
                    break;
            }
            if (response != null && response.getDrDireccion() != null) {
                response.setDireccionStr(getDireccion(response.getDrDireccion()));
            }
            return response;
        } catch (ApplicationException e) {
            String msg = "Ha ocurrido un error construyendo"
                    + " la direccion: " + e.getMessage();
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Construye una direccion Calle-Carrera para una Solicitud.Permite crear
 una direccion detallada Calle-Carrera campo a campo para luego ser usada
 en una solicitud.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud
     * @return respuesta con el proceso de creacion de la solicitud
     * @throws ApplicationException
     *
     */
    public ResponseConstruccionDireccion construirDireccionCkSolicitud(
            RequestConstruccionDireccion request) throws ApplicationException {
        try {

            CmtValidadorDireccionesManager validadorDireccionesManager
                    = new CmtValidadorDireccionesManager();
            GeograficoPoliticoMgl politicoMgl = new GeograficoPoliticoMgl();
            
            /* @author Juan David Hernandez */
             //Si la comunidad no es un numero (codigoDane) es porque viene de visor en formato RR
             boolean comunidadVisor = isNumeric(request.getComunidad());
             /*Si la comunidad llega desde visor en formato RR se hace la busqueda de la comunidad para 
             obtener el centro poblado y extraer el codigo dane para seguir con el proceso normal de MGL*/
            if (!comunidadVisor) {
                CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
                CmtComunidadRr cmtComunidadRr =
                        cmtComunidadRrManager.findComunidadByComunidad(request.getComunidad());
                //Se reemplaza la comunidad RR por el codigo dane del centro poblado
                if (cmtComunidadRr != null && cmtComunidadRr.getCiudad() != null) {
                    request.setComunidad(cmtComunidadRr.getCiudad().getGeoCodigoDane());
                } else {
                    throw new ApplicationException(" Comunidad RR no tiene configurado"
                            + " la ciudad en MGL para obtener el codigo dane");
                }
            }

            /* @author Juan David Hernandez */
            /*Se realiza busqueda del centro poblado por CodDane.*/
            if (request.getComunidad() != null ) {
                GeograficoPoliticoManager geograficoPoliticoMglManager =
                        new GeograficoPoliticoManager();
                politicoMgl = geograficoPoliticoMglManager.findCityByComundidad(request.getComunidad());
            }
            
            if (request.getComunidad() != null) {
                GeograficoPoliticoManager geograficoPoliticoMglManager
                        = new GeograficoPoliticoManager();               
                if (request.getComunidad() == null || request.getComunidad().length() <= 3 ||
                        request.getComunidad().isEmpty()) {
                    HhppMglManager hhppMglManager = new HhppMglManager();
                    DireccionMglManager direccionMglManager = new DireccionMglManager();
                    UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();
                    UbicacionMgl ubicacionMgl;
                    HhppMgl hhpp = hhppMglManager.findById(request.getDrDireccion().getId());
                    DireccionMgl direccionMgl = direccionMglManager.findById(hhpp.getDireccionObj());
                    ubicacionMgl = ubicacionMglManager.findById(direccionMgl.getUbicacion());
                    politicoMgl
                            = geograficoPoliticoMglManager.findGeoPoliticoById(ubicacionMgl.getGpoIdObj().getGpoId());
                } else {
                    politicoMgl
                            = geograficoPoliticoMglManager.findCityByComundidad(request.getComunidad());
                }
            }            

            if (politicoMgl == null || politicoMgl.getGpoId() == null) {
                throw new ApplicationException("Comunidad no tiene codigo dane en Ciudades");
            }
            DrDireccion dirConst = request.getDrDireccion();
            if (request.getTipoAdicion() != null && !request.getTipoAdicion().trim().isEmpty()) {
                if (request.getTipoAdicion().trim().equalsIgnoreCase("C")
                        || request.getTipoAdicion().trim().equalsIgnoreCase("A")) {
                    dirConst = agregaNivelCompAptoDireccion(dirConst, request.getTipoAdicion(),
                            request.getTipoNivel(), request.getValorNivel());
                } else if (request.getTipoAdicion().trim().equalsIgnoreCase("N")) {
                    if (request.getDireccionStr() == null
                            || request.getDireccionStr().trim().isEmpty()) {
                        throw new ApplicationException("Debe Ingresar Direccion");
                    }
                    //TABULA LA DIRECCION CON EL CODIGO ENTREGADO POR SERVIINFROMACIÓN
                    dirConst = validadorDireccionesManager.convertirDireccionStringADrDireccion(
                            request.getDireccionStr(), politicoMgl.getGpoId());

                    if (dirConst == null) {
                        ResponseConstruccionDireccion responde = new ResponseConstruccionDireccion();
                        ResponseMesaje responseMesajes = new ResponseMesaje();
                        responseMesajes.setTipoRespuesta("E");

                        responseMesajes.setMensaje(StringUtils.isBlank(validadorDireccionesManager.getErrorDescription())
                                ? "La direccion no es traducible" : validadorDireccionesManager.getErrorDescription());

                        responde.setResponseMesaje(responseMesajes);
                        responde.setDrDireccion(null);
                        responde.setDireccionRespuestaGeo(null);
                        return responde;
                    }
                }
            }
            dirConst.setBarrio(request.getBarrio());
            ResponseConstruccionDireccion response = new ResponseConstruccionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setTipoRespuesta("I");
            responseMesaje.setMensaje("Proceso Exitoso");
            response.setResponseMesaje(responseMesaje);
            response.setDrDireccion(dirConst);
            if (dirConst.getDireccionRespuestaGeo() != null && !dirConst.getDireccionRespuestaGeo().isEmpty()) {
                response.setDireccionRespuestaGeo(dirConst.getDireccionRespuestaGeo());
            }
            return response;
        } catch (ApplicationException e) {
            String msg = "No fue posible construir"
                    + " la direccion Calle-Carrera: " + e.getMessage();
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Construye una direccion Barrio-Mazana para una Solicitud.Permite crear
 una direccion detallada Barrio-Mazana campo a campo para luego ser usada
 en una solicitud.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud
     * @return respuesta con el proceso de creacion de la solicitud
     * @throws ApplicationException
     *
     */
    public ResponseConstruccionDireccion construirDireccionBmItSolicitud(
            RequestConstruccionDireccion request) throws ApplicationException {
        try {

            DrDireccion dirConst = null;
            if (request.getTipoAdicion().trim().equalsIgnoreCase("P")) {
                dirConst = agregaNivelPrincipalDireccion(
                        request.getDrDireccion(),
                        request.getTipoAdicion(),
                        request.getTipoNivel(),
                        request.getValorNivel());
            } else if (request.getTipoAdicion().trim().equalsIgnoreCase("C")
                    || request.getTipoAdicion().trim().equalsIgnoreCase("A")
                    || request.getTipoAdicion().trim().equalsIgnoreCase("N")) {
                dirConst = agregaNivelCompAptoDireccion(
                        request.getDrDireccion(),
                        request.getTipoAdicion(),
                        request.getTipoNivel(),
                        request.getValorNivel());
            }
            ResponseConstruccionDireccion response = new ResponseConstruccionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setTipoRespuesta("I");
            responseMesaje.setMensaje("Proceso Exitoso");
            response.setResponseMesaje(responseMesaje);
            response.setDrDireccion(dirConst);
            return response;
        } catch (ApplicationException e) {
            String msg = "Error construyendo"
                    + " la direccion Barrio-Manzana: " + e.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, e);
        }

    }

    /**
     * Adiciona un nivel a un detalle de un Direccion.Permite adicionar un
 nivel tipo Principal a un detalle de direccion.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion de la direccion
     * @param tipoAdicion indica si se debe adicionar un complemento o un tipo
     * de apartamento
     * @param tipoNivel indica el tipo del nivel a adicionar
     * @param valorNivel indica el valor del nivel a adicionar
     * @return direccion con el nivel adicional
     * @throws ApplicationException
     *
     */
    public DrDireccion agregaNivelPrincipalDireccion(DrDireccion request,
            String tipoAdicion, String tipoNivel, String valorNivel)
            throws ApplicationException {

        try {
            if (tipoAdicion == null || tipoAdicion.trim().isEmpty()
                    || !tipoAdicion.trim().equalsIgnoreCase("P")) {
                throw new ApplicationException(
                        "Debe indicar tipo de nivel se va a adicionar a la "
                        + "direccion, P-Principal : C-Complemento : A-Apartamento : N-No Complemento");
            }
            if (request == null) {
                throw new ApplicationException(
                        "El atributo DrDireccion no puede ser nulo");
            }
            String value = request.getIdTipoDireccion();
            if (value == null || value.trim().isEmpty()) {
                throw new ApplicationException("Se debe especificar el tipo de "
                        + "direccion (CK,BM,IT) en el atributo DrDireccion");
            }

            Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(value);
            switch (valida) {
                case CK:
                    break;
                case BM:
                    request = agregaNivelPrincipalDireccionBm(request, tipoNivel, valorNivel);
                    break;
                case IT:
                    request = agregaNivelPrincipalDireccionIt(request, tipoNivel, valorNivel);
                    break;
            }
            
        } catch (ApplicationException e) {
            String msg = "Error agregando un nivel Principal a"
                    + " la direccion: " + e.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, e);
        }
        return request;

    }

    /**
     * Adiciona un nivel a un detalle de un Direccion.Permite adicionar un
 nivel tipo Principal a un detalle de direccion tipo Barrio Manzana.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion de la direccion de apartamento
     * @param tipoNivel indica el tipo del nivel a adicionar
     * @param valorNivel indica el valor del nivel a adicionar
     * @return direccion con el nivel adicional
     * @throws ApplicationException
     *
     */
    public DrDireccion agregaNivelPrincipalDireccionBm(DrDireccion request,
            String tipoNivel, String valorNivel)
            throws ApplicationException {
        try {
            if (tipoNivel == null || valorNivel == null) {
                throw new ApplicationException(
                        "Debe el nivel o el valor no pueden ser nulos");
            }
            CmtComponenteDireccionesMglManager componenteDireccionesMglManager
                    = new CmtComponenteDireccionesMglManager();
            ConfigurationAddressComponent configurationAddressComponent
                    = componenteDireccionesMglManager.getConfiguracionComponente();
            BmValues bmValues = configurationAddressComponent.getBmValues();
            if (tipoNivel.equalsIgnoreCase("barrio")) {
                request.setBarrio(valorNivel);
            } else if (bmValues != null) {
                boolean procesado = false;

                if (!procesado && request.getMzTipoNivel1() == null
                        && bmValues.getTipoConjuntoBm() != null
                        && !bmValues.getTipoConjuntoBm().isEmpty()) {
                    for (OpcionIdNombre item : bmValues.getTipoConjuntoBm()) {
                        if (item.getIdParametro().equalsIgnoreCase(tipoNivel)) {
                            item.getIdParametro();
                            if (((item.getIdParametro().equalsIgnoreCase("urbanizacion") || item.getIdParametro().equalsIgnoreCase("ciudadela") || item.getIdParametro().equalsIgnoreCase("conjunto residencial"))) && (request.getBarrio() == null)) {
                                request.setBarrio(tipoNivel + " " + valorNivel.toUpperCase());
                            } else {
                                if (request.getBarrio() != null && !request.getBarrio().isEmpty()) {
                                    request.setBarrio(request.getBarrio().toUpperCase());
                                }
                            }
                            request.setMzTipoNivel1(tipoNivel);
                            request.setMzValorNivel1(valorNivel);
                            procesado = true;
                            break;
                        }
                    }

                }
                if (!procesado && request.getMzTipoNivel2() == null
                        && bmValues.getSubdivision1Bm() != null
                        && !bmValues.getSubdivision1Bm().isEmpty()) {
                    for (OpcionIdNombre item : bmValues.getSubdivision1Bm()) {
                        if (item.getIdParametro().equalsIgnoreCase(tipoNivel)) {
                            item.getIdParametro();
                            request.setMzTipoNivel2(tipoNivel);
                            request.setMzValorNivel2(valorNivel);
                            procesado = true;
                            break;
                        }
                    }
                }
                if (!procesado && request.getMzTipoNivel3() == null
                        && bmValues.getSubdivision2Bm() != null
                        && !bmValues.getSubdivision2Bm().isEmpty()) {
                    for (OpcionIdNombre item : bmValues.getSubdivision2Bm()) {
                        if (item.getIdParametro().equalsIgnoreCase(tipoNivel)) {
                            item.getIdParametro();
                            request.setMzTipoNivel3(tipoNivel);
                            request.setMzValorNivel3(valorNivel);
                            procesado = true;
                            break;
                        }
                    }
                }
                if (!procesado && request.getMzTipoNivel4() == null
                        && bmValues.getSubdivision3Bm() != null
                        && !bmValues.getSubdivision3Bm().isEmpty()) {
                    for (OpcionIdNombre item : bmValues.getSubdivision3Bm()) {
                        if (item.getIdParametro().equalsIgnoreCase(tipoNivel)) {
                            item.getIdParametro();
                            request.setMzTipoNivel4(tipoNivel);
                            request.setMzValorNivel4(valorNivel);
                            procesado = true;
                            break;
                        }
                    }
                }
                if (!procesado && request.getMzTipoNivel5() == null
                        && bmValues.getSubdivision4Bm() != null
                        && !bmValues.getSubdivision4Bm().isEmpty()) {
                    for (OpcionIdNombre item : bmValues.getSubdivision4Bm()) {
                        if (item.getIdParametro().equalsIgnoreCase(tipoNivel)) {
                            item.getIdParametro();
                            request.setMzTipoNivel5(tipoNivel);
                            request.setMzValorNivel5(valorNivel);
                            procesado = true;
                            break;
                        }
                    }
                }
                if (!procesado) {
                    throw new ApplicationException(
                            "No fue posible Asignar el tipo y valor en la direccion Barrio Manzana");
                }

            } else {
                throw new ApplicationException(
                        "No fue posible cargar la configuracion de la Direccion Barrio Manzana");
            }
            return request;
        } catch (ApplicationException e) {
            String msg = "Error agregando un nivel Principal a"
                    + " la dirección: " + e.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, e);
        }

    }

    /**
     * Adiciona un nivel a un detalle de un Direccion.Permite adicionar un
 nivel tipo Principal a un detalle de direccion tipo Intraducible.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion de la direccion de apartamento
     * @param tipoNivel indica el tipo del nivel a adicionar
     * @param valorNivel indica el valor del nivel a adicionar
     * @return direccion con el nivel adicional
     * @throws ApplicationException Excepción en caso de error
     *
     */
    public DrDireccion agregaNivelPrincipalDireccionIt(DrDireccion request,
            String tipoNivel, String valorNivel)
            throws ApplicationException {
        try {
            if (tipoNivel == null || valorNivel == null) {
                throw new ApplicationException(
                        "Debe el nivel o el valor no pueden ser nulos");
            }

            CmtComponenteDireccionesMglManager componenteDireccionesMglManager = new CmtComponenteDireccionesMglManager();
            ConfigurationAddressComponent configurationAddressComponent = componenteDireccionesMglManager.getConfiguracionCompletaComponente();
            ItValues itValues = configurationAddressComponent.getItValues();

            if (Objects.isNull(itValues)) {
                throw new ApplicationException(
                        "No fue posible cargar la configuracion de la Direccion Intraducible");
            }

            AtomicBoolean procesado = new AtomicBoolean(false);
            procesarNivel1Intraducible(request, tipoNivel, valorNivel, itValues, procesado);
            procesarNivel2Intraducible(request, tipoNivel, valorNivel, itValues, procesado);
            procesarNivel3Intraducible(request, tipoNivel, valorNivel, itValues, procesado);
            procesarNivel4Intraducible(request, tipoNivel, valorNivel, itValues, procesado);
            procesarNivel5Intraducible(request, tipoNivel, valorNivel, itValues, procesado);
            procesarNivel6Intraducible(request, tipoNivel, valorNivel, itValues, procesado);

            if (request.getItTipoPlaca() == null
                    && CollectionUtils.isNotEmpty(itValues.getPlacaIt())) {
                itValues.getPlacaIt().stream()
                        .filter(item -> tipoNivel.equalsIgnoreCase(item.getIdParametro()))
                        .findFirst()
                        .ifPresent(item -> {
                            request.setItTipoPlaca(tipoNivel);
                            request.setItValorPlaca(valorNivel);
                            procesado.set(true);
                        });
            }

            if (!procesado.get()) {
                throw new ApplicationException("No fue posible Asignar el tipo y valor en la direccion Intraducible");
            }

        } catch (ApplicationException e) {
            String msg = "Error agregando un nivel Principal a"
                    + " la dirección: " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return request;
    }

    /**
     * Procesa el nivel 6 de dirección intraducible.
     * @param request Objeto de tipo DrDireccion
     * @param tipoNivel Indica el tipo de nivel a procesar
     * @param valorNivel Indica el valor del nivel a procesar
     * @param itValues Configuracion de la direccion intraducible
     * @param procesado Indica si el nivel fue procesado
     * @author Gildardo Mora
     */
    private void procesarNivel6Intraducible(DrDireccion request, String tipoNivel, String valorNivel, ItValues itValues, AtomicBoolean procesado) {
        if (request.getMzTipoNivel6() == null
                && CollectionUtils.isNotEmpty(itValues.getIntr6It())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel1())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel2())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel3())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel4())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel5())) {
            itValues.getIntr6It().stream()
                    .filter(item -> tipoNivel.equalsIgnoreCase(item.getIdParametro()))
                    .findFirst()
                    .ifPresent(item -> {
                        request.setMzTipoNivel6(tipoNivel);
                        request.setMzValorNivel6(valorNivel);
                        procesado.set(true);
                    });
        }
    }

    /**
     * Procesa el nivel 5 de dirección intraducible.
     * @param request Objeto de tipo DrDireccion
     * @param tipoNivel Indica el tipo de nivel a procesar
     * @param valorNivel Indica el valor del nivel a procesar
     * @param itValues Configuración de la dirección intraducible
     * @param procesado Indica si el nivel fue procesado
     * @author Gildardo Mora
     */
    private void procesarNivel5Intraducible(DrDireccion request, String tipoNivel, String valorNivel, ItValues itValues, AtomicBoolean procesado) {
        if (request.getMzTipoNivel5() == null
                && CollectionUtils.isNotEmpty(itValues.getIntr5It())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel1())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel2())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel3())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel4())) {
            itValues.getIntr5It().stream()
                    .filter(item -> tipoNivel.equalsIgnoreCase(item.getIdParametro()))
                    .findFirst()
                    .ifPresent(item -> {
                        request.setMzTipoNivel5(tipoNivel);
                        request.setMzValorNivel5(valorNivel);
                        procesado.set(true);
                    });
        }
    }

    /**
     * Procesa el nivel 4 de dirección intraducible.
     * @param request Objeto de tipo DrDireccion
     * @param tipoNivel Indica el tipo de nivel a procesar
     * @param valorNivel Indica el valor del nivel a procesar
     * @param itValues Configuración de la dirección intraducible
     * @param procesado Indica si el nivel fue procesado
     * @author Gildardo Mora
     */
    private void procesarNivel4Intraducible(DrDireccion request, String tipoNivel, String valorNivel, ItValues itValues, AtomicBoolean procesado) {
        if (request.getMzTipoNivel4() == null
                && CollectionUtils.isNotEmpty(itValues.getIntr4It())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel1())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel2())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel3())) {
            itValues.getIntr4It().stream()
                    .filter(item -> tipoNivel.equalsIgnoreCase(item.getIdParametro()))
                    .findFirst()
                    .ifPresent(item -> {
                        request.setMzTipoNivel4(tipoNivel);
                        request.setMzValorNivel4(valorNivel);
                        procesado.set(true);
                    });
        }
    }

    /**
     * Procesa el nivel 3 de dirección intraducible.
     * @param request Objeto de tipo DrDireccion
     * @param tipoNivel Indica el tipo de nivel a procesar
     * @param valorNivel Indica el valor del nivel a procesar
     * @param itValues Configuración de la dirección intraducible
     * @param procesado Indica si el nivel fue procesado
     * @author Gildardo Mora
     */
    private void procesarNivel3Intraducible(DrDireccion request, String tipoNivel, String valorNivel, ItValues itValues, AtomicBoolean procesado) {
        if (request.getMzTipoNivel3() == null
                && CollectionUtils.isNotEmpty(itValues.getIntr3It())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel1())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel2())) {
            itValues.getIntr3It().stream()
                    .filter(item -> tipoNivel.equalsIgnoreCase(item.getIdParametro()))
                    .findFirst()
                    .ifPresent(item -> {
                        request.setMzTipoNivel3(tipoNivel);
                        request.setMzValorNivel3(valorNivel);
                        procesado.set(true);
                    });
        }
    }

    /**
     * Procesa el nivel 2 de dirección intraducible.
     * @param request Objeto de tipo DrDireccion
     * @param tipoNivel Indica el tipo de nivel a procesar
     * @param valorNivel Indica el valor del nivel a procesar
     * @param itValues Configuración de la dirección intraducible
     * @param procesado Indica si el nivel fue procesado
     * @author Gildardo Mora
     */
    private void procesarNivel2Intraducible(DrDireccion request, String tipoNivel, String valorNivel, ItValues itValues, AtomicBoolean procesado) {
        if (request.getMzTipoNivel2() == null
                && CollectionUtils.isNotEmpty(itValues.getIntr2It())
                && !tipoNivel.equalsIgnoreCase(request.getMzTipoNivel1())) {
            itValues.getIntr2It().stream()
                    .filter(item -> tipoNivel.equalsIgnoreCase(item.getIdParametro()))
                    .findFirst()
                    .ifPresent(item -> {
                        request.setMzTipoNivel2(tipoNivel);
                        request.setMzValorNivel2(valorNivel);
                        procesado.set(true);
                    });
        }
    }

    /**
     * Procesa el nivel 1 de dirección intraducible.
     * @param request Objeto de tipo DrDireccion
     * @param tipoNivel Indica el tipo de nivel a procesar
     * @param valorNivel Indica el valor del nivel a procesar
     * @param itValues Configuración de la dirección intraducible
     * @param procesado Indica si el nivel fue procesado
     * @author Gildardo Mora
     */
    private void procesarNivel1Intraducible(DrDireccion request, String tipoNivel, String valorNivel, ItValues itValues, AtomicBoolean procesado) {
        if (request.getMzTipoNivel1() == null && CollectionUtils.isNotEmpty(itValues.getIntr1It())) {
            itValues.getIntr1It().stream()
                    .filter(item -> tipoNivel.equalsIgnoreCase(item.getIdParametro()))
                    .findFirst()
                    .ifPresent(item -> {
                        request.setMzTipoNivel1(tipoNivel);
                        request.setMzValorNivel1(valorNivel);
                        procesado.set(true);
                    });
        }
    }

    /**
     * Adiciona un nivel a un detalle de un Direccion.Permite adicionar un
 nivel tipo complemento o apartamento a un detalle de direccion.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion de la direccion
     * @param tipoAdicion indica si se debe adicionar un complemento o un tipo
     * de apartamento
     * @param tipoNivel indica el tipo del nivel a adicionar
     * @param valorNivel indica el valor del nivel a adicionar
     * @return direccion con el nivel adicional
     * @throws ApplicationException
     *
     */
    public DrDireccion agregaNivelCompAptoDireccion(DrDireccion request,
            String tipoAdicion, String tipoNivel, String valorNivel)
            throws ApplicationException {
        try {
            if (tipoAdicion == null || tipoAdicion.trim().isEmpty()
                    || !(tipoAdicion.trim().equalsIgnoreCase("C")
                    || tipoAdicion.trim().equalsIgnoreCase("A")
                    || tipoAdicion.trim().equalsIgnoreCase("N"))) {
                throw new ApplicationException(
                        "Debe indicar que tipo de nivel se va a adicionar a la "
                        + "direccion, C-Complemento : A-Apartamento : N-No Complemento");
            }
            if (tipoAdicion.trim().equalsIgnoreCase("C")) {

                if (tipoNivel == null || valorNivel == null
                        || tipoNivel.trim().isEmpty()
                        || valorNivel.trim().isEmpty()) {
                    throw new ApplicationException(
                            "Debe indicar Tipo y Valor para el nivel Complemento a adicionar");
                }
                if (request.getCpTipoNivel1() == null
                        || request.getCpTipoNivel1().trim().isEmpty()) {
                    request.setCpTipoNivel1(tipoNivel);
                    request.setCpValorNivel1(valorNivel);
                } else if (request.getCpTipoNivel2() == null
                        || request.getCpTipoNivel2().trim().isEmpty()) {
                    request.setCpTipoNivel2(tipoNivel);
                    request.setCpValorNivel2(valorNivel);
                } else if (request.getCpTipoNivel3() == null
                        || request.getCpTipoNivel3().trim().isEmpty()) {
                    request.setCpTipoNivel3(tipoNivel);
                    request.setCpValorNivel3(valorNivel);
                } else if (request.getCpTipoNivel4() == null
                        || request.getCpTipoNivel4().trim().isEmpty()) {
                    request.setCpTipoNivel4(tipoNivel);
                    request.setCpValorNivel4(valorNivel);
                } else {
                    throw new ApplicationException("No es posible agregar el nivel");
                }
            } else if (tipoAdicion.trim().equalsIgnoreCase("A")) {
                if (tipoNivel == null || tipoNivel.trim().isEmpty()) {
                    throw new ApplicationException(
                            "Debe indicar Tipo para el nivel Apartamento a adicionar");
                }
                if (!tipoNivel.trim().isEmpty()
                        && !tipoNivel.trim().equalsIgnoreCase("CASA")
                        && !tipoNivel.trim().equalsIgnoreCase("FUENTE")
                        && !tipoNivel.trim().equalsIgnoreCase("RECEPTOR")                        
                        && (valorNivel == null || valorNivel.trim().isEmpty())) {
                    throw new ApplicationException(
                            "Debe indicar Valor para el nivel Apartamento a adicionar");
                }

                if (request.getCpTipoNivel5() == null
                        || request.getCpTipoNivel5().trim().isEmpty()) {
                    request.setCpTipoNivel5(tipoNivel);
                    request.setCpValorNivel5(valorNivel);
                } else if (request.getCpTipoNivel5().trim().equalsIgnoreCase(
                        Constant.OPT_CASA_PISO)
                        || request.getCpTipoNivel5().trim().equalsIgnoreCase(
                                Constant.OPT_PISO_INTERIOR)
                        || request.getCpTipoNivel5().trim().equalsIgnoreCase(
                                Constant.OPT_PISO_LOCAL)
                        || request.getCpTipoNivel5().trim().equalsIgnoreCase(
                                Constant.OPT_PISO_APTO)) {

                        request.setCpTipoNivel6(tipoNivel);
                        request.setCpValorNivel6(valorNivel);
                    
                } else {
                    if (request.getCpTipoNivel5() != null
                            && (!request.getCpTipoNivel5().trim().equalsIgnoreCase(
                                    Constant.OPT_CASA_PISO)
                            || !request.getCpTipoNivel5().trim().equalsIgnoreCase(
                                    Constant.OPT_PISO_INTERIOR)
                            || !request.getCpTipoNivel5().trim().equalsIgnoreCase(
                                    Constant.OPT_PISO_LOCAL)
                            || !request.getCpTipoNivel5().trim().equalsIgnoreCase(
                                    Constant.OPT_PISO_APTO))) {
                        request.setCpTipoNivel5(tipoNivel);
                        request.setCpValorNivel5(valorNivel);

                    } else {
                        throw new ApplicationException("No es posible agregar el nivel");
                    }
                }
            }
            return request;
        } catch (ApplicationException e) {
            String msg = "Error agregando un nivel "
                    + "Complemento/Apartamento a "
                    + " la dirección: " + e.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Validad si un nivel ya se encuentre en la direccion.Permite validar si
 un nivel tipo complemento o apartamento a un detalle de direccion.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion de la direccion
     * @param tipoAdicion indica si se debe adicionar un complemento o un tipo
     * de apartamento
     * @param tipoNivel indica el tipo del nivel a adicionar
     * @throws ApplicationException
     *
     */
    public void validaDuplicidadNivelDireccion(DrDireccion request,
            String tipoAdicion, String tipoNivel)
            throws ApplicationException {
        if (tipoAdicion == null || tipoAdicion.trim().isEmpty()
                || !(tipoAdicion.trim().equalsIgnoreCase("C")
                || tipoAdicion.trim().equalsIgnoreCase("A")
                || tipoAdicion.trim().equalsIgnoreCase("P")
                || tipoAdicion.trim().equalsIgnoreCase("N"))) {
            throw new ApplicationException(
                    "Debe indicar que tipo de nivel se va a adicionar a la "
                    + "direccion,P-Principal : C-Complemento : A-Apartamento : N-No Complemento");
        }

        String value = request.getIdTipoDireccion();
        if (value == null || value.trim().isEmpty()) {
            throw new ApplicationException("Se debe especificar el tipo de "
                    + "direccion (CK,BM,IT) en el atributo DrDireccion");
        }
        boolean isDuplicate = false;
        //Validamos los niveles del complemento
        if (tipoAdicion.trim().equalsIgnoreCase("C")) {

            if (!isDuplicate && request.getCpTipoNivel1() != null
                    && !request.getCpTipoNivel1().trim().isEmpty()
                    && request.getCpTipoNivel1().trim().equalsIgnoreCase(tipoNivel)) {
                isDuplicate = true;
            } else if (!isDuplicate && request.getCpTipoNivel2() != null
                    && !request.getCpTipoNivel2().trim().isEmpty()
                    && request.getCpTipoNivel2().trim().equalsIgnoreCase(tipoNivel)) {
                isDuplicate = true;
            } else if (!isDuplicate && request.getCpTipoNivel3() != null
                    && !request.getCpTipoNivel3().trim().isEmpty()
                    && request.getCpTipoNivel3().trim().equalsIgnoreCase(tipoNivel)) {
                isDuplicate = true;
            } else if (!isDuplicate && request.getCpTipoNivel4() != null
                    && !request.getCpTipoNivel4().trim().isEmpty()
                    && request.getCpTipoNivel4().trim().equalsIgnoreCase(tipoNivel)) {
                isDuplicate = true;
            }
            //Validacion de los nivel apto
        } else if (tipoAdicion.trim().equalsIgnoreCase("A")) {

            if (!isDuplicate && request.getCpTipoNivel5() != null
                    && !request.getCpTipoNivel5().trim().isEmpty()
                    && request.getCpTipoNivel5().trim().equalsIgnoreCase(tipoNivel)) {
                isDuplicate = true;
            } else if (!isDuplicate && request.getCpTipoNivel6() != null
                    && !request.getCpTipoNivel6().trim().isEmpty()
                    && request.getCpTipoNivel6().trim().equalsIgnoreCase(tipoNivel)) {
                isDuplicate = true;
            }
            //Validacion de los niveles de la principal 
        } else if (tipoAdicion.trim().equalsIgnoreCase("P")) {
            if (value.equalsIgnoreCase("BM")
                    || value.equalsIgnoreCase("IT")) {
                if (!isDuplicate && request.getMzTipoNivel1() != null
                        && !request.getMzTipoNivel1().trim().isEmpty()
                        && request.getMzTipoNivel1().trim().equalsIgnoreCase(tipoNivel)) {
                    isDuplicate = true;
                } else if (!isDuplicate && request.getMzTipoNivel2() != null
                        && !request.getMzTipoNivel2().trim().isEmpty()
                        && request.getMzTipoNivel2().trim().equalsIgnoreCase(tipoNivel)) {
                    isDuplicate = true;
                } else if (!isDuplicate && request.getMzTipoNivel3() != null
                        && !request.getMzTipoNivel3().trim().isEmpty()
                        && request.getMzTipoNivel3().trim().equalsIgnoreCase(tipoNivel)) {
                    isDuplicate = true;
                } else if (!isDuplicate && request.getMzTipoNivel4() != null
                        && !request.getMzTipoNivel4().trim().isEmpty()
                        && request.getMzTipoNivel4().trim().equalsIgnoreCase(tipoNivel)) {
                    isDuplicate = true;
                } else if (!isDuplicate && request.getMzTipoNivel5() != null
                        && !request.getMzTipoNivel5().trim().isEmpty()
                        && request.getMzTipoNivel5().trim().equalsIgnoreCase(tipoNivel)) {
                    isDuplicate = true;
                }
            }
            if (value.equalsIgnoreCase("IT")) {
                if (!isDuplicate && request.getMzTipoNivel5() != null
                        && !request.getMzTipoNivel5().trim().isEmpty()
                        && request.getMzTipoNivel5().trim().equalsIgnoreCase(tipoNivel)) {
                    isDuplicate = true;
                } else if (!isDuplicate && request.getItTipoPlaca() != null
                        && !request.getItTipoPlaca().trim().isEmpty()
                        && request.getItTipoPlaca().trim().equalsIgnoreCase(tipoNivel)) {
                    isDuplicate = true;
                }
            }
        }

        if (isDuplicate) {
            throw new ApplicationException("El nivel que intenta adicionar a la direccion ya se encuentra");
        }

    }

    /**
     * Construye la direccion para una Solicitud. Permite crear una direccion
     * detallada campo a campo para luego se usada en una solicitud.
     *
     * @author Victor Bocanegra
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud
     * @return respuesta con el proceso de creacion de la solicitud
     *
     */
    public CmtResponseConstruccionDireccionDto construirDireccionSolicitudHhpp(
            CmtRequestConstruccionDireccionDto request) {

        CmtResponseConstruccionDireccionDto cmtResponseConstruccionDireccionDto = new CmtResponseConstruccionDireccionDto();
        try {
            RequestConstruccionDireccion construccionDireccion = new RequestConstruccionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            
            if (request == null) {               
                responseMesaje.setTipoRespuesta("E");
                responseMesaje.setMensaje("Es necesario construir una peticion para consumir el servicio");
                cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                return cmtResponseConstruccionDireccionDto;
            }
            
            if (request.getDrDireccion() == null) {
                responseMesaje.setTipoRespuesta("E");
                responseMesaje.setMensaje("Es necesario construir el DrDireccion de la petición para consumir el servicio");
                cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                return cmtResponseConstruccionDireccionDto;
            }            
            

            if (request.getDrDireccion() != null && request.getDrDireccion().getIdTipoDireccion() != null
                    && !request.getDrDireccion().getIdTipoDireccion().isEmpty()) {

                if (!request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")
                        && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")
                        && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")) {
                    responseMesaje.setTipoRespuesta("E");
                    responseMesaje.setMensaje("Los valores permitidos para tipo de direccion son CK, BM o IT");
                    cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                    return cmtResponseConstruccionDireccionDto;
                }else{
                    if (request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")) {
                        if (request.getDireccionStr() == null || request.getDireccionStr().trim().isEmpty()) {
                            responseMesaje.setTipoRespuesta("E");
                            responseMesaje.setMensaje("Debe ingresar el DireccionStr por ser una direccion Calle-Carrera");
                            cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                            return cmtResponseConstruccionDireccionDto;
                        }
                    }
                }
            } else {
                responseMesaje.setTipoRespuesta("E");
                responseMesaje.setMensaje("Debe ingresar el IdTipoDireccion por favor: Los valores permitidos son CK, BM o IT");
                cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                return cmtResponseConstruccionDireccionDto;
            } 

            if (request.getComunidad() == null || request.getComunidad().isEmpty()) {
                responseMesaje.setTipoRespuesta("E");
                responseMesaje.setMensaje("Debe ingresar el codigo dane en el campo comunidad");
                cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                return cmtResponseConstruccionDireccionDto;
            }            

                if (request.getTipoAdicion() != null && !request.getTipoAdicion().isEmpty()) {
                    if (!request.getTipoAdicion().equalsIgnoreCase("N") && !request.getTipoAdicion().equalsIgnoreCase("C")
                            && !request.getTipoAdicion().equalsIgnoreCase("P")
                            && !request.getTipoAdicion().equalsIgnoreCase("A")) {
                        responseMesaje.setTipoRespuesta("E");
                        responseMesaje.setMensaje("Los valores permitidos para TipoAdición son: N para calle "
                                + "carrera dirección hasta placa, C para complementos, P para dirección principal "
                                + "barrio-manzana e intraducible, A para apartamento");
                        cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                        return cmtResponseConstruccionDireccionDto;
                    }
                } else {
                    responseMesaje.setTipoRespuesta("E");
                    responseMesaje.setMensaje("Debe ingresar el tipo de adición: N para calle "
                            + "carrera dirección hasta placa, C para complementos, P para dirección principal "
                            + "barrio-manzana e intraducible, A para apartamento");
                    cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                    return cmtResponseConstruccionDireccionDto;
                }
                
            if (!request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")) {

                if (request.getTipoNivel() == null || request.getTipoNivel().trim().isEmpty()) {
                    responseMesaje.setTipoRespuesta("E");
                    responseMesaje.setMensaje("Debe ingresar el tipoNivel debido a que no es una direccion calle-carrera");
                    cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                    return cmtResponseConstruccionDireccionDto;
                }

                if ((request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM") || request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")) && request.getTipoAdicion().equalsIgnoreCase("A")) {
                    /* cuando IdTipoDireccion tenga el valor de BM (Barrio Manzana) o IT 

                    solo con esos valores se deberá de permitir que el campo 
                    valorNivel pueda ingresarse como nulo o vacío                    
                     */
                } else {
                    if (request.getValorNivel() == null || request.getValorNivel().trim().isEmpty()) {
                        responseMesaje.setTipoRespuesta("E");
                        responseMesaje.setMensaje("Debe ingresar el valorNivel debido a que no es una direccion calle-carrera");
                        cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                        return cmtResponseConstruccionDireccionDto;
                    }
                }
            }


            construccionDireccion.setDrDireccion(request.getDrDireccion());
            construccionDireccion.setDireccionStr(request.getDireccionStr());
            construccionDireccion.setComunidad(request.getComunidad());
            construccionDireccion.setBarrio(request.getBarrio());
            construccionDireccion.setTipoAdicion(request.getTipoAdicion());
            construccionDireccion.setTipoNivel(request.getTipoNivel());
            construccionDireccion.setValorNivel(request.getValorNivel());
            construccionDireccion.setIdUsuario(request.getIdUsuario());
            
            
              /* @author Juan David Hernandez */
             //Si la comunidad no es un numero (codigoDane) es porque viene de visor en formato RR
             boolean comunidadVisor = isNumeric(request.getComunidad());
             /*Si la comunidad llega desde visor en formato RR se hace la busqueda de la comunidad para 
             obtener el centro poblado y extraer el codigo dane para seguir con el proceso normal de MGL*/
            if (!comunidadVisor) {
                CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
                CmtComunidadRr cmtComunidadRr =
                        cmtComunidadRrManager.findComunidadByComunidad(request.getComunidad());
                //Se reemplaza la comunidad RR por el codigo dane del centro poblado
                if (cmtComunidadRr != null && cmtComunidadRr.getCiudad() != null) {
                    request.setComunidad(cmtComunidadRr.getCiudad().getGeoCodigoDane());
                } else {
                    throw new ApplicationException(" Comunidad RR no tiene configurado"
                            + " la ciudad en MGL para obtener el codigo dane");
                }
            }

             GeograficoPoliticoMgl centroPoblado = new GeograficoPoliticoMgl();
            /* @author Juan David Hernandez */
            /*Se realiza busqueda del centro poblado por CodDane.*/
            if (request.getComunidad() != null ) {
                GeograficoPoliticoManager geograficoPoliticoMglManager =
                        new GeograficoPoliticoManager();
                centroPoblado =
                        geograficoPoliticoMglManager.findCityByComundidad(request.getComunidad());
            }
            
            if(centroPoblado != null && centroPoblado.getGpoMultiorigen().equalsIgnoreCase("1") &&
                    request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK") && 
                    (request.getDrDireccion().getBarrio() == null || request.getDrDireccion().getBarrio().isEmpty())){
                responseMesaje.setTipoRespuesta("E");
                responseMesaje.setMensaje("La direccion calle-carrera pertenece"
                        + " a una ciudad multiorigen, es necesario ingresar el barrio en el drDireccion");
                cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                return cmtResponseConstruccionDireccionDto;                
            }            

            ResponseConstruccionDireccion responseConstruccionDireccion = construirDireccionSolicitud(construccionDireccion);
            if (responseConstruccionDireccion != null) {
                cmtResponseConstruccionDireccionDto = new CmtResponseConstruccionDireccionDto();
                cmtResponseConstruccionDireccionDto.setDrDireccion(responseConstruccionDireccion.getDrDireccion());
                cmtResponseConstruccionDireccionDto.setBarrioList(responseConstruccionDireccion.getBarrioList());
                cmtResponseConstruccionDireccionDto.setDireccionStr(responseConstruccionDireccion.getDireccionStr());
                cmtResponseConstruccionDireccionDto.setResponseMesaje(responseConstruccionDireccion.getResponseMesaje());
            } else {
                cmtResponseConstruccionDireccionDto = new CmtResponseConstruccionDireccionDto();
                responseMesaje.setTipoRespuesta("E");
                responseMesaje.setMensaje("Ocurrio un error construyendo la direccion" + " " + request.getDireccionStr());
                cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
            }

            return cmtResponseConstruccionDireccionDto;

        } catch (ApplicationException e) {
            String msg = "Ocurrio un error construyendo la direccion" + " " + ((request != null) ? request.getDireccionStr() : "") + ": " + e.getMessage();
            LOGGER.error(msg);
            
            cmtResponseConstruccionDireccionDto = new CmtResponseConstruccionDireccionDto();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setTipoRespuesta("E");
            responseMesaje.setMensaje(msg);
            cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
            return cmtResponseConstruccionDireccionDto;
        }
    }
    
    
    
    public CmtResponseConstruccionDireccionDto validacionCamposConstruirDireccionSolicitudHhpp(CmtRequestConstruccionDireccionDto request){
        try {
            CmtResponseConstruccionDireccionDto cmtResponseConstruccionDireccionDto = new CmtResponseConstruccionDireccionDto();

            if (request != null) {                
                if (request.getDrDireccion() != null) {

                    if (request.getDrDireccion().getIdTipoDireccion() != null
                            && !request.getDrDireccion().getIdTipoDireccion().isEmpty()) {

                        if (!request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")
                                && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")
                                && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")) {
                            
                            ResponseMesaje responseMesaje = new ResponseMesaje();
                            responseMesaje.setTipoRespuesta("E");
                            responseMesaje.setMensaje("Los valores permitidos para tipo de direccion son CK, BM o IT");
                            cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                            return cmtResponseConstruccionDireccionDto;
                        }
                    } else {
                        
                        ResponseMesaje responseMesaje = new ResponseMesaje();
                        responseMesaje.setTipoRespuesta("E");
                        responseMesaje.setMensaje("Debe ingresar el IdTipoDireccion por favor: Los valores permitidos son CK, BM o IT");
                        cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                        return cmtResponseConstruccionDireccionDto;
                    }
                    
                    if(request.getDrDireccion().getDirPrincAlt() != null && !request.getDrDireccion().getDirPrincAlt().isEmpty()){
                        if (!request.getDrDireccion().getDirPrincAlt().equalsIgnoreCase("P")) {

                            ResponseMesaje responseMesaje = new ResponseMesaje();
                            responseMesaje.setTipoRespuesta("E");
                            responseMesaje.setMensaje("El valor para DirPrincAlt debe ser 'P'");
                            cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                            return cmtResponseConstruccionDireccionDto;
                        }
                    } else {                        
                        ResponseMesaje responseMesaje = new ResponseMesaje();
                        responseMesaje.setTipoRespuesta("E");
                        responseMesaje.setMensaje("Debe ingresar el valor P para DirPrincAlt");
                        cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                        return cmtResponseConstruccionDireccionDto;
                    }                    
                    
                    if (request.getDireccionStr() == null || request.getDireccionStr().isEmpty()){
                        ResponseMesaje responseMesaje = new ResponseMesaje();
                        responseMesaje.setTipoRespuesta("E");
                        responseMesaje.setMensaje("Debe ingresar una dirección por favor en el campo DireccionStr.");
                        cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                        return cmtResponseConstruccionDireccionDto;
                    }
                    
                    if (request.getComunidad() == null || request.getComunidad().isEmpty()){
                        ResponseMesaje responseMesaje = new ResponseMesaje();
                        responseMesaje.setTipoRespuesta("E");
                        responseMesaje.setMensaje("Debe ingresar el codigo dane en el campo comunidad");
                        cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                        return cmtResponseConstruccionDireccionDto;                        
                    }                      
                    
                    if(request.getTipoAdicion() != null && !request.getTipoAdicion().isEmpty()){                        
                        if (!request.getTipoAdicion().equalsIgnoreCase("N") && !request.getTipoAdicion().equalsIgnoreCase("C")
                                && !request.getTipoAdicion().equalsIgnoreCase("P")
                                && !request.getTipoAdicion().equalsIgnoreCase("A")) {
                            ResponseMesaje responseMesaje = new ResponseMesaje();
                            responseMesaje.setTipoRespuesta("E");
                            responseMesaje.setMensaje("Los valores permitidos para TipoAdición son: N para calle "
                                    + "carrera dirección hasta placa, C para complementos, P para dirección principal "
                                    + "barrio-manzana e intraducible, A para apartamento");
                            cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                            return cmtResponseConstruccionDireccionDto;
                        }
                    }else{
                        ResponseMesaje responseMesaje = new ResponseMesaje();
                        responseMesaje.setTipoRespuesta("E");
                        responseMesaje.setMensaje("Debe ingresar el tipo de adición: N para calle "
                                + "carrera dirección hasta placa, C para complementos, P para dirección principal "
                                + "barrio-manzana e intraducible, A para apartamento");
                        cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                        return cmtResponseConstruccionDireccionDto;                       
                    }                        
                    
                    //validacion de campos exitosa
                    ResponseMesaje responseMesaje = new ResponseMesaje();
                    responseMesaje.setTipoRespuesta("I");
                    responseMesaje.setMensaje("Validacion de campos obligatorios correcta");
                    cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                    return cmtResponseConstruccionDireccionDto;

                } else {
                    //validacion de campos exitosa
                    ResponseMesaje responseMesaje = new ResponseMesaje();
                    responseMesaje.setTipoRespuesta("E");
                    responseMesaje.setMensaje("Es necesario construir el DrDireccion de la petición para consumir el servicio");
                    cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                    return cmtResponseConstruccionDireccionDto;                    
                }

            } else {                
                ResponseMesaje responseMesaje = new ResponseMesaje();
                responseMesaje.setTipoRespuesta("E");
                responseMesaje.setMensaje("Es necesario construir una peticion para consumir el servicio");
                cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
                return cmtResponseConstruccionDireccionDto;
            }


        } catch (Exception e) {
            CmtResponseConstruccionDireccionDto cmtResponseConstruccionDireccionDto = new CmtResponseConstruccionDireccionDto();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setTipoRespuesta("E");
            responseMesaje.setMensaje("Ocurrir un error validando los campos obligatorios " + e.getMessage());
            cmtResponseConstruccionDireccionDto.setResponseMesaje(responseMesaje);
            return cmtResponseConstruccionDireccionDto;
        }
    }
    
         /**
     * Función para validar si el dato recibido es númerico
     * 
     * @author Juan David Hernandez}
     * return true si el dato es úumerico
     * @param cadena
     * @return 
     */
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    +ClassUtils.getCurrentMethodName(this.getClass())+"': " 
                    + nfe.getMessage();
            LOGGER.error(msg);            
            return false;
        }
    }
    
    /**
     * Metodo para tabular una direccion texto CK y convertirla en Drdireccion
     *
     * @author Victor Bocanegra
     * @param direccionTexto
     * @return DrDireccion
     * @throws ApplicationException
     */
    public DrDireccion tabularDireccionTextoCK(String direccionTexto) throws ApplicationException {

        DrDireccion direccionReturn = null;
        String[] partesDireccion = null;

        String regExpression;
        boolean controlFormatoDirVP = false;
        boolean controlFormatoDirVG = false;
        String tipoViaPr = "";
        String[] numPriLetras;
        String numPrincipal = "";
        String letraPri1 = "";
        String letraPri2 = "";
        String bisPri = "";
        String bisPriVal = "";
        String bisPriFin = "";
        String cuaViaPri = "";

        String[] tipoAndNumGen;
        String tipoViaGen = "";
        String[] numGenLetras;
        String numGeneradora = "";
        String letraGen1 = "";
        String letraGen2 = "";
        String letraGen3 = "";
        String bisgen = "";
        String bisgenVal = "";
        String bisgenFin = "";
        String cuaViaGen = "";
        String placa = "";

        cargarParametros();

        if (direccionTexto != null && !direccionTexto.isEmpty()) {

            for (String d : divLst) {

                int intIndex = direccionTexto.indexOf(d);

                if (intIndex != -1) {
                    partesDireccion = direccionTexto.split(d);
                    break;
                }

            }

            if (partesDireccion != null) {

                //Verificamos la primera parte de la direccion via Principal  
                if (parametroDirCK != null) {
                    regExpression = parametroDirCK.getParValor();
                    Pattern pat = Pattern.compile(regExpression);
                    final Matcher matcher = pat.matcher(partesDireccion[0].trim());

                    while (matcher.find()) {

                        for (int i = 1; i <= matcher.groupCount(); i++) {

                            if (i == 1 && matcher.group(i) != null
                                    && !matcher.group(i).isEmpty()) {
                                tipoViaPr = matcher.group(i);
                            }

                            if (i == 29 && matcher.group(i) != null
                                    && !matcher.group(i).isEmpty()) {

                                numPriLetras = matcher.group(i).split(" ");
                                int j = 0;

                                if (numPriLetras.length > 1 && numPriLetras[1] != null
                                        && !numPriLetras[1].equalsIgnoreCase("BIS")) {
                                    for (String partes : numPriLetras) {

                                        if (j == 0) {
                                            numPrincipal = partes;
                                        }
                                        if (j == 1) {
                                            letraPri1 = partes;
                                        }
                                        if (j == 2) {
                                            letraPri2 = partes;
                                        }

                                        j++;
                                    }
                                } else {

                                    for (String partes : numPriLetras) {

                                        if (j == 0) {
                                            numPrincipal = partes;
                                        }
                                        j++;
                                    }
                                }
                            }

                            if (i == 33 && matcher.group(i) != null
                                    && !matcher.group(i).isEmpty()) {
                                bisPri = matcher.group(i);
                            }
                            if (i == 34 && matcher.group(i) != null
                                    && !matcher.group(i).isEmpty()) {
                                bisPriVal = matcher.group(i);
                            }

                            if (bisPri != null && !bisPriVal.isEmpty()) {
                                bisPriFin = bisPriVal;
                            }

                            if (bisPri != null && bisPriVal.isEmpty()) {
                                bisPriFin = bisPri;
                            }

                            if (i == 41 && matcher.group(i) != null
                                    && !matcher.group(i).isEmpty()) {
                                cuaViaPri = matcher.group(i);
                            }

                            controlFormatoDirVP = true;
                        }
                    }

                    if (!controlFormatoDirVP) {

                        String msg = "La via principal debe ir minimo TIPO VIA PRINCIPAL:'CK|CL|'"
                                + " + NUMERO VIA PRINCIPAL; maximo: TIPO VIA PRINCIPAL:"
                                + " 'CK|CL|'+ NUEMRO VIA PRINCIPAL + LETRA 1 + LETRA 2 + BIS + CARDINALES";
                        throw new ApplicationException(msg);
                    }
                } else {
                    String msg = "No existe una expression regular configurada para validar la Via Principal";
                    throw new ApplicationException(msg);
                }
                //Verificamos la segunda parte de la direccion via Generadora o parte de la placa  

                if (parametroDirPlacaCK != null) {

                    regExpression = parametroDirPlacaCK.getParValor();
                    Pattern pat2 = Pattern.compile(regExpression);
                    final Matcher matcher2 = pat2.matcher(partesDireccion[1].trim());
                    while (matcher2.find()) {
                        
                        for (int i = 1; i <= matcher2.groupCount(); i++) {

                            if (i == 3 && matcher2.group(i) != null
                                    && !matcher2.group(i).isEmpty()) {
                                tipoAndNumGen = matcher2.group(i).split(" ");

                                int j = 0;
                                for (String partes : tipoAndNumGen) {
                                    if (tipoAndNumGen.length == 1) {
                                        numGeneradora = partes;
                                    } else {
                                        if (j == 0) {
                                            tipoViaGen = partes;
                                        }
                                        if (j == 1) {
                                            numGeneradora = partes;
                                        }
                                    }
                                    j++;
                                }
                            }

                            if (i == 9 && matcher2.group(i) != null
                                    && !matcher2.group(i).isEmpty()) {

                                numGenLetras = matcher2.group(i).split(" ");

                                int j = 0;
                                for (String partes : numGenLetras) {

                                    if (j == 0) {
                                        letraGen1 = partes;

                                    }
                                    if (j == 1) {
                                        letraGen2 = partes;

                                    }
                                    if (j == 2) {
                                        letraGen3 = partes;

                                    }
                                    j++;
                                }

                            }

                            if (i == 11 && matcher2.group(i) != null
                                    && !matcher2.group(i).isEmpty()) {

                                bisgen = matcher2.group(i);
                            }

                            if (i == 12 && matcher2.group(i) != null
                                    && !matcher2.group(i).isEmpty()) {

                                bisgenVal = matcher2.group(i);
                            }

                            if (bisgen != null && !bisgenVal.isEmpty()) {
                                bisgenFin = bisgenVal;
                            }

                            if (bisgen != null && bisgenVal.isEmpty()) {
                                bisgenFin = bisgen;
                            }

                            if (i == 13 && matcher2.group(i) != null
                                    && !matcher2.group(i).isEmpty()) {

                                placa = matcher2.group(i);
                            }

                            if (i == 17 && matcher2.group(i) != null
                                    && !matcher2.group(i).isEmpty()) {

                                cuaViaGen = matcher2.group(i);
                            }
                            controlFormatoDirVG = true;
                        }
                    }

                    if (!controlFormatoDirVG) {

                        String msg = "La via generadora debe ir minimo "
                                + "NUMERO VIA GENERADORA + '-' + PLACA; maximo: TIPO VIA GENERADORA:"
                                + " 'TV|TR|DG'+ NUEMRO VIA GENERADORA + LETRA 1 + "
                                + "LETRA 2 + LETRA 3 + BIS + '-' + PLACA+ CARDINALES";

                        throw new ApplicationException(msg);
                    }
                } else {
                    String msg = "No existe una expression regular configurada para validar la Via Generadora";
                    throw new ApplicationException(msg);
                }

                if (controlFormatoDirVP && controlFormatoDirVG) {
                    direccionReturn = new DrDireccion();
                    direccionReturn.setTipoViaPrincipal(tipoViaPr);
                    direccionReturn.setNumViaPrincipal(numPrincipal);
                    direccionReturn.setLtViaPrincipal(letraPri1);
                    direccionReturn.setNlPostViaP(letraPri2);
                    direccionReturn.setBisViaPrincipal(bisPriFin);
                    direccionReturn.setCuadViaPrincipal(cuaViaPri);
                    direccionReturn.setTipoViaGeneradora(tipoViaGen);
                    direccionReturn.setNumViaGeneradora(numGeneradora);
                    direccionReturn.setLtViaGeneradora(letraGen1);
                    direccionReturn.setNlPostViaG(letraGen2);
                    direccionReturn.setLetra3G(letraGen3);
                    direccionReturn.setBisViaGeneradora(bisgenFin);
                    direccionReturn.setCuadViaGeneradora(cuaViaGen);
                    direccionReturn.setPlacaDireccion(placa);

                } else {
                    String msg = "Ocurrio un error al menos formando una de las dos partes de la direccion";
                    throw new ApplicationException(msg);
                }
            } else {
                String msg = "la direccion debe ir separado por alguno de estos separadores: " + divisores + " ";
                throw new ApplicationException(msg);
            }
        } else {
            String msg = "ingrese una direccion valida para tabular";
            throw new ApplicationException(msg);
        }

        return direccionReturn;
    }

    public void cargarParametros() throws ApplicationException {

        try {
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroDiv = parametrosMglManager.findByAcronimoName(Constant.DIVISION_DIR_CK);
            if (parametroDiv != null) {
                divisores = parametroDiv.getParValor();
                String[] div = divisores.split("\\|");
                divLst = Arrays.asList(div);
            }

            parametroDirCK = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_DIR_CK_FICHAS_NODO);
            parametroDirPlacaCK = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_CK_FICHAS_NODO);

        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
