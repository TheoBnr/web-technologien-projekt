package de.htwberlin.mywatchlist.model;

public class WatchlistEntry {
    private Long movieId;
    private String title;
    private String type;
    private String status;
    private Integer rating;

    public WatchlistEntry(Long id, String title, String type, String status, Integer rating) {
        this.movieId = id;
        this.title = title;
        this.type = type;
        this.status = status;
        this.rating = rating;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getId() { return movieId; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public Integer getRating() { return rating; }
}