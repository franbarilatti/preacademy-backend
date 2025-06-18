package main.cineverse.models;

import java.time.LocalDate;

public class Viewing {
    Movie movie;
    User user;
    LocalDate date;
    int minutesWatched;

    public Viewing() {
    }

    public Viewing(Movie movie, User user, LocalDate date, int minutesWatched) {
        this.movie = movie;
        this.user = user;
        this.date = date;
        this.minutesWatched = minutesWatched;
    }

    public Viewing movie(Movie movie){
        this.movie = movie;
        return this;
    }

    public Viewing user(User user){
        this.user = user;
        return this;
    }

    public Viewing date(LocalDate date){
        this.date = date;
        return this;
    }

    public Viewing minutesWatched(int minutesWatched){
        this.minutesWatched = minutesWatched;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMinutesWatched() {
        return minutesWatched;
    }

    public void setMinutesWatched(int minutesWatched) {
        this.minutesWatched = minutesWatched;
    }

    @Override
    public String toString() {
        return "Viewing{" +
                "movie=" + movie.getTitle() +
                ", user=" + user.getUsername() +
                ", date=" + date +
                ", minutesWatched=" + minutesWatched +
                '}';
    }
}
