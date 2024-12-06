package org.cryptoserver.packets.dashboard;

import org.cryptoserver.engine.CoinAPIManager;
import org.cryptoserver.packets.Event;
import org.cryptoserver.packets.headers.OutgoingHeaders;
import org.cryptoserver.server.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class CryptoSelectedEvent extends Event {
    @Override
    public void handle(Connection connection, JSONObject packet) {
        // Get packet data
        String cryptocurrencyName = packet.getString("cryptoName");
        String period = packet.getString("period");

        // Get crypto asset id with given crypto name
        String cryptoAssetId = CoinAPIManager.getInstance().getCryptoCurrencies().get(cryptocurrencyName);

        // Different cases
        //DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime previousDateTime;
        String timeEnd;
        String timeStart;
        JSONArray data;
        String periodId;
        if (period.equals("ONE_DAY")) {
            // Get previous date time
            previousDateTime = currentDateTime.minusDays(1);
            // Specify a correct periodId
            periodId = "1HRS";
        } else if (period.equals("ONE_MONTH")) {
            // Get previous date time
            previousDateTime = currentDateTime.minusMonths(1);
            // Specify a correct periodId
            periodId = "4HRS";
        } else if (period.equals("ONE_YEAR")) {
            // Get previous date time
            previousDateTime = currentDateTime.minusYears(1);
            // Specify a correct periodId
            periodId = "1DAY";
        } else {
            // Get previous date time
            previousDateTime = currentDateTime.minusMonths(1);
            // Specify a correct periodId
            periodId = "1DAY";
        }

        // Parse into ISO-8601 format
        timeEnd = currentDateTime.toString();
        timeEnd = timeEnd.substring(0, timeEnd.length() - 2).concat("Z");
        timeStart = previousDateTime.toString();
        timeStart = timeStart.substring(0, timeStart.length() - 2).concat("Z");

        data = CoinAPIManager.getInstance().getTimeSeriesData(cryptoAssetId, "EUR", periodId, timeStart, timeEnd, 100);
        System.out.println(data.toString());

        // Prepare sending packet
        JSONObject response = new JSONObject();
        response.put("header", OutgoingHeaders.DASHBOARD_SELECT_CRYPTO_RESPONSE);
        response.put("cryptoName", cryptocurrencyName);
        response.put("cryptoData", data);
        connection.sendPacket(response);
    }
}
