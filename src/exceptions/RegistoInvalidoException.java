/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author diogoleitao
 */
public class RegistoInvalidoException extends Exception{
    
    public RegistoInvalidoException(){
        super();
    }
            
    public RegistoInvalidoException(String m){
        super(m);
    }
}
