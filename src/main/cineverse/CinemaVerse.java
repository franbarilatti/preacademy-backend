package main.cineverse;

import main.cineverse.models.*;
import main.cineverse.models.enums.Genre;
import main.cineverse.models.enums.SubcriptionPlan;
import main.cineverse.repositories.MovieRepository;
import main.cineverse.repositories.UserRepository;
import main.cineverse.repositories.ViewingRepository;
import main.cineverse.services.PlatformService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CinemaVerse {

    public static void main(String[] args) {

        // === Movies ===

        /*Movie m1 = new InternalMovie(1, "El secreto de sus ojos", 129, Genre.THRILLER, 95, "Juan José Campanella", "2009");
        Movie m2 = new InternalMovie(2, "Relatos salvajes", 122, Genre.DRAMA, 90, "Damián Szifron", "2014");
        Movie m3 = new ExternalMovie(3, "Nueve reinas", 114, Genre.ACTION, 88, "Patagonik Film Group", LocalDate.of(2026, 5, 1));
        Movie m4 = new ExternalMovie(4, "El clan", 110, Genre.THRILLER, 85, "Telefe", LocalDate.of(2025, 12, 1));
        Movie m5 = new InternalMovie(5, "Metegol", 106, Genre.KIDS, 75, "Juan José Campanella", "2013");
        Movie m6 = new ExternalMovie(6, "La odisea de los giles", 116, Genre.COMEDY, 83, "K&S Films", LocalDate.of(2027, 3, 15));

        List<Movie> movies = Arrays.asList(m1, m2, m3, m4, m5,m6);

        // === Users ===

        User u1 = new User().id(1).username("Pepe").plan(SubcriptionPlan.PREMIUM).country("Argentina").viewings(new ArrayList<>());
        User u2 = new User().id(2).username("Lolo").plan(SubcriptionPlan.FREE).country("uruguay").viewings(new ArrayList<>());
        User u3 = new User().id(3).username("Fulano").plan(SubcriptionPlan.STANDAR).country("Argentina").viewings(new ArrayList<>());

        List<User> users = Arrays.asList(u1, u2, u3);

        Viewing v1 = new Viewing().movie(m1).user(u1).date(LocalDate.now().minusDays(5)).minutesWatched(110);
        Viewing v2 = new Viewing().movie(m2).user(u1).date(LocalDate.now().minusDays(2)).minutesWatched(150);
        Viewing v3 = new Viewing().movie(m3).user(u2).date(LocalDate.now().minusDays(1)).minutesWatched(100);
        Viewing v4 = new Viewing().movie(m3).user(u2).date(LocalDate.now()).minutesWatched(125); // vista completa
        Viewing v5 = new Viewing().movie(m4).user(u1).date(LocalDate.now().minusDays(10)).minutesWatched(90);
        Viewing v6 = new Viewing().movie(m4).user(u3).date(LocalDate.now()).minutesWatched(50);

        List<Viewing> viewings = Arrays.asList(v1, v2, v3, v4, v5, v6);

        // Set viewings to users
        u1.setViewings(Arrays.asList(v1, v2, v5));
        u2.setViewings(Arrays.asList(v3, v4));
        u3.setViewings(List.of(v6));

        // === Platform ===
        Platform cineverse = new Platform("CineVerse", movies, users, viewings);

        // === Service ===
        PlatformService service = new PlatformService(cineverse);

        // === Tests ===
        System.out.println("Longest movies:");
        service.longestMovies().forEach(System.out::println);

        System.out.println("\nMost active country:");
        System.out.println(service.countryWithMostUsers());

        System.out.println("\nHas everyone watched something?");
        System.out.println(service.allUsersHaveViewedSomething());

        System.out.println("\nViewings distribution by plan:");
        service.viewingsDistributionPlan()
                .forEach((plan, count) -> System.out.println(plan + ": " + count));*/

        /*MovieRepository repo = new MovieRepository();
         repo.printAllMovies();*/

        // Crear instancias de repositorios
        MovieRepository movieRepo = new MovieRepository();
        UserRepository userRepo = new UserRepository();
        ViewingRepository viewingRepo = new ViewingRepository();

        try {
            // Crear objetos sin id (0 o sin asignar)
            InternalMovie interna1 = new InternalMovie(
                    0,
                    "Nueve Reinas",
                    114,
                    Genre.THRILLER,
                    92,
                    "Fabián Bielinsky",
                    "2000"
            );

            ExternalMovie externa1 = new ExternalMovie(
                    0,
                    "El Padrino",
                    175,
                    Genre.DRAMA,
                    98,
                    "Paramount Pictures",
                    LocalDate.of(2030, 12, 31)
            );

            // Guardar películas, se asigna ID autoincremental desde DB
            movieRepo.save(interna1);
            movieRepo.save(externa1);

            // Ahora interna1.getId() y externa1.getId() tienen el valor asignado por la DB

            User user1 = new User(0, "agus_mdp", SubcriptionPlan.PREMIUM, "Argentina", List.of());
            User user2 = new User(0, "nico_br", SubcriptionPlan.STANDAR, "Brasil", List.of());

            userRepo.save(user1);
            userRepo.save(user2);

            // user1.getId() y user2.getId() también ya tienen IDs asignados

            // Crear visualizaciones usando IDs ya asignados
            Viewing view1 = new Viewing(interna1, user1, LocalDate.now(), 114);
            Viewing view2 = new Viewing(externa1, user1, LocalDate.now(), 175);
            Viewing view3 = new Viewing(interna1, user2, LocalDate.now(), 60); // no la termina

            viewingRepo.save(view1);
            viewingRepo.save(view2);
            viewingRepo.save(view3);

            System.out.println("¡Datos cargados correctamente!");

        } catch (SQLException e) {
            System.err.println("Error al interactuar con la base de datos:");
            e.printStackTrace();
        }


    }

}
