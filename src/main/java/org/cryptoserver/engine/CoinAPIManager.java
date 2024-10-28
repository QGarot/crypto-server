package org.cryptoserver.engine;

import okhttp3.*;
import org.json.JSONArray;

public class CoinAPIManager {

    private static CoinAPIManager instance;
    private final String API_KEY = "202BAFCB-6607-4890-AAE0-111CBE7E4681";
    private OkHttpClient client;
    private MediaType mediaType;

    public CoinAPIManager() {
        this.client = new OkHttpClient().newBuilder().build();
        this.mediaType = MediaType.parse("text/plain");
    }

    public static CoinAPIManager getInstance() {
        if (instance == null) {
            instance = new CoinAPIManager();
        }

        return instance;
    }

    public MediaType getMediaType() {
        return this.mediaType;
    }

    public OkHttpClient getClient() {
        return this.client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Returns the timeserie corresponding to the given parameters
     * @param assetIdBase: name of the cryptocurrency
     * @param assetIdQuote: name of the currency
     * @param periodId: period
     * @param timeStart: starting time in ISO-8601
     * @return JSONArray containing the requested number of data
     */
    public JSONArray getTimeSeriesData(String assetIdBase, String assetIdQuote, String periodId, String timeStart, int limit) {
        String url = "https://rest.coinapi.io/v1/exchangerate/" + assetIdBase + "/" + assetIdQuote + "/history?period_id=" + periodId + "&time_start=" + timeStart + "&limit=" + limit;

        // Make the request to CoinAPI
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "text/plain")
                .addHeader("X-CoinAPI-Key", API_KEY)
                .build();

        // Try to parse the response to a JSONArray
        JSONArray jsonArray = null;
        try {
            Response response = client.newCall(request).execute();
            jsonArray = new JSONArray(response.body().string());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return jsonArray;
    }
}
