package co.com.claro.mer.estadoxflujo.dao;

import co.com.claro.mer.dtos.response.service.RolPortalResponseDto;
import co.com.claro.mer.utils.SesionUtil;
import co.com.claro.mgl.dao.impl.cm.CmtEstadoxFlujoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Dao para la gestión de los estados de flujo.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/05/30
 */
@Log4j2
@Stateless
public class EstadoFlujoDao implements IEstadoFlujoDao{

    /**
     * Obtiene los estados de flujo del usuario.
     *
     * @return Una lista de identificadores de estados de flujo asociados al usuario actual.
     * @throws ApplicationException Si ocurre un error al consultar los estados de flujo.
     * @author Gildardo Mora
     */
    @Override
    public List<BigDecimal> findEstadoFlujoUsuario() throws ApplicationException {
        try {
            CmtEstadoxFlujoMglDaoImpl dao = new CmtEstadoxFlujoMglDaoImpl();
            List<CmtEstadoxFlujoMgl> estadosFlujo = dao.findAllItemsActive();

            if (CollectionUtils.isEmpty(estadosFlujo)) {
                LOGGER.warn("No hay estados de flujo disponibles para el usuario.");
                return Collections.emptyList();
            }

            List<RolPortalResponseDto> rolesUsuarioSesion = SesionUtil.getRoles();

            if (CollectionUtils.isEmpty(rolesUsuarioSesion)) {
                LOGGER.warn("No se encontraron roles de usuario en la sesión.");
                return Collections.emptyList();
            }

            Set<String> rolesUsuario = rolesUsuarioSesion.stream()
                    .map(RolPortalResponseDto::getCodRol)
                    .collect(Collectors.toSet());

            return estadosFlujo.stream()
                    .filter(e -> e.getGestionRol() != null && rolesUsuario.contains(e.getGestionRol()))
                    .map(CmtEstadoxFlujoMgl::getEstadoxFlujoId)
                    .collect(Collectors.toList());
        } catch (ApplicationException e) {
            String msgError = "Error al consultar los estados de flujo del usuario: " + e.getMessage();
            LOGGER.error(msgError, e);
            throw e;
        } catch (Exception e) {
            String msgError = "Error de IO al consultar los estados de flujo del usuario: " + e.getMessage();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError);
        }
    }

}
