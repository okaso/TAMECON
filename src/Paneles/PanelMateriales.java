/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paneles;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class PanelMateriales extends javax.swing.JPanel {

    /**
     * Creates new form PanelMateriales
     */
    public PanelMateriales() {
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

        BtnEditarMaterial = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaArticulos = new javax.swing.JTable();
        BtnBuscar = new javax.swing.JButton();
        BtnMaterialesBajos = new javax.swing.JButton();
        TxtBusqueda = new javax.swing.JTextField();
        BtnInventario = new javax.swing.JButton();
        BtnRegistoArticulo = new javax.swing.JButton();
        LabelTotal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BtnActualizar = new javax.swing.JButton();
        BtnIngresar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(996, 622));

        BtnEditarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Manager/edicion.png"))); // NOI18N
        BtnEditarMaterial.setText("Editar");

        TablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(TablaArticulos);

        BtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Manager/buscar.png"))); // NOI18N
        BtnBuscar.setText("Buscar");

        BtnMaterialesBajos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Manager/advertencia.png"))); // NOI18N
        BtnMaterialesBajos.setText("Materiales  Bajos");

        BtnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Manager/Inventory.png"))); // NOI18N
        BtnInventario.setText("Mas Detalles");

        BtnRegistoArticulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Manager/addarticulo.png"))); // NOI18N
        BtnRegistoArticulo.setText("NUEVO");
        BtnRegistoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistoArticuloActionPerformed(evt);
            }
        });

        LabelTotal.setText("TOTAL REGISTROS");

        jLabel1.setFont(new java.awt.Font("Wide Latin", 1, 36)); // NOI18N
        jLabel1.setText("Lista De Materiales");

        jLabel2.setText("Buscar Material Codigo/Nombre");

        BtnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Paneles/Imagenes/refresh.png"))); // NOI18N
        BtnActualizar.setText("Actualizar");

        BtnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Manager/Inventory.png"))); // NOI18N
        BtnIngresar.setText("Ingresar Materiales");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelTotal)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(BtnRegistoArticulo)
                                    .addGap(18, 18, 18)
                                    .addComponent(BtnEditarMaterial)
                                    .addGap(18, 18, 18)
                                    .addComponent(BtnIngresar)
                                    .addGap(31, 31, 31)
                                    .addComponent(BtnActualizar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(TxtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(BtnBuscar))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BtnMaterialesBajos)
                        .addGap(29, 29, 29)
                        .addComponent(BtnInventario)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnMaterialesBajos)
                    .addComponent(BtnInventario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addComponent(BtnBuscar, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnRegistoArticulo)
                        .addComponent(BtnEditarMaterial)
                        .addComponent(BtnActualizar)
                        .addComponent(BtnIngresar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(LabelTotal)
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtnRegistoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistoArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRegistoArticuloActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEditarMaterial;
    private javax.swing.JButton BtnIngresar;
    private javax.swing.JButton BtnInventario;
    private javax.swing.JButton BtnMaterialesBajos;
    private javax.swing.JButton BtnRegistoArticulo;
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JTable TablaArticulos;
    private javax.swing.JTextField TxtBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public JButton BtnBuscar() {
        return BtnBuscar;
    }
    
    public JTable TablaArticulos(){
        return TablaArticulos;
    }

    public void setDatos(DefaultTableModel modeloTabla, int total) {
        TablaArticulos.setModel(modeloTabla);
        LabelTotal.setText("Total Registros: " + total);
    }

    public JButton BtnEditarMaterial() {
        return BtnEditarMaterial;
    }

    public JButton BtnDetalles() {
        return BtnInventario;
    }

    public JButton BtnMaterialBajo() {
        return BtnMaterialesBajos;
    }
    public JButton BtnActualizar(){
        return BtnActualizar;
    }
    public JButton BtnNuevo() {
        return BtnRegistoArticulo;
    }
    public JButton BtnIngresoMateriales() {
        return BtnIngresar;
    }
    
    public JTextField TxtBusqueda() {
        return TxtBusqueda;
    }
}
