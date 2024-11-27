package org.cryptoserver.users;

import org.cryptoserver.storage.dao.TransactionDao;
import org.cryptoserver.storage.dao.UserDetailsDao;
import org.cryptoserver.users.components.UserDetails;
import org.cryptoserver.users.components.Wallet;
import org.cryptoserver.wallets.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    public static UserManager instance;
    private final UserDetailsDao userDetailsDao;
    private final TransactionDao transactionDao;
    private final List<User> users;

    public UserManager() {
        this.userDetailsDao = new UserDetailsDao();
        this.transactionDao = new TransactionDao();
        this.users = new ArrayList<>();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
            System.out.println("Users manager loaded!");
        }
        return instance;
    }

    public List<User> getLoggedUsers() {
        return this.users;
    }

    public User getUserByUsername(String username) {
        for (User user : this.getLoggedUsers()) {
            if (user.getDetails().getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean attemptLogin(String username, String password) {
        boolean success;

        // Fetch details with provided data
        UserDetails details = this.getUserDetailsDao().get(username, password);

        if (details != null) {
            // Create corresponding User instance
            success = true;
            User user = new User(details);

            // Load personal wallet
            Wallet wallet = new Wallet(this.getTransactionDao().get(user.getDetails().getId()));
            user.setWallet(wallet);

            // Add user instance to the connected users list
            this.getLoggedUsers().add(user);
        } else {
            success = false;
        }

        return success;
    }

    public UserDetailsDao getUserDetailsDao() {
        return this.userDetailsDao;
    }

    public TransactionDao getTransactionDao() {
        return this.transactionDao;
    }
}
