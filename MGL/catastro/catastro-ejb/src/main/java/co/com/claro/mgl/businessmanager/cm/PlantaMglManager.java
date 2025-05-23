package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dao.impl.PlantaMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaPlantaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.PlantaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class PlantaMglManager {


  
    private static final Logger LOGGER = LogManager.getLogger(PlantaMglManager.class);
    private PlantaMglDaoImpl plantaMglDaoImpl = new PlantaMglDaoImpl();
    
    private ParametrosMglManager parametrosMglManager =new ParametrosMglManager();
    private PlantaMglSincronizacionRR plantaMglSincronizacionRR = 
            new PlantaMglSincronizacionRR();
   
    private String sincronizacionFlag;

    public PlantaMglManager() {
        try {
            sincronizacionFlag = parametrosMglManager.findByAcronimoName
        (Constant.HABILITAR_RR).getParValor();
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }
    
    
    
    /**
     * *Jonathan Peña
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<PlantaMgl>
     * @throws ApplicationException
     */
    public PaginacionDto<PlantaMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaPlantaDto consulta) throws ApplicationException {

        PaginacionDto<PlantaMgl> resultado = new PaginacionDto<PlantaMgl>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        if (consulta != null) {
            resultado.setNumPaginas(plantaMglDaoImpl.countByPlantFiltro(consulta));
            resultado.setListResultado(plantaMglDaoImpl.findByFiltro(firstResult, maxResults, consulta));
        } else {
            consulta = new CmtFiltroConsultaPlantaDto();
            resultado.setNumPaginas(plantaMglDaoImpl.countByPlantFiltro(consulta));
            resultado.setListResultado(plantaMglDaoImpl.findByFiltro(firstResult, maxResults, consulta));
        }
        return resultado;
    }
   
     /**
     * *Jonathan Peña
     *
     * @param localType
     * @param localCode
     * @param parentLocalType
     * @param parentLocalCode
     * @return PlantaMgl
     * @throws ApplicationException
     */
    public PlantaMgl findByTypeAndCode(String localType, String localCode,
        String parentLocalType, String parentLocalCode) throws ApplicationException {
        return plantaMglDaoImpl.findByTypeAndCode(localType, localCode, parentLocalType, parentLocalCode);
    }
    
    public PlantaMgl create(PlantaMgl plantaMgl) throws ApplicationException {
        PlantaMgl result = null;
        String PFHTYP="HE";
        String PFHEND = "";
        if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("HE")==0){
            PFHEND = plantaMgl.getLocationcode();
        }else if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("CT")==0){
            PFHEND = plantaMgl.getConfiguracionplantaparentid().getLocationcode();
        }else if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("ND")==0){
            PFHEND = plantaMgl.getConfiguracionplantaparentid().getConfiguracionplantaparentid().getLocationcode();
        }else{
            throw new ApplicationException("Error tipo de Planta create no concuerda con los tipos "+plantaMgl.getLocationtype());
        }
        if ( Integer.valueOf(sincronizacionFlag).compareTo(1)==0 ) {
            plantaMglSincronizacionRR.createPlantaRR(plantaMgl,PFHTYP,PFHEND);
        }
        result = plantaMglDaoImpl.create(plantaMgl);
        return result;
    }
    
    public PlantaMgl update(PlantaMgl plantaMgl) throws ApplicationException {
        String PFHTYP="HE";
        String PFHEND = "";
        if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("HE")==0){
            PFHEND = plantaMgl.getLocationcode();
        }else if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("CT")==0){
            PFHEND = plantaMgl.getConfiguracionplantaparentid().getLocationcode();
        }else if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("ND")==0){
            PFHEND = plantaMgl.getConfiguracionplantaparentid().getConfiguracionplantaparentid().getLocationcode();
        }else{
            throw new ApplicationException("Error tipo de Planta update no concuerda con los tipos " 
                    + plantaMgl.getLocationtype());
        }
        if ( Integer.valueOf(sincronizacionFlag).compareTo(1) == 0 ){
            if( !plantaMglSincronizacionRR.updateNodoRR(plantaMgl,PFHTYP,PFHEND)){
                plantaMglDaoImpl.update(plantaMgl);
            }else{
                throw new ApplicationException("Error no se pudo realizar actualizacion servicio RR " 
                    + plantaMgl.getLocationtype());
            }
        }else{
            plantaMglDaoImpl.update(plantaMgl);
        }
        return plantaMgl;//devuelve el dato de entrada pero sin realizar persistencia de datos
    }
    
    public boolean delete(PlantaMgl plantaMgl) throws ApplicationException {
        String PFHTYP = "HE";
        String PFHEND = "";
        if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("HE") == 0) {
            PFHEND = plantaMgl.getLocationcode();
        } else if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("CT") == 0) {
            PFHEND = plantaMgl.getConfiguracionplantaparentid().getLocationcode();
        } else if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("ND") == 0) {
            PFHEND = plantaMgl.getConfiguracionplantaparentid().getConfiguracionplantaparentid().getLocationcode();
        } else {
            LOGGER.error(this.getClass()+" : delete : Error : tipo de Planta en RR no se puede realizar eliminacion: "+plantaMgl.getLocationtype()+plantaMgl.getLocationcode());
            return false;
        }
        if ( Integer.valueOf(sincronizacionFlag).compareTo(1)==0 ) {
            if (plantaMglSincronizacionRR.deletePlantaRR(plantaMgl, PFHTYP, PFHEND)){
                throw new ApplicationException("Registro no pudo eliminarse en RR verificar que el servicio este activo.");
            }
        }
        plantaMgl.setEstadoRegistro(0);
        plantaMglDaoImpl.update(plantaMgl);
        return true;
    }
    
}
