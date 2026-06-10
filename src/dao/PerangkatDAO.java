package dao;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PerangkatDAO {

    public void updateStatus(
            String nama,
            boolean status) {

        String sql =
                "UPDATE perangkat SET status=? WHERE nama=?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            ps.setBoolean(1, status);
            ps.setString(2, nama);

            ps.executeUpdate();
            System.out.println(
                nama + " -> " + status
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}