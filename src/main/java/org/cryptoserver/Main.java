package org.cryptoserver;

import org.cryptoserver.engine.CoinAPIManager;
import org.cryptoserver.storage.Database;
import org.json.JSONArray;

public class Main {
    public static void main(String[] args) {
        System.out.println("The server is starting!");
        System.out.println();

        System.out.println("Try to connect to the database...");
        Database.getInstance();

        System.out.println("Database & all managers are loaded!");
        System.out.println("Test section:");
        System.out.println();
    }

    public static void testAPI() {
        System.out.println("Hello World!");
        System.out.println();
        JSONArray data = CoinAPIManager.getInstance().getTimeSeriesData("BTC", "EUR", "1DAY", "2024-01-01T00:00:00", 360);
        System.out.println(data.get(301));
    }
}
