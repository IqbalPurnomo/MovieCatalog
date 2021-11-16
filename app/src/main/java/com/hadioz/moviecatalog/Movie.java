package com.hadioz.moviecatalog;

public class Movie {

    private int id;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterUrl;
    private String rating;

    public Movie(int id, String title, String overview, String releaseDate, String poster, String rating) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterUrl = poster;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public  String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getRating() { return rating; }
}
