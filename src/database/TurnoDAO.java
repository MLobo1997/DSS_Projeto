/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import business.Aluno;
import business.Turno;
import business.UC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diogoleitao
 */
public class TurnoDAO {

    private Connection con;
    
    // Implementacao lazy. Faltas, trocas e alunos só são carregados quando são precisoos
    public List<Turno> list(UC u){
        ArrayList<Turno> res = new ArrayList<Turno>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Turnos t"
                                                      + "WHERE t.UC_Sigla = ?");
            ps.setString(1, u.getSigla());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                Turno t = new Turno();
                t.setCodigo(rs.getString("Codigo"));
                t.setCapacidade(rs.getInt("Capacidade"));
                t.setTipo(rs.getString("Tipo"));
                t.setHora(rs.getString("Hora"));
                t.setDiaSem(rs.getString("DiaSemana"));
                String DocUsername = rs.getString("Docente_Username");
                
                DocenteDAO d = new DocenteDAO();
                t.setDocente(d.getDocente(DocUsername));
                
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
    
    public Turno getTurno(String codigoTurno){
        Turno tr = null;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Turnos WHERE Codigo = ?");
            ps.setString(1,codigoTurno);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Turno t = new Turno();
                t.setCodigo(rs.getString("Codigo")); 
                t.setCapacidade(rs.getInt("Capacidade"));
                t.setTipo(rs.getString("Tipo"));
                t.setDiaSem(rs.getString("DiaSemana"));
                t.setHora(rs.getString("Hora"));
                
                DocenteDAO d = new DocenteDAO();
                t.setDocente(d.getDocente(rs.getString("Docente_Username")));
                
                tr = t;  
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
        return tr;                   
    }
    
    public void add(List<Turno> turnos){ //TODO:::::::::::::::::::::::
        
    }
    
    
}
