/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cayetano
 */
public class Session {
    static final Map<String,Object> SESSION = new HashMap<>();
    
    static Object getAttribute(String key){
        return SESSION.get(key);
    }
    static void setAttribute(String key, Object value){
        SESSION.put(key, value);
    }
    
    static void destroy(){
        SESSION.clear();
    }
}
