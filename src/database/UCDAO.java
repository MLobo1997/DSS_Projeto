/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import business.Aluno;
import business.Docente;
import business.UC;
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
import java.util.stream.Collectors;

/**
 *
 * @author diogoleitao
 */
public class UCDAO implements Map<String,UC>{ 
    
    private Connection con;
    
    public List<String> getAlunosUsername(String siglaUC){
        ArrayList<String> res = new ArrayList<String>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT  * FROM AlunosTemUCs WHERE UC_SIGLA = ?");
            ps.setString(1, siglaUC);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                res.add(rs.getString("Aluno_Username"));
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<UC> getUCsAno(int ano){
        return this.list().stream().filter(f -> f.getAno() == ano).collect(Collectors.toList());
    }
    
    public List<UC> list(){
        ArrayList<UC> res = new ArrayList<UC>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT  * FROM UCs");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                UC u = new UC();
                u.setSigla(rs.getString("Sigla"));
                u.setAno(rs.getInt("Ano"));
                u.setSemester(rs.getInt("Semestre"));
                u.setNome(rs.getString("Nome"));
                res.add(u);
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<UC> list(Docente d){
        ArrayList<UC> res = new ArrayList<UC>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT  * FROM UCs u\n"
                                                      + "INNER JOIN DocentesTemUCs dtc ON u.Sigla = dtc.UC_Sigla\n"
                                                      + "WHERE dtc.Docente_Username = ?");
            ps.setString(1, d.getUsername());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                UC u = new UC();
                u.setSigla(rs.getString("Sigla"));
                u.setAno(rs.getInt("Ano"));
                u.setSemester(rs.getInt("Semestre"));
                u.setNome(rs.getString("Nome"));
                res.add(u);
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<UC> list(Aluno a){
        ArrayList<UC> res = new ArrayList<UC>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT  * FROM UCs u\n"
                                                      + "INNER JOIN AlunosTemUCs atc ON u.Sigla = atc.UC_Sigla\n"
                                                      + "WHERE atc.Aluno_Username = ?");
            ps.setString(1, a.getUsername());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                UC u = new UC();
                u.setSigla(rs.getString("Sigla"));
                u.setAno(rs.getInt("Ano"));
                u.setSemester(rs.getInt("Semestre"));
                u.setNome(rs.getString("Nome"));
                res.add(u);
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public int size() {
        int size = 0;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM UCs");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){ 
                size = rs.getInt(1);
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM UCs WHERE Sigla = ?");
            ps.setString(1,chave);
            ResultSet rs = ps.executeQuery();
            res = rs.next();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
  
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return res;}

    @Override
    public boolean containsValue(Object value) {
        boolean res = false;
        
        if(value.getClass().getName().equals("business.UC")){
            UC u = (UC) value;
            String sigla = u.getSigla();
            UC uc = this.get(sigla);
            if(u.equals(uc)){
                res=true;
            }
        }
        return res;
    }

    @Override
    public UC get(Object key) {
        String chave = (String) key;
        UC uc = null;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM UCs WHERE Sigla = ?");
            ps.setString(1,chave);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                uc = new UC();
                uc.setSigla(rs.getString("Sigla"));
                uc.setAno(rs.getInt("Ano"));
                uc.setSemester(rs.getInt("Semestre"));
                uc.setNome(rs.getString("Nome"));
            }
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                 System.out.printf(e.getMessage());
            }
        }
        return uc; 
    }

    @Override
    public UC put(String key, UC value) {
        UC u = null;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("INSERT INTO UCs (Sigla,Ano,Semestre,Nome)\n" +
                                                        "VALUES (?,?,?,?)\n" +
                                                        "ON DUPLICATE KEY UPDATE Ano=VALUES(Ano),Semestre=VALUES(Semestre),Nome=VALUES(Nome)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, value.getSigla());
            ps.setInt(2, value.getAno());
            ps.setInt(3, value.getSemester());
            ps.setString(4, value.getNome());
            ps.executeUpdate(); 

            u = value;
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public UC remove(Object key) {
        UC u = this.get(key);
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM UCs WHERE Sigla = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public void putAll(Map<? extends String, ? extends UC> m) {
        for(UC u: m.values()){
           put(u.getSigla(),u);
        }    
    }

    @Override
    public void clear() {
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM UCs");
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
        Set<String> siglas = null;
        
        try{
            con = Connect.connect();
            siglas = new HashSet<>();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM UCs");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                siglas.add(rs.getString("Sigla"));
            } 

        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) { 
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return siglas;
    }

    @Override
    public Collection<UC> values() {
        Set<UC> set = new HashSet<>();
        Set<String> keys = new HashSet<>(this.keySet());
        for(String key : keys){
            set.add(this.get(key));
        }
        return set; 
    }

    @Override
    public Set<Entry<String, UC>> entrySet() {
        Set<String> keys = new HashSet<>(this.keySet());
        
        HashMap<String,UC> map = new HashMap<>();
        for(String key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }
    
    public void removeDocente(String username, String sigla){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM DocentesTemUCs\n" +
                                                        "WHERE UC_Sigla = ? AND Docente_Username = ?");
            ps.setString(1, sigla);
            ps.setString(2, username);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void adicionaUCs(String alunoUsername, List<UC> ucs){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("INSERT INTO AlunosTemUCs (Aluno_Username,UC_Sigla)\n" +
                                                        "VALUES (?,?)\n" +
                                                        "ON DUPLICATE KEY UPDATE Aluno_Username=VALUES(Aluno_Username),UC_Sigla=VALUES(UC_Sigla)", Statement.RETURN_GENERATED_KEYS);
            for(UC u: ucs){
                ps.setString(1, alunoUsername);
                ps.setString(2, u.getSigla());
                ps.executeUpdate(); 
            }

        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void remInscricaoAluno(String sigla, String username){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM AlunosTemUCs\n" +
                                                        "WHERE UC_Sigla = ? AND Aluno_Username = ?");
            ps.setString(1, sigla);
            ps.setString(2, username);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void addInscricaoAluno(String sigla, String username){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("INSERT INTO AlunosTemUCs (Aluno_Username,UC_Sigla)\n" +
                                                        "VALUES (?,?)\n" +
                                                        "ON DUPLICATE KEY UPDATE Aluno_Username=VALUES(Aluno_Username),UC_Sigla=VALUES(UC_Sigla)", Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, username);
            ps.setString(2, sigla);
            ps.executeUpdate(); 


        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(UCDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
}
