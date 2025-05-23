
package co.com.claro.mgl.mbeans.vt.migracion.login;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gabriel.santos
 */
public class LoginFilter implements Filter {

    private String mode;
  
    
    
    public LoginFilter() {
    }

    /**
     *
     * @param request Envia la solicitud para abrir siguiente pagina
     * @param response Captura la respuesta, me muestra cada vez la carga de la
     * pantalla de cada direccion
     * @param chain
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpSession session = ((HttpServletRequest) request).getSession();
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            if (req.getRequestURI().contains("/catastro-warIns/view/MGL/login")
                    || req.getRequestURI().contains("/view/MGL/template/login")
                    || req.getRequestURI().contains("/view/MGL/loginFactibilidades")
                    || req.getRequestURI().contains("/view/coberturas/consultaCoberturas")
                    || ((HttpServletRequest) request).getRequestURI().contains("/view/coberturas/consultaCoberturasV1Externo")
                    || ((HttpServletRequest) request).getRequestURI().contains("/view/coberturas/consultaCoberturasV1")
                    || ((HttpServletRequest) request).getRequestURI().contains("/view/MGL/loginVisorfactibilidad")
                    || req.getRequestURI().contains("/view/MGL/CM/respuestaPostFactibilidades")) {

                if (req.getRequestURI().contains("/view/MGL/loginFactibilidades")
                        || req.getRequestURI().contains("/view/coberturas/consultaCoberturas")
                        || ((HttpServletRequest) request).getRequestURI().contains("/view/coberturas/consultaCoberturasV1Externo")
                        || ((HttpServletRequest) request).getRequestURI().contains("/view/coberturas/consultaCoberturasV1")
                        || ((HttpServletRequest) request).getRequestURI().contains("/view/MGL/loginVisorfactibilidad")
                        || req.getRequestURI().contains("/view/MGL/CM/respuestaPostFactibilidades")) {

                } else {
                    res.addHeader("X-Frame-Options", "SAMEORIGIN");
                }
                res.addHeader("X-XSS-Protection", "1; mode=block");
                chain.doFilter(req, res);
                return;
            } else if (session.getAttribute("cookie") != null || session.getAttribute("cookieXml") != null) {
                res.addHeader("X-Frame-Options", "SAMEORIGIN");
                res.addHeader("X-XSS-Protection", "1; mode=block");
                chain.doFilter(req, res);
                return;
            } else {
                res.addHeader("X-Frame-Options", "SAMEORIGIN");
                res.addHeader("X-XSS-Protection", "1; mode=block");
                res.sendRedirect(req.getContextPath() + "/view/MGL/template/login.xhtml");
                return;
            }
        }
    }


    @Override
    public void init(FilterConfig fc) throws ServletException {
        String configMode = fc.getInitParameter("mode");
        if (configMode != null) {
            mode = configMode;
        }
    }

    @Override
    public void destroy() {
    }
    
    
    public String retornaParteUrl(HttpServletRequest req) {

        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath);
        
        return url.toString();
    }
}
