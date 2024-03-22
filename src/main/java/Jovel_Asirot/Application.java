package Jovel_Asirot;

import DAO.CatalogDAO;
import DAO.LoanDAO;
import DAO.UserDAO;
import entities.Book;
import entities.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static entities.Book.getBookSupplier;
import static entities.Magazine.getMagazineSupplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("library_db");

    public static void main(String[] args) {

        EntityManager eM = emf.createEntityManager();

        CatalogDAO cDAO = new CatalogDAO(eM);
        LoanDAO lDAO = new LoanDAO(eM);
        UserDAO uDAO = new UserDAO(eM);

//        Books
        Supplier<Book> bookSupplier = getBookSupplier();
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            bookList.add(bookSupplier.get());
        }
        bookList.forEach(cDAO::save);

//        Magazines
        Supplier<Magazine> magazineSupplier = getMagazineSupplier();
        List<Magazine> magazineList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            magazineList.add(magazineSupplier.get());
        }
        magazineList.forEach(cDAO::save);


        System.out.println("Hello World!");

        emf.close();
        eM.close();
    }
}
