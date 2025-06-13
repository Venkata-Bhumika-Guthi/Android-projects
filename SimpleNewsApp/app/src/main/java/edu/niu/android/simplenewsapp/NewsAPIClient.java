package edu.niu.android.simplenewsapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NewsAPIClient {

    private final String apiKey; // Your API Key // Your API Key
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new GsonBuilder().create();

    public NewsAPIClient(String apiKey) {
        this.apiKey = apiKey;
        initializeClient();
        this.gson = new GsonBuilder().create(); // Use Gson to deserialize JSON
    }

    private void initializeClient() {
        if (client == null) {
            client = new OkHttpClient();
        }
    }

    public interface ArticlesResponseCallback {
        void onSuccess(ArticleResponse response);
        void onFailure(Throwable throwable);
    }

    public void getTopHeadlines(String category, String language, String country, ArticlesResponseCallback callback) {
        initializeClient();

        // Construct the URL
        String url = "https://newsapi.org/v2/top-headlines?"
                + "apiKey=" + apiKey
                + (country != null ? "&country=" + country : "")
                + (language != null ? "&language=" + language : "")
                + (category != null ? "&category=" + category : "");

        // Build the HTTP request
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0") // Mimic browser requests
                .build();

        // Make an asynchronous HTTP request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle HTTP failure
                callback.onFailure(e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Parse the JSON response using Gson
                    String responseBody = response.body().string();
                    try {
                        ArticleResponse articleResponse = gson.fromJson(responseBody, ArticleResponse.class);
                        callback.onSuccess(articleResponse);
                    } catch (Exception e) {
                        callback.onFailure(new Throwable("Error parsing JSON: " + e.getMessage()));
                    }
                } else {
                    // Handle non-2XX responses
                    callback.onFailure(new Throwable("HTTP error: " + response.code()));
                }
            }
        });
    }
}
