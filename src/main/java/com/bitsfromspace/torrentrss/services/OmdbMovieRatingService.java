package com.bitsfromspace.torrentrss.services;

import com.bitsfromspace.torrentrss.utils.Cache;
import com.bitsfromspace.torrentrss.utils.TimeProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import static com.bitsfromspace.torrentrss.utils.LogUtils.logError;

/**
 * @author chris
 * @since 01-09-16.
 */
@SuppressWarnings("WeakerAccess")
@Component
public class OmdbMovieRatingService implements MovieRatingService {

    private final Cache<String, Double> ratingsCache;
    private final static Double RATING_UNAVAILABLE = -1D;

    @Autowired
    public OmdbMovieRatingService(TimeProvider timeProvider, @Value("${movieRatingServie.cacheReults.minutes}") int cacheTimeoutMinutes){
        this.ratingsCache = new Cache<>(timeProvider, 10_000, cacheTimeoutMinutes, TimeUnit.HOURS);
    }


    @Override
    public Double getRating(String movieTitle) {
        final Double rating = ratingsCache.computeIfAbsent(movieTitle, this::loadRating);
        return RATING_UNAVAILABLE.equals(rating) ? null : rating;

    }

    private Double loadRating(String movieTitle){
        try {
            final String url = String.format("http://www.omdbapi.com/?t=%s&y=&plot=short&r=json", URLEncoder.encode(movieTitle, "UTF8"));
            final BasicDBObject result = (BasicDBObject) JSON.parse(httpGet(url));
            return Double.parseDouble(result.get("imdbRating").toString());
        } catch (NullPointerException | NumberFormatException | IOException ex) {
            logError(OmdbMovieRatingService.class, String.format("Unable to get movie rating for: '%s'", movieTitle), null);
            return RATING_UNAVAILABLE;
        }
    }

    private String httpGet(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        try (InputStream inputStream = connection.getInputStream()) {
            final byte[] buffer = new byte[4096];
            final StringBuilder stringBuilder = new StringBuilder();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > -1) {
                stringBuilder.append(new String(buffer, 0, bytesRead));
            }
            return stringBuilder.toString();
        } finally {
            connection.disconnect();
        }
    }
}
