package org.cryptoserver.users;

import org.cryptoserver.storage.dao.UserDetailsDao;
import org.cryptoserver.users.components.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    public static UserManager instance;
    private final UserDetailsDao userDetailsDao;
    private final List<User> users;

    public UserManager() {
        this.userDetailsDao = new UserDetailsDao();
        this.users = new ArrayList<User>();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }

        return instance;
    }

    public List<User> getLoggedUsers() {
        return this.users;
    }

    public boolean attemptLogin(String username, String password) {
        boolean success;

        // Fetch details with provided data
        UserDetails details = this.getUserDetailsDao().get(username, password);

        if (details != null) {
            // Create corresponding User instance and add it to the connected users list
            success = true;
            User user = new User(details);
            this.getLoggedUsers().add(user);
        } else {
            success = false;
        }

        return success;
    }

    public UserDetailsDao getUserDetailsDao() {
        return this.userDetailsDao;
    }
}
