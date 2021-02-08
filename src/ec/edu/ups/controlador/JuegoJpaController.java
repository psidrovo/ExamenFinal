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
import ec.edu.ups.modelo.Juego;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Paul Idrovo
 */
public class JuegoJpaController implements Serializable {

    public JuegoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Juego juego) {
        if (juego.getDetalleJuegoList() == null) {
            juego.setDetalleJuegoList(new ArrayList<DetalleJuego>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DetalleJuego> attachedDetalleJuegoList = new ArrayList<DetalleJuego>();
            for (DetalleJuego detalleJuegoListDetalleJuegoToAttach : juego.getDetalleJuegoList()) {
                detalleJuegoListDetalleJuegoToAttach = em.getReference(detalleJuegoListDetalleJuegoToAttach.getClass(), detalleJuegoListDetalleJuegoToAttach.getId());
                attachedDetalleJuegoList.add(detalleJuegoListDetalleJuegoToAttach);
            }
            juego.setDetalleJuegoList(attachedDetalleJuegoList);
            em.persist(juego);
            for (DetalleJuego detalleJuegoListDetalleJuego : juego.getDetalleJuegoList()) {
                Juego oldJuegoCodigoOfDetalleJuegoListDetalleJuego = detalleJuegoListDetalleJuego.getJuegoCodigo();
                detalleJuegoListDetalleJuego.setJuegoCodigo(juego);
                detalleJuegoListDetalleJuego = em.merge(detalleJuegoListDetalleJuego);
                if (oldJuegoCodigoOfDetalleJuegoListDetalleJuego != null) {
                    oldJuegoCodigoOfDetalleJuegoListDetalleJuego.getDetalleJuegoList().remove(detalleJuegoListDetalleJuego);
                    oldJuegoCodigoOfDetalleJuegoListDetalleJuego = em.merge(oldJuegoCodigoOfDetalleJuegoListDetalleJuego);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Juego juego) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juego persistentJuego = em.find(Juego.class, juego.getCodigo());
            List<DetalleJuego> detalleJuegoListOld = persistentJuego.getDetalleJuegoList();
            List<DetalleJuego> detalleJuegoListNew = juego.getDetalleJuegoList();
            List<String> illegalOrphanMessages = null;
            for (DetalleJuego detalleJuegoListOldDetalleJuego : detalleJuegoListOld) {
                if (!detalleJuegoListNew.contains(detalleJuegoListOldDetalleJuego)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleJuego " + detalleJuegoListOldDetalleJuego + " since its juegoCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DetalleJuego> attachedDetalleJuegoListNew = new ArrayList<DetalleJuego>();
            for (DetalleJuego detalleJuegoListNewDetalleJuegoToAttach : detalleJuegoListNew) {
                detalleJuegoListNewDetalleJuegoToAttach = em.getReference(detalleJuegoListNewDetalleJuegoToAttach.getClass(), detalleJuegoListNewDetalleJuegoToAttach.getId());
                attachedDetalleJuegoListNew.add(detalleJuegoListNewDetalleJuegoToAttach);
            }
            detalleJuegoListNew = attachedDetalleJuegoListNew;
            juego.setDetalleJuegoList(detalleJuegoListNew);
            juego = em.merge(juego);
            for (DetalleJuego detalleJuegoListNewDetalleJuego : detalleJuegoListNew) {
                if (!detalleJuegoListOld.contains(detalleJuegoListNewDetalleJuego)) {
                    Juego oldJuegoCodigoOfDetalleJuegoListNewDetalleJuego = detalleJuegoListNewDetalleJuego.getJuegoCodigo();
                    detalleJuegoListNewDetalleJuego.setJuegoCodigo(juego);
                    detalleJuegoListNewDetalleJuego = em.merge(detalleJuegoListNewDetalleJuego);
                    if (oldJuegoCodigoOfDetalleJuegoListNewDetalleJuego != null && !oldJuegoCodigoOfDetalleJuegoListNewDetalleJuego.equals(juego)) {
                        oldJuegoCodigoOfDetalleJuegoListNewDetalleJuego.getDetalleJuegoList().remove(detalleJuegoListNewDetalleJuego);
                        oldJuegoCodigoOfDetalleJuegoListNewDetalleJuego = em.merge(oldJuegoCodigoOfDetalleJuegoListNewDetalleJuego);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = juego.getCodigo();
                if (findJuego(id) == null) {
                    throw new NonexistentEntityException("The juego with id " + id + " no longer exists.");
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
            Juego juego;
            try {
                juego = em.getReference(Juego.class, id);
                juego.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juego with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleJuego> detalleJuegoListOrphanCheck = juego.getDetalleJuegoList();
            for (DetalleJuego detalleJuegoListOrphanCheckDetalleJuego : detalleJuegoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Juego (" + juego + ") cannot be destroyed since the DetalleJuego " + detalleJuegoListOrphanCheckDetalleJuego + " in its detalleJuegoList field has a non-nullable juegoCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(juego);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Juego> findJuegoEntities() {
        return findJuegoEntities(true, -1, -1);
    }

    public List<Juego> findJuegoEntities(int maxResults, int firstResult) {
        return findJuegoEntities(false, maxResults, firstResult);
    }

    private List<Juego> findJuegoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Juego.class));
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

    public Juego findJuego(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Juego.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuegoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Juego> rt = cq.from(Juego.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
