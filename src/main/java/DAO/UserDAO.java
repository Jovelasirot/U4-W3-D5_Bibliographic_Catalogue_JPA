package DAO;

import entities.User;
import exeptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserDAO {
    private final EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public void save(User user) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(user);
        transaction.commit();
        System.out.println("The user: " + user.getName() + " " + user.getSurname() + ", has been saved correctly");
    }

    public List<User> getALlUser() {
        TypedQuery<User> fullCatalog = this.em.createQuery("SELECT u FROM User u", User.class);

        return fullCatalog.getResultList();
    }

    public List<User> getUserWithCardNumber(Long cardNumber) {
        TypedQuery<User> cardNumberQuery = this.em.createQuery("SELECT u FROM User u WHERE u.cardNumber = :cardNumber", User.class);
        cardNumberQuery.setParameter("cardNumber", cardNumber);

        return cardNumberQuery.getResultList();

    }

    public User findById(long userID) {
        User user = em.find(User.class, userID);
        if (user == null) throw new NotFoundException(userID);
        return user;
    }
}
