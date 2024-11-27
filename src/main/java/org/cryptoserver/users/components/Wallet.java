package org.cryptoserver.users.components;

import org.cryptoserver.wallets.Transaction;

import java.util.List;

public class Wallet {
    private final List<Transaction> transactions;

    public Wallet(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void registerTransaction(String transactionType, int credit, String cryptoId, String description) {
        this.getTransactions().add(new Transaction(transactionType, credit, cryptoId, description));
    }
}
