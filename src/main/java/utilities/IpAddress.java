/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import interfaces.IipAddress;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Israel Preciado
 */
public class IpAddress implements IipAddress {
    private final HttpServletRequest request;
    
    public IpAddress(HttpServletRequest request){
        this.request = request;
    }
    
    @Override
    public String getIpAddress(){
        String ip = request.getHeader("x-forwarded-for");
        
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("X_FORWARDED_FOR");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        
        return ip;
    }
}
