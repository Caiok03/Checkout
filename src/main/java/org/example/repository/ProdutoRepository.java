package org.example.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.example.models.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository {
    private static EntityManagerFactory entityManagerFactory;

    public ProdutoRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("NomeDaUnidadeDePersistencia");
    }

    public void salvarProduto(Produto produto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(produto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Optional<Produto> buscarProdutoPorId(int idProduto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Produto produto = null;

        try {
            produto = entityManager.find(Produto.class, idProduto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return Optional.ofNullable(produto);
    }

    public List<Produto> listarProdutos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Produto> produtos = null;

        try {
            produtos = entityManager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return produtos;
    }

    public List<Produto> read() throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Produto> produtos = new ArrayList<>();


            stmt = (PreparedStatement) entityManager.createQuery("SELECT * FROM produto");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Produto produto = new Produto();

                produto.setIdProduto(rs.getInt("id"));
                produto.setNomeProduto(rs.getString("descricao"));
                produto.setUnidade(rs.getInt("qtd"));
                produto.setPreco(rs.getDouble("preco"));
                produtos.add(produto);
            }


        return produtos;

    }

    public void atualizarProduto(Produto produto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.merge(produto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void deletarProduto(int idProduto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Produto produto = entityManager.find(Produto.class, idProduto);
            if (produto != null) {
                entityManager.remove(produto);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
