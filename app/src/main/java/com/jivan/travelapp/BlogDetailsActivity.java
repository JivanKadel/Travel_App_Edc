package com.jivan.travelapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BlogDetailsActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        ImageView imageMain = findViewById(R.id.mainImage);
        imageMain.setImageResource(R.drawable.sydney);

        ImageView imageAvatar = findViewById(R.id.imageAvatar);
        imageAvatar.setImageResource(R.drawable.avatar);

        TextView textDate = findViewById(R.id.textDate);
        textDate.setText("August 2, 2019");

        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText("G'day from Sydney");

        TextView textAuthor = findViewById(R.id.sydney_guide_name);
        textAuthor.setText("Grayson Wells");

        TextView ratingText = findViewById(R.id.sydney_text_rating);
        ratingText.setText("4.4");

        RatingBar sydneyRating = findViewById(R.id.sydney_guide_rating);
        sydneyRating.setRating(4.4F);

        TextView totalViews = findViewById(R.id.sydney_views);
        totalViews.setText("(2687 views)");

        TextView sydneyDesc = findViewById(R.id.sydney_description);
        sydneyDesc.setText("Australia is one of the most beautiful place on the planet.");

        ImageView backIcon = findViewById(R.id.back_arrow);
        backIcon.setOnClickListener(v->finish());

    }
}
