/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.storedprocedures;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.utils.CarguesMasivosMensajeria;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.EntityManagerUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Impl Dao para operaciones en TEC_PREFICHA
 * 
 * @author Juan Carlos Cediel Hitss
 * @version 1.1 Rev Juan Carlos Cediel Hitss
 */
public class CargueFichasDaoImpl {   
    
    private static  final Logger LOGGER = LogManager.getLogger(CargueFichasDaoImpl.class);
    private static final String PO_CODIGO = "PO_CODIGO";    
    private static final String PO_DESCRIPCION  = "PO_DESCRIPCION";
    private static final String PO_V_RESPONSE  = "PO_V_RESPONSE";
    private static final String PROCESO_EXITOSO = "PROCESO_EXITOSO";
    private static final String PI_FECHA_INI = "PI_FECHA_INI";
    private static final String PI_FECHA_FIN = "PI_FECHA_FIN";
    private static final String PI_PREFICHA_ID = "PI_PREFICHA_ID";
    
    
    public List<PreFichaMgl> tablaPrefichas () throws ApplicationException {
        
        EntityManager manager = null;
        
        try{
            manager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            manager.getTransaction().begin(); //Iniciar transacción.
            StoredProcedureQuery procedureQuery = manager.createStoredProcedureQuery(Constant.MGL_DATABASE_SCHEMA
                    +"."+ StoredProcedureNamesConstants.TEC_CON_PREFICHA_PRC);
            
            procedureQuery.registerStoredProcedureParameter(PI_FECHA_INI, Date.class, ParameterMode.IN);             
            procedureQuery.registerStoredProcedureParameter(PI_FECHA_FIN, Date.class, ParameterMode.IN); 
            procedureQuery.registerStoredProcedureParameter(PI_PREFICHA_ID, Integer.class, ParameterMode.IN); 
            
            procedureQuery.registerStoredProcedureParameter(PO_CODIGO , Integer.class, ParameterMode.OUT);
            procedureQuery.registerStoredProcedureParameter(PO_DESCRIPCION , String.class, ParameterMode.OUT);
            procedureQuery.registerStoredProcedureParameter(PO_V_RESPONSE , Class.class, ParameterMode.REF_CURSOR);
            procedureQuery.execute();
            
            String resultado = (String) procedureQuery.getOutputParameterValue(PO_DESCRIPCION);
            List<Object[]> Values = procedureQuery.getResultList();
            List<PreFichaMgl> preFichaMgl = new ArrayList();
            if (resultado.equals(PROCESO_EXITOSO)){
                if (Values != null){
                    int i;
                    for (Object[] datoActual: Values){
                        i = 0;
                        PreFichaMgl preFicha = new PreFichaMgl();
                        preFicha.setNombreArchivo((String) datoActual[9]); 
                        preFichaMgl.add(preFicha);
                    }                    
                }else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_MGL_PROCEDIMIENTO_NO_ARROJO_DATOS, ""));
                }
            }
            return(preFichaMgl);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            if (Objects.nonNull(manager)) {
                manager.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage());
        }finally {
            if (manager != null && manager.isOpen()) {
                manager.clear();
                manager.close();
            }
        }        
    }
}
