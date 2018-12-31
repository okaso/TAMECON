/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

import Mysql.ConexionBD;
import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ConsultaGlobal {

    ConexionBD Conexion;
    int Total;

    public ConsultaGlobal() {
        Conexion = new ConexionBD();

    }

    public String[] getAcceso(String login, String contrasenia) {
        String[] usuario = {"", "", "", ""};
        try {
            ResultSet resultado = Conexion.getDatos("SELECT * FROM Usuario WHERE User = '" + login + "' AND Password=SHA1('" + contrasenia + "')");
            if (resultado.next()) {
                usuario[0] = resultado.getString("User");
                usuario[1] = resultado.getString("NombreUsuario");
                usuario[2] = resultado.getString("Cargo");
                String url = "Insert Into Bitacora(User,FechaIngreso) values('" + usuario[0] + "',NOW())";

                Conexion.EjecutarConsulta(url);
            }
            ResultSet rs = Conexion.getDatos("Select * From Usuario U inner join Bitacora B on U.User=B.User where U.User ='" + login + "'");
            if (rs.last()) {
                usuario[3] = rs.getString("FechaIngreso");
            }

        } catch (SQLException e) {
            System.err.println(e);
        }
        return usuario;
    }
    /*                       Cagar Datos de La LIsta De Articulos en Manager         */
    public DefaultTableModel getLista(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            String consulta = "SELECT I.Codigo,I.Material,I.PrecioCompra,I.PrecioVenta,Sum(D.CEntrada)-Sum(D.CSalida),Unidad,Fecha"
                    + " From Inventario I inner join Deposito D on I.Codigo = D.Codigo"
                    + " Group by I.Codigo";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE P.nombre LIKE '%" + textoBusqueda + "%' OR P.docente LIKE '%" + textoBusqueda + "%' OR P.descripcion LIKE '%" + textoBusqueda + "%' OR C.nombre LIKE '%" + textoBusqueda + "%'";
            }
//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Codigo", "Articulo", "P/Compra", "P/Venta", "En Almacen", "Unidad", "F/Registro"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][7];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    datos[i][2] = resultado.getString(3);
                    datos[i][3] = resultado.getString(4);
                    datos[i][4] = resultado.getString(5);
                    datos[i][5] = resultado.getString(6);
                    datos[i][6] = resultado.getString(7);
//                    datos[i][6] = resultado.getString("categoria");
                    i++;
                } while (resultado.next());
            }
            resultado.close();
            modeloTabla.setDataVector(datos, columnas);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return modeloTabla;
    }

    public int getTotal() {
        return Total;
    }

}
