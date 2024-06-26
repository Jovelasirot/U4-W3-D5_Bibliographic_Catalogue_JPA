package DAO;

import entities.Book;
import entities.Catalog;
import exeptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

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

    public void deleteByISBN(String ISBN) {
        try {
            EntityTransaction eT = this.em.getTransaction();

            TypedQuery<Catalog> queryDelete = this.em.createQuery("SELECT c from Catalog c WHERE c.ISBN = :ISBN", Catalog.class);
            queryDelete.setParameter("ISBN", ISBN);

            Catalog catalogElementFounded = queryDelete.getSingleResult();

            if (catalogElementFounded != null) {
                eT.begin();
                this.em.remove(catalogElementFounded);
                eT.commit();
                System.out.println("Element with ISBN: " + ISBN + " has been deleted from the catalog");
            } else {
                System.out.println("Element not found");
            }
        } catch (NoResultException e) {
            System.out.println("Element with the isbn: " + ISBN + " was not found ᕙ(⇀‸↼‶)ᕗ.");
        }

    }

    public List<Catalog> catalogListBasedOnYear(int year) {
        TypedQuery<Catalog> catalogYearListQuery = this.em.createQuery("SELECT c FROM Catalog c WHERE EXTRACT(YEAR FROM c.releaseDate) = :year", Catalog.class);
        catalogYearListQuery.setParameter("year", year);

        return catalogYearListQuery.getResultList();
    }

    public List<Book> searchByAuthor(String author) {
        TypedQuery<Book> authorQuery = this.em.createQuery("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(:author)", Book.class);
        authorQuery.setParameter("author", author + "%");

        return authorQuery.getResultList();
    }

    public List<Catalog> searchByTitle(String title) {
        TypedQuery<Catalog> titleQuery = this.em.createQuery("SELECT c FROM Catalog c WHERE LOWER(c.title) LIKE LOWER(:title)", Catalog.class);
        titleQuery.setParameter("title", "%" + title + "%");

        return titleQuery.getResultList();
    }

    public List<Catalog> getFullCatalog() {
        TypedQuery<Catalog> fullCatalog = this.em.createQuery("SELECT c FROM Catalog c", Catalog.class);

        return fullCatalog.getResultList();
    }

    public List<Catalog> getAllBooks() {
        TypedQuery<Catalog> fullCatalog = this.em.createQuery("SELECT b FROM Book b", Catalog.class);

        return fullCatalog.getResultList();
    }

    public List<Catalog> getAllMagazine() {
        TypedQuery<Catalog> fullCatalog = this.em.createQuery("SELECT m FROM Magazine m", Catalog.class);

        return fullCatalog.getResultList();
    }

    public Catalog findById(long elementId) {
        Catalog catalog = em.find(Catalog.class, elementId);
        if (catalog == null) throw new NotFoundException(elementId);
        return catalog;
    }
}
