package co.com.telmex.catastro.services.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

/**
 * Clase LdapAuthentication
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
public class LdapAuthentication {

    private static final Logger LOGGER = LogManager.getLogger(LdapAuthentication.class);

    /**
     *
     */
    public LdapAuthentication() {
    }

    /**
     * Realiza la validación del usuario autenticado en el LDAP
     *
     * @param login
     * @param password
     * @return TRUE:si el usuario se encuentra en el LDAP, FALSE en caso
     * contrario
     */
    public boolean validarLdap(String login, String password) {
        boolean valido = false;
        Hashtable<String, String> env = new Hashtable<String, String>();
        //Parámetros de conexion al LDAP
        env.put(Context.INITIAL_CONTEXT_FACTORY, ConstantLdap.CONTEXT);
        env.put(Context.SECURITY_AUTHENTICATION, ConstantLdap.TIPO_AUTH);
        env.put(Context.SECURITY_PRINCIPAL, ConstantLdap.USERNAME);
        env.put(Context.SECURITY_CREDENTIALS, ConstantLdap.PWD);
        env.put(Context.PROVIDER_URL, ConstantLdap.URL);
        try {
            //CONEXION CON EL LDAP
            DirContext ctx = new InitialLdapContext(env, null);
            SearchControls searchCtls = new SearchControls();
            //ITEMS A DEVOLVER POR EL LDAP
            String returnedAtts[] = {"cn", "sn", "givenName", "mail", "sAMAccountName"};
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(&(objectCategory=person)(objectClass=user)(sAMAccountName=" + login.toLowerCase() + "))";
            String searchBase = "DC=co,DC=attla,DC=corp";
            //Se consultan los resultados
            NamingEnumeration<SearchResult> results = ctx.search(searchBase, searchFilter, searchCtls);
            while (results.hasMoreElements()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attrs = searchResult.getAttributes();
                //OPTENEMOS LA UNIDAD ORGANIZATIVA DEL UID BUSCADO CON SU UID Y LO COMPLETAMOS CON LA BASE
                String dn = searchResult.getName() + "," + searchBase;
                if (attrs != null) {
                    //El usuario existe, ahora se valida el password
                    valido = validarAuth(dn, password);
                }
            }
            ctx.close();
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de validar el LDAP. EX000: " + ex.getMessage(), ex);
        }
        return valido;
    }

    /**
     *
     * @param dn
     * @param password
     * @return
     */
    private boolean validarAuth(String dn, String password) {
        boolean valido = false;
        Hashtable<String, String> env1 = new Hashtable<String, String>();
        env1.put(Context.INITIAL_CONTEXT_FACTORY, ConstantLdap.CONTEXT);
        env1.put(Context.SECURITY_AUTHENTICATION, ConstantLdap.TIPO_AUTH);
        env1.put(Context.SECURITY_PRINCIPAL, dn);
        env1.put(Context.SECURITY_CREDENTIALS, password);
        env1.put(Context.PROVIDER_URL, ConstantLdap.URL);
        try {
            DirContext ctx1 = new InitialLdapContext(env1, null);
            valido = true;
            ctx1.close();
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de validar la autenticación. EX000: " + ex.getMessage(), ex);
        }
        return valido;
    }
}
