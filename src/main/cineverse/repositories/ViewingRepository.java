package main.cineverse.repositories;

import main.cineverse.configs.DatabaseConnection;
import main.cineverse.models.Movie;
import main.cineverse.models.User;
import main.cineverse.models.Viewing;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewingRepository {

    private final MovieRepository movieRepository = new MovieRepository();
    private final UserRepository userRepository = new UserRepository();

    public void save(Viewing viewing) throws SQLException {
        String sql = "INSERT INTO viewings (movie_id, user_id, date, minutes_watched) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, viewing.getMovie().getId());
            stmt.setInt(2, viewing.getUser().getId());
            stmt.setDate(3, Date.valueOf(viewing.getDate()));
            stmt.setInt(4, viewing.getMinutesWatched());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating viewing failed, no rows affected.");
            }
        }
    }

    public List<Viewing> findAll() throws SQLException{
        List<Viewing> viewings = new ArrayList<>();

        String sql = "SELECT id, movie_id, user_id, date, minutes_watched FROM viewings";

        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

            while (rs.next()){
                int movieId = rs.getInt("movie_id");
                int userId = rs.getInt("user_id");
                LocalDate date = rs.getDate("date").toLocalDate();
                int minutesWatched = rs.getInt("minutes_watched");

                Movie movie = movieRepository.findById(movieId);
                User user = userRepository.findById(userId);

                Viewing viewing = new Viewing(movie,user,date,minutesWatched);
                viewings.add(viewing);
            }

            return viewings;

        }


    }

}
