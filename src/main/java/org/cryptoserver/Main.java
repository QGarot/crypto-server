package org.cryptoserver;

import org.cryptoserver.engine.CoinAPIManager;
import org.json.JSONArray;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println();
        JSONArray data = CoinAPIManager.getInstance().getTimeSeriesData("BTC", "EUR", "1DAY", "2024-01-01T00:00:00", 360);
        System.out.println(data.get(301));
    }
}
