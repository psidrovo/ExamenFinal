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
@Table(name = "jugador")
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
    @NamedQuery(name = "Jugador.findByCodigo", query = "SELECT j FROM Jugador j WHERE j.codigo = :codigo"),
    @NamedQuery(name = "Jugador.findByCartera", query = "SELECT j FROM Jugador j WHERE j.cartera = :cartera")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "cartera")
    private Integer cartera;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jugadorCodigo")
    private List<DetalleJuego> detalleJuegoList;

    public Jugador() {
    }

    public Jugador(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCartera() {
        return cartera;
    }

    public void setCartera(Integer cartera) {
        this.cartera = cartera;
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
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.ups.modelo.Jugador[ codigo=" + codigo + " ]";
    }
    
}
