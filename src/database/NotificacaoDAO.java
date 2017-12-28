/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import business.Aluno;
import business.Falta;
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
public class NotificacaoDAO {
    
    Connection con;
    
    
    public List<String> list(Aluno a){
        ArrayList<String> res = new ArrayList<String>();
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Notificacoes\n"
                                                      + "WHERE Alunos_Username = ?");
            ps.setString(1, a.getUsername());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                res.add(rs.getString("Texto"));
            }

        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(NotificacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
}
