
package co.com.telmex.catastro.util;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.telmex.catastro.services.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class EliminarHhppDiezMasivoThreads extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(EliminarHhppDiezMasivoThreads.class);
    private static List<HhppMgl> hhppList;

    EliminarHhppDiezMasivoThreads(List<HhppMgl> ehhppList) {
        hhppList = ehhppList;
    }

    EliminarHhppDiezMasivoThreads(String tipo, String estado) {
        EliminarHHPPMasivoRR eHhppRr = new EliminarHHPPMasivoRR(tipo, estado);
    }

    EliminarHhppDiezMasivoThreads(String str) {
        super(str);
    }

    @Override
    public void run() {
        co.com.claro.mgl.mbeans.direcciones.EliminarHHPPMasivoBean.finHilo = false;
        int count = 0;
        List<HhppMgl> hhppList10 = new ArrayList<HhppMgl>();
        
        int diezes = hhppList.size() / 10;
        
        int mod = hhppList.size() % 10;
        
        int vueltas = 0;
        for (HhppMgl hp : hhppList) {

            count++;
            
            hhppList10.add(hp);
            if (mod != 0 && diezes != 0 && diezes == vueltas && count == mod ) {
                eHhppDiezMasivoThreads(hhppList10);
                hhppList10.clear();
            }
            if ((hhppList.size() < 10 && hhppList.size() == hhppList10.size()) || count == 10) {
                count = 0;
                eHhppDiezMasivoThreads(hhppList10);
                hhppList10.clear();
                vueltas++;
            }


        }
        co.com.claro.mgl.mbeans.direcciones.EliminarHHPPMasivoBean.finHilo = true;

        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametrosMgl = new ParametrosMgl();

        try {
            List<ParametrosMgl> parametrosMglList;
            parametrosMglList = parametrosMglManager.findByAcronimo(Constant.FLAG_PROCESHILO_ELIMINARMASIVOHHPP);
            parametrosMgl = parametrosMglList.get(0);
            parametrosMgl.setParValor("I");
            parametrosMglManager.update(parametrosMgl);
        } catch (Exception ex) {
            LOGGER.error("Error en el hilo de ejecución de eliminación masiva de HHPP. " + ex.getMessage(), ex);
        }
        
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            LOGGER.error("Error en run. " +e.getMessage(), e); 
        }
        co.com.claro.mgl.mbeans.direcciones.EliminarHHPPMasivoBean.finHilo = false;
        co.com.claro.mgl.mbeans.direcciones.EliminarHHPPMasivoBean.count = 0;
    }

    public void eHhppDiezMasivoThreads(List<HhppMgl> hhppList10) {

        EliminarHHPPMasivoRR eHhppRr = new EliminarHHPPMasivoRR(hhppList10);
        eHhppRr.eHHPPMasivoRR();


    }

    public void hhppRR() {
        new EliminarHhppDiezMasivoThreads("").start();
    }
}
