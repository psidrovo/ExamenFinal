/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import ec.edu.ups.controlador.DetalleJuegoJpaController;
import ec.edu.ups.controlador.JugadorJpaController;
import ec.edu.ups.controlador.exceptions.NonexistentEntityException;
import ec.edu.ups.utils.Utils;
import ec.edu.ups.vista.VistaPrincipal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.JLabel;

/**
 *
 * @author Paul Idrovo
 */
public class HiloJugador extends Thread {

    private int codigoJugador;
    private int carteraJugador;
    private int posicion;
    private String modoJuego;
    private int resultado;
    private List<JLabel> labelValorApuesta;
    private List<JLabel> labelCarteraJugador;
    private List<JLabel> labelCodigoJugador;
    private List<JLabel> labelContadorJugadas;
    private List<JLabel> labelNumeroApostar;
    private JugadorJpaController controladorJugador;
    private EntityManagerFactory emf;
    private int codigoPartida;
    private DetalleJuegoJpaController controladorDetalleJuego;

    public HiloJugador(int codigoPartida, int resultado, int codigoJugador, int carteraJugador, int posicion, String modoJuego, List<JLabel> labelValorApuesta, List<JLabel> labelCarteraJugador,
            List<JLabel> labelCodigoJugador, List<JLabel> labelContadorJugadas, List<JLabel> labelNumeroApostar) {
        emf = Utils.getEntityManagerFactory();
        this.codigoPartida = codigoPartida;
        this.controladorJugador = new JugadorJpaController(emf);
        this.codigoJugador = codigoJugador;
        this.carteraJugador = carteraJugador;
        this.posicion = posicion;
        this.modoJuego = modoJuego;
        this.labelValorApuesta = labelValorApuesta;
        this.labelCarteraJugador = labelCarteraJugador;
        this.labelCodigoJugador = labelCodigoJugador;
        this.labelContadorJugadas = labelContadorJugadas;
        this.labelNumeroApostar = labelNumeroApostar;
        this.resultado = resultado;
        this.controladorDetalleJuego = new DetalleJuegoJpaController(emf);
        if (!modoJuego.equals("MARTINGALA")) {
            apostarNumero();
        }
    }

    @Override
    public void run() {
        int apuestaNum = Integer.parseInt(labelNumeroApostar.get(posicion).getText());
        int valorApuesta = Integer.parseInt(labelValorApuesta.get(posicion).getText());
        String estado = "";
        if (carteraJugador > 0) {

            switch (modoJuego) {
                case "NUM. CONCRETO":
                    if (apuestaNum == resultado) {
                        carteraJugador += 360;
                        System.out.println("GANA JUGADOR " + codigoJugador);
                        estado = "GANA";
                    } else {
                        carteraJugador -= valorApuesta;
                        System.out.println("PIERDE JUGADOR " + codigoJugador);
                        estado = "PIERDE";
                    }
                    break;
                case "PAR IMPAR":
                    if (apuestaNum % 2 == resultado % 2) {
                        carteraJugador += 20;
                        System.out.println("GANA JUGADOR " + codigoJugador);
                        estado = "GANA";
                    } else {
                        carteraJugador -= valorApuesta;
                        System.out.println("PIERDE JUGADOR " + codigoJugador);
                        estado = "PIERDE";
                    }
                    break;
                case "MARTINGALA":
                    if (apuestaNum == resultado) {
                        carteraJugador += 360;
                        System.out.println("GANA JUGADOR " + codigoJugador);
                        estado = "GANA";
                    } else {
                        carteraJugador -= valorApuesta;
                        valorApuesta = valorApuesta * 2;
                        labelValorApuesta.get(posicion).setText(valorApuesta + "");
                        System.out.println("PIERDE JUGADOR " + codigoJugador);
                        estado = "PIERDE";
                    }
                    break;
            }
        }
        Jugador actualizarJugador = new Jugador(codigoJugador);
        actualizarJugador.setCartera(carteraJugador);
        actualizarJugador.setDetalleJuegoList(null);
        DetalleJuego nueDetalleJuego = new DetalleJuego();
        nueDetalleJuego.setEstado(estado);
        nueDetalleJuego.setJuegoCodigo(new Juego(codigoPartida));
        nueDetalleJuego.setJugadorCodigo(new Jugador(codigoJugador));
        nueDetalleJuego.setValorApuesta(Integer.parseInt(labelValorApuesta.get(posicion).getText()));
        controladorDetalleJuego.create(nueDetalleJuego);
        try {
            controladorJugador.edit(actualizarJugador);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(HiloJugador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HiloJugador.class.getName()).log(Level.SEVERE, null, ex);
        }

        labelCarteraJugador.get(posicion).setText("$  " + carteraJugador);
    }

    public void apostarNumero() {
        int numeroApuesta = (int) (Math.random() * 35) + 1;
        labelNumeroApostar.get(posicion).setText(numeroApuesta + "");
        System.out.println("Jugador " + codigoJugador + " numero " + numeroApuesta);
    }

}
