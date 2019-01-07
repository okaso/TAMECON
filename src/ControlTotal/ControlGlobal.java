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
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
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
 * @author PABLO
 */
public class ControlGlobal implements ActionListener, KeyListener {

    Login login;
    ConsultaGlobal CG;
    InterfazManager IM;

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
    RegistroEntregaMateriales REntrega;
    Backup PB;
    JDialog Dialogo;

    DateFormat df = DateFormat.getDateInstance();

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

        RArticulo = new RegistroArticulo();
        RAyudante = new RegistroAyudante();
        RVehiculo = new RegistroVehiculo();
        RIngresoVehiculo = new RegistroIngresoVehiculo();

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

        IM.ItemIngresoVehiculos().addActionListener((ActionListener) this);
        IM.ItemMateriales().addActionListener((ActionListener) this);
        IM.ItemProveedor().addActionListener((ActionListener) this);
        IM.ItemSesion().addActionListener((ActionListener) this);
        IM.ItemUsuarios().addActionListener((ActionListener) this);
        IM.ItemVehiculos().addActionListener((ActionListener) this);
        IM.ItemVentas().addActionListener((ActionListener) this);

        /*              Iniciando Panel Materiales                 */
        PanelMateriales();

        //          PANEL REGISTRO NUEVO USUARIO
        PanelUsuarios();

        //            PANEL REGISTRO AYUDANTES 
        PanelAyudantes();

        //              PANEL INGRESO VEHICULO AL TALLER
        PanelIngresoVehiculo();
        //              PANEL REGISTRO PROVEEDORES
        PP = new PanelProveedores();
        PP.BtnActualizar().addActionListener((ActionListener) this);
        PP.BtnBuscar().addActionListener((ActionListener) this);
        PP.BtnEditarMaterial().addActionListener((ActionListener) this);
        PP.BtnEliminar().addActionListener((ActionListener) this);
        PP.BtnNuevo().addActionListener((ActionListener) this);
        PP.TxtBusqueda().addKeyListener((KeyListener) this);

        //              PANEL REGISTRO VEHICULOS
        PanelVehiculo();

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

    //PANEL USUARIOS
    public void PanelUsuarios() {
        PU = new PanelUsuarios();
        PU.BtnActualizar().addActionListener((ActionListener) this);
        PU.BtnBuscar().addActionListener((ActionListener) this);
        PU.BtnEditarMaterial().addActionListener((ActionListener) this);
        PU.BtnDetalles().addActionListener((ActionEvent e) -> {
            System.out.println("asdas");
            CargarDatosUsuariosDetalles(PU.TxtBusqueda().getText());
        });
        PU.BtnNuevo().addActionListener((ActionListener) this);
        PU.TxtBusqueda().addKeyListener((KeyListener) this);
    }

    //PANEL MATERIALES
    public void PanelMateriales() {
        PM = new PanelMateriales();
        PM.BtnBuscar().addActionListener((ActionEvent e) -> {
            CargarDatosMateriales(PM.TxtBusqueda().getText());
        });
        PM.BtnEditarMaterial().addActionListener((ActionEvent e) -> {
            if (PM.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PM, "Debe seleccionar un registro");
            } else {
                RArticulo.BtnAgregar().setVisible(false);
                RArticulo.BtnAgregar1().setVisible(false);
                RArticulo.BtnModificar().setVisible(true);
                Articulos(1);
            }
        });
        PM.BtnActualizar().addActionListener((e) -> {
            CargarDatosMateriales("");
        });
        PM.BtnDetalles().addActionListener((ActionEvent e) -> {
            CargarDatosMaterialesDetalles(PM.TxtBusqueda().getText());
        });
        PM.BtnMaterialBajo().addActionListener((e) -> {
            CargarDatosMaterialesBajos(PM.TxtBusqueda().getText());
        });
        PM.TxtBusqueda().addKeyListener((KeyListener) this);
        PM.BtnNuevo().addActionListener((ActionEvent e) -> {
            RArticulo.BtnAgregar().setVisible(true);
            RArticulo.BtnAgregar1().setVisible(false);
            RArticulo.BtnModificar().setVisible(false);
            Articulos(0);
        });
        PM.BtnIngresoMateriales().addActionListener((ActionEvent e) -> {
            if (PM.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PM, "Debe seleccionar un registro");
            } else {
                RArticulo.BtnAgregar().setVisible(false);
                RArticulo.BtnAgregar1().setVisible(true);
                RArticulo.BtnModificar().setVisible(false);
                Articulos(1);
            }
        });
    }

    //PANEL AYUDANTES
    public void PanelAyudantes() {
        PA = new PanelAyudantes();

        PA.BtnActualizar().addActionListener((e) -> {
            CargarDatosAyudantes(PA.TxtBusqueda().getText());
        });
        PA.BtnBuscar().addActionListener((e) -> {
            CargarDatosAyudantes(PA.TxtBusqueda().getText());
        });
        PA.BtnEditarMaterial().addActionListener((e) -> {
            if (PA.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PU, "Debe seleccionar un registro");
            } else {
                RAyudante.setVisible(true);
                InsertarAyudantes(false);
            }
        });
        PA.BtnEliminar().addActionListener((e) -> {
            if (PA.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PA, "Debe seleccionar un registro");
            } else {
                int dialogoResultado = JOptionPane.showConfirmDialog(PA, "¿Esta segur@ de borrar el registro de Ayudante?", "Pregunta", JOptionPane.YES_NO_OPTION);
                if (dialogoResultado == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(
                            ((DefaultTableModel) PA.TablaArticulos().getModel())
                                    .getValueAt(PA.TablaArticulos().getSelectedRow(), 0).toString());
                    if (CG.EliminarAyudante(id)) {
                        CargarDatosAyudantes("");
                    } else {
                        JOptionPane.showMessageDialog(PA, "El Registro no se pudo borrar");
                    }

                }
            }
        });
        PA.BtnNuevo().addActionListener((e) -> {
            RAyudante.setVisible(true);
            InsertarAyudantes(true);
        });
        PA.TxtBusqueda().addKeyListener((KeyListener) this);
    }

    public void PanelVehiculo() {
        PV = new PanelVehiculos();
        PV.BtnActualizar().addActionListener((e) -> {
            CargarDatosVehiculo("");
        });
        PV.BtnBuscar().addActionListener((e) -> {
            CargarDatosVehiculo(PV.TxtBusqueda().getText());
        });
        PV.BtnEditarMaterial().addActionListener((e) -> {
            if (PV.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PU, "Debe seleccionar un registro");
            } else {
                RVehiculo.setVisible(true);
                RVehiculo.TxtNombre().setText(PV.TablaArticulos().getValueAt(PV.TablaArticulos().getSelectedRow(), 3).toString());
                RVehiculo.TxtPlaca().setText(PV.TablaArticulos().getValueAt(PV.TablaArticulos().getSelectedRow(), 0).toString());
                RVehiculo.TxtModelo().setText(PV.TablaArticulos().getValueAt(PV.TablaArticulos().getSelectedRow(), 1).toString());
                RVehiculo.TxtColor().setText(PV.TablaArticulos().getValueAt(PV.TablaArticulos().getSelectedRow(), 2).toString());
                RVehiculo.BtnAgregar().setVisible(false);
                RVehiculo.BtnModificar().setVisible(true);
                RVehiculo.TxtPlaca().setEditable(false);
            }
        });

        PV.BtnNuevo().addActionListener((e) -> {
            RVehiculo.setVisible(true);
            RVehiculo.BtnAgregar().setVisible(true);
            RVehiculo.BtnModificar().setVisible(false);
            RVehiculo.TxtPlaca().setEditable(true);
        });
        PV.TxtBusqueda().addKeyListener((KeyListener) this);
    }

    public void PanelIngresoVehiculo() {
        PIV = new PanelIngresoVehiculo();
        PIV.BtnActualizar().addActionListener((e) -> {
            PIV.TxtBusqueda().setText("");
            CargarDatosIngresoVehiculo(PIV.TxtBusqueda().getText());
        });
        PIV.BtnBuscar().addActionListener((e) -> {
            CargarDatosIngresoVehiculo(PIV.TxtBusqueda().getText());
        });
        PIV.BtnEditarMaterial().addActionListener((e) -> {
            if (PIV.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PU, "Debe seleccionar un registro");
            } else {
                RIngresoVehiculo.Ayudante().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getAyudantes()));
                RIngresoVehiculo.Placa().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getPlaca("")));
                RIngresoVehiculo.setVisible(true);
                RIngresoVehiculo.BtnAgregar().setVisible(false);
                RIngresoVehiculo.BtnModificar().setVisible(true);

                RIngresoVehiculo.TxtPlaca().setText(PIV.TablaArticulos().getValueAt(PIV.TablaArticulos().getSelectedRow(), 1).toString());
                RIngresoVehiculo.Placa().setSelectedItem(PIV.TablaArticulos().getValueAt(PIV.TablaArticulos().getSelectedRow(), 1).toString());
                RIngresoVehiculo.Ayudante().setSelectedItem(PIV.TablaArticulos().getValueAt(PIV.TablaArticulos().getSelectedRow(), 4).toString());
                RIngresoVehiculo.setTitle(PIV.TablaArticulos().getValueAt(PIV.TablaArticulos().getSelectedRow(), 0).toString());
            }
        });
        PIV.BtnNuevo().addActionListener((e) -> {
            RIngresoVehiculo.Ayudante().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getAyudantes()));
            RIngresoVehiculo.Placa().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getPlaca("")));
            RIngresoVehiculo.setVisible(true);
            RIngresoVehiculo.BtnAgregar().setVisible(true);
            RIngresoVehiculo.BtnModificar().setVisible(false);
        });
        PIV.TxtBusqueda().addKeyListener((KeyListener) this);
        PIV.BtnDetalles().addActionListener((e) -> {
            this.PIV.setDatos(this.CG.getListaIngresoVehiculoDetalles(PIV.TxtBusqueda().getText()), this.CG.getTotal());
        });
        PIV.BtnEntrega().addActionListener((e) -> {
            EntregaMateriales();
        });
    }

    public void EntregaMateriales() {
        REntrega = new RegistroEntregaMateriales();
        REntrega.setLocationRelativeTo(null);
        REntrega.setVisible(true);
        REntrega.BtnAgregar().addActionListener(this);
        REntrega.BtnImprimir().addActionListener(this);
        REntrega.BtnQuitar().addActionListener(this);
        REntrega.BtnRebaja().addActionListener(this);
        REntrega.ComboMaterial().addActionListener(this);
        REntrega.TxtCodigo().addKeyListener(this);

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
        if (e.getSource() == PA.TxtBusqueda()) {
            CargarDatosAyudantes(PA.TxtBusqueda().getText());
        }
        if (e.getSource() == PV.TxtBusqueda()) {
            CargarDatosVehiculo(PV.TxtBusqueda().getText());
        }
        if (e.getSource() == PIV.TxtBusqueda()) {
            CargarDatosIngresoVehiculo(PIV.TxtBusqueda().getText());
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
        RVehiculo.BtnAgregar().addActionListener((e) -> {
            NuevoVehiculo();
        });
        RVehiculo.BtnModificar().addActionListener((e) -> {
            System.out.println("as");
            ModificarVehiculo();
        });

    }

    public void Ayudantes() {
        CargarDatosAyudantes(PA.TxtBusqueda().getText());
        PA.setVisible(true);
        LimpiarInterfaz(PA);
        RAyudante.BtnAgregar().addActionListener((e) -> {
            String S = RAyudante.Estado().getSelectedItem().toString();
            if (S.equals("Habilitado")) {
                S = "H";
            } else {
                S = "D";
            }
            if (CG.NuevoAyudante(RAyudante.TxtNombre().getText(), S)) {
                CargarDatosAyudantes(PA.TxtBusqueda().getText());
            } else {
                JOptionPane.showMessageDialog(PA, "NO SE PUDO COMPLETAR EL REGISTRO");
            }
        });
        RAyudante.BtnModificar().addActionListener((e) -> {
            NuevoAyudante(false);
        });

    }

    public void IngresoVehiculo() {
        CargarDatosIngresoVehiculo(PIV.TxtBusqueda().getText());
        PIV.setVisible(true);
        LimpiarInterfaz(PIV);
        RIngresoVehiculo.BtnAgregar().addActionListener((e) -> {
            NuevoIngresoVehiculo();
        });
        RIngresoVehiculo.BtnModificar().addActionListener((e) -> {
            ModificarIngresoVehiculo();
        });
        RIngresoVehiculo.TxtPlaca().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                RIngresoVehiculo.Placa().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getPlaca(RIngresoVehiculo.TxtPlaca().getText())));
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        RIngresoVehiculo.Placa().addActionListener((e) -> {
            RIngresoVehiculo.TxtPlaca().setText(RIngresoVehiculo.Placa().getSelectedItem().toString());
        });
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
                + "    Codigo varchar(15)primary key,CodMat varchar(50),\n"
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

    public void CargarDatosMaterialesDetalles(String textoBusqueda) {
        this.PM.setDatos(this.CG.getListaDetalles(textoBusqueda), this.CG.getTotal());
    }

    public void CargarDatosMaterialesBajos(String textoBusqueda) {
        this.PM.setDatos(this.CG.getListaBajos(textoBusqueda), this.CG.getTotal());
    }

    public void CargarDatosUsuarios(String textoBusqueda) {
        this.PU.setDatos(this.CG.getListaUsuarios(textoBusqueda), this.CG.getTotal());
    }

    public void CargarDatosUsuariosDetalles(String textoBusqueda) {
        this.PU.setDatos(this.CG.getListaUsuariosDetalles(textoBusqueda), this.CG.getTotal());
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

    /*                  INSERTAR Y MODIFICAR USUARIOS       */
    public void InsertarAyudantes(boolean Condicion) {

        if (Condicion) {
            RAyudante.BtnAgregar().setVisible(true);
            RAyudante.BtnModificar().setVisible(false);
            RAyudante.TxtNombre().setText("");
            RAyudante.setTitle("0");
        } else {
            RAyudante.BtnAgregar().setVisible(false);
            RAyudante.BtnModificar().setVisible(true);
            RAyudante.setTitle(PA.TablaArticulos().getValueAt(PA.TablaArticulos().getSelectedRow(), 0).toString());
            RAyudante.TxtNombre().setText(PA.TablaArticulos().getValueAt(PA.TablaArticulos().getSelectedRow(), 1).toString());
            RAyudante.Estado().setSelectedItem(PA.TablaArticulos().getValueAt(PA.TablaArticulos().getSelectedRow(), 2));
        }

    }

    public void NuevoAyudante(boolean condicion) {

        int id = Integer.parseInt(RAyudante.getTitle());
        String S = RAyudante.Estado().getSelectedItem().toString();
        if (S.equals("Habilitado")) {
            S = "H";
        } else {
            S = "D";
        }
        if (CG.ModificarAyudante(id, RAyudante.TxtNombre().getText(), S)) {
            RAyudante.setVisible(false);
            CargarDatosAyudantes(PA.TxtBusqueda().getText());
        }

    }

    /*                    FIn interfaz Manager     */
 /*                  Interfaz Registro Articulo       */
    public void Articulos(int n) {
        RArticulo.setVisible(true);
        if (n == 1) {

            String Codigo = PM.TablaArticulos().getValueAt(PM.TablaArticulos().getSelectedRow(), 0).toString();
            String[] datos = CG.getArticulo(Codigo);
            RArticulo.TxtCodigo().setText(Codigo);
            RArticulo.TxtNombre().setText(datos[0]);
            RArticulo.TxtPrecioCompra().setText(datos[1]);
            RArticulo.TxtPrecioVenta().setText(datos[2]);
            RArticulo.TxtUnidad().setSelectedItem(datos[3]);
            RArticulo.TxtCantidad().setText(datos[4]);
            RArticulo.TxtCodigo1().setText(datos[5]);

        } else {

            LimpiarInterfazArticulo();

        }
        RArticulo.BtnAgregar().addActionListener((ActionEvent e) -> {
            InsertarArticulo(n);
            CargarDatosMateriales(PM.TxtBusqueda().getText());
        });
        RArticulo.BtnModificar().addActionListener((ActionEvent e) -> {
            InsertarArticulo(n);
            CargarDatosMateriales(PM.TxtBusqueda().getText());
            RArticulo.setVisible(false);
        });
        RArticulo.BtnAgregar1().addActionListener((ActionEvent e) -> {
            InsertarArticulo(2);
            CargarDatosMateriales(PM.TxtBusqueda().getText());
            RArticulo.setVisible(false);
        });
    }

    public void InsertarArticulo(int x) {
        try {
            if (CG.InsertarArticulo(
                    RArticulo.TxtCodigo().getText(),
                    RArticulo.TxtCodigo1().getText(),
                    RArticulo.TxtNombre().getText(),
                    RArticulo.TxtPrecioCompra().getText(),
                    RArticulo.TxtPrecioVenta().getText(),
                    RArticulo.TxtUnidad().getSelectedItem().toString(),
                    RArticulo.TxtCantidad().getText(), x)) {
                System.out.println("Correcto");
                LimpiarInterfazArticulo();

            } else {
                System.out.println("NO se pudo guardar el articulo");
            }
        } catch (Exception e) {
            System.out.println("ERROR ");
        }
    }

    public void LimpiarInterfazArticulo() {
        RArticulo.LabelCodigo().setText("Ultimo : " + CG.getCodigo());

        RArticulo.TxtCantidad().setText("");
        RArticulo.TxtCodigo().setText("TMC-");
        RArticulo.TxtCodigo1().setText("");
        RArticulo.TxtNombre().setText("");
        RArticulo.TxtPrecioCompra().setText("");
        RArticulo.TxtPrecioVenta().setText("");
    }

    /*              INTERFAZ VEHICULOS*/
    public void NuevoVehiculo() {

        if (CG.NuevoVehiculo(RVehiculo.TxtNombre().getText(),
                RVehiculo.TxtPlaca().getText(),
                RVehiculo.TxtModelo().getText(),
                RVehiculo.TxtColor().getText())) {
            CargarDatosVehiculo("");
            RVehiculo.TxtNombre().setText("");
            RVehiculo.TxtPlaca().setText("");
            RVehiculo.TxtModelo().setText("");
            RVehiculo.TxtColor().setText("");
        } else {
            JOptionPane.showMessageDialog(PV, "NO SE PUDO REGISTRAR EL VEHICULO");
        }
    }

    public void ModificarVehiculo() {

        if (CG.ModificarVehiculo(RVehiculo.TxtNombre().getText(),
                RVehiculo.TxtPlaca().getText(),
                RVehiculo.TxtModelo().getText(),
                RVehiculo.TxtColor().getText())) {
            CargarDatosVehiculo("");
            RVehiculo.setVisible(false);
            RVehiculo.TxtNombre().setText("");
            RVehiculo.TxtPlaca().setText("");
            RVehiculo.TxtModelo().setText("");
            RVehiculo.TxtColor().setText("");
        } else {
            JOptionPane.showMessageDialog(PV, "NO SE PUDO MODIFICAR EL VEHICULO");
        }
    }

    public void NuevoIngresoVehiculo() {
        String fecha = df.format(RIngresoVehiculo.Date().getDate());
        if (CG.NuevoIngresoVehiculo(fecha,
                RIngresoVehiculo.TxtPlaca().getText(),
                RIngresoVehiculo.Ayudante().getSelectedItem().toString())) {
            CargarDatosIngresoVehiculo("");
            RIngresoVehiculo.TxtPlaca().setText("");
            RIngresoVehiculo.Ayudante().setSelectedIndex(0);
            Date date = null;
            RIngresoVehiculo.Date().setDate(date);

        } else {
            JOptionPane.showMessageDialog(PV, "NO SE PUDO REGISTRAR EL VEHICULO");
        }
    }

    public void ModificarIngresoVehiculo() {

        if (CG.ModificarIngresoVehiculo(
                RIngresoVehiculo.TxtPlaca().getText(),
                RIngresoVehiculo.Ayudante().getSelectedItem().toString(),
                RIngresoVehiculo.getTitle())) {
            CargarDatosVehiculo("");
            RIngresoVehiculo.setVisible(false);
            CargarDatosIngresoVehiculo("");
            RIngresoVehiculo.TxtPlaca().setText("");
            RIngresoVehiculo.Ayudante().setSelectedIndex(0);
            Date date = null;
            RIngresoVehiculo.Date().setDate(date);
        } else {
            JOptionPane.showMessageDialog(PV, "NO SE PUDO MODIFICAR EL VEHICULO");
        }
    }
}
