/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.util;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class JSFUtil {

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static HttpSession getHttpSession() {
        return (HttpSession) getFacesContext().getExternalContext().getSession(true);
    }

    public static HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    public static Object getBean(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, name);
    }

    public static <T extends Object> T getSessionBean(Class<T> clase) {
        ManagedBean sessionBean = clase.getAnnotation(ManagedBean.class);
        String nombre = "#{" + sessionBean.name() + "}";
        FacesContext contx = FacesContext.getCurrentInstance();
        if (contx != null && contx.getApplication() != null
                && contx.getApplication().getExpressionFactory() != null
                && contx.getELContext() != null) {
            return (T) contx.getApplication().getExpressionFactory()
                    .createValueExpression(contx.getELContext(), nombre, clase)
                    .getValue(contx.getELContext());
        } else {
            return null;
        }
    }

    /**
     * Metodo utilizado para poner en el FacesContext un atributo - valor.
     *
     * @param name Nombre del Atributo.
     * @param value Valor de tipo Object.
     */
    public static void setBean(String name, Object value) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getApplication().getELResolver().setValue(facesContext.getELContext(), null, name, value);
    }

    public static <T extends Object> T getBean(String name, Class<T> beanClass) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return (T) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, name);
    }

    public <T> SingularAttribute<? super T, ?> getIdAttribute(EntityManager em,
            Class<T> clazz) {
        Metamodel m = em.getMetamodel();
        IdentifiableType<T> of = (IdentifiableType<T>) m.managedType(clazz);
        return of.getId(of.getIdType().getJavaType());
    }
}
