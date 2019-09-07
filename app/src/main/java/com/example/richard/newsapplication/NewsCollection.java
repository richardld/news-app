package com.example.richard.newsapplication;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsCollection {
    // Page number for API call
    private int page = 1;

    // Secret API Key
    private String apiKey = "a5eaf03446824c559cc8c655fe0e769b";

    private JSONconvert JSONconvert;
    private int nextIndex = 0;

    // ArrayList for storage of news objects
    ArrayList<NewsArticle> articles = new ArrayList<NewsArticle>();

    // Create the news collection object
    NewsCollection() {

    }

    // Add 30 more articles to the article collection
    public void appendMoreArticles() {
        try {
            // Create url for GET request
            URL url = new URL("https://newsapi.org/v2/everything?q=" + "politics" + "&from=" +
                    whatIsTheDate(0) + "&to=" + whatIsTheDate(-7) + "&sortBy=popularity"
                    + "&pageSize=30&page=" + page + "&apiKey=" + apiKey);

            // Create HTTP connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Read HTTP response and append to string buffer
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Attempt to parse JSON response
            try {
                // Use GSON library for parsing of JSON, store response as a string
                Gson gson = new Gson();
                String strResponse = response.toString();

                // Create temporary map to store JSON in usable format
                // Place objects in 'articles' in JSON into map
                Map<String,Object> map = new HashMap<String,Object>();
                map = (Map<String,Object>) gson.fromJson(strResponse, map.getClass());

                System.out.println(map.toString());

                ArrayList alsoMap = (ArrayList<Object>)map.get("articles");

                for(int i = 0; i < alsoMap.size(); i++) {
                    LinkedTreeMap<Object, Object> sigh = (LinkedTreeMap<Object, Object>)(alsoMap).get(i);

                    articles.add(new NewsArticle((String)(sigh.get("title")),(String)sigh.get("author"),
                            (String)((LinkedTreeMap<Object, Object>)(sigh.get("source"))).get("name"),
                            (String)sigh.get("publishedAt"), (String) sigh.get("url"), (String) sigh.get("urlToImage")));
                    System.out.println(articles.get(i));

                }
                page++;

            } catch (Exception e) {
                // Handle exception
                Log.d("Oh no.", Log.getStackTraceString(e));

            }

        } catch (Exception e) {
            // Handle exception
            Log.d("Oh no.", Log.getStackTraceString(e));
        }
        page++;
    }

    // Return the next article when called
    public NewsArticle nextArticle() {
        if(nextIndex < articles.size()) {
            nextIndex++;
            return articles.get(nextIndex);
        } else {
            return null;
        }
    }

    // Returns if the collection has another article that has not been returned yet
    public boolean hasNext() {
        return nextIndex < articles.size();
    }


    // Returns the date in MM-DD-YYYY format with an offset number of days
    // whatIsTheDate(-10) gives the date 10 days ago
    private String whatIsTheDate(int offset) {
        LocalDate today = LocalDate.now().minusDays(offset*-1);
        return today.toString();
    }


}
