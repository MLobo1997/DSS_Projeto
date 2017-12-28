/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import business.GesTurno;
import presentation.Login;

/**
 *
 * @author diogoleitao
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GesTurno gesTurno = new GesTurno();
        Login f = new Login(gesTurno);
        f.setVisible(true);
    }
    
}
