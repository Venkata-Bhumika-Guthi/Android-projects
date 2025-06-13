/************************************************************************
 * *
 * CSCI 522                  Assignment 5                  current semester *
 * *
 * App Name: Indian Cricket *
 * *
 * Class Name: MainActivity.java *
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
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_COLUMNS = 3;
    private static final int NUM_ROWS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // Main vertical layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Heading
        TextView heading = new TextView(this);
        heading.setText("Indian Cricket Players");
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextSize(35);
        heading.setGravity(Gravity.CENTER);
        heading.setPadding(0, 100, 0, 50);
        mainLayout.addView(heading);
        // Subheading
        TextView subheading = new TextView(this);
        subheading.setText("Please choose your favorite player!");
        subheading.setTypeface(null, Typeface.BOLD);
        subheading.setTextSize(20);
        subheading.setGravity(Gravity.CENTER);
        subheading.setPadding(0, 50, 0, 50); // Adjust padding as needed
        mainLayout.addView(subheading);


        // Getting screen width for dynamically setting ImageButton sizes
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int imageSize = size.x / (NUM_COLUMNS);

        // GridLayout for images
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(NUM_COLUMNS);
        gridLayout.setRowCount(NUM_ROWS);
        gridLayout.setPadding(0, 30, 0, 0);

        // Creating and adding ImageButtons to the GridLayout
        for (int i = 0; i < 9; i++) {
            ImageButton imageButton = new ImageButton(this);
            int imageResId = getImageResourceByIndex(i);
            imageButton.setImageResource(imageResId);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);

            // Setting layout parameters for each button
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = imageSize;
            params.height = imageSize;
            params.setMargins(0, 0, 0, 0);
            imageButton.setLayoutParams(params);

            // Setting OnClickListener to open DetailActivity with specific player information
            int finalI = i;
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("imageRes", imageResId);

                    // Set specific player information based on index
                    switch (finalI) {
                        case 0:
                            intent.putExtra("name", "<b>Virat Kohli</b>");
                            intent.putExtra("details", "<b>Role:</b> Batsman, former captain<br/>" +
                                    "<b>Batting Style:</b> Right-hand bat<br/>" +
                                    "<b>Born:</b> November 5, 1988<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Captained India to numerous victories across formats.</li>" +
                                    "<li>Holds multiple records, including the fastest century in ODIs.</li></ul>");
                            break;
                        case 1:
                            intent.putExtra("name", "<b>Rohit Sharma</b>");
                            intent.putExtra("details", "<b>Role:</b> Batsman, captain<br/>" +
                                    "<b>Batting Style:</b> Right-hand bat<br/>" +
                                    "<b>Born:</b> April 30, 1987<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Known for scoring double centuries in ODIs.</li>" +
                                    "<li>Led India to several T20 series wins as captain.</li></ul>");
                            break;
                        case 2:
                            intent.putExtra("name", "<b>Jasprit Bumrah</b>");
                            intent.putExtra("details", "<b>Role:</b> Bowler<br/>" +
                                    "<b>Bowling Style:</b> Right-arm fast<br/>" +
                                    "<b>Born:</b> December 6, 1993<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Famous for his death-over bowling skills.</li>" +
                                    "<li>Among the fastest Indian bowlers, with consistent performances in all formats.</li></ul>");
                            break;
                        case 3:
                            intent.putExtra("name", "<b>KL Rahul</b>");
                            intent.putExtra("details", "<b>Role:</b> Batsman, wicketkeeper<br/>" +
                                    "<b>Batting Style:</b> Right-hand bat<br/>" +
                                    "<b>Born:</b> April 18, 1992<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Versatile batsman and wicketkeeper.</li>" +
                                    "<li>Known for scoring quickly and adapting to various positions in the batting lineup.</li></ul>");
                            break;
                        case 4:
                            intent.putExtra("name", "<b>Hardik Pandya</b>");
                            intent.putExtra("details", "<b>Role:</b> All-rounder<br/>" +
                                    "<b>Batting Style:</b> Right-hand bat<br/>" +
                                    "<b>Bowling Style:</b> Right-arm fast-medium<br/>" +
                                    "<b>Born:</b> October 11, 1993<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Renowned for his hard-hitting batting and effective fast bowling.</li>" +
                                    "<li>Has won matches with his all-round performances.</li></ul>");
                            break;
                        case 5:
                            intent.putExtra("name", "<b>Ravindra Jadeja</b>");
                            intent.putExtra("details", "<b>Role:</b> All-rounder<br/>" +
                                    "<b>Batting Style:</b> Left-hand bat<br/>" +
                                    "<b>Bowling Style:</b> Left-arm orthodox spin<br/>" +
                                    "<b>Born:</b> December 6, 1988<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Highly skilled in both bowling and batting.</li>" +
                                    "<li>Especially valuable in Test and ODI formats.</li></ul>");
                            break;
                        case 6:
                            intent.putExtra("name", "<b>Shubman Gill</b>");
                            intent.putExtra("details", "<b>Role:</b> Batsman<br/>" +
                                    "<b>Batting Style:</b> Right-hand bat<br/>" +
                                    "<b>Born:</b> September 8, 1999<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Known for his technical skill and elegance, especially in Test cricket.</li>" +
                                    "<li>A promising young talent in Indian cricket.</li></ul>");
                            break;
                        case 7:
                            intent.putExtra("name", "<b>Suryakumar Yadav</b>");
                            intent.putExtra("details", "<b>Role:</b> Batsman<br/>" +
                                    "<b>Batting Style:</b> Right-hand bat<br/>" +
                                    "<b>Born:</b> September 14, 1990<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Aggressive middle-order batsman, known for innovative shots.</li>" +
                                    "<li>Made an impact with rapid scoring in limited-overs cricket.</li></ul>");
                            break;
                        case 8:
                            intent.putExtra("name", "<b>Rishabh Pant</b>");
                            intent.putExtra("details", "<b>Role:</b> Wicketkeeper-batsman<br/>" +
                                    "<b>Batting Style:</b> Left-hand bat<br/>" +
                                    "<b>Born:</b> October 4, 1997<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Explosive batsman and reliable wicketkeeper.</li>" +
                                    "<li>Known for match-winning innings and aggressive batting style.</li></ul>");
                            break;
                       /* case 9:
                            intent.putExtra("name", "<b>Mohammed Shami</b>");
                            intent.putExtra("details", "<b>Role:</b> Bowler<br/>" +
                                    "<b>Bowling Style:</b> Right-arm fast<br/>" +
                                    "<b>Born:</b> September 3, 1990<br/><br/>" +
                                    "<b>Career Highlights:</b><ul>" +
                                    "<li>Skilled fast bowler, especially effective in seam-friendly conditions.</li>" +
                                    "<li>Has taken key wickets for India across all formats.</li></ul>");
                            break;*/
                        default:
                            intent.putExtra("name", "Unknown Player");
                            intent.putExtra("details", "No details available.");
                            break;
                    }
                    startActivity(intent);
                }
            });

            // Adding the ImageButton to the GridLayout
            gridLayout.addView(imageButton);
        }

        // Adding GridLayout to mainLayout
        mainLayout.addView(gridLayout);


        // Subheading
        TextView subheading1 = new TextView(this);
        subheading1.setText("Know more about them :)");
        //subheading.setTypeface(null, Typeface.BOLD);
        subheading1.setTextSize(25);
        subheading1.setGravity(Gravity.CENTER);
        subheading1.setPadding(0, 50, 0, 50); // Adjust padding as needed
        mainLayout.addView(subheading1);
        // Setting the main layout as the content view
        setContentView(mainLayout);
    }

    // Method to get a sample image resource by index
    private int getImageResourceByIndex(int index) {
        switch (index) {
            case 0: return R.drawable.image1;
            case 1: return R.drawable.image2;
            case 2: return R.drawable.image3;
            case 3: return R.drawable.image4;
            case 4: return R.drawable.image5;
            case 5: return R.drawable.image6;
            case 6: return R.drawable.image7;
            case 7: return R.drawable.image8;
            case 8: return R.drawable.image9;
           // case 9: return R.drawable.image10;
            default: return R.drawable.image1;
        }
    }
}
