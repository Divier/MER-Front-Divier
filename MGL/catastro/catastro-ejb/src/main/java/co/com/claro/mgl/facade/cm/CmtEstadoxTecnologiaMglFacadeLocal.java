/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.EstadosTecnologiasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxTecnologiaMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public interface CmtEstadoxTecnologiaMglFacadeLocal extends BaseFacadeLocal<CmtEstadoxTecnologiaMgl> {

    /**
     * Cargar la configuracions de validaciones. Carga la configuracions de
     * validaciones activas por proyecto.
     *
     * @author Lenis Cardenas
     * @version 1.0 revision 03/08/2017.
     * @return Lista de estados x tecnologias
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en
     * a ejecución de la sentencia.
     */
    @Override
    List<CmtEstadoxTecnologiaMgl> findAll()
            throws ApplicationException;



     List<EstadosTecnologiasDto> getListaConf(String estadoCcmm)
            throws ApplicationException;
     
      public void setUser(String user, int perfil) throws ApplicationException;
      
      public boolean  createConf( List<EstadosTecnologiasDto>  lista, String user,int perfil)
            throws ApplicationException;
      
       public List<CmtEstadoxTecnologiaMgl> findByEstado(CmtBasicaMgl basicaEstado) throws ApplicationException ;

}
