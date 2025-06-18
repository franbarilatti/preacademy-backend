package main.cineverse.services;

import main.cineverse.models.Movie;
import main.cineverse.models.Platform;
import main.cineverse.models.User;
import main.cineverse.models.Viewing;
import main.cineverse.models.enums.Genre;

import java.security.PublicKey;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlatformService {

    private Platform platform;

    public PlatformService(Platform platform){
        this.platform = platform;
    }

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

    public Map<User,Integer> topContentConsumers(){
        return platform.getViewings()
                .stream()
                .collect(Collectors.groupingBy(
                        Viewing::getUser,
                        Collectors.summingInt(Viewing::getMinutesWatched)));
    }

    public Map<User,Double> averageMinutesPerUser(){
        return platform.getViewings()
                .stream()
                .collect(Collectors.groupingBy(
                        Viewing::getUser,
                        Collectors.averagingInt(Viewing::getMinutesWatched)));
    }

    public double PercentageOfMoviesWatched(){
        Set<Movie> viewedMovies = platform.getViewings()
                .stream()
                .map(Viewing::getMovie)
                .collect(Collectors.toSet());

        int totalMovies = platform.getMovies().size();
        int viewedCount = completeViewedMovies().size();

        double percentageViewed = (viewedCount * 100.00) / totalMovies;

        return percentageViewed;

    }

    public List<Movie> unwatchedMovies(){
        Set<Movie> viewedMovies = platform.getViewings()
                .stream()
                .map(Viewing::getMovie)
                .collect(Collectors.toSet());
        
        return platform.getMovies()
                .stream()
                .filter(m -> !completeViewedMovies().contains(m))
                .collect(Collectors.toList());
        
    }


}
