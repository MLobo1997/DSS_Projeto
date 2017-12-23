/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.GregorianCalendar;

/**
 *
 * @author diogoleitao
 */
public class Troca {//Este objeto só existe no turno para o qual o Aluno quer ir
    
    private GregorianCalendar data;
    private Aluno aluno;
    private Turno turnoAtual;

    public Troca() {
        this.data = null;
        this.aluno = null;
        this.turnoAtual = null;
    }

    public Troca(GregorianCalendar data, Aluno aluno, Turno turnoAtual) {
        this.data = data;
        this.aluno = aluno;
        this.turnoAtual = turnoAtual;
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
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
}
