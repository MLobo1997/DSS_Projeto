/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diogoleitao
 */
public class Horario {
        
    private int ano;
    private int semestre;
    private List<String> turnos;

    public Horario(int ano, int semestre) {
        this.ano = ano;
        this.semestre = semestre;
        this.turnos = new ArrayList<String>();
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public List<String> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<String> turnos) {
        this.turnos = turnos;
    }
    
    public void addTurno(String t){
        this.turnos.add(t);
    }
}
