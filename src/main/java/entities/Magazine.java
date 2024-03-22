package entities;

import com.github.javafaker.Faker;
import enums.Frequency;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Random;
import java.util.function.Supplier;

@Entity
public class Magazine extends Catalog {
    @Enumerated(EnumType.STRING)
    private Frequency frequency;


    public Magazine(String ISBN, String title, int releaseDate, int numberPages, Frequency frequency) {
        super(ISBN, title, releaseDate, numberPages);
        this.frequency = frequency;
    }

    public Magazine(String ISBN, String title, int releaseDate, int numberPages, String frequencyFromFile) {
        super(ISBN, title, releaseDate, numberPages);
        this.frequency = Frequency.valueOf(frequencyFromFile);
    }

    public static Supplier<Magazine> getMagazineSupplier() {
        Random rdm = new Random();
        Faker faker = new Faker();
        Frequency[] frequencies = Frequency.values();


        return () -> {
            int rdmFrequency = rdm.nextInt(frequencies.length);
            String isbn = faker.code().isbn10();
            String title = faker.book().title();
            int releaseDate = rdm.nextInt(1950, 2024);
            int numberPage = rdm.nextInt(20, 500);
            Frequency frequency = frequencies[rdmFrequency];

            return new Magazine(isbn, title, releaseDate, numberPage, frequency);
        };
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "ISBN='" + getISBN() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", releaseDate='" + getReleaseDate() + '\'' +
                ", numberPages='" + getNumberPages() + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
