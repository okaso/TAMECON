package Interfaces.Registros;

import Consultas.ConsultaGlobal;
import Impresion.ImpresionVentaMateriales;
import Mysql.ConexionBD;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class RegistroVentaArticulos extends javax.swing.JFrame {

    ConsultaGlobal CG;
    ImpresionVentaMateriales IVM;

    public RegistroVentaArticulos() {

        CG = new ConsultaGlobal();
        IVM= new ImpresionVentaMateriales();
        initComponents();
        Acciones();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaVenta = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        TxtCodigo = new javax.swing.JTextField();
        ComboMateriales = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        TxtCD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TxtPC = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TxtPV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TxtNombCliente = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TxtCV = new javax.swing.JTextField();
        BtnVenta = new javax.swing.JButton();
        BtnQuitarVenta = new javax.swing.JButton();
        BtnImprimir = new javax.swing.JButton();
        TxtRebaja = new javax.swing.JTextField();
        BtnDescuento = new javax.swing.JButton();
        LabelNro = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 24)); // NOI18N
        jLabel1.setText("REGISTRO DE VENTA DE ARTICULO");

        TablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(TablaVenta);

        jLabel2.setText("Codigo de Articulo :");

        TxtCodigo.setText("TMC-");

        jLabel4.setText("Cantidad en Deposito :");

        TxtCD.setEnabled(false);

        jLabel5.setText("Precio de Compra :");

        TxtPC.setEnabled(false);

        jLabel6.setText("Precio de Venta :");

        TxtPV.setEnabled(false);

        jLabel7.setText("Nombre de Cliente :");

        TxtNombCliente.setEnabled(false);

        jLabel8.setText("Cantidad de Venta :");

        BtnVenta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnVenta.setText("Agregar");
        BtnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVentaActionPerformed(evt);
            }
        });

        BtnQuitarVenta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnQuitarVenta.setText("Quitar Registro");

        BtnImprimir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnImprimir.setText("Imprimir");

        TxtRebaja.setText("0");

        BtnDescuento.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnDescuento.setText("Agregar Rebaja");

        LabelNro.setText("N");

        jLabel3.setText("Descuento :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                        .addComponent(LabelNro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtNombCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ComboMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TxtCD, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(49, 49, 49)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TxtPC, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TxtPV, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(TxtCV, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnVenta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnQuitarVenta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtRebaja, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnDescuento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtnImprimir)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(LabelNro))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TxtCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(TxtPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(TxtPV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TxtNombCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TxtCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnVenta)
                    .addComponent(BtnQuitarVenta)
                    .addComponent(BtnImprimir)
                    .addComponent(TxtRebaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnDescuento)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnVentaActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroVentaArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroVentaArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroVentaArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVentaArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroVentaArticulos().setVisible(true);
            }
        });
    }

    public void Acciones() {
        TxtCodigo().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Materiales().setModel(new javax.swing.DefaultComboBoxModel<>(CG.getMaterial(TxtCodigo().getText())));
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        Materiales().addActionListener((e) -> {
            TxtCodigo.setText(CG.getCodigo(Materiales().getSelectedItem().toString()));
            String[] datos = CG.getMaterialDetalles(TxtCodigo().getText());
            TxtPC().setText(datos[0]);
            TxtPV().setText(datos[1]);
            CDeposito().setText(datos[3]);
        });
        BtnAgregar().addActionListener((e) -> {
            if (CG.AgregarVenta(TxtCodigo().getText(),
                    TxtCV().getText(),
                    TxtPV().getText(),
                    Nro().getText())) {
                setDatos(CG.getVenta(Nro().getText()));
                TxtCV().setText("0");
                
            } else {
                System.out.println("NOSE PUDO AGREGAR VENTA");
            }
        });
        BtnQuitar().addActionListener((e) -> {
            if(CG.QuitarVenta(TablaVenta.getValueAt(TablaVenta.getSelectedRow(), 1).toString(),
                    TablaVenta.getValueAt(TablaVenta.getSelectedRow(), 4).toString(),
                    TablaVenta.getValueAt(TablaVenta.getSelectedRow(), 0).toString())){
                setDatos(CG.getVenta(Nro().getText()));
            }else{
                System.out.println("NO SE PUDO QUITAR LA VENTA");
            }
        });
        BtnDescuento().addActionListener((e) -> {
            if(CG.AgregarDescuento(TxtRebaja.getText(),Nro().getText())){
                TxtRebaja.setText("0");
                setDatos(CG.getVenta(Nro().getText()));
            }else{
                System.out.println("NO SE PUDO AGREGAR REBAJA");
            }
        });
        BtnImprimir().addActionListener((e) -> {
            IVM.setVisible(true);
            IVM.setTitle(Nro().getText());
            IVM.setDatos(CG.getImpresionVenta(IVM.getTitle()));
            IVM.Fecha();
            IVM.setCliente(CG.getNombre(IVM.getTitle()));
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnDescuento;
    private javax.swing.JButton BtnImprimir;
    private javax.swing.JButton BtnQuitarVenta;
    private javax.swing.JButton BtnVenta;
    private javax.swing.JComboBox<String> ComboMateriales;
    private javax.swing.JLabel LabelNro;
    private javax.swing.JTable TablaVenta;
    private javax.swing.JTextField TxtCD;
    private javax.swing.JTextField TxtCV;
    private javax.swing.JTextField TxtCodigo;
    private javax.swing.JTextField TxtNombCliente;
    private javax.swing.JTextField TxtPC;
    private javax.swing.JTextField TxtPV;
    private javax.swing.JTextField TxtRebaja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    public JTextField TxtCodigo() {
        return TxtCodigo;
    }

    public JTextField TxtPV() {
        return TxtPV;
    }

    public JTextField TxtPC() {
        return TxtPC;
    }

    public JTextField TxtCV() {
        return TxtCV;
    }

    public JTextField CDeposito() {
        return TxtCD;
    }

    public JTextField Descuento() {
        return TxtRebaja;
    }

    public JTextField Nombre() {
        return TxtNombCliente;
    }

    public JButton BtnAgregar() {
        return BtnVenta;
    }

    public JButton BtnQuitar() {
        return BtnQuitarVenta;
    }

    public JButton BtnDescuento() {
        return BtnDescuento;
    }

    public JButton BtnImprimir() {
        return BtnImprimir;
    }

    public JTable TablaVenta() {
        return TablaVenta;
    }

    public JLabel Nro() {
        return LabelNro;
    }

    public void setDatos(DefaultTableModel modeloTabla) {
        TablaVenta.setModel(modeloTabla);
    }

    public javax.swing.JComboBox Materiales() {
        return ComboMateriales;
    }
}
