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
public class DistribuiHorario {
    
    private int semestreAtual;
    private List<Horario> ano1s1;
    private List<Horario> ano1s2;
    private int totalAno1;
    private List<Horario> ano2s1;
    private List<Horario> ano2s2;
    private int totalAno2;
    private List<Horario> ano3s1;
    private List<Horario> ano3s2;
    private int totalAno3;

    public DistribuiHorario(int semestreAtual) {
        this.semestreAtual = semestreAtual;
        this.ano1s1 = new ArrayList<Horario>();
        this.ano1s2 = new ArrayList<Horario>();
        this.ano2s1 = new ArrayList<Horario>();
        this.ano2s2 = new ArrayList<Horario>();
        this.ano3s1 = new ArrayList<Horario>();
        this.ano3s2 = new ArrayList<Horario>();
        this.totalAno1 = 0;
        this.totalAno2 = 0;
        this.totalAno3 = 0;
    }
    
    public void addHorario(Horario h){
        int ano = h.getAno();
        int semestre = h.getSemestre();
        switch(ano){
            case 1:
                if(semestre == 1) this.ano1s1.add(h);
                else this.ano1s2.add(h);
                break;
            
            case 2:
                if(semestre == 1) this.ano2s1.add(h);
                else this.ano2s2.add(h);
                break;
                
            case 3:
                if(semestre == 1) this.ano3s1.add(h);
                else this.ano3s2.add(h);
                break;
        }
    }
    
    public void distribui(List<Aluno> alunos){
        for (Aluno a: alunos){
            int ano = a.getAno();
            a.getUcs(); //inscrever só nestas UCs
            //implementar metodo no aluno de devolve uma lista com as siglas da UC 
        }
    }
    
    
}
