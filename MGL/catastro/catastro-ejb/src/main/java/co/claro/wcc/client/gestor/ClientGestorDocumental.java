/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.claro.wcc.client.gestor;

import co.claro.wcc.schema.schemaoperations.ResponseType;
import co.claro.wcc.services.search.ordenesservicio.SearchOrdenesServicioFault;
import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.ordenesservicio.UploadOrdenesServicioFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import java.net.URL;

/**
 *
 * @author bocanegravm
 */
public class ClientGestorDocumental {

    private final String user;
    private final String password;

    public ClientGestorDocumental(String user, String password) {

        this.user = user;
        this.password = password;
    }

    public ResponseType insert(co.claro.wcc.schema.schemaoperations.FileRequestType requestMessage, URL url) throws UploadCuentasMatricesFault {

        java.net.Authenticator myAuth = new java.net.Authenticator() {
            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication(user, password.toCharArray());
            }
        };
        java.net.Authenticator.setDefault(myAuth);
        co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatrices_Service service = new co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatrices_Service(url);
        co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatrices port = service.getUploadCuentasMatricesPort();
        return port.insert(requestMessage);
    }

    public ResponseType insertOrdenes(co.claro.wcc.schema.schemaoperations.FileRequestType requestMessage, URL url) throws UploadOrdenesServicioFault {

        java.net.Authenticator myAuth = new java.net.Authenticator() {
            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication(user, password.toCharArray());
            }
        };
        java.net.Authenticator.setDefault(myAuth);
        co.claro.wcc.services.upload.ordenesservicio.UploadOrdenesServicio_Service service = new co.claro.wcc.services.upload.ordenesservicio.UploadOrdenesServicio_Service(url);
        co.claro.wcc.services.upload.ordenesservicio.UploadOrdenesServicio port = service.getUploadOrdenesServicioPort();
        return port.insert(requestMessage);
    }

    public ResponseType find(co.claro.wcc.schema.schemaoperations.RequestType requestMessage, URL url) throws SearchCuentasMatricesFault {
        java.net.Authenticator myAuth = new java.net.Authenticator() {
            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication(user, password.toCharArray());
            }
        };
        java.net.Authenticator.setDefault(myAuth);
        co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatrices_Service service = new co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatrices_Service(url);
        co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatrices port = service.getSearchCuentasMatricesPort();
        return port.find(requestMessage);
    }

    public  ResponseType findOrdenes(co.claro.wcc.schema.schemaoperations.RequestType requestMessage, URL url) throws SearchOrdenesServicioFault {

        java.net.Authenticator myAuth = new java.net.Authenticator() {
            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication(user, password.toCharArray());
            }
        };
        java.net.Authenticator.setDefault(myAuth);
        co.claro.wcc.services.search.ordenesservicio.SearchOrdenesServicio_Service service = new co.claro.wcc.services.search.ordenesservicio.SearchOrdenesServicio_Service(url);
        co.claro.wcc.services.search.ordenesservicio.SearchOrdenesServicio port = service.getSearchOrdenesServicioPort();
        return port.find(requestMessage);
    }

}
