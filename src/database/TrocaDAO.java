/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import business.Aluno;
import business.Troca;
import business.Turno;
import business.UC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diogoleitao
 */
public class TrocaDAO {
    
    private Connection con;
    
    public List<Troca> list(Turno turno){
        ArrayList<Troca> res = new ArrayList<Troca>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Trocas t\n"
                                                      + "WHERE t.Turno_Codigo = ?");
            ps.setString(1, turno.getCodigo());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                Troca t = new Troca();
                t.setData(LocalDate.parse(rs.getString("Data")));
                AlunoDAO a = new AlunoDAO();
                Aluno aluno = a.getAluno(rs.getString("Aluno_Username"));
                t.setAluno(aluno);
                
                TurnoDAO td = new TurnoDAO();
                Turno atual = td.getTurno(rs.getString("TurnosAtual"));
                t.setTurnoAtual(atual);

                res.add(t);
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(TurnoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void add(Troca t, String codigo){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("INSERT INTO TROCAS (Data, Turno_Codigo, Aluno_Username, TurnosAtual)\n"
                                                      + "VALUES (?,?,?,?)\n"
                                                      + "ON DUPLICATE KEY UPDATE Data=VALUES(Data),Turno_Codigo=VALUES(Turno_Codigo),Aluno_Username=VALUES(Aluno_Username),TurnosAtual=VALUES(TurnosAtual)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getData().toString());
            ps.setString(2, codigo);
            ps.setString(3, t.getAluno().getUsername());
            ps.setString(4, t.getTurnoAtual().getCodigo());
            ps.executeUpdate();
           
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(TurnoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void remove(String username, String codigo){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Trocas \n" +
                                                        "WHERE Aluno_Username = ? AND TurnosAtual = ?\n");
                                                     
            ps.setString(1, username);
            ps.setString(2, codigo);
            ps.executeUpdate();
           
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(TurnoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
