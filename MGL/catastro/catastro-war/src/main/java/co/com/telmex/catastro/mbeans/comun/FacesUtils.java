package co.com.telmex.catastro.mbeans.comun;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase FacesUtils
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class FacesUtils {

    /**
     * Get servlet context.
     * 
     * @return the servlet context
     */
    public static ServletContext getServletContext() {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }

    /**
     * Obtiene la session actual del usuario y en caso de no encontrar una
     * session retorna null
     * 
     * @return
     */
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    /**
     * obtiene los datos de la configuracion de la aplicacion 
     * @return
     */
    public static Application getCurrentApplication() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    /**
     * Get managed bean based on the bean name.
     * 
     * @param beanName
     *            the bean name
     * @return the managed bean associated with the bean name
     */
    public static Object getManagedBean(String beanName) {
        Object o = getValueBinding(getJsfEl(beanName)).getValue(
                FacesContext.getCurrentInstance());

        return o;
    }

    /**
     * Remove the managed bean based on the bean name.
     * 
     * @param beanName
     *            the bean name of the managed bean to be removed
     */
    public static void resetManagedBean(String beanName) {
        getValueBinding(getJsfEl(beanName)).setValue(
                FacesContext.getCurrentInstance(), null);
    }

    /**
     * Store the managed bean inside the session scope.
     * 
     * @param beanName
     *            the name of the managed bean to be stored
     * @param managedBean
     *            the managed bean to be stored
     */
    public static void setManagedBeanInSession(String beanName,
            Object managedBean) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, managedBean);
    }

    /**
     * Get parameter value from request scope.
     * 
     * @param name
     *            the name of the parameter
     * @return the parameter value
     */
    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    /**
     * 
     * @return 
     */
    private static Application getApplication() {
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
    }

    /**
     * 
     * @param el
     * @return 
     */
    private static ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);

    }

    /**
     * 
     * @param metodo
     * @param param
     * @return
     */
    public static MethodBinding getMethodBinding(String metodo, Class[] param) {
        return getApplication().createMethodBinding(getJsfEl(metodo), param);
    }

    /**
     * 
     * @return
     */
    public static HttpServletRequest getServletRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    /**
     * 
     * @param el
     * @return 
     */
    private static Object getElValue(String el) {
        return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
    }

    /**
     * 
     * @param value
     * @return 
     */
    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }

    /**
     * 
     * @return
     */
    public static HttpServletResponse getServletResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    /**
     * 
     */
    public static void actualizarPresentacion() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.renderResponse();
    }
}
