/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Paul Idrovo
 */
@Entity
@Table(name = "detalle_juego")
@NamedQueries({
    @NamedQuery(name = "DetalleJuego.findAll", query = "SELECT d FROM DetalleJuego d"),
    @NamedQuery(name = "DetalleJuego.findById", query = "SELECT d FROM DetalleJuego d WHERE d.id = :id"),
    @NamedQuery(name = "DetalleJuego.findByValorApuesta", query = "SELECT d FROM DetalleJuego d WHERE d.valorApuesta = :valorApuesta"),
    @NamedQuery(name = "DetalleJuego.findByEstado", query = "SELECT d FROM DetalleJuego d WHERE d.estado = :estado")})
public class DetalleJuego implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "valor_apuesta")
    private Integer valorApuesta;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "juego_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Juego juegoCodigo;
    @JoinColumn(name = "jugador_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Jugador jugadorCodigo;

    public DetalleJuego() {
    }

    public DetalleJuego(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValorApuesta() {
        return valorApuesta;
    }

    public void setValorApuesta(Integer valorApuesta) {
        this.valorApuesta = valorApuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Juego getJuegoCodigo() {
        return juegoCodigo;
    }

    public void setJuegoCodigo(Juego juegoCodigo) {
        this.juegoCodigo = juegoCodigo;
    }

    public Jugador getJugadorCodigo() {
        return jugadorCodigo;
    }

    public void setJugadorCodigo(Jugador jugadorCodigo) {
        this.jugadorCodigo = jugadorCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleJuego)) {
            return false;
        }
        DetalleJuego other = (DetalleJuego) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.ups.modelo.DetalleJuego[ id=" + id + " ]";
    }
    
}
