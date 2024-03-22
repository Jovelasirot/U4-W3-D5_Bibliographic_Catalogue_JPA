package Jovel_Asirot;

import DAO.CatalogDAO;
import DAO.LoanDAO;
import DAO.UserDAO;
import com.github.javafaker.Faker;
import entities.Book;
import entities.Magazine;
import enums.Frequency;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Scanner;

public class DataBaseQueries {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("library_db");

    public static void main(String[] args) {
        EntityManager eM = emf.createEntityManager();

        CatalogDAO cDAO = new CatalogDAO(eM);
        LoanDAO lDAO = new LoanDAO(eM);
        UserDAO uDAO = new UserDAO(eM);

        handleUserAction(cDAO, lDAO, uDAO);

        eM.close();
        emf.close();

    }

    public static void handleUserAction(CatalogDAO cDAO, LoanDAO lDAO, UserDAO uDAO) {
        Scanner sc = new Scanner(System.in);
        int handleAction;
        do {
            System.out.println();
            System.out.println("What do you want to do?");
            System.out.println("1 - Add book");
            System.out.println("2 - Add magazine");
            System.out.println("3 - Delete by ISBN");
            System.out.println("4 - Filter catalog by year");
            System.out.println("0 - Terminate the program.");

            handleAction = sc.nextInt();
            sc.nextLine();

            switch (handleAction) {
                case 1:
                    addBook(cDAO);
                    break;

                case 2:
                    addMagazine(cDAO);
                    break;

                case 3:
                    deleteWithISBN(cDAO);
                    break;

                case 4:
                    searchByYear(cDAO);
                    break;

                case 0:
                    System.out.println("Terminating program =͟͟͞͞ =͟͟͞͞ ﾍ ( ´ Д `)ﾉ");
                    break;

                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }

        } while (handleAction != 0);
        sc.close();
    }

    public static void addBook(CatalogDAO cDAO) {
        Scanner sc = new Scanner(System.in);
        Faker faker = new Faker();

        String isbn = faker.code().isbn10();

        System.out.println("Title of the book:");
        String title = sc.nextLine();

        System.out.println("Release date of the book (Y-M-D):");
        String releaseDateInput = sc.nextLine();
        LocalDate releaseDate = LocalDate.parse(releaseDateInput);

        int numberOfPages;
        do {
            System.out.println("Number of pages of the book:");
            numberOfPages = sc.nextInt();
            if (numberOfPages < 5) {
                System.out.println("The book needs to have at least five pages, try again.");
            }
        } while (numberOfPages < 5);

        sc.nextLine();

        System.out.println("Author of the book:");
        String author = sc.nextLine();

        System.out.println("Genre of the book:");
        String genre = sc.nextLine();

        Book newBook = new Book(isbn, title, releaseDate, numberOfPages, author, genre);
        cDAO.save(newBook);

    }

    public static void addMagazine(CatalogDAO cDAO) {
        Scanner sc = new Scanner(System.in);
        Faker faker = new Faker();

        String isbn = faker.code().isbn10();

        System.out.println("Title of the magazine:");
        String title = sc.nextLine();

        System.out.println("Release date of the magazine (Y-M-D):");

        System.out.println("Release date of the book (Y-M-D):");
        String releaseDateInput = sc.nextLine();
        LocalDate releaseDate = LocalDate.parse(releaseDateInput);

        int numberOfPages;
        do {
            System.out.println("Number of pages of the magazine:");
            numberOfPages = sc.nextInt();
            if (numberOfPages < 5) {
                System.out.println("The magazine needs to have at least five pages, try again.");
            }
        } while (numberOfPages < 5);

        System.out.println("Select frequency for the magazine:");
        System.out.println("1 - WEEKLY");
        System.out.println("2 - MONTHLY");
        System.out.println("3 - SEMESTER");
        int selectorFrequency = sc.nextInt();
        Frequency[] frequencies = Frequency.values();
        Frequency frequency = frequencies[selectorFrequency - 1];

        Magazine newMagazine = new Magazine(isbn, title, releaseDate, numberOfPages, frequency);
        cDAO.save(newMagazine);

    }

    public static void deleteWithISBN(CatalogDAO cDAO) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type the ISBN you want to delete:");
        String isbn = sc.nextLine();

        cDAO.deleteByISBN(isbn);
    }

    public static void searchByYear(CatalogDAO cDAO) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type the year you want to filter with the catalog:");
        int yearInput = sc.nextInt();

        cDAO.catalogListBasedOnYear(yearInput).forEach(System.out::println);
    }
}
