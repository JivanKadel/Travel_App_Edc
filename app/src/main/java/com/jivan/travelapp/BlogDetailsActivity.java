package com.jivan.travelapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity {
    private ImageView imageMain;
    private ImageView imageAvatar;
    private ImageView backIcon;
    private TextView textDate;
    private TextView textTitle;
    private TextView textAuthor;
    private TextView textRating;
    private RatingBar ratingBar;
    private TextView totalViews;
    private TextView textDescription;

    private ProgressBar progressBar;
//    public static final String IMAGE_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/3436e16367c8ec2312a0644bebd2694d484eb047/images/sydney_image.jpg";
//    public static final String AVATAR_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/3436e16367c8ec2312a0644bebd2694d484eb047/avatars/avatar1.jpg";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        imageMain = findViewById(R.id.mainImage);
//        Glide.with(this).load(IMAGE_URL).transition(DrawableTransitionOptions.withCrossFade()).into(imageMain);
//        imageMain.setImageResource(R.drawable.sydney);

        imageAvatar = findViewById(R.id.imageAvatar);
//        Glide.with(this).load(AVATAR_URL).transform(new CircleCrop()).into(imageAvatar);

        textDate = findViewById(R.id.textDate);
        textDate.setText("August 2, 2019");

        textTitle = findViewById(R.id.textTitle);
        textTitle.setText("G'day from Sydney");

        textAuthor = findViewById(R.id.sydney_guide_name);
        textAuthor.setText("Grayson Wells");

        textRating = findViewById(R.id.sydney_text_rating);
        textRating.setText("4.4");

        ratingBar = findViewById(R.id.sydney_guide_rating);
        ratingBar.setRating(4.4F);

        totalViews = findViewById(R.id.sydney_views);
        totalViews.setText("(2687 views)");

        textDescription = findViewById(R.id.sydney_description);
        textDescription.setText("Australia is one of the most beautiful place on the planet.");

        backIcon = findViewById(R.id.back_arrow);
        backIcon.setOnClickListener(v -> finish());

        progressBar = findViewById(R.id.progressBar);

        loadData();

    }

    public void loadData() {
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() {
            @Override
            public void onSuccess(List<Blog> blogList) {
                runOnUiThread(() -> showData(blogList.get(0)));
            }

            @Override
            public void onError() {
                runOnUiThread(()->showErrorSnackBar());
            }
        });
    }

    public void showData(Blog blog) {
        progressBar.setVisibility(TextView.GONE);
        textTitle.setText(blog.getTitle());
        textAuthor.setText(blog.getAuthor().getName());
        textDate.setText(blog.getDate());
        textRating.setText(String.valueOf(blog.getRating()));
        totalViews.setText(String.format("(%d views)", blog.getViews()));
        textDescription.setText(blog.getDescription());
        ratingBar.setRating(blog.getRating());

        Glide.with(this).load(blog.getImage()).
                transition(DrawableTransitionOptions.withCrossFade())
                .into(imageMain);

        Glide.with(this)
                .load(blog.getAuthor().getAvatar())
                .transform(new CircleCrop()).transition(DrawableTransitionOptions.withCrossFade())
                .into(imageAvatar);
    }

    private void showErrorSnackBar(){
        View rootView = findViewById(android.R.id.content);
        Snackbar errorSnack = Snackbar.make(rootView, "Error loading blog articles", Snackbar.LENGTH_INDEFINITE);
        errorSnack.setActionTextColor(getResources().getColor(R.color.orange500));

        errorSnack.setAction("Retry", v->{
            loadData();
            errorSnack.dismiss();
        });
        errorSnack.show();
    }
}
