package com.hadioz.moviecatalog;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import java.util.ArrayList;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private ArrayList<Movie> data;
    private Context context;

    public static String EXTRA_TITLE = "title";
    public static String EXTRA_OVERVIEW = "overview";
    public static String EXTRA_POSTER = "poster_path";
    public static String EXTRA_RELEASE = "release";
    public static String EXTRA_RATTING ="ratting";


    public MovieAdapter(ArrayList<Movie> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.overview.setText(data.get(position).getOverview());
        holder.releaseDate.setText(data.get(position).getReleaseDate());
        holder.itemView.setOnClickListener(v -> {
            Log.d(Integer.toString(position),"cliked");
            Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
            intent.putExtra(EXTRA_TITLE, data.get(position).getTitle());
            intent.putExtra(EXTRA_OVERVIEW, data.get(position).getOverview());
            intent.putExtra(EXTRA_RELEASE, data.get(position).getReleaseDate());
            intent.putExtra(EXTRA_RATTING, data.get(position).getRating());
            intent.putExtra(EXTRA_POSTER, data.get(position).getPosterUrl());



            v.getContext().startActivity(intent);
        });

        Glide.with(context).load("https://themoviedb.org/t/p/w500"+data.get(position).getPosterUrl()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() : 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView title, overview, releaseDate;
        private ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleMovie);
            overview = (TextView) itemView.findViewById(R.id.overview);
            releaseDate = (TextView) itemView.findViewById(R.id.releaseDate);
            poster = (ImageView) itemView.findViewById(R.id.poster);

        }

    }

}
