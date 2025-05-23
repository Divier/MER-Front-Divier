/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.ParametrosCallesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AptoValues;
import co.com.claro.mgl.jpa.entities.BmValues;
import co.com.claro.mgl.jpa.entities.CkValues;
import co.com.claro.mgl.jpa.entities.ComplementoValues;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DrTipoDireccion;
import co.com.claro.mgl.jpa.entities.ItValues;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.rest.dtos.CmtConfiguracionDto;
import co.com.claro.mgl.rest.dtos.CmtConfigurationAddressComponentDto;
import co.com.claro.mgl.utils.Constant;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author camargomf
 */
@Log4j2
public class CmtComponenteDireccionesMglManager {

    /**
     * Obtine todos los niveles para una direccion.Permite obtener todos los
 niveles necesarios para los diferentes niveles de una direccion para el
 tipo de red BI
     *
     * @author Johnnatan Ortiz
     * @return Configuracion de los niveles de la direccion segun el tipo de red
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public ConfigurationAddressComponent getConfiguracionComponente()
            throws ApplicationException {
        ConfigurationAddressComponent configurationAddressComponent = 
                new ConfigurationAddressComponent();
        configurationAddressComponent
                .setTiposDireccion(obtenerTiposDireccion());
        configurationAddressComponent.setCkValues(cargarCalleCarrera());
        configurationAddressComponent.setBmValues(cargarBarrioManzana());
        configurationAddressComponent.setItValues(cargarIntraducible());
        configurationAddressComponent.setAptoValues(cargarAptoValues());
        configurationAddressComponent.setComplementoValues(cargarComplementoValues());
        return configurationAddressComponent;
    }

    /**
     * Obtiene todos los niveles para una direccion intraducible.
     * @return {@link ConfigurationAddressComponent} Configuracion de los niveles
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    public ConfigurationAddressComponent getConfiguracionCompletaComponente()
            throws ApplicationException {
        try {
            ConfigurationAddressComponent configurationAddressComponent = new ConfigurationAddressComponent();
            configurationAddressComponent.setTiposDireccion(obtenerTiposDireccion());
            configurationAddressComponent.setCkValues(cargarCalleCarrera());
            configurationAddressComponent.setBmValues(cargarBarrioManzana());
            configurationAddressComponent.setItValues(cargarIntraducibleCompleto());
            configurationAddressComponent.setAptoValues(cargarAptoValues());
            configurationAddressComponent.setComplementoValues(cargarComplementoValues());
            return configurationAddressComponent;
        } catch (ApplicationException e) {
            LOGGER.error("Error al cargar la configuracion completa para direcciones intraducibles", e);
            throw e;
        }
    }

    /**
     * Obtine todos los niveles para una direccion.Permite obtener todos los
 niveles necesarios para los diferentes niveles de una direccion segun el
 tipo de red
     *
     * @author Johnnatan Ortiz
     * @param identificadorApp
     * @return Configuracion de los niveles de la direccion segun el tipo de red
     * @throws co.com.claro.mgl.error.ApplicationException
     * @Modificado por Juan David Hernandez
     */
    public ConfigurationAddressComponent getConfiguracionComponente(
            String identificadorApp)
            throws ApplicationException {
        ConfigurationAddressComponent configurationAddressComponent =
                new ConfigurationAddressComponent();
        configurationAddressComponent.setTiposDireccion(obtenerTiposDireccion());
        configurationAddressComponent.setCkValues(cargarCalleCarrera());
        configurationAddressComponent.setBmValues(cargarBarrioManzana());
        configurationAddressComponent.setItValues(cargarIntraducible()); 
        configurationAddressComponent.setAptoValues(cargarAptoValues());
        configurationAddressComponent.setComplementoValues(cargarComplementoValues());
        return configurationAddressComponent;
    }
    
     /**
     * Obtine todos los niveles para una direccion.Permite obtener todos los
 niveles necesarios para los diferentes niveles de una direccion segun el
 tipo de red
     *
     * @author Juan David Hernandez
     * @param tipoTecnologiaBasicaId
     * @return Configuracion de los niveles de la direccion segun el tipo de tecnologia
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ConfigurationAddressComponent getConfiguracionComponente(
            String tipoRed, BigDecimal tipoTecnologiaBasicaId)
            throws ApplicationException {
        ConfigurationAddressComponent configurationAddressComponent =
                new ConfigurationAddressComponent();
        configurationAddressComponent
                .setTiposDireccion(obtenerTiposDireccion());
        /*@author Juan David Hernandez
        Ajuste realizado para obtener la configuración por tecnologia 
        de los tipos de vivienda */
        CmtExtendidaTecnologiaMglManager manager = new CmtExtendidaTecnologiaMglManager();
        CmtBasicaMgl basica = new CmtBasicaMgl();
        basica.setBasicaId(tipoTecnologiaBasicaId);

         CmtExtendidaTecnologiaMgl extendidaTecnologia = new CmtExtendidaTecnologiaMgl();
         
         //Se obtiene la configuración por Tecnología.
         extendidaTecnologia = manager.findBytipoTecnologiaObj(basica);
        
        configurationAddressComponent.setCkValues(cargarCalleCarrera());
        configurationAddressComponent.setBmValues(cargarBarrioManzana());
        configurationAddressComponent.setItValues(cargarIntraducible());
        configurationAddressComponent.setAptoValues(cargarAptoValues(tipoRed, 
                tipoTecnologiaBasicaId, extendidaTecnologia));
        
        /*Valida si es requerido en la configuración de la tecnología 
         * el cargue del listado de complemento */
        if (extendidaTecnologia != null
                && extendidaTecnologia.getReqSubedificios() == 1) {
            configurationAddressComponent.
                    setComplementoValues(cargarComplementoValues(tipoRed));
        }
        return configurationAddressComponent;
    }

    /**
     * Obtine todos los niveles para una direccion.Permite obtener todos los
 niveles necesarios para los diferentes niveles de una direccion segun el
 tipo de red
     *
     * @author Victor Bocanegra
     * @param cmtConfiguracionDto
     * @return Configuracion de los niveles de la direccion segun el tipo de red
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public CmtConfigurationAddressComponentDto getConfiguracionComponente(
            CmtConfiguracionDto cmtConfiguracionDto)
            throws ApplicationException {
        CmtConfigurationAddressComponentDto addressComponentDto = null;
        if (cmtConfiguracionDto != null) {
            ConfigurationAddressComponent addressComponent
                    = getConfiguracionComponente(cmtConfiguracionDto.getTipoRed());
            if (addressComponent != null) {
                addressComponentDto = new CmtConfigurationAddressComponentDto();
                addressComponentDto.setAptoValues(addressComponent.getAptoValues());
                addressComponentDto.setBmValues(addressComponent.getBmValues());
                addressComponentDto.setCkValues(addressComponent.getCkValues());
                addressComponentDto.setComplementoValues(addressComponent.getComplementoValues());
                addressComponentDto.setItValues(addressComponent.getItValues());
                addressComponentDto.setTiposDireccion(addressComponent.getTiposDireccion());
                addressComponentDto.setMessageType("I");
                addressComponentDto.setMessage("Proceso Exitoso");
            } else {
                addressComponentDto = new CmtConfigurationAddressComponentDto();
                addressComponentDto.setMessageType("E");
                addressComponentDto.setMessage("Ocurrio un error obteniendo la configuracion para:" + " " + cmtConfiguracionDto.getTipoRed());
            }
        } else {
            addressComponentDto = new CmtConfigurationAddressComponentDto();
            addressComponentDto.setMessageType("E");
            addressComponentDto.setMessage("Es nesario crear una petición para consumir el Servicio.");
        }

        return addressComponentDto;
    }
    
    public List<OpcionIdNombre> obtenerCalleCarreraList(
            ConfigurationAddressComponent configurationAddressComponent)
            throws ApplicationException {

        List<OpcionIdNombre> ckList = new ArrayList<>();
        CkValues ckValues = configurationAddressComponent.getCkValues();

        if (ckValues != null) {
            ckList = new ArrayList<>();
            if (ckValues.getBisCK() != null 
                    && !ckValues.getBisCK().isEmpty()) {
                ckList.addAll(ckValues.getBisCK());
            }

            if (ckValues.getCardinalesCK() != null 
                    && !ckValues.getCardinalesCK().isEmpty()) {
                ckList.addAll(ckValues.getCardinalesCK());
            }
            if (ckValues.getCruceCK() != null 
                    && !ckValues.getCruceCK().isEmpty()) {
                ckList.addAll(ckValues.getCruceCK());
            }
            if (ckValues.getLetrasCK() != null 
                    && !ckValues.getLetrasCK().isEmpty()) {
                ckList.addAll(ckValues.getLetrasCK());
            }
            if (ckValues.getTiposDeViaPrinCK() != null 
                    && !ckValues.getTiposDeViaPrinCK().isEmpty()) {
                ckList.addAll(ckValues.getTiposDeViaPrinCK());
            }
        }

        Collections.sort(ckList);
        return ckList;
    }

    public List<OpcionIdNombre> obtenerBarrioManzanaList(
            ConfigurationAddressComponent configurationAddressComponent)
            throws ApplicationException {
        List<OpcionIdNombre> bmList = new ArrayList<>();
        BmValues bmValues = configurationAddressComponent.getBmValues();

        if (bmValues != null) {
            bmList = new ArrayList<>();
            if (bmValues.getSubdivision1Bm() != null 
                    && !bmValues.getSubdivision1Bm().isEmpty()) {
                bmList.addAll(bmValues.getSubdivision1Bm());
            }
            if (bmValues.getSubdivision2Bm() != null 
                    && !bmValues.getSubdivision2Bm().isEmpty()) {
                bmList.addAll(bmValues.getSubdivision2Bm());
            }
            if (bmValues.getSubdivision3Bm() != null 
                    && !bmValues.getSubdivision3Bm().isEmpty()) {
                bmList.addAll(bmValues.getSubdivision3Bm());
            }
            if (bmValues.getSubdivision4Bm() != null 
                    && !bmValues.getSubdivision4Bm().isEmpty()) {
                bmList.addAll(bmValues.getSubdivision4Bm());
            }
            if (bmValues.getTipoConjuntoBm() != null 
                    && !bmValues.getTipoConjuntoBm().isEmpty()) {
                bmList.addAll(bmValues.getTipoConjuntoBm());
            }
        }

        Collections.sort(bmList);
        return bmList;
    }

    public List<OpcionIdNombre> obtenerIntraducibleList(
            ConfigurationAddressComponent configurationAddressComponent)
            throws ApplicationException {

        List<OpcionIdNombre> itList = new ArrayList<>();
        ItValues itValues = configurationAddressComponent.getItValues();

        if (itValues != null) {
            itList = new ArrayList<>();
            if (itValues.getComplementoIt() != null 
                    && !itValues.getComplementoIt().isEmpty()) {
                itList.addAll(itValues.getComplementoIt());
            }
            if (itValues.getIntr1It() != null 
                    && !itValues.getIntr1It().isEmpty()) {
                itList.addAll(itValues.getIntr1It());
            }
            if (itValues.getIntr2It() != null 
                    && !itValues.getIntr2It().isEmpty()) {
                itList.addAll(itValues.getIntr2It());
            }
            if (itValues.getIntr3It() != null 
                    && !itValues.getIntr3It().isEmpty()) {
                itList.addAll(itValues.getIntr3It());
            }
            if (itValues.getIntr4It() != null 
                    && !itValues.getIntr4It().isEmpty()) {
                itList.addAll(itValues.getIntr4It());
            }
            if (itValues.getIntr5It() != null 
                    && !itValues.getIntr5It().isEmpty()) {
                itList.addAll(itValues.getIntr5It());
            }
            if (itValues.getIntr6It() != null 
                    && !itValues.getIntr6It().isEmpty()) {
                itList.addAll(itValues.getIntr6It());
            }
            if (itValues.getPlacaIt() != null 
                    && !itValues.getPlacaIt().isEmpty()) {
                itList.addAll(itValues.getPlacaIt());
            }
        }
        Collections.sort(itList);
        return itList;
    }

    public List<OpcionIdNombre> obtenerAptoList(
            ConfigurationAddressComponent configurationAddressComponent)
            throws ApplicationException {

        List<OpcionIdNombre> aptoList = new ArrayList<>();
        AptoValues aptoValues = configurationAddressComponent.getAptoValues();

        if (aptoValues != null) {
            if (aptoValues.getTiposApto() != null 
                    && !aptoValues.getTiposApto().isEmpty()) {
                aptoList.addAll(aptoValues.getTiposApto());
            }
            if (aptoValues.getTiposAptoComplemento() != null 
                    && !aptoValues.getTiposAptoComplemento().isEmpty()) {
                aptoList.addAll(aptoValues.getTiposAptoComplemento());
            }
        }
        Collections.sort(aptoList);
        
        if (!aptoList.isEmpty()) {
            List<OpcionIdNombre> aptoListTemp = new ArrayList<>(aptoList);
            for (int i = 1; i < aptoListTemp.size(); i++) {
                if (aptoListTemp.get(i - 1).getDescripcion().equals(aptoListTemp.get(i).getDescripcion())) {
                    aptoList.remove(aptoListTemp.get(i));
                }
            }
        }

        return aptoList;
    }

    public List<OpcionIdNombre> obtenerComplementoList(
            ConfigurationAddressComponent configurationAddressComponent) 
            throws ApplicationException {

        List<OpcionIdNombre> complementoList = new ArrayList<>();
        ComplementoValues complementoValues = 
                configurationAddressComponent.getComplementoValues();

        if (complementoValues != null) {
            if (complementoValues.getComplementoDir() != null 
                    && !complementoValues.getComplementoDir().isEmpty()) {
                complementoList.addAll(complementoValues.getComplementoDir());
            }
        }
        Collections.sort(complementoList);
        return complementoList;
    }

    private List<DrTipoDireccion> obtenerTiposDireccion()
            throws ApplicationException {
        List<DrTipoDireccion> listTipoDireccion;
        CmtDrTiposDireccionMglManager cmtDrTiposDireccionMglManager = 
                new CmtDrTiposDireccionMglManager();
        listTipoDireccion = cmtDrTiposDireccionMglManager.findAll();
        return listTipoDireccion;
    }

    private CkValues cargarCalleCarrera() {
        CkValues ckValues = new CkValues();

        ckValues.setTiposDeViaPrinCK(
                traerListasPorTipo(Constant.TIPO_VIA_PRINCIPAL_CK));
        ckValues.setLetrasCK(
                traerListasPorTipo(Constant.LETRA_NUMEROS_BIS_CK));
        ckValues.setBisCK(
                traerListasPorTipo(Constant.LETRA_NUMEROS_BIS_CK));
        ckValues.setCardinalesCK(
                traerListasPorTipo(Constant.CARDINALES_CK));
        ckValues.setCruceCK(
                traerListasPorTipo(Constant.CRUCE_CK));

        return ckValues;
    }

    private BmValues cargarBarrioManzana() {
        BmValues bmValues = new BmValues();

        bmValues.setTipoConjuntoBm(
                traerListasPorTipo(Constant.TIPO_CONJUNTO_BM));
        //Adicion Opcion Barrio
        OpcionIdNombre opcionIdNombre = new OpcionIdNombre();
        opcionIdNombre.setDescripcion("BARRIO");
        opcionIdNombre.setIdParametro("BARRIO");
        opcionIdNombre.setIdTipo("BARRIO");
        bmValues.getTipoConjuntoBm().add(opcionIdNombre);
        //Fin Adicion Opcion Barrio
        bmValues.setSubdivision1Bm(
                traerListasPorTipo(Constant.SUBDIVISION1_BM));
        bmValues.setSubdivision2Bm(
                traerListasPorTipo(Constant.SUBDIVISION2_BM));
        bmValues.setSubdivision3Bm(
                traerListasPorTipo(Constant.SUBDIVISION3_BM));
        bmValues.setSubdivision4Bm(
                traerListasPorTipo(Constant.SUBDIVISION4_BM));

        return bmValues;
    }

    
    //SIN ELEMENTOS REPETIDOS
    private ItValues cargarIntraducible() {
        ItValues itValues = new ItValues();

         List<OpcionIdNombre> list = new ArrayList<>();
        itValues.setAllItems(list);
        
        itValues.setIntr1It(traerListasPorTipo(Constant.TIPO_VIA_PRINCIPAL_IT));  
         //se agregan todos los elementos de la primera lista
        itValues.getAllItems().addAll(itValues.getIntr1It());
        
        itValues.setIntr2It(traerListasPorTipo(Constant.COMPLEMENTO_VIA_PRINCIPAL_IT));
        
         List<OpcionIdNombre>  intr2ItList = traerListasPorTipo(Constant.COMPLEMENTO_VIA_PRINCIPAL_IT);
         List<OpcionIdNombre>  intr2ItTmp = new ArrayList<>();
        
        if (intr2ItList != null && !intr2ItList.isEmpty()) {
            for (OpcionIdNombre intr2It : intr2ItList) {
                int registroRepetido = 0;
                for (OpcionIdNombre allItems : itValues.getAllItems()) {
                    if (intr2It.getDescripcion().equalsIgnoreCase(allItems.getDescripcion())) {
                        registroRepetido++;
                        break;
                    }
                }
                if (registroRepetido == 0) {
                    intr2ItTmp.add(intr2It);
                }
            }
            //se agregan los que no quedaron y que no estan repetidos
            if (!intr2ItTmp.isEmpty()) {
                itValues.setIntr2It(intr2ItTmp);
                itValues.getAllItems().addAll(itValues.getIntr2It());
            }
        }    
        
        
        List<OpcionIdNombre> intr3ItList = traerListasPorTipo(Constant.ZONA_RURAL_IT);
        List<OpcionIdNombre> intr3ItTmp = new ArrayList<>();

        if (intr3ItList != null && !intr3ItList.isEmpty()) {
            for (OpcionIdNombre intr3It : intr3ItList) {
                int registroRepetido = 0;
                for (OpcionIdNombre allItems : itValues.getAllItems()) {
                    if (intr3It.getDescripcion().equalsIgnoreCase(allItems.getDescripcion())) {
                        registroRepetido++;
                        break;
                    }
                }
                if (registroRepetido == 0) {
                    intr3ItTmp.add(intr3It);
                }
            }
            //se agregan los que no quedaron y que no estan repetidos
            if (!intr3ItTmp.isEmpty()) {
                itValues.setIntr3It(intr3ItTmp);
                itValues.getAllItems().addAll(itValues.getIntr3It());
            }
        }

        List<OpcionIdNombre> intr4ItList = traerListasPorTipo(Constant.SUBDIVISION_IT); 
        List<OpcionIdNombre> intr4ItTmp = new ArrayList<>();

        if (intr4ItList != null && !intr4ItList.isEmpty()) {
            for (OpcionIdNombre intr4It : intr4ItList) {
                int registroRepetido = 0;
                for (OpcionIdNombre allItems : itValues.getAllItems()) {
                    if (intr4It.getDescripcion().equalsIgnoreCase(allItems.getDescripcion())) {
                        registroRepetido++;
                        break;
                    }
                }
                if (registroRepetido == 0) {
                    intr4ItTmp.add(intr4It);
                }
            }
            //se agregan los que no quedaron y que no estan repetidos
            if (!intr4ItTmp.isEmpty()) {
                itValues.setIntr4It(intr4ItTmp);
                itValues.getAllItems().addAll(itValues.getIntr4It());
            }
        }     
        
         List<OpcionIdNombre>  intr5ItList = traerListasPorTipo(Constant.SUBDIVISION1_IT);
         List<OpcionIdNombre>  intr5ItTmp = new ArrayList<>();
        
        if (intr5ItList != null && !intr5ItList.isEmpty()) {
            for (OpcionIdNombre intr5It : intr5ItList) {
                int registroRepetido = 0;
                for (OpcionIdNombre allItems : itValues.getAllItems()) {
                    if (intr5It.getDescripcion().equalsIgnoreCase(allItems.getDescripcion())) {
                        registroRepetido++;
                        break;
                    }
                }
                if (registroRepetido == 0) {
                    intr5ItTmp.add(intr5It);
                }
            }
            //se agregan los que no quedaron y que no estan repetidos
            if (!intr5ItTmp.isEmpty()) {
                itValues.setIntr5It(intr5ItTmp);
                itValues.getAllItems().addAll(itValues.getIntr5It());
            }
        }        
        
         List<OpcionIdNombre>  intr6ItList = traerListasPorTipo(Constant.OPCION_IT);
         List<OpcionIdNombre>  intr6ItTmp = new ArrayList<>();
         itValues.setIntr6It(traerListasPorTipo(Constant.OPCION_IT)); 

        if (intr6ItList != null && !intr6ItList.isEmpty()) {
            for (OpcionIdNombre intr6It : intr6ItList) {
                int registroRepetido = 0;
                for (OpcionIdNombre allItems : itValues.getAllItems()) {
                    if (intr6It.getDescripcion().equalsIgnoreCase(allItems.getDescripcion())) {
                        registroRepetido++;
                        break;
                    }
                }
                if (registroRepetido == 0) {
                    intr6ItTmp.add(intr6It);
                }

            }
            //se agregan los que no quedaron y que no estan repetidos
            if (!intr6ItTmp.isEmpty()) {
                itValues.setIntr6It(intr6ItTmp);
                itValues.getAllItems().addAll(itValues.getIntr6It());                
            }
        }

        itValues.setPlacaIt(
                traerListasPorTipo(Constant.PLACA_IT));
        
        return itValues;
    }

    private AptoValues cargarAptoValues() {
        AptoValues aptoValues = new AptoValues();
        aptoValues.setTiposApto(
                traerListasPorTipo(Constant.TIPO_APTO_BI));
        aptoValues.setTiposApto(
                    traerListasPorTipo(Constant.TIPO_APTO_UNI));
        
        List <OpcionIdNombre> tiposAptoComplemento = traerListasPorTipo(Constant.TIPO_APTO_COMP);
        List <OpcionIdNombre> tipoAptoComplementoAgregarList = new ArrayList<>();
        
        //JDHT RECORRIDO PARA NO AGREGAR VALORES REPETIDOS AL LISTADO DE APARTAMENTO
        if (tiposAptoComplemento != null && !tiposAptoComplemento.isEmpty()) {
            for (OpcionIdNombre valor : tiposAptoComplemento) {

                if (aptoValues != null && aptoValues.getTiposApto() != null
                        && !aptoValues.getTiposApto().isEmpty()) {
                    
                    int valorExistente = 0;
                    for (OpcionIdNombre opcionIdNombre : aptoValues.getTiposApto()) {
                        
                        if (valor.getDescripcion().equalsIgnoreCase(opcionIdNombre.getDescripcion())) {
                            valorExistente++;
                        }
                    }
                    if (valorExistente == 0) {
                        tipoAptoComplementoAgregarList.add(valor);
                    }
                }
            }
        }

        //listado de los valores que no estan repetidos y que se pueden agregar al listado final
        if (tipoAptoComplementoAgregarList != null && !tipoAptoComplementoAgregarList.isEmpty()) {
            aptoValues.setTiposAptoComplemento(tipoAptoComplementoAgregarList);
        }

       
        return aptoValues;
    }

    /**
     * Carga la configuracion de niveles intraducible
     *
     * @return {@link ItValues} Valor de la configuracion de niveles intraducible
     * @author Gildardo Mora
     */
    private ItValues cargarIntraducibleCompleto() {
        ItValues itValues = new ItValues();
        List<OpcionIdNombre> list = new ArrayList<>();
        itValues.setAllItems(list);
        List<OpcionIdNombre> lista1 = traerListasPorTipo(Constant.TIPO_VIA_PRINCIPAL_IT);
        itValues.setIntr1It(lista1);
        List<OpcionIdNombre> listaCompleta = new ArrayList<>(lista1);
        List<OpcionIdNombre> lista2 = traerListasPorTipo(Constant.COMPLEMENTO_VIA_PRINCIPAL_IT);
        itValues.setIntr2It(lista2);
        listaCompleta.addAll(lista2);
        List<OpcionIdNombre> lista3 = traerListasPorTipo(Constant.ZONA_RURAL_IT);
        itValues.setIntr3It(lista3);
        listaCompleta.addAll(lista3);
        List<OpcionIdNombre> lista4 = traerListasPorTipo(Constant.SUBDIVISION_IT);
        itValues.setIntr4It(lista4);
        listaCompleta.addAll(lista4);
        List<OpcionIdNombre> lista5 = traerListasPorTipo(Constant.SUBDIVISION1_IT);
        itValues.setIntr5It(lista5);
        listaCompleta.addAll(lista5);
        List<OpcionIdNombre> lista6 = traerListasPorTipo(Constant.OPCION_IT);
        itValues.setIntr6It(lista6);
        listaCompleta.addAll(lista6);

        itValues.setPlacaIt(traerListasPorTipo(Constant.PLACA_IT));
        // lista completa sin duplicados a partir del idParametro
        List<OpcionIdNombre> listaCompletaSinDuplicados = new ArrayList<>(listaCompleta.stream()
                .collect(Collectors.toMap(
                        OpcionIdNombre::getIdParametro,
                        Function.identity(),
                        (existente, nuevo) -> existente))
                .values());

        itValues.setAllItems(listaCompletaSinDuplicados);
        itValues.setAllItems(listaCompleta);
        return itValues;
    }

    private AptoValues cargarAptoValues(String tipoRed,
            BigDecimal tipoTecnologiaBasicaId, CmtExtendidaTecnologiaMgl extendidaTecnologia ) 
            throws ApplicationException {

        AptoValues aptoValues = new AptoValues(); 
        List<OpcionIdNombre> tiposApto = new ArrayList<>();
        if (extendidaTecnologia != null && extendidaTecnologia.getTiposVivienda() != null
                && !extendidaTecnologia.getTiposVivienda().isEmpty()) {

            String[] viviendas = extendidaTecnologia.getTiposVivienda().split("\\|");
            
            for (int i = 0; i < viviendas.length; i++) {
                OpcionIdNombre apto = new OpcionIdNombre();
                apto.setIdParametro(viviendas[i]);
                apto.setDescripcion(viviendas[i]);
                tiposApto.add(apto);
            }
            aptoValues.setTiposApto(tiposApto);            
        }
        
        aptoValues.setTiposAptoComplemento(
                    traerListasPorTipoPorTecnologia(Constant.TIPO_APTO_COMP, tiposApto));

       
        return aptoValues;
    }
    
     private AptoValues cargarAptoValues(String tipoRed) {

        AptoValues aptoValues = new AptoValues();        
        
      if (tipoRed != null && !tipoRed.trim().isEmpty()
                && (tipoRed.equalsIgnoreCase(Constant.HFC_UNI) 
              || tipoRed.equalsIgnoreCase(Constant.DTH))) {
            aptoValues.setTiposApto(
                    traerListasPorTipo(Constant.TIPO_APTO_UNI));
        } else {
            aptoValues.setTiposApto(
                    traerListasPorTipo(Constant.TIPO_APTO_BI));
        }        
        aptoValues.setTiposAptoComplemento(
                traerListasPorTipo(Constant.TIPO_APTO_COMP));
        return aptoValues;
    }

    private ComplementoValues cargarComplementoValues() {
        ComplementoValues complementoValues = new ComplementoValues();
        complementoValues.setComplementoDir(
                traerListasPorTipo(Constant.TIPO_COMPLEMENTO));
        return complementoValues;
    }

    private ComplementoValues cargarComplementoValues(String tipoRed) {
        ComplementoValues complementoValues = new ComplementoValues();
        // cargar configuracion por tipo de red, si aplica complemento se hace consulta
        if (tipoRed != null && !tipoRed.trim().isEmpty()
                && !tipoRed.equalsIgnoreCase(Constant.TIPO_RED_BIDIRECCIONAL_INSPIRA)) {
            complementoValues.setComplementoDir(
                    traerListasPorTipo(Constant.TIPO_COMPLEMENTO));
        }
        return complementoValues;
    }

    public List<OpcionIdNombre> traerListasPorTipo(String tipo) {
        List<OpcionIdNombre> listaOpciones = new ArrayList<>();
        ParametrosCallesManager parametrosCallesManager = 
                new ParametrosCallesManager();
        List<ParametrosCalles> listParametrosCalles = 
                parametrosCallesManager.findByTipo(tipo);
        for (ParametrosCalles parametrosCalles : listParametrosCalles) {
            OpcionIdNombre opcionIdNombre = new OpcionIdNombre();
            opcionIdNombre.setDescripcion(parametrosCalles.getDescripcion());
            opcionIdNombre.setIdParametro(parametrosCalles.getDescripcion());
            opcionIdNombre.setIdTipo(parametrosCalles.getIdTipo());
            listaOpciones.add(opcionIdNombre);
        }
        return listaOpciones;
    }
    
    /**
     * Funcion utilizada par obtener los parametros de la lista de complemento
     * de apartamento
     *
     * @author Juan David Hernandez
     */
    private List<OpcionIdNombre> traerListasPorTipoPorTecnologia(String tipo,  List<OpcionIdNombre> tiposApto) {
        List<OpcionIdNombre> listaOpciones = new ArrayList<>();
        ParametrosCallesManager parametrosCallesManager = 
                new ParametrosCallesManager();
        List<ParametrosCalles> listParametrosCalles = 
                parametrosCallesManager.findByTipo(tipo);
        
        if (tiposApto != null && !tiposApto.isEmpty() && listParametrosCalles != null
                && !listParametrosCalles.isEmpty()) {
            boolean parametroRepetido = false;
            for (ParametrosCalles parametrosCalles : listParametrosCalles) {
                for (OpcionIdNombre parametrosTipoApto : tiposApto) {
                    if (parametrosCalles.getDescripcion().equals(parametrosTipoApto.getIdParametro())) {
                        parametroRepetido = true;
                    }
                }
                if (!parametroRepetido) {
                    OpcionIdNombre opcionIdNombre = new OpcionIdNombre();
                    opcionIdNombre.setDescripcion(parametrosCalles.getDescripcion());
                    opcionIdNombre.setIdParametro(parametrosCalles.getDescripcion());
                    opcionIdNombre.setIdTipo(parametrosCalles.getIdTipo());
                    listaOpciones.add(opcionIdNombre);
                }
                parametroRepetido = false;
            }
        } else {
            if (listParametrosCalles != null && !listParametrosCalles.isEmpty()) {
                for (ParametrosCalles parametrosCalles : listParametrosCalles) {
                    OpcionIdNombre opcionIdNombre = new OpcionIdNombre();
                    opcionIdNombre.setDescripcion(parametrosCalles.getDescripcion());
                    opcionIdNombre.setIdParametro(parametrosCalles.getDescripcion());
                    opcionIdNombre.setIdTipo(parametrosCalles.getIdTipo());
                    listaOpciones.add(opcionIdNombre);
                }
            }
        }
        return listaOpciones;
    }
}
