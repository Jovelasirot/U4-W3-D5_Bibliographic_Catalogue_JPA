package entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn", unique = true)
    private String ISBN;
    private String title;
    @Column(name = "release_date")
    private int releaseDate;
    @Column(name = "number_pages")
    private int numberPages;

    public Catalog(String ISBN, String title, int releaseDate, int numberPages) {
        this.ISBN = ISBN;
        this.title = title;
        this.releaseDate = releaseDate;
        this.numberPages = numberPages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", numberPages=" + numberPages +
                '}';
    }
}
