/************************************************************************
 * *
 * CSCI 522                  Graduate Honours Project                 current semester *
 * *
 * App Name: News App *
 * *
 * Class Name: MainActivity.java *
 * *
 * Developers: Venkata Bhumika Guthi (Z2016526)
 *             Bharath Kumar Bandi (Z2001489) *
 * *
 * Due Date: 11-29-2024 *
 * *
 * Purpose: This .java file develops an app that provides real-time news using API integration. *
 ************************************************************************/

package edu.niu.android.simplenewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearProgressIndicator progressIndicator;
    private SearchView searchView;

    private List<Article> articleList = new ArrayList<>();
    private NewsRecyclerAdapter adapter;
    private static final String API_KEY = "bb2f77671aca451db40a0c5922cef16a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupSearchView();
        setupSwipeToRefresh();

        // Fetch general news on app launch
        getNews("GENERAL", null);
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        searchView = findViewById(R.id.search_view);

        Button[] categoryButtons = {
                findViewById(R.id.btn_1),
                findViewById(R.id.btn_2),
                findViewById(R.id.btn_3),
                findViewById(R.id.btn_4),
                findViewById(R.id.btn_5),
                findViewById(R.id.btn_6),
                findViewById(R.id.btn_7)
        };

        for (Button button : categoryButtons) {
            button.setOnClickListener(this);
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews("GENERAL", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getNews("GENERAL", null);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void toggleProgressIndicator(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void getNews(String category, String query) {
        toggleProgressIndicator(true);
        NewsApiClient newsApiClient = new NewsApiClient(API_KEY);

        TopHeadlinesRequest.Builder requestBuilder = new TopHeadlinesRequest.Builder()
                .language("en")
                .country("us");

        if (category != null && !category.equalsIgnoreCase("GENERAL")) {
            requestBuilder.category(category.toLowerCase());
        }

        if (query != null && !query.trim().isEmpty()) {
            requestBuilder.q(query);
        }

        newsApiClient.getTopHeadlines(
                requestBuilder.build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(() -> {
                            toggleProgressIndicator(false);
                            if (response.getArticles() != null && !response.getArticles().isEmpty()) {
                                articleList.clear();
                                articleList.addAll(response.getArticles());
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.d("NewsApp", "No articles found");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        runOnUiThread(() -> {
                            toggleProgressIndicator(false);
                            Log.e("NewsApp", "Error fetching news: " + throwable.getMessage());
                        });
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            String category = ((Button) v).getText().toString();
            getNews(category, null);
        }
    }
}
