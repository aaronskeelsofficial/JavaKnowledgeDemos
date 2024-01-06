package work.aaronskeels.javaknowledgedemos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariDataSource;

public class App 
{
    private static final HikariDataSource DS;
    static {
        DS = new HikariDataSource();
        // The below "url" comes from the H2 documentation for a single instanced memory-based embedded database.
        DS.setJdbcUrl("jdbc:h2:mem:;");
        try (Connection connection = DS.getConnection()) {
            Statement S = connection.createStatement();
            // Make "names" table - id | name
            S.addBatch("""
                CREATE TABLE IF NOT EXISTS names (id IDENTITY PRIMARY KEY, name VARCHAR (255) NOT NULL);
                TRUNCATE TABLE names;
                INSERT INTO names (name) VALUES ('Marco');
                INSERT INTO names (name) VALUES ('Mark');
                INSERT INTO names (name) VALUES ('Lisa');
                """);
            // Make "ages" table - id | age
            S.addBatch("""
                CREATE TABLE IF NOT EXISTS ages (id IDENTITY PRIMARY KEY, age TINYINT NOT NULL);
                TRUNCATE TABLE ages;
                INSERT INTO ages (age) VALUES (24);
                INSERT INTO ages (age) VALUES (74);
                INSERT INTO ages (age) VALUES (12);
                """);
            // Make "genders" table - id | gender
            S.addBatch("""
                CREATE TABLE IF NOT EXISTS genders (id IDENTITY PRIMARY KEY, gender BOOL NOT NULL);
                TRUNCATE TABLE genders;
                INSERT INTO genders (gender) VALUES (1);
                INSERT INTO genders (gender) VALUES (1);
                INSERT INTO genders (gender) VALUES (0);
                """);
            S.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        exemplifyBasicQuery();
        exemplifyTableJoin();
        exemplifyExpressions();
    }

    public static void exemplifyBasicQuery() {
        /*
         * Basic Query Form:
         * 
         * SELECT column, another_column, …     // This defines the columns to gather the subset of data from
         * FROM mytable                         // This defines which table to gather from
         * WHERE condition(s)                   // This defines the initial filter
         * ORDER BY column ASC/DESC             // This reorganizes the gathered data
         * LIMIT num_limit OFFSET num_offset;   // This takes the data which has been gathered so far, and performs a second pass filter limiting the number of results taken from where
         */
        try (Connection connection = DS.getConnection()) {
            Statement S = connection.createStatement();
            ResultSet resultSet = S.executeQuery("""
                    SELECT *
                    FROM names
                    WHERE name LIKE 'M%';
                    """);
            while (resultSet.next()) {
                System.out.println("[exemplifyBasicQuery] resultSet: " + resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void exemplifyTableJoin() {
        /*
         * Inner Join Query Form:
         * 
         * SELECT column, another_column, …     // This defines the columns to gather the subset of data from
         * FROM mytable                         // This defines which table to gather from
         * INNER JOIN othertable
         *      ON mytable.id = othertable.id
         * WHERE condition(s)                   // This defines the initial filter
         * ORDER BY column ASC/DESC             // This reorganizes the gathered data
         * LIMIT num_limit OFFSET num_offset;   // This takes the data which has been gathered so far, and performs a second pass filter limiting the number of results taken from where
         */

         /*
          * Inner Join - Only keeps data where overlap exists in both tables
          * Left Join - Keeps all data from left table and only pulls right table where overlaps with left
          * Right Join - Keeps all data from right table and only pulls left table where overlaps with right
          * Full Join - Keeps all data from both tables
          */
        try (Connection connection = DS.getConnection()) {
            Statement S = connection.createStatement();
            ResultSet resultSet = S.executeQuery("""
                    SELECT *
                    FROM names
                    INNER JOIN ages
                        ON names.id = ages.id
                    WHERE name LIKE 'M%'
                        AND age > 30;
                    """);
            while (resultSet.next()) {
                System.out.println("[exemplifyTableJoin] resultSet: " + resultSet.getInt("id") + " - " + resultSet.getString("name")
                + " - " + resultSet.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void exemplifyExpressions() {
        /*
         * Expression Query Form:
         * 
         * SELECT FUNC(column)                  // This defines the columns to gather the subset of data from
         * FROM mytable                         // This defines which table to gather from
         * WHERE condition(s)                   // This defines the initial filter
         * ORDER BY column ASC/DESC             // This reorganizes the gathered data
         * LIMIT num_limit OFFSET num_offset;   // This takes the data which has been gathered so far, and performs a second pass filter limiting the number of results taken from where
         * GROUP BY condition
         */

         /*
          * Important JDBC Note:
          * When using JDBC with ResultSets, in order to retrieve the data of an aggregate function, you MUST assign it an alias. Anonymous aggregate function returns
          * are unretrievable.
          */
        try (Connection connection = DS.getConnection()) {
            Statement S = connection.createStatement();
            ResultSet resultSet = S.executeQuery("""
                    SELECT AVG(age) AS avg, gender
                    FROM names
                    INNER JOIN ages
                        ON names.id = ages.id
                    INNER JOIN genders
                        ON names.id = genders.id
                    GROUP BY gender
                    """);
            while (resultSet.next()) {
                System.out.println("[exemplifyExpressions] resultSet: " + resultSet.getInt("gender") + " - " + resultSet.getString("avg"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
