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
public class Falta {
    
    private Aluno aluno;
    private GregorianCalendar data;

    public Falta() {
        this.aluno = null;
        this.data = null;
    }

    public Falta(Aluno aluno, GregorianCalendar data) {
        this.aluno = aluno;
        this.data = data;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }
    
}
