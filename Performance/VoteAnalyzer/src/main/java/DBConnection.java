import java.sql.*;
import java.util.Map;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/learn";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final int BATCH_SIZE = 10_000;
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                createTableIfNotExists();
            } catch (SQLException e) {
                throw new SQLException("Unable to establish a connection to the database: " + e.getMessage());
            }
        }
        return connection;
    }

    private static void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS voter_count (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name TINYTEXT NOT NULL, " +
                "birthDate DATE NOT NULL, " +
                "count INT NOT NULL, " +
                "PRIMARY KEY(id)) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci";

        getConnection().createStatement().execute(createTableSQL);
    }

    public static void executeBatchInsert(Map<String, Integer> voterCounts) throws SQLException {
        StringBuilder insertQuery = new StringBuilder();
        int count = 0;

        for (Map.Entry<String, Integer> entry : voterCounts.entrySet()) {
            String[] keyParts = entry.getKey().split("\t");
            String name = keyParts[0];
            String birthDay = keyParts[1];
            int voterCount = entry.getValue();

            if (insertQuery.length() > 0) {
                insertQuery.append(", ");
            }
            insertQuery.append("('").append(name).append("', '").append(birthDay).append("', ")
                    .append(voterCount).append(")");
            count++;
            if (count % BATCH_SIZE == 0) {
                executeInsert(insertQuery.toString());
                insertQuery.setLength(0);
            }
        }
        if (insertQuery.length() > 0) {
            executeInsert(insertQuery.toString());
        }
    }


    private static void executeInsert(String query) throws SQLException {
        String sql = "INSERT INTO voter_count(name,birthDate,count) VALUES " + query;
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, SUM(count) as count FROM voter_count " +
                "GROUP BY name, birthDate HAVING SUM(count) > 1";

        System.out.println("Duplicated voters: ");
        try (var rs = getConnection().createStatement().executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("\t" + rs.getString("name") + " ("
                        + rs.getString("birthDate") + ") - " + rs.getInt("count"));
            }
        }
    }
}
