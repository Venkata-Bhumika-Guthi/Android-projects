/************************************************************************
 * *
 * CSCI 522                  Assignment 5                  current semester *
 * *
 * App Name: Indian Cricket *
 * *
 * Class Name: DetailActivity.java *
 * *
 * Developers: Venkata Bhumika Guthi (Z2016526)
 *             Bharath Kumar Bandi (Z2001489) *
 * *
 * Due Date: 11-01-2024 *
 * *
 * Purpose: This .java file develops an app that uses Grid Layout and also two activities to navigate through the functionalities of the app.*
 * It also contains image buttons and when we click on the image button, it goes to second activity that displays the details of the image in that button. *
 * The main purpose of this app is to get familiar with Intents and Activities. *
 * *
 ************************************************************************/


package edu.niu.android.indiancricket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creating a vertical LinearLayout to hold the image and text
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        // Getting screen width for scaling image
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;

        // Creating an ImageView for the subject image
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        layout.addView(imageView);

        // Creating a TextView for the subject name
        TextView nameTextView = new TextView(this);
        nameTextView.setTextSize(24);
        nameTextView.setPadding(0, 16, 0, 16);
        layout.addView(nameTextView);

        // Creating a TextView for the subject details
        TextView detailsTextView = new TextView(this);
        detailsTextView.setTextSize(16);
        layout.addView(detailsTextView);

        // Retrieving the intent data
        Intent intent = getIntent();
        String details = null;
        String name = null;
        if (intent != null) {
            int imageRes = intent.getIntExtra("imageRes", 0);
            name = intent.getStringExtra("name");
            details = intent.getStringExtra("details");

            // Setting the data on the views
            imageView.setImageResource(imageRes);
            nameTextView.setText(name);
            detailsTextView.setText(details);
        }
        nameTextView.setText(HtmlCompat.fromHtml(name, HtmlCompat.FROM_HTML_MODE_LEGACY));
        detailsTextView.setText(HtmlCompat.fromHtml(details, HtmlCompat.FROM_HTML_MODE_LEGACY));
        // Creating a Button to go back to the main activity
        Button backButton = new Button(this);
        backButton.setText("Home");
        backButton.setTextSize(15);
        backButton.setPadding(0, 50, 0, 50);
        // Setting the button's layout parameters to wrap content for width and height
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.gravity = Gravity.CENTER_HORIZONTAL;
        backButton.setLayoutParams(buttonParams);


        layout.addView(backButton);

// Applying a color filter to the button
        backButton.getBackground().setColorFilter(Color.parseColor("#550000FF"), PorterDuff.Mode.SRC_ATOP);


        // Setting OnClickListener for the button to go back to MainActivity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closing DetailActivity and go back to MainActivity
            }
        });

        // Setting the layout as the content view
        setContentView(layout);
    }
}
