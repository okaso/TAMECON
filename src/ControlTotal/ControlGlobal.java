/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlTotal;

import Consultas.ConsultaGlobal;
import Impresion.ImpresionEntregaMateriales;
import Interfaces.*;
import Interfaces.Login;
import Interfaces.Registros.RegistroArticulo;
import Interfaces.Registros.*;
import Paneles.*;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
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
    DetallesDeVentas DV;

    //   INTERFACES DE REGISTROS
    RegistroUsuario RUsuario;
    RegistroArticulo RArticulo;
    RegistroAyudante RAyudante;
    RegistroIngresoVehiculo RIngresoVehiculo;
    RegistroVehiculo RVehiculo;

    RegistroEntregaMateriales REntrega;
    RegistroProveedor RProveedor;
    ImpresionEntregaMateriales IEM;

    Backup PB;
    JDialog Dialogo;
    String User = "";
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
        RProveedor = new RegistroProveedor();

        IEM = new ImpresionEntregaMateriales();

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
        IM.ItemDetalles().addActionListener((e) -> {
            DetallesIngresos();
        });

        /*              Iniciando Panel Materiales                 */
        PanelMateriales();

        //          PANEL REGISTRO NUEVO USUARIO
        PanelUsuarios();

        //            PANEL REGISTRO AYUDANTES 
        PanelAyudantes();

        //              PANEL INGRESO VEHICULO AL TALLER
        PanelIngresoVehiculo();
        //              PANEL REGISTRO PROVEEDORES
        PanelProveedores();

        //              PANEL REGISTRO VEHICULOS
        PanelVehiculo();
        DetallesVentas();

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
                JOptionPane.showMessageDialog(PA, "Debe seleccionar un registro");
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
                JOptionPane.showMessageDialog(PV, "Debe seleccionar un registro");
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
                JOptionPane.showMessageDialog(PIV, "Debe seleccionar un registro");
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
            if (PIV.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PIV, "Debe seleccionar un registro");
            } else {
                Alertas();
                EntregaMateriales();

            }
        });
    }

    public void DetallesVentas() {
        DV = new DetallesDeVentas();

    }

    //PANEL PROVEEDORES
    public void PanelProveedores() {
        PP = new PanelProveedores();
        PP.BtnActualizar().addActionListener((e) -> {
            PP.TxtBusqueda().setText("");
            CargarDatosProveedor(PP.TxtBusqueda().getText());
        });
        PP.BtnBuscar().addActionListener((e) -> {
            CargarDatosProveedor(PP.TxtBusqueda().getText());
        });
        PP.BtnEditarMaterial().addActionListener((e) -> {
            if (PP.TablaArticulos().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(PP, "Debe seleccionar un registro");
            } else {
                InsertarProveedor(false);
            }
        });
        PP.BtnEliminar().addActionListener((e) -> {
            EliminarProveedor();
        });
        PP.BtnNuevo().addActionListener((e) -> {
            InsertarProveedor(true);
        });
        PP.TxtBusqueda().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                CargarDatosProveedor(PP.TxtBusqueda().getText());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void InsertarProveedor(boolean condicion) {
        RProveedor.setVisible(true);
        if (condicion) {
            RProveedor.BtnAgregar().setVisible(true);
            RProveedor.BtnModificar().setVisible(false);
            RProveedor.TxtDetalle().setText("");
            RProveedor.TxtDirec().setText("");
            RProveedor.TxtNomb().setText("");
            RProveedor.TxtTelef().setText("");
        } else {
            RProveedor.BtnAgregar().setVisible(false);
            RProveedor.BtnModificar().setVisible(true);
            RProveedor.setTitle(PP.TablaArticulos().getValueAt(PP.TablaArticulos().getSelectedRow(), 0).toString());
            RProveedor.TxtDetalle().setText(PP.TablaArticulos().getValueAt(PP.TablaArticulos().getSelectedRow(), 4).toString());
            RProveedor.TxtDirec().setText(PP.TablaArticulos().getValueAt(PP.TablaArticulos().getSelectedRow(), 3).toString());
            RProveedor.TxtNomb().setText(PP.TablaArticulos().getValueAt(PP.TablaArticulos().getSelectedRow(), 1).toString());
            RProveedor.TxtTelef().setText(PP.TablaArticulos().getValueAt(PP.TablaArticulos().getSelectedRow(), 2).toString());
        }
        RProveedor.BtnAgregar().addActionListener((e) -> {
            if (CG.InsertarProveedor(RProveedor.TxtNomb().getText(),
                    RProveedor.TxtTelef().getText(),
                    RProveedor.TxtDirec().getText(),
                    RProveedor.TxtDetalle().getText())) {
                CargarDatosProveedor(PP.TxtBusqueda().getText());
                RProveedor.setVisible(false);
            } else {
                System.out.println("NO SE PUDO INSERTAR EL PROVEEDOR");
            }
        });
        RProveedor.BtnModificar().addActionListener((e) -> {
            if (CG.ModificarProveedor(RProveedor.TxtNomb().getText(),
                    RProveedor.TxtTelef().getText(),
                    RProveedor.TxtDirec().getText(),
                    RProveedor.TxtDetalle().getText(),
                    RProveedor.getTitle())) {
                CargarDatosProveedor(PP.TxtBusqueda().getText());
                RProveedor.setVisible(false);
            } else {
                System.out.println("NO SE PUDO MODIFICAR EL PROVEEDOR");
            }
        });
    }

    public void EliminarProveedor() {
        if (CG.EliminarProveedor(PP.TablaArticulos().getValueAt(PP.TablaArticulos().getSelectedRow(), 0).toString())) {
            CargarDatosProveedor(PP.TxtBusqueda().getText());
        } else {
            System.out.println("NO SE PUDO ELIMINAR EL PROVEEDOR");
        }
    }

    public void EntregaMateriales() {
        REntrega = new RegistroEntregaMateriales();
        REntrega.setLocationRelativeTo(null);
        REntrega.setVisible(true);
        CargarDatosEntrega();
        REntrega.BtnAgregar().addActionListener((e) -> {
            RegistroEntregaMateriales();
            Alertas();
        });
        REntrega.BtnImprimir().addActionListener((e) -> {
            IEM.setVisible(true);
            IEM.setTitle(REntrega.LabelNro().getText());
            IEM.setDatos(CG.getImpresionEntrega(REntrega.LabelNro().getText()));
            IEM.Fecha();
            IEM.setPlaca(REntrega.LabelPlaca().getText());
            IEM.setCliente(CG.getCliente(REntrega.LabelPlaca().getText()));

        });
        REntrega.BtnQuitar().addActionListener((e) -> {
            if (REntrega.TablaEntregas().getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(REntrega, "Debe seleccionar un registro");
            } else {
                QuitarMaterial();
            }
        });
        REntrega.BtnRebaja().addActionListener((e) -> {
            AgregarRebaja();
        });

    }

    public void RegistroEntregaMateriales() {
        if (CG.EntregaMateriales(REntrega.TxtCodigo().getText(),
                REntrega.LabelNro().getText(),
                REntrega.TxtCantidad().getText(),
                User,
                REntrega.TxtRebaja().getText(),
                REntrega.LabelPV().getText())) {
            Alertas();
            REntrega.TxtCantidad().setText("");
            REntrega.setDatos(CG.getEntrega(REntrega.LabelNro().getText()));

        } else {
            System.out.println("NOse PUDO GUARDAR");
        }
    }

    public void QuitarMaterial() {
        if (CG.QuitarMaterial(REntrega.TablaEntregas().getValueAt(REntrega.TablaEntregas().getSelectedRow(), 1).toString(),
                REntrega.TablaEntregas().getValueAt(REntrega.TablaEntregas().getSelectedRow(), 4).toString(),
                REntrega.TablaEntregas().getValueAt(REntrega.TablaEntregas().getSelectedRow(), 0).toString()
        )) {
            REntrega.TxtCantidad().setText("");
            REntrega.setDatos(CG.getEntrega(REntrega.LabelNro().getText()));
        } else {
            System.out.println("No se Pudo Quitar Este Articulo");
        }
    }

    public void AgregarRebaja() {
        if (CG.AgregarRebaja(REntrega.LabelNro().getText(), REntrega.TxtRebaja().getText())) {
            REntrega.setDatos(CG.getEntrega(REntrega.LabelNro().getText()));
            REntrega.TxtRebaja().setText("0");
        } else {
            System.out.println("No se pudo Agregar Rebaja");
        }
    }

    public void CargarDatosEntrega() {
        REntrega.LabelNro().setText(PIV.TablaArticulos().getValueAt(PIV.TablaArticulos().getSelectedRow(), 0).toString());
        REntrega.LabelPlaca().setText(PIV.TablaArticulos().getValueAt(PIV.TablaArticulos().getSelectedRow(), 1).toString());
        REntrega.LabelAyudante().setText(PIV.TablaArticulos().getValueAt(PIV.TablaArticulos().getSelectedRow(), 4).toString());
        REntrega.ComboMaterial().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getMaterial(REntrega.TxtCodigo().getText())));
        REntrega.ComboMaterial().addActionListener((e) -> {
            REntrega.TxtCodigo().setText(CG.getCodigo(REntrega.ComboMaterial().getSelectedItem().toString()));
            String[] datos = CG.getMaterialDetalles(REntrega.TxtCodigo().getText());
            REntrega.LabelPC().setText(datos[0]);
            REntrega.LabelPV().setText(datos[1]);
            REntrega.LabelUnidad().setText(datos[2]);
            REntrega.LabelCantidad().setText(datos[3]);
        });

        REntrega.TxtCodigo().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                REntrega.ComboMaterial().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getMaterial(REntrega.TxtCodigo().getText())));
                String[] datos = CG.getMaterialDetalles(REntrega.TxtCodigo().getText());
                REntrega.LabelPC().setText(datos[0]);
                REntrega.LabelPV().setText(datos[1]);
                REntrega.LabelUnidad().setText(datos[2]);
                REntrega.LabelCantidad().setText(datos[3]);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        REntrega.setDatos(CG.getEntrega(REntrega.LabelNro().getText()));
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
                    User = Usuario[0];
                    login.TxtPassword().setText("");
                    login.TxtUsuario().setText("");
                    if (Usuario[2].equals("Manager")) {
                        Alertas();
                        UserManager();
                    }
                    if (Usuario[2].equals("Supervisor")) {
                        Alertas();
                        UserSupervisor();
                    }
                    if (Usuario[2].equals("Administrador")) {
                        Alertas();
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
            RegistroVentaArticulos RVenta;
            Alertas();
            RVenta = new RegistroVentaArticulos();
            String name = JOptionPane.showInputDialog(IM, "Igrese Nombre Del Cliente");
            RVenta.Nombre().setText(name);
            CG.RVenta(User, name);
            RVenta.Nro().setText(CG.getIdVenta());
            RVenta.Materiales().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getMaterial(RVenta.TxtCodigo().getText())));
            RVenta.setDatos(CG.getVenta(RVenta.Nro().getText()));
            RVenta.setVisible(true);
            RVenta.setLocationRelativeTo(null);

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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            login.BtnIngresar().doClick();
        }

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
        IM.ItemDetalles().setVisible(true);
        IM.ItemDetalles().addActionListener((e) -> {
        });
        PM.BtnDetalles().setVisible(true);
        PIV.BtnDetalles().setVisible(true);
    }

    public void UserSupervisor() {
        IM.ItemAyudantes().setVisible(true);
        IM.ItemBackup().setVisible(false);
        IM.ItemDetalles().setVisible(false);

        IM.ItemIngresoVehiculos().setVisible(true);
        IM.ItemSesion().setVisible(true);
        IM.ItemProveedor().setVisible(true);
        IM.ItemMateriales().setVisible(true);
        IM.ItemUsuarios().setVisible(false);
        IM.ItemVehiculos().setVisible(true);
        IM.ItemVentas().setVisible(true);
        PM.BtnDetalles().setVisible(false);
        PIV.BtnDetalles().setVisible(false);
    }

    public void UserAdministrador() {
        IM.ItemAyudantes().setVisible(true);
        IM.ItemBackup().setVisible(false);
        IM.ItemDetalles().setVisible(false);

        IM.ItemIngresoVehiculos().setVisible(true);
        IM.ItemSesion().setVisible(true);
        IM.ItemProveedor().setVisible(true);
        IM.ItemMateriales().setVisible(true);
        IM.ItemUsuarios().setVisible(false);
        IM.ItemVehiculos().setVisible(true);
        IM.ItemVentas().setVisible(true);
        PM.BtnDetalles().setVisible(false);
        PIV.BtnDetalles().setVisible(false);
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

    public void DetallesIngresos() {

        DV.setVisible(true);
        LimpiarInterfaz(DV);
        DV.setDatos(CG.getDetallesVenta(DV.TxtBusqueda().getText()), this.CG.getTotal());
        DV.BtnActualizar().addActionListener((e) -> {
            DV.TxtBusqueda().setText("");
            DV.setDatos(CG.getDetallesVenta(DV.TxtBusqueda().getText()), this.CG.getTotal());

        });
        DV.BtnVentas().addActionListener((e) -> {
            DV.setDatos(CG.getDetallesVenta(DV.TxtBusqueda().getText()), this.CG.getTotal());
        });
        DV.BtnEntregas().addActionListener((e) -> {
            DV.setDatos(CG.getDetallesEntrega(DV.TxtBusqueda().getText()), this.CG.getTotal());

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
                + "	Placa varchar(12) primary key,\n"
                + "    Modelo varchar(50),\n"
                + "    Color varchar(20),\n"
                + "    NombreCliente varchar(80)\n"
                + ");\n"
                + "Create Table Ayudante(\n"
                + "	Id int auto_increment primary key,\n"
                + "    Nombre varchar(35),\n"
                + "    Estado CHAR(1)\n"
                + ");\n"
                + "\n"
                + "Create table IngresoVehiculo(\n"
                + "	Id int Auto_Increment primary key,\n"
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
                + "	Codigo varchar(15)primary key,\n"
                + "    Material varchar(100),\n"
                + "    PrecioCompra float,\n"
                + "    PrecioVenta float,\n"
                + "    Unidad varchar(10)\n"
                + ");\n"
                + "create table DepositoTotal(\n"
                + "	Codigo varchar(15)primary key,\n"
                + "    CEntrada float,\n"
                + "    CSalida float,\n"
                + "    CSaldo float,\n"
                + "	Foreign key (Codigo) references Inventario(Codigo)\n"
                + ");\n"
                + "Create table Deposito(\n"
                + "	Codigo varchar(15),\n"
                + "    CEntrada float,\n"
                + "    CSalida float,\n"
                + "    CSaldo float,\n"
                + "    Fecha datetime,\n"
                + "    Foreign key (Codigo) references Inventario(Codigo)\n"
                + ");\n"
                + "\n"
                + "Create Table EntregaMateriales(\n"
                + "	Id int auto_Increment primary key,\n"
                + "	Codigo varchar(15),\n"
                + "    IdV int,\n"
                + "    Cantidad float,\n"
                + "    PrecioVenta float,\n"
                + "    Fecha datetime,\n"
                + "    User varchar(25),\n"
                + "    Descuento float,\n"
                + "    Foreign key (User) references Usuario(User),\n"
                + "    Foreign key(Codigo) references Inventario(Codigo),\n"
                + "    Foreign key (IdV) references IngresoVehiculo(Id)\n"
                + ");\n"
                + "\n"
                + "\n"
                + "Create table NroVenta(\n"
                + "	Id int auto_Increment Primary key,\n"
                + "    Nombre varchar(50),\n"
                + "    Descuento float,\n"
                + "    User varchar(25),\n"
                + "     Foreign key (User) references Usuario(User)\n"
                + ");\n"
                + "\n"
                + "\n"
                + "Create table Venta(\n"
                + "	I int Auto_Increment primary key,\n"
                + "	Id int,\n"
                + "	Codigo varchar(15),\n"
                + "    Cantidad float,\n"
                + "    PrecioVenta float,\n"
                + "    FechaCancelacion datetime,\n"
                + "    Foreign key(Id)references NroVenta(Id),\n"
                + "    Foreign key(Codigo) references Inventario(Codigo)\n"
                + ");\n"
                + "\n"
                + "                    \n"
                + "Create Table Usuario(\n"
                + "	User varchar(25) primary key,\n"
                + "    Password varchar(60),\n"
                + "    NombreUsuario varchar(50),\n"
                + "    FechaIngreso datetime,\n"
                + "    Cargo varchar(25)\n"
                + ");\n"
                + "create table Proveedor(\n"
                + "	Id int auto_increment primary key,\n"
                + "    Nombre varchar(50),\n"
                + "    Telefono varchar(20),\n"
                + "    Direccion varchar(100),\n"
                + "    Detalles varchar(100)\n"
                + ");\n"
                + "Create table Bitacora(\n"
                + "	User varchar(25),\n"
                + "    FechaIngreso datetime,\n"
                + "    Foreign key (User) references Usuario(User)\n"
                + ");\n"
                + "delimiter $\n"
                + "CREATE PROCEDURE `InsertarNuevoMaterial` (IN `Cod` VARCHAR(15), IN `CM` VARCHAR(50), IN `M` VARCHAR(100), IN `PC` FLOAT, IN `PV` FLOAT, IN `U` VARCHAR(10), IN `C` FLOAT)\n"
                + "  begin\n"
                + "	insert Into Inventario(Codigo,CodMat,Material,PrecioCompra,PrecioVenta,Unidad) Values(Cod,CM,M,PC,PV,U);\n"
                + "    Insert into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,C,0,now());\n"
                + "    Insert Into DepositoTotal(Codigo,CEntrada,CSalida,CSaldo) values(Cod,C,0,C);\n"
                + "		\n"
                + "end $\n"
                + "\n"
                + "\n"
                + "delimiter $\n"
                + "create procedure ModificarMaterial(IN Cod VARCHAR(15), IN CM VARCHAR(50), IN M VARCHAR(100), IN PC FLOAT, IN PV FLOAT, IN U VARCHAR(10), IN C FLOAT)\n"
                + "	begin\n"
                + "	UPDATE Inventario SET CodMat=CM,Material=M,PrecioCompra= PC,PrecioVenta = PV,Unidad=U where Codigo=Cod;\n"
                + "    UPDATE DepositoTotal SET CEntrada=C,CSaldo=C,CSalida=0 where Codigo=Cod;\n"
                + "	end $\n"
                + "\n"
                + "DELIMITER $\n"
                + "CREATE PROCEDURE InsertarDeposito (IN `Cod` VARCHAR(15), IN `CE` FLOAT, IN `CS` FLOAT) \n"
                + " begin\n"
                + "Set @CE1=(Select CEntrada from DepositoTotal where Codigo=Cod);\n"
                + "Set @CS1=(Select CSalida from DepositoTotal where Codigo=Cod);\n"
                + "\n"
                + "	insert Into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,CE,CS,Now());\n"
                + "    Update DepositoTotal SET CEntrada=CE+@CE1, CSalida=CS+@CS1, CSaldo=(CE+@CE1)-(CS+@CS1) where Codigo=Cod;\n"
                + "end$\n"
                + "delimiter $\n"
                + "create procedure IngresoVehiculo(in placa varchar(12),in Nomb varchar(35),in fecha varchar(12))\n"
                + "begin\n"
                + "set @Id=(Select Id from Ayudante where Nombre=Nomb);\n"
                + "Insert Into IngresoVehiculo(Placa,PC,FechaIngreso1,FechaIngreso,FechaSalida1) values(placa,@Id,fecha,NOW(),'null');\n"
                + "end $\n"
                + "\n"
                + "delimiter $\n"
                + "create procedure ModificarIngresoVehiculo(in placa varchar(12),in Nomb varchar(35),in I int)\n"
                + "begin\n"
                + "set @Id=(Select Id from Ayudante where Nombre=Nomb);\n"
                + "Update IngresoVehiculo SET Placa=placa, PC=@Id,FechaIngreso=now() Where Id=I;\n"
                + "end $\n"
                + "\n"
                + "\n"
                + "delimiter $\n"
                + "create procedure Entrega(in Cod varchar(15),in Id int,in Cant float,in Us varchar(25),in Des float,in PV float)\n"
                + "begin\n"
                + "insert into EntregaMateriales(Codigo,IdV,Cantidad,PrecioVenta,Fecha,User,Descuento)values(Cod,Id,Cant,PV,NOW(),Us,Des);\n"
                + "CALL InsertarDeposito(Cod,0,Cant);\n"
                + "end $\n"
                + "delimiter $\n"
                + "delimiter $\n"
                + "Create Procedure Quitar(in I int,in Cod Varchar(15),in Cant float)\n"
                + "begin\n"
                + "Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);\n"
                + "Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);\n"
                + "Delete From EntregaMateriales Where Id=I;\n"
                + "Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@CE-(@C-Cant) Where Codigo=Cod;\n"
                + "end $"
                + "\n"
                + "delimiter $\n"
                + "Create Procedure AgregarRebaja(in Id int,in Des float)\n"
                + "begin\n"
                + "Update EntregaMateriales SEt Descuento=Des where IdV=Id;\n"
                + "end$\n"
                + "\n"
                + "delimiter $\n"
                + "Create Procedure AgregarVenta(in Ide int,Cod varchar(15),\n"
                + "    Cant float,\n"
                + "    PV float)\n"
                + "begin\n"
                + "insert into Venta(Id,Codigo,Cantidad,PrecioVenta,FechaCancelacion)values(Ide,Cod,Cant,PV,'1111-11-11 00:00:00');\n"
                + "CALL InsertarDeposito(Cod,0,Cant);\n"
                + "end$\n"
                + "\n"
                + "delimiter $\n"
                + "Create Procedure QuitarVenta(in Cod varchar(15),\n"
                + "    in Cant float,\n"
                + "    in iss int)\n"
                + "begin\n"
                + "Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);\n"
                + "Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);\n"
                + "Delete From Venta  Where I=iss;\n"
                + "Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@CE-(@C-Cant) Where Codigo=Cod;\n"
                + "end$\n"
                + "\n"
                + "delimiter $\n"
                + "create procedure InsertarDescuento(in Des float,in iss int)\n"
                + "begin\n"
                + "	Update NroVenta Set Descuento=Des Where Id=iss;\n"
                + "end $\n"
                + "\n"
                + "delimiter $\n"
                + "Create Procedure FechaS(in FS varchar(12),in d int)\n"
                + "begin\n"
                + "UPDATE IngresoVehiculo SET FechaSalida1=FS,FechaSalida=now() where Id=d;\n"
                + "end $\n"
                + "Select * From Venta;\n"
                + "Select * From NroVenta;\n"
                + "delimiter $\n"
                + "Create Procedure FeS(in iss int)\n"
                + "begin\n"
                + "UPDATE Venta SET FechaCancelacion=now() where Id=iss;\n"
                + "end $\n"
                + "\n"
                + "delimiter $\n"
                + "Create Procedure RVenta(in Nomb varchar(50),in Us varchar(25))\n"
                + "begin\n"
                + "insert into NroVenta(Nombre,User,Descuento) values(Nomb,Us,0);\n"
                + "end $\n"
                + "\n"
                + "\n"
                + "DELIMITER $$\n"
                + "--\n"
                + "-- Procedimientos\n"
                + "--\n"
                + "CREATE  PROCEDURE AgregarRebaja (IN Id INT, IN Des FLOAT)  begin\n"
                + "Update EntregaMateriales SEt Descuento=Des where IdV=Id;\n"
                + "end$$\n"
                + "\n"
                + "CREATE  PROCEDURE AgregarVenta (IN Ide INT, Cod VARCHAR(15), Cant FLOAT, PV FLOAT)  begin\n"
                + "insert into Venta(Id,Codigo,Cantidad,PrecioVenta,FechaCancelacion)values(Ide,Cod,Cant,PV,'1111-11-11 00:00:00');\n"
                + "CALL InsertarDeposito(Cod,0,Cant);\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE Entrega (IN Cod VARCHAR(15), IN Id INT, IN Cant FLOAT, IN Us VARCHAR(25), IN Des FLOAT, IN PV FLOAT)  begin\n"
                + "insert into EntregaMateriales(Codigo,IdV,Cantidad,PrecioVenta,Fecha,User,Descuento)values(Cod,Id,Cant,PV,NOW(),Us,Des);\n"
                + "CALL InsertarDeposito(Cod,0,Cant);\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE FechaS (IN FS VARCHAR(12), IN d INT)  begin\n"
                + "UPDATE IngresoVehiculo SET FechaSalida1=FS,FechaSalida=now() where Id=d;\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE FeS (IN iss INT)  begin\n"
                + "UPDATE Venta SET FechaCancelacion=now() where Id=iss;\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE IngresoVehiculo (IN placa VARCHAR(12), IN Nomb VARCHAR(35), IN fecha VARCHAR(12))  begin\n"
                + "set @Id=(Select Id from Ayudante where Nombre=Nomb);\n"
                + "Insert Into IngresoVehiculo(Placa,PC,FechaIngreso1,FechaIngreso,FechaSalida1) values(placa,@Id,fecha,NOW(),'null');\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE InsertarDeposito (IN Cod VARCHAR(15), IN CE FLOAT, IN CS FLOAT)  begin\n"
                + "Set @CE1=(Select CEntrada from DepositoTotal where Codigo=Cod);\n"
                + "Set @CS1=(Select CSalida from DepositoTotal where Codigo=Cod);\n"
                + "\n"
                + "	insert Into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,CE,CS,Now());\n"
                + "    Update DepositoTotal SET CEntrada=CE+@CE1, CSalida=CS+@CS1, CSaldo=(CE+@CE1)-(CS+@CS1) where Codigo=Cod;\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE InsertarDescuento (IN Des FLOAT, IN iss INT)  begin\n"
                + "	Update NroVenta Set Descuento=Des Where Id=iss;\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE InsertarNuevoMaterial (IN Cod VARCHAR(15), IN CM VARCHAR(50), IN M VARCHAR(100), IN PC FLOAT, IN PV FLOAT, IN U VARCHAR(10), IN C FLOAT)  begin\n"
                + "	insert Into Inventario(Codigo,CodMat,Material,PrecioCompra,PrecioVenta,Unidad) Values(Cod,CM,M,PC,PV,U);\n"
                + "    Insert into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,C,0,now());\n"
                + "    Insert Into DepositoTotal(Codigo,CEntrada,CSalida,CSaldo) values(Cod,C,0,C);\n"
                + "    \n"
                + "end$$\n"
                + "\n"
                + "CREATE  PROCEDURE InsertarProveedor (IN Nomb VARCHAR(50), IN Telef VARCHAR(20), IN Direc VARCHAR(100), IN Det VARCHAR(100))  begin\n"
                + "Insert Into Proveedor(Nombre,Telefono,Direccion,Detalles)values(Nomb,Telef,Direc,Det);\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE ModificarAyudante (IN I INT, IN Nomb VARCHAR(35), IN St CHAR(1))  begin\n"
                + "	UPDATE Ayudante SET Estado=St, Nombre=Nomb Where Id=I;\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE ModificarIngresoVehiculo (IN placa VARCHAR(12), IN Nomb VARCHAR(35), IN I INT)  begin\n"
                + "set @Id=(Select Id from Ayudante where Nombre=Nomb);\n"
                + "Update IngresoVehiculo SET Placa=placa, PC=@Id,FechaIngreso=now() Where Id=I;\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE ModificarMaterial (IN Cod VARCHAR(15), IN CM VARCHAR(50), IN M VARCHAR(100), IN PC FLOAT, IN PV FLOAT, IN U VARCHAR(10), IN C FLOAT)  begin\n"
                + "	UPDATE Inventario SET CodMat=CM,Material=M,PrecioCompra= PC,PrecioVenta = PV,Unidad=U where Codigo=Cod;\n"
                + "    UPDATE DepositoTotal SET CEntrada=C,CSaldo=C,CSalida=0 where Codigo=Cod;\n"
                + "	end$$\n"
                + "\n"
                + "CREATE PROCEDURE ModificarProveedor (IN Nomb VARCHAR(50), IN Telef VARCHAR(20), IN Direc VARCHAR(100), IN Det VARCHAR(100), IN I INT)  begin\n"
                + "    Update Proveedor Set Nombre=Nomb,Telefono=Telef,Direccion=Direc,Detalles=Det Where Id=I;\n"
                + "    End$$\n"
                + "\n"
                + "CREATE PROCEDURE Quitar (IN I INT, IN Cod VARCHAR(15), IN Cant FLOAT)  begin\n"
                + "Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);\n"
                + "Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);\n"
                + "Set @CS=(Select CSaldo from DepositoTotal Where Codigo=Cod);\n"
                + "Delete From EntregaMateriales Where Id=I;\n"
                + "Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@C-Cant+@CE+Cant Where Codigo=Cod;\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE QuitarVenta (IN Cod VARCHAR(15), IN Cant FLOAT, IN iss INT)  begin\n"
                + "Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);\n"
                + "Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);\n"
                + "Delete From Venta  Where I=iss;\n"
                + "Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@CE-(@C-Cant) Where Codigo=Cod;\n"
                + "end$$\n"
                + "\n"
                + "CREATE PROCEDURE RVenta (IN Nomb VARCHAR(50), IN Us VARCHAR(25))  begin\n"
                + "insert into NroVenta(Nombre,User,Descuento) values(Nomb,Us,0);\n"
                + "end$$\n"
                + "\n"
                + "DELIMITER ;";
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

    public void Alertas() {
        alertalitros();
        masalertar();

    }

    public void alertalitros() {
        String al = CG.getLimiteLitros();
        if (!al.equals("")) {
            JOptionPane.showMessageDialog(IM, "MATERIALES BAJOS Lts \n" + al);
        }
    }

    public void masalertar() {
        String al = CG.getLimiteMetros();
        if (!al.equals("")) {
            JOptionPane.showMessageDialog(IM, "MATERIALES BAJOS Mts, U. \n" + al);
        }
    }
}
