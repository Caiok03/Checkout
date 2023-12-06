package org.example.repository;

import org.example.models.Carrinho;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CarrinhoRepository {
    private EntityManagerFactory entityManagerFactory;

    public CarrinhoRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("NomeDaUnidadeDePersistencia");
    }

    public void adicionarItemCarrinho(Carrinho carrinho) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(carrinho);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Carrinho buscarItensCarrinhoPorId(int idItemPedido) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Carrinho carrinho = null;

        try {
            carrinho = entityManager.find(Carrinho.class, idItemPedido);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return carrinho;
    }

    public List<Carrinho> listarItensCarrinho() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Carrinho> itensPedido = null;

        try {
            itensPedido = entityManager.createQuery("SELECT ip FROM ItemPedido ip", Carrinho.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return itensPedido;
    }

    public void atualizarItemPedido(Carrinho carrinho) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.merge(carrinho);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void deletarItemPedido(int idItemPedido) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Carrinho carrinho = entityManager.find(Carrinho.class, idItemPedido);
            if (carrinho != null) {
                entityManager.remove(carrinho);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {

        }    entityManager.close();
        }
}
