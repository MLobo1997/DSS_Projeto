/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author diogoleitao
 */
public class Docente implements Utilizador{ 
    private String nome;
    private String email;
    private String username;
    private String password;  

    public Docente() {
        this.nome = new String();
        this.email = new String();
        this.username = new String();
        this.password = new String();
    }
    
    public Docente(String nome, String email, String username, String password) {
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.password = password;
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

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
