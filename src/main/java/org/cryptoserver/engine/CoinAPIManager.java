package org.cryptoserver.engine;

import okhttp3.*;
import org.cryptoserver.config.Configuration;
import org.cryptoserver.storage.dao.CryptocurrenciesDao;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class CoinAPIManager {

    private static CoinAPIManager instance;
    private OkHttpClient client;
    private MediaType mediaType;
    private HashMap<String, String> cryptoCurrencies;
    private final CryptocurrenciesDao cryptocurrenciesDao;

    public CoinAPIManager() {
        this.client = new OkHttpClient().newBuilder().build();
        this.mediaType = MediaType.parse("text/plain");
        this.cryptocurrenciesDao = new CryptocurrenciesDao();
        this.loadCryptoCurrencies();
    }

    public CryptocurrenciesDao getCryptocurrenciesDao() {
        return this.cryptocurrenciesDao;
    }

    public HashMap<String, String> getCryptoCurrencies() {
        return this.cryptoCurrencies;
    }

    public void setCryptoCurrencies(HashMap<String, String> cryptoCurrencies) {
        this.cryptoCurrencies = cryptoCurrencies;
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

    public boolean isPopularCryptocurrency(String id) {
        return id.equals("BTC") || id.equals("XRP") || id.equals("ADA") || id.equals("ETH") || id.equals("SOL") ||
                id.equals("LTC") || id.equals("XLM") || id.equals("FTM") || id.equals("TIA") || id.equals("SUI") ||
                id.equals("USDT") || id.equals("SEI") || id.equals("MOODENG") || id.equals("PEPE") || id.equals("WIF")
                || id.equals("FET") || id.equals("VET") || id.equals("GRASS");
    }

    public void loadCryptoCurrencies() {

        if (Configuration.SAVE_CRYPTO_IN_DATABASE) {
            this.setCryptoCurrencies(new HashMap<>());

            // Make the request to CoinAPI
            Request request = new Request.Builder()
                    .url("https://rest.coinapi.io/v1/assets")
                    .addHeader("Accept", "text/plain")
                    .addHeader("X-CoinAPI-Key", Configuration.API_KEY)
                    .build();

            // Try to parse the response to a JSONArray
            JSONArray jsonArray;
            try {
                Response response = this.getClient().newCall(request).execute();
                jsonArray = new JSONArray(response.body().string());
                JSONObject jsonObject;
                String fullName;
                String id;

                // Load all data: -> (asset_id, name)
                // Ex: (BTC, Bitcoin)
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    if ((int) jsonObject.get("type_is_crypto") == 1) {
                        fullName = jsonObject.getString("name");
                        id = jsonObject.getString("asset_id");
                        if (this.isPopularCryptocurrency(id)) {
                            this.getCryptoCurrencies().put((String) jsonObject.get("name"), (String) jsonObject.get("asset_id"));
                            this.getCryptocurrenciesDao().insert(id, fullName);
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            this.setCryptoCurrencies(this.getCryptocurrenciesDao().getAll());
        }
    }

    /**
     * Returns the timeserie corresponding to the given parameters
     * @param assetIdBase: name of the cryptocurrency
     * @param assetIdQuote: name of the currency
     * @param periodId: period
     * @param timeStart: starting time in ISO-8601
     * @return JSONArray containing the requested number of data
     */
    public JSONArray getTimeSeriesData(String assetIdBase, String assetIdQuote, String periodId, String timeStart, String timeEnd, int limit) {
        String url = "https://rest.coinapi.io/v1/exchangerate/" + assetIdBase + "/" + assetIdQuote + "/history?period_id=" + periodId + "&time_start=" + timeStart + "&time_end=" + timeEnd + "&limit=" + limit;

        // Make the request to CoinAPI
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "text/plain")
                .addHeader("X-CoinAPI-Key", Configuration.API_KEY)
                .build();

        // Try to parse the response to a JSONArray
        JSONArray jsonArray = null;
        try {
            Response response = client.newCall(request).execute();
            jsonArray = new JSONArray(response.body().string());
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }
}
