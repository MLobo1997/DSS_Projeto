/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author diogoleitao
 */
public class AuxTroca {
    
    private String detalhesTroca;
    private Troca troca;
    
    public AuxTroca(String codigoTurno, Troca troca){
        this.detalhesTroca = codigoTurno;
        this.troca = troca;
    }
    
    public Troca getTroca(){
        return this.troca;
    }
    
    public String getDetalhes(){
        return this.detalhesTroca;
    }
    
}
