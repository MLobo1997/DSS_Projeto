/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import business.Falta;
import business.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diogoleitao
 */
public class InfoLocaisDAO {
    
    private Connection con;
    
    public void novo(){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("INSERT INTO InfoLocais (Fase, Semestre) VALUES (1,1)");
            ps.executeUpdate();
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoLocaisDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public int size(){
        int size = 0;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM InfoLocais");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){ 
                size = rs.getInt(1);
            }
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoLocaisDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public int getSemestre(){
        int res = 0;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM InfoLocais");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                res = rs.getInt("Semestre");
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoLocaisDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public int getFase(){
        int res = 0;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM InfoLocais");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                res = rs.getInt("Fase");
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoLocaisDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void setFase(int fase){
        try{
        con = Connect.connect();            
        PreparedStatement ps = con.prepareStatement("UPDATE InfoLocais SET Fase = ?");
                                       
        ps.setInt(1, fase);
        ps.executeUpdate(); 
                

        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoLocaisDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void setSemestre(int semestre){
        try{
        con = Connect.connect();            
        PreparedStatement ps = con.prepareStatement("UPDATE InfoLocais SET Semestre = ?");
                                       
        ps.setInt(1, semestre);
        ps.executeUpdate(); 
                

        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoLocaisDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void reset(){//Limpa a BD
        try{
        con = Connect.connect();            
        PreparedStatement ps = con.prepareStatement("DELETE FROM UCs;");                         
        ps.executeUpdate(); 
        ps = con.prepareStatement("DELETE FROM Alunos;");                         
        ps.executeUpdate();         
        ps = con.prepareStatement("DELETE FROM Docentes;");                         
        ps.executeUpdate();   
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoLocaisDAO.class.getName()).log(Level.SEVERE, null, ex);
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
