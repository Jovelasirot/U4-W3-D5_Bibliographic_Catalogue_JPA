package DAO;

import entities.Catalog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

public class CatalogDAO {
    private final EntityManager em;

    public CatalogDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Catalog catalogElement) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(catalogElement);
        transaction.commit();
        System.out.println("The element: " + catalogElement.getTitle() + ", has been saved correctly");
    }

    public Catalog findById(long elementId) {
        Catalog catalog = em.find(Catalog.class, elementId);
        if (catalog == null) throw new EntityNotFoundException(String.valueOf(elementId));
        return catalog;
    }
}
