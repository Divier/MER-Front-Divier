package co.com.claro.mgl.facade.cm;

import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.dtos.ResponseCreateHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
public interface CmtVisitaTecnicaMglFacadeLocal {

    public CmtVisitaTecnicaMgl createCm(CmtVisitaTecnicaMgl t) throws ApplicationException;

    public CmtVisitaTecnicaMgl update(CmtVisitaTecnicaMgl t) throws ApplicationException;

    public boolean delete(CmtVisitaTecnicaMgl t) throws ApplicationException;

    public CmtVisitaTecnicaMgl findById(CmtVisitaTecnicaMgl sqlData) throws ApplicationException;
    
    public List<CmtVisitaTecnicaMgl> findByIdOt(CmtOrdenTrabajoMgl idOt) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    public ResponseCreateHhppDto consolidacionSubEdiVt(List<CmtSubEdificiosVt> subEdificiosVtList) throws ApplicationException;

    public CmtVisitaTecnicaMgl findVtbyIdOt(BigDecimal idOt) throws ApplicationException;
    
     /**
     * Cambiar el estado a inactivo de las visitas tecnicas diferentes a la visita tecnica recien creada
     * 
     * @author rodriguezluim
     * @version 1.0
     * @param visitaTecnica objeto recien creado para la obtencion de la ot y el id que no se va a cambiar
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cambiarEstadoVisitasTecnicasAnteriores(CmtVisitaTecnicaMgl visitaTecnica) throws ApplicationException;
    
    /**
     * Buscar Visitas tecnicas activas de una derminada OT
     * 
     * @param ordenTrabajo orden de trabajo como condicional de la busqueda
     * @return Retorna el objeto activo de una determinada ot Visitas tegnicas
     * @throws ApplicationException 
     */
    public CmtVisitaTecnicaMgl findVTActiveByIdOt(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException;
    
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
      public List<Boolean> actualizarCostosAcometidaCcmm(List<CmtSubEdificiosVt> subEdificiosVtList,
            CmtVisitaTecnicaMgl vtAcometida, String usuario, int perfil)
            throws ApplicationException;
  
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
    public boolean cargarArchivoVTxUCM(CmtVisitaTecnicaMgl visitaTecnicaMgl,
            UploadedFile archivo, String usuario, int perfil, String origen)
            throws MalformedURLException, FileNotFoundException, ApplicationException, 
            UploadCuentasMatricesFault, SearchCuentasMatricesFault;
}
