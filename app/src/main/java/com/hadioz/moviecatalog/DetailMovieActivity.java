package com.hadioz.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ImageView poster = (ImageView) findViewById(R.id.detailPoster);
        TextView title = (TextView) findViewById(R.id.detailTitle);
        TextView release = (TextView) findViewById(R.id.detailRelease);
        TextView rating = (TextView) findViewById(R.id.detailRating);
        TextView overview = (TextView) findViewById((R.id.detailOverview));

        title.setText(getIntent().getStringExtra(MovieAdapter.EXTRA_TITLE));
        release.setText(getIntent().getStringExtra(MovieAdapter.EXTRA_RELEASE));
        rating.setText(getIntent().getStringExtra(MovieAdapter.EXTRA_RATTING));
        overview.setText(getIntent().getStringExtra(MovieAdapter.EXTRA_OVERVIEW));

        Glide.with(DetailMovieActivity.this).load("https://themoviedb.org/t/p/w500"+getIntent().getStringExtra(MovieAdapter.EXTRA_POSTER)).into(poster);

    }
}