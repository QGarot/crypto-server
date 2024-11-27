package org.cryptoserver.storage.dao;

import org.cryptoserver.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CryptocurrenciesDao {

    /***
     * Insert a cryptocurrency in the database
     */
    public void insert(String id, String fullName) {
        Connection connection;
        PreparedStatement preparedStatement;

        String sql = "INSERT cryptocurrencies (id, fullname) VALUES (?, ?)";

        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, fullName);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, String> getAll() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        HashMap<String, String> cryptoCurrencies = new HashMap<>();

        String sql = "SELECT * FROM cryptocurrencies;";

        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                cryptoCurrencies.put(resultSet.getString("id"), resultSet.getString("fullname"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cryptoCurrencies;
    }
}
