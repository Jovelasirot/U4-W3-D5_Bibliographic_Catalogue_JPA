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
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Objects;
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
            try {


                System.out.println();
                System.out.println("What do you want to do?");
                System.out.println("1 - Add book");
                System.out.println("2 - Add magazine");
                System.out.println("3 - Delete by ISBN");
                System.out.println("4 - Filter catalog by year");
                System.out.println("5 - Search by author");
                System.out.println("6 - Search by title");
                System.out.println("7 - Search lent catalog elements with user card number");
                System.out.println("8 - View all expired loans");
                System.out.println("9 - View catalog");
                System.out.println("10 - View all users");
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

                    case 5:
                        searchByAuthor(cDAO);
                        break;

                    case 6:
                        searchByTitle(cDAO);
                        break;

                    case 7:
                        searchLentElementsWithUserCardNumber(lDAO);
                        break;

                    case 8:
                        lDAO.getExpiredLoans().forEach(System.out::println);
                        break;

                    case 9:
                        handleViewCatalog(cDAO);
                        break;

                    case 10:
                        uDAO.getALlUser().forEach(System.out::println);
                        break;

                    case 0:
                        System.out.println("Terminating program =͟͟͞͞ =͟͟͞͞ ﾍ ( ´ Д `)ﾉ");
                        break;

                    default:
                        System.out.println("Invalid action. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, type a number.");
                System.out.println("-------------");
                handleAction = -1;
                sc.nextLine();

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


        LocalDate releaseDate = null;
        boolean validReleaseDate = false;
        while (!validReleaseDate) {

            System.out.println("Release date of the book (Y-M-D):");
            String releaseDateInput = sc.nextLine();

            try {
                releaseDate = LocalDate.parse(releaseDateInput);
                System.out.println("Release date of the book was added successfully");
                validReleaseDate = true;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, try again.");
            }
        }

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

        LocalDate releaseDate = null;
        boolean validReleaseDate = false;
        while (!validReleaseDate) {

            System.out.println("Release date of the magazine (Y-M-D):");
            String releaseDateInput = sc.nextLine();

            try {
                releaseDate = LocalDate.parse(releaseDateInput);
                System.out.println("Release date  of the magazine was added successfully");
                validReleaseDate = true;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, try again.");
            }
        }

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

    public static void searchByAuthor(CatalogDAO cDAO) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type the author (even just the initials):");
        String authorInput = sc.nextLine();

        cDAO.searchByAuthor(authorInput).forEach(System.out::println);
    }

    public static void searchByTitle(CatalogDAO cDAO) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type the title (even just parts of the title):");
        String titleInput = sc.nextLine();

        cDAO.searchByTitle(titleInput).forEach(System.out::println);
    }

    public static void searchLentElementsWithUserCardNumber(LoanDAO lDAO) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type the user card number:");
        Long cardNumberInput = sc.nextLong();

        lDAO.getLoansWithCardNumber(cardNumberInput).forEach(System.out::println);
    }

    public static void handleViewCatalog(CatalogDAO cDAO) {
        Scanner sc = new Scanner(System.in);

        System.out.println("What do you want to view");
        System.out.println("C - Catalog");
        System.out.println("B - Book list");
        System.out.println("M - Magazine list");

        String viewInput = sc.nextLine();
        if (Objects.equals(viewInput, "C")) {
            cDAO.getFullCatalog().forEach(System.out::println);

        } else if (Objects.equals(viewInput, "B")) {
            cDAO.getAllBooks().forEach(System.out::println);

        } else if (Objects.equals(viewInput, "M")) {
            cDAO.getAllMagazine().forEach(System.out::println);
        } else {
            System.out.println("Invalid input");
        }
    }
}
