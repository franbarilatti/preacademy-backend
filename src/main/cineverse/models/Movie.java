package main.cineverse.models;

import main.cineverse.models.enums.Genre;

public abstract class Movie {
    int id;
    String title;
    int durationMinutes;
    Genre genre;
    int rating;

    public Movie() {
    }

    public Movie(int id, String title, int durationMinutes, Genre genre, int rating) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.genre = genre;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", genre=" + genre +
                ", rating=" + rating +
                '}';
    }
}
