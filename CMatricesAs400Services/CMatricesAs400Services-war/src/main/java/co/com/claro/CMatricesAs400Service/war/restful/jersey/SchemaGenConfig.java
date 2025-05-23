/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful.jersey;

import com.sun.jersey.api.wadl.config.WadlGeneratorConfig;
import com.sun.jersey.api.wadl.config.WadlGeneratorDescription;
import com.sun.jersey.server.wadl.generators.WadlGeneratorJAXBGrammarGenerator;
import java.util.List;

public class SchemaGenConfig extends WadlGeneratorConfig {

    @Override
    public List<WadlGeneratorDescription> configure() {
        return generator(  WadlGeneratorJAXBGrammarGenerator.class   ).descriptions();
    }
}