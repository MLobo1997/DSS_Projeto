/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import business.Aluno;
import business.Docente;
import business.Utilizador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diogoleitao
 */
public class UtilizadorDAO implements Map<String,Utilizador>{

    private Connection con;

    @Override
    public int size() {
        int size = 0;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Alunos");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){ 
                size = rs.getInt(1);
            }
            ps = con.prepareStatement("SELECT COUNT(*) FROM Docentes");
            rs = ps.executeQuery();
            if(rs.next()){ 
                size += rs.getInt(1);
            }
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                 System.out.printf(e.getMessage());
            }
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        String chave = (String) key;
        boolean res = false;
        
        try{
            con = Connect.connect();
            PreparedStatement ps = null;
            if(chave.startsWith("A")){
                ps = con.prepareStatement("SELECT * FROM Alunos WHERE Username = ?");
                ps.setString(1,chave);
                ResultSet rs = ps.executeQuery();
                res = rs.next();
            }
            if(chave.startsWith("D")){
                ps = con.prepareStatement("SELECT * FROM Docentes WHERE Username = ?");
                ps.setString(1,chave);
                ResultSet rs = ps.executeQuery();
                res = rs.next();
            }
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return res;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean res = false;
        
        if(value.getClass().getName().equals("business.Utilizador")){
            Utilizador u = (Utilizador)value;
            String username = u.getUsername();
            Utilizador ut = this.get(username);
            if(u.equals(ut)){
                res=true;
            }
        }
        return res;
    }

    @Override
    public Utilizador get(Object key) {
        Utilizador u = null;
        String chave = (String) key;
        try{
            if(chave.startsWith("A")){
                con = Connect.connect();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Alunos WHERE Username = ?");
                ps.setString(1,chave);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    Aluno a = new Aluno();
                    a.setEmail(rs.getString("Email"));
                    a.setNome(rs.getString("Nome"));
                    a.setPassword(rs.getString("Password"));
                    a.setUsername(rs.getString("Username"));
                    a.setEstatuto("Estatuto");
                    a.setAno(rs.getInt("Ano"));
                    
                    List<String> notificacoes = new ArrayList<String>();
                    ps = con.prepareStatement("SELECT * FROM Notificacoes WHERE Alunos_Username = ?");
                    ps.setString(1, chave);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        notificacoes.add(rs.getString("Texto"));
                    }
                    a.setNotificacoes(notificacoes);
                    u = (Utilizador)a;  
                }
            }
            
            if(chave.startsWith("D")){
                con = Connect.connect();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Docentes WHERE Username = ?");
                ps.setString(1,chave);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    Docente d = new Docente();
                    d.setEmail(rs.getString("Email"));
                    d.setNome(rs.getString("Nome"));
                    d.setPassword(rs.getString("Password"));
                    d.setUsername(rs.getString("Username"));
                    u = (Utilizador)d; 
                }
            }
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                 System.out.printf(e.getMessage());
            }
        }
        return u;
    }

    @Override
    public Utilizador put(String key, Utilizador value) {
        Utilizador u = null;
        try{
            con = Connect.connect();
            if(value instanceof Aluno){
                Aluno a = (Aluno) value;
                PreparedStatement ps = con.prepareStatement("INSERT INTO Alunos (Username,Nome,Password,Estatuto,Ano,Email)\n" +
                                                            "VALUES (?,?,?,?,?,?)\n" +
                                                            "ON DUPLICATE KEY UPDATE Nome=VALUES(Nome),Password=VALUES(Password),Estatuto=VALUES(Estatuto),Ano=VALUES(Ano),Email=VALUES(Email)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, a.getUsername());
                ps.setString(2, a.getNome());
                ps.setString(3, a.getPassword());
                ps.setString(4, a.getEstatuto());
                ps.setInt(5, a.getAno());
                ps.setString(6, a.getEmail());
                ps.executeUpdate(); 
                
                List<String> notificacoes = a.getNotificacoes();
                for(String texto : notificacoes){
                    ps = con.prepareStatement("INSERT INTO Notificaceos (Alunos_Username, Morador) VALUES (?,?)");
                    ps.setString(1,a.getUsername());
                    ps.setString(2,texto);
                    ps.executeUpdate();
                }
                u = (Utilizador) a;
            }
            if(value instanceof Docente){
                Docente d = (Docente) value;
                PreparedStatement ps = con.prepareStatement("INSERT INTO Docentes (Username,Nome,Password,Email)\n" +
                                                            "VALUES (?,?,?,?)\n" +
                                                            "ON DUPLICATE KEY UPDATE Nome=VALUES(Nome),Password=VALUES(Password),Email=VALUES(Email)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, d.getUsername());
                ps.setString(2, d.getNome());
                ps.setString(3, d.getPassword());
                ps.setString(4, d.getEmail());
                ps.executeUpdate(); 
                
                u = (Utilizador) d;
            }
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return u;
    }

    @Override
    public Utilizador remove(Object key) {
        String chave = (String) key;
        Utilizador u = null;
        try{
            con = Connect.connect();
            if(chave.startsWith("A")){
                u = this.get(key);
                PreparedStatement ps = con.prepareStatement("DELETE FROM Alunos WHERE Username = ?");
                ps.setString(1, (String) key);
                ps.executeUpdate();
            }
        
            if(chave.startsWith("D")){
                u = this.get(key);
                PreparedStatement ps = con.prepareStatement("DELETE FROM Docentes WHERE Username = ?");
                ps.setString(1, (String) key);
                ps.executeUpdate();
            }   
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        } 
        return u;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Utilizador> m) {
       for(Utilizador u: m.values()){
           put(u.getUsername(),u);
       }
    }

    @Override
    public void clear() {
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Alunos");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM Docentes");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM Notificacoes");
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> usernames = null;
        
        try{
            con = Connect.connect();
            usernames = new HashSet<>();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Alunos");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                usernames.add(rs.getString("Username"));
            } 
            ps = con.prepareStatement("SELECT * FROM Docentes");
            rs = ps.executeQuery();
            while(rs.next()){
                usernames.add(rs.getString("Username"));
            }  
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return usernames;
    }

    @Override
    public Collection<Utilizador> values() {
        Set<Utilizador> set = new HashSet<>();
        Set<String> keys = new HashSet<>(this.keySet());
        for(String key : keys){
            set.add(this.get(key));
        }
        return set; 
    }

    @Override
    public Set<Entry<String, Utilizador>> entrySet() {
        Set<String> keys = new HashSet<>(this.keySet());
        
        HashMap<String,Utilizador> map = new HashMap<>();
        for(String key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }
      
}
