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
import java.util.ArrayList;
import java.util.List;
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
     
    public List<UC> getUCs(){
        return this.ucs.list();
    } 
    
    public Turno getTurno(String codigo){
        Turno t = null;
        for(UC u: ucs.list()){
            for(Turno x: u.getTurnos()){
                if(x.getCodigo().equals(codigo)){
                    t = x;
                }
            }
        }
        return t;
    }
    
    public List<Docente> getDocentes(){
        ArrayList<Docente> docentes = new ArrayList<Docente>();
        
        for(Utilizador u : this.utilizadores.values()){
            if(u instanceof Docente){
                docentes.add((Docente)u);
            }
        }
        
        return docentes;
    }
    
    public Utilizador getUtilizadorByUsername(String username){
        return this.utilizadores.get(username);
    }
    
    public Utilizador getUtilizador(){
        return this.utilizador;
    }
    
    public void setUtilizador(Utilizador u){
        this.utilizador = u;
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
        if(!this.utilizadores.containsKey(username)) 
            throw new RegistoInvalidoException();
        
        Utilizador u = this.utilizadores.get(username);
        
        if(u instanceof Docente) 
            throw new RegistoInvalidoException();
        
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
     
    public void registarAlunos(String path) throws FileNotFoundException, Exception { 
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
                    
                    if(!username.matches("A[0-9]{5}")) throw new Exception(); 

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
                    
                    Docente d = new Docente(nome, email, username, password);
                    this.utilizadores.put(username, d);

                }
            }catch(IndexOutOfBoundsException e){}
     }
     
    public void registarUCs(String path) throws FileNotFoundException, Exception{
        InputStream fis = new FileInputStream(path);

            JsonReader reader = Json.createReader(fis);
            System.out.println("no inicio");
            JsonArray x = reader.readArray();
            System.out.println("readArray");
            reader.close();
            
            try{
                for (int i = 0; ; i++){
                    JsonObject uc = x.getJsonObject(i);
                    
                    String sigla = uc.getString("Sigla");
                    String nome = uc.getString("UC");
                    int ano = uc.getInt("Ano");
                    int semestre = uc.getInt("Semestre");
                    UC u = new UC(sigla, ano, semestre, nome);
                    this.ucs.put(sigla, u);
                    System.out.println("Vou para os turnos");
                    JsonArray turnos = uc.getJsonArray("Turnos");
                    ArrayList<Turno> ts = new ArrayList<Turno>();
                    try{
                        for (int j = 0; ; j++) {
                            Turno turno = new Turno();
                            System.out.println("buscar jason turnos");
                            JsonObject t = turnos.getJsonObject(j);
                            System.out.println("Afinal não é daqui!");
                            String codigo = t.getString("Codigo");
                            int capacidade = t.getInt("Capacidade");
                            String tipo = t.getString("Tipo");
                            String diaSem = t.getString("DiaSem");
                            String hora = t.getString("Hora");
                            String docente = t.getString("Docente");
                            System.out.println("Vou para as trocas");
                            
                            turno.setCodigo(codigo); 
                            turno.setCapacidade(capacidade);
                            turno.setTipo(tipo);
                            turno.setDiaSem(diaSem);
                            turno.setHora(hora);
                            turno.setDocente((Docente)this.utilizadores.get(docente));
                            System.out.println("Vou para as trocas");
                            turno.setNTrocas(0);
                            /*
                            System.out.println("------------------------------");
                            System.out.println("Codigo     : " + codigo);
                            System.out.println("Capacidade : " + capacidade);
                            System.out.println("Tipo       : " + tipo);
                            System.out.println("DiaSem     : " + diaSem);
                            System.out.println("Hora       : " + hora);
                            System.out.println("Docente    : " + docente);
                            System.out.println("------------------------------");
                            */
                        }
                    }catch(IndexOutOfBoundsException e){}
                    u.setTurnos(ts);//vai inserir na base da dados
                    
                    
                }
            }catch(IndexOutOfBoundsException e){}
    }
     
    public void atualizaUC(UC u){
        this.ucs.put(u.getSigla(), u);
    }
    
    public void atualizaTurno(UC u, String turnoCodigo, String docUsername, String hora, String diaSem, String capacidade,String nTrocas){
        Turno t = u.getTurno(turnoCodigo);
        String docAnterior = t.getDocente().getUsername();
        
        if(!docAnterior.equals(docUsername)){
            if(u.getTurnos().stream().map(f -> f.getDocente().getUsername()).filter(x -> x.equals(docAnterior)).count() == 1){
                this.ucs.removeDocente(docAnterior, u.getSigla());
            }
        }
        
        t.setHora(hora);
        t.setDocente((Docente)this.utilizadores.get(docUsername));
        t.setDiaSem(diaSem);
        t.setCapacidade(new Integer(capacidade));
        t.setNTrocas(new Integer(nTrocas));
        
        u.atualizaTurno(t);
    }
    
    public void removeUC(String sigla){
        this.ucs.remove(sigla);
    }
    
    public Turno removeTurno(String siglaUC, String codigoTurno){
        UC u = this.ucs.get(siglaUC);
        return u.removeTurno(codigoTurno);
    }
    
    public void registaFaltas(String turnoCodigo, List<Falta> faltas){
        this.getTurno(turnoCodigo).setFaltas(faltas);
    }
}
