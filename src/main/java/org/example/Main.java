package org.example;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.example.models.Produto;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NomeDaUnidadeDePersistencia");

    }
    }