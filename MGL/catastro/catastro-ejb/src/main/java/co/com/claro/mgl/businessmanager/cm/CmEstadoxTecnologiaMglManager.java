/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtEstadoxTecnologiaMglDaoImpl;
import co.com.claro.mgl.dtos.EstadosTecnologiasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author cardenaslb
 */
public class CmEstadoxTecnologiaMglManager {

    /**
     * Constante para la escritura de log de la clase actual.
     */
    private static final Logger LOGGER = LogManager.getLogger(CmEstadoxTecnologiaMglManager.class);

    CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();

    /**
     * Buscar todas las validaciones. Busca todas las validaciones de los
     * proyectos.
     *
     * @author Lenis Cardenas
     * @version 1.0 revision 03/08/2017.
     * @return La lista de validaciones.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en
     * a ejecución de la sentencia.
     */
    public List<CmtEstadoxTecnologiaMgl> findAll() throws ApplicationException {

        List<CmtEstadoxTecnologiaMgl> resulList;
        CmtEstadoxTecnologiaMglDaoImpl cmtEstadoxTecnologiaMglDaoImpl
                = new CmtEstadoxTecnologiaMglDaoImpl();

        resulList = cmtEstadoxTecnologiaMglDaoImpl.findAll();

        return resulList;
    }

    /**
     * Crear una valiacion a un proyecto.Crea una valiacion a un proyecto.
     *
     * @author Lenis Cardenas
     * @version 1.0 revision 03/08/2017.
     * @param cmtTipoSolicitudMgl Validacion de proyecto a crear.
     * @param user
     * @param perfil
     * @return La validacion creada con su nuevo id.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en
     * a ejecución de la sentencia.
     */
    public CmtEstadoxTecnologiaMgl create(CmtEstadoxTecnologiaMgl cmtTipoSolicitudMgl, String user, int perfil)
            throws ApplicationException {
        CmtEstadoxTecnologiaMglDaoImpl cmtEstadoxTecnologiaMglDaoImpl
                = new CmtEstadoxTecnologiaMglDaoImpl();
        return cmtEstadoxTecnologiaMglDaoImpl.create(cmtTipoSolicitudMgl);
    }

    /**
     * Actualizar la validacion a un proyecto.Actualiza la validacion a un
 proyecto.
     *
     * @author Lenis Cardenas
     * @version 1.0 revision 03/08/2017.
     * @param cmtTipoSolicitudMgl Validacion de proyecto a actualizar.
     * @param user
     * @param perfil
     * @return La validacion de un proyecto actualizada.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en
     * a ejecución de la sentencia.
     */
    public CmtEstadoxTecnologiaMgl update(CmtEstadoxTecnologiaMgl cmtTipoSolicitudMgl, String user, int perfil)
            throws ApplicationException {
        CmtEstadoxTecnologiaMglDaoImpl cmtEstadoxTecnologiaMglDaoImpl
                = new CmtEstadoxTecnologiaMglDaoImpl();
        return cmtEstadoxTecnologiaMglDaoImpl.update(cmtTipoSolicitudMgl);
    }

    /**
     * Borrar una validación de un proyecto. Borra una validación de un
     * proyecto.
     *
     * @author Lenis Cardenas
     * @version 1.0 revision 03/08/2017.
     * @param cmtTipoSolicitudMgl Validacion a eliminar del proyecto.
     * @return True si la eliminación es completada.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en
     * a ejecución de la sentencia.
     */
    public boolean delete(CmtEstadoxTecnologiaMgl cmtTipoSolicitudMgl)
            throws ApplicationException {
        CmtEstadoxTecnologiaMglDaoImpl cmtEstadoxTecnologiaMglDaoImpl
                = new CmtEstadoxTecnologiaMglDaoImpl();
        return cmtEstadoxTecnologiaMglDaoImpl.delete(cmtTipoSolicitudMgl);
    }

    public List<EstadosTecnologiasDto> getListaConf(String user, int perfil, String estadoCcmm) throws ApplicationException {
        CmtEstadoxTecnologiaMglDaoImpl cmtEstadoxTecnologiaMglDaoImpl
                = new CmtEstadoxTecnologiaMglDaoImpl();
        List<EstadosTecnologiasDto> resulListEstados = new ArrayList<>();
        List<CmtEstadoxTecnologiaMgl> resulListEstadosxTec;

        CmtTipoBasicaMgl cmtTipoBasicaMgl;
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        cmtTipoBasicaMgl=cmtTipoBasicaMglManager.findByCodigoInternoApp(
                Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
        
        List<CmtBasicaMgl> estados;
        
        if (estadoCcmm != null && !estadoCcmm.isEmpty()) {
             estados = cmtBasicaMglDaoImpl.findLstByTipoBasicaAndNombre(cmtTipoBasicaMgl, estadoCcmm);
        }else{
             estados = cmtBasicaMglDaoImpl.findByTipoBasica(cmtTipoBasicaMgl);
        }
       

        CmtTipoBasicaMgl cmtTipoBasicaTec;
        cmtTipoBasicaTec=cmtTipoBasicaMglManager.findByCodigoInternoApp(
                Constant.TIPO_BASICA_TECNOLOGIA);
        List<CmtBasicaMgl> tecnologias = cmtBasicaMglDaoImpl.findByTipoBasica(cmtTipoBasicaTec);

        if (estados != null && !estados.isEmpty()) {
            for (CmtBasicaMgl estado : estados) {
                resulListEstadosxTec = cmtEstadoxTecnologiaMglDaoImpl.findByEstado(estado);
                List<CmtBasicaMgl> listaTec = new ArrayList<CmtBasicaMgl>();

                if (!resulListEstadosxTec.isEmpty()) {
                    for (CmtEstadoxTecnologiaMgl estxTec : resulListEstadosxTec) {

                        CmtBasicaMgl cmtBas = new CmtBasicaMgl();
                        cmtBas.setBasicaId(estxTec.getBasicaId_tecnologias().getBasicaId());
                        cmtBas.setNombreBasica(estxTec.getBasicaId_tecnologias().getNombreBasica());
                        cmtBas.setActivacion(estxTec.getActivacion());
                        cmtBas.setIdEstadosxTec(estxTec.getId());
                        listaTec.add(cmtBas);
                    }

                }

                EstadosTecnologiasDto estDto = new EstadosTecnologiasDto();
                estDto.setEstadosCm(estado);
                Collections.sort(listaTec, new Comparator<CmtBasicaMgl>() {

                    @Override
                    public int compare(CmtBasicaMgl p1, CmtBasicaMgl p2) {
                       return new Integer((p1.getBasicaId().intValueExact())).compareTo(p2.getBasicaId().intValueExact());
                    }

                });
                estDto.setInnerlistTec(listaTec);
                estDto.setIdEstadosxTecnologia(null);
                resulListEstados.add(estDto);
            }

        }

        for (EstadosTecnologiasDto tecBesicas : resulListEstados) {
            for (CmtBasicaMgl listaBasica : tecnologias) {
                boolean existe = false;

                if (tecBesicas.getInnerlistTec() != null && !tecBesicas.getInnerlistTec().isEmpty()) {
                    for (CmtBasicaMgl listaBasicaTec : tecBesicas.getInnerlistTec()) {
                        if (listaBasicaTec.getBasicaId().equals(listaBasica.getBasicaId())) {
                            existe = true;
                        }
                    }
                    if (!existe) {
                        CmtBasicaMgl cmtBasica = new CmtBasicaMgl();
                        cmtBasica.setBasicaId(listaBasica.getBasicaId());
                        cmtBasica.setActivacion(false);
                        cmtBasica.setNombreBasica(listaBasica.getNombreBasica());
                        tecBesicas.getInnerlistTec().add(cmtBasica);
                    }
                } else {
                    List<CmtBasicaMgl> listaTecnoNew2 = new ArrayList<>();
                    for (CmtBasicaMgl tecNew : tecnologias) {
                        CmtBasicaMgl cmtBasTec = new CmtBasicaMgl();
                        cmtBasTec.setBasicaId(tecNew.getBasicaId());
                        cmtBasTec.setActivacion(false);
                        cmtBasTec.setNombreBasica(tecNew.getNombreBasica());
                        listaTecnoNew2.add(cmtBasTec);
                        tecBesicas.setInnerlistTec(listaTecnoNew2);
                    }
                }

            }

        }

        for (EstadosTecnologiasDto estaos : resulListEstados) {
            LOGGER.error("Id est xtec" + estaos.getEstadosCm().getBasicaId());
            for (CmtBasicaMgl bs : estaos.getInnerlistTec()) {
                LOGGER.error("id basica" + bs.getIdEstadosxTec());
                LOGGER.error("Tecnologias" + bs.getNombreBasica());
            }

        }


        return resulListEstados;
    }

    public boolean createConfig(List<EstadosTecnologiasDto> resulListEstados, String user, int perfil) {
        CmtEstadoxTecnologiaMglDaoImpl cmtEstadoxTecnologiaMglDaoImpl
                = new CmtEstadoxTecnologiaMglDaoImpl();

        for (EstadosTecnologiasDto estadosTecnologiasDto : resulListEstados) {

            for (CmtBasicaMgl c : estadosTecnologiasDto.getInnerlistTec()) {
                if (c.getIdEstadosxTec() == null) {

                    CmtEstadoxTecnologiaMgl cmtEstadoxTecnologiaMgl = new CmtEstadoxTecnologiaMgl();

                    CmtBasicaMgl basicaTec = new CmtBasicaMgl();

                    basicaTec.setBasicaId(c.getBasicaId());

                    cmtEstadoxTecnologiaMgl.setBasicaId_tecnologias(basicaTec);
                    cmtEstadoxTecnologiaMgl.setBasicaId_estados(estadosTecnologiasDto.getEstadosCm());
                    cmtEstadoxTecnologiaMgl.setActivacion(c.getActivacion());
                    cmtEstadoxTecnologiaMgl.setEstadoRegistro(1);
                    cmtEstadoxTecnologiaMgl.setFechaCreacion(new Date());
                    cmtEstadoxTecnologiaMgl.setUsuarioCreacion(user);
                    cmtEstadoxTecnologiaMgl.setPerfilCreacion(perfil);
                    cmtEstadoxTecnologiaMgl.setFechaEdicion(new Date());
                    cmtEstadoxTecnologiaMgl.setPerfilEdicion(0);
                    cmtEstadoxTecnologiaMgl.setUsuarioEdicion("NA");
                    try {
                        cmtEstadoxTecnologiaMglDaoImpl.create(cmtEstadoxTecnologiaMgl);

                    } catch (ApplicationException e) {
                        LOGGER.error("Error en createConfig. " +e.getMessage(), e);      
                    }

                } else {

                    CmtEstadoxTecnologiaMgl cmtEstadoxTecnologiaMgl = new CmtEstadoxTecnologiaMgl();
                    cmtEstadoxTecnologiaMgl.setId(c.getIdEstadosxTec());
                    cmtEstadoxTecnologiaMgl.setBasicaId_estados(estadosTecnologiasDto.getEstadosCm());
                    cmtEstadoxTecnologiaMgl.setActivacion(c.getActivacion());
                    cmtEstadoxTecnologiaMgl.setEstadoRegistro(1);
                    cmtEstadoxTecnologiaMgl.setFechaCreacion(new Date());
                    cmtEstadoxTecnologiaMgl.setUsuarioCreacion(user);
                    cmtEstadoxTecnologiaMgl.setPerfilCreacion(perfil);
                    cmtEstadoxTecnologiaMgl.setFechaEdicion(new Date());
                    cmtEstadoxTecnologiaMgl.setPerfilEdicion(0);
                    cmtEstadoxTecnologiaMgl.setUsuarioEdicion("NA");
                    CmtBasicaMgl basicaTec = new CmtBasicaMgl();
                    basicaTec.setBasicaId(c.getBasicaId());
                    cmtEstadoxTecnologiaMgl.setBasicaId_tecnologias(basicaTec);
            
                    try {
                        cmtEstadoxTecnologiaMglDaoImpl.update(cmtEstadoxTecnologiaMgl);
                    } catch (ApplicationException e) {
                        LOGGER.error("Error en crearConfig. " +e.getMessage(), e);      
                    }

                }
            }

        }
        return true;
    }
    
     public List<CmtEstadoxTecnologiaMgl> findByEstado(CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {

        List<CmtEstadoxTecnologiaMgl> resulList;
        CmtEstadoxTecnologiaMglDaoImpl cmtEstadoxTecnologiaMglDaoImpl
                = new CmtEstadoxTecnologiaMglDaoImpl();

        resulList = cmtEstadoxTecnologiaMglDaoImpl.findByEstado(cmtBasicaMgl);

        return resulList;
    }

}
