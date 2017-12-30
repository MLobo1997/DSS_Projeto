/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import database.UCDAO;
import database.UtilizadorDAO;
import exceptions.PedidoRegistadoException;
import exceptions.RegistoInvalidoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
     private InfoLocais info;
     
    public GesTurno(){
        this.utilizador = null;
        this.utilizadores = new UtilizadorDAO();
        this.ucs = new UCDAO();
        this.info = new InfoLocais();
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
    
    public List<Aluno> getAlunos(){
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        
        for(Utilizador u : this.utilizadores.values()){
            if(u instanceof Aluno){
                alunos.add((Aluno)u);
            }
        }
        
        return alunos;
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
     
    public int iniciarSessao(String username, String password){ 
       
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
        
        List<UC> ano1 =  this.ucs.getUCsAno(1);
        List<UC> ano2 =  this.ucs.getUCsAno(2);
        List<UC> ano3 =  this.ucs.getUCsAno(3);

        JsonReader reader = Json.createReader(fis);
        JsonArray utilizadores = reader.readArray();
        reader.close();

        for (int i = 0; i < utilizadores.size(); i++){
            JsonObject aluno = utilizadores.getJsonObject(i);
            String username = aluno.getString("Username");
            String nome = aluno.getString("Nome") + " " + aluno.getString("Apelido");
            String email = aluno.getString("Email");
            String ano = aluno.getString("Ano");
            String estatuto = aluno.getString("Estatuto");

            if(!username.matches("A[0-9]{5}")) throw new Exception(); 

            Aluno a = new Aluno(nome, email, username, "", estatuto, Integer.parseInt(ano));
            this.utilizadores.put(username, a);
            switch (new Integer(ano)) {
                case 1:
                    a.setUcs(ano1);
                    break;
                case 2:
                    a.setUcs(ano2);
                    break;
                case 3:
                    a.setUcs(ano3);
                    break;
                default:
                    break;
            }
        }

     }
     
    public void registarDocentes(String path) throws FileNotFoundException, Exception { 
         InputStream fis = new FileInputStream(path);

        JsonReader reader = Json.createReader(fis);
        JsonArray utilizadores = reader.readArray();
        reader.close();

        for (int i = 0; i < utilizadores.size(); i++){
            JsonObject docente = utilizadores.getJsonObject(i);
            String username = docente.getString("Username");
            String nome = docente.getString("Nome") + " " + docente.getString("Apelido");
            String email = docente.getString("Email");
            String password = docente.getString("Password");

            if(!username.matches("D[0-9]{5}")) throw new Exception();

            Docente d = new Docente(nome, email, username, password);
            this.utilizadores.put(username, d);

        }
     }
     
    public void registarUCs(String path) throws FileNotFoundException, Exception{
        InputStream fis = new FileInputStream(path);

        JsonReader reader = Json.createReader(fis);
        JsonArray x = reader.readArray();
        reader.close();

        for (int i = 0; i < x.size(); i++){
            JsonObject uc = x.getJsonObject(i);

            String sigla = uc.getString("Sigla");
            String nome = uc.getString("UC");
            int ano = uc.getInt("Ano");
            int semestre = uc.getInt("Semestre");
            UC u = new UC(sigla, ano, semestre, nome);
            this.ucs.put(sigla, u);

            JsonArray turnos = uc.getJsonArray("Turnos");
            ArrayList<Turno> ts = new ArrayList<Turno>();

            for (int j = 0; j < turnos.size(); j++) {
                Turno turno = new Turno();
                JsonObject t = turnos.getJsonObject(j);
                String codigo = t.getString("Codigo");
                int capacidade = t.getInt("Capacidade");
                String tipo = t.getString("Tipo");
                String diaSem = t.getString("DiaSem");
                String hora = t.getString("Hora");
                String docente = t.getString("Docente");

                turno.setCodigo(codigo); 
                turno.setCapacidade(capacidade);
                turno.setTipo(tipo);
                turno.setDiaSem(diaSem);
                turno.setHora(hora);
                turno.setDocente((Docente)this.utilizadores.get(docente));
                turno.setNTrocas(0);

                ts.add(turno);
            }

            u.setTurnos(ts);//vai inserir na base da dados


        }
    }
    
    public void atribuiHorarios(String path) throws FileNotFoundException, Exception{
        InputStream fis = new FileInputStream(path);

        JsonReader reader = Json.createReader(fis);
        JsonArray x = reader.readArray();
        reader.close();
        DistribuiHorario dh = new DistribuiHorario(this.getSemestre()); 

        String[] n = {"H1.1","H1.2","H2.1","H2.2","H3.1","H3.2"};
        for (int i = 0; i < x.size(); i++){

            JsonObject horario = x.getJsonObject(i);

            JsonArray turnos = horario.getJsonArray(n[i]);

            for(int j = 0; j < turnos.size(); j++){
                JsonArray js = turnos.getJsonArray(j);
                int ano = Integer.valueOf(n[i].substring(1, 2));
                int semestre = Integer.valueOf(n[i].substring(3,4));
                
                Horario h = new Horario(ano, semestre);
                for(int k = 0; k < js.size(); k++){
                    String t = js.getString(k);
                    h.addTurno(t);

                }
                dh.addHorario(h);
            }
        }
        dh.distribui(this.getAlunos());
    }
     
    public void atualizaUC(UC u){
        this.ucs.put(u.getSigla(), u);
    }
    
    public void atualizaTurno(UC u, String turnoCodigo, String docUsername, String hora, String diaSem, String capacidade,Integer nTrocas){
        Turno t = u.getTurno(turnoCodigo);
        String docAnterior = t.getDocente().getUsername();
        
        if(!docAnterior.equals(docUsername)){
            if(u.getTurnos().stream().map(f -> f.getDocente().getUsername())
                                     .filter(x -> x.equals(docAnterior)).count() == 1){
                this.ucs.removeDocente(docAnterior, u.getSigla());
            }
        }
        
        t.setHora(hora);
        t.setDocente((Docente)this.utilizadores.get(docUsername));
        t.setDiaSem(diaSem);
        t.setCapacidade(new Integer(capacidade));
        t.setNTrocas(nTrocas);
        
        u.atualizaTurno(t);
    }
    
    public void removeUC(String sigla){
        this.ucs.remove(sigla);
    }
    
    public Turno removeTurno(String siglaUC, String codigoTurno){
        UC u = this.ucs.get(siglaUC);
        return u.removeTurno(codigoTurno);
    }
    
    public void registaFaltas(String turnoCodigo, List<String> usernames, LocalDate data){
        Turno turno = this.getTurno(turnoCodigo);
        String tipo = turno.getTipo();
        List<Falta> faltas = new ArrayList<Falta>();
            for(String s : usernames){
                Aluno a = (Aluno)this.getUtilizadorByUsername(s.split("\t")[0]);
                StringBuilder sb = new StringBuilder();
                sb.append("Falta a: ");
                sb.append(turnoCodigo);
                sb.append(" em ");
                sb.append(data.toString());
                a.addNotificacao(sb.toString());
                int nFaltas = turno.getNFaltas(s.split("\t")[0]);
                if(!tipo.equals("T")){
                    if(nFaltas == 2){
                        a.addNotificacao("Atingiu o limite de faltas possíveis em " + turnoCodigo);
                    }
                    else if(nFaltas == 3){
                        a.addNotificacao("Atingiu as 4 faltas em " + turnoCodigo);
                        a.addNotificacao("Perdeu o seu lugar em " + turnoCodigo);
                        turno.removeFaltas(s.split("\t")[0]);
                        this.ucs.get(turnoCodigo.split("-")[0]).removeDoTurno(s.split("\t")[0], turnoCodigo);
                        continue;
                    }
                }
                Falta f = new Falta(a, data);
                faltas.add(f);
            }
        turno.setFaltas(faltas);
    }
    
    public List<Aluno> getAlunos(String siglaUC){
        List<String> usernames = this.ucs.getAlunosUsername(siglaUC);
        return this.utilizadores.values().stream().filter(f -> usernames.contains(f.getUsername()))
                                                  .map(c -> (Aluno) c).collect(Collectors.toList());
    }
    
    public List<UC> getUCsAno(int ano){
        return this.ucs.getUCsAno(ano);
    }
    
    public UC getUC(String sigla){
        return this.ucs.get(sigla);
    }
    
    
    public void TrocaTurno(Aluno aluno, String codigoTurnoAtual, String codigoTurnoPretendido) throws PedidoRegistadoException{
        String siglaUC = codigoTurnoAtual.split("-")[0];
        String alunoUsername = aluno.getUsername();
        UC u = this.getUC(siglaUC);
        Turno atual = u.getTurno(codigoTurnoAtual);
        Turno escolhido = u.getTurno(codigoTurnoPretendido);
        String estatuto = aluno.getEstatuto();
        
        
        if(escolhido.getCapacidade() > escolhido.getAlunos().size() && estatuto.equals("TE")){ // ainda há vagas e aluno é trabalhador estudante
            u.removeDoTurno(alunoUsername, codigoTurnoAtual);
            u.inscreveNoTurno(alunoUsername, codigoTurnoPretendido);
            atual.incrementaNTrocas();
            escolhido.incrementaNTrocas();
            u.atualizaTurno(atual);
            u.atualizaTurno(escolhido);
        }
        
        
        else{
            Set<Troca> trocas = atual.getTrocas(); //Ordenado pelo compareTo de Troca
            boolean flag = false;
            for(Troca t: trocas){ 
                
                if(t.getTurnoAtual().getCodigo().equals(codigoTurnoPretendido)){//foi possível fazer a troca
                    Aluno outroAluno = t.getAluno();
                    String outroAlunoUsername = outroAluno.getUsername();
                    
                    StringBuilder sb = new StringBuilder();
                    sb.append("Troca efetuada em ");
                    sb.append(codigoTurnoAtual.split("-")[0]);
                    sb.append(" ");
                    sb.append(codigoTurnoPretendido.split("-")[1]);
                    sb.append(" -> ");
                    sb.append(codigoTurnoAtual.split("-")[1]);
                    outroAluno.addNotificacao(sb.toString());
                    
                    u.removeDoTurno(alunoUsername, codigoTurnoAtual);
                    u.removeDoTurno(outroAlunoUsername, codigoTurnoPretendido);
                    u.inscreveNoTurno(alunoUsername, codigoTurnoPretendido);
                    u.inscreveNoTurno(outroAlunoUsername, codigoTurnoAtual);
                    
                    atual.removeTroca(outroAlunoUsername, codigoTurnoPretendido);
                    
                    atual.incrementaNTrocas();
                    escolhido.incrementaNTrocas();
                    u.atualizaTurno(atual);
                    u.atualizaTurno(escolhido);
                    flag = true;
                    break;
                }
            }
            
            if(flag == false){ //não foi possível fazer a troca
                LocalDate agora = LocalDate.now();
                Troca t = new Troca(agora, aluno, atual);
                escolhido.addTroca(t);
                throw new PedidoRegistadoException();
            }
            
        }
    }
    
    public void removeTroca(String alunoUsername, String atual, String escolhido){
        UC u = this.ucs.get(atual.split("-")[0]);
        Turno t = u.getTurno(escolhido);
        t.removeTroca(alunoUsername, atual);
    }
    
    public int getSemestre(){
        return this.info.getSemestre();
    }
    
    public void setSemestre(int semestre){
        this.info.setSemestre(semestre);
    }
    
    public int getFase(){
        return this.info.getFase();
    }
    
    public void setFase(int fase){
        this.info.setFase(fase);
    }
    
    public void reset(){
        this.info.reset();
    }
}
