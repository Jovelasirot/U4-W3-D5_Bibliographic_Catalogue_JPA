package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "loaned_element_id")
    private Catalog loanedElement;
    private LocalDate startLoan;
    private LocalDate estimatedReturnDate;
    private LocalDate actualReturnDate;

    public Loan() {
    }

    public Loan(User user, Catalog loanedElement, LocalDate startLoan, LocalDate estimatedReturnDate, LocalDate actualReturnDate) {
        this.user = user;
        this.loanedElement = loanedElement;
        this.startLoan = startLoan;
        this.estimatedReturnDate = estimatedReturnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Catalog getLoanedElement() {
        return loanedElement;
    }

    public void setLoanedElement(Catalog loanedElement) {
        this.loanedElement = loanedElement;
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
