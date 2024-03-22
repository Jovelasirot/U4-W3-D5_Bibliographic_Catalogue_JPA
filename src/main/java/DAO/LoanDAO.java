package DAO;

import entities.Loan;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

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

    public User findById(long userID) {
        User user = em.find(User.class, userID);
        if (user == null) throw new EntityNotFoundException(String.valueOf(userID));
        return user;
    }
}
