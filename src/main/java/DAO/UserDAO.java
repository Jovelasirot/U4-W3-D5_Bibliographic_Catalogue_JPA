package DAO;

import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

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

    public User findById(long userID) {
        User user = em.find(User.class, userID);
        if (user == null) throw new EntityNotFoundException(String.valueOf(userID));
        return user;
    }
}
