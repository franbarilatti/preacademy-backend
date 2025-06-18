package main.cineverse.models;

import java.util.List;

public class Platform {

    private String name;
    private List<Movie> movies;
    private List<User> users;
    private List<Viewing> viewings;

    public Platform() {
    }

    public Platform(String name, List<Movie> movies, List<User> users, List<Viewing> viewings) {
        this.name = name;
        this.movies = movies;
        this.users = users;
        this.viewings = viewings;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Viewing> getViewings() {
        return viewings;
    }

    public void setViewings(List<Viewing> viewings) {
        this.viewings = viewings;
    }

    @Override
    public String toString() {

        String movieTitles = movies.stream()
                .map(Movie::getTitle)
                .toList()
                .toString();

        String usernames = users.stream()
                .map(User::getUsername)
                .toList()
                .toString();

        return "Platform{" +
                "name='" + name + '\'' +
                ", movies=" + movieTitles +
                ", users=" + usernames +
                '}';
    }
}
