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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
public class FaltaDAO {
    
    private Connection con;
    
    public List<Falta> list(Turno t){
        ArrayList<Falta> res = new ArrayList<Falta>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Faltas f\n"
                                                      + "WHERE f.Turno_Codigo = ?");
            ps.setString(1, t.getCodigo());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                Falta f = new Falta();
                f.setData(LocalDate.parse(rs.getString("Dia")));
                String AlunoUsername = rs.getString("Aluno_Username");
                
                AlunoDAO d = new AlunoDAO();
                f.setAluno(d.getAluno(AlunoUsername));
                
                res.add(f);
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(FaltaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void set(List<Falta> faltas, String turnoCodigo){
        try{
            con = Connect.connect();            
            PreparedStatement ps = con.prepareStatement("INSERT INTO Faltas (Turno_Codigo,Aluno_Username,Dia)\n" +
                                                        "VALUES (?,?,?)\n" +
                                                        "ON DUPLICATE KEY UPDATE Turno_Codigo=VALUES(Turno_Codigo),Aluno_Username=VALUES(Aluno_Username),Dia=VALUES(Dia)");
            for(Falta f: faltas){
                ps.setString(1, turnoCodigo);
                ps.setString(2, f.getAluno().getUsername());
                ps.setString(3, f.getData().toString());
                ps.executeUpdate(); 
            }
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(FaltaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
