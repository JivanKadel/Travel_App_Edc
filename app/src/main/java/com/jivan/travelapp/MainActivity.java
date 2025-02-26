package com.jivan.travelapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.jivan.travelapp.adapter.MainAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    private static final int SORT_TITLE = 0;
    private static final int SORT_DATE = 1;

    private int currentSort = SORT_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.main);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

            mlp.leftMargin = insets.left;
            mlp.rightMargin = insets.right;
            mlp.bottomMargin = insets.bottom;
            mlp.topMargin = insets.top;

            v.setLayoutParams(mlp);
            return windowInsets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.sort) {
                onSortClicked();
            }
            return false;
        });

        adapter = new MainAdapter(blog -> BlogDetailsActivity.startBlogDetailsActivity(this, blog));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this::loadData);

//        startActivity(new Intent(this, BlogDetailsActivity.class));
        loadData();

    }

    private void onSortClicked() {
        String[] sortBy = {"Title", "Date"};
        new MaterialAlertDialogBuilder(this)
                .setTitle("Sort order")
                .setSingleChoiceItems(sortBy, currentSort, (dialog, which) -> {
                    dialog.dismiss();
                    currentSort = which;
                    sortData();
                }).show();
    }

    public void sortData() {
        if (currentSort == SORT_TITLE) {
            adapter.sortByTitle();
        } else if (currentSort == SORT_DATE) {
            adapter.sortByDate();
        }
    }

    private void loadData() {
        refreshLayout.setRefreshing(true);
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() {
            @Override
            public void onSuccess(List<Blog> blogList) {
                runOnUiThread(() -> {
                    refreshLayout.setRefreshing(false);
                    adapter.submitList(blogList);
                });
            }

            @Override
            public void onError() {
                runOnUiThread(() -> {
                    refreshLayout.setRefreshing(false);
                    showErrorSnackBar();
                });
            }
        });
    }

    private void showErrorSnackBar() {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, "Error loading Blog Data", Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.orange500));
        snackbar.setAction("Retry", v -> {
            loadData();
            snackbar.dismiss();
        });
        snackbar.show();
    }
}