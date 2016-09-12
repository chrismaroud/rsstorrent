package com.bitsfromspace.torrentrss;

/**
 * @author chris
 * @since 01-09-16.
 */
public class Torrent {
    private final String name;
    private final String magnetLink;
    private final String movieTitle;

    public Torrent(String name, String magnetLink, String movieTitle) {
        this.name = name;
        this.magnetLink = magnetLink;
        this.movieTitle = movieTitle;
    }

    public String getName() {
        return name;
    }

    public String getMagnetLink() {
        return magnetLink;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
