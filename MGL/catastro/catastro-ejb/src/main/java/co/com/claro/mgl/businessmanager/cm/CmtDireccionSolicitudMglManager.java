/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.dao.impl.cm.CmtDireccionSolicitudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 * Manager mapeo tabla CMT_DIRECCION_SOLICITUD
 *
 * @author yimy diaz
 * @versión 1.00.0000
 */
public class CmtDireccionSolicitudMglManager {
    
    
    private static final Logger LOGGER = LogManager.getLogger(CmtDireccionSolicitudMglManager.class);

    public List<CmtDireccionSolicitudMgl> findAll() throws ApplicationException {
        List<CmtDireccionSolicitudMgl> resulList;
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        resulList = cmtDireccionSolicitudMglDaoImpl.findAll();
        return resulList;
    }

    public CmtDireccionSolicitudMgl create(
            CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl, String usuario, int perfil)
            throws ApplicationException {
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        return cmtDireccionSolicitudMglDaoImpl.createCm(cmtDireccionSolicitudMgl, usuario, perfil);
    }

    public CmtDireccionSolicitudMgl update(
            CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl)
            throws ApplicationException {
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        return cmtDireccionSolicitudMglDaoImpl.update(cmtDireccionSolicitudMgl);
    }

    public boolean delete(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl)
            throws ApplicationException {
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        return cmtDireccionSolicitudMglDaoImpl.delete(cmtDireccionSolicitudMgl);
    }

    public CmtDireccionSolicitudMgl findById(
            CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl)
            throws ApplicationException {
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        return cmtDireccionSolicitudMglDaoImpl.find(
                cmtDireccionSolicitudMgl.getDireccionSolId());
    }

    public CmtDireccionSolicitudMgl findById(BigDecimal id)
            throws ApplicationException {
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        return cmtDireccionSolicitudMglDaoImpl.find(id);
    }

    public List<CmtDireccionSolicitudMgl> findSolicitudId(BigDecimal idSolicitud) {
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        return cmtDireccionSolicitudMglDaoImpl.findSolicitudId(idSolicitud);
    }

   public List<CmtSolicitudCmMgl> findByDrDireccion(DrDireccion drDireccion, String centroPoblado)
            throws ApplicationException {
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        return cmtDireccionSolicitudMglDaoImpl.findByDrDireccion(
                drDireccion, centroPoblado);
    }

    /**
     * @param idSolicitud
     * @return drDirecion
     * @throws ApplicationException
     */
    public DrDireccion findEstateSolicitud(BigDecimal idSolicitud)
            throws ApplicationException {
        CmtDireccionSolicitudMglDaoImpl cmtDireccionSolicitudMglDaoImpl =
                new CmtDireccionSolicitudMglDaoImpl();
        DrDireccion drDirecion = new DrDireccion();

        return drDirecion;
    }
    public void siExistenSolictudesEnCurso(DrDireccion drDireccion, String centroPoblado) throws ApplicationExceptionCM, CloneNotSupportedException {
        CmtCuentaMatrizMgl matrizMgl = null;
        
        try {
            
            /* @author Juan David Hernandez */
            //Si la comunidad no es un numero (codigoDane) es porque viene de visor en formato RR
            boolean comunidadVisor = isNumeric(centroPoblado);
            /*Si la comunidad llega desde visor en formato RR se hace la busqueda de la comunidad para 
             obtener el centro poblado y extraer el codigo dane para seguir con el proceso normal de MGL*/
            CmtComunidadRr cmtComunidadRr = new CmtComunidadRr();
            if (!comunidadVisor) {
                CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
                cmtComunidadRr
                        = cmtComunidadRrManager.findComunidadByComunidad(centroPoblado);
                //Se reemplaza la comunidad RR por el codigo dane del centro poblado
                if (cmtComunidadRr != null && cmtComunidadRr.getCiudad() != null) {
                   centroPoblado = cmtComunidadRr.getCiudad().getGpoId().toString();
                } else {
                    throw new ApplicationException(" Comunidad RR no tiene configurado"
                            + " la ciudad en MGL para obtener el codigo dane");
                }
            }

            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centro = geograficoPoliticoManager.findById(new BigDecimal(centroPoblado));
            String ciudad = centro != null ? centro.getGpoNombre() : "";
            
            String messageError = "";
            DrDireccionManager direccionManager = new DrDireccionManager();
            CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
            List<CmtCuentaMatrizMgl> listaCmtCuentaMatrizMgl = cmtCuentMatrizMglManager
                        .findCuentasMatricesByDrDireccion(centro, drDireccion);
            List<CmtSolicitudCmMgl> listaCmtSolicitudCmMgl = findByDrDireccion(drDireccion, centroPoblado);
           
            List<Solicitud> listaSolicitudvt = direccionManager.findByDrDireccion(drDireccion, ciudad);

            if (listaCmtCuentaMatrizMgl != null && !listaCmtCuentaMatrizMgl.isEmpty()) {
                for (CmtCuentaMatrizMgl cm : listaCmtCuentaMatrizMgl) {
                    if(cm.getNumeroCuenta() != null && !cm.getNumeroCuenta().equals(BigDecimal.ZERO)){
                    messageError += "La direccion esta relacionada en la Cuenta matriz con id de RR: " + cm.getNumeroCuenta().toString() + "\n";
                    }else{
                         if(cm.getCuentaMatrizId() != null && !cm.getCuentaMatrizId().equals(BigDecimal.ZERO)){
                            messageError += "La direccion esta relacionada en la Cuenta matriz con id de MER: " + cm.getCuentaMatrizId().toString() + "\n";  
                         }
                    }
                    matrizMgl=cm;
                }
            }
            if (listaCmtSolicitudCmMgl != null && !listaCmtSolicitudCmMgl.isEmpty()) {
                for (CmtSolicitudCmMgl csm : listaCmtSolicitudCmMgl) {
                    messageError += "No se puede generar una solicitud de creación con esta dirección, Existe solicitud número: " + csm.getSolicitudCmId().toString() + " pendiente por gestionar en el Modulo de Cuentas Matrices. \n";
                }
            }
            if (listaSolicitudvt != null && !listaSolicitudvt.isEmpty()) {
                for (Solicitud svt : listaSolicitudvt) {
                    messageError += "No se puede generar una solicitud de creación con esta dirección, Existe solicitud número : " + svt.getIdSolicitud().toString() + " pendiente por gestionar en el Modulo de Visitas Técnicas. \n";
                }
            }

            if (!messageError.isEmpty()) {
                throw new ApplicationExceptionCM(messageError + "No se puede continuar", matrizMgl);
            }

        } catch (ApplicationException ex) {
                String msg = "Error en siExistenSolictudesEnCurso. EX000 '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
		throw new ApplicationExceptionCM("Error en siExistenSolictudesEnCurso. EX000: " + ex.getMessage(), ex, matrizMgl);
        } 

    }
    
    /**
     * Autor: Victor Bocanegra
     * metodo que devuelve una Direccion solicitud MGL 
     * @param detalleDireccionEntity
     * @return CmtDireccionSolicitudMgl
     */
    public CmtDireccionSolicitudMgl parseDetalleDireccionEntityToCmtDireccionSolicitudMgl(DetalleDireccionEntity detalleDireccionEntity) {

        CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl = new CmtDireccionSolicitudMgl();
        try {
            if (detalleDireccionEntity.getIdtipodireccion() != null && !detalleDireccionEntity.getIdtipodireccion().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCodTipoDir(detalleDireccionEntity.getIdtipodireccion());
            }

            if (detalleDireccionEntity.getTipoviaprincipal() != null && !detalleDireccionEntity.getTipoviaprincipal().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setTipoViaPrincipal(detalleDireccionEntity.getTipoviaprincipal());
            }

            if (detalleDireccionEntity.getNumviaprincipal() != null && !detalleDireccionEntity.getNumviaprincipal().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setNumViaPrincipal(detalleDireccionEntity.getNumviaprincipal());
            }

            if (detalleDireccionEntity.getLtviaprincipal() != null && !detalleDireccionEntity.getLtviaprincipal().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setLtViaPrincipal(detalleDireccionEntity.getLtviaprincipal());
            }

            if (detalleDireccionEntity.getNlpostviap() != null && !detalleDireccionEntity.getNlpostviap().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setNlPostViaP(detalleDireccionEntity.getNlpostviap());
            }

            if (detalleDireccionEntity.getBisviaprincipal() != null && !detalleDireccionEntity.getBisviaprincipal().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setBisViaPrincipal(detalleDireccionEntity.getBisviaprincipal());
            }

            if (detalleDireccionEntity.getCuadviaprincipal() != null && !detalleDireccionEntity.getCuadviaprincipal().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCuadViaPrincipal(detalleDireccionEntity.getCuadviaprincipal());
            }

            if (detalleDireccionEntity.getLetra3g() != null && !detalleDireccionEntity.getLetra3g().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setLetra3G(detalleDireccionEntity.getLetra3g());
            }
            
            if (detalleDireccionEntity.getTipoviageneradora() != null && !detalleDireccionEntity.getTipoviageneradora().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setTipoViaGeneradora(detalleDireccionEntity.getTipoviageneradora());
            }

            if (detalleDireccionEntity.getNumviageneradora() != null && !detalleDireccionEntity.getNumviageneradora().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setNumViaGeneradora(detalleDireccionEntity.getNumviageneradora());
            }

            if (detalleDireccionEntity.getLtviageneradora() != null && !detalleDireccionEntity.getLtviageneradora().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setLtViaGeneradora(detalleDireccionEntity.getLtviageneradora());
            }

            if (detalleDireccionEntity.getNlpostviag() != null && !detalleDireccionEntity.getNlpostviag().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setNlPostViaG(detalleDireccionEntity.getNlpostviag());
            }

            if (detalleDireccionEntity.getBisviageneradora() != null && !detalleDireccionEntity.getBisviageneradora().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setBisViaGeneradora(detalleDireccionEntity.getBisviageneradora());
            }

            if (detalleDireccionEntity.getCuadviageneradora() != null && !detalleDireccionEntity.getCuadviageneradora().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCuadViaGeneradora(detalleDireccionEntity.getCuadviageneradora());
            }

            if (detalleDireccionEntity.getPlacadireccion() != null && !detalleDireccionEntity.getPlacadireccion().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setPlacaDireccion(detalleDireccionEntity.getPlacadireccion());
            }

            if (detalleDireccionEntity.getMztiponivel1() != null && !detalleDireccionEntity.getMztiponivel1().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzTipoNivel1(detalleDireccionEntity.getMztiponivel1());
            }

            if (detalleDireccionEntity.getMztiponivel2() != null && !detalleDireccionEntity.getMztiponivel2().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzTipoNivel2(detalleDireccionEntity.getMztiponivel2());
            }
            if (detalleDireccionEntity.getMztiponivel3() != null && !detalleDireccionEntity.getMztiponivel3().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzTipoNivel3(detalleDireccionEntity.getMztiponivel3());
            }
            if (detalleDireccionEntity.getMztiponivel4() != null && !detalleDireccionEntity.getMztiponivel4().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzTipoNivel4(detalleDireccionEntity.getMztiponivel4());
            }
            if (detalleDireccionEntity.getMztiponivel5() != null && !detalleDireccionEntity.getMztiponivel5().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzTipoNivel5(detalleDireccionEntity.getMztiponivel5());
            }
            if (detalleDireccionEntity.getMztiponivel6() != null && !detalleDireccionEntity.getMztiponivel6().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzTipoNivel6(detalleDireccionEntity.getMztiponivel6());
            }
            if (detalleDireccionEntity.getMzvalornivel1() != null && !detalleDireccionEntity.getMzvalornivel1().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzValorNivel2(detalleDireccionEntity.getMzvalornivel1());
            }
            if (detalleDireccionEntity.getMzvalornivel2() != null && !detalleDireccionEntity.getMzvalornivel2().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzValorNivel2(detalleDireccionEntity.getMzvalornivel2());
            }
            if (detalleDireccionEntity.getMzvalornivel3() != null && !detalleDireccionEntity.getMzvalornivel3().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzValorNivel3(detalleDireccionEntity.getMzvalornivel3());
            }
            if (detalleDireccionEntity.getMzvalornivel4() != null && !detalleDireccionEntity.getMzvalornivel4().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzValorNivel4(detalleDireccionEntity.getMzvalornivel4());
            }
            if (detalleDireccionEntity.getMzvalornivel5() != null && !detalleDireccionEntity.getMzvalornivel5().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzValorNivel5(detalleDireccionEntity.getMzvalornivel5());
            }
            if (detalleDireccionEntity.getMzvalornivel6() != null && !detalleDireccionEntity.getMzvalornivel6().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setMzValorNivel6(detalleDireccionEntity.getMzvalornivel6());
            }

            if (detalleDireccionEntity.getCptiponivel1() != null && !detalleDireccionEntity.getCptiponivel1().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpTipoNivel1(detalleDireccionEntity.getCptiponivel1());
            }
            if (detalleDireccionEntity.getCptiponivel2() != null && !detalleDireccionEntity.getCptiponivel2().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpTipoNivel2(detalleDireccionEntity.getCptiponivel2());
            }
            if (detalleDireccionEntity.getCptiponivel3() != null && !detalleDireccionEntity.getCptiponivel3().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpTipoNivel3(detalleDireccionEntity.getCptiponivel3());
            }
            if (detalleDireccionEntity.getCptiponivel4() != null && !detalleDireccionEntity.getCptiponivel4().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpTipoNivel4(detalleDireccionEntity.getCptiponivel4());
            }
            if (detalleDireccionEntity.getCptiponivel5() != null && !detalleDireccionEntity.getCptiponivel5().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpTipoNivel5(detalleDireccionEntity.getCptiponivel5());
            }
            if (detalleDireccionEntity.getCptiponivel6() != null && !detalleDireccionEntity.getCptiponivel6().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpTipoNivel6(detalleDireccionEntity.getCptiponivel6());
            }

            if (detalleDireccionEntity.getCpvalornivel1() != null && !detalleDireccionEntity.getCpvalornivel1().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpValorNivel1(detalleDireccionEntity.getCpvalornivel1());
            }
            if (detalleDireccionEntity.getCpvalornivel2() != null && !detalleDireccionEntity.getCpvalornivel2().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpValorNivel2(detalleDireccionEntity.getCpvalornivel2());
            }
            if (detalleDireccionEntity.getCpvalornivel3() != null && !detalleDireccionEntity.getCpvalornivel3().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpValorNivel3(detalleDireccionEntity.getCpvalornivel3());
            }
            if (detalleDireccionEntity.getCpvalornivel4() != null && !detalleDireccionEntity.getCpvalornivel4().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpValorNivel4(detalleDireccionEntity.getCpvalornivel4());
            }
            if (detalleDireccionEntity.getCpvalornivel5() != null && !detalleDireccionEntity.getCpvalornivel5().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpValorNivel5(detalleDireccionEntity.getCpvalornivel5());
            }
            if (detalleDireccionEntity.getCpvalornivel6() != null && !detalleDireccionEntity.getCpvalornivel6().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setCpValorNivel6(detalleDireccionEntity.getCpvalornivel6());
            }
            if (detalleDireccionEntity.getItTipoPlaca() != null && !detalleDireccionEntity.getItTipoPlaca().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setItTipoPlaca(detalleDireccionEntity.getItTipoPlaca());
            }
            if (detalleDireccionEntity.getItValorPlaca() != null && !detalleDireccionEntity.getItValorPlaca().trim().isEmpty()) {
                cmtDireccionSolicitudMgl.setItValorPlaca(detalleDireccionEntity.getItValorPlaca());
            }
             
        } catch (Exception ex) {
            String msg = "Error en el parseo de detalleDireccionEntity a  cmtDireccionSolicitudMgl '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        
        return cmtDireccionSolicitudMgl;
    }
    
    
        /**
     * Función para validar si el dato recibido es númerico
     *
     * @author Juan David Hernandez return true si el dato es úumerico
     * @param cadena
     * @return 
     */
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + nfe.getMessage();
            LOGGER.error(msg);
            return false;
        }
    }
    
}
