/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.CmtFiltroPrefichasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 * Impl Dao para operaciones de Cargue de Fichas
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */

public class PreFichaXlsMglNewDaoImpl extends GenericDaoImpl<PreFichaXlsMglNew>{
    
    public List<PreFichaXlsMglNew> savePreFichaXlsList (List<PreFichaXlsMglNew> prefichaXlsList)throws ApplicationException {
        for(PreFichaXlsMglNew pf : prefichaXlsList){
            create(pf);
        }
        return prefichaXlsList;
    }
    
    public List<PreFichaXlsMglNew> getListXLSByPrefichaFase(BigDecimal idPreficha, String fase){
        List<PreFichaXlsMglNew> resultList;
        Query query = entityManager.createNamedQuery("PreFichaXlsMglNew.findByFaseAndIdPf");
        query.setParameter("prfId", idPreficha);
        query.setParameter("fase", fase);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaXlsMglNew>)query.getResultList();
        return resultList;
    }
        
    public List<PreFichaXlsMglNew> getListadoPrefichasPorTab(BigDecimal idPreficha, String fase, String tab , CmtFiltroPrefichasDto filtros){
        List<PreFichaXlsMglNew> resultList;
        StringBuilder consulta = new StringBuilder("SELECT DISTINCT p FROM PreFichaXlsMglNew p "
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
        resultList = (List<PreFichaXlsMglNew>)query.getResultList();
        
        if (control && resultList.size() > 0) {
            List<PreFichaXlsMglNew> lstAux = resultList;
            resultList = new ArrayList<>();
            for (PreFichaXlsMglNew preFichaXlsMgl : lstAux) {
                if (preFichaXlsMgl.getFichaGeorreferenciaMgl().getLevelEconomic() == null) {
                    resultList.add(preFichaXlsMgl);
                }
            }
        }
        return resultList;
    }
    
    public void acualizarPrefichaXls (PreFichaGeorreferenciaMglNew preFichaGeoActual, PreFichaGeorreferenciaMglNew preFichaGeorreferenciaMglNueva, PreFichaXlsMglNew xls , boolean isNoProcesados){
        
        entityManager.getTransaction().begin();
        PreFichaXlsMglNew fichaXlsMgl = entityManager.find(PreFichaXlsMglNew.class, xls.getId());
         
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
        fichaXlsMgl.setNuevoApto(xls.getNuevoApto() != null ? xls.getNuevoApto() : "");
        fichaXlsMgl.setPisoUso(xls.getPisoUso() != 0 ? xls.getPisoUso(): 0);
        fichaXlsMgl.setUnidadUso(xls.getUnidadUso() != 0 ? xls.getUnidadUso(): 0);
        fichaXlsMgl.setActUnidad(xls.getActUnidad() != 0 ? xls.getActUnidad() : 0);
              
        PreFichaGeorreferenciaMglNew geoActualizar = entityManager.find(PreFichaGeorreferenciaMglNew.class, preFichaGeoActual.getId());
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
    
    public List<PreFichaTxtMglNew> getListadoPrefichasTxtPorTab(BigDecimal idPreficha) {
        List<PreFichaTxtMglNew> resultList;
        String consulta = "SELECT p FROM PreFichaTxtMglNew p where p.prfId = :prfId ";
        Query query = entityManager.createQuery(consulta);
        query.setParameter("prfId", idPreficha);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<PreFichaTxtMglNew>)query.getResultList();
        return resultList;
    }
  
    public List<PreFichaXlsMglNew> getListadoByFaseAndIdPfAndPestana(BigDecimal idPreficha, String fase, String tab) {
        List<PreFichaXlsMglNew> resultList;
        Query query = entityManager.createNamedQuery("PreFichaXlsMglNew.findByFaseAndIdPfAndPestana");
        query.setParameter("prfId", idPreficha);
        query.setParameter("fase", fase);
        query.setParameter("tab", tab);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaXlsMglNew>) query.getResultList();
        return resultList;
    }

}
