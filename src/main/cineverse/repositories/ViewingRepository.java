package main.cineverse.repositories;

import main.cineverse.configs.DatabaseConnection;
import main.cineverse.models.Viewing;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ViewingRepository {

    public void save(Viewing viewing) throws SQLException{
        String sql = "INSERT INTO viewings (movie_id, user_id, date, minutes_watched) VALUES (?, ?, ?, ?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1,viewing.getMovie().getId());
            stmt.setInt(2, viewing.getUser().getId());
            stmt.setDate(3, Date.valueOf(viewing.getDate()));
            stmt.setInt(4, viewing.getMinutesWatched());

            stmt.executeUpdate();
        }

    }

}
