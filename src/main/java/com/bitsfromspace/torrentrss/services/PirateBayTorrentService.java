package com.bitsfromspace.torrentrss.services;

import com.bitsfromspace.torrentrss.Torrent;
import com.bitsfromspace.torrentrss.utils.Cache;
import com.bitsfromspace.torrentrss.utils.TimeProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.bitsfromspace.torrentrss.utils.MovieTitleParser.parseMovieTitle;

/**
 * @author chris
 * @since 01-09-16.
 */
@Component
public class PirateBayTorrentService implements TorrentService {

    private final Cache<String, List<Torrent>> searchCache;


    @Autowired
    public PirateBayTorrentService(TimeProvider timeProvider, @Value("${torrentService.cacheResults.minutes}") int cacheTimeoutMinues) {
        this.searchCache = new Cache<>(timeProvider, 1000, cacheTimeoutMinues, TimeUnit.MINUTES);
    }

    @Override
    public List<Torrent> searchLatestTorrents(String search) throws Exception {
        final String normalizedSearchQuery = search.toLowerCase().trim();

        return searchCache.computeIfAbsent(normalizedSearchQuery, this::executeSearch);
    }

    private List executeSearch(String search) {

        try {
            final String searchUrl = String.format("https://thepiratebay.org/search/%s/0/3/201", search);
            final Document doc = Jsoup.connect(searchUrl)
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/601.6.17 (KHTML, like Gecko) Version/9.1.1 Safari/601.6.17")
                    .get();


            final Elements names = doc.select("table#searchResult tbody tr div.detName a");//.get(0).text();
            final Elements links = doc.select("table#searchResult tbody > tr > td:eq(1) > a:eq(1)");//.get(0).attr("href")

            assert names.size() == links.size();

            final List<Torrent> results = new ArrayList<>();
            for (int i = 0; i < names.size(); i++) {
                results.add(new Torrent(
                        names.get(i).text(),
                        links.get(i).attr("href"),
                        parseMovieTitle(names.get(i).text())));
            }

            return results;
        } catch (IOException ioEx){
            throw new RuntimeException(ioEx);
        }
    }
}
