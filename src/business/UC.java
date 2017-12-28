/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import database.TurnoDAO;
import java.util.List;

/**
 *
 * @author diogoleitao
 */
public class UC {
    
    private String sigla;
    private Integer ano;
    private Integer semester;
    private String nome;
    private TurnoDAO turnos;

    public UC() {
        this.sigla = null;
        this.ano = null;
        this.semester = null;
        this.nome = null;
        this.turnos = new TurnoDAO();
    }

    public UC(String sigla, Integer ano, Integer semester, String nome) {
        this.sigla = sigla;
        this.ano = ano;
        this.semester = semester;
        this.nome = nome;
        this.turnos = new TurnoDAO();
    }

    public List<Turno> getTurnos() {
        return this.turnos.list(this);
    }

    public void setTurnos(List<Turno> turnos) {
        TurnoDAO d = new TurnoDAO();
        d.addMTurnos(turnos, sigla);
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "UC{" + "sigla=" + sigla + ", ano=" + ano + ", semester=" + semester + ", nome=" + nome + ", turnos=" + turnos + '}';
    }
    
    public void atualizaTurno(Turno t){
        this.turnos.addTurno(t, sigla);
    }
    
    public Turno getTurno(String codigo){
        Turno t = null;
        for(Turno x: this.getTurnos()){
            if(x.getCodigo().equals(codigo))
                t = x;
        }
        return t;
    }
    
    public Turno removeTurno(String codigo){
        return this.turnos.remove(codigo);
    }
    
    public void removeDoTurno(String alunoUsername, String turnoCodigo){
        this.turnos.removeDoTurno(alunoUsername, turnoCodigo);
    }
    
    public void inscreveNoTurno(String alunoUsername, String turnoCodigo){
        this.turnos.inscreveNoTurno(alunoUsername, turnoCodigo);
    }
    
}
