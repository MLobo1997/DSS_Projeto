/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import business.Aluno;
import business.Docente;
import business.Turno;
import business.UC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diogoleitao
 */
public class AlunoDAO {

    private Connection con;
    
    public List<Aluno> list(Turno t){
        ArrayList<Aluno> res = new ArrayList<Aluno>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT  * FROM Alunos a\n" +
                                                        "INNER JOIN AlunosTemTurnos att ON a.Username = att.Aluno_Username\n" +
                                                        "WHERE att.Turno_Codigo = ?");
            ps.setString(1, t.getCodigo());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                Aluno a = new Aluno();
                a.setNome(rs.getString("Nome"));
                a.setEmail(rs.getString("Email"));
                a.setUsername(rs.getString("Username"));
                a.setPassword(rs.getString("Password"));
                a.setEstatuto(rs.getString("Estatuto"));
                a.setAno(rs.getInt("Ano"));
                
                res.add(a);
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Aluno getAluno(String username){
        Aluno al = null;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Alunos WHERE Username = ?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Aluno a = new Aluno();
                a.setNome(rs.getString("Nome"));
                a.setEmail(rs.getString("Email"));
                a.setUsername(rs.getString("Username"));
                a.setPassword(rs.getString("Password"));
                a.setEstatuto(rs.getString("Estatuto"));
                a.setAno(rs.getInt("Ano"));
                
                al = a;  
            }
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 

        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                 System.out.printf(e.getMessage());
            }
        }
        return al;
    }
    
}
