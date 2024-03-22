package entities;

import com.github.javafaker.Faker;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Random;
import java.util.function.Supplier;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long idUser;
    private String name;
    private String surname;

    @Column(name = "birth_date")
    private LocalDate birtDate;
    @Column(name = "card_number", unique = true)
    private Long cardNumber;

    public User() {
    }

    public User(String name, String surname, LocalDate birtDate, Long cardNumber) {
        this.name = name;
        this.surname = surname;
        this.birtDate = birtDate;
        this.cardNumber = cardNumber;
    }

    public static Supplier<User> getUserSupplier() {
        Random rdm = new Random();
        Faker faker = new Faker();

        return () -> {
            String name = faker.name().firstName();
            String surname = faker.name().lastName();

            String birthDateStartString = "2002-01-01";
            LocalDate birthDateStartStringParsed = LocalDate.parse(birthDateStartString);
            LocalDate birthDate = birthDateStartStringParsed.plusDays(rdm.nextInt(730));

            Long cardNumber = rdm.nextLong(1000L, 100000L);

            return new User(name, surname, birthDate, cardNumber);
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirtDate() {
        return birtDate;
    }

    public void setBirtDate(LocalDate birtDate) {
        this.birtDate = birtDate;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birtDate=" + birtDate +
                ", cardNumber=" + cardNumber +
                '}';
    }
}
