package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "loan_catalog",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "element_loaned_id"))
    private Set<Catalog> loanedElement = new HashSet<>();
    private LocalDate startLoan;
    private LocalDate estimatedReturnDate;
    private LocalDate actualReturnDate;

    public Loan() {
    }

    public Loan(User user, Set<Catalog> loanedElement, LocalDate startLoan, LocalDate estimatedReturnDate, LocalDate actualReturnDate) {
        this.user = user;
        this.loanedElement = loanedElement;
        this.startLoan = startLoan;
        this.estimatedReturnDate = estimatedReturnDate;
        this.actualReturnDate = actualReturnDate;
    }


    public static Supplier<Loan> getLoanSupplier(EntityManagerFactory emf) {
        Random rdm = new Random();


        return () -> {
            EntityManager eM = emf.createEntityManager();

            TypedQuery<Catalog> queryCatalog = eM.createQuery("SELECT c from Catalog c", Catalog.class);
            List<Catalog> catalogList = queryCatalog.getResultList();

            Set<Catalog> elementsCatalogLoaned = new HashSet<>();
            int numberLoanedElements = rdm.nextInt(1, 5);
            for (int i = 0; i < numberLoanedElements; i++) {
                int rdmIndex = rdm.nextInt(catalogList.size());
                elementsCatalogLoaned.add(catalogList.get(rdmIndex));
            }

            TypedQuery<User> userQuery = eM.createQuery("SELECT u from User u", User.class);
            List<User> userList = userQuery.getResultList();
            int rdmUserSelector = rdm.nextInt(userList.size());
            User rdmUser = userList.get(rdmUserSelector);

            LocalDate startLoan = LocalDate.now().plusDays(rdm.nextInt(365));
            LocalDate estimatedReturnDate = startLoan.plusDays(30);

            LocalDate actualReturnDate = startLoan.plusDays(rdm.nextInt(40));
            
            eM.close();

            return new Loan(rdmUser, elementsCatalogLoaned, startLoan, estimatedReturnDate, actualReturnDate);
        };
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Catalog> getLoanedElement() {
        return loanedElement;
    }

    public LocalDate getStartLoan() {
        return startLoan;
    }

    public void setStartLoan(LocalDate startLoan) {
        this.startLoan = startLoan;
    }

    public LocalDate getEstimatedReturnDate() {
        return estimatedReturnDate;
    }

    public void setEstimatedReturnDate(LocalDate estimatedReturnDate) {
        this.estimatedReturnDate = estimatedReturnDate;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(Long idLoan) {
        this.idLoan = idLoan;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "user=" + user +
                ", loanedElement=" + loanedElement +
                ", startLoan=" + startLoan +
                ", estimatedReturnDate=" + estimatedReturnDate +
                ", actualReturnDate=" + actualReturnDate +
                '}';
    }
}
