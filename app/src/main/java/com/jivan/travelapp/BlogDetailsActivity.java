package com.jivan.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
    private static final String EXTRAS_BLOG = "EXTRAS_BLOG";

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        imageMain = findViewById(R.id.mainImage);
        imageAvatar = findViewById(R.id.imageAvatar);

        textDate = findViewById(R.id.textDate);
        textDate.setText(R.string.sydney_date);

        textTitle = findViewById(R.id.textTitle);
        textTitle.setText(R.string.sydney_title);

        textAuthor = findViewById(R.id.sydney_guide_name);
        textAuthor.setText(R.string.sydney_guide_name);

        textRating = findViewById(R.id.sydney_text_rating);
        textRating.setText(R.string.sydney_rating);

        ratingBar = findViewById(R.id.sydney_guide_rating);
        ratingBar.setRating((float) R.string.sydney_rating);

        totalViews = findViewById(R.id.sydney_views);
        totalViews.setText(R.string.sydney_views);

        textDescription = findViewById(R.id.sydney_description);
        textDescription.setText(R.string.sydney_desc);

        backIcon = findViewById(R.id.back_arrow);
        backIcon.setOnClickListener(v -> finish());

        progressBar = findViewById(R.id.progressBar);

//        loadData();

        showData(getIntent().getExtras().getParcelable(EXTRAS_BLOG));
    }

    public void loadData() {
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() {
            @Override
            public void onSuccess(List<Blog> blogList) {
                runOnUiThread(() -> showData(blogList.get(0)));
            }

            @Override
            public void onError() {
                runOnUiThread(() -> showErrorSnackbar());
            }
        });
    }

    public static void startBlogDetailsActivity(Activity activity, Blog blog){
        Intent intent = new Intent(activity, BlogDetailsActivity.class);
        intent.putExtra(EXTRAS_BLOG, blog);
        activity.startActivity(intent);
    }

    public void showData(Blog blog) {
        progressBar.setVisibility(TextView.GONE);
        textTitle.setText(blog.getTitle());
        textAuthor.setText(blog.getAuthor().getName());
        textDate.setText(blog.getDate());
        textRating.setText(String.valueOf(blog.getRating()));
        totalViews.setText(String.format("(%d views)", blog.getViews()));
//        textDescription.setText(blog.getDescription());
        textDescription.setText(Html.fromHtml(blog.getDescription()));
        ratingBar.setRating(blog.getRating());

        Glide.with(this).load(blog.getImage()).
                transition(DrawableTransitionOptions.withCrossFade())
                .into(imageMain);

        Glide.with(this)
                .load(blog.getAuthor().getAvatar())
                .transform(new CircleCrop()).transition(DrawableTransitionOptions.withCrossFade())
                .into(imageAvatar);
    }

    private void showErrorSnackbar() {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView,
                "Error during loading blog articles", Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.orange500));
        snackbar.setAction("Retry", v -> {
            loadData();
            snackbar.dismiss();
        });
        snackbar.show();
    }
}
