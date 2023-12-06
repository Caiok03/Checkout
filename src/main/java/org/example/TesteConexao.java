/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example;

/**
 *
 * @author Pichau
 */
import org.example.models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class TesteConexao {

    public static void main(String[] args) {
 
        
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        
            // Cria o EntityManagerFactory com base nas configurações do persistence.xml
            //entityManagerFactory = Persistence.createEntityManagerFactory("NomeDaUnidadeDePersistencia");

            // Cria o EntityManager
            //entityManager = entityManagerFactory.createEntityManager();

            // Testa se a conexão foi bem-sucedida

        try {
            // Cria o EntityManagerFactory com base nas configurações do persistence.xml
            entityManagerFactory = Persistence.createEntityManagerFactory("NomeDaUnidadeDePersistencia");

            // Cria o EntityManager
            entityManager = entityManagerFactory.createEntityManager();

            // Testa se a conexão foi bem-sucedida
            if (entityManager != null) {
                System.out.println("Conexão com o banco de dados estabelecida!");

                // Realize alguma operação simples, como persistir uma entidade
                // Exemplo: Se você tem uma entidade Usuario, poderia fazer algo como:
                 Usuario usuario = new Usuario();
                 usuario.setNome("Exemplo");
                 entityManager.getTransaction().begin();
                entityManager.persist(usuario);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("Falha ao conectar ao banco de dados!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }
}