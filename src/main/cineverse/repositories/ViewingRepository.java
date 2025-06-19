package main.cineverse.repositories;

import main.cineverse.configs.DatabaseConnection;
import main.cineverse.models.Viewing;

import javax.xml.crypto.Data;
import java.sql.*;

public class ViewingRepository {

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

}
