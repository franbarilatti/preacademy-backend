package main.cineverse.services;

import main.cineverse.models.Movie;
import main.cineverse.models.Platform;
import main.cineverse.models.Viewing;
import main.cineverse.models.enums.Genre;

import java.security.PublicKey;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class PlatformService {

    private Platform platform;

    public List<Movie> completeViewedMovies(){
        return platform.getViewings()
                .stream()
                .filter(v -> v.getMinutesWatched() >= v.getMovie().getDurationMinutes())
                .map(Viewing::getMovie)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<> mostPopularGenres(){
        return
    }

    public User mostViewsUser(){

    }

    public double minutesPerUserAverage(){

    }

}
