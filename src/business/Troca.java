/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.time.LocalDate;
import java.util.GregorianCalendar;

/**
 *
 * @author diogoleitao
 */
public class Troca implements Comparable{//Este objeto só existe no turno para o qual o Aluno quer ir
    
    private LocalDate data;
    private Aluno aluno;
    private Turno turnoAtual;

    public Troca() {
        this.data = null;
        this.aluno = null;
        this.turnoAtual = null;
    }

    public Troca(LocalDate data, Aluno aluno, Turno turnoAtual) {
        this.data = data;
        this.aluno = aluno;
        this.turnoAtual = turnoAtual;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turno getTurnoAtual() {
        return turnoAtual;
    }

    public void setTurnoAtual(Turno turnoAtual) {
        this.turnoAtual = turnoAtual;
    }

    @Override
    public int compareTo(Object o) {
        Troca t = (Troca) o;
        String estatutoT = t.getAluno().getEstatuto();
        String estatutoThis = this.getAluno().getEstatuto();
        
        if(estatutoThis.equals("TE") && estatutoT.equals("TE")){
            return this.getData().compareTo(t.getData());
        }
        
        if(estatutoThis.equals("TE")){
            return -1;
        }
        
        if(estatutoT.equals("TE")){
            return 1;
        }
        
        else{
            return this.getData().compareTo(t.getData());
        } 
    }
}
