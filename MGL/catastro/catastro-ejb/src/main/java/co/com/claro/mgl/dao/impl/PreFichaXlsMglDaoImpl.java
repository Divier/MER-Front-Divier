/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.CmtFiltroPrefichasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class PreFichaXlsMglDaoImpl extends GenericDaoImpl<PreFichaXlsMgl>{
    
    public List<PreFichaXlsMgl> savePreFichaXlsList (List<PreFichaXlsMgl> prefichaXlsList)throws ApplicationException {
        for(PreFichaXlsMgl pf : prefichaXlsList){
            create(pf);
        }
        return prefichaXlsList;
    }
    
    public List<PreFichaXlsMgl> getListXLSByPrefichaFase(BigDecimal idPreficha, String fase){
        List<PreFichaXlsMgl> resultList;
        Query query = entityManager.createNamedQuery("PreFichaXlsMgl.findByFaseAndIdPf");
        query.setParameter("prfId", idPreficha);
        query.setParameter("fase", fase);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaXlsMgl>)query.getResultList();
        return resultList;
    }
    
    
    public List<PreFichaXlsMgl> getListadoPrefichasPorTab(BigDecimal idPreficha, String fase, String tab , CmtFiltroPrefichasDto filtros){
        List<PreFichaXlsMgl> resultList;
        StringBuilder consulta = new StringBuilder("SELECT DISTINCT p FROM PreFichaXlsMgl p "
                + "INNER JOIN p.fichaGeorreferenciaMgl g "
                + "WHERE p.prfId = :prfId and p.fase = :fase  "
                + "AND p.pestana = :tab ");
        
        boolean control= false;
        
        if(filtros.getAptos() !=  null){
            consulta.append(" AND p.aptos = :numeroAptos"); 
        }
        if(filtros.getLocales() != null){
            consulta.append(" AND p.locales = :numeroLocales"); 
        }
        if (filtros.getOficinas() != null) {
            if (filtros.getOficinas().toString().toUpperCase().equalsIgnoreCase("SV")) {
                consulta.append(" AND p.getOficinas  is NULL");
            } else {
                consulta.append(" AND p.oficinas = :numeroOficinas");
            }
        }
        if (filtros.getDistribucion() != null && !filtros.getDistribucion().isEmpty()) {
            if (filtros.getDistribucion().toUpperCase().equalsIgnoreCase("SV")) {
                consulta.append(" AND p.distribucion  is NULL");
            } else {
                consulta.append(" AND p.distribucion LIKE :distribucion");
            }
        } 
        if(filtros.getEstrato()!= null && !filtros.getEstrato().isEmpty()){
        
            if (filtros.getEstrato().toUpperCase().equalsIgnoreCase("SV")) {
                consulta.append(" AND g.levelEconomic is NULL ");
                control=true;
            } else {
                consulta.append(" AND g.levelEconomic LIKE :estrato ");
            }
        }
        
        consulta.append(" AND g.estadoRegistro = 1");

        Query query = entityManager.createQuery(consulta.toString());
        query.setParameter("prfId", idPreficha);
        query.setParameter("fase", fase);
        query.setParameter("tab", tab);
        
        if(filtros.getAptos() !=  null){
            query.setParameter("numeroAptos", filtros.getAptos());
        }
        if(filtros.getLocales() != null){
            query.setParameter("numeroLocales", filtros.getLocales());
        }
        if (filtros.getOficinas() != null) {
            if (!filtros.getOficinas().toString().toUpperCase().equalsIgnoreCase("SV")) {
                query.setParameter("numeroOficinas", filtros.getOficinas());
            }
        }
        if (filtros.getDistribucion() != null && !filtros.getDistribucion().isEmpty()) {
            if (!filtros.getDistribucion().toUpperCase().equalsIgnoreCase("SV")) {
                query.setParameter("distribucion", "%" + filtros.getDistribucion() + "%");
            }
        }
        if (filtros.getEstrato() != null && !filtros.getEstrato().isEmpty()) {
            if (!filtros.getEstrato().toUpperCase().equalsIgnoreCase("SV")) {
                query.setParameter("estrato", "%" + filtros.getEstrato() + "%");
            }
        }
        
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<PreFichaXlsMgl>)query.getResultList();
        
        if (control && resultList.size() > 0) {
            List<PreFichaXlsMgl> lstAux = resultList;
            resultList = new ArrayList<>();
            for (PreFichaXlsMgl preFichaXlsMgl : lstAux) {
                if (preFichaXlsMgl.getFichaGeorreferenciaMgl().getLevelEconomic() == null) {
                    resultList.add(preFichaXlsMgl);
                }
            }
        }
        return resultList;
    }
     
    public void acualizarPrefichaXls (PreFichaGeorreferenciaMgl preFichaGeoActual, PreFichaGeorreferenciaMgl preFichaGeorreferenciaMglNueva, PreFichaXlsMgl xls , boolean isNoProcesados){
        
        entityManager.getTransaction().begin();
        PreFichaXlsMgl fichaXlsMgl = entityManager.find(PreFichaXlsMgl.class, xls.getId());
         
        fichaXlsMgl.setNombreConj(xls.getNombreConj());
        fichaXlsMgl.setViaPrincipal(xls.getViaPrincipal());
        fichaXlsMgl.setPlaca(xls.getPlaca());
        fichaXlsMgl.setBarrio(xls.getBarrio());
        fichaXlsMgl.setClasificacion(xls.getClasificacion());
        fichaXlsMgl.setDistribucion(xls.getDistribucion());
        fichaXlsMgl.setRegistroValido(xls.getRegistroValido());
        fichaXlsMgl.getFichaGeorreferenciaMgl().setNeighborhood(xls.getFichaGeorreferenciaMgl().getNeighborhood());
        fichaXlsMgl.getFichaGeorreferenciaMgl().setLevelEconomic(xls.getFichaGeorreferenciaMgl().getLevelEconomic());
        fichaXlsMgl.getFichaGeorreferenciaMgl().setAddress(xls.getFichaGeorreferenciaMgl().getAddress());
        fichaXlsMgl.getFichaGeorreferenciaMgl().setSource(xls.getFichaGeorreferenciaMgl().getSource());
        fichaXlsMgl.getFichaGeorreferenciaMgl().setAlternateAddress(xls.getFichaGeorreferenciaMgl().getAlternateAddress());
        fichaXlsMgl.getFichaGeorreferenciaMgl().setLocality(xls.getFichaGeorreferenciaMgl().getLocality());
        fichaXlsMgl.getFichaGeorreferenciaMgl().setAppletStandar(xls.getFichaGeorreferenciaMgl().getAppletStandar());
        fichaXlsMgl.setIdTipoDireccion(xls.getIdTipoDireccion() != null ? xls.getIdTipoDireccion() : "");
        fichaXlsMgl.setPestana(xls.getPestana());
      
             
        PreFichaGeorreferenciaMgl geoActualizar = entityManager.find(PreFichaGeorreferenciaMgl.class, preFichaGeoActual.getId());
         geoActualizar.setActivityEconomic(preFichaGeorreferenciaMglNueva.getActivityEconomic());
         geoActualizar.setAddress(preFichaGeorreferenciaMglNueva.getAddress());
         geoActualizar.setAddressCode(preFichaGeorreferenciaMglNueva.getAddressCode());
         geoActualizar.setAddressCodeFound(preFichaGeorreferenciaMglNueva.getAddressCodeFound());
         geoActualizar.setAlternateAddress(preFichaGeorreferenciaMglNueva.getAlternateAddress());
         geoActualizar.setAppletStandar(preFichaGeorreferenciaMglNueva.getAppletStandar());
         geoActualizar.setCategory(preFichaGeorreferenciaMglNueva.getCategory());
         geoActualizar.setCodDaneMcpio(preFichaGeorreferenciaMglNueva.getCodDaneMcpio());
         geoActualizar.setCx(preFichaGeorreferenciaMglNueva.getCx());
         geoActualizar.setCy(preFichaGeorreferenciaMglNueva.getCy());
         geoActualizar.setDaneCity(preFichaGeorreferenciaMglNueva.getDaneCity());
         geoActualizar.setDanePopArea(preFichaGeorreferenciaMglNueva.getDanePopArea());
         geoActualizar.setEstadoRegistro(preFichaGeorreferenciaMglNueva.getEstadoRegistro());
         geoActualizar.setExist(preFichaGeorreferenciaMglNueva.getExist());
         geoActualizar.setIdAddress(preFichaGeorreferenciaMglNueva.getIdAddress());
         geoActualizar.setLevelEconomic(preFichaGeorreferenciaMglNueva.getLevelEconomic());
         geoActualizar.setLevelLive(preFichaGeorreferenciaMglNueva.getLevelLive());
         geoActualizar.setLocality(preFichaGeorreferenciaMglNueva.getLocality());
         geoActualizar.setNeighborhood(preFichaGeorreferenciaMglNueva.getNeighborhood());
         geoActualizar.setNodoDos(preFichaGeorreferenciaMglNueva.getNodoDos());
         geoActualizar.setNodoTres(preFichaGeorreferenciaMglNueva.getNodoTres());
         geoActualizar.setNodoUno(preFichaGeorreferenciaMglNueva.getNodoUno());
         geoActualizar.setQualifiers(preFichaGeorreferenciaMglNueva.getQualifiers());
         geoActualizar.setSource(preFichaGeorreferenciaMglNueva.getSource());
         geoActualizar.setState(preFichaGeorreferenciaMglNueva.getState());
         geoActualizar.setTranslate(preFichaGeorreferenciaMglNueva.getTranslate());
         geoActualizar.setZipCode(preFichaGeorreferenciaMglNueva.getTranslate());
         geoActualizar.setZipCodeDistrict(preFichaGeorreferenciaMglNueva.getZipCodeDistrict());
         geoActualizar.setZipCodeState(preFichaGeorreferenciaMglNueva.getZipCodeState()); 
         if(isNoProcesados){
             geoActualizar.setCambioEstrato(preFichaGeorreferenciaMglNueva.getLevelEconomic());
         }else{
             geoActualizar.setCambioEstrato(preFichaGeorreferenciaMglNueva.getCambioEstrato());
         }
         entityManager.flush();
         entityManager.getTransaction().commit();
    }

    public List<PreFichaTxtMgl> getListadoPrefichasTxtPorTab(BigDecimal idPreficha) {
        List<PreFichaTxtMgl> resultList;
        String consulta = "SELECT p FROM PreFichaTxtMgl p where p.prfId = :prfId ";
        Query query = entityManager.createQuery(consulta);
        query.setParameter("prfId", idPreficha);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<PreFichaTxtMgl>)query.getResultList();
        return resultList;
    }
  
    public List<PreFichaXlsMgl> getListadoByFaseAndIdPfAndPestana(BigDecimal idPreficha, String fase, String tab) {
        List<PreFichaXlsMgl> resultList;
        Query query = entityManager.createNamedQuery("PreFichaXlsMgl.findByFaseAndIdPfAndPestana");
        query.setParameter("prfId", idPreficha);
        query.setParameter("fase", fase);
        query.setParameter("tab", tab);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaXlsMgl>) query.getResultList();
        return resultList;
    }

}
