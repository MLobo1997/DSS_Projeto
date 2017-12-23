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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Faltas f"
                                                      + "WHERE f.Turno_Codigo = ?");
            ps.setString(1, t.getCodigo());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                Falta f = new Falta();
                f.setData(toGCalendar(rs.getString("Dia")));
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
    
    public void set(List<Falta> faltas){
        
    }
    

    public String toSQLDate(GregorianCalendar date) {
        StringBuilder sb = new StringBuilder();
        sb.append(date.get(Calendar.YEAR)).append("-");
        sb.append(date.get(Calendar.MONTH)).append("-");
        sb.append(date.get(Calendar.DAY_OF_MONTH));
        return sb.toString();
    }
    

    public GregorianCalendar toGCalendar(String date) {
        int ano, mes, dia;
        String toks[] = date.split("[- ]");
        ano = Integer.parseInt(toks[0].trim());
        mes = Integer.parseInt(toks[1].trim());
        dia = Integer.parseInt(toks[2].trim());
        return new GregorianCalendar(ano,mes,dia);      
    }
}
