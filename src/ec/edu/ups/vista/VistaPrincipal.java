/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.vista;

import ec.edu.ups.controlador.DetalleJuegoJpaController;
import ec.edu.ups.controlador.JuegoJpaController;
import ec.edu.ups.controlador.JugadorJpaController;
import ec.edu.ups.modelo.DetalleJuego;
import ec.edu.ups.modelo.HiloJugador;
import ec.edu.ups.modelo.HiloMesa;
import ec.edu.ups.modelo.Juego;
import ec.edu.ups.modelo.Jugador;
import ec.edu.ups.utils.Utils;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 *
 * @author Paul Idrovo
 */
public class VistaPrincipal extends javax.swing.JFrame {

    private DetalleJuegoJpaController controladorDetalleJuego;
    private JuegoJpaController controladorJuego;
    private JugadorJpaController controladorJugador;
    private EntityManagerFactory emf;
    private List<JLabel> labelCodigoJugador;
    private List<JLabel> labelCarteraJugador;
    private List<JLabel> labelContadorJugadas;
    private List<JLabel> labelValorApuesta;
    private List<JLabel> labelNumeroApostar;
    private List<JComboBox> comboBoxModalidad;
    private List<Thread> listaJugadoresHilos;
    public static Integer resultado;
    public static Boolean cobros;
    public static Boolean ejecucion;

    public VistaPrincipal() {
        initComponents();
        emf = Utils.getEntityManagerFactory();
        controladorDetalleJuego = new DetalleJuegoJpaController(emf);
        controladorJuego = new JuegoJpaController(emf);
        controladorJugador = new JugadorJpaController(emf);
        this.setLocationRelativeTo(null);
        cargarListaJugadores();
        cargarPartidasHistorial();
        cargarDatosPartida();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spnNuevosJugadores = new javax.swing.JSpinner();
        btnCrearJugadores = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstDetalleJugadas = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstRegistroJuegosCasa = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstJugadoresRegistrados = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        spnTiempo = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        btnAgregarMesa = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        pnlMesaJugadores = new javax.swing.JPanel();
        btnIniciar = new javax.swing.JButton();
        btnIniciar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RULETA");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR JUGADORES"));
        jPanel1.setMaximumSize(new java.awt.Dimension(338, 75));
        jPanel1.setMinimumSize(new java.awt.Dimension(338, 75));

        jLabel1.setText("NUEVOS JUGADORES:");

        spnNuevosJugadores.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        btnCrearJugadores.setText("CREAR");
        btnCrearJugadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearJugadoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addComponent(spnNuevosJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCrearJugadores)
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spnNuevosJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCrearJugadores))
                .addGap(15, 15, 15))
        );

        lstDetalleJugadas.setBorder(javax.swing.BorderFactory.createTitledBorder("DETALLE JUGADAS"));
        jScrollPane1.setViewportView(lstDetalleJugadas);

        lstRegistroJuegosCasa.setBorder(javax.swing.BorderFactory.createTitledBorder("REGISTRO JUEGOS CASA"));
        lstRegistroJuegosCasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstRegistroJuegosCasaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lstRegistroJuegosCasa);

        lstJugadoresRegistrados.setBorder(javax.swing.BorderFactory.createTitledBorder("JUGADORES REGISTRADOS"));
        jScrollPane3.setViewportView(lstJugadoresRegistrados);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/ups/imagenes/MesaNumeros.jpg"))); // NOI18N

        spnTiempo.setModel(new javax.swing.SpinnerNumberModel(2000, 1, null, 100));

        jLabel3.setText("TIEMPO DE EJECUCION");

        btnAgregarMesa.setText("AGREGAR MESA");
        btnAgregarMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMesaActionPerformed(evt);
            }
        });

        btnEliminar.setText("ELIMINAR MESA");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        pnlMesaJugadores.setBorder(javax.swing.BorderFactory.createTitledBorder("MESA JUGADORES"));
        pnlMesaJugadores.setMaximumSize(new java.awt.Dimension(890, 130));
        pnlMesaJugadores.setMinimumSize(new java.awt.Dimension(890, 130));
        pnlMesaJugadores.setPreferredSize(new java.awt.Dimension(890, 130));

        javax.swing.GroupLayout pnlMesaJugadoresLayout = new javax.swing.GroupLayout(pnlMesaJugadores);
        pnlMesaJugadores.setLayout(pnlMesaJugadoresLayout);
        pnlMesaJugadoresLayout.setHorizontalGroup(
            pnlMesaJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1089, Short.MAX_VALUE)
        );
        pnlMesaJugadoresLayout.setVerticalGroup(
            pnlMesaJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 152, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(pnlMesaJugadores);

        btnIniciar.setText("JUGAR");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnIniciar1.setText("DETENER");
        btnIniciar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1103, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAgregarMesa)
                                        .addGap(73, 73, 73)
                                        .addComponent(btnEliminar))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(spnTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnIniciar)
                                .addGap(18, 18, 18)
                                .addComponent(btnIniciar1)))
                        .addGap(269, 269, 269)
                        .addComponent(jLabel2)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarMesa)
                                .addComponent(btnEliminar))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(spnTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnIniciar)
                            .addComponent(btnIniciar1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearJugadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearJugadoresActionPerformed
        for (int i = 0; i < (int) spnNuevosJugadores.getValue(); i++) {
            Jugador nuevoJugador = new Jugador();
            nuevoJugador.setCartera(1000);
            controladorJugador.create(nuevoJugador);
            cargarListaJugadores();            
            cargarPartidasHistorial();
            cargarDatosPartida();
        }
    }//GEN-LAST:event_btnCrearJugadoresActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        pnlMesaJugadores.removeAll();
        pnlMesaJugadores.updateUI();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMesaActionPerformed
        pnlMesaJugadores.removeAll();
        List<String> listaJugadoresPartida = lstJugadoresRegistrados.getSelectedValuesList();
        labelCodigoJugador = new ArrayList<>();
        labelCarteraJugador = new ArrayList<>();
        labelContadorJugadas = new ArrayList<>();
        comboBoxModalidad = new ArrayList<>();
        labelValorApuesta = new ArrayList<>();
        labelNumeroApostar = new ArrayList<>();

        int posicionX = 20;
        for (String jugador : listaJugadoresPartida) {
            int posicionCorte = jugador.indexOf(")") + 1;
            JLabel lblJugador = new JLabel(jugador.substring(0, posicionCorte));
            lblJugador.setBounds(posicionX, 20, 130, 30);
            lblJugador.setFont(new Font("Verdana", Font.BOLD, 10)); //ESTILO TEXTO
            pnlMesaJugadores.add(lblJugador);
            labelCodigoJugador.add(lblJugador);

            posicionCorte = jugador.indexOf("RA: ") + 3;
            JLabel lblCartera = new JLabel("$ " + jugador.substring(posicionCorte));
            lblCartera.setBounds(posicionX, 40, 130, 30);
            lblCartera.setFont(new Font("Verdana", Font.BOLD, 10)); //ESTILO TEXTO
            pnlMesaJugadores.add(lblCartera);
            labelCarteraJugador.add(lblCartera);

            JLabel lblJugadas = new JLabel("P. MESA: " + 0);
            lblJugadas.setBounds(posicionX, 60, 130, 30);
            lblJugadas.setFont(new Font("Verdana", Font.BOLD, 10)); //ESTILO TEXTO
            pnlMesaJugadores.add(lblJugadas);
            labelContadorJugadas.add(lblJugadas);

            JComboBox<String> cmbTipoJugada = new JComboBox<>();
            cmbTipoJugada.setBounds(posicionX, 90, 130, 20);
            cmbTipoJugada.setFont(new Font("Verdana", Font.PLAIN, 9));
            cmbTipoJugada.addItem("NUM. CONCRETO");
            cmbTipoJugada.addItem("PAR IMPAR");
            cmbTipoJugada.addItem("MARTINGALA");
            int tipoTransaccion = (int) (Math.random() * 3);
            cmbTipoJugada.setSelectedIndex(tipoTransaccion);
            pnlMesaJugadores.add(cmbTipoJugada);
            comboBoxModalidad.add(cmbTipoJugada);

            JLabel lblApuestas = new JLabel("10");
            lblApuestas.setBounds(posicionX, 110, 130, 30);
            lblApuestas.setFont(new Font("Verdana", Font.PLAIN, 10)); //ESTILO TEXTO
            pnlMesaJugadores.add(lblApuestas);
            labelValorApuesta.add(lblApuestas);

            JLabel lblNumero = new JLabel((int) (Math.random() * 36) + "");
            lblNumero.setBounds(posicionX, 130, 130, 30);
            lblNumero.setFont(new Font("Verdana", Font.BOLD, 10)); //ESTILO TEXTO
            pnlMesaJugadores.add(lblNumero);
            labelNumeroApostar.add(lblNumero);

            posicionX += 145;
        }
        pnlMesaJugadores.setPreferredSize(new java.awt.Dimension(posicionX, pnlMesaJugadores.getHeight() - 40));
        pnlMesaJugadores.updateUI();

    }//GEN-LAST:event_btnAgregarMesaActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        ejecucion=true;
        HiloMesa mesaHilo = new HiloMesa((int) spnTiempo.getValue(), labelValorApuesta, labelCarteraJugador, labelCodigoJugador, labelContadorJugadas, labelNumeroApostar, comboBoxModalidad,this);
        Thread mesaHiloThread = new Thread(mesaHilo);
        mesaHiloThread.start();         
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnIniciar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciar1ActionPerformed
        ejecucion=false;
    }//GEN-LAST:event_btnIniciar1ActionPerformed

    private void lstRegistroJuegosCasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstRegistroJuegosCasaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lstRegistroJuegosCasaMouseClicked

    public void cargarListaJugadores() {
        List<Jugador> lstJugadores = controladorJugador.findJugadorEntities();
        int numeroItems = lstJugadores.size();
        String[] imp = new String[numeroItems];
        int i = 0;
        for (Jugador listaJugadores : controladorJugador.findJugadorEntities()) {
            imp[i] = "JUGADOR # (" + listaJugadores.getCodigo() + ") - CARTERA: " + listaJugadores.getCartera();
            i++;
        }
        lstJugadoresRegistrados.setListData(imp);
    }
    public void cargarPartidasHistorial() {
        List<Juego> listaPartidas = controladorJuego.findJuegoEntities();

        int numeroItems = listaPartidas.size();
        String[] imp = new String[numeroItems];
        int i = 0;
        for (Juego listPartidas : listaPartidas) {
            imp[i] = "PARTIDA # (" + listPartidas.getCodigo() + ") - NUMERO GANADOR: " + listPartidas.getNumeroGanador();
            i++;
        }
        lstRegistroJuegosCasa.setListData(imp);
    }
    public void cargarDatosPartida() {
        List<DetalleJuego> lstDetalleJuegos = controladorDetalleJuego.findDetalleJuegoEntities();
        int numeroItems = lstDetalleJuegos.size();
        String[] imp = new String[numeroItems];
        int i = 0;
        for (DetalleJuego listaDetalle : lstDetalleJuegos) {
            imp[i] = "JUGADOR # (" + listaDetalle.getJugadorCodigo().getCodigo()+ ") - ESTADO: " + listaDetalle.getEstado() + " - VALOR: "+listaDetalle.getValorApuesta()+"- PARTIDA: "+listaDetalle.getJuegoCodigo().getCodigo();
            i++;
        }
        lstDetalleJugadas.setListData(imp);
    }

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
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            } catch (Exception e) {

            }
            new VistaPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarMesa;
    private javax.swing.JButton btnCrearJugadores;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnIniciar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<String> lstDetalleJugadas;
    private javax.swing.JList<String> lstJugadoresRegistrados;
    private javax.swing.JList<String> lstRegistroJuegosCasa;
    private javax.swing.JPanel pnlMesaJugadores;
    private javax.swing.JSpinner spnNuevosJugadores;
    private javax.swing.JSpinner spnTiempo;
    // End of variables declaration//GEN-END:variables
}
