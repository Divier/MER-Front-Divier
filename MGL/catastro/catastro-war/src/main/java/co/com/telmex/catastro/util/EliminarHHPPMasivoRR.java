
package co.com.telmex.catastro.util;

import co.com.claro.mgl.businessmanager.address.DirEliminaMasivaDetalMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DirEliminaMasivaDetalMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DirEliminaMasivaDetalMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
public class EliminarHHPPMasivoRR {
    
    private static final Logger LOGGER = LogManager.getLogger(EliminarHHPPMasivoRR.class);


    public static List<HhppMgl> hhppList;
    public static HhppMgl hhpp;
    List<HhppMgl> hhppListDelRR;
    List<HhppMgl> hhppListNoDelRR;
    public static String tipohhpp;
    public static String estadohhpp;
    @EJB
    private HhppMglFacadeLocal hhppMglFacade;
    @EJB
    private DirEliminaMasivaDetalMglFacadeLocal dirEliminaMasivaDetalMglFacade;

    public EliminarHHPPMasivoRR(List<HhppMgl> hhppListS) {
        hhppList = new ArrayList<HhppMgl>();
        setHhppList(hhppListS);
    }

    public EliminarHHPPMasivoRR(String tipo, String estado) {
        tipohhpp = tipo;
        estadohhpp = estado;
    }

    public void eHHPPMasivoRR() {
        

        DireccionRRManager manager = new DireccionRRManager(true);

        String strMessage = null;
        boolean isDele = false;
        hhppListDelRR = new ArrayList<HhppMgl>();
        hhppListNoDelRR = new ArrayList<HhppMgl>();
        
        for (HhppMgl hp : hhppList) {
            String comunidad = hp.getHhpComunidad();

            String division = hp.getHhpDivision();

            String apart = hp.getHhpApart();

            String calle = hp.getHhpCalle();

            String placa = hp.getHhpPlaca();

            try {
                strMessage = manager.getInfoRegularHHPP(comunidad, division, apart, calle, placa);
            } catch (Exception e) {
                LOGGER.error("Error en eHHPPMasivoRR. " +e.getMessage(), e); 
            }

            String[] lines = strMessage.split("\\n");
            if (!strMessage.contains("STAT|")) {
                hp.setResultado("No eliminado");
                hp.setDescripcion(strMessage);
                hhppListNoDelRR.add(hp);
                registrarHhppMgl(hp, "No eliminado", strMessage);

            }

            String tipoh = null;
            if (strMessage.contains("UTYP|")) {
                String[] UTYP = strMessage.split("UTYP");
                tipoh = UTYP[1].substring(1, 2);

            }

            for (String param : lines) {
                if (tipohhpp != null) {
                    if (tipoh != null && !tipoh.equals(tipohhpp)) {
                        break;
                    }
                }
                if (estadohhpp != null && param.contains("STAT|")) {
                    String[] ustat = param.trim().split("\\|");

                    if (!(ustat[1].trim()).equals(estadohhpp)) {
                        break;
                    }
                }
                if (param.contains("STAT|")) {

                    String[] stat = param.trim().split("\\|");

                    if (!(stat[1].trim()).equals("S")) {
                        try {
                            isDele = manager.borrarHHPPRR(comunidad, division, apart, calle, placa);
                        } catch (Exception e) {
                            LOGGER.error("Error en EHHPPMasivoRR. " +e.getMessage(), e); 
                        }
                        if (isDele) {
                            hhpp = hp;
                            String resp = eliminarHhppMgl(hhpp);
                            hp.setResultado("HHPP Si eliminado en RR, " + resp);
                            hp.setDescripcion("OK");
                            hhppListDelRR.add(hp);
                            registrarHhppMgl(hp, "HHPP Si eliminado en RR, " + resp, "OK");

                        } else {
                            hp.setResultado("No eliminado");
                            hp.setDescripcion("Conexion RR");
                            hhppListNoDelRR.add(hp);
                            registrarHhppMgl(hp, "No eliminado", "No eliminado en RR");

                        }

                    } else {
                        hp.setResultado("No eliminado");
                        hp.setDescripcion("Estado HHPP en Servicio");
                        hhppListNoDelRR.add(hp);
                        registrarHhppMgl(hp, "No eliminado", "Estado HHPP en Servicio");

                    }
                    break;
                }
            }

        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error("Error en eHHPPMasivoRR. " +e.getMessage(), e); 
        }

        if (hhppListDelRR != null) {
        }

        if (hhppListNoDelRR != null) {
        }
        co.com.claro.mgl.mbeans.direcciones.EliminarHHPPMasivoBean.eliminarHHPPsMasivoRR(hhppListDelRR, hhppListNoDelRR);

    }

    public List<HhppMgl> getHhppList() {
        return hhppList;
    }

    public void setHhppList(List<HhppMgl> hhppList) {
        this.hhppList = hhppList;
    }

    public String eliminarHhppMgl(HhppMgl hhppMgl) {
        
        try {
            if (hhpp.getHhpId() != null) {
                hhppMglFacade.delete(hhpp);
                String resp = "HHPP Si Eliminado en Repositorio";
                return resp;
            } else {
                String resp = "HHPP No encontrado en Repositorio";
                return resp;
            }

        } catch (ApplicationException e) {
            String resp = "HHPP No Eliminado en Repositorio";
            LOGGER.error("Error en EliminarHHPPMasivoRR. " +e.getMessage(), e); 
            return resp;
        }

    }

    public void registrarHhppMgl(HhppMgl hhppMgl, String resultado, String descripcion) {

        DirEliminaMasivaDetalMgl dirEliminaMasivaDetalMgl = new DirEliminaMasivaDetalMgl();

        dirEliminaMasivaDetalMgl.setEmdID(BigDecimal.ZERO);
        dirEliminaMasivaDetalMgl.setLemID(co.com.claro.mgl.mbeans.direcciones.EliminarHHPPMasivoBean.logId);
        dirEliminaMasivaDetalMgl.setDirId(hhppMgl.getDirId());
        dirEliminaMasivaDetalMgl.setEhhId(hhppMgl.getEhhId().getEhhNombre());
        dirEliminaMasivaDetalMgl.setHhpApart(hhppMgl.getHhpApart());
        dirEliminaMasivaDetalMgl.setHhpCalle(hhppMgl.getHhpCalle());
        dirEliminaMasivaDetalMgl.setHhpComunidad(hhppMgl.getHhpComunidad());
        dirEliminaMasivaDetalMgl.setHhpDivision(hhppMgl.getHhpDivision());
        dirEliminaMasivaDetalMgl.setHhpFechaCreacion(hhppMgl.getFechaCreacion());
        dirEliminaMasivaDetalMgl.setHhpFechaModificacion(hhppMgl.getFechaEdicion());
        dirEliminaMasivaDetalMgl.setHhpId(hhppMgl.getHhpId());
        dirEliminaMasivaDetalMgl.setHhpIdrR(hhppMgl.getHhpIdrR());
        dirEliminaMasivaDetalMgl.setHhpPlaca(hhppMgl.getHhpPlaca());
        dirEliminaMasivaDetalMgl.setHhpUsuarioCreacion(hhppMgl.getUsuarioCreacion());
        dirEliminaMasivaDetalMgl.setHhpUsuarioModificacion(hhppMgl.getUsuarioEdicion());
        if (hhppMgl.getHhpId() != null) {
            dirEliminaMasivaDetalMgl.setNodId(hhppMgl.getNodId().getNodId());
        }
        dirEliminaMasivaDetalMgl.setSdiId(hhppMgl.getSdiId());
        dirEliminaMasivaDetalMgl.setThcId(hhppMgl.getThcId());
        dirEliminaMasivaDetalMgl.setThhId(hhppMgl.getThhId());
        dirEliminaMasivaDetalMgl.setThrId(hhppMgl.getThrId());
        dirEliminaMasivaDetalMgl.setEmdResultadoProceso(resultado);
        dirEliminaMasivaDetalMgl.setEmdDescripcionResultProc(descripcion);

        dirEliminaMasivaDetalMglFacade = new DirEliminaMasivaDetalMglFacadeLocal() {
            DirEliminaMasivaDetalMglManager dirEliminaMasivaDetalMglManager = new DirEliminaMasivaDetalMglManager();

            @Override
            public List<DirEliminaMasivaDetalMgl> findAll() throws ApplicationException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public DirEliminaMasivaDetalMgl create(DirEliminaMasivaDetalMgl dirEliminaMasivaDetalMgl) throws ApplicationException {
                return dirEliminaMasivaDetalMglManager.create(dirEliminaMasivaDetalMgl);
            }

            @Override
            public DirEliminaMasivaDetalMgl update(DirEliminaMasivaDetalMgl t) throws ApplicationException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean delete(DirEliminaMasivaDetalMgl t) throws ApplicationException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public DirEliminaMasivaDetalMgl findById(DirEliminaMasivaDetalMgl sqlData) throws ApplicationException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<DirEliminaMasivaDetalMgl> findByLemId(BigDecimal bigDecimal) throws ApplicationException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        try {
            dirEliminaMasivaDetalMglFacade.create(dirEliminaMasivaDetalMgl);
        } catch (ApplicationException e) {
            LOGGER.error("Error en registrarHhppMgl. " +e.getMessage(), e); 
        }

    }
}
