package org.cryptoserver.wallets;

public class Transaction {
    private final String transactionType;
    private int credit;
    private String cryptoId;
    private String description;
    // date

    public Transaction(String transactionType, int credit, String cryptoId, String description) {
        this.credit = credit;
        this.cryptoId = cryptoId;
        this.transactionType = transactionType;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(String cryptoId) {
        this.cryptoId = cryptoId;
    }
}
