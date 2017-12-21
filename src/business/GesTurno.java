/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

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
    
     private Utilizador utilizador; //Aqui talvez não faça sentido termos o UtilizadorDAO
     
     
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
                    
                    if(!username.matches("A[0-9]{5}")) throw new Exception(); //TODO: criar Exception para este caso????
                        
                    System.out.println("------------------------------");
                    System.out.println("Nome    : " + nome);
                    System.out.println("Email   : " + email);
                    System.out.println("Username: " + username);
                    System.out.println("------------------------------");
                    
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
                    
                    System.out.println("------------------------------");
                    System.out.println("Nome    : " + nome);
                    System.out.println("Email   : " + email);
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);
                    System.out.println("------------------------------");

                }
            }catch(IndexOutOfBoundsException e){}
     }
     
    public void registarUCs(String path) throws FileNotFoundException, Exception{
        InputStream fis = new FileInputStream(path);

            JsonReader reader = Json.createReader(fis);
            JsonArray ucs = reader.readArray();
            reader.close();
            
            try{
                for (int i = 0; ; i++){
                    
                    JsonObject uc = ucs.getJsonObject(i);
                    System.out.println("------------------------------");
                    System.out.println("Codigo  : " + uc.getString("Codigo"));
                    System.out.println("Nome    : " + uc.getString("UC"));
                    System.out.println("Sigla   : " + uc.getString("Sigla"));
                    System.out.println("------------------------------");

                }
            }catch(IndexOutOfBoundsException e){}
    }
     
    
}
