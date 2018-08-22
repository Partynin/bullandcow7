package app.entities;

import java.sql.*;
import java.util.*;

/**
 * This class is used to establish a connection and execute queries to the database.
 *
 * @author Partynin - <a href="mailto:partinin@bk.ru">partinin@bk.ru</a>
 */

public class DbBean {

    private String dbUrl = "";
    private String dbUserName = "";
    private String dbPassword = "";

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * Returns <code>true</code> if the user login in to the database.
     * Establishes a connection to the database and checks if the user is registered.
     *
     * @param userName a name of user what will be checked
     * @param password a password for the user
     * @return <code>true</code> user and password are valid; <code>false</code> otherwise
     */
    public boolean login(String userName, String password) {
        try {
            Locale.setDefault(Locale.ENGLISH);
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);

            Statement statement = connection.createStatement();
            String sql = "SELECT UserName FROM Users WHERE UserName='" + fixSqlFieldValue(userName) +
                    "' AND Password='" + fixSqlFieldValue(password) + "'";

            ResultSet resultSet = statement.executeQuery(sql);

            return IsResultSetHasNext(resultSet, statement, connection);
        } catch (Exception ex) {
            System.out.println(ex + " method login in DbBean");
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the user name already used in the database.
     * Establishes a connection to the DB and checks if it user name already used.
     *
     * @param userName a name what will be checked
     * @return <code>true</code> if the name already used; <code>false</code> otherwise
     */
    public boolean isRegistered(String userName) {
        try {
            Locale.setDefault(Locale.ENGLISH);
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);

            Statement statement = connection.createStatement();

            String sql = "SELECT UserName FROM Users WHERE userName ='" +
                    fixSqlFieldValue(userName) + "'";

            ResultSet resultSet = statement.executeQuery(sql);

            return IsResultSetHasNext(resultSet, statement, connection);
        } catch (Exception ex) {
            System.out.println(ex + " method isRegistered in DbBean");
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the result set has next element.
     * Checks whether the result set has the next element and closes ResultSet,
     * Statement, Connection.
     *
     * @param resultSet  the result set from execute query SQL
     * @param statement  the statement for executing SQL
     * @param connection the connection to the database
     * @return <code>true</code> if the result set has next element; <code>false</code> otherwise
     */
    private boolean IsResultSetHasNext(ResultSet resultSet, Statement statement,
                                       Connection connection) {
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex + " method IsResultSetHasNext in DbBean");
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Returns int 1 if the database update was successful.
     * Establishes a connection to the database and adds a new user in the database.
     *
     * @param userName the username to update the data
     * @param password the password of the user
     * @param email    the user's email can be null
     * @return the int 1 if execute update successful
     */
    public int addUserDataInDB(String userName, String password, String email) {
        int i = 0;

        try {
            Locale.setDefault(Locale.ENGLISH);
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            try {
                Statement statement = connection.createStatement();
                try {
                    String sql = "INSERT INTO Users (UserName, Password, Email, NumberOfGames, " +
                            "NumberOfTries) VALUES ('" +
                            fixSqlFieldValue(userName) + "', '" + fixSqlFieldValue(password) +
                            "', '" + fixSqlFieldValue(email) + "', 0, 0)";

                    i = statement.executeUpdate(sql);
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (Exception ex) {
            System.out.println(ex + " method addUserDataInDB in DbBean");
        }

        return i;
    }

    /**
     * Establishes a connection to the database and enhance number of games by 1.
     * Also increase amount of attempt by specified meaning numberOfTries for given user.
     *
     * @param numberOfTries the number of user attempts
     * @param userName      the username for update the data
     */
    public void addResultOfGameToDB(int numberOfTries, String userName) {
        try {
            Locale.setDefault(Locale.ENGLISH);
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            try {
                Statement statement = connection.createStatement();
                try {
                    String sql = "UPDATE USERS SET USERS.NUMBEROFGAMES = USERS.NUMBEROFGAMES + 1," +
                            " USERS.NUMBEROFTRIES = USERS.NUMBEROFTRIES + "
                            + numberOfTries + " WHERE USERNAME = '" + userName + "'";
                    statement.executeUpdate(sql);
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex + " method addResultOfGameToDB");
        }
    }

    /**
     * Replaces every occurrence of a single quote in the String with two single
     * quote character. This is for the proper spelling of the names and passwords in the database.
     *
     * @param value the string what will be changed
     * @return the string value with double each quote
     */
    private String fixSqlFieldValue(String value) {
        if (value == null)
            return null;
        int length = value.length();
        StringBuffer fixedValue = new StringBuffer((int) (length * 1.1));
        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            if (c == '\'')
                fixedValue.append("''");
            else
                fixedValue.append(c);
        }
        return fixedValue.toString();
    }

    /**
     * Returns the users and their amount of tries.
     * Establishes a connection to the database and gets the result map of average
     * amount of attempts and user names.
     *
     * @return the collection of user names and average amount of tries
     */
    public Map<String, Integer> getResultsMap() {
        Map<String, Integer> map = new HashMap<>();

        try {
            Locale.setDefault(Locale.ENGLISH);
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);

            try {
                Statement statement = connection.createStatement();
                String sql = "SELECT userName, numberOfGames, numberOfTries FROM users";
                try {
                    ResultSet resultSet = statement.executeQuery(sql);
                    try {
                        while (resultSet.next()) {
                            int average = (int) Math.round(resultSet.getInt(3) /
                                    resultSet.getDouble(2));
                            if (average > 0) {
                                map.put(resultSet.getString(1), average);
                            }
                        }
                    } finally {
                        resultSet.close();
                    }
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }

        } catch (Exception ex) {
            System.out.println(ex + " method getResultsMap in DbBean");
        }

        map = sortByValue(map);

        return map;
    }

    /**
     * Return collection reordered by values
     * Sorts a Map&lt;String, Integer&gt; by values.
     *
     * @return the collection which is reordered by the values
     */
    private static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() > o2.getValue())
                    return 1;
                else if (o1.getValue() < o2.getValue())
                    return -1;
                else return 0;
            }
        });

        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}