/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.util;

import co.com.claro.mgl.jpa.entities.HhppMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class EliminarHhppMasivoManagedThreads {

    public static class EliminarHhppMasivoManagedThreadsInt {

        public static List<HhppMgl> hhppListDeleThr;
        public static List<HhppMgl> hhppListNoDeleThr;

        public EliminarHhppMasivoManagedThreadsInt(List<HhppMgl> hhppList, String tipoHhpp, String estadoHhpp) {

            new EliminarHhppDiezMasivoThreads( tipoHhpp, estadoHhpp);
            EliminarHhppDiezMasivoThreads eHhppDM = new EliminarHhppDiezMasivoThreads(hhppList);
            eHhppDM.hhppRR();

        }

        public static List<HhppMgl> getHhppListDele() {
            return hhppListDeleThr;
        }

        public static void setHhppListDele(List<HhppMgl> hhppListDel) {
            if (EliminarHhppMasivoManagedThreadsInt.hhppListDeleThr == null) {
                EliminarHhppMasivoManagedThreadsInt.hhppListDeleThr = hhppListDel;
            } else {
                EliminarHhppMasivoManagedThreadsInt.hhppListDeleThr.addAll(hhppListDel);
            }
        }

        public static List<HhppMgl> getHhppListNoDele() {
            return hhppListNoDeleThr;
        }

        public static void setHhppListNoDele(List<HhppMgl> hhppListNoDele) {
            if (EliminarHhppMasivoManagedThreadsInt.hhppListNoDeleThr == null) {
                EliminarHhppMasivoManagedThreadsInt.hhppListNoDeleThr = hhppListNoDele;
                
            } else {
                EliminarHhppMasivoManagedThreadsInt.hhppListNoDeleThr.addAll(hhppListNoDele);
                
            }
        }
    }
}
