/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Paul Idrovo
 */
@Entity
@Table(name = "juego")
@NamedQueries({
    @NamedQuery(name = "Juego.findAll", query = "SELECT j FROM Juego j"),
    @NamedQuery(name = "Juego.findByCodigo", query = "SELECT j FROM Juego j WHERE j.codigo = :codigo"),
    @NamedQuery(name = "Juego.findByGanacia", query = "SELECT j FROM Juego j WHERE j.ganacia = :ganacia"),
    @NamedQuery(name = "Juego.findByNumeroGanador", query = "SELECT j FROM Juego j WHERE j.numeroGanador = :numeroGanador"),
    @NamedQuery(name = "Juego.findByNumeroJugadores", query = "SELECT j FROM Juego j WHERE j.numeroJugadores = :numeroJugadores")})
public class Juego implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "ganacia")
    private Integer ganacia;
    @Column(name = "numero_ganador")
    private Integer numeroGanador;
    @Column(name = "numero_jugadores")
    private Integer numeroJugadores;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "juegoCodigo")
    private List<DetalleJuego> detalleJuegoList;

    public Juego() {
    }

    public Juego(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getGanacia() {
        return ganacia;
    }

    public void setGanacia(Integer ganacia) {
        this.ganacia = ganacia;
    }

    public Integer getNumeroGanador() {
        return numeroGanador;
    }

    public void setNumeroGanador(Integer numeroGanador) {
        this.numeroGanador = numeroGanador;
    }

    public Integer getNumeroJugadores() {
        return numeroJugadores;
    }

    public void setNumeroJugadores(Integer numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
    }

    public List<DetalleJuego> getDetalleJuegoList() {
        return detalleJuegoList;
    }

    public void setDetalleJuegoList(List<DetalleJuego> detalleJuegoList) {
        this.detalleJuegoList = detalleJuegoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Juego)) {
            return false;
        }
        Juego other = (Juego) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.ups.modelo.Juego[ codigo=" + codigo + " ]";
    }
    
}
