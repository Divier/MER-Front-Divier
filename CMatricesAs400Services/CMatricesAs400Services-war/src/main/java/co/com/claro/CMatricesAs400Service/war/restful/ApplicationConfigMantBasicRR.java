/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author jrodriguez
 */
@javax.ws.rs.ApplicationPath("basicRR")
public class ApplicationConfigMantBasicRR extends Application {
    
    private static final Logger LOGGER = LogManager.getLogger(ApplicationConfigMantBasicRR.class);

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        // following code can be used to customize Jersey 1.x JSON provider:
        try {
            Class jacksonProvider = Class.forName("org.codehaus.jackson.jaxrs.JacksonJsonProvider");
            resources.add(jacksonProvider);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Error en getClasses. " +e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en getClasses. " +e.getMessage(), e);
        }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * re-generated by NetBeans REST support to populate given list with all
     * resources defined in the project.
     */
    private void addRestResourceClasses(java.util.Set<java.lang.Class<?>> resources) {
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.BasicMaintenanceRRAllies.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.BasicMaintenanceRRMunicipalityCenterPopulatedDane.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.BasicMaintenanceRRNetworkTyping.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.BasicMaintenanceRRNodes.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.BasicMaintenanceRRPlants.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.BasicMaintenanceRRStateNodes.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.BasicMainteniceRRHomologations.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.CmtLocationsRest.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.HhppPagination.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.RestCuentasMatrices.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.RestOrdenTrabajo.class);
        resources.add(co.com.claro.CMatricesAs400Service.war.restful.RestTablasBasicas.class);
    }

}
