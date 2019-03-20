/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import exceptions.SException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 *
 * @author Desarrollo6
 */
public class ConfigProperties {
    private String resourceName;
    private String value;
    private Properties props;
    private InputStream is;
    
    public ConfigProperties(String resourceName) {
        this.resourceName = resourceName;
        this.value = "";
        this.props = new Properties();        
    }
    
    public String getProperty(String key) throws SException {        
        try {
            is = ConfigProperties.class.getClassLoader().getResourceAsStream(resourceName);
            props.load(is);
            value = props.getProperty(key);
        } catch (IOException ex) {
            throw new SException(ex.getMessage());
        }
        
        return value;
    }
}
