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
import java.util.logging.Level;
import java.util.logging.Logger;
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
                Conexion.EjecutarConsulta("Update Usuario set FechaIngreso=NOW() where User='" + usuario[0] + "'");
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
            String consulta = "SELECT I.Codigo,I.CodMat,I.Material,I.PrecioCompra,I.PrecioVenta,Q.CSaldo,Unidad,Fecha"
                    + " From Inventario I inner join Deposito D on I.Codigo = D.Codigo inner join DepositoTotal Q on I.Codigo=Q.Codigo"
                    + " ";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE I.Material LIKE '" + textoBusqueda + "%' or I.Codigo LIKE '" + textoBusqueda + "%' OR I.CodMat LIKE '" + textoBusqueda + "%'";
            }
            consulta += " Group by I.Codigo";
//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Codigo", "Codigo Material", "Articulo", "P/Compra", "P/Venta", "En Almacen", "Unidad", "F/Registro"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][8];

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
                    datos[i][7] = resultado.getString(8);

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

    public DefaultTableModel getListaUsuarios(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            String consulta = "SELECT User,NombreUsuario,Cargo,FechaIngreso"
                    + " From Usuario";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE NombreUsuario LIKE '" + textoBusqueda + "%'";
            }

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Nombre del Usuario", "Login", "Cargo de Usuario", "Ultimo Ingreso"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][4];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    datos[i][2] = resultado.getString(3);
                    datos[i][3] = resultado.getString(4);

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

    public DefaultTableModel getListaAyudante(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            String consulta = "SELECT Id,Nombre,Estado "
                    + " From Ayudante";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE Nombre LIKE '" + textoBusqueda + "%'";
            }

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"ID Ayudante", "Nombre de Ayudante", "Estado"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][3];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    if (resultado.getString(3).equals("H")) {
                        datos[i][2] = "Habilitado";
                    } else {
                        datos[i][2] = "Deshabilitado";
                    }
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

    public DefaultTableModel getListaProveedor(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            String consulta = "SELECT Id,Nombre,Telefono,Direccion,Detalles"
                    + " From Proveedor";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE Nombre LIKE '" + textoBusqueda + "%' OR Detalles LIKE '" + textoBusqueda + "%'";
            }

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Id", "Nombre Proveedor", "Telefono", "Direccion", "Detalles"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][5];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    datos[i][2] = resultado.getString(3);
                    datos[i][3] = resultado.getString(4);
                    datos[i][4] = resultado.getString(5);

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

    public DefaultTableModel getListaVehiculo(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            String consulta = "SELECT Placa,Modelo,Color,NombreCliente"
                    + " From Vehiculo";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE NombreCliente LIKE '" + textoBusqueda + "%' OR Placa LIKE '" + textoBusqueda + "%'";
            }

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Placa", "Modelo ", "Color", "Nombre de Cliente"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][4];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    datos[i][2] = resultado.getString(3);
                    datos[i][3] = resultado.getString(4);

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

    public DefaultTableModel getListaIngresoVehiculo(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            String consulta = "SELECT I.Id,I.Placa,I.FechaIngreso,I.FechaSalida,A.Nombre "
                    + " From IngresoVehiculo I inner join Ayudante A on I.PC=A.Id WHERE I.FechaSalida1='null' ";

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Nro Registro", "Placa", "F/Ingreso", "F/Salida", "Ayudante"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][5];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    datos[i][2] = resultado.getString(3);
                    datos[i][3] = resultado.getString(4);
                    datos[i][4] = resultado.getString(5);

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

    public DefaultTableModel getListaIngresoVehiculoDetalles(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            String consulta = "SELECT I.Id,I.Placa,I.FechaIngreso,I.FechaSalida,A.Nombre "
                    + " From IngresoVehiculo I inner join Ayudante A on I.PC=A.Id ";
            if (!textoBusqueda.isEmpty()) {
                consulta += " Where Placa LIKE '" + textoBusqueda + "%' OR Nombre LIKE '" + textoBusqueda + "%'";
            }

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Nro Registro", "Placa", "F/Ingreso", "F/Salida", "Ayudante"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][5];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    datos[i][2] = resultado.getString(3);
                    datos[i][3] = resultado.getString(4);
                    datos[i][4] = resultado.getString(5);

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

    public DefaultTableModel getListaUsuariosDetalles(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            String consulta = "SELECT NombreUsuario,U.User,Cargo,B.FechaIngreso"
                    + " From Usuario U inner join Bitacora B on U.User=B.User";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE NombreUsuario LIKE '" + textoBusqueda + "%'";
            }

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Nombre del Usuario", "Login", "Cargo de Usuario", "Fechas de Ingreso"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][4];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    datos[i][2] = resultado.getString(3);
                    datos[i][3] = resultado.getString(4);

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

    public DefaultTableModel getListaDetalles(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            String consulta = "SELECT I.Codigo,I.CodMat,I.Material,D.CEntrada,D.CSalida,Unidad,Fecha"
                    + " From Inventario I inner join Deposito D on I.Codigo = D.Codigo ";
            if (!textoBusqueda.isEmpty()) {
                consulta += " WHERE  I.Codigo ='" + textoBusqueda + "'";
            }

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Codigo", "Codigo Material", "Articulo", "Entrada", "Salida", "Unidad", "F/Registro"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][8];

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

    public DefaultTableModel getListaBajos(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            String consulta = "SELECT I.Codigo,I.CodMat,I.Material,D.CSaldo,Unidad"
                    + " From Inventario I inner join DepositoTotal D on I.Codigo = D.Codigo where D.CSaldo BETWEEN 0 and 30 ";
            if (!textoBusqueda.isEmpty()) {
                consulta += " AND  I.Codigo ='" + textoBusqueda + "'";
            }

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);

            // Se crea el array de columnas
            String[] columnas = {"Codigo", "Codigo Material", "Articulo", "Cantidad", "Unidad"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][8];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i][0] = resultado.getString(1);
                    datos[i][1] = resultado.getString(2);
                    datos[i][2] = resultado.getString(3);
                    datos[i][3] = resultado.getString(4);
                    datos[i][4] = resultado.getString(5);

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

    public DefaultTableModel getEntrega(String textoBusqueda) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            String consulta = "SELECT E.Id,I.Codigo,I.Material,I.PrecioVenta,E.Cantidad,I.PrecioVenta*E.Cantidad,E.Descuento From EntregaMateriales E inner join Inventario I on E.Codigo=I.Codigo WHERE  E.IdV =" + textoBusqueda;

//            System.out.println(consulta);
            ResultSet resultado = Conexion.getDatos(consulta);
            float Total1 = 0, Descuento=0, Total2;
            // Se crea el array de columnas
            String[] columnas = {"Nro Registro", "Codigo Material", "Articulo", "P/Venta", "Cantidad", " Total"};

            resultado.last();
            Total = resultado.getRow();
            //Se crea una matriz con tantas filas y columnas que necesite
            Object[][] datos = new String[Total][6];

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
                    //datos[i][6] = resultado.getString(7);
                    Total1 += Float.parseFloat(resultado.getString(6));
                    Descuento = Float.parseFloat(resultado.getString(7));

                    i++;
                } while (resultado.next());
            }
            Total2 = Total1 - Descuento;
            resultado.close();
            modeloTabla.setDataVector(datos, columnas);
            modeloTabla.addRow(new Object[]{"-", "-", "-", "-", "-", "-"});
            modeloTabla.addRow(new Object[]{"TOTAL ", String.valueOf(Total1), "DESCUENTO", String.valueOf(Descuento), "PAGO FINAL", String.valueOf(Total2)});

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return modeloTabla;
    }

    public String Backup() {
        String Tabla[] = {"Usuario", "Vehiculo", "Inventario", "Deposito", "DepositoTotal", "Ayudante", "Venta", "IngresoVehiculo", "EntregaMateriales", "Bitacora", "Proveedor"};

        String Copia = "";

        for (int x = 0; x < 11; x++) {
            String consulta = "Select * From ";
            if (Tabla[x].equals("Usuario")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into Usuario(User,NombreUsuario,FechaIngreso,Cargo) values('" + rs.getString("User") + "','"
                                + rs.getString("NombreUsuario") + "','" + rs.getString("FechaIngreso") + "','" + rs.getString("Cargo") + "');";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Usuario");
                }
            }
            if (Tabla[x].equals("Bitacora")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into Bitacora(User,FechaIngreso) values('" + rs.getString("User") + "','" + rs.getString("FechaIngreso") + "');";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Bitacora");
                }
            }
            if (Tabla[x].equals("Proveedor")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into Proveedor(Nombre,Telefono,Direccion,Detalles) values('" + rs.getString("Nombre") + "','" + rs.getString("Telefono") + "','"
                                + rs.getString("Direccion") + "','" + rs.getString("Detalles") + "');";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Proveedor");
                }
            }
            if (Tabla[x].equals("Vehiculo")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into Vehiculo(Placa,Modelo,Color,NombreCliente) values('" + rs.getString("Placa") + "','" + rs.getString("Modelo") + "','"
                                + rs.getString("Color") + "','" + rs.getString("NombreCliente") + "');";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Vehiculo" + ex);
                }
            }
            if (Tabla[x].equals("Ayudante")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into Ayudante(Id,Nombre,Estado) values('" + rs.getString("Id") + "','" + rs.getString("Nombre") + "','"
                                + rs.getString("Estado") + "');";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Ayudante");
                }
            }
            if (Tabla[x].equals("IngresoVehiculo")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into IngresoVehiculo(Id,Placa,PC,FechaIngreso,FechaSalida) values(" + rs.getInt("Id") + ",'" + rs.getString("Placa") + "'," + rs.getInt("PC") + ",'"
                                + rs.getString("FechaIngreso") + "','" + rs.getString("FechaSalida") + "');";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Vehiculo2");
                }
            }
            if (Tabla[x].equals("Inventario")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into Inventario(Codigo,CodMat,Material,PrecioCompra,PrecioVenta,Unidad) values('" + rs.getString("Codigo") + "','" + rs.getString("CodMat") + "','" + rs.getString("Material") + "'," + rs.getFloat("PrecioCompra") + ","
                                + rs.getFloat("PrecioVenta") + ",'" + rs.getString("Unidad") + "');";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Inventario");
                }
            }
            if (Tabla[x].equals("Deposito")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into Deposito(Codigo,CEntrada,CSalida,Fecha) values('" + rs.getString("Codigo") + "'," + rs.getFloat("CEntrada") + ","
                                + rs.getFloat("CSalida") + ",'" + rs.getString("Fecha") + "');";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Deposito");
                }
            }
            if (Tabla[x].equals("DepositoTotal")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into DepositoTotal(Codigo,CEntrada,CSalida,CSaldo) values('" + rs.getString("Codigo") + "'," + rs.getFloat("CEntrada") + ","
                                + rs.getFloat("CSalida") + "," + rs.getString("CSaldo") + ");";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Deposito2");
                }
            }
            if (Tabla[x].equals("EntregaMateriales")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into EntregaMateriales(Codigo,Id,Cantidad,PrecioVenta,Fecha,User,Descuento) values('" + rs.getString("Codigo") + "'," + rs.getInt("Id") + "," + rs.getFloat("Cantidad") + "," + rs.getFloat("PrecioVenta") + ",'"
                                + rs.getString("Fecha") + "','" + rs.getString("User") + "'," + rs.getFloat("Descuento") + ");";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en Entrega");
                }

            }
            if (Tabla[x].equals("Venta")) {
                try {
                    consulta += Tabla[x];
                    ResultSet rs = Conexion.getDatos(consulta);
                    while (rs.next()) {
                        Copia += "\n Insert into Venta(Codigo,Cantidad,PrecioVenta,FechaCancelacion,User,Nombre,Descuento) values('" + rs.getString("Codigo") + "'," + rs.getFloat("Cantidad") + "," + rs.getFloat("PrecioVenta") + ",'" + rs.getString("FechaCancelacion") + "','"
                                + rs.getString("User") + "','" + rs.getString("Nombre") + "'," + rs.getFloat("Descuento") + ");";
                    }
                } catch (SQLException ex) {
                    System.out.println("Error en venta");
                }
            }
        }
        Conexion.Desconectar();
        return Copia;
    }

    public int getTotal() {
        return Total;
    }

    ////////Insertar nuevo Articulo
    public boolean InsertarArticulo(String Codigo, String Codigo1, String Material, String PC, String PV, String Unidad, String Cantidad, int x) {
        int c = 0;
        String consulta = "";

        if (x == 1) {
            consulta = "CALL ModificarMaterial('" + Codigo + "','" + Codigo1 + "','" + Material + "'," + PC + "," + PV + ",'" + Unidad + "'," + Cantidad + ")";
        }
        if (x == 0) {
            consulta = "CALL InsertarNuevoMaterial('" + Codigo + "','" + Codigo1 + "','" + Material + "'," + PC + "," + PV + ",'" + Unidad + "'," + Cantidad + ")";
        }
        if (x == 2) {
            consulta = " CALL InsertarDeposito('" + Codigo + "'," + Cantidad + "," + c + ")";
        }
        if (x == 3) {
            consulta = " CALL InsertarDeposito('" + Codigo + "'," + c + "," + Cantidad + ")";
        }

        if (Conexion.EjecutarConsulta(consulta)) {

            return true;
        } else {
            return false;
        }

    }

    public String getCodigo() {
        String cod = "";
        try {

            ResultSet rs = Conexion.getDatos("Select * from Inventario");
            if (rs.last()) {
                cod = rs.getString("Codigo");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cod;
    }

    public String[] getArticulo(String Codigo) {
        String datos[] = {"", "", "", "", "", ""};
        String consulta = "Select * From Inventario I inner join DepositoTotal D on D.Codigo =I.Codigo where I.Codigo='" + Codigo + "'";
        try {
            ResultSet rs = Conexion.getDatos(consulta);
            if (rs.last()) {
                datos[0] = rs.getString("Material");
                datos[1] = rs.getString("PrecioCompra");
                datos[2] = rs.getString("PrecioVenta");
                datos[3] = rs.getString("Unidad");
                datos[4] = rs.getString("CSaldo");
                datos[5] = rs.getString("CodMat");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }

    //           INSERTAR NUEVO USUARIO
    public boolean InsertarUsuario(String login, String Nombre, String Contrasenia, String Cargo, boolean condicion) {
        String consulta = "";
        if (condicion) {
            consulta = "Insert into Usuario(User,NombreUsuario,Password,Cargo,FechaIngreso) values "
                    + "('" + login + "','" + Nombre + "',SHA1('" + Contrasenia + "'),'" + Cargo + "',NOW())";
        } else {
            consulta = "UPDATE Usuario SET NombreUsuario='" + Nombre + "', Password=SHA1('" + Contrasenia + "'), Cargo='" + Cargo + "' where User ='" + login + "'";
        }

        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    //          INSERTAR NUEVO AYUDANTE
    public boolean ModificarAyudante(int Id, String Nombre, String Estado) {
        String consulta = "";
        consulta = "CALL ModificarAyudante(" + Id + ",'" + Nombre + "','" + Estado + "')";

        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean NuevoAyudante(String Nombre, String Estado) {
        String consulta = "Insert Into Ayudante(Nombre,Estado)Values('" + Nombre + "','" + Estado + "')";
        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarAyudante(int id) {
        String consulta = "Delete From Ayudante where Id=" + id;
        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean NuevoVehiculo(String NombreCliente, String Placa, String Modelo, String Color) {
        String consulta = "Insert Into Vehiculo(NombreCliente,Placa,Modelo,Color)Values('" + NombreCliente + "','" + Placa + "','" + Modelo + "','" + Color + "')";
        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ModificarVehiculo(String NombreCliente, String Placa, String Modelo, String Color) {
        String consulta = "UPDATE Vehiculo SET NombreCliente='" + NombreCliente + "',Modelo='" + Modelo + "',Color='" + Color + "' where Placa='" + Placa + "'";
        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarVehiculo(String Placa) {
        String consulta = "Delete From Vehiculo where Placa='" + Placa + "'";
        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean NuevoIngresoVehiculo(String fecha, String Placa, String Ayudante) {
        String consulta = "CALL IngresoVehiculo('" + Placa + "','" + Ayudante + "','" + fecha + "')";
        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ModificarIngresoVehiculo(String Placa, String Ayudante, String id) {
        String consulta = "CALL ModificarIngresoVehiculo('" + Placa + "','" + Ayudante + "'," + id + ")";
        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EntregaMateriales(String Codigo, String Id, String Cantidad, String User, String Descuento, String PV) {
        String consulta = "CALL Entrega('" + Codigo + "'," + Id + "," + Cantidad + ",'" + User + "'," + Descuento + "," + PV + ")";
        if (Conexion.EjecutarConsulta(consulta)) {
            return true;
        } else {
            return false;
        }
    }

    public String[] getAyudantes() {
        try {
            String consulta = "SELECT * FROM Ayudante Where Estado='H'";
            ResultSet resultado = Conexion.getDatos(consulta);

            resultado.last();
            //Se crea una matriz con tantas filas y columnas que necesite
            String[] datos = new String[resultado.getRow()];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i] = resultado.getString("Nombre");
                    i++;
                } while (resultado.next());
            }
            resultado.close();
            return datos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String[] getPagarTotal() {
        try {
            String consulta = "SELECT * FROM Ayudante Where Estado='H'";
            ResultSet resultado = Conexion.getDatos(consulta);

            resultado.last();
            //Se crea una matriz con tantas filas y columnas que necesite
            String[] datos = new String[resultado.getRow()];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i] = resultado.getString("Nombre");
                    i++;
                } while (resultado.next());
            }
            resultado.close();
            return datos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String[] getPlaca(String txtBusqueda) {
        try {
            String consulta = "SELECT * FROM Vehiculo";

            if (!txtBusqueda.isEmpty()) {
                consulta += " WHERE Placa LIKE '" + txtBusqueda + "%'";
            }
            ResultSet resultado = Conexion.getDatos(consulta);

            resultado.last();
            //Se crea una matriz con tantas filas y columnas que necesite
            String[] datos = new String[resultado.getRow()];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i] = resultado.getString("Placa");
                    i++;
                } while (resultado.next());
            }
            resultado.close();
            return datos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String[] getMaterial(String txtBusqueda) {
        try {
            String consulta = "SELECT * FROM Inventario";

            if (!txtBusqueda.isEmpty()) {
                consulta += " WHERE Codigo LIKE '" + txtBusqueda + "%' OR CodMat LIKE '" + txtBusqueda + "%'";
            }
            ResultSet resultado = Conexion.getDatos(consulta);

            resultado.last();
            //Se crea una matriz con tantas filas y columnas que necesite
            String[] datos = new String[resultado.getRow()];

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos[i] = resultado.getString("Material");
                    i++;
                } while (resultado.next());
            }
            resultado.close();
            return datos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String getCodigo(String txtBusqueda) {
        try {
            String consulta = "SELECT * FROM Inventario Where Material='" + txtBusqueda + "'";

            ResultSet resultado = Conexion.getDatos(consulta);

            resultado.last();
            //Se crea una matriz con tantas filas y columnas que necesite
            String datos = new String();

            if (resultado.getRow() > 0) {
                resultado.first();
                int i = 0;
                do {
                    datos = resultado.getString("Codigo");
                    i++;
                } while (resultado.next());
            }
            resultado.close();
            return datos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String[] getMaterialDetalles(String Codigo) {
        String datos[] = {"", "", "", ""};
        String consulta = "Select * From Inventario I inner join DepositoTotal D on D.Codigo =I.Codigo where I.Codigo='" + Codigo + "'";
        try {
            ResultSet rs = Conexion.getDatos(consulta);
            if (rs.last()) {

                datos[0] = rs.getString("PrecioCompra");
                datos[1] = rs.getString("PrecioVenta");
                datos[2] = rs.getString("Unidad");
                datos[3] = rs.getString("CSaldo");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }

}
