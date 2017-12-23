/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import database.AlunoDAO;
import database.FaltaDAO;
import database.TrocaDAO;
import java.util.List;

/**
 *
 * @author diogoleitao
 */
public class Turno { //TODO: mudar o get e set do DAOS
    
    private String codigo; //(Ex: TP1-DSS)
    private Integer capacidade;
    private String tipo;
    private String diaSem;
    private String hora;
    private Docente docente;
    private FaltaDAO faltas; //List<Falta> faltas; 
    private TrocaDAO trocas; //List<Troca> trocas;
    private AlunoDAO alunos; //List<Aluno> alunos;

    public Turno() {
        this.codigo = null; 
        this.capacidade = null;
        this.tipo = null;
        this.diaSem = null;
        this.hora = null;
        this.docente = null;
        this.faltas = new FaltaDAO(); 
        this.trocas = new TrocaDAO(); 
        this.alunos = new AlunoDAO();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDiaSem() {
        return diaSem;
    }

    public void setDiaSem(String diaSem) {
        this.diaSem = diaSem;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public List<Falta> getFaltas() {
        return faltas.list(this);
    }

    public void setFaltas(List<Falta> faltas) {
        
    }

    public List<Troca> getTrocas() {
        return trocas.list(this);
    }

    public void setTrocas(List<Troca> trocas) {
        
    }

    public List<Aluno> getAlunos() {
        return alunos.list(this);
    }

    public void setAlunos(List<Aluno> alunos) {
        
    }

        
    
    
}
