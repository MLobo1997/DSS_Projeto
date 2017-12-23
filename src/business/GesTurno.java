/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import database.UCDAO;
import database.UtilizadorDAO;
import exceptions.RegistoInvalidoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author diogoleitao
 */
public class GesTurno {
    
     private Utilizador utilizador;
     private UtilizadorDAO utilizadores;
     private UCDAO ucs;
     
     public GesTurno(){
         this.utilizador = null;
         this.utilizadores = new UtilizadorDAO();
         this.ucs = new UCDAO();
     }
     
    public int iniciarSessao(String username, String password){ //TODO: ser especifico no erro de acesso????
       
        if(this.utilizadores.containsKey(username)){
            Utilizador u = utilizadores.get(username);
            
            if(!u.getPassword().equals(password) || password.equals("")) return 0;
            this.utilizador = u;
            if(this.utilizador instanceof Aluno) return 1;
            else return 2;
        }
        else if(username.equals("admin")){
            return 3;
        }
        else return 0;
    }
    
    public void registo(String nome, String username, String email, String password) throws RegistoInvalidoException{
        if(!this.utilizadores.containsKey(username)) throw new RegistoInvalidoException();
        Utilizador u = this.utilizadores.get(username);
        if(u instanceof Docente) throw new RegistoInvalidoException();
        
        Aluno a = (Aluno) u;
        String n = a.getNome();
        String e = a.getEmail();
        String p = a.getPassword();
        
        if(n.equals(nome) && e.equals(email) && p.equals("")){
            a.setPassword(password);
            this.utilizadores.put(username, a);
        }
        else throw new RegistoInvalidoException();
                
    }
     
    public void registarAlunos(String path) throws FileNotFoundException, Exception { //TODO: Alterar codigo para cirar novos alunos
         InputStream fis = new FileInputStream(path);

            JsonReader reader = Json.createReader(fis);
            JsonArray utilizadores = reader.readArray();
            reader.close();
            
            try{
                for (int i = 0; ; i++){
                    JsonObject aluno = utilizadores.getJsonObject(i);
                    String username = aluno.getString("Username");
                    String nome = aluno.getString("Nome") + " " + aluno.getString("Apelido");
                    String email = aluno.getString("Email");
                    String ano = aluno.getString("Ano");
                    String estatuto = aluno.getString("Estatuto");
                    
                    if(!username.matches("A[0-9]{5}")) throw new Exception(); //TODO: criar Exception para este caso????
                    /*    
                    System.out.println("------------------------------");
                    System.out.println("Nome    : " + nome);
                    System.out.println("Email   : " + email);
                    System.out.println("Username: " + username);
                    System.out.println("Ano     : " + ano);
                    System.out.println("Estatuto: " + estatuto);
                    System.out.println("------------------------------");
                    */
                    Aluno a = new Aluno(nome, email, username, "", estatuto, Integer.parseInt(ano));
                    this.utilizadores.put(username, a);
                }
            }catch(IndexOutOfBoundsException e){}
     }
     
    public void registarDocentes(String path) throws FileNotFoundException, Exception { 
         InputStream fis = new FileInputStream(path);

            JsonReader reader = Json.createReader(fis);
            JsonArray utilizadores = reader.readArray();
            reader.close();
            
            try{
                for (int i = 0; ; i++){
                    JsonObject docente = utilizadores.getJsonObject(i);
                    String username = docente.getString("Username");
                    String nome = docente.getString("Nome") + " " + docente.getString("Apelido");
                    String email = docente.getString("Email");
                    String password = docente.getString("Password");
                    
                    if(!username.matches("D[0-9]{5}")) throw new Exception();
                    /*
                    System.out.println("------------------------------");
                    System.out.println("Nome    : " + nome);
                    System.out.println("Email   : " + email);
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);
                    System.out.println("------------------------------");
                    */
                    Docente d = new Docente(nome, email, username, password);
                    this.utilizadores.put(username, d);

                }
            }catch(IndexOutOfBoundsException e){}
     }
     
    public void registarUCs(String path) throws FileNotFoundException, Exception{
        InputStream fis = new FileInputStream(path);

            JsonReader reader = Json.createReader(fis);
            JsonArray x = reader.readArray();
            reader.close();
            
            try{
                for (int i = 0; ; i++){
                    JsonObject uc = x.getJsonObject(i);
                    
                    String sigla = uc.getString("Sigla");
                    String nome = uc.getString("UC");
                    int ano = uc.getInt("Ano");
                    int semestre = uc.getInt("Semestre");
                    /*
                    System.out.println("------------------------------");
                    System.out.println("Nome    : " + nome);
                    System.out.println("Sigla   : " + sigla);
                    System.out.println("Ano     : " + ano);
                    System.out.println("Semestre: " + semestre);
                    System.out.println("------------------------------");
                    */
                    UC u = new UC(sigla, ano, semestre, nome);
                    this.ucs.put(sigla, u);
                }
            }catch(IndexOutOfBoundsException e){}
    }
     
     
}
