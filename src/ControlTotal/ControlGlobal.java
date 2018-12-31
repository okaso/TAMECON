/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlTotal;

import Consultas.ConsultaGlobal;
import Interfaces.InterfazManager;
import Interfaces.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class ControlGlobal implements ActionListener, KeyListener {

    Login login;
    ConsultaGlobal CG;
    InterfazManager IM;

    public ControlGlobal() {
        /*Iniciando COnexion Con la Base De Datos*/
        CG = new ConsultaGlobal();

        login = new Login();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        /*Objetos de interfaz login*/
        login.BtnIngresar().addActionListener(this);
        login.TxtPassword().addKeyListener(this);
        login.TxtUsuario().addKeyListener(this);
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
                } else {
                    if (Usuario[2].equals("Manager")) {
                        Manager("Usuario : " + Usuario[1] + "  Cargo : " + Usuario[2] + "   Ultimo Acceso : " + Usuario[3]);
                    }
                    if (Usuario[2].equals("Supervisor")) {
                    }
                    if (Usuario[2].equals("Administrador")) {
                    }

                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /*FIN ACCIOBES BOTONES Y TEXTOS*/

 /*                      Interfaz Manager       */
    public void Manager(String title) {
        IM = new InterfazManager();
        IM.setLocationRelativeTo(null);
        IM.setVisible(true);
        IM.setTitle(title);
        IM.BtnBackup().addActionListener(this);
        IM.BtnBuscar().addActionListener(this);
        IM.BtnEditarMaterial().addActionListener(this);
        IM.BtnInventario().addActionListener(this);
        IM.BtnMaterialBajo().addActionListener(this);
        IM.BtnProveedor().addActionListener(this);
        IM.BtnRegistroArticulo().addActionListener(this);
        IM.BtnRegistroAyudante().addActionListener(this);
        IM.BtnRegistroVehiculos().addActionListener(this);
        IM.BtnRegistroUsuario().addActionListener(this);
        IM.BtnVentas().addActionListener(this);
        IM.TxtBusqueda().addKeyListener(this);
        cargarDatos(IM.TxtBusqueda().getText());

    }

    public void cargarDatos(String textoBusqueda) {
        this.IM.setDatos(this.CG.getLista(textoBusqueda), this.CG.getTotal());
    }

    /*                    FIn interfaz Manager     */
}
