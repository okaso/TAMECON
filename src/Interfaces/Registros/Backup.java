/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces.Registros;

import Mysql.CopiaSeguridad;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author HP
 */
public class Backup extends javax.swing.JFrame {

    JFileChooser seleccionado = new JFileChooser();
    File archivo;
    CopiaSeguridad gestion = new CopiaSeguridad();

    public Backup() {
        initComponents();
       
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TxtBackup = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        TxtBackup.setColumns(20);
        TxtBackup.setRows(5);
        TxtBackup.setText("Create database TAMECON;\nUse TAMECON;\nCreate Table Vehiculo(\n    Placa varchar(12) primary key,\n    Modelo varchar(50),\n    Color varchar(20),\n    NombreCliente varchar(80)\n);\nCreate Table Ayudante(\n    Id int auto_increment primary key,\n    Nombre varchar(35),\n    Estado CHAR(1)\n);\n\nCreate table IngresoVehiculo(\n    Id int Auto_Increment primary key,\n    Placa varchar(12),\n    PC int,\n    FechaIngreso datetime,\n    FechaIngreso1 varchar(12),\n    FechaSalida datetime,\n    FechaSalida1 varchar(12),\n    foreign key (Placa) references Vehiculo(Placa),\n    Foreign key (PC) references Ayudante(Id)\n);\nCreate table Inventario(\n    Codigo varchar(15)primary key,\n    CodMat varchar(50),\n    Material varchar(100),\n    PrecioCompra float,\n    PrecioVenta float,\n    Unidad varchar(10)\n);\ncreate table DepositoTotal(\n    Codigo varchar(15)primary key,\n    CEntrada float,\n    CSalida float,\n    CSaldo float,\n    Foreign key (Codigo) references Inventario(Codigo)\n);\nCreate table Deposito(\n    Codigo varchar(15),\n    CEntrada float,\n    CSalida float,\n    Fecha datetime,\n    Foreign key (Codigo) references Inventario(Codigo)\n);\n\nCreate Table EntregaMateriales(\n    Codigo varchar(15),\n    Id int,\n    Cantidad float,\n    PrecioVenta float,\n    Fecha datetime,\n    User varchar(25),\n    Descuento float,\n    Foreign key (User) references Usuario(User),\n    Foreign key(Codigo) references Inventario(Codigo),\n    Foreign key (Id) references IngresoVehiculo(Id)\n);\nCreate table Venta(\n    Codigo varchar(15),\n    Cantidad float,\n    PrecioVenta float,\n    FechaCancelacion Datetime,\n    Descuento float,\n    User varchar(25),\n    Nombre Varchar(50),\n    Foreign key (User) references Usuario(User),\n    Foreign key(Codigo) references Inventario(Codigo)\n);\nCreate Table Usuario(\n    User varchar(25) primary key,\n    Password varchar(60),\n    NombreUsuario varchar(50),\n    Id datetime,\n    Cargo varchar(25)\n);\ncreate table Proveedor(\n    Id int auto_increment primary key,\n    Nombre varchar(50),\n    Telefono varchar(20),\n    Direccion varchar(100),\n    Detalles varchar(100)\n);\n\nCreate table Bitacora(\n    User varchar(25),\n    FechaIngreso datetime,\n    Foreign key (User) references Usuario(User)\n);");
        jScrollPane1.setViewportView(TxtBackup);

        jButton1.setText("GUARDAR COPIA DE SEGURIDAD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (seleccionado.showDialog(Texto(), "Guardar Copia Seguridad") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionado.getSelectedFile();
            

            if (archivo.getName().endsWith("bd") || archivo.getName().endsWith("txt")||archivo.getName().endsWith("")) {
                String contenido = Texto().getText();
                String respuesta = gestion.guardarTexto(archivo, contenido);
                if (contenido != null) {
                    JOptionPane.showMessageDialog(null, respuesta);
                } else {
                    JOptionPane.showMessageDialog(null, "Error");
                }
            }
        }       // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Backup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Backup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Backup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Backup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
                }
                new Backup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TxtBackup;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    public JButton Guardar() {
        return jButton1;
    }

    public JTextArea Texto() {
        return TxtBackup;
    }
}
