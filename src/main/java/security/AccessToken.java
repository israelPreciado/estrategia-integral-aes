
package security;

import java.sql.Timestamp;

/**
 *
 * @author Israel Preciado
 */
public class AccessToken {
    private final Timestamp timestamp;
    
    public AccessToken() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        long duration = 4 * 60 * 60; // by default the session lives 4 hours
        this.timestamp.setTime(this.timestamp.getTime() + duration);
    }
    
    public AccessToken(int hours) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        long duration = hours * 60 * 60;
        this.timestamp.setTime(this.timestamp.getTime() + duration);
    }
    
    public String generate() {
        // insert token and expiration date
        
        return "";
    }
}
