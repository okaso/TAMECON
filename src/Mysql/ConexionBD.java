 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mysql;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class ConexionBD {

    private Connection Conexion = null;

    public ConexionBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/TAMECON";
            String User = "root";
            String Password = "kmoprsuwxz1";
            Conexion = (Connection) DriverManager.getConnection(url, User, Password);
            System.out.println("Exito");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConection() {
        return Conexion;
    }

    public void Desconectar() {
        Conexion = null;
    }

    public boolean EjecutarConsulta(String consulta) {
        try {
            PreparedStatement preparacionConsulta = (PreparedStatement) this.getConection().prepareStatement(consulta);
            preparacionConsulta.execute();
            preparacionConsulta.close();
            System.out.println("Consulta Ejecutada");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ResultSet getDatos(String Consulta) {
        try {
            PreparedStatement preparacionConsulta = (PreparedStatement) this.getConection().prepareStatement(Consulta);
            return preparacionConsulta.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
