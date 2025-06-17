package main.cineverse.models;

import main.cineverse.models.enums.Genre;

import java.time.LocalDate;

public class ExternalMovie extends Movie{

    private String studioName;
    private LocalDate licenceExpirationDate;

    public ExternalMovie(){

    }

    public ExternalMovie(String studioName, LocalDate licenceExpirationDate) {
        this.studioName = studioName;
        this.licenceExpirationDate = licenceExpirationDate;
    }

    public ExternalMovie(int id, String title, int durationMinutes, Genre genre, int rating, String studioName, LocalDate licenceExpirationDate) {
        super(id, title, durationMinutes, genre, rating);
        this.studioName = studioName;
        this.licenceExpirationDate = licenceExpirationDate;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public LocalDate getLicenceExpirationDate() {
        return licenceExpirationDate;
    }

    public void setLicenceExpirationDate(LocalDate licenceExpirationDate) {
        this.licenceExpirationDate = licenceExpirationDate;
    }
}
