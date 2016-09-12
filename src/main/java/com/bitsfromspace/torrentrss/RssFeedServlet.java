package com.bitsfromspace.torrentrss;

import com.bitsfromspace.torrentrss.services.MovieRatingService;
import com.bitsfromspace.torrentrss.services.TorrentRssFeedService;
import com.bitsfromspace.torrentrss.services.TorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chris
 * @since 01-09-16.
 */
@Controller
@WebServlet(urlPatterns = "/q/*", name = "Torrent Search RSS Feed Servlet")
public class RssFeedServlet extends HttpServlet {

    private final TorrentService torrentService;
    private final TorrentRssFeedService torrentRssFeedService;
    private final MovieRatingService movieRatingService;

    @Autowired
    public RssFeedServlet(TorrentService torrentService, TorrentRssFeedService torrentRssFeedService, MovieRatingService movieRatingService) {
        this.torrentService = torrentService;
        this.torrentRssFeedService = torrentRssFeedService;
        this.movieRatingService = movieRatingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String query = req.getPathInfo().substring(1);
        if (query.length() < 2) {
            resp.sendError(400, "Invalid request: Search query missing or too short, e.g. /myTitle.");
        }
        final Double minImdbRating = req.getParameter("minImdbRating") == null ? null : Double.parseDouble(req.getParameter("minImdbRating"));

        try {
            final Set<String> movieTitles = new HashSet<>();

            final List<Torrent> searchResults = torrentService.searchLatestTorrents(query).stream()
                    .filter(t -> movieTitles.add(t.getMovieTitle())) // filter out duplicates
                    .filter(t -> minImdbRating == null || meetsImdbScore(t, minImdbRating))
                    .collect(Collectors.toList());

            final String rss = torrentRssFeedService.generateFeed(searchResults);
            try (OutputStream out = resp.getOutputStream()) {
                out.write(rss.getBytes());
            }
        } catch (Exception ex) {
            resp.sendError(500, ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean meetsImdbScore(Torrent t, Double minImdbRating) {

        try {
            final Double actualScore = movieRatingService.getRating(t.getMovieTitle());

            return actualScore != null && actualScore > minImdbRating;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
