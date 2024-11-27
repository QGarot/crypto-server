package org.cryptoserver.users;

import org.cryptoserver.users.components.UserDetails;
import org.cryptoserver.users.components.Wallet;

public class User {

    private UserDetails details;
    private Wallet wallet;

    public User(UserDetails details) {
        this.details = details;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public UserDetails getDetails() {
        return this.details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }
}
