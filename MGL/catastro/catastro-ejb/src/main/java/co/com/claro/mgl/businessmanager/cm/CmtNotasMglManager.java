/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtNotasMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroNotasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasDetalleMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtNotasMglManager {

    public List<CmtNotasMgl> findAll() throws ApplicationException {
        List<CmtNotasMgl> resulList;
        CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
        resulList = cmtNotasMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtNotasMgl> findTipoNotasId(BigDecimal tipoNotasId) throws ApplicationException {
        CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
        return cmtNotasMglDaoImpl.findTipoNotasId(tipoNotasId);
    }

    public List<CmtNotasMgl> findNotasBySubEdificioId(BigDecimal subEdificioId, BigDecimal tipoNota) throws ApplicationException {
        CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasBySubEdificioId(subEdificioId, tipoNota);
    }

    public CmtNotasMgl create(CmtNotasMgl cmtNotasMgl, String usuario, int perfil) throws ApplicationException {
        CmtCuentaMatrizRRMglManager rRMglManager = new CmtCuentaMatrizRRMglManager();
        boolean habilitarRR = false;       
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }
        
        //si esta activo RR ir a crear nota allá
        boolean isActualizadoRr = false;
        isActualizadoRr = (habilitarRR) ? rRMglManager.notasCuentaMatrizAdd(cmtNotasMgl, usuario) : true;
       
        if (isActualizadoRr) {
            CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
            return cmtNotasMglDaoImpl.createCm(cmtNotasMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No fue posible actualizar la nota en RR");
        }
    }

    public CmtNotasMgl update(CmtNotasMgl cmtNotasMgl) throws ApplicationException {
        CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
        return cmtNotasMglDaoImpl.update(cmtNotasMgl);
    }

    public boolean delete(CmtNotasMgl cmtNotasMgl) throws ApplicationException {
        CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
        return cmtNotasMglDaoImpl.delete(cmtNotasMgl);
    }

    public CmtNotasMgl findById(CmtNotasMgl cmtNotasMgl) throws ApplicationException {
        CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
        return cmtNotasMglDaoImpl.find(cmtNotasMgl.getNotasId());
    }

    public CmtNotasMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
        return cmtNotasMglDaoImpl.find(id);
    }

    public List<AuditoriaDto> construirAuditoria(CmtNotasMgl cmtNotasMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglManager = new CmtNotasDetalleMglManager();
        UtilsCMAuditoria<CmtNotasMgl, CmtNotasAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtNotasMgl, CmtNotasAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtNotasMgl);
        if (cmtNotasMgl.getComentarioList() != null && !cmtNotasMgl.getComentarioList().isEmpty()) {
            for (CmtNotasDetalleMgl cmtNotasDetalleMgl : cmtNotasMgl.getComentarioList()) {
                listAuditoriaDto.addAll(cmtNotasDetalleMglManager.construirAuditoria(cmtNotasDetalleMgl));
            }
        }
        return listAuditoriaDto;
    }

    public FiltroNotasDto getNotasSearch(HashMap<String, Object> params, BigDecimal subEdificio,boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtNotasMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasMglDaoImpl();
        FiltroNotasDto filtroConsulta;

        if ((params.get("notas_id") != null)|| (params.get("descripcion") != null )) {
            if ((params.get("id") != null)) {
                params.put("ubic_punto_inicial", params.get("ubic_punto_inicial"));
                params.put("tipoInfraestructura", params.get("tipoInfraestructura"));
                params.put("ubicacionCaja", params.get("ubicacionCaja"));
            }
        }
        filtroConsulta = cmtNotasMglDaoImpl.findNotasSearch(params, subEdificio, contar, firstResult, maxResults);
        return filtroConsulta;
    }

}
