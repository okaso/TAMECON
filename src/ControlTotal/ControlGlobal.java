/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlTotal;

import Consultas.ConsultaGlobal;
import Interfaces.*;
import Interfaces.Login;
import Interfaces.Registros.RegistroArticulo;
import Interfaces.Registros.*;
import Mysql.CopiaSeguridad;
import Paneles.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ControlGlobal implements ActionListener, KeyListener {

    Login login;
    ConsultaGlobal CG;
    InterfazManager IM;
    RegistroArticulo RA;

    //PANELES DE CONTROL O LISTAS 
    PanelMateriales PM;
    PanelAyudantes PA;
    PanelIngresoVehiculo PIV;
    PanelProveedores PP;
    PanelUsuarios PU;
    PanelVehiculos PV;

    //   INTERFACES DE REGISTROS
    RegistroUsuario RUsuario;
    RegistroArticulo RArticulo;
    RegistroAyudante RAyudante;
    RegistroIngresoVehiculo RIngresoVehiculo;
    RegistroVehiculo RVehiculo;
    RegistroVentaArticulos RVenta;
    Backup PB;
    JDialog Dialogo;

    public ControlGlobal() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterfazManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InterfazManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InterfazManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(InterfazManager.class.getName()).log(Level.SEVERE, null, ex);
        }


        /*Iniciando COnexion Con la Base De Datos*/
        CG = new ConsultaGlobal();

        login = new Login();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        /*Objetos de interfaz login*/
        login.BtnIngresar().addActionListener((ActionListener) this);
        login.TxtPassword().addKeyListener((KeyListener) this);
        login.TxtUsuario().addKeyListener((KeyListener) this);

        /*           Interfaz Principal  Iniciando JMenuItem             */
        IM = new InterfazManager();
        IM.setLocationRelativeTo(null);
        IM.ItemAyudantes().addActionListener((ActionListener) this);
        IM.ItemBackup().addActionListener((ActionListener) this);
        IM.ItemEntregas().addActionListener((ActionListener) this);
        IM.ItemIngresoVehiculos().addActionListener((ActionListener) this);
        IM.ItemMateriales().addActionListener((ActionListener) this);
        IM.ItemProveedor().addActionListener((ActionListener) this);
        IM.ItemSesion().addActionListener((ActionListener) this);
        IM.ItemUsuarios().addActionListener((ActionListener) this);
        IM.ItemVehiculos().addActionListener((ActionListener) this);
        IM.ItemVentas().addActionListener((ActionListener) this);

        /*              Iniciando Panel Materiales                 */
        PM = new PanelMateriales();
        PM.BtnBuscar().addActionListener((ActionListener) this);
        PM.BtnEditarMaterial().addActionListener((ActionListener) this);
        PM.BtnInventario().addActionListener((ActionListener) this);
        PM.BtnMaterialBajo().addActionListener((ActionListener) this);
        PM.TxtBusqueda().addKeyListener((KeyListener) this);

        //          PANEL REGISTRO NUEVO USUARIO
        PU = new PanelUsuarios();
        PU.BtnActualizar().addActionListener((ActionListener) this);
        PU.BtnBuscar().addActionListener((ActionListener) this);
        PU.BtnEditarMaterial().addActionListener((ActionListener) this);

        PU.BtnNuevo().addActionListener((ActionListener) this);
        PU.TxtBusqueda().addKeyListener((KeyListener) this);

        //            PANEL REGISTRO AYUDANTES 
        PA = new PanelAyudantes();
        PA.BtnActualizar().addActionListener((ActionListener) this);
        PA.BtnBuscar().addActionListener((ActionListener) this);
        PA.BtnEditarMaterial().addActionListener((ActionListener) this);
        PA.BtnEliminar().addActionListener((ActionListener) this);
        PA.BtnNuevo().addActionListener((ActionListener) this);
        PA.TxtBusqueda().addKeyListener((KeyListener) this);

        //              PANEL INGRESO VEHICULO AL TALLER
        PIV = new PanelIngresoVehiculo();
        PIV.BtnActualizar().addActionListener((ActionListener) this);
        PIV.BtnBuscar().addActionListener((ActionListener) this);
        PIV.BtnEditarMaterial().addActionListener((ActionListener) this);
        PIV.BtnEliminar().addActionListener((ActionListener) this);
        PIV.BtnNuevo().addActionListener((ActionListener) this);
        PIV.TxtBusqueda().addKeyListener((KeyListener) this);

        //              PANEL REGISTRO PROVEEDORES
        PP = new PanelProveedores();
        PP.BtnActualizar().addActionListener((ActionListener) this);
        PP.BtnBuscar().addActionListener((ActionListener) this);
        PP.BtnEditarMaterial().addActionListener((ActionListener) this);
        PP.BtnEliminar().addActionListener((ActionListener) this);
        PP.BtnNuevo().addActionListener((ActionListener) this);
        PP.TxtBusqueda().addKeyListener((KeyListener) this);

        //              PANEL REGISTRO VEHICULOS
        PV = new PanelVehiculos();
        PV.BtnActualizar().addActionListener((ActionListener) this);
        PV.BtnBuscar().addActionListener((ActionListener) this);
        PV.BtnEditarMaterial().addActionListener((ActionListener) this);
        PV.BtnEliminar().addActionListener((ActionListener) this);
        PV.BtnNuevo().addActionListener((ActionListener) this);
        PV.TxtBusqueda().addKeyListener((KeyListener) this);

        //              PANEL COPIA DE SEGURIDAD
        PB = new Backup();
        //PB.Guardar().addActionListener((ActionListener) this);

        //              INTERFACES DE REGISTROS
        //              REGISTRO DE USUARIOS
        RUsuario = new RegistroUsuario();
        RUsuario.BtnAgregar().addActionListener((ActionEvent e) -> {
            GuardarUsuario(true);
            RUsuario.setVisible(false);
        });
        RUsuario.BtnModificar().addActionListener((ActionEvent e) -> {
            GuardarUsuario(false);
            RUsuario.setVisible(false);
        });

    }

    /*Vaciar Interfaz Principañ*/
    public void LimpiarInterfaz(JPanel Panel) {
        Panel.setSize(IM.getWidth(), IM.getHeight());
        IM.getContentPane().removeAll();
        IM.getContentPane().add(Panel);
        IM.pack();
    }

    /*ACCIONES BOTONES Y TEXTOS*/
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] Usuario = CG.getAcceso(login.TxtUsuario().getText(), login.TxtPassword().getText());
        if (e.getSource() == login.BtnIngresar()) {
            if (login.TxtUsuario().getText().isEmpty()) {
                JOptionPane.showMessageDialog(login, "EL ESPACIO DE USUARIO O CONTRASEÑA \n NO PUEDE IR VACIO \n ", "Ingreso Fallido...", JOptionPane.ERROR_MESSAGE);
            } else {
                if (Usuario[0].isEmpty()) {
                    JOptionPane.showMessageDialog(login, "El Usuario No esta Registrado o \n La Contraseña es Incorrecta", "Datos Erroneos..", JOptionPane.WARNING_MESSAGE);
                    login.TxtPassword().setText("");
                } else {
                    login.TxtPassword().setText("");
                    login.TxtUsuario().setText("");
                    if (Usuario[2].equals("Manager")) {
                        UserManager();
                    }
                    if (Usuario[2].equals("Supervisor")) {
                        UserSupervisor();
                    }
                    if (Usuario[2].equals("Administrador")) {
                        UserAdministrador();
                    }
                    Manager("Usuario : " + Usuario[1] + "  Cargo : " + Usuario[2] + "   Ultimo Acceso : " + Usuario[3]);

                    Materiales();

                }
            }
        }
        if (e.getSource() == IM.ItemMateriales()) {
            Materiales();
        }
        if (e.getSource() == IM.ItemUsuarios()) {
            Usuarios();
        }
        if (e.getSource() == IM.ItemBackup()) {
            Backup();
        }
        if (e.getSource() == IM.ItemAyudantes()) {
            Ayudantes();
        }
        if (e.getSource() == IM.ItemEntregas()) {
            //Vacio
        }
        if (e.getSource() == IM.ItemIngresoVehiculos()) {
            IngresoVehiculo();
        }
        if (e.getSource() == IM.ItemProveedor()) {
            Proveedor();
        }
        if (e.getSource() == IM.ItemSesion()) {
            IM.setVisible(false);
        }
        if (e.getSource() == IM.ItemVehiculos()) {
            Vehiculos();
        }
        if (e.getSource() == IM.ItemVentas()) {
            //Vacio
        }
        //Boton NUEVO PRESIONADO DE PANEL USUARIO
        if (e.getSource() == PU.BtnNuevo()) {
            RegistroUsuario(true);
        }
        //BOTON EDITAR PRESIONADO PANEL USUARIO
        if (e.getSource() == PU.BtnEditarMaterial()) {
            if (PU.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PU, "Debe seleccionar un registro");
            } else {
                RUsuario.TxtUser().setText(PU.TablaArticulos().getValueAt(PU.TablaArticulos().getSelectedRow(), 0).toString());
                RUsuario.TxtNombre().setText(PU.TablaArticulos().getValueAt(PU.TablaArticulos().getSelectedRow(), 1).toString());
                RUsuario.ComboCargo().setSelectedItem(PU.TablaArticulos().getValueAt(PU.TablaArticulos().getSelectedRow(), 2).toString());
                RegistroUsuario(false);
            }
        }
        if (e.getSource() == this.PU.BtnActualizar()) {
            System.out.println("Funciona");
            CargarDatosUsuarios("");
        }
        if (e.getSource() == PU.BtnBuscar()) {
            CargarDatosUsuarios(PU.TxtBusqueda().getText());
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == PM.TxtBusqueda()) {
            CargarDatosMateriales(PM.TxtBusqueda().getText());
        }
        if (e.getSource() == PU.TxtBusqueda()) {
            CargarDatosUsuarios(PU.TxtBusqueda().getText());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /*FIN ACCIOBES BOTONES Y TEXTOS*/
    //                  REGISTRO DE USUARIOS
    public void RegistroUsuario(boolean condicion) {
        RUsuario.setVisible(true);
        RUsuario.setLocationRelativeTo(null);
        if (condicion) {
            RUsuario.BtnAgregar().setVisible(true);
            RUsuario.BtnModificar().setVisible(false);
            RUsuario.TxtUser().setEditable(true);
            RUsuario.TxtContrasenia().setText("");
            RUsuario.TxtNombre().setText("");
            RUsuario.TxtUser().setText("");
        } else {
            RUsuario.BtnAgregar().setVisible(false);
            RUsuario.BtnModificar().setVisible(true);
            RUsuario.TxtUser().setEditable(false);
        }
    }

    //                  Guardar DATOS Usuario
    public void GuardarUsuario(boolean condicion) {
        if (CG.InsertarUsuario(RUsuario.TxtUser().getText(),
                RUsuario.TxtNombre().getText(),
                RUsuario.TxtContrasenia().getText(),
                RUsuario.ComboCargo().getSelectedItem().toString(),
                condicion)) {
            JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO...");
        } else {
            JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR...");

        }

    }

    /*                      Interfaz Manager       */
    public void UserManager() {
        IM.ItemAyudantes().setVisible(true);
        IM.ItemBackup().setVisible(true);
        IM.ItemEntregas().setVisible(true);
        IM.ItemIngresoVehiculos().setVisible(true);
        IM.ItemSesion().setVisible(true);
        IM.ItemProveedor().setVisible(true);
        IM.ItemMateriales().setVisible(true);
        IM.ItemUsuarios().setVisible(true);
        IM.ItemVehiculos().setVisible(true);
        IM.ItemVentas().setVisible(true);
    }

    public void UserSupervisor() {
        IM.ItemAyudantes().setVisible(true);
        IM.ItemBackup().setVisible(false);
        IM.ItemEntregas().setVisible(true);
        IM.ItemIngresoVehiculos().setVisible(true);
        IM.ItemSesion().setVisible(true);
        IM.ItemProveedor().setVisible(true);
        IM.ItemMateriales().setVisible(true);
        IM.ItemUsuarios().setVisible(false);
        IM.ItemVehiculos().setVisible(true);
        IM.ItemVentas().setVisible(true);
    }

    public void UserAdministrador() {
        IM.ItemAyudantes().setVisible(true);
        IM.ItemBackup().setVisible(false);
        IM.ItemEntregas().setVisible(true);
        IM.ItemIngresoVehiculos().setVisible(true);
        IM.ItemSesion().setVisible(true);
        IM.ItemProveedor().setVisible(true);
        IM.ItemMateriales().setVisible(true);
        IM.ItemUsuarios().setVisible(false);
        IM.ItemVehiculos().setVisible(true);
        IM.ItemVentas().setVisible(true);
    }

    public void Manager(String title) {
        IM.setVisible(true);
        IM.setTitle(title);

    }

    public void Materiales() {
        CargarDatosMateriales(PM.TxtBusqueda().getText());
        PM.setVisible(true);
        LimpiarInterfaz(PM);
    }

    public void Usuarios() {
        CargarDatosUsuarios(PU.TxtBusqueda().getText());
        PU.setVisible(true);
        LimpiarInterfaz(PU);
    }

    public void Vehiculos() {
        CargarDatosVehiculo(PV.TxtBusqueda().getText());
        PV.setVisible(true);
        LimpiarInterfaz(PV);
    }

    public void Ayudantes() {
        CargarDatosAyudantes(PA.TxtBusqueda().getText());
        PA.setVisible(true);
        LimpiarInterfaz(PA);
        System.out.println(" Ayudantes");
    }

    public void IngresoVehiculo() {
        CargarDatosIngresoVehiculo(PIV.TxtBusqueda().getText());
        PIV.setVisible(true);
        LimpiarInterfaz(PIV);
        System.out.println("Ingreso ");
    }

    public void Proveedor() {
        CargarDatosProveedor(PP.TxtBusqueda().getText());
        PP.setVisible(true);
        LimpiarInterfaz(PP);
    }

    public void Backup() {
        CopiadeDatos();
        PB.setVisible(true);

    }

    public void CopiadeDatos() {
        String Copia = CG.Backup();
        String ba = "Create database TAMECON;\n"
                + "Use TAMECON;\n"
                + "Create Table Vehiculo(\n"
                + "    Placa varchar(12) primary key,\n"
                + "    Modelo varchar(50),\n"
                + "    Color varchar(20),\n"
                + "    NombreCliente varchar(80)\n"
                + ");\n"
                + "Create Table Ayudante(\n"
                + "    Id int auto_increment primary key,\n"
                + "    Nombre varchar(35),\n"
                + "    Estado CHAR(1)\n"
                + ");\n"
                + "\n"
                + "Create table IngresoVehiculo(\n"
                + "    Id int Auto_Increment primary key,\n"
                + "    Placa varchar(12),\n"
                + "    PC int,\n"
                + "    FechaIngreso datetime,\n"
                + "    FechaIngreso1 varchar(12),\n"
                + "    FechaSalida datetime,\n"
                + "    FechaSalida1 varchar(12),\n"
                + "    foreign key (Placa) references Vehiculo(Placa),\n"
                + "    Foreign key (PC) references Ayudante(Id)\n"
                + ");\n"
                + "Create table Inventario(\n"
                + "    Codigo varchar(15)primary key,\n"
                + "    Material varchar(100),\n"
                + "    PrecioCompra float,\n"
                + "    PrecioVenta float,\n"
                + "    Unidad varchar(10)\n"
                + ");\n"
                + "create table DepositoTotal(\n"
                + "    Codigo varchar(15)primary key,\n"
                + "    CEntrada float,\n"
                + "    CSalida float,\n"
                + "    CSaldo float,\n"
                + "    Foreign key (Codigo) references Inventario(Codigo)\n"
                + ");\n"
                + "Create table Deposito(\n"
                + "    Codigo varchar(15),\n"
                + "    CEntrada float,\n"
                + "    CSalida float,\n"
                + "    Fecha datetime,\n"
                + "    Foreign key (Codigo) references Inventario(Codigo)\n"
                + ");\n"
                + "\n"
                + "Create Table EntregaMateriales(\n"
                + "    Codigo varchar(15),\n"
                + "    Id int,\n"
                + "    Cantidad float,\n"
                + "    PrecioVenta float,\n"
                + "    Fecha datetime,\n"
                + "    User varchar(25),\n"
                + "    Descuento float,\n"
                + "    Foreign key (User) references Usuario(User),\n"
                + "    Foreign key(Codigo) references Inventario(Codigo),\n"
                + "    Foreign key (Id) references IngresoVehiculo(Id)\n"
                + ");\n"
                + "Create table Venta(\n"
                + "    Codigo varchar(15),\n"
                + "    Cantidad float,\n"
                + "    PrecioVenta float,\n"
                + "    FechaCancelacion Datetime,\n"
                + "    Descuento float,\n"
                + "    User varchar(25),\n"
                + "    Nombre Varchar(50),\n"
                + "    Foreign key (User) references Usuario(User),\n"
                + "    Foreign key(Codigo) references Inventario(Codigo)\n"
                + ");\n"
                + "Create Table Usuario(\n"
                + "    User varchar(25) primary key,\n"
                + "    Password varchar(60),\n"
                + "    NombreUsuario varchar(50),\n"
                + "    Id datetime,\n"
                + "    Cargo varchar(25)\n"
                + ");\n"
                + "create table Proveedor(\n"
                + "    Id int auto_increment primary key,\n"
                + "    Nombre varchar(50),\n"
                + "    Telefono varchar(20),\n"
                + "    Direccion varchar(100),\n"
                + "    Detalles varchar(100)\n"
                + ");\n"
                + "\n"
                + "Create table Bitacora(\n"
                + "    User varchar(25),\n"
                + "    FechaIngreso datetime,\n"
                + "    Foreign key (User) references Usuario(User)\n"
                + ");";
        PB.Texto().setText(ba + Copia);

    }

    public void CargarDatosMateriales(String textoBusqueda) {
        this.PM.setDatos(this.CG.getLista(textoBusqueda), this.CG.getTotal());
    }

    public void CargarDatosUsuarios(String textoBusqueda) {
        this.PU.setDatos(this.CG.getListaUsuarios(textoBusqueda), this.CG.getTotal());
    }

    public void CargarDatosAyudantes(String textoBusqueda) {
        this.PA.setDatos(this.CG.getListaAyudante(textoBusqueda), this.CG.getTotal());
    }

    public void CargarDatosProveedor(String textoBusqueda) {
        this.PP.setDatos(this.CG.getListaProveedor(textoBusqueda), this.CG.getTotal());
    }

    public void CargarDatosVehiculo(String textoBusqueda) {
        this.PV.setDatos(this.CG.getListaVehiculo(textoBusqueda), this.CG.getTotal());
    }

    public void CargarDatosIngresoVehiculo(String textoBusqueda) {
        this.PIV.setDatos(this.CG.getListaIngresoVehiculo(textoBusqueda), this.CG.getTotal());
    }

    /*                    FIn interfaz Manager     */
 /*                  Interfaz Registro Articulo       */
    public void Articulos(int n) {
        RA = new RegistroArticulo();
        RA.setLocationRelativeTo(null);
        RA.setVisible(true);
        if (n == 1) {
            String Codigo = PM.TablaArticulos().getValueAt(PM.TablaArticulos().getSelectedRow(), 0).toString();
            String[] datos = CG.getArticulo(Codigo);
            RA.TxtCodigo().setText(Codigo);
            RA.TxtNombre().setText(datos[0]);
            RA.TxtPrecioCompra().setText(datos[1]);
            RA.TxtPrecioVenta().setText(datos[2]);
            RA.TxtUnidad().setSelectedItem(datos[3]);
            RA.TxtCantidad().setText(datos[4]);

        } else {
            RA.BtnAgregar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (CG.InsertarArticulo(
                            RA.TxtCodigo().getText(),
                            RA.TxtNombre().getText(),
                            RA.TxtPrecioCompra().getText(),
                            RA.TxtPrecioVenta().getText(),
                            RA.TxtUnidad().getSelectedItem().toString(),
                            RA.TxtCantidad().getText())) {
                        System.out.println("Correcto");

                    } else {
                        System.out.println("NO se pudo guardar el articulo");
                    }
                }
            });
            RA.BtnModificar().addActionListener(this);
            RA.LabelCodigo().setText("Ultimo : " + CG.getCodigo());
            RA.TxtCantidad().setText("");
            RA.TxtCodigo().setText("TMC-");
            RA.TxtNombre().setText("");
            RA.TxtPrecioCompra().setText("");
            RA.TxtPrecioVenta().setText("");
            RA.TxtUnidad();
        }
    }

}
