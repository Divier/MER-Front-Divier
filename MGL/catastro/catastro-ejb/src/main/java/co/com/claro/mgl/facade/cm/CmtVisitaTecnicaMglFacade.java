package co.com.claro.mgl.facade.cm;

import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.businessmanager.cm.CmtVisitaTecnicaMglManager;
import co.com.claro.mgl.dtos.ResponseCreateHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import com.jlcg.db.exept.ExceptionDB;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
@Stateless
public class CmtVisitaTecnicaMglFacade implements CmtVisitaTecnicaMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
    private static final Logger LOGGER = LogManager.getLogger(CmtVisitaTecnicaMglFacade.class);

    @Override
    public CmtVisitaTecnicaMgl createCm(CmtVisitaTecnicaMgl t) throws ApplicationException {
        CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
        return manager.createCm(t, user, perfil);
    }

    @Override
    public CmtVisitaTecnicaMgl update(CmtVisitaTecnicaMgl t)
            throws ApplicationException {
        CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
        return manager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtVisitaTecnicaMgl t) throws ApplicationException {
        CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
        return manager.deleteCm(t, user, perfil);
    }

    @Override
    public CmtVisitaTecnicaMgl findById(CmtVisitaTecnicaMgl sqlData) throws ApplicationException {
        CmtVisitaTecnicaMglManager cmtCuentMatrizMglManager = new CmtVisitaTecnicaMglManager();
        return cmtCuentMatrizMglManager.findById(sqlData);
    }

    @Override
    public CmtVisitaTecnicaMgl findVtbyIdOt(BigDecimal idOt) throws ApplicationException {
        try {
            CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
            return manager.findVtbyIdOt(idOt);
        } catch (ApplicationException | NoResultException e) {
            if (e instanceof NoResultException) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                return null;
            } else {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(e.getMessage(), e);
            }
            
        }
    }

    @Override
    public List<CmtVisitaTecnicaMgl> findByIdOt(CmtOrdenTrabajoMgl idOt) throws ApplicationException {
        CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
        return manager.findByIdOt(idOt);
    }

    @Override
    public ResponseCreateHhppDto consolidacionSubEdiVt(List<CmtSubEdificiosVt> subEdificiosVtList) throws ApplicationException {
        CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
        ResponseCreateHhppDto responseCreateHhppDto = null;
        try {
            responseCreateHhppDto = manager.consolidacionSubEdiVt(subEdificiosVtList, user, perfil);
        } catch (ApplicationException | IOException | ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
        return responseCreateHhppDto;
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    /**
     * Cambiar el estado a inactivo de las visitas tecnicas diferentes a la
     * visita tecnica recien creada
     *
     * @author rodriguezluim
     * @version 1.0
     * @param visitaTecnica objeto recien creado para la obtencion de la ot y el
     * id que no se va a cambiar
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public void cambiarEstadoVisitasTecnicasAnteriores(CmtVisitaTecnicaMgl visitaTecnica) throws ApplicationException {
        List<CmtVisitaTecnicaMgl> listaVisitasOT = findByIdOt(visitaTecnica.getOtObj());
        for (CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl : listaVisitasOT) {
            if (!cmtVisitaTecnicaMgl.getIdVt().equals(visitaTecnica.getIdVt())) {
                if (cmtVisitaTecnicaMgl.getEstadoVisitaTecnica().equals(BigDecimal.ONE)) {
                    cmtVisitaTecnicaMgl.setEstadoVisitaTecnica(BigDecimal.ZERO);
                    update(cmtVisitaTecnicaMgl);
                }
            }
        }
    }

    /**
     * Buscar Visitas tecnicas activas de una derminada OT
     *
     * @param ordenTrabajo orden de trabajo como condicional de la busqueda
     * @return Retorna el objeto activo de una determinada ot Visitas tegnicas
     * @throws ApplicationException
     */
    @Override
    public CmtVisitaTecnicaMgl findVTActiveByIdOt(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException {
        CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
        return manager.findVTActiveByIdOt(ordenTrabajo);
    }

    /**
     * Actualiza los costos de acometidas totales de los subedificios
     *
     * @param subEdificiosVtList lista de subedificios
     * @param vtAcometida acometida con los costos reales
     * @param usuario en sesion que realiza la operacion
     * @param perfil del usuario
     * @return List<Boolean>
     * @throws ApplicationException
     */
    @Override
    public List<Boolean> actualizarCostosAcometidaCcmm(List<CmtSubEdificiosVt> subEdificiosVtList,
            CmtVisitaTecnicaMgl vtAcometida, String usuario, int perfil)
            throws ApplicationException {

        CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
        return manager.actualizarCostosAcometidaCcmm(subEdificiosVtList, vtAcometida, usuario, perfil);
    }

    /**
     * Metodo para cargar un documento de VT al UCM Autor: Victor Bocanegra
     *
     * @param visitaTecnicaMgl a la cual se le van adjuntar la imagen
     * @param archivo archivo que se va a cargar al UCM
     * @param usuario que realiza la operacion
     * @param perfil del usuario que realiza la operacion
     * @return boolean
     * @throws java.net.MalformedURLException
     * @throws ApplicationException Excepcion lanzada por la carga del archivo
     * @throws java.io.FileNotFoundException
     */
    @Override
    public boolean cargarArchivoVTxUCM(CmtVisitaTecnicaMgl visitaTecnicaMgl,
            UploadedFile archivo, String usuario, int perfil, String origen)
            throws MalformedURLException, FileNotFoundException, ApplicationException, 
            UploadCuentasMatricesFault, SearchCuentasMatricesFault {

        CmtVisitaTecnicaMglManager manager = new CmtVisitaTecnicaMglManager();
        return manager.cargarArchivoVTxUCM(visitaTecnicaMgl, archivo, usuario, perfil,origen);

    }
}
