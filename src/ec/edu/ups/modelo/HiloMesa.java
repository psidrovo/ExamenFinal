/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import ec.edu.ups.controlador.DetalleJuegoJpaController;
import ec.edu.ups.controlador.JuegoJpaController;
import ec.edu.ups.utils.Utils;
import ec.edu.ups.vista.VistaPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author Paul Idrovo
 */
public class HiloMesa extends Thread {

    private int tiempoEjecucion;
    private int resultado;
    private DetalleJuegoJpaController controladorDetalleJuego;
    private JuegoJpaController controladorJuego;
    private List<Thread> listaJugadoresHilos;
    private List<JLabel> labelValorApuesta;
    private List<JLabel> labelCarteraJugador;
    private List<JLabel> labelCodigoJugador;    
    private List<JLabel> labelContadorJugadas;
    private List<JLabel> labelNumeroApostar;
    private List<JComboBox> comboBoxModalidad;
    private EntityManagerFactory emf;
    private VistaPrincipal vista;

    public HiloMesa(int tiempoEjecucion, List<JLabel> labelValorApuesta, List<JLabel> labelCarteraJugador, List<JLabel> labelCodigoJugador,List<JLabel> labelContadorJugadas, 
            List<JLabel> labelNumeroApostar,List<JComboBox> comboBoxModalidad,VistaPrincipal vista) {
        this.tiempoEjecucion = tiempoEjecucion;
        this.labelValorApuesta = labelValorApuesta;
        this.labelCarteraJugador = labelCarteraJugador;
        this.labelCodigoJugador = labelCodigoJugador;
        this.labelContadorJugadas=labelContadorJugadas;
        this.labelNumeroApostar=labelNumeroApostar;
        this.comboBoxModalidad = comboBoxModalidad;
         emf = Utils.getEntityManagerFactory();
        this.controladorDetalleJuego=new DetalleJuegoJpaController(emf);
        this.controladorJuego=new JuegoJpaController(emf);
        this.vista=vista;
    }

    @Override
    public void run() {
        while (VistaPrincipal.ejecucion) {
            try {
                resultado = (int) (Math.random() * 36);
                System.out.println("NUMERO GANADOR " + resultado);
                listaJugadoresHilos = new ArrayList<>();
                Juego nuevaPartida = new Juego();
                nuevaPartida.setNumeroGanador(resultado);
                nuevaPartida.setNumeroJugadores(labelCarteraJugador.size());
                controladorJuego.create(nuevaPartida);
                int codigoPartida = controladorJuego.getJuegoCount();
                for (int i = 0; i < labelCarteraJugador.size(); i++) {
                    String codigo = labelCodigoJugador.get(i).getText();
                    String cartera = labelCarteraJugador.get(i).getText();
                    int codigoJugador = Integer.parseInt(codigo.substring(codigo.indexOf("(") + 1, codigo.indexOf(")")));
                    int carteraJugador = Integer.parseInt(cartera.substring(3));
                    listaJugadoresHilos.add(new Thread(new HiloJugador(codigoPartida,resultado,codigoJugador, carteraJugador, i, comboBoxModalidad.get(i).getSelectedItem().toString(),
                            labelValorApuesta, labelCarteraJugador, labelCodigoJugador, labelContadorJugadas,labelNumeroApostar)));
                }  
                for (Thread listaJugadoresHilo : listaJugadoresHilos) {
                    listaJugadoresHilo.start();
                }
                Thread.sleep(tiempoEjecucion);
                vista.cargarListaJugadores();
                vista.cargarPartidasHistorial();
                vista.cargarDatosPartida();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloMesa.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
