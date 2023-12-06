package org.example.repository;

import org.example.models.Pedido;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class PedidoRepository {
    private EntityManagerFactory entityManagerFactory;

    public PedidoRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("NomeDaUnidadeDePersistencia");
    }

    public void adicionarPedido(Pedido pedido) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(pedido);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Pedido buscarPedidoPorId(int idPedido) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Pedido pedido = null;

        try {
            pedido = entityManager.find(Pedido.class, idPedido);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return pedido;
    }

    public List<Pedido> listarPedidos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Pedido> pedidos = null;

        try {
            pedidos = entityManager.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return pedidos;
    }

    public void atualizarPedido(Pedido pedido) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.merge(pedido);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void deletarPedido(int idPedido) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Pedido pedido = entityManager.find(Pedido.class, idPedido);
            if (pedido != null) {
                entityManager.remove(pedido);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Pedido buscarUltimoPedido() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TypedQuery<Pedido> query = entityManager.createQuery("SELECT p FROM Pedido p ORDER BY p.idPedido DESC", Pedido.class);
        query.setMaxResults(1);

        List<Pedido> resultList = query.getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
