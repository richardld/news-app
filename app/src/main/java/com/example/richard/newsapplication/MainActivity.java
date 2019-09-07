
package com.example.richard.newsapplication;

import android.content.Intent;
import android.graphics.Color;



import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private NewsCollection nc = new NewsCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Request full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set activity_main as view
        setContentView(R.layout.activity_main);

        // MAYBE CHANGE THIS LATER TO ASYNC THREAD
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Create objects for scrolling container
        final ScrollView scroll = findViewById(R.id.scroll);

        // Create objects for linear container
        final LinearLayout root = findViewById(R.id.newslayout);

        //Add initial articles to container
        addMoreArticles(root);

        // Add listener for when scroll container reaches the bottom
        // Calls addMoreArticles to append more articles to the container
        scroll.getViewTreeObserver()
            .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (scroll.getChildAt(0).getBottom()
                            <= (scroll.getHeight() + scroll.getScrollY())) {
                        //scroll view is at bottom
                        addMoreArticles(root);
                    }
                }
            });
    }

    // Method for adding more articles to scroll container
    private void addMoreArticles(LinearLayout root) {
        // Add more articles to the NewsCollection
        nc.appendMoreArticles();

        // Loop through each new article added
        for(int i = 0; i < 20; i++) {
            // Store the next news article retrieved
            NewsArticle a = nc.nextArticle();

            // Create a text view for the title
            TextView t = new TextView(this);
            t.setText(a.toString());
            t.setTextSize(20);
            t.setPadding(15, 10, 5, 10);
            t.setGravity(Gravity.CENTER_VERTICAL);

            // Create a view with a gray background to set a horizontal bar between articles
            View l = new View(this);
            l.setMinimumHeight(2);
            l.setBackgroundColor(Color.GRAY);

            // Set LayoutParams for horizontal line
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 0, 5, 0);
            l.setLayoutParams(params);

            // Create horizontal linear layout to store entire news article display
            final LinearLayout h = new LinearLayout(this);
            h.setOrientation(LinearLayout.HORIZONTAL);

            // Set LayoutParams for h
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    275
            );
            h.setLayoutParams(params2);


            // Create ImageView to contain news article image
            ImageView iv = new ImageView(this);

            // Load Image from URL using Picasso and add into ImageView container
            Picasso.get().load(a.getUrlToImage()).resize(0, 400).into(iv);

            // Set LayoutParams for ImageView
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    400,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            iv.setLayoutParams(imageParams);

            // Set LayoutParams for right side of display
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params3.setMargins(15, 5, 15, 5);

            // Create vertical linear layout for right side of article display
            final LinearLayout j = new LinearLayout(this);
            j.setLayoutParams(params3);
            j.setOrientation(LinearLayout.VERTICAL);

            // Create TextView for author name and information
            TextView author = new TextView(this);
            author.setText(a.getAuthor());
            author.setTextSize(14);
            author.setLayoutParams(params3);

            // Add views to right side of display
            j.addView(t);
            //j.addView(author);

            // Add views to entire display
            h.addView(iv);
            h.addView(j);

            // Set link as tag for view
            h.setTag(a.getUrlToPage());

            // Set open link intent on click on news article
            h.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse((String)h.getTag()));
                    startActivity(browserIntent);
                }
            });

            // Add news article to scroll container
            root.addView(h);

            // Add horizontal bar to scroll container
            root.addView(l);
        }

    }

}