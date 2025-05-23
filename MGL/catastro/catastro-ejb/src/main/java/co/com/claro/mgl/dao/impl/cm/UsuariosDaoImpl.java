/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mer.dtos.request.procedure.SetUserRequestDto;
import co.com.claro.mer.dtos.response.procedure.SetUserResponseDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mer.utils.enums.SchemesMerEnum;
import co.com.claro.mgl.dao.impl.MglGestionGenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.UsArea;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

import static co.com.claro.mer.constants.StoredProcedureNamesConstants.MG_CONSULTA_USUARIOS;
import static co.com.claro.mer.utils.constants.SessionConstants.PORTAL_LOGIN;

/**
 *
 * @author User
 */
public class UsuariosDaoImpl extends MglGestionGenericDaoImpl<Usuarios> {
    
    private static final Logger LOGGER = LogManager.getLogger(UsuariosDaoImpl.class);

    public List<Usuarios> findAll() throws ApplicationException {
        List<Usuarios> resultList;
        if (entityManager != null) {
            Query query = entityManager.createNamedQuery("Usuarios.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<Usuarios>) query.getResultList();
            return resultList;
        } else {
            throw new ApplicationException("La conexión a GESTIONNEW no se encuentra disponible al momento de consultar los usuarios.");
        }
    }

    public Usuarios findUsuarioById(BigDecimal idUsuario) throws ApplicationException {
        Usuarios result = null;
        try {
            if (entityManager != null) {
                Query query = entityManager.createQuery("SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario ");
                query.setParameter("idUsuario", idUsuario);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                result = (Usuarios) query.getSingleResult();
            } else {
                throw new ApplicationException("La conexión a GESTIONNEW no se encuentra disponible al momento de consultar el usuario.");
            }

        } catch (NoResultException nre) {
            String msg = "No se encontraron resultados al momento de consultar el usuario '" 
                    + idUsuario + "' en el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + nre.getMessage();
            LOGGER.error(msg);
            result=null;
        } catch (NonUniqueResultException ex) {
            String msg = "Se encontró más de un resultado al momento de consultar el usuario '" 
                    + idUsuario + "' en el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al buscar usuario: " + e.getMessage(), e);
        }
        return result;
    }

    public Usuarios findUsuarioByUsuario(String usuario) throws ApplicationException {
        Usuarios resultUsuario;
        try {
            if (entityManager != null) {
                Query query = entityManager.createNamedQuery("Usuarios.findUsuarioByUsuario");
                query.setParameter("usuario", usuario);
                resultUsuario = (Usuarios) query.getSingleResult();
            } else {
                throw new ApplicationException("La conexión a GESTIONNEW no se encuentra disponible al momento de consultar el usuario.");
            }
        } catch (NoResultException ne) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ne.getMessage();
            LOGGER.error(msg);
            // retorna informacion del usuario en blanco, ya que no hubo resultado.
            resultUsuario = null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al buscar usuario: " + e.getMessage(), e);
        }

        return resultUsuario;
    }
    
    public String findAreaUsuarioById(BigDecimal idUsuario) throws ApplicationException {
        String result;
        try {
            if (entityManager != null) {
                String query = " SELECT AREA FROM "+Constant.GESTION_DATABASE_SCHEMA+"."+ "US_AREA "
                        + " WHERE IDAREA = "
                        + "("
                        + " SELECT IDAREA FROM "+Constant.GESTION_DATABASE_SCHEMA+"."+ "US_PERFILES "
                        + " WHERE ID_PERFIL = (SELECT ID_PERFIL FROM  (SELECT * FROM "+Constant.GESTION_DATABASE_SCHEMA+"."+ "USUARIOS WHERE ID_USUARIO =  ?  ) ) ) ";

                Query q = entityManager.createNativeQuery(query);
                q.setParameter(1, idUsuario);

                result = (String) q.getSingleResult();
            } else {
                throw new ApplicationException("La conexión a GESTIONNEW no se encuentra disponible al momento de consultar el usuario.");
            }
        } catch (NoResultException ne) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ne.getMessage();
            LOGGER.error(msg);
            // retorna informacion del usuario en blanco, ya que no hubo resultado.
            result = null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al buscar usuario: " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * <p>Consulta el usuario de la sesión actual consumiendo el procedimiento almacenado <b>MG_USUARIOS_PKG.SET_USER_SP</b></p>
     * @param usuario usuario a buscar
     * @return Usuario consultado
     * @throws ApplicationException Error al consultar el usuario
     * @see co.com.claro.mer.dtos.request.procedure.SetUserRequestDto Para el request del procedimiento almacenado
     * @see co.com.claro.mer.dtos.response.procedure.SetUserResponseDto Para el response del procedimiento almacenado
     * @see co.com.claro.mer.utils.StoredProcedureUtil Para la ejecución del procedimiento almacenado
     * @see co.com.claro.mer.utils.enums.SchemesMerEnum Para el esquema de la base de datos
     * @see co.com.claro.mer.constants.StoredProcedureNamesConstants Para el nombre del procedimiento almacenado
     * @autor Manuel Hernández Rivas
     */
    public Usuarios findUsuarioSesionSP(String usuario) throws ApplicationException {

        String flagLogin = "US";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            flagLogin = request.getSession().getAttribute(PORTAL_LOGIN).toString();
        }

        StoredProcedureUtil spUtil = new StoredProcedureUtil(MG_CONSULTA_USUARIOS);

        spUtil.addRequestData(new SetUserRequestDto(usuario, flagLogin));
        SetUserResponseDto responseDto = spUtil.executeStoredProcedure(SetUserResponseDto.class, SchemesMerEnum.GESTIONNEW);
        if (responseDto.getCodigo() == 1) {
            return datosUsuario(responseDto);
        }

        String msgError = "Error en el procedimiento almacenado " + MG_CONSULTA_USUARIOS
                +": " + responseDto.getDescripcion();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);
    }

    /**
     * <p> Asigna los datos del usuario consultado en el procedimiento almacenado <b>MG_USUARIOS_PKG.SET_USER_SP</b> a un objeto de tipo <b>Usuarios</b></p>
     * @param responseDto Respuesta del procedimiento almacenado
     * @return Usuario consultado
     * @autor Manuel Hernández Rivas
     */
    private Usuarios datosUsuario(SetUserResponseDto responseDto) {
        if (responseDto.getCodigo() == 1){
            UsArea area = new UsArea();

            area.setAreaNombre(responseDto.getArea());
            area.setIdArea(responseDto.getIdArea());

            UsPerfil perfil = new UsPerfil();

            perfil.setIdPerfil(responseDto.getIdPerfil());
            perfil.setCodPerfil(responseDto.getCodPerfil());
            perfil.setDescripcion(responseDto.getDescPerfil());
            perfil.setArea(area);

            Usuarios usuarios = new Usuarios();

            usuarios.setIdUsuario(responseDto.getIdUsuarioPortal());
            usuarios.setNombre(responseDto.getNombre());
            usuarios.setTelefono(responseDto.getTelefono());
            usuarios.setEmail(responseDto.getEmail());
            usuarios.setDireccion(responseDto.getDescripcion());
            usuarios.setUsuario(responseDto.getUsuario());
            usuarios.setPerfil(perfil);

            return usuarios;
        }
        return null;
    }

}