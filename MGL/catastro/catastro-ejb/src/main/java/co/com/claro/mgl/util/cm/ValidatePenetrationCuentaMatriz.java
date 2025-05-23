package co.com.claro.mgl.util.cm;

import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSubEdificioMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTecnologiaSubMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.dtos.CmtPenetracionCMDto;
import co.com.claro.mgl.dtos.CmtPestanaPenetracionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.Constant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Permite realizar validaciones sobre penetración de coberturas de CCMM
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/04/11
 */
public class ValidatePenetrationCuentaMatriz {

    /**
     * Obtiene la lista de tecnologías de penetración de la CCMM
     *
     * @param ccmmSeleccionada Cuenta matriz a la que para obtener la penetración de tecnologías
     * @return {@link List<String>} Retorna la lista de nombres de tecnologías de la penetración de la CCMM
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    public List<String> findTecnologiasPenetracionCuentaMatriz(CmtCuentaMatrizMgl ccmmSeleccionada)
            throws ApplicationException {

        CmtPestanaPenetracionDto cmtPestanaPenetracionDto;
        List<CmtPenetracionCMDto> listaPenetracionTecnologias;
        HashMap<String, Object> params = new HashMap<>();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        List<CmtSubEdificioMgl> edificiosCcmmList = cmtSubEdificioMglManager.findSubEdificioByCuentaMatriz(ccmmSeleccionada);
        CmtSubEdificioMgl cmtsubEdificioMglaux;
        List<String> tecnologiasPenetracion = new ArrayList<>();

        if (Objects.nonNull(edificiosCcmmList) && !edificiosCcmmList.isEmpty()) {
            cmtsubEdificioMglaux = edificiosCcmmList.get(0);
            CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
            cmtPestanaPenetracionDto = cmtTecnologiaSubMglManager
                    .findListTecnologiasPenetracionSubCM(params, 1, 15,
                            cmtsubEdificioMglaux, Constant.FIND_HHPP_BY.CUENTA_MATRIZ);
            listaPenetracionTecnologias = cmtPestanaPenetracionDto.getListaPenetracionTecnologias();

            //Comprueba las tecnologias de penetración que tiene la CCMM
            tecnologiasPenetracion = obtenerTecnologiasPenetracion(listaPenetracionTecnologias);

        }
        return tecnologiasPenetracion;
    }

    /**
     * Obtiene el listado de nombres de tecnologías correspondientes
     * a la lista de penetración de CCMM recibida
     *
     * @param listaPenetracionTecnologias lista de penetración de tecnologías
     * @return {@link List<String>} Lista de tecnologías que corresponden a la penetración de la CCMM
     */
    public List<String> obtenerTecnologiasPenetracion(List<CmtPenetracionCMDto> listaPenetracionTecnologias)
            throws ApplicationException {

        if (Objects.isNull(listaPenetracionTecnologias) ||listaPenetracionTecnologias.isEmpty()) return Collections.emptyList();

        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        List<CmtBasicaMgl> tecnologiaList = cmtBasicaMglManager.findByTipoBasica(
                cmtTipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA));

        if (Objects.isNull(tecnologiaList) ||tecnologiaList.isEmpty()) return Collections.emptyList();

        return listaPenetracionTecnologias.stream()
                .map(CmtPenetracionCMDto::getTecnologia)//mapea las tecnologías de la penetración.
                .map(String::trim)// Elimina espacios en blanco al inicio y final de la cadena.
                // Se recorre la lista de tecnologías y comprueba si el nombre básica es igual al nombre de la penetración.
                .flatMap(penetracionTecnologia -> tecnologiaList.stream()
                        .filter(tecnologia -> tecnologia.getNombreBasica().trim().equalsIgnoreCase(penetracionTecnologia))
                        .map(CmtBasicaMgl::getCodigoBasica))
                .collect(Collectors.toList());
    }

}
