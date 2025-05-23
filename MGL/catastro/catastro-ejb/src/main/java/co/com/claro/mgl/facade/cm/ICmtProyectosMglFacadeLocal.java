/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.CmtFiltroProyectosDto;
import co.com.claro.mgl.dtos.CmtTecSiteSapRespDto;
import co.com.claro.mgl.error.ApplicationException;

/**
 * Facade para realizar CRUD a tabla CMT_COM_TECHNICALSITESAP.
 *
 * @author Johan Gomez
 * @version 1.0, 2024/12/10
 */
public interface ICmtProyectosMglFacadeLocal {
    
    CmtTecSiteSapRespDto crudProyectosCm(CmtFiltroProyectosDto filtro) throws ApplicationException;
}
