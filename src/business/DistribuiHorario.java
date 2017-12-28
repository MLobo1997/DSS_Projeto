/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import database.TurnoDAO;
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
    private int totalAlunosAno1;
    private int nHorariosAno1;
    private List<Horario> ano2s1;
    private List<Horario> ano2s2;
    private int totalAlunosAno2;
    private int nHorariosAno2;
    private List<Horario> ano3s1;
    private List<Horario> ano3s2;
    private int totalAlunosAno3;
    private int nHorariosAno3;
    private TurnoDAO turnos;

    public DistribuiHorario(int semestreAtual) {
        this.semestreAtual = semestreAtual;
        this.ano1s1 = new ArrayList<Horario>();
        this.ano1s2 = new ArrayList<Horario>();
        this.ano2s1 = new ArrayList<Horario>();
        this.ano2s2 = new ArrayList<Horario>();
        this.ano3s1 = new ArrayList<Horario>();
        this.ano3s2 = new ArrayList<Horario>();
        this.totalAlunosAno1 = 0;
        this.totalAlunosAno2 = 0;
        this.totalAlunosAno3 = 0;
        this.nHorariosAno1 = 0;
        this.nHorariosAno1 = 0;
        this.nHorariosAno1 = 0;
        this.turnos = new TurnoDAO();
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
    
    private void inscreve(Aluno a, int ano){
        Horario h = null;
        if(this.semestreAtual == 1){
            switch(ano){
                case 1:
                    h = this.ano1s1.get(totalAlunosAno1%nHorariosAno1);
                    this.turnos.inscreveTurnos(a, h);
                    this.totalAlunosAno1++;
                    break;
                case 2:
                    h = this.ano2s1.get(totalAlunosAno2%nHorariosAno2);
                    this.turnos.inscreveTurnos(a, h);
                    this.totalAlunosAno2++;
                    break;

                case 3:
                    h = this.ano3s1.get(totalAlunosAno2%nHorariosAno3);
                    this.turnos.inscreveTurnos(a, h);
                    this.totalAlunosAno3++;
                    break;
            }
        }
        else{
            switch(ano){
                case 1:
                    h = this.ano1s2.get(totalAlunosAno1%nHorariosAno1);
                    this.turnos.inscreveTurnos(a, h);
                    this.totalAlunosAno1++;
                    break;

                case 2:
                    h = this.ano2s2.get(totalAlunosAno1%nHorariosAno2);
                    this.turnos.inscreveTurnos(a, h);
                    this.totalAlunosAno2++;
                    break;

                case 3:
                    h = this.ano3s2.get(totalAlunosAno1%nHorariosAno3);
                    this.turnos.inscreveTurnos(a, h);
                    this.totalAlunosAno3++;
                    break;
            }
        }
    }
    
    public void distribui(List<Aluno> alunos){
        if(this.semestreAtual == 1){
            this.nHorariosAno1 = this.ano1s1.size();
            this.nHorariosAno2 = this.ano2s1.size();
            this.nHorariosAno3 = this.ano3s1.size();
        }
        else{
            this.nHorariosAno1 = this.ano1s2.size();
            this.nHorariosAno2 = this.ano2s2.size();
            this.nHorariosAno3 = this.ano3s2.size();
        }
        for (Aluno a: alunos){
            inscreve(a, a.getAno());
        }
    }
    
    
    
}
