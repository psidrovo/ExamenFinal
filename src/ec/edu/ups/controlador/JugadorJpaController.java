/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.controlador;

import ec.edu.ups.controlador.exceptions.IllegalOrphanException;
import ec.edu.ups.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.edu.ups.modelo.DetalleJuego;
import ec.edu.ups.modelo.Jugador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Paul Idrovo
 */
public class JugadorJpaController implements Serializable {

    public JugadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jugador jugador) {
        if (jugador.getDetalleJuegoList() == null) {
            jugador.setDetalleJuegoList(new ArrayList<DetalleJuego>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DetalleJuego> attachedDetalleJuegoList = new ArrayList<DetalleJuego>();
            for (DetalleJuego detalleJuegoListDetalleJuegoToAttach : jugador.getDetalleJuegoList()) {
                detalleJuegoListDetalleJuegoToAttach = em.getReference(detalleJuegoListDetalleJuegoToAttach.getClass(), detalleJuegoListDetalleJuegoToAttach.getId());
                attachedDetalleJuegoList.add(detalleJuegoListDetalleJuegoToAttach);
            }
            jugador.setDetalleJuegoList(attachedDetalleJuegoList);
            em.persist(jugador);
            for (DetalleJuego detalleJuegoListDetalleJuego : jugador.getDetalleJuegoList()) {
                Jugador oldJugadorCodigoOfDetalleJuegoListDetalleJuego = detalleJuegoListDetalleJuego.getJugadorCodigo();
                detalleJuegoListDetalleJuego.setJugadorCodigo(jugador);
                detalleJuegoListDetalleJuego = em.merge(detalleJuegoListDetalleJuego);
                if (oldJugadorCodigoOfDetalleJuegoListDetalleJuego != null) {
                    oldJugadorCodigoOfDetalleJuegoListDetalleJuego.getDetalleJuegoList().remove(detalleJuegoListDetalleJuego);
                    oldJugadorCodigoOfDetalleJuegoListDetalleJuego = em.merge(oldJugadorCodigoOfDetalleJuegoListDetalleJuego);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jugador jugador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador persistentJugador = em.find(Jugador.class, jugador.getCodigo());            
            List<String> illegalOrphanMessages = null;
            jugador = em.merge(jugador);
            
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }           
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jugador.getCodigo();
                if (findJugador(id) == null) {
                    throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador jugador;
            try {
                jugador = em.getReference(Jugador.class, id);
                jugador.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleJuego> detalleJuegoListOrphanCheck = jugador.getDetalleJuegoList();
            for (DetalleJuego detalleJuegoListOrphanCheckDetalleJuego : detalleJuegoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jugador (" + jugador + ") cannot be destroyed since the DetalleJuego " + detalleJuegoListOrphanCheckDetalleJuego + " in its detalleJuegoList field has a non-nullable jugadorCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(jugador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jugador> findJugadorEntities() {
        return findJugadorEntities(true, -1, -1);
    }

    public List<Jugador> findJugadorEntities(int maxResults, int firstResult) {
        return findJugadorEntities(false, maxResults, firstResult);
    }

    private List<Jugador> findJugadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jugador.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Jugador findJugador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugador.class, id);
        } finally {
            em.close();
        }
    }

    public int getJugadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jugador> rt = cq.from(Jugador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
