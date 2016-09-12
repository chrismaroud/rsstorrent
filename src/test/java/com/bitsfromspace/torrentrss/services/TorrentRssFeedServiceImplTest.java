package com.bitsfromspace.torrentrss.services;

import com.bitsfromspace.torrentrss.Torrent;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author chris
 * @since 01-09-16.
 */
public class TorrentRssFeedServiceImplTest {

    @Test
    public void test() throws Exception {

        final List<Torrent> torrentList = Arrays.asList(
                new Torrent(
                        "INDEPENDENCE DAY RESURGENCE (2016) HD-TS x264 AC3 2 0 NL Subs MM",
                        "magnet:?xt=urn:btih:25390317220b845086960f492071a2cf5556aa8f&dn=INDEPENDENCE+DAY+RESURGENCE+%282016%29+HD-TS+x264+AC3+2+0+NL+Subs+MM&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969",
                        "title1"
                ),
                new Torrent(
                        "ROKJESDAG_NL-RENTAL_AVI",
                        "magnet:?xt=urn:btih:b55e468ba43b3c5400fa7843b4ce0855f492b7d7&dn=ROKJESDAG_NL-RENTAL_AVI&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969",
                        "title2"
                ),
                new Torrent(
                        "The Best of Me (2014) BRRip (xvid) NL Subs. DMT",
                        "magnet:?xt=urn:btih:69f6b59941e3ce06f0a77c4c2f8fb4c189704bc4&dn=The+Best+of+Me+%282014%29+BRRip+%28xvid%29+NL+Subs.+DMT&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969",
                        "title3"
                )

        );
        final String rssFeedContent = new TorrentRssFeedServiceImpl().generateFeed(torrentList);
        assertNotNull(rssFeedContent);


    }
}