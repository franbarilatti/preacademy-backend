package main.cineverse.models;

import main.cineverse.models.enums.Genre;

public class InternalMovie extends Movie{

    private String directorName;
    private String productionYear;

    public InternalMovie(){

    }

    public InternalMovie(String directorName, String productionYear) {
        this.directorName = directorName;
        this.productionYear = productionYear;
    }

    public InternalMovie(int id, String title, int durationMinutes, Genre genre, int rating, String directorName, String productionYear) {
        super(id, title, durationMinutes, genre, rating);
        this.directorName = directorName;
        this.productionYear = productionYear;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }
}
