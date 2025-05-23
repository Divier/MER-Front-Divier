/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.MarcasHhppMglDaoImpl;
import co.com.claro.mgl.dao.impl.MarcasMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroMarcasMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Admin
 */
public class MarcasHhppMglManager {
    
    private static final Logger LOGGER = LogManager.getLogger(MarcasHhppMglManager.class);

    MarcasHhppMglDaoImpl marcasHhppMglDaoImpl = new MarcasHhppMglDaoImpl();

    public List<MarcasHhppMgl> findAll() throws ApplicationException {
        List<MarcasHhppMgl> result;
        MarcasHhppMglDaoImpl marcasHhppMglDaoImpl1 = new MarcasHhppMglDaoImpl();
        result = marcasHhppMglDaoImpl1.findAll();
        return result;
    }

    public List<MarcasHhppMgl> findMarcasHhppByHhppMgl(List<HhppMgl> HhppMglList, BigDecimal blackLis) throws ApplicationException {
        return marcasHhppMglDaoImpl.findMarcasHhppByHhppMgl(HhppMglList, blackLis);
    }
    
    public List<MarcasHhppMgl> findMarcasHhppByHhppAndMarca(HhppMgl hhppMgl, MarcasMgl marcasMgl) throws ApplicationException{
        return marcasHhppMglDaoImpl.findMarcasHhppByHhppAndMarca(hhppMgl, marcasMgl);
    }
    private ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
    private MarcasMglManager marcasMglManager = new MarcasMglManager();
    
    public MarcasHhppMgl create(MarcasHhppMgl t) throws ApplicationException {
        //no todos los hhpp tienen id de RR aqui se realiza la validacion
        if (    t.getEstadoRegistro() == 1 && 
                t.getHhpp().getHhpIdrR() != null &&
                !t.getHhpp().getHhpIdrR().isEmpty()     ) {
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && 
                parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)){
                DireccionRRManager drrm = new DireccionRRManager(true);
                //se busca de nuevo el hhpp para tener todas las marcas actualizadas al instante  
                List<MarcasMgl> marcasMgls = marcasMglManager.findMarcasMglByHhpp(t.getHhpp());
                marcasMgls.add(t.getMarId());
                drrm.editarMarcasHhppRR(t.getHhpp(), marcasMgls);
            }
        }
        return marcasHhppMglDaoImpl.create(t);
    }

    public MarcasHhppMgl update(MarcasHhppMgl t) throws ApplicationException {
        if (    t.getHhpp().getHhpIdrR() != null &&
                !t.getHhpp().getHhpIdrR().isEmpty() ) {
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (    parametroHabilitarRR != null && 
                    parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO) ){
                DireccionRRManager drrm = new DireccionRRManager(true);
                //se busca de nuevo el hhpp para tener todas las marcas actualizadas al instante  
                MarcasHhppMgl actualiza = marcasHhppMglDaoImpl.update(t);//se pone inhabilitado
                List<MarcasMgl> marcasMgls = marcasMglManager.findMarcasMglByHhpp(t.getHhpp());
                drrm.editarMarcasHhppRR(t.getHhpp(), marcasMgls);
                return actualiza;
            }
        }
        return marcasHhppMglDaoImpl.update(t);
    }

    public boolean delete(MarcasHhppMgl t) throws ApplicationException {
        return marcasHhppMglDaoImpl.delete(t);
    }

    public MarcasHhppMgl findById(MarcasHhppMgl sqlData) throws ApplicationException {
        return marcasHhppMglDaoImpl.find(sqlData.getMhhId());
    }

    public boolean asignarMarcaHhppMgl(HhppMgl hhppMgl, MarcasHhppMgl marcasHhppMgl, String blackLis, String usuario) throws ApplicationException {

        MarcasMglDaoImpl marcasMglDaoImpl = new MarcasMglDaoImpl();

        MarcasMgl blackLisId;
        if (marcasHhppMgl.getMarId() == null) {
            blackLisId = marcasMglDaoImpl.findMarcaHhppMglByCode(blackLis).get(0);
        } else {
            blackLisId = marcasHhppMgl.getMarId();
        }

        List<MarcasHhppMgl> result;
        MarcasHhppMglDaoImpl marcasHhppMglDaoImpl1 = new MarcasHhppMglDaoImpl();
        result = marcasHhppMglDaoImpl1.findByMarcaHhppMgl(hhppMgl, blackLisId);

        if (result.isEmpty()) {
            marcasHhppMgl.setMarId(blackLisId);
            marcasHhppMgl.setHhpp(hhppMgl);
            marcasHhppMgl.setMhhFechaCreacion(new Date());
            marcasHhppMgl.setEstadoRegistro(1);
            marcasHhppMgl.setMhhUsuarioCreacion(usuario);
            marcasHhppMgl.setPerfilCreacion(0);
            marcasHhppMglDaoImpl.create(marcasHhppMgl);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * valbuenayf Metodo para buscar las marcar de un hhpp
     * @param idHhpp
     * @return
     * @throws ApplicationException 
     */
    public List<MarcasHhppMgl> findMarcasHhppMglidHhpp(BigDecimal idHhpp) throws ApplicationException {
        List<MarcasHhppMgl> listResult;
        try {
            listResult = marcasHhppMglDaoImpl.findMarcasHhppMglidHhpp(idHhpp);
        } catch (ApplicationException e) {
            LOGGER.error("Error en findMarcasHhppMglidHhpp. " +e.getMessage(), e);      
            return null;
        }
        return listResult;
    }
    /**
     * espinosadiea Metodo para buscar las marcar de un hhpp
     * @param idHhpp
     * @return
     * @throws ApplicationException 
     */
    public List<MarcasHhppMgl> findMarcasHhppMglidHhppSinEstado(List<HhppMgl> idHhpp) throws ApplicationException {
        List<MarcasHhppMgl> listResult;
        listResult = marcasHhppMglDaoImpl.findMarcasHhppMglidHhppSinEstado(idHhpp);
        return listResult;
    }
    
    public MarcasMgl findMarcasMglByCodigo(String marca) throws ApplicationException{
        MarcasMglDaoImpl marcasMglDaoImpl = new MarcasMglDaoImpl();
     return marcasMglDaoImpl.findMarcasMglByCodigo(marca);
    }
    
    
    public MarcasHhppMgl finfById(BigDecimal id) throws ApplicationException {
        return marcasHhppMglDaoImpl.find(id);
    }

    public List<MarcasHhppMgl> buscarXHhppMarca(BigDecimal homePass, BigDecimal marca) throws ApplicationException{
        return marcasHhppMglDaoImpl.buscarXHhppMarca(homePass, marca);
    }
    
            /**
     * Se obtiene la lista según el filtro
     * @param filtroMarcasDto
     * @return 
     */
    public List<MarcasHhppMgl> findResultadosFiltro(FiltroMarcasMglDto filtroMarcasDto) {
        return marcasHhppMglDaoImpl.findResultadosFiltro(filtroMarcasDto);
    }
}
