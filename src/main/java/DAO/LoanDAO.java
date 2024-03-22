package DAO;

import entities.Loan;
import entities.User;
import exeptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LoanDAO {
    private final EntityManager em;

    public LoanDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Loan loan) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(loan);
        transaction.commit();
        System.out.println("The loan: " + loan.getIdLoan() + ", has been saved correctly");
    }

    public List<Loan> getLoansWithCardNumber(Long cardNumber) {
        TypedQuery<Loan> loanWithCardNumberQuery = em.createQuery("SELECT l FROM Loan l WHERE l.user.cardNumber = :cardNumber", Loan.class);
        loanWithCardNumberQuery.setParameter("cardNumber", cardNumber);
        return loanWithCardNumberQuery.getResultList();
    }

    public List<Loan> getExpiredLoans() {
        TypedQuery<Loan> expiredLoanQuery = em.createQuery("SELECT l FROM Loan l where l.actualReturnDate IS NULL", Loan.class);

        return expiredLoanQuery.getResultList();
    }

    public User findById(long userID) {
        User user = em.find(User.class, userID);
        if (user == null) throw new NotFoundException(userID);
        return user;
    }
}
