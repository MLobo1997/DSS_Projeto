/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import database.InfoLocaisDAO;

/**
 *
 * @author diogoleitao
 */
public class InfoLocais {
    
    private InfoLocaisDAO info;
    
    
    public InfoLocais(){
        this.info = new InfoLocaisDAO();
        if(this.info.size() == 0){
            this.info.novo();
        }
    }
    
    public int getSemestre(){
        return this.info.getSemestre();
    }
    
    public void setSemestre(int semestre){
        this.info.setSemestre(semestre);
    }
    
    public int getFase(){
        return this.info.getFase();
    }
    
    public void setFase(int fase){
        this.info.setFase(fase);
    }
}
