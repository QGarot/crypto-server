package org.cryptoserver.storage.dao;

import org.cryptoserver.engine.Cryptocurrency;
import org.cryptoserver.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CryptocurrenciesDao {

    /***
     * Insert a cryptocurrency object in the database
     * @param cryptocurrency
     */
    public void insert(Cryptocurrency cryptocurrency) {
        Connection connection;
        PreparedStatement preparedStatement;

        String sql = "INSERT cryptocurrencies (id, fullname) VALUES (?, ?)";

        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cryptocurrency.getId());
            preparedStatement.setString(2, cryptocurrency.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
