package org.example.repository;

import org.example.models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UsuarioRepository {
    private EntityManagerFactory entityManagerFactory;

    public UsuarioRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("NomeDaUnidadeDePersistencia");
    }

    public void salvarUsuario(Usuario usuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Usuario buscarUsuarioPorId(int idUsuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Usuario usuario = null;

        try {
            usuario = entityManager.find(Usuario.class, idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Usuario> usuarios = null;

        try {
            usuarios = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return usuarios;
    }

    public void atualizarUsuario(Usuario usuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void deletarUsuario(int idUsuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario != null) {
                entityManager.remove(usuario);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Usuario buscarUsuarioPorNome(String nomeUsuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Usuario usuario = null;

        try {
            usuario = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nome = :nomeUsuario", Usuario.class)
                    .setParameter("nomeUsuario", nomeUsuario)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return usuario;
    }
}
