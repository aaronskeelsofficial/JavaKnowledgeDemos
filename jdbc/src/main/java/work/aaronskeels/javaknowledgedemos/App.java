package work.aaronskeels.javaknowledgedemos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

public class App 
{
    private static boolean isDatabaseInitialized = false;

    public static void main( String[] args )
    {
        /*
         * Notes regarding JDBC:
         *  - Use PreparedStatement/CallableStatement over Statement to avoid SQL Injection
         *  - Use a connection pooling interface over any archaic approach
         *   - If you choose to be archaic anyway, make sure you close connections
         *  - executeQuery() seems to be for reading, executeUpdate() seems to be for ALL write operations (including non-"updates")
         *  - JDBC is basically SQLite but more intrinsically tied to Java, with in-memory databases, and with more advanced functionality
         *  - Topics to keep in mind but not necessarily are worth exemplifying are batch processing/procedures/transactions and database metadata retrieval
         */
        exemplifyQuickDirtyConnection();
        exemplifyProperConnectionPool();
    }

    public static void exemplifyQuickDirtyConnection() {
        // The below "url" comes from the H2 documentation for a single instanced memory-based embedded database.
        String url = "jdbc:h2:mem:;";
        String startupParams = "INIT=RUNSCRIPT FROM 'classpath:users.sql';";
        String fullInitQuery = url + startupParams;
        try (Connection connection = DriverManager.getConnection(fullInitQuery)) {
            System.out.println("[exemplifyQuickDirtyConnection] isValid: " + connection.isValid(0));

            // Select
            PreparedStatement selectPS = connection.prepareStatement("select * from USERS where name = ?");
            selectPS.setString(1, "Marco"); // Note: SQL indexing starts at 1. Weirdos.
            ResultSet resultSet = selectPS.executeQuery();
            while (resultSet.next()) {
                System.out.println("[exemplifyQuickDirtyConnection] resultSet: " + resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }

            // Insert
            PreparedStatement insertPS = connection.prepareStatement("insert into USERS (name) values (?)");
            insertPS.setString(1, "John");
            int insertCount = insertPS.executeUpdate(); // Note: Reads utilize executeQuery(), writes seem to use executeUpdate()
            System.out.println("[exemplifyQuickDirtyConnection] insertCount: " + insertCount + "/(1)");

            // Update
            PreparedStatement updatePS = connection.prepareStatement("update USERS set name = ? where name = ?");
            updatePS.setString(1, "Johnny");
            updatePS.setString(2, "John");
            int updateCount = updatePS.executeUpdate();
            System.out.println("[exemplifyQuickDirtyConnection] updateCount: " + updateCount + "/(1)");


            // Delete
            PreparedStatement deletePS = connection.prepareStatement("delete from USERS where name = ?");
            deletePS.setString(1, "Johnny");
            int deleteCount = deletePS.executeUpdate();
            System.out.println("[exemplifyQuickDirtyConnection] deleteCount: " + deleteCount + "/(1)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * A better approach is to create a connection pool which never kills connections and rotates out resources as demand appears.
     * HikariCP is used in this example, but I'd assume there are many alternatives which fulfill this task.
     */
    public static void exemplifyProperConnectionPool() {
        class DatabaseManager {
            private static final HikariDataSource DS;

            static {
                DS = new HikariDataSource();
                // The below "url" comes from the H2 documentation for a single instanced memory-based embedded database.
                String url = "jdbc:h2:mem:;";
                String startupParams = "INIT=RUNSCRIPT FROM 'classpath:users.sql';";
                String fullInitQuery = url + (!isDatabaseInitialized ? startupParams : "");
                isDatabaseInitialized = true;
                DS.setJdbcUrl(fullInitQuery);
            }

            public static HikariDataSource getDataSource() {
                return DS;
            }
        }

        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            System.out.println("[exemplifyProperConnectionPool] isValid: " + connection.isValid(0));

            // Select
            PreparedStatement selectPS = connection.prepareStatement("select * from USERS where name = ?");
            selectPS.setString(1, "Marco"); // Note: SQL indexing starts at 1. Weirdos.
            ResultSet resultSet = selectPS.executeQuery();
            while (resultSet.next()) {
                System.out.println("[exemplifyProperConnectionPool] resultSet: " + resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }

            // Insert
            PreparedStatement insertPS = connection.prepareStatement("insert into USERS (name) values (?)");
            insertPS.setString(1, "John");
            int insertCount = insertPS.executeUpdate(); // Note: Reads utilize executeQuery(), writes seem to use executeUpdate()
            System.out.println("[exemplifyProperConnectionPool] insertCount: " + insertCount + "/(1)");

            // Update
            PreparedStatement updatePS = connection.prepareStatement("update USERS set name = ? where name = ?");
            updatePS.setString(1, "Johnny");
            updatePS.setString(2, "John");
            int updateCount = updatePS.executeUpdate();
            System.out.println("[exemplifyProperConnectionPool] updateCount: " + updateCount + "/(1)");


            // Delete
            PreparedStatement deletePS = connection.prepareStatement("delete from USERS where name = ?");
            deletePS.setString(1, "Johnny");
            int deleteCount = deletePS.executeUpdate();
            System.out.println("[exemplifyProperConnectionPool] deleteCount: " + deleteCount + "/(1)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
