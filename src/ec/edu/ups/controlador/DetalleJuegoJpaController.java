/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.controlador;

import ec.edu.ups.controlador.exceptions.NonexistentEntityException;
import ec.edu.ups.modelo.DetalleJuego;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.edu.ups.modelo.Juego;
import ec.edu.ups.modelo.Jugador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Paul Idrovo
 */
public class DetalleJuegoJpaController implements Serializable {

    public DetalleJuegoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleJuego detalleJuego) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juego juegoCodigo = detalleJuego.getJuegoCodigo();
            if (juegoCodigo != null) {
                juegoCodigo = em.getReference(juegoCodigo.getClass(), juegoCodigo.getCodigo());
                detalleJuego.setJuegoCodigo(juegoCodigo);
            }
            Jugador jugadorCodigo = detalleJuego.getJugadorCodigo();
            if (jugadorCodigo != null) {
                jugadorCodigo = em.getReference(jugadorCodigo.getClass(), jugadorCodigo.getCodigo());
                detalleJuego.setJugadorCodigo(jugadorCodigo);
            }
            em.persist(detalleJuego);
            if (juegoCodigo != null) {
                juegoCodigo.getDetalleJuegoList().add(detalleJuego);
                juegoCodigo = em.merge(juegoCodigo);
            }
            if (jugadorCodigo != null) {
                jugadorCodigo.getDetalleJuegoList().add(detalleJuego);
                jugadorCodigo = em.merge(jugadorCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleJuego detalleJuego) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleJuego persistentDetalleJuego = em.find(DetalleJuego.class, detalleJuego.getId());
            Juego juegoCodigoOld = persistentDetalleJuego.getJuegoCodigo();
            Juego juegoCodigoNew = detalleJuego.getJuegoCodigo();
            Jugador jugadorCodigoOld = persistentDetalleJuego.getJugadorCodigo();
            Jugador jugadorCodigoNew = detalleJuego.getJugadorCodigo();
            if (juegoCodigoNew != null) {
                juegoCodigoNew = em.getReference(juegoCodigoNew.getClass(), juegoCodigoNew.getCodigo());
                detalleJuego.setJuegoCodigo(juegoCodigoNew);
            }
            if (jugadorCodigoNew != null) {
                jugadorCodigoNew = em.getReference(jugadorCodigoNew.getClass(), jugadorCodigoNew.getCodigo());
                detalleJuego.setJugadorCodigo(jugadorCodigoNew);
            }
            detalleJuego = em.merge(detalleJuego);
            if (juegoCodigoOld != null && !juegoCodigoOld.equals(juegoCodigoNew)) {
                juegoCodigoOld.getDetalleJuegoList().remove(detalleJuego);
                juegoCodigoOld = em.merge(juegoCodigoOld);
            }
            if (juegoCodigoNew != null && !juegoCodigoNew.equals(juegoCodigoOld)) {
                juegoCodigoNew.getDetalleJuegoList().add(detalleJuego);
                juegoCodigoNew = em.merge(juegoCodigoNew);
            }
            if (jugadorCodigoOld != null && !jugadorCodigoOld.equals(jugadorCodigoNew)) {
                jugadorCodigoOld.getDetalleJuegoList().remove(detalleJuego);
                jugadorCodigoOld = em.merge(jugadorCodigoOld);
            }
            if (jugadorCodigoNew != null && !jugadorCodigoNew.equals(jugadorCodigoOld)) {
                jugadorCodigoNew.getDetalleJuegoList().add(detalleJuego);
                jugadorCodigoNew = em.merge(jugadorCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleJuego.getId();
                if (findDetalleJuego(id) == null) {
                    throw new NonexistentEntityException("The detalleJuego with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleJuego detalleJuego;
            try {
                detalleJuego = em.getReference(DetalleJuego.class, id);
                detalleJuego.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleJuego with id " + id + " no longer exists.", enfe);
            }
            Juego juegoCodigo = detalleJuego.getJuegoCodigo();
            if (juegoCodigo != null) {
                juegoCodigo.getDetalleJuegoList().remove(detalleJuego);
                juegoCodigo = em.merge(juegoCodigo);
            }
            Jugador jugadorCodigo = detalleJuego.getJugadorCodigo();
            if (jugadorCodigo != null) {
                jugadorCodigo.getDetalleJuegoList().remove(detalleJuego);
                jugadorCodigo = em.merge(jugadorCodigo);
            }
            em.remove(detalleJuego);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleJuego> findDetalleJuegoEntities() {
        return findDetalleJuegoEntities(true, -1, -1);
    }

    public List<DetalleJuego> findDetalleJuegoEntities(int maxResults, int firstResult) {
        return findDetalleJuegoEntities(false, maxResults, firstResult);
    }

    private List<DetalleJuego> findDetalleJuegoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleJuego.class));
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

    public DetalleJuego findDetalleJuego(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleJuego.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleJuegoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleJuego> rt = cq.from(DetalleJuego.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
