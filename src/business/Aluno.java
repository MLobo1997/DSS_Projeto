/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import database.NotificacaoDAO;
import database.UCDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diogoleitao
 */
public class Aluno implements Utilizador{ 
    private String nome;
    private String email;
    private String username;
    private String password;
    private String estatuto;
    private Integer ano;
    private NotificacaoDAO notificacoes;
    private UCDAO ucs;

    public Aluno() {
        this.nome = null;
        this.email = null;
        this.username = null;
        this.password = null;
        this.estatuto = null;
        this.ano = null;
        this.notificacoes = new NotificacaoDAO();
        this.ucs = new UCDAO();
    }

    public Aluno(String nome, String email, String username, String password, String estatuto, Integer ano) {
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.estatuto = estatuto;
        this.ano = ano;
        this.notificacoes = new NotificacaoDAO();
        this.ucs = new UCDAO();
    }

    public List<UC> getUcs() {
        return this.ucs.list(this);
    }

    public void setUcs(List<UC> ucs) {
        this.ucs.adicionaUCs(this.username, ucs);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstatuto() {
        return estatuto;
    }

    public void setEstatuto(String estatuto) {
        this.estatuto = estatuto;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public List<String> getNotificacoes() {
        return notificacoes.list(this);
    }

    public void setNotificacoes(List<String> notificacoes) {
        
    }
    
    public void remInscricao(String sigla){
        this.ucs.remInscricaoAluno(sigla, this.username);
    }
    
    public void addInscricao(String sigla){
        this.ucs.addInscricaoAluno(sigla, this.username);
    }

    @Override
    public String toString() {
        return "Aluno{" + "nome=" + nome + ", email=" + email + ", username=" + username + ", password=" + password + ", estatuto=" + estatuto + ", ano=" + ano + ", notificacoes=" + notificacoes + '}';
    }
    
    public List<Turno> getTurnos(){
        List<Turno> turnos = new ArrayList<Turno>();
        List<UC> ucsDoAluno = this.getUcs();
        
        for(UC u : ucsDoAluno){
            for(Turno t : u.getTurnos()){
                if(t.temAluno(this)){
                    turnos.add(t);
                }
            }
        }
        
        return turnos;
    }
    
    public List<Troca> getTrocas(){
        List<Troca> trocas = new ArrayList<Troca>();
        List<UC> ucsDoAluno = this.getUcs();
        
        for(UC u : ucsDoAluno){
            for(Turno turno : u.getTurnos()){
                for(Troca t : turno.getTrocas()){
                    if(t.getAluno().getUsername().equals(username)){
                        trocas.add(t);
                    }
                }
            }
        }
        return trocas;
    }

}
