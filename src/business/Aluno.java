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
public class Aluno implements Utilizador{ 
    private String nome;
    private String email;
    private String username;
    private String password;
    private String estatuto;
    private Integer ano;
    private List<String> notificacoes;

    public Aluno() {
        this.nome = new String();
        this.email = new String();
        this.username = new String();
        this.password = new String();
        this.estatuto = new String();
        this.ano = new Integer(0);
        this.notificacoes = new ArrayList<String>();
    }

    public Aluno(String nome, String email, String username, String password, String estatuto, Integer ano) {
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.estatuto = estatuto;
        this.ano = ano;
        this.notificacoes = new ArrayList<String>();
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
        return notificacoes;
    }

    public void setNotificacoes(List<String> notificacoes) {
        this.notificacoes = notificacoes;
    }

    

}
