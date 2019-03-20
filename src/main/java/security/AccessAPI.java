/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

/**
 *
 * @author Israel Preciado
 */
public class AccessAPI implements IaccessAPI {
    private Boolean access;
    
    public AccessAPI() {
        this.access = true;
    }

    @Override
    public Boolean allowAccess(String ip) {
        SiOACL sioACL = new SiOACL();        
        
        if(sioACL.accessControlList().contains(ip)) {
            access = true;
        }
        
        return access;
    }
}
