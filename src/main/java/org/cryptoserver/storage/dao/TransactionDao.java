package org.cryptoserver.storage.dao;

import org.cryptoserver.storage.Database;
import org.cryptoserver.wallets.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    public void insert(int userId, Transaction transaction) {
        Connection connection;
        PreparedStatement preparedStatement;

        String sql = "INSERT transactions (user_id, transaction_type, credit, crypto_id, description) VALUES (?, ?, ?, ?, ?)";

        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, transaction.getTransactionType());
            preparedStatement.setInt(3, transaction.getCredit());
            preparedStatement.setString(4, transaction.getCryptoId());
            preparedStatement.setString(5, transaction.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Transaction> get(int userId) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE user_id = ?";

        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new Transaction(resultSet.getString("transaction_type"), resultSet.getInt("credit"), resultSet.getString("crypto_id"), resultSet.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return transactions;
    }
}
