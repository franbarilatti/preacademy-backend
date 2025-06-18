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

    public List<Genre> mostPopularGenres(){
        Map<Genre, Long> genreCount = platform
                .getViewings()
                .stream()
                .collect(Collectors
                        .groupingBy(v -> v.getMovie().getGenre(), Collectors.counting()));

        long max = genreCount
                .values()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        return genreCount
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public User mostViewsUser(){

    }

    public double minutesPerUserAverage(){

    }

}
