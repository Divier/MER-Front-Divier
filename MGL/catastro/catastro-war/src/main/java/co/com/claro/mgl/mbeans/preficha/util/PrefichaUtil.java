package co.com.claro.mgl.mbeans.preficha.util;

import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase para validaciones generales de prefichas
 * 
 * @author Divier Casas
 */
public class PrefichaUtil {
    
    private static final String MSG_ID_1 = "Campo AMP no puede ser nulo";
    private static final String MSG_ID_2 = "El nap enviado no coincide con la troncal registrada";
    private static final String MSG_ID_3 = "El campo AMP no debe contener información para la tecnología seleccionada";
    
    private static final String OBS = "obs";
    private static final String IS_VALID = "isValid";
    private static final String N_CASA = "N_Casa";
    
    public static Map<String, Object> validateTypeOfTechnology(PreFichaTxtMgl preFichaTxtMgl, List<Object> lsParameters, List<String> lsFilterValues) {
        
        NodoMgl nodoSelected = (NodoMgl) lsParameters.get(0);   
        List<CmtBasicaMgl> lsTechnology = (List<CmtBasicaMgl>) lsParameters.get(1);
        String isValidPreficha = (String) lsParameters.get(2);
        String strTechnology = lsFilterValues.get(0);
        String nodoStr = lsFilterValues.get(1);
        Map<String, Object> mapa = new HashMap();
        mapa.put(OBS, new String());
        mapa.put(IS_VALID, isValidPreficha);
        boolean validationControl = false;
        for (CmtBasicaMgl technology : lsTechnology) {
            if(technology.getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH) && technology.getBasicaId().toString().equals(strTechnology) && preFichaTxtMgl.getBlockName().equalsIgnoreCase(N_CASA)) {
                if(preFichaTxtMgl.getAmp() == null || preFichaTxtMgl.getAmp().trim().isEmpty()) {
                    mapa.put(OBS, MSG_ID_1);
                    mapa.put(IS_VALID, false);
                    validationControl = true;
                    break;
                }
            } 
            if(!technology.getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH) && technology.getBasicaId().toString().equals(strTechnology) && preFichaTxtMgl.getBlockName().equalsIgnoreCase(N_CASA)) {
                if(preFichaTxtMgl.getAmp() != null && !preFichaTxtMgl.getAmp().trim().isEmpty()) {
                    mapa.put(OBS, MSG_ID_3);
                    mapa.put(IS_VALID, false);
                    validationControl = true;
                    break;
                } else {
                    validationControl = true;
                    break;
                }
            }
        }
        if(!validationControl) {
            String amp = preFichaTxtMgl.getAmp();
            if(nodoSelected.getNodId().toString().equals(nodoStr) && !nodoSelected.getNodCodigo().equals(amp.substring(0, (amp.length() - 2 < 0) ? 0 : amp.length() - 2))) {
                mapa.put(OBS, MSG_ID_2);
                mapa.put(IS_VALID, false);
            }
        }
        return mapa;
    }

    public static String getObs() {
        return OBS;
    }

    public static String getIsValid() {
        return IS_VALID;
    }
}